/*
 * NetCDFPropertiesExtension.java
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
 * Copyright (C) 2014 Daynier Cardoso Roque <dcardoso@uclv.edu.cu>
 *                    Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */
package uclv.gvsig.extsdf.properties;

import uclv.gvsig.extsdf.properties.panels.NetCDFPanel;

import com.iver.andami.plugins.Extension;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;

/**
 * Extension para gestionar la entrada del Dialog de propiedades en el TOC y en
 * la barra de propiedades genéricas
 * 
 * @author dcardoso
 * 
 */
public class NetCDFPropertiesExtension extends Extension {

	/*
	 * (non-Javadoc)
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
		// extensionPoints.add("NetCDFPropertiesDialog", "Time",
		// TimePanel.class);

		// Añadimos las entradas del menú del toc de raster
		extensionPoints.add("View_TocActions", "NetCDFProperties",
				PropertiesNetCDFRasterTocMenuEntry.getSingleton());

		org.gvsig.raster.util.extensionPoints.ExtensionPoint point = org.gvsig.raster.util.extensionPoints.ExtensionPoint
				.getExtensionPoint("GenericToolBarMenu");
		point.register("NetCDFProperties",
				PropertiesNetCDFRasterTocMenuEntry.getSingleton());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.IExtension#execute(java.lang.String)
	 */
	@Override
	public void execute(String actionCommand) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.IExtension#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.iver.andami.plugins.IExtension#isVisible()
	 */
	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return false;
	}

}
