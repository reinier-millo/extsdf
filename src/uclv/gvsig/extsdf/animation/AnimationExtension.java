/*
 * AnimationExtension.java
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

package uclv.gvsig.extsdf.animation;

import org.gvsig.fmap.raster.layers.FLyrRasterSE;

import uclv.gvsig.extsdf.raster.FlyrNetCDFRaster;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.fmap.layers.FLayers;
import com.iver.cit.gvsig.project.documents.view.gui.View;
import com.iver.andami.plugins.Extension;

/**
 * Extensión para añadir los controles de la animación del raster NetCDF.
 * @author afmoya
 * @version 1.0.0
 */
public class AnimationExtension extends Extension{

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	/**
	 * @author afmoya
	 * @version 1.0.0
	 */
	public void execute(String actionCommand) {
		// TODO Auto-generated method stub
		IWindow activeWindow = PluginServices.getMDIManager().getActiveWindow();
		if(activeWindow instanceof View) {
			View activeView = (View)activeWindow;
			FLayers fLayers = activeView.getMapControl().getMapContext().getLayers();
			int layersCount = fLayers.getLayersCount();
			
			FlyrNetCDFRaster cdfRaster = null;
			
			for(int i=0; i<layersCount; ++i) {
//				if(fLayer instanceof FlyrNetCDFRaster) {
//					cdfRaster = (FlyrNetCDFRaster)fLayer;
					AnimationWindow animationWindow = new AnimationWindow();
					animationWindow.setNetCDFRasterLayer(cdfRaster);
					animationWindow.setRelatedWindow(activeWindow);
					PluginServices.getMDIManager().addWindow(animationWindow);
					break;
//				}				
			}
		}
	}

	@Override
	/**
	 * Chequear si la vista activa ya tiene asociada una ventana de administración
	 * de la animación.
	 * @author afmoya
	 * @version 1.0.0
	 */
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		IWindow activeWindow = PluginServices.getMDIManager().getActiveWindow();
		if(activeWindow instanceof View) {
//			View view = (View)activeWindow;
			IWindow[] allWindows = PluginServices.getMDIManager().getAllWindows();
			for(IWindow analized : allWindows) {
				if(analized instanceof AnimationWindow) {
					if(activeWindow.equals(((AnimationWindow)analized).getRelatedWindow())) {
						return false;
					}
				}				
			}
		}
		return true;
	}

	@Override
	/**
	 * @author afmoya
	 * @version 1.0.0
	 */
	public boolean isVisible() {
		// TODO Auto-generated method stub
		IWindow iWindow = PluginServices.getMDIManager().getActiveWindow();		
		if(iWindow instanceof View) {
			FLayers fLayers = ((View)iWindow).getMapControl().getMapContext().getLayers();
			int numberOfLayers = fLayers.getLayersCount();
			for(int index=0; index<numberOfLayers; ++index) {
				// OJO: Suprimir FLyrRasterSE por la capa que se defina en el plugin
				if(fLayers.getLayer(index) instanceof FLyrRasterSE) {
					return true;
				}
			}			
		}		
		return false;
	}
}
