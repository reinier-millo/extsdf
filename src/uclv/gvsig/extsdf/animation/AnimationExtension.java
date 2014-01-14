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

import uclv.gvsig.extsdf.raster.FlyrNetCDFRaster;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import com.iver.cit.gvsig.project.documents.view.gui.View;
import com.iver.andami.plugins.Extension;

/**
 * Extension para anadir los controles de la animacion del raster NetCDF
 * @author afmoya
 * @version 1.0.0
 * revision afmoya 201401141337
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
	 * revision afmoya 201401141336
	 */
	public void execute(String actionCommand) {
		// TODO Auto-generated method stub
		IWindow iWindow = PluginServices.getMDIManager().getActiveWindow();
		if(iWindow instanceof View) {
			View view = (View)iWindow;
//			OJO: consultar si la capa raster del netcdf es la que esta 
//			en el top del MapContext o solamente debe estar activa?
			FLayer[] fLayers = view.getMapControl().getMapContext().getLayers().getActives();
			FlyrNetCDFRaster cdfRaster;
			
			for(FLayer fLayer : fLayers) {
//				if(fLayer instanceof FlyrNetCDFRaster) {
//					cdfRaster = (FlyrNetCDFRaster)fLayer;
					AnimationWindow window = new AnimationWindow();
//					window.setNetCDFRasterLayer(cdfRaster);
					PluginServices.getMDIManager().addWindow(window);
//				}				
			}
		}
	}

	@Override
	/**
	 * @author afmoya
	 * @version 1.0.0
	 * revision afmoya 201401140840
	 */
	public boolean isEnabled() {
		// TODO Auto-generated method stub
//		chequear si la vista ya tiene una ventana de animacion asociada
//		para evitar levantar multiples instancias de animaciones que manipulen
//		la misma capa raster
		return true;
	}

	@Override
	/**
	 * @author afmoya
	 * @version 1.0.0
	 * revision afmoya 201401140831
	 */
	public boolean isVisible() {
		// TODO Auto-generated method stub
		IWindow[] i = PluginServices.getMDIManager().getAllWindows();		
		for(IWindow iWindow : i) {
			if(iWindow instanceof View) {
//				OJO: consultar si la capa raster del netcdf es la que esta 
//				en el top del MapContext o solamente debe estar activa?
				FLayer[] fLayers = ((View)iWindow).getMapControl().getMapContext().getLayers().getActives();
//				Se chequea cual de las capas son del tipo que visualiza los 
//				datos del SDT NetCDF.				
				for(FLayer fLayer : fLayers) {
//					Anadir aqui la condicion de chequeo para el tipo de 
//					capa que se defina en el complemento					
//					if(fLayer instanceof FlyrNetCDFRaster) {
//						return true;
//					}
					return true;
				}
			}
		}
		
		return false;
	}

}
