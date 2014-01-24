/*
 * TimeSliderWindow.java
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

import java.awt.BorderLayout;

import javax.swing.JPanel;

import org.gvsig.gui.beans.swing.JButton;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.andami.ui.mdiManager.WindowInfo;

/**
 * <p>
 * Ventana con los controles y par&aacute;metros para el deslizador de tiempo
 * de la capa raster del formato NetCDF.
 * </p>
 * 
 * @author afmoya
 * @version 1.0.0
 */
public class TimeSliderWindow extends JPanel implements IWindow{
	/**
	 * Default serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Atributo para definir la informaci&oacute;n de la ventana.
	 */
	private WindowInfo windowInfo = null;
	
	/**
	 * Ventana sobre la que act&uacute;a la ventana del deslizador de tiempo.
	 */
	private IWindow relatedWindow = null;
	
	public TimeSliderWindow() {
		super();
		initialize();
	}
	
	/**
	 * (non javadoc)
	 * 
	 * @see com.iver.andami.ui.mdiManager.IWindow#getWindowInfo()
	 */
	@Override
	public WindowInfo getWindowInfo() {
		// TODO Auto-generated method stub
		if (windowInfo == null){
			windowInfo = new WindowInfo(WindowInfo.PALETTE);
			windowInfo.setWidth(this.getWidth());
			windowInfo.setHeight(this.getHeight());
			windowInfo.setTitle(PluginServices.getText(this, "time_slider_window_title") + 
								" -> " + relatedWindow.getWindowInfo().getTitle());
		}
		
		return windowInfo;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see com.iver.andami.ui.mdiManager.IWindow#getWindowProfile()
	 */
	@Override
	public Object getWindowProfile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * <p>
	 * Inicializa los componentes visuales de la ventana.
	 * </p>
	 */
	private void initialize() {
		TimeSliderPanel panel = new TimeSliderPanel();
		setLayout(new BorderLayout());
		setBounds(0, 0, 600, panel.getPreferredSize().height);
		add(panel);
	}
	
	/**
	 * <p>
	 * Asignar a la ventana del deslizador de tiempo la ventana sobre la cual 
	 * est&aacute; actuando. 
	 * </p>
	 * 
	 * Esto se realiza para controlar si el componente puede estar
	 * activo o no para la correspondiente vista.
	 * 
	 * @param view Vista sobre la cual act&uacute;a la ventana del deslizador de tiempo.
	 */
	public void setRelatedWindow(IWindow iWindow) {
			relatedWindow = iWindow;		
	}

	/**
	 * <p>
	 * Devuelve la ventana asociada a la ventana del deslizador de tiempo.
	 * </p>
	 *  
	 * Esto se realiza para controlar si la extensi&oacute;n estar&aacute; activo o no 
	 * para la correspondiente vista.
	 * 
	 * @return IWindow Ventana asociada a la instancia del deslizador de tiempo.
	 */
	public IWindow getRelatedWindow() {
		return relatedWindow;		
	}
}
