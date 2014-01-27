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

import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.gui.beans.panelGroup.PanelGroupManager;
import org.gvsig.gui.beans.panelGroup.tabbedPanel.TabbedPanel;
import org.gvsig.raster.gui.IGenericToolBarMenuItem;
import org.gvsig.raster.util.RasterToolsUtil;

import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.panelGroup.PanelGroupDialog;
import com.iver.cit.gvsig.panelGroup.loaders.PanelGroupLoaderFromExtensionPoint;
import com.iver.cit.gvsig.project.documents.view.toc.AbstractTocContextMenuAction;
import com.iver.cit.gvsig.project.documents.view.toc.ITocItem;

/**
 * Clase que posibilita la entrada en el menú contextual del TOC correspondiente
 * al cuadro de propiedades del raster
 * 
 * @author dcardoso
 * @see link AbstractTocContextMenuAction
 * @see IGenericToolBarMenuItem
 * 
 */
public class PropertiesNetCDFRasterTocMenuEntry extends
		AbstractTocContextMenuAction implements IGenericToolBarMenuItem {

	static private PropertiesNetCDFRasterTocMenuEntry singleton = null;
	private PanelGroupDialog properties = null;
	FLayer lyr = null;
	/**
	 * Variable para controlar si los eventos de los paneles se deben
	 * interpretar. En la carga inicial se deben desactivar todos los eventos
	 */
	public static boolean enableEvents = false;

	/**
	 * Nadie puede crear una instancia a esta clase unica, hay que usar el
	 * getSingleton()
	 */
	private PropertiesNetCDFRasterTocMenuEntry() {
	}

	/**
	 * Devuelve un objeto unico de esta clase
	 * 
	 * @return PropertiesNetCDFRasterTocMenuEntry
	 */
	static public PropertiesNetCDFRasterTocMenuEntry getSingleton() {
		if (singleton == null)
			singleton = new PropertiesNetCDFRasterTocMenuEntry();
		return singleton;
	}

	/**
	 * Devuelve el nombre del grupo al que pertenece el ítem de menú
	 * 
	 * @see com.iver.cit.gvsig.project.documents.contextMenu.AbstractContextMenuAction#getGroup()
	 * @see org.gvsig.raster.gui.IGenericToolBarMenuItem#getGroup()
	 */
	@Override
	public String getGroup() {
		return "RasterLayer";
	}

	/**
	 * Devuelve el orden del grupo
	 * 
	 * @see com.iver.cit.gvsig.project.documents.contextMenu.AbstractContextMenuAction#getGroupOrder()
	 * @see org.gvsig.raster.gui.IGenericToolBarMenuItem#getGroupOrder()
	 */
	@Override
	public int getGroupOrder() {
		return 60;
	}

	/**
	 * Devuelve orden del ítem
	 * 
	 * @see com.iver.cit.gvsig.project.documents.contextMenu.AbstractContextMenuAction#getOrder()
	 * @see org.gvsig.raster.gui.IGenericToolBarMenuItem#getOrder()
	 */
	@Override
	public int getOrder() {
		return 0;
	}

	/**
	 * Devuelve el texto que debe mostrar el ítem del menú
	 * 
	 * @see com.iver.cit.gvsig.project.documents.contextMenu.AbstractContextMenuAction#getText()
	 * @see org.gvsig.raster.gui.IGenericToolBarMenuItem#getText()
	 */
	@Override
	public String getText() {
		return PluginServices.getText(this, "NetCDF_properties");
	}

	/**
	 * Devuelve el icono del ítem del menú
	 * 
	 * @see com.iver.cit.gvsig.project.documents.contextMenu.AbstractContextMenuAction#getIcon()
	 * @see org.gvsig.raster.gui.IGenericToolBarMenuItem#getIcon()
	 */
	@Override
	public Icon getIcon() {
		return RasterToolsUtil.getIcon("properties-icon");
	}

	/**
	 * Verifica si el menú puede habilitarse o no
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
	public boolean isEnabled(ITocItem item, FLayer[] selectedItems) {
		if ((selectedItems == null) || (selectedItems.length != 1))
			return false;
		FLayer lyr = selectedItems[0];
		if (lyr instanceof FLyrRasterSE) {
			FLyrRasterSE fr = (FLyrRasterSE) lyr;
			return fr.getDataSource().getDataset(0)[0] instanceof NetCDFRasterDataset;
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
		FLayer lyr = selectedItems[0];
		if (lyr instanceof FLyrRasterSE) {
			FLyrRasterSE fr = (FLyrRasterSE) lyr;
			return fr.getDataSource().getDataset(0)[0] instanceof NetCDFRasterDataset;
		}
		return false;
	}

	/**
	 * Gestiona la apertura del dialogo de propiedades del NetCDF
	 * 
	 * @param item
	 *            elemento del menu
	 * @param selectedItems
	 *            conjunto de capas seleccionadas
	 * @see com.iver.cit.gvsig.project.documents.view.toc.AbstractTocContextMenuAction
	 *      #execute(com.iver.cit.gvsig.project.documents.view.toc.ITocItem,
	 *      com.iver.cit.gvsig.fmap.layers.FLayer[])
	 */
	@Override
	public void execute(ITocItem item, FLayer[] selectedItems) {
		if ((selectedItems == null) || (selectedItems.length != 1))
			return;
		FLayer lyr = selectedItems[0];

		try {
			enableEvents = false;

			PanelGroupManager manager = PanelGroupManager.getManager();

			manager.registerPanelGroup(TabbedPanel.class);
			manager.setDefaultType(TabbedPanel.class);

			TabbedPanel panelGroup = (TabbedPanel) manager.getPanelGroup(lyr);
			PanelGroupLoaderFromExtensionPoint loader = new PanelGroupLoaderFromExtensionPoint(
					"NetCDFPropertiesDialog");

			properties = new PanelGroupDialog(lyr.getName(),
					PluginServices.getText(this, "NetCDF_properties"), 550,
					450, (byte) (WindowInfo.MODELESSDIALOG
							| WindowInfo.RESIZABLE | WindowInfo.MAXIMIZABLE),
					panelGroup);
			properties.loadPanels(loader);
			enableEvents = true;
			RasterToolsUtil.addWindow(properties);
		} catch (Exception e) {
			RasterToolsUtil.messageBoxInfo("error_props_tabs", this, e);
		} finally {
			enableEvents = true;
		}
	}
}
