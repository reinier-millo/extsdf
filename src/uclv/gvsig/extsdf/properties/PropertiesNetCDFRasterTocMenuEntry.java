
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
import javax.swing.JDialog;

import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.fmap.raster.layers.ILayerState;
import org.gvsig.gui.beans.panelGroup.PanelGroupManager;
import org.gvsig.gui.beans.panelGroup.tabbedPanel.TabbedPanel;
import org.gvsig.raster.gui.IGenericToolBarMenuItem;
import org.gvsig.raster.util.RasterToolsUtil;
import org.gvsig.rastertools.RasterModule;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.panelGroup.PanelGroupDialog;
import com.iver.cit.gvsig.panelGroup.loaders.PanelGroupLoaderFromExtensionPoint;
import com.iver.cit.gvsig.project.documents.view.toc.AbstractTocContextMenuAction;
import com.iver.cit.gvsig.project.documents.view.toc.ITocItem;

/**
 * Entrada en el menu contextual del TOC correspondiente al cuadro de
 * propiedades del raster
 * 
 * @author dcardoso
 *
 */
public class PropertiesNetCDFRasterTocMenuEntry extends 	AbstractTocContextMenuAction
												implements 	IGenericToolBarMenuItem{

	static private PropertiesNetCDFRasterTocMenuEntry 	singleton  = null;
	private PanelGroupDialog                    		properties = null;                         
	FLayer 													   lyr = null;
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
		return "Properties NetCDF";
	}

	/**
	 * Gestiona la apertura del dialogo de propiedades de raster cuando se pulsa
	 * la opcion asignando a este las propiedades iniciales.
	 */
	@Override
	public void execute(ITocItem item, FLayer[] selectedItems) {
		if ((selectedItems == null) || (selectedItems.length != 1))
			return;

		lyr = selectedItems[0];

		try {
			enableEvents = false;

			PanelGroupManager manager = PanelGroupManager.getManager();

			manager.registerPanelGroup(TabbedPanel.class);
			manager.setDefaultType(TabbedPanel.class);

			TabbedPanel panelGroup = (TabbedPanel) manager.getPanelGroup(lyr);
			PanelGroupLoaderFromExtensionPoint loader = new PanelGroupLoaderFromExtensionPoint("RasterSEPropertiesDialog");

			properties = new PanelGroupDialog(lyr.getName() ,PluginServices.getText(this,"NetCDF_properties"), 550, 450, (byte) (WindowInfo.MODELESSDIALOG | WindowInfo.RESIZABLE | WindowInfo.MAXIMIZABLE), panelGroup);
			properties.loadPanels(loader);
			enableEvents = true;
			//RasterToolsUtil.addWindow(properties);
			PluginServices.getMDIManager().addWindow(properties);
		} catch (Exception e) {
			RasterToolsUtil.messageBoxInfo("error_props_tabs", this, e);
		} finally  {
			enableEvents = true;
		}
	}

	/* (non-Javadoc)
	 * @see org.gvsig.raster.gui.IGenericToolBarMenuItem#getIcon()
	 */
	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see com.iver.cit.gvsig.project.documents.view.toc.AbstractTocContextMenuAction#isEnabled(com.iver.cit.gvsig.project.documents.view.toc.ITocItem, com.iver.cit.gvsig.fmap.layers.FLayer[])
	 */
	public boolean isEnabled(ITocItem item, FLayer[] selectedItems) {
		if ((selectedItems == null) || (selectedItems.length != 1))
            return false;
        FLayer lyr = getNodeLayer(item);
        if (lyr instanceof FLyrRasterSE) {
            return true;
        }
        return false;
    }
	
	/**
     * Verifica si el menú puede mostrarse o no
     *
     * @param item
     *            elemento del menu
     * @param selectedItems
     *            conjunto de capas seleccionadas
     *
     * @return <b>true</b> si la capa seleccionada es un raster de un NetCDF<br />
     *         <b>false</b> en cualquier otro caso
     *
     * @see com.iver.cit.gvsig.project.documents.view.toc.AbstractTocContextMenuAction#isVisible(com.iver.cit.gvsig.project.documents.view.toc.ITocItem,
     *      com.iver.cit.gvsig.fmap.layers.FLayer[])
     */
    public boolean isVisible(ITocItem item, FLayer[] selectedItems) {
        if ((selectedItems == null) || (selectedItems.length != 1))
            return false;
        FLayer lyr = getNodeLayer(item);
        if (lyr instanceof FLyrRasterSE) {
            FLyrRasterSE fr = (FLyrRasterSE) lyr;
       //     return fr.getDataSource().getDataset(0)[0] instanceof MMMC;
            return true;
        }
        return false;
    }

	
}
