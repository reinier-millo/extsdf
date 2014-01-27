/*
 * NetCDFConfiguration.java
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
package uclv.gvsig.extsdf;

/**
 * clase donde se guarda la configuración de las propiedades. Es un puente entre
 * el Dialog de propiedades y las propiedades en la animacilón
 * 
 * @author dcardoso
 * 
 */
public class NetCDFConfiguration {
	/**
	 * indice del formato de fecha seleccionado
	 */
	private int dateformat =0;
	/**
	 * indice del formato de hora seleccionado
	 */
	private int timeformat =0;
	/**
	 * Dice si debe activarse el TimeSlider
	 */
	private Boolean enabled = false;

	/**
	 * Constructor
	 */
	public NetCDFConfiguration() {
		super();
	}

	/**
	 * @return the dateformat
	 */
	public int getDateformat() {
		return dateformat;
	}

	/**
	 * @param dateformat
	 *            the dateformat to set
	 */
	public void setDateformat(int dateformat) {
		this.dateformat = dateformat;
	}

	/**
	 * @return the timeformat
	 */
	public int getTimeformat() {
		return timeformat;
	}

	/**
	 * @param timeformat
	 *            the timeformat to set
	 */
	public void setTimeformat(int timeformat) {
		this.timeformat = timeformat;
	}

	/**
	 * @return the enable
	 */
	public Boolean getEnabled() {
		return enabled;
	}

	/**
	 * @param enable
	 *            the enable to set
	 */
	public void setEnable(Boolean enabled) {
		this.enabled = enabled;
	}

}
