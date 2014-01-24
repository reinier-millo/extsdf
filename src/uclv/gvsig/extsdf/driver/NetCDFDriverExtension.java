/*
 /*
 * NetCDFDriverExtension.java
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 *
 * Copyright (C) 2013 Reinier Millo Sánchez <rmillo@uclv.cu>
 * Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */

package uclv.gvsig.extsdf.driver;

import uclv.gvsig.extsdf.properties.PropertiesNetCDFRasterTocMenuEntry;
import uclv.gvsig.extsdf.properties.panels.NetCDFPanel;
import uclv.gvsig.extsdf.properties.panels.TimePanel;
import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;

import com.iver.andami.plugins.Extension;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;

/**
 * Extensi&oacute;n para darle soporte al Driver para leer archivos NetCDF
 * 
 * @author millo
 * @version 1.0.1
 * 
 */
public class NetCDFDriverExtension extends Extension {

    /*
     * (non-Javadoc)
     * 
     * @see com.iver.andami.plugins.IExtension#execute(java.lang.String)
     */
    @Override
    public void execute(String arg0) {
    }

    /**
     * Inicializa la extensi&oacute;n
     * 
     * @see com.iver.andami.plugins.IExtension#initialize()
     */
    @Override
    public void initialize() {
        // Obtiene todos los puntos de extensión del gvSIG
        ExtensionPoints extensionPoints = ExtensionPointsSingleton
                .getInstance();

        // Creación del punto de extensión para registrar paneles en el cuadro
        // de propiedades.
        
        if (!extensionPoints.containsKey("NetCDFPropertiesDialog")) {
            extensionPoints
                    .put(new ExtensionPoint(
                            "NetCDFPropertiesDialog",
                            "NetCDF Properties registrable panels (register instances of javax.swing.JPanel)"));
        }

        // Añadimos paneles al cuadro de propiedades.
        extensionPoints.add("NetCDFPropertiesDialog", "NetCDF",
                NetCDFPanel.class);
        extensionPoints.add("NetCDFPropertiesDialog", "Time", TimePanel.class);

        // Añadimos las entradas del menú del toc de raster
        extensionPoints.add("View_TocActions", "NetCDFProperties",
                PropertiesNetCDFRasterTocMenuEntry.getSingleton());
        
        org.gvsig.raster.util.extensionPoints.ExtensionPoint point = org.gvsig.raster.util.extensionPoints.ExtensionPoint.getExtensionPoint("GenericToolBarMenu");
        point.register("NetCDFProperties",
                PropertiesNetCDFRasterTocMenuEntry.getSingleton());
        
        
        // Registra el driver para dar soporte a los archivos NetCDF
        NetCDFRasterDataset.registerDriver();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.iver.andami.plugins.IExtension#isEnabled()
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.iver.andami.plugins.IExtension#isVisible()
     */
    @Override
    public boolean isVisible() {
        return false;
    }

}