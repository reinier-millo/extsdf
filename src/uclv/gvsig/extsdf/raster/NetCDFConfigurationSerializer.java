/*
 * NetCDFConfigurationSerializer.java
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

package uclv.gvsig.extsdf.raster;

import java.io.IOException;

import org.gvsig.raster.dataset.io.rmf.ClassSerializer;
import org.gvsig.raster.dataset.io.rmf.ParsingException;
import org.gvsig.raster.util.extensionPoints.ExtensionPoint;


import uclv.gvsig.extsdf.NetCDFConfiguration;

/**
 * Clase empleada para guardar la configuración de la capa NetCDF en el archivo
 * RMF generado por el gvSIG
 * 
 * @author millo
 * 
 */
public class NetCDFConfigurationSerializer extends ClassSerializer{

    private final String MAIN_TAG = "NetCDFConfiguration";

    private NetCDFConfiguration config = null;

    /**
     * Registra el serializador en los puntos de extensi&oacute;n de Serializer
     */
    public static void register() {
        ExtensionPoint point = ExtensionPoint.getExtensionPoint("Serializer");
        point.register("NetCDFConfiguration",
                NetCDFConfigurationSerializer.class);
    }

    /**
     * Constructor para asignar la configuraci&oacute;n a serializar
     * 
     * @param config
     *            configuraci&oacute;n de la capa NetCDF a convertir en XML
     */
    public NetCDFConfigurationSerializer(NetCDFConfiguration config) {
        this.config = config;
    }

    /**
     * Constructor por defecto
     */
    public NetCDFConfigurationSerializer() {
    }

    /**
     * Lee la configuraci&oacute;n de la capa NetCDF a partir de una cadena en
     * formato XML le&iacute;da desde el archivo RMF
     * 
     * @param xml
     *            cadena en formato XML
     * @throws ParsingException
     *             formato XML incorrecto
     * 
     * @see org.gvsig.raster.dataset.io.rmf.IRmfBlock#read(java.lang.String)
     */
    @Override
    public void read(String xml) throws ParsingException {
        // Inicializa la configuración de la capa NetCDF
        config = new NetCDFConfiguration();

    }

    /**
     * Devuelve una cadena en formato XML con la informaci&oacute;n de la
     * configuraci&oacute;n de la capa NetCDF
     * 
     * @return cadena en formato XML
     * 
     * @throws IOException
     *             error de entrada/salida del archivo XML
     * 
     * @see org.gvsig.raster.dataset.io.rmf.IRmfBlock#write()
     */
    @Override
    public String write() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Devuelve el nombre del TAG principal de la configuración dentro del
     * archivo XML
     * 
     * @return tag principal
     * 
     * @see org.gvsig.raster.dataset.io.rmf.IRmfBlock#getMainTag()
     */
    @Override
    public String getMainTag() {
        return MAIN_TAG;
    }

    
    /**
     * Devuelve el objeto creado a partir de la información leída desde el
     * archivo RMF
     * 
     * @return objeto
     * 
     * @see org.gvsig.raster.dataset.io.rmf.IRmfBlock#getResult()
     */
    @Override
    public Object getResult() {
        return config;
    }
}
