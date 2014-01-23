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
     * &Iacute; correspondiente a la variable latitud en los rangos
     * correspondientes a la variable a renderizar
     */
    private int idxLat;
    /**
     * &Iacute; correspondiente a la variable longitud en los rangos
     * correspondientes a la variable a renderizar
     */
    private int idxLon;
    /**
     * &Iacute; correspondiente a la variable tomada como par&aacute;metro
     * (tiempo, altura...) en los rangos correspondientes a la variable a
     * renderizar
     */
    private int idxTime;
    /**
     * Valor m&iacute;nimo de la variable de datos renderizada
     */
    private Object min;
    /**
     * Valor m&aacute;ximo de la variable de datos renderizada
     */
    private Object max;
    
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

        // Calcula los índices de cada uno de los ejes de coordenadas
        findVariableIndex();

        // Calcula los valores mínimos y máximos de la variable
        //calculaMinMax();

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
     * <p>
     * Calcula los &iacute;ndices correspondientes a cada uno de los ejes de
     * coordenadas empleados para la renderización de la variable
     * </p>
     * 
     * Este &iacute;ndice es calculado teniendo en cuenta el orden en que
     * aparece cada una de las dimensiones correspondientes a cada eje en las
     * dimensiones asociadas a la variable que se renderiza
     */
    private void findVariableIndex() {
        // Busca el índice de dimensión que le corresponde a la latitud, la
        // longitud y a la variable de tiempo
        idxLat = -1;
        idxLon = -1;
        idxTime = -1;
        int itr = 0;
        for (Dimension dim : dataVar.getDimensions()) {
            if (time != null && dim.compareTo(time.getDimension(0)) == 0) {
                idxTime = itr;
            } else if (dim.compareTo(lat.getDimension(0)) == 0) {
                idxLat = itr;
            } else if (dim.compareTo(lon.getDimension(0)) == 0) {
                idxLon = itr;
            }
            itr++;
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

        

        // Verifica que el formato del archivo sea correcto
        // La variable debe tener una dimensión de longitud y una de latitud
        if (idxLat == -1 || idxLon == -1)
            throw new RasterDriverException("Formato incorrecto o no soportado");

        // Crea una estructura con los rangos leidos
        ArrayList<Range> newRanges = new ArrayList<Range>();
        newRanges.addAll(ranges);

        // Selecciona los rango que se van a tomar
        if (idxTime != -1)
            newRanges.set(idxTime, new Range(timeIdx, timeIdx));

        // Lee los datos para cada instante de tiempo
        Array arr = dataVar.read(newRanges);
        // Toma el índice del arreglo de datos
        Index idx = arr.getIndex();

        int maxHeight = getHeight();
        int maxWidth = getWidth();
        // Recorre todas las latitudes
        for (int lat = 0; lat < maxHeight; ++lat) {
            // Establece el valor para la latitud
            idx.setDim(idxLat, lat);
            for (int lon = 0; lon < maxWidth; ++lon) {
                // Establece el valor para la longitud
                idx.setDim(idxLon, lon);

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
     *             error de entrada/salida del archivo NetCDF
     */
    public void setCoordinateSystem(int dataIdx) throws IOException,
            RasterDriverException {
        // Establece el sistema de coordenadas seleccionado
        this.dataIdx = dataIdx;
        data = datas.get(dataIdx);

        // Determina los ejes de coordenadas a partir del sistema de coordenadas
        loadDefaultCoordinates();
    }

    /**
     * Devuelve una lista de variables que pueden ser representadas en un
     * sistema de coordenadas
     * 
     * @param system
     *            sistema de coordenadas
     * 
     * @return lista de variables
     */
    public Variable[] getVariablesForCoordinateSystem(CoordinateSystem system) {
        ArrayList<Variable> vars = new ArrayList<Variable>();
        // Toma todas las variables definidas en el archivo NetCDF
        List<Variable> allVars = fileDataSet.getVariables();

        // Recorre todas las variables
        for (Variable var : allVars) {
            // Verifica si la variable se puede representar con el sistema de
            // coordenadas
            if (system.isCoordinateSystemFor(var)) {
                vars.add(var);
            }
        }
        return vars.toArray(new Variable[0]);
    }

    /**
     * Devuelve el eje de coordenadas de latitud asociado a un sistema de
     * coordenadas
     * 
     * @param system
     *            sistema de coordenadas
     * 
     * @return eje de coordenadas
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public CoordinateAxis1D getLatitudeForCoordinateSystem(
            CoordinateSystem system) throws RasterDriverException {
        // Verifica si el sistema está georeferenciado por latitud/longitud
        if (system.isLatLon()) {
            return new CoordinateAxis1D(fileDataSet, system.getLatAxis());
            // Verifica si el sistema está georeferenciado por X/Y
        } else if (system.isGeoXY()) {
            return new CoordinateAxis1D(fileDataSet, system.getYaxis());
        } else {
            throw new RasterDriverException("Formato incorrecto o no soportado");
        }
    }

    /**
     * Devuelve el eje de coordenadas de longitud asociado a un sistema de
     * coordenadas
     * 
     * @param system
     *            sistema de coordenadas
     * 
     * @return eje de coordenadas
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public CoordinateAxis1D getLongitudeForCoordinateSystem(
            CoordinateSystem system) throws RasterDriverException {
        // Verifica si el sistema está georeferenciado por latitud/longitud
        if (system.isLatLon()) {
            return new CoordinateAxis1D(fileDataSet, system.getLonAxis());
            // Verifica si el sistema está georeferenciado por X/Y
        } else if (system.isGeoXY()) {
            return new CoordinateAxis1D(fileDataSet, system.getXaxis());
        } else {
            throw new RasterDriverException("Formato incorrecto o no soportado");
        }
    }

    /**
     * Devuelve el eje de coordenadas correspondiente a una variable tomada como
     * par&aacute;metro variable, asociado a un sistema de coordenadas
     * 
     * @param system
     *            sistema de coordenadas
     * 
     * @return eje de coordenadas
     * 
     * @throws IOException
     *             error de entrada/salida del archivo NetCDF
     */
    public CoordinateAxis1DTime getParameterForCoordinateSystem(
            CoordinateSystem system) throws IOException {
        // Verifica si el sistema coordenado tiene un eje de tiempo
        if (system.hasTimeAxis()) {
            return CoordinateAxis1DTime.factory(fileDataSet, data.getTaxis(),
                    new Formatter());
        }
        return null;
    }

    /**
     * Devuelve la variable que se est&aacute; renderizando en la capa Raster
     * 
     * @return variable renderizada
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public Variable getSelectedVariable() throws RasterDriverException {
        if (dataVar == null)
            throw new RasterDriverException(
                    "Formato de archivo NetCDF no soportado");
        return dataVar;
    }

    /**
     * Devuelve la variable correpondiente al eje de coordenadas de latitud
     * 
     * @return variable latitud
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public CoordinateAxis1D getSelectedLat() throws RasterDriverException {
        if (lat == null)
            throw new RasterDriverException(
                    "Formato de archivo NetCDF no soportado");
        return lat;
    }

    /**
     * Devuelve la variable correpondiente al eje de coordenadas de longitud
     * 
     * @return variable longitud
     * 
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public CoordinateAxis1D getSelectedLon() throws RasterDriverException {
        if (lon == null)
            throw new RasterDriverException(
                    "Formato de archivo NetCDF no soportado");
        return lon;
    }

    /**
     * Establece la variable que se renderizar&aacute; en la capa Raster
     * 
     * @param dataVar
     *            variable a renderizar
     * 
     * @throws IOException
     *             error de entrada/salida del archivo NetCDF
     * @throws InvalidRangeException
     *             formato de archivo NetCDF no soportado
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public void setSelectedVariable(Variable dataVar) throws IOException,
            InvalidRangeException, RasterDriverException {
        this.dataVar = dataVar;
        // Determina el valor numérico empleado para relleno
        findMissind();
        // Calcula los índices de cada uno de los ejes de coordenadas
        findVariableIndex();
        // Lee la primera capa correspondiente al archivo NetCDF
        readData();
    }

    /**
     * Inicializa los valores m&iacute;nimos y m&aacute;ximos de la variable a
     * renderizar en dependencia del tipo de dato
     */
    private void initMinMax() {
        int type = getDataType();
        switch (type) {
        // Tipo de dato double
            case IBuffer.TYPE_DOUBLE:
                min = Double.MAX_VALUE;
                max = Double.MIN_VALUE;
                break;
            // Tipo de dato float
            case IBuffer.TYPE_FLOAT:
                min = Float.MAX_VALUE;
                max = Float.MIN_VALUE;
                break;
            // Tipo de dato int
            case IBuffer.TYPE_INT:
                min = Integer.MAX_VALUE;
                max = Integer.MIN_VALUE;
                break;
            // Tipo de dato short
            case IBuffer.TYPE_SHORT:
                min = Short.MAX_VALUE;
                max = Short.MIN_VALUE;
                break;
            // Tipo de dato byte
            default:
                min = Byte.MAX_VALUE;
                max = Byte.MIN_VALUE;
        }
    }
    
    /**
     * Devuelve si un valor pasado como par&aacute;metro es igual al valor de
     * relleno empleado en la renderizaci&oacute;n
     * 
     * @param val
     *            valor a verificar
     * 
     * @return <b>true</b> si el valor indicado es igual al valor de relleno<br />
     *         <b>false</b> en cualquier otro caso
     */
    private boolean isDataFillValue(Object val) {
        int type = getDataType();
        switch (type) {
        // Tipo de dato double
            case IBuffer.TYPE_DOUBLE:
                return (getMissing() == ((Double) val));
                // Tipo de dato float
            case IBuffer.TYPE_FLOAT:
                return (getMissing() == ((Float) val));
                // Tipo de dato int
            case IBuffer.TYPE_INT:
                return (getMissing() == ((Integer) val));
                // Tipo de dato short
            case IBuffer.TYPE_SHORT:
                return (getMissing() == ((Short) val));
                // Tipo de dato byte
            default:
                return (getMissing() == ((Byte) val));
        }
    }

    /**
     * Compara un valor con el valor m&iacute;nimo de la variable renderizada,
     * si este es menor, actualiza el valor m&iacute;nimo
     * 
     * @param val
     *            valor a verificar
     */
    private void compareDataMin(Object val) {
        int type = getDataType();
        switch (type) {
        // Tipo de dato double
            case IBuffer.TYPE_DOUBLE:
                if (((Double) min) > ((Double) val))
                    min = val;
                break;
            // Tipo de dato float
            case IBuffer.TYPE_FLOAT:
                if (((Float) min) > ((Float) val))
                    min = val;
                break;
            // Tipo de dato int
            case IBuffer.TYPE_INT:
                if (((Integer) min) > ((Integer) val))
                    min = val;
                break;
            // Tipo de dato short
            case IBuffer.TYPE_SHORT:
                if (((Short) min) > ((Short) val))
                    min = val;
                break;
            // Tipo de dato byte
            default:
                if (((Byte) min) > ((Byte) val))
                    min = val;
        }
    }

    /**
     * Compara un valor con el valor m&aacute;ximo de la variable renderizada,
     * si este es menor, actualiza el valor m&aacute;ximo
     * 
     * @param val
     *            valor a verificar
     */
    private void compareDataMax(Object val){
        int type = getDataType();
        switch (type) {
        // Tipo de dato double
            case IBuffer.TYPE_DOUBLE:
                if(((Double)max)<((Double)val))
                    max = val;
                break;
            // Tipo de dato float
            case IBuffer.TYPE_FLOAT:
                if(((Float)max)<((Float)val))
                    max = val;
                break;
            // Tipo de dato int
            case IBuffer.TYPE_INT:
                if(((Integer)max)<((Integer)val))
                    max = val;
                break;
            // Tipo de dato short
            case IBuffer.TYPE_SHORT:
                if(((Short)max)<((Short)val))
                    max = val;
                break;
            // Tipo de dato byte
            default:
                if(((Byte)max)<((Byte)val))
                    max = val;
        }
    }

    /**
     * Lee una capa del archiv NetCDF y actualiza los valores m&iacute;nimos y
     * m&aacute;ximos de la variable a renderizar
     * 
     * @param ranges
     *            rangos de datos que se leer&aacute;n del archiv NetCDF
     * @param latMax
     *            cantidad m&aacute;xima de valores de latitud
     * @param lonMax
     *            cantidad m&aacute;xima de valores de longitud
     * 
     * @throws IOException
     *             error de entrada salida
     * @throws InvalidRangeException
     *             formato de archivo NetCDF no soportado
     */
    private void readData(ArrayList<Range> ranges, int latMax, int lonMax)
            throws IOException, InvalidRangeException {
        // Lee los valores desde el archivo NetCDF
        Array arr = dataVar.read(ranges);
        Index idx = arr.getIndex();

        // Si existe la dimensión de tiempo establece el índice como 0
        if (idxTime != -1)
            idx.setDim(idxTime, 0);

        // Recorre la dimensión correspondiente a la latitud
        for (int i = 0; i < latMax; ++i) {
            idx.setDim(idxLat, i);
            // Recorre la dimensión correspondiente a la longitud
            for (int j = 0; j < lonMax; ++j) {
                idx.setDim(idxLon, j);
                // Lee el valor correspondiente
                Object val = readData(arr, idx);
                // Verifica si no es un valor de relleno y compara con el mínimo
                // y el máximo
                if (!isDataFillValue(val)) {
                    compareDataMin(val);
                    compareDataMax(val);
                }
            }
        }
    }

    /**
     * Calcula los valores m&iacute;nimos y m&aacute;ximos de la variable a
     * renderizar
     * 
     * @throws IOException
     *             error de entrada salida
     * @throws InvalidRangeException
     *             formato de archivo NetCDF no soportado
     */
    public void calculaMinMax() throws IOException, InvalidRangeException {
        // Inicializa los valores mínimos y máximos
        initMinMax();
        // Inicializa los rangos de la variable renderizada para leer los
        // valores desde el archivo NetCDF
        List<Range> roRanges = dataVar.getRanges();
        ArrayList<Range> ranges = new ArrayList<Range>();
        ranges.addAll(roRanges);
        // Calcula la cantidad de valores de latitud y longitud
        int lonMax = lon.getDimension(0).getLength();
        int latMax = lat.getDimension(0).getLength();

        // Verifica si existe un parámetro variable (tiempo, altura...)
        if (idxTime != -1) {
            // Toma la cantidad máxima de valores del parámetro
            int tmax = time.getDimension(0).getLength();
            // Recorre cada uno de los valores del parámetro
            for (int i = 0; i < tmax; ++i) {
                // Lee los datos de la capa correspondiente al variar el
                // parámetro
                ranges.set(idxTime, new Range(i, i));
                readData(ranges, latMax, lonMax);
            }
            // No existe parámetro variable, por lo que se lee una sola capa del
            // archivo NetCDF
        } else {
            readData(ranges, latMax, lonMax);
        }
    }

    /**
     * Devuelve el valor m&iacute;nimo de la variable renderizada
     * 
     * @return valor m&iacute;nimo
     */
    public Object getMinValue() {
        return min;
    }

    /**
     * Devuelve el valor m&aacute;ximo de la variable renderizada
     * 
     * @return valor m&aacute;ximo
     */
    public Object getMaxValue() {
        return max;
    }

    /**
     * Carga un fragmento de la capa del NetCDF en el b&uuacute;ffer de la capa
     * Raster, teniendo en cuenta el tipo de dato de la capa NetCDF
     * 
     * @param rasterBuf
     *            b&uacute;ffer de la capa raster
     * @param initRow
     *            fila inicial de la capa NetCDF
     * @param initCol
     *            columna inicial de la capa NetCDF
     * @param incRow
     *            factor de incremento de las filas en la capa NetCDF
     * @param incCol
     *            factor de incremento de las columnas en la capa NetCDF
     * 
     * @throws IOException
     *             error de entrada/salida de los datos
     * @throws InterruptedException
     *             error en el procesamiento concurrente
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    public void readDataToBuffer(IBuffer rasterBuf, int initRow, int initCol,
            double incRow, double incCol) throws IOException,
            InterruptedException, RasterDriverException {
        switch (getDataType()) {
            case IBuffer.TYPE_DOUBLE:
                readDouble2Buffer(rasterBuf, initRow, initCol, incRow, incCol);
                break;

            case IBuffer.TYPE_FLOAT:
                readFloat2Buffer(rasterBuf, initRow, initCol, incRow, incCol);
                break;
            case IBuffer.TYPE_INT:
                readInteger2Buffer(rasterBuf, initRow, initCol, incRow, incCol);
                break;
            case IBuffer.TYPE_SHORT:
                readShort2Buffer(rasterBuf, initRow, initCol, incRow, incCol);
                break;

            default:
                readByte2Buffer(rasterBuf, initRow, initCol, incRow, incCol);
        }
    }

    /**
     * Carga un fragmento de la capa del NetCDF en el b&uuacute;ffer de la capa
     * Raster.<br/>
     * Los datos se leen de tipo double
     * 
     * @param rasterBuf
     *            b&uacute;ffer de la capa raster
     * @param initRow
     *            fila inicial de la capa NetCDF
     * @param initCol
     *            columna inicial de la capa NetCDF
     * @param incRow
     *            factor de incremento de las filas en la capa NetCDF
     * @param incCol
     *            factor de incremento de las columnas en la capa NetCDF
     * 
     * @throws IOException
     *             error de entrada/salida de los datos
     * @throws InterruptedException
     *             error en el procesamiento concurrente
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    private void readDouble2Buffer(IBuffer rasterBuf, int initRow, int initCol,
            double incRow, double incCol) throws IOException,
            InterruptedException, RasterDriverException {
        for (int line = 0; line < rasterBuf.getHeight(); line++) {
            for (int col = 0; col < rasterBuf.getWidth(); col++) {
                rasterBuf.setElem(
                        line,
                        col,
                        0,
                        getValueDouble(initRow + (int) (line * incRow), initCol
                                + (int) (col * incCol)));
            }
        }
    }

    /**
     * Carga un fragmento de la capa del NetCDF en el b&uuacute;ffer de la capa
     * Raster.<br/>
     * Los datos se leen de tipo float
     * 
     * @param rasterBuf
     *            b&uacute;ffer de la capa raster
     * @param initRow
     *            fila inicial de la capa NetCDF
     * @param initCol
     *            columna inicial de la capa NetCDF
     * @param incRow
     *            factor de incremento de las filas en la capa NetCDF
     * @param incCol
     *            factor de incremento de las columnas en la capa NetCDF
     * 
     * @throws IOException
     *             error de entrada/salida de los datos
     * @throws InterruptedException
     *             error en el procesamiento concurrente
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */

    private void readFloat2Buffer(IBuffer rasterBuf, int initRow, int initCol,
            double incRow, double incCol) throws IOException,
            InterruptedException, RasterDriverException {
        for (int line = 0; line < rasterBuf.getHeight(); line++) {
            for (int col = 0; col < rasterBuf.getWidth(); col++) {
                rasterBuf.setElem(
                        line,
                        col,
                        0,
                        getValueFloat(initRow + (int) (line * incRow), initCol
                                + (int) (col * incCol)));
            }
        }
    }

    /**
     * Carga un fragmento de la capa del NetCDF en el b&uuacute;ffer de la capa
     * Raster.<br/>
     * Los datos se leen de tipo int
     * 
     * @param rasterBuf
     *            b&uacute;ffer de la capa raster
     * @param initRow
     *            fila inicial de la capa NetCDF
     * @param initCol
     *            columna inicial de la capa NetCDF
     * @param incRow
     *            factor de incremento de las filas en la capa NetCDF
     * @param incCol
     *            factor de incremento de las columnas en la capa NetCDF
     * 
     * @throws IOException
     *             error de entrada/salida de los datos
     * @throws InterruptedException
     *             error en el procesamiento concurrente
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */

    private void readInteger2Buffer(IBuffer rasterBuf, int initRow,
            int initCol, double incRow, double incCol) throws IOException,
            InterruptedException, RasterDriverException {
        for (int line = 0; line < rasterBuf.getHeight(); line++) {
            for (int col = 0; col < rasterBuf.getWidth(); col++) {
                rasterBuf.setElem(
                        line,
                        col,
                        0,
                        getValueInteger(initRow + (int) (line * incRow),
                                initCol + (int) (col * incCol)));
            }
        }
    }

    /**
     * Carga un fragmento de la capa del NetCDF en el b&uuacute;ffer de la capa
     * Raster.<br/>
     * Los datos se leen de tipo short
     * 
     * @param rasterBuf
     *            b&uacute;ffer de la capa raster
     * @param initRow
     *            fila inicial de la capa NetCDF
     * @param initCol
     *            columna inicial de la capa NetCDF
     * @param incRow
     *            factor de incremento de las filas en la capa NetCDF
     * @param incCol
     *            factor de incremento de las columnas en la capa NetCDF
     * 
     * @throws IOException
     *             error de entrada/salida de los datos
     * @throws InterruptedException
     *             error en el procesamiento concurrente
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    private void readShort2Buffer(IBuffer rasterBuf, int initRow, int initCol,
            double incRow, double incCol) throws IOException,
            InterruptedException, RasterDriverException {
        for (int line = 0; line < rasterBuf.getHeight(); line++) {
            for (int col = 0; col < rasterBuf.getWidth(); col++) {
                rasterBuf.setElem(
                        line,
                        col,
                        0,
                        getValueShort(initRow + (int) (line * incRow), initCol
                                + (int) (col * incCol)));
            }
        }
    }

    /**
     * Carga un fragmento de la capa del NetCDF en el b&uuacute;ffer de la capa
     * Raster.<br/>
     * Los datos se leen de tipo byte
     * 
     * @param rasterBuf
     *            b&uacute;ffer de la capa raster
     * @param initRow
     *            fila inicial de la capa NetCDF
     * @param initCol
     *            columna inicial de la capa NetCDF
     * @param incRow
     *            factor de incremento de las filas en la capa NetCDF
     * @param incCol
     *            factor de incremento de las columnas en la capa NetCDF
     * 
     * @throws IOException
     *             error de entrada/salida de los datos
     * @throws InterruptedException
     *             error en el procesamiento concurrente
     * @throws RasterDriverException
     *             formato de archivo NetCDF no soportado
     */
    private void readByte2Buffer(IBuffer rasterBuf, int initRow, int initCol,
            double incRow, double incCol) throws IOException,
            InterruptedException, RasterDriverException {
        for (int line = 0; line < rasterBuf.getHeight(); line++) {
            for (int col = 0; col < rasterBuf.getWidth(); col++) {
                rasterBuf.setElem(
                        line,
                        col,
                        0,
                        getValueByte(initRow + (int) (line * incRow), initCol
                                + (int) (col * incCol)));
            }
        }
    }
}
