/*
 * AnimationWindow.java
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

import javax.swing.JPanel;

import org.gvsig.gui.beans.swing.JButton;

import uclv.gvsig.extsdf.raster.FlyrNetCDFRaster;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * Ventana con los controles y parametros para administrar la animacion
 * @author afmoya
 * @version 1.0.0
 * revision afmoya 201401141340
 */
public class AnimationWindow extends JPanel implements IWindow{
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Atributo para definir la informacion de la ventana
	 */
	private WindowInfo windowInfo = null;
	
	/**
	 * Tipo de capa manipulable por la extension
	 */
	private FlyrNetCDFRaster cdfRaster;	
	
	public AnimationWindow() {
		super();
		initialize();
	}
	
	@Override
	public WindowInfo getWindowInfo() {
		// TODO Auto-generated method stub
		if (windowInfo == null){
			windowInfo = new WindowInfo(WindowInfo.ICONIFIABLE);
			windowInfo.setWidth(this.getWidth());
			windowInfo.setHeight(this.getHeight());
			windowInfo.setTitle(PluginServices.getText(this, "Animation_Timer"));
		}
		
		return windowInfo;
	}

	@Override
	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Metodo para asignarle valor al atributo cdfRaster
	 * @param cdfRaster Capa raster basado en el tipo de dato cientifico NetCDF
	 * @author afmoya
	 * @version 1.0.0
	 * revision 201401141325*/
	public void setNetCDFRasterLayer(FlyrNetCDFRaster cdfRaster) {
		this.cdfRaster = cdfRaster;
	}
	
	/**
	 * Metodo que inicia los componentes visuales de la ventana
	 * @author afmoya
	 * @version 1.0.0
	 * revision 201401141430*/
	private void initialize() {
		add(new JButton("Testing this shit!!"));
	}

}
