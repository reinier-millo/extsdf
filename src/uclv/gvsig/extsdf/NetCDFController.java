/*
 * NetCDFController.java
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * Copyright (C) 2013 Reinier Millo Sánchez <rmillo@uclv.cu>
 *                    Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */

package uclv.gvsig.extsdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import org.apache.log4j.Logger;
import org.gvsig.raster.RasterLibrary;
import org.gvsig.raster.dataset.IBuffer;
import org.gvsig.raster.dataset.io.RasterDriverException;

import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.ma2.InvalidRangeException;
import ucar.ma2.Range;
import ucar.nc2.Attribute;
import ucar.nc2.Dimension;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.CoordinateAxis1D;
import ucar.nc2.dataset.CoordinateAxis1DTime;
import ucar.nc2.dataset.CoordinateSystem;
import ucar.nc2.dataset.NetcdfDataset;

import com.iver.cit.gvsig.fmap.drivers.db.utils.SingleDBConnectionManager;

/**
 * <p>
 * Clase para manejar el flujo de datos entre la aplicaci&oacute;n y el archivo
 * NetCDF de la capa Raster
 * </p>
 * 
 * @author millo
 * @version 1.0.2
 */
public class NetCDFController {

    private static Logger logger = Logger
            .getLogger(SingleDBConnectionManager.class.getName());

    /**
     * Ruta del archivo NetCDF
     */
    private String filename = null;
    /**
     * Objeto del archivo NetCDF abierto
     */
    private NetcdfFile file = null;
    /**
     * Dataset del archivo NetCDF abierto
     */
    private NetcdfDataset fileDataSet = null;
    /**
     * Sistemas de coordenadas disponibles en el archivo NetCDF
     */
    ArrayList<CoordinateSystem> datas = null;
    /**
     * &Iacute;ndice del sistema de coordenadas que se est&aacute; empleando
     */
    private int dataIdx = -1;
    /**
     * Sistema de coordenada que se est&aacute; empleando en la
     * renderizaci&oacute;n
     */
    private CoordinateSystem data = null;
    /**
     * Variables cuyos valores representan valores de longitud en un sistema de
     * coordenadas geogr&aacute;ficas
     */
    private CoordinateAxis1D lon = null;
    /**
     * Variables cuyos valores representan valores de latitud en un sistema de
     * coordenadas geogr&aacute;ficas
     */
    private CoordinateAxis1D lat = null;
    /**
     * Variables cuyos valores representan valores de tiempo u otra unidad de
     * medida variable, como puede ser la altura
     */
    private CoordinateAxis1DTime time = null;
    /**
     * Indica el &iacute;ndice de tiempo que se est&aacute; empleando en la
     * renderizaci&oacute;n
     */
    private int timeIdx = 0;
    /**
     * Variables cuyos valores son renderizados en el Raster
     */
    private Variable dataVar;
    /**
     * Buffer donde se cargan los datos de la variable del NetCDF
     */
    private Object[][] dData;
    /**
     * Valor de relleno definido en el NetCDF
     */
    private double missing;

    /**
     * <p>
     * Constructor de la clase
     * </p>
     * 
     * Inicializa el objeto con el archivo NetCDF agregado como una capa Raster
     * en el gvSIG.
     * 
     * @param filename
     *            ruta al archivo NetCDF
     * 
     * @throws IOException
     *             formato incorrecto del archivo NetCDF o formato no soportado
     * @throws InvalidRangeException
     */
    public NetCDFController(String filename) throws IOException,
            InvalidRangeException, RasterDriverException {
        this.filename = filename;
        loadDefaults();
    }

    /**
     * <p>
     * Carga la informaci&oacute;n por defecto de un archivo NetCDF
     * </p>
     * 
     * La informaci&oacute;n por defecto incluye las variables de coordenada
     * para la ubicaci&oacute;n geogr&aacute;fica, las variables que representan
     * unidades de medidas variables (como tiempo o altura), y la variable de
     * datos a renderizar.
     * 
     * @throws IOException
     *             Error de lectura del archivo NetCDF
     * @throws InvalidRangeException
     */
    private void loadDefaults() throws IOException, InvalidRangeException,
            RasterDriverException {
        logger.info("Inicializando informaci\u00F3n del archivo NetCDF: ("
                + filename + ")");

        // Abre el archivo NetCDF
        file = NetcdfFile.open(filename);
        fileDataSet = new NetcdfDataset(file);

        // Inicializa las estructuras para almacenar los ejes coordenados
        datas = new ArrayList<CoordinateSystem>();

        List<CoordinateSystem> coordSystems = fileDataSet
                .getCoordinateSystems();
        for (CoordinateSystem system : coordSystems) {
            if (system.isGeoReferencing()) {
                if (data == null) {
                    dataIdx = datas.size();
                    data = system;

                    // Busca la primera variable que se puede representar con el
                    // sistema de coordenadas
                    List<Variable> vars = file.getVariables();
                    for (Variable var : vars) {
                        if (system.isCoordinateSystemFor(var)) {
                            dataVar = var;
                            break;
                        }
                    }
                }
                datas.add(system);
            }
        }
        // Determina los ejes de coordenadas a partir del sistema de coordenadas
        loadDefaultCoordinates();

        // Determina el valor numérico empleado para relleno
        findMissind();

        // Lee la primera capa correspondiente al archivo NetCDF
        readData();
    }

    /**
     * Determina los ejes de coordenadas a partir del sistema de coordenada
     * selecionado
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     * @throws IOException
     *             formato de archivo NetCDF no soportado
     */
    private void loadDefaultCoordinates() throws RasterDriverException,
            IOException {
        if (data == null) {
            throw new RasterDriverException("Formato incorrecto o no soportado");
        }

        // Verifica si el sistema está georeferenciado por latitud/longitud
        if (data.isLatLon()) {
            lat = new CoordinateAxis1D(fileDataSet, data.getLatAxis());
            lon = new CoordinateAxis1D(fileDataSet, data.getLonAxis());

            // Verifica si el sistema está georeferenciado por X/Y
        } else if (data.isGeoXY()) {
            lat = new CoordinateAxis1D(fileDataSet, data.getYaxis());
            lon = new CoordinateAxis1D(fileDataSet, data.getXaxis());
        } else {
            throw new RasterDriverException("Formato incorrecto o no soportado");
        }

        // Verifica si el sistema coordenado tiene un eje de tiempo
        if (data.hasTimeAxis()) {
            time = CoordinateAxis1DTime.factory(fileDataSet, data.getTaxis(),
                    new Formatter());
            timeIdx = 0;
        } else {
            time = null;
            timeIdx = -1;
        }
    }

    /**
     * <p>
     * Devuelve el tipo de dato asociado a la variable renderizada
     * </p>
     * 
     * El tipo de dato depende del tipo de dato del cual est&aacute; definida la
     * variable a redenrizar. Los tipos de datos num&eacute;ricos admitidos por
     * el formato NetDCF son:
     * <ul>
     * <li>DataType.BYTE</li>
     * <li>DataType.CHAR</li>
     * <li>DataType.DOUBLE</li>
     * <li>DataType.FLOAT</li>
     * <li>DataType.INT</li>
     * <li>DataType.LONG</li>
     * <li>DataType.SHORT</li>
     * </ul>
     * A cada uno de estos tipos de datos se le hace corresponder un tipo de
     * dato en el gvSIG. Los tipos de datos admitidos por el gvSIG son:
     * <ul>
     * <li>IBuffer.TYPE_BYTE</li>
     * <li>IBuffer.TYPE_DOUBLE</li>
     * <li>IBuffer.TYPE_FLOAT</li>
     * <li>IBuffer.TYPE_INT</li>
     * <li>IBuffer.TYPE_SHORT</li>
     * </ul>
     * Por defecto cuando no se ha podido identificar la variable a renderizar
     * se asume como tipo de datos IBuffer.TYPE_DOUBLE
     * 
     * @return tipo de dato de la variable renderizada<br/>
     * 
     * @see ucar.ma2.DataType
     * @see org.gvsig.raster.dataset.IBuffer
     */
    public int getDataType() {
        // No se ha identificado la variable a renderizar
        if (dataVar == null)
            return IBuffer.TYPE_DOUBLE;

        // Tipo de dato DOUBLE
        if (dataVar.getDataType() == DataType.DOUBLE)
            return IBuffer.TYPE_DOUBLE;

        // Tipo de dato FLOAT
        if (dataVar.getDataType() == DataType.FLOAT)
            return IBuffer.TYPE_FLOAT;

        // Tipo de dato INT, los datos LONG se trabajan como INT, gvSIG no tiene
        // datos LONG
        if (dataVar.getDataType() == DataType.INT
                || dataVar.getDataType() == DataType.LONG)
            return IBuffer.TYPE_INT;

        // Tipo de dato SHORT
        if (dataVar.getDataType() == DataType.SHORT)
            return IBuffer.TYPE_SHORT;

        // Tipo de dato BYTE
        return IBuffer.TYPE_BYTE;
    }

    /**
     * Devuelve la cantidad de puntos horizontales que contiene el archivo
     * NetCDF de la variable a representar
     * 
     * @return cantidad de puntos
     */
    public int getWidth() {
        if (lon == null)
            return 0;
        // Siempre es una variable unidimensional
        return lon.getShape(0);
    }

    /**
     * Devuelve la cantidad de puntos verticales que contiene el archivo NetCDF
     * de la variable a representar
     * 
     * @return cantidad de puntos
     */
    public int getHeight() {
        if (lat == null)
            return 0;
        // Siempre es una variable unidimensional
        return lat.getShape(0);
    }

    /**
     * <p>
     * Cierra el archivo NetCDF
     * </p>
     * 
     * Cierra el archivo NetCDF abierto, y libera la memoria ocupada.
     * 
     * @throws IOException
     *             error de entrada/salida al cerrar el archivo
     */
    public void close() throws IOException {
        logger.info("Inicializando informaci\u00F3n del archivo NetCDF: ("
                + filename + ")");
        // Libera la memoria
        filename = null;
        file = null;
        fileDataSet = null;
        datas = null;
        data = null;
        lon = null;
        lat = null;
        time = null;
        dataVar = null;

        // Cierra el archivo
        if (file != null)
            file.close();
    }

    /**
     * <p>
     * Busca el valor de relleno de la variable de datos
     * </p>
     * 
     * Primero busca el atributo <i>_FillValue</i> de la variables. Si este no
     * se ha podido encontrar entonces busca el atributo <i>missing_value</i>,
     * si no, toma el valor por defecto del gvSIG
     */
    private void findMissind() {
        Attribute attr = dataVar.findAttributeIgnoreCase("_FillValue");
        if (attr != null) {
            missing = attr.getNumericValue().doubleValue();
        } else {
            attr = dataVar.findAttributeIgnoreCase("missing_value");
            if (attr != null) {
                missing = attr.getNumericValue().doubleValue();
            } else {
                missing = RasterLibrary.defaultNoDataValue;
            }
        }
    }

    /**
     * <p>
     * Devuelve el valor de relleno de la variable de datos
     * </p>
     * 
     * Primero busca el atributo <i>_FillValue</i> de la variables. Si este no
     * se ha podido encontrar entonces busca el atributo <i>missing_value</i>,
     * si no, toma el valor por defecto del gvSIG
     * 
     * @return valor de relleno
     */
    public double getMissing() {
        return missing;
    }

    /**
     * <p>
     * Devuelve la informaci&oacute;n obtenida de los atributos del archivo
     * NetCDF
     * </p>
     * .
     * 
     * @return informaci&oacute;n de metadatos
     */
    public String[] getFileMetadata() {
        if (fileDataSet == null)
            return new String[0];

        // Crea la lista de metadatos
        ArrayList<String> metadata = new ArrayList<String>();

        // Toma todos los atributos del archivo NetCDF
        List<Attribute> attrs = fileDataSet.getGlobalAttributes();
        // Recorre todos los atributos del archivo
        for (Attribute attr : attrs) {
            // Nombre del atributo
            StringBuilder sb = new StringBuilder();
            sb.append(attr.getName());
            sb.append("=");

            // El valor del atributo es de tipo cadena
            if (attr.isString()) {
                sb.append(attr.getStringValue(0));
                // Verifica si el valor del atributo es un arreglo
                for (int i = 0; i < attr.getLength(); ++i) {
                    sb.append(",");
                    sb.append(attr.getStringValue(i));
                }

                // El valor del atributo es de tipo numérico
            } else {
                sb.append(attr.getNumericValue(0).doubleValue());
                // Verifica si el valor del atributo es un arreglo
                for (int i = 0; i < attr.getLength(); ++i) {
                    sb.append(",");
                    sb.append(attr.getNumericValue(i).doubleValue());
                }
            }
            metadata.add(sb.toString());
        }

        return metadata.toArray(new String[0]);
    }

    /**
     * Inicializa el b&uacute;ffer para almacenar los datos en dependencia del
     * tipo de dato de la variable
     */
    private void initDataType() {
        int type = getDataType();
        switch (type) {
            case IBuffer.TYPE_DOUBLE:
                dData = new Double[getHeight()][getWidth()];
                break;
            case IBuffer.TYPE_FLOAT:
                dData = new Float[getHeight()][getWidth()];
                break;
            case IBuffer.TYPE_INT:
                dData = new Integer[getHeight()][getWidth()];
                break;
            case IBuffer.TYPE_SHORT:
                dData = new Short[getHeight()][getWidth()];
                break;
            default:
                dData = new Byte[getHeight()][getWidth()];
        }
    }

    /**
     * Lee el b&uacute;ffer de datos de la primera capa de la variable
     * renderizada del archivo NetCDF
     * 
     * @throws IOException
     *             error de entrada/salida del archivo NetCDF
     * @throws InvalidRangeException
     *             formato de archivo NetCDF no soportado
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public void readData() throws IOException, InvalidRangeException,
            RasterDriverException {
        // Inicializa la matriz de datos
        initDataType();

        // Toma los rangos de las variables, de solo lectura
        List<Range> ranges = dataVar.getRanges();

        // Busca el índice de dimensión que le corresponde a la latitud, la
        // longitud y a la variable de tiempo
        int timeDimIdx = -1;
        int latDimIdx = -1;
        int lonDimIdx = -1;
        int itr = 0;
        for (Dimension dim : dataVar.getDimensions()) {
            if (time != null && dim.compareTo(time.getDimension(0)) == 0) {
                timeDimIdx = itr;
            } else if (dim.compareTo(lat.getDimension(0)) == 0) {
                latDimIdx = itr;
            } else if (dim.compareTo(lon.getDimension(0)) == 0) {
                lonDimIdx = itr;
            }
            itr++;
        }

        // Verifica que el formato del archivo sea correcto
        // La variable debe tener una dimensión de longitud y una de latitud
        if (latDimIdx == -1 || lonDimIdx == -1)
            throw new RasterDriverException("Formato incorrecto o no soportado");

        // Crea una estructura con los rangos leidos
        ArrayList<Range> newRanges = new ArrayList<Range>();
        newRanges.addAll(ranges);

        // Selecciona los rango que se van a tomar
        if (timeDimIdx != -1)
            newRanges.set(timeDimIdx, new Range(timeIdx, timeIdx));

        // Lee los datos para cada instante de tiempo
        Array arr = dataVar.read(newRanges);
        // Toma el índice del arreglo de datos
        Index idx = arr.getIndex();

        int maxHeight = getHeight();
        int maxWidth = getWidth();
        // Recorre todas las latitudes
        for (int lat = 0; lat < maxHeight; ++lat) {
            // Establece el valor para la latitud
            idx.setDim(latDimIdx, lat);
            for (int lon = 0; lon < maxWidth; ++lon) {
                // Establece el valor para la longitud
                idx.setDim(lonDimIdx, lon);

                // Lee el valor correspondiente teniendo en cuenta el tipo
                // de dato
                dData[lat][lon] = readData(arr, idx);
            }
        }
    }

    /**
     * <p>
     * Lee un valor del archivo NetCDF seg&uacute;n el tipo de dato
     * </p>
     * 
     * @param arr
     *            Array del NetCDF desde donde se leen los datos
     * @param idx
     *            &Iacute;ndice del Array que se debe leer
     * 
     * @return el dato leido
     */
    private Object readData(Array arr, Index idx) {
        int type = getDataType();
        switch (type) {
            case IBuffer.TYPE_DOUBLE:
                return arr.getDouble(idx);
            case IBuffer.TYPE_FLOAT:
                return arr.getFloat(idx);
            case IBuffer.TYPE_INT:
                return arr.getInt(idx);
            case IBuffer.TYPE_SHORT:
                return arr.getShort(idx);
            default:
                return arr.getByte(idx);
        }
    }

    /**
     * Devuelve el b&uacute;ffer de datos como una matriz de objetos
     * 
     * @return b&uacute;ffer de datos
     */
    public Object[][] getFullData() {
        return dData;
    }

    /**
     * <p>
     * Devuelve el valor correspondiente a un punto dentro del conjunto de datos
     * </p>
     * 
     * @param line
     *            l&iacute;nea o fila dentro de la variable de datos
     * @param col
     *            columna dentro de la variable de datos
     * 
     * @return valor correspondiente a la posici&oacute;n fila x columna
     */
    public Object getValue(int line, int col) {
        return dData[line][col];
    }

    /**
     * <p>
     * Devuelve el valor correspondiente a un punto dentro del conjunto de datos
     * como un tipo de datos byte
     * </p>
     * 
     * @param line
     *            l&iacute;nea o fila dentro de la variable de datos
     * @param col
     *            columna dentro de la variable de datos
     * 
     * @return valor correspondiente a la posici&oacute;n fila x columna
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public byte getValueByte(int line, int col) throws RasterDriverException {
        try {
            return (Byte) dData[line][col];
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * <p>
     * Devuelve el valor correspondiente a un punto dentro del conjunto de datos
     * como un tipo de datos short
     * </p>
     * 
     * @param line
     *            l&iacute;nea o fila dentro de la variable de datos
     * @param col
     *            columna dentro de la variable de datos
     * 
     * @return valor correspondiente a la posici&oacute;n fila x columna
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public short getValueShort(int line, int col) throws RasterDriverException {
        try {
            return (Short) dData[line][col];
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * <p>
     * Devuelve el valor correspondiente a un punto dentro del conjunto de datos
     * como un tipo de datos int
     * </p>
     * 
     * @param line
     *            l&iacute;nea o fila dentro de la variable de datos
     * @param col
     *            columna dentro de la variable de datos
     * 
     * @return valor correspondiente a la posici&oacute;n fila x columna
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public int getValueInteger(int line, int col) throws RasterDriverException {
        try {
            return (Integer) dData[line][col];
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * <p>
     * Devuelve el valor correspondiente a un punto dentro del conjunto de datos
     * como un tipo de datos float
     * </p>
     * 
     * @param line
     *            l&iacute;nea o fila dentro de la variable de datos
     * @param col
     *            columna dentro de la variable de datos
     * 
     * @return valor correspondiente a la posici&oacute;n fila x columna
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public float getValueFloat(int line, int col) throws RasterDriverException {
        try {
            return (Float) dData[line][col];
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * <p>
     * Devuelve el valor correspondiente a un punto dentro del conjunto de datos
     * como un tipo de datos double
     * </p>
     * 
     * @param line
     *            l&iacute;nea o fila dentro de la variable de datos
     * @param col
     *            columna dentro de la variable de datos
     * 
     * @return valor correspondiente a la posici&oacute;n fila x columna
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public double getValueDouble(int line, int col)
            throws RasterDriverException {
        try {
            return (Double) dData[line][col];
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * <p>
     * Lee una l&iacute;nea de datos de tipo <i>double</i> dentro del conjunto
     * de datos del archivo NetCDF
     * </p>
     * 
     * @param line
     *            l&iacute;nea a leer
     * @param buf
     *            b&uacute;fer para guardar la l&iacute;nea leida
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public void readLine(int line, double[] buf) throws RasterDriverException {
        try {
            Object[] tline = dData[line];
            for (int lon = 0; lon < getWidth(); ++lon) {
                double val = (Double) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * <p>
     * Lee una l&iacute;nea de datos de tipo <i>float</i> dentro del conjunto de
     * datos del archivo NetCDF
     * </p>
     * 
     * @param line
     *            l&iacute;nea a leer
     * @param buf
     *            b&uacute;fer para guardar la l&iacute;nea leida
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public void readLine(int line, float[] buf) throws RasterDriverException {
        try {
            Object[] tline = dData[line];
            for (int lon = 0; lon < getWidth(); ++lon) {
                float val = (Float) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * <p>
     * Lee una l&iacute;nea de datos de tipo <i>int</i> dentro del conjunto de
     * datos del archivo NetCDF
     * </p>
     * 
     * @param line
     *            l&iacute;nea a leer
     * @param buf
     *            b&uacute;fer para guardar la l&iacute;nea leida
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public void readLine(int line, int[] buf) throws RasterDriverException {
        try {
            Object[] tline = dData[line];
            for (int lon = 0; lon < getWidth(); ++lon) {
                int val = (Integer) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * <p>
     * Lee una l&iacute;nea de datos de tipo <i>short</i> dentro del conjunto de
     * datos del archivo NetCDF
     * </p>
     * 
     * @param line
     *            l&iacute;nea a leer
     * @param buf
     *            b&uacute;fer para guardar la l&iacute;nea leida
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public void readLine(int line, short[] buf) throws RasterDriverException {
        try {
            Object[] tline = dData[line];
            for (int lon = 0; lon < getWidth(); ++lon) {
                short val = (Short) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * <p>
     * Lee una l&iacute;nea de datos de tipo <i>byte</i> dentro del conjunto de
     * datos del archivo NetCDF
     * </p>
     * 
     * @param line
     *            l&iacute;nea a leer
     * @param buf
     *            b&uacute;fer para guardar la l&iacute;nea leida
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public void readLine(int line, byte[] buf) throws RasterDriverException {
        try {
            Object[] tline = dData[line];
            for (int lon = 0; lon < getWidth(); ++lon) {
                byte val = (Byte) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            throw new RasterDriverException(
                    "Formato incorrecto o no soportado", e);
        }
    }

    /**
     * Devuelve todos los sistemas de coordenadas contenidos en el archiv NetCDF
     * 
     * @return sistemas de coordenadas
     */
    public CoordinateSystem[] getCoordinateSystems() {
        return datas.toArray(new CoordinateSystem[0]);
    }

    /**
     * Devuelve el &iacute;ndice del sistema de coordenada que se est&aacute;
     * empleando
     * 
     * @return &iacute;ndice del sistema de coordenada
     */
    public int getCoordinateSystemIndex() {
        return dataIdx;
    }

    /**
     * Establece como activo un sistema de coordenada
     * 
     * @param dataIdx
     *            &iacute;ndice del sistema de coordenada seleccionado
     * 
     * @throws IOException
     */
    public void setCoordinateSystem(int dataIdx) throws IOException,
            RasterDriverException {
        // Establece el sistema de coordenadas seleccionado
        this.dataIdx = dataIdx;
        data = datas.get(dataIdx);

        // Determina los ejes de coordenadas a partir del sistema de coordenadas
        loadDefaultCoordinates();
    }

}
