/*
 * TimeSliderExtension.java
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
 * Copyright (C) 2013 Alexis Fajardo Moya <afmoya@uclv.cu>
 *                    Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */

package uclv.gvsig.extsdf.timeslider;

import org.gvsig.fmap.raster.layers.FLyrRasterSE;

import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;

import com.iver.andami.PluginServices;
import com.iver.andami.plugins.Extension;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.project.documents.view.gui.View;
import com.iver.utiles.extensionPoints.ExtensionPoint;
import com.iver.utiles.extensionPoints.ExtensionPoints;
import com.iver.utiles.extensionPoints.ExtensionPointsSingleton;

/**
 * <p>
 * Extensi&oacute;n para el deslizador de tiempo de la capa raster NetCDF.
 * </p>
 * 
 * Esta extensi&oacute;n adiciona un bot&oacute;n a la barra de herramientas de
 * <i>gvSIG</i> que ejecuta el deslizador en el tiempo de la capa raster
 * asociada a un archivo NetCDF de la Vista (<i>View</i>) activa.
 * 
 * @author afmoya
 * @version 1.0.0
 */
public class TimeSliderExtension extends Extension {

	/**
	 * (non javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#initialize()
	 */
	@Override
	public void initialize() {
		registerIcons();
	}

	/**
	 * (non javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#execute(String)
	 */
	@Override
	public void execute(String actionCommand) {
		IWindow activeWindow = PluginServices.getMDIManager().getActiveWindow();

		TimeSliderWindow timeSliderWindow = new TimeSliderWindow();
		timeSliderWindow.setRelatedWindow(activeWindow);
		timeSliderWindow.setLayer(layer);
		PluginServices.getMDIManager().addWindow(timeSliderWindow);

		// Obtiene todos los puntos de extensiÃ³n del gvSIG
		ExtensionPoints extensionPoints = ExtensionPointsSingleton
				.getInstance();

		// CreaciÃ³n del punto de extensiÃ³n para registrar paneles en el cuadro
		// de propiedades.
		if (!extensionPoints.containsKey("NetCDFAnimationSettingsDialog")) {
			extensionPoints
					.put(new ExtensionPoint(
							"NetCDFAnimationSettingsDialog",
							"NetCDF Properties registrable panels (register instances of javax.swing.JPanel)"));
		}

		// AÃ±adimos paneles al cuadro de propiedades.
		extensionPoints.add("NetCDFAnimationSettingsDialog", "Time Display",
				TimeDisplayOptionsPanel.class);
		extensionPoints.add("NetCDFAnimationSettingsDialog", "Time Extent",
				TimeExtentOptionsPanel.class);
		extensionPoints.add("NetCDFAnimationSettingsDialog", "Playback",
				PlaybackOptionsPanel.class);
	}
	
	private FLyrRasterSE layer;

	/**
	 * (non javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		IWindow activeWindow = PluginServices.getMDIManager().getActiveWindow();
		NetCDFRasterDataset dataset = null;

		if (activeWindow instanceof View) {
			// Desactivar si existe un slider abierto para esta ventana
			IWindow[] allWindows = PluginServices.getMDIManager()
					.getAllWindows();
			for (IWindow analized : allWindows) {
				if (analized instanceof TimeSliderWindow) {
					if (activeWindow.equals(((TimeSliderWindow) analized)
							.getRelatedWindow())) {
						return false;
					}
				}
			}

			// Obtener el dataset asociado a esta capa
			View activeView = (View) activeWindow;
			FLayer lyr = activeView.getTOC().getActiveLayer();
			
			// Verificar sÃ³lo si capa activa en el TOC es de tipo NetCDF
      if (lyr instanceof FLyrRasterSE) {
          layer = (FLyrRasterSE) lyr;
          if(layer.getDataSource().getDataset(0)[0] instanceof NetCDFRasterDataset){
              dataset = (NetCDFRasterDataset)layer.getDataSource().getDataset(0)[0];
              return dataset.getConfiguration().getEnabled();
          }
      }
   }

		return false;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see com.iver.andami.plugins.Extension#isVisible()
	 */
	@Override
	public boolean isVisible() {
		IWindow iWindow = PluginServices.getMDIManager().getActiveWindow();
		if (iWindow instanceof View) {
		    // Obtener el dataset asociado a esta capa
	      View activeView = (View) iWindow;
	      FLayer lyr = activeView.getTOC().getActiveLayer();
	      
	      // Verificar sÃ³lo si capa activa en el TOC es de tipo NetCDF
	      if (lyr instanceof FLyrRasterSE) {
	          layer = (FLyrRasterSE) lyr;
	          return layer.getDataSource().getDataset(0)[0] instanceof NetCDFRasterDataset;
	      }
		}
		return false;
	}

	/**
	 * <p>
	 * Registra los iconos de la extensi&oacute;n
	 * </p>
	 */
	private void registerIcons() {
		String[] icons = new String[] { "decrease-icon", "increase-icon",
				"seek-backward-icon", "seek-forward-icon", "settings-icon",
				"skip-backward-icon", "skip-forward-icon", "start-icon",
				"pause-icon", "time-on-map-icon", "full-icon", "video-icon" };
		String[] resources = new String[] { "Decrease-32.png",
				"Increase-32.png", "Seek-Backward-32.png",
				"Seek-Forward-32.png", "Settings-32.png",
				"Skip-Backward-32.png", "Skip-Forward-32.png", "Start-32.png",
				"Pause-32.png", "Time-On-Map-32.png", "Full-32.png",
				"Video-32.png" };

		for (int i = 0; i < icons.length; i++) {
			if (!PluginServices.getIconTheme().exists(icons[i])) {
				PluginServices.getIconTheme().registerDefault(
						icons[i],
						this.getClass().getClassLoader()
								.getResource("images/" + resources[i]));
			}
		}
	}
}
