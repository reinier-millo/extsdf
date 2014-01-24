/*
 * DateTimeFormats.java
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * @author rmartinez
 *
 */
public class DateTimeFormats {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	String [] formats;
	int default_format;
	
	/**
	 * 
	 */
	public DateTimeFormats(String[] formats) {
		this.formats = formats;
	}
	
	public void setDefaultFormatIndex(int index) {
		if(index >= 0 && index < formats.length) {
			default_format = index;
		} else {
			logger.warn("No valid default format. Set default to -1");
		}
	}
	
	public int getDefaultFormatIndex() {
		return default_format;
	}
	
	public String getDefaultFormat() {
		if(default_format != -1) {
			return formats[default_format];
		}
		return null;
	}
	
	public void setFormats(String[] formats) {
		this.formats = formats;
	}
	
	public String[] getFormats() {
		return formats;
	}

}
