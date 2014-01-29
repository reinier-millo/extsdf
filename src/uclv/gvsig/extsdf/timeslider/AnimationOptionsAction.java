/*
 * AnimationOptionsActionListener.java
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
 * Copyright (C) 2014 Rainer Martinez Fraga <rmartinez@uclv.edu.cu>
 *                    Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */

package uclv.gvsig.extsdf.timeslider;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.gui.beans.panelGroup.PanelGroupManager;
import org.gvsig.gui.beans.panelGroup.tabbedPanel.TabbedPanel;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.WindowInfo;
import com.iver.cit.gvsig.panelGroup.PanelGroupDialog;
import com.iver.cit.gvsig.panelGroup.loaders.PanelGroupLoaderFromExtensionPoint;

/**
 * Acción encargada de mostrar el cuadro de diálogo de opciones de la animación.
 * @author rmartinez
 *
 */
public class AnimationOptionsAction extends AbstractAction  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanelGroupDialog optionsDialog;
	private FLyrRasterSE layer;

	/**
	 * 
	 */
	public AnimationOptionsAction() {
		putValue(LONG_DESCRIPTION, PluginServices.getText(this, "options")); //$NON-NLS-1$
		putValue(SMALL_ICON, PluginServices.getIconTheme().get("settings-icon")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(optionsDialog == null) {
			PanelGroupManager manager = PanelGroupManager.getManager();

			manager.registerPanelGroup(TabbedPanel.class);
			manager.setDefaultType(TabbedPanel.class);

			TabbedPanel panelGroup = null;
			try {				
				panelGroup = (TabbedPanel) manager.getPanelGroup(layer); //$NON-NLS-1$
				PanelGroupLoaderFromExtensionPoint loader = new PanelGroupLoaderFromExtensionPoint("NetCDFAnimationSettingsDialog"); //$NON-NLS-1$
				panelGroup.loadPanels(loader);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			optionsDialog = new PanelGroupDialog(PluginServices.getText(this, "options"), PluginServices.getText(this, "animation_options"), 550, 450, (byte) (WindowInfo.MODALDIALOG | WindowInfo.RESIZABLE | WindowInfo.MAXIMIZABLE), panelGroup); //$NON-NLS-1$ //$NON-NLS-2$
		}
		PluginServices.getMDIManager().addCentredWindow(optionsDialog);
	}
	
	/**
	 * @param layer the layer to set
	 */
	public void setLayer(FLyrRasterSE layer) {
		this.layer = layer;
	}

}
