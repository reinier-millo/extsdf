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
import com.iver.cit.gvsig.project.documents.view.gui.View;

/**
 * Ventana con los controles y parámetros para administrar la animación
 * de la capa raster del formato NetCDF.
 * @author afmoya
 * @version 1.0.0
 */
public class AnimationWindow extends JPanel implements IWindow{
	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Atributo para definir la información de la ventana
	 */
	private WindowInfo windowInfo = null;
	
	/**
	 * Tipo de capa manipulable por la extensión
	 */
	private FlyrNetCDFRaster cdfRaster;
	
	/**
	 * Vista sobre la que actúa la ventana de animación
	 */
	private IWindow relatedWindow = null;
	
	public AnimationWindow() {
		super();
		initialize();
	}
	
	@Override
	/**
	 * Método que devuelve las propiedades de la ventana de animación.
	 * @author afmoya
	 * @version 1.0.0
	 */
	public WindowInfo getWindowInfo() {
		// TODO Auto-generated method stub
		if (windowInfo == null){
			windowInfo = new WindowInfo(WindowInfo.ICONIFIABLE);
			windowInfo.setWidth(this.getWidth());
			windowInfo.setHeight(this.getHeight());
			windowInfo.setTitle(relatedWindow.getWindowInfo().getTitle());
		}
		
		return windowInfo;
	}

	@Override
	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Método para asignarle valor al atributo cdfRaster.
	 * @param cdfRaster Capa raster basado en el tipo de dato científico NetCDF
	 * @author afmoya
	 * @version 1.0.0
	 */
	public void setNetCDFRasterLayer(FlyrNetCDFRaster cdfRaster) {
		this.cdfRaster = cdfRaster;
	}
	
	/**
	 * Método que inicia los componentes visuales de la ventana.
	 * @author afmoya
	 * @version 1.0.0
	 */
	private void initialize() {
		setBounds(0, 0, 500, 500);
		add(new JButton("Hello gvSIG extension!!"));		
	}
	
	/**
	 * Método para asignarle a la ventana de animación la ventana sobre la cual 
	 * está actuando. Esto se realiza para controlar si el componente puede estar
	 * activo o no para la correspondiente vista.
	 * @param view Vista sobre la cual actúa la ventana de animación.
	 * @author afmoya
	 * @version 1.0.0
	 */
	public void setRelatedWindow(IWindow iWindow) {
			relatedWindow = iWindow;		
	}

	/**
	 * Método que devuelve la ventana asociada a la ventana de animación. 
	 * Esto se realiza para controlar si el componente estará activo o no 
	 * para la correspondiente vista.
	 * @param view Vista sobre la cual actua la ventana de animacion.
	 * @author afmoya
	 * @version 1.0.0
	 */
	public IWindow getRelatedWindow() {
		return relatedWindow;		
	}
}
