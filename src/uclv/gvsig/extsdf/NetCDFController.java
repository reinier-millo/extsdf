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

import ucar.ma2.Array;
import ucar.ma2.DataType;
import ucar.ma2.Index;
import ucar.ma2.Range;
import ucar.nc2.Attribute;
import ucar.nc2.NetcdfFile;
import ucar.nc2.Variable;
import ucar.nc2.dataset.CoordinateAxis1D;
import ucar.nc2.dataset.CoordinateAxis1DTime;
import ucar.nc2.dataset.CoordinateSystem;
import ucar.nc2.dataset.NetcdfDataset;

import com.iver.cit.gvsig.fmap.drivers.db.utils.SingleDBConnectionManager;

/**
 * <p>Clase para manejar el flujo de datos entre la aplicación y el archivo NetCDF
 * de la capa Raster</p>
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
     * Índice del sistema de coordenadas que se est&aacute; empleando
     */
    private int dataIdx = -1;
    /**
     * Sistema de coordenada que se est&aacute; empleando en la
     * renderizaci&oacute;n
     */
    private CoordinateSystem data = null;

    /**
     * Variables cuyos valores representan representan valores de longitud en un
     * sistema de coordenadas geogr&aacute;ficas
     */
    private CoordinateAxis1D lon = null;
    /**
     * Variables cuyos valores representan representan valores de latitud en un
     * sistema de coordenadas geogr&aacute;ficas
     */
    private CoordinateAxis1D lat = null;
    /**
     * Variables cuyos valores representan representan valores de tiempo u otra
     * unidad de medida variable, como puede ser la altura
     */
    private CoordinateAxis1DTime time = null;
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
     */
    public NetCDFController(String filename) throws IOException {
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
     */
    private void loadDefaults() throws IOException {
        logger.info("Inicializando informaci\u00F3n del archivo NetCDF: ("
                + filename + ")");

        // Abre el archivo NetCDF
        file = NetcdfFile.open(filename);
        fileDataSet = new NetcdfDataset(file);

        // Inicializa las estructuras para almacenar los ejes coordenados
        datas = new ArrayList<CoordinateSystem>();
        dataVar = file.getVariables().get(3);

        List<CoordinateSystem> coordSystems = fileDataSet
                .getCoordinateSystems();
        for (CoordinateSystem system : coordSystems) {
            if (system.isGeoReferencing()) {
                if (data == null) {
                    if (system.isLatLon()) {
                        lat = new CoordinateAxis1D(fileDataSet,
                                system.getLatAxis());
                        lon = new CoordinateAxis1D(fileDataSet,
                                system.getLonAxis());
                    } else if (system.isGeoXY()) {
                        lat = new CoordinateAxis1D(fileDataSet,
                                system.getYaxis());
                        lon = new CoordinateAxis1D(fileDataSet,
                                system.getXaxis());
                    } else {
                        // TODO Ver que sucede a partir de este punto con nuevos
                        // NetCDF
                        throw new IOException(
                                "Formato incorrecto o no soportado");
                    }

                    // Verifica si el sistema coordenado tiene un eje de tiempo
                    if (system.hasTimeAxis()) {
                        // TODO Ver en que influye el formater
                        time = CoordinateAxis1DTime.factory(fileDataSet,
                                system.getLatAxis(), new Formatter());
                    }
                    dataIdx = datas.size();
                    data = system;
                }
                datas.add(system);
            }

        }
        findMissind();
        readData();
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
     * Devuelve la información obtenida de los atributos del archivo NetCDF
     * </p>
     * .
     * 
     * @return información de metadatos
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

    public void readData() {
        // Inicializa la matriz de datos
        dData = new Object[getHeight()][getWidth()];

        try {
            // Toma los rangos de las variables, de solo lectura
            List<Range> ranges = dataVar.getRanges();

            // Crea una estructura con los rangos leidos
            ArrayList<Range> newRanges = new ArrayList<Range>();
            newRanges.add(ranges.get(0));
            newRanges.add(ranges.get(1));
            newRanges.add(ranges.get(2));

            // Selecciona los rango que se van a tomar
            // TODO Tomar el rango de tiempo según el orden de las dimensiones
            newRanges.set(0, new Range(0, 0));

            // Lee los datos para cada instante de tiempo
            Array arr = dataVar.read(newRanges);
            // Toma el índice del arreglo de datos
            Index idx = arr.getIndex();

            // Recorre todas las latitudes
            // TODO Tomar las dimensiones según las variables de lon y lat
            for (int lat = 0; lat < 360; ++lat) {
                for (int lon = 0; lon < 720; ++lon) {
                    // TODO Tomar los índices según el orden de las dimensiones
                    idx.set(0, lat, lon); // tiempo, latitud, longitud

                    // Lee el valor correspondiente teniendo en cuenta el tipo
                    // de dato
                    dData[lat][lon] = readData(arr, idx);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     * Lee una l&iacute;nea de datos de tipo <i>double</i> dentro del conjunto
     * de datos del archivo NetCDF
     * </p>
     * 
     * @param line
     *            l&iacute;nea a leer
     * @param buf
     *            b&uacute;fer para guardar la l&iacute;nea leida
     */
    public void readLine(int line, double[] buf) {
        // TODO Chequear el tipo de datos de la variable
        try {
            Object[] tline = dData[line];
            // TODO Tomar las dimensiones según la variable de lat y lon
            for (int lon = 0; lon < 720; ++lon) {
                double val = (Double) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     */
    public void readLine(int line, float[] buf) {
        // TODO Chequear el tipo de datos de la variable
        try {
            Object[] tline = dData[line];
            // TODO Tomar las dimensiones según la variable de lat y lon
            for (int lon = 0; lon < 720; ++lon) {
                float val = (Float) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     */
    public void readLine(int line, int[] buf) {
        // TODO Chequear el tipo de datos de la variable
        try {
            Object[] tline = dData[line];
            // TODO Tomar las dimensiones según la variable de lat y lon
            for (int lon = 0; lon < 720; ++lon) {
                int val = (Integer) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     */
    public void readLine(int line, short[] buf) {
        // TODO Chequear el tipo de datos de la variable
        try {
            Object[] tline = dData[line];
            // TODO Tomar las dimensiones según la variable de lat y lon
            for (int lon = 0; lon < 720; ++lon) {
                short val = (Short) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            e.printStackTrace();
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
     */
    public void readLine(int line, byte[] buf) {
        // TODO Chequear el tipo de datos de la variable
        try {
            Object[] tline = dData[line];
            // TODO Tomar las dimensiones según la variable de lat y lon
            for (int lon = 0; lon < 720; ++lon) {
                byte val = (Byte) tline[lon];
                buf[lon] = val;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
