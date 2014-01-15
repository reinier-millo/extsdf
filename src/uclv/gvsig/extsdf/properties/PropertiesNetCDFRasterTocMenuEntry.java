
/*
 * PropertiesNetCDFRaster.java
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

import javax.swing.Icon;

import org.gvsig.raster.gui.IGenericToolBarMenuItem;

import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.project.documents.view.toc.AbstractTocContextMenuAction;
import com.iver.cit.gvsig.project.documents.view.toc.ITocItem;

/**
 * @author dcardoso
 *
 */
public class PropertiesNetCDFRasterTocMenuEntry extends 	AbstractTocContextMenuAction 
												implements 	IGenericToolBarMenuItem{

	static private PropertiesNetCDFRasterTocMenuEntry singleton  = null;
	                         

	/**
	 * Variable para controlar si los eventos de los paneles se deben interpretar.
	 * En la carga inicial se deben desactivar todos los eventos
	 */
	public static boolean                     enableEvents = false;
	
	/**
	 * Nadie puede crear una instancia a esta clase unica, hay que usar el
	 * getSingleton()
	 */
	private PropertiesNetCDFRasterTocMenuEntry() {}

	/**
	 * Devuelve un objeto unico a dicha clase
	 * @return
	 */
	static public PropertiesNetCDFRasterTocMenuEntry getSingleton() {
		if (singleton == null)
			singleton = new PropertiesNetCDFRasterTocMenuEntry();
		return singleton;
	}	
	
	/* (non-Javadoc)
	 * @see com.iver.cit.gvsig.project.documents.IContextMenuAction#getText()
	 */
	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.iver.cit.gvsig.project.documents.view.toc.AbstractTocContextMenuAction#execute(com.iver.cit.gvsig.project.documents.view.toc.ITocItem, com.iver.cit.gvsig.fmap.layers.FLayer[])
	 */
	@Override
	public void execute(ITocItem item, FLayer[] selectedItems) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see org.gvsig.raster.gui.IGenericToolBarMenuItem#getIcon()
	 */
	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
