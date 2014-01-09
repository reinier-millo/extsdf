/*
 * NetCDFFileFilter.java
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

package uclv.gvsig.extsdf.driver;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * Filtro de selecci&oacute;n para los archivos NetCDF
 * 
 * @author millo
 * @version 1.0.0
 * 
 */
public class NetCDFFileFilter extends FileFilter {

    public static final String EXTENSION = ".NC";
    public static final String DESCRIPTION = "gvSIG NetCDF Raster File";

    /**
     * Indica si un archivo es aceptado por el filtro
     * 
     * @return <b>true</b> si el archivo tiene la extensi&oacute;n <b>nc</b> <br />
     *         <b>false</b> en cualquier otro caso
     * 
     * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
     */
    @Override
    public boolean accept(File arg0) {
        String name = arg0.getName().toUpperCase();
        return arg0.isDirectory() || name.endsWith(EXTENSION);
    }

    /**
     * Devuelve la descripci&oacute;n del filtro
     * 
     * @return descripci&oacute;n del filtro
     * @see javax.swing.filechooser.FileFilter#getDescription()
     */
    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

}
