/*
 * NetCDFDriver.java
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

import java.awt.geom.Rectangle2D;
import java.io.File;

import org.cresques.cts.IProjection;
import org.gvsig.raster.util.RasterToolsUtil;

import ucar.nc2.NetcdfFile;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.addlayer.fileopen.AbstractFileOpen;
import com.iver.cit.gvsig.exceptions.layers.LoadLayerException;
import com.iver.cit.gvsig.fmap.MapControl;

/**
 * Driver para la lectura de archivos NetCDF
 * 
 * @author millo
 * @version 1.0.0
 * 
 */
public class NetCDFDriver extends AbstractFileOpen {

    /**
     * Constructor por defecto del Driver
     */
    @SuppressWarnings("unchecked")
    public NetCDFDriver() {
        super();

        // Añade el filtro para archivos NetCDF
        getFileFilter().add(new NetCDFFileFilter());
    }

    /**
     * Procesa el archivo NetCDF cargado por el Driver
     * 
     * @param file
     *            archivo cargado
     * 
     * @return
     * 
     * @throws LoadLayerException
     * 
     * @see org.gvsig.raster.gui.wizards.IFileOpen#post(java.io.File[])
     */
    @Override
    public File post(File file) throws LoadLayerException {

        // Verifica si el archivo seleccionado no es null
        if (file == null || file.getAbsoluteFile() == null)
            return null;

        // Verificamos que el archivo se pueda abrir correctamente
        try {
            NetcdfFile ncdf = NetcdfFile.open(file.getAbsolutePath());
            ncdf.close();
        } catch (Exception ex) {
            RasterToolsUtil.messageBoxError(
                    PluginServices.getText(this, "error_abrir_fichero")
                            + " "
                            + file.getName()
                            + "\n\n"
                            + PluginServices.getText(this,
                                    "informacion_adicional") + ":\n\n  "
                            + ex.getMessage(), this, ex);
            return null;
        }

        return file;
    }

    @Override
    public Rectangle2D createLayer(File arg0, MapControl arg1, String arg2,
            IProjection arg3) {
        // TODO Auto-generated method stub
        return null;
    }
}
