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
 * 					  Daynier Cardoso Roque <dcardoso@uclv.edu.cu>
 *                    Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */

package uclv.gvsig.extsdf.timeslider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author rmartinez
 * @author dcardoso
 */
public class DateTimeFormats {

	private String[] dates = { "MM/dd/yy",
			"MM/dd/yyyy",
			"M/d/yy",
			"M/d/yyyy",
			"yy/MM/dd",
			"yyyy",
			"yyyy-MM-dd",
			"dd MMMM, yyyy",
			"dd-MMM-yyy",
			"EEEE",
			"EEEE, dd MMMM, yyyy",
			"EEEE, MMMM dd, yyyy",
			"MMM yyy",
			"MMMM yyyy",
			"MMMM dd, yyyy" };
	private String[]hours={"hh:mm a",
			"H:mm:ss",
			"HH:mm:ss",
			"h:mm:ss a",
			"hh:mm:ss a"};
	/**
	 * @return the dates
	 */
	public String[] getDates() {
		return dates;
	}
	/**
	 * @return the hours
	 */
	public String[] getHours() {
		return hours;
	}
	
	public String[] getTodayDatesFormat(){
		return getTodayFormat(dates);
	}
	
	public String[] getTodayHoursFormat(){
		return getTodayFormat(hours);
	}
	
	private String[] getTodayFormat(String[] format){
		String[]today = new String[format.length];
		SimpleDateFormat formatter = new SimpleDateFormat();
		Date date = new Date(System.currentTimeMillis());
		for (int i = 0; i < format.length; i++) {
			formatter.applyLocalizedPattern(format[i]);
			today[i] = formatter.format(date) + " ("+format[i]+")";
		}
		return today;
	}

}
