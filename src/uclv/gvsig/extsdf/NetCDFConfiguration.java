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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uclv.gvsig.extsdf.timeslider.AnimationBehaviour;
import uclv.gvsig.extsdf.timeslider.AnimationListener;

/**
 * clase donde se guarda la configuración de las propiedades. Es un puente entre
 * el Dialog de propiedades y las propiedades en la animacilón
 * 
 * @author dcardoso
 * 
 */
public class NetCDFConfiguration implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * indice del formato de fecha seleccionado
	 */
	private int dateformat = 0;
	/**
	 * indice del formato de hora seleccionado
	 */
	private int timeformat = 0;
	/**
	 * Dice si debe activarse el TimeSlider
	 */
	private Boolean enabled = false;
	/**
	 * indice del instante de tiempo a visualizar
	 */
	private int visualizemoment = 0;
	/**
	 * indice del sistema de coordenadas seleccionado
	 */
	private int sistemacoordenada = 0;
	/**
	 * indice de la variable seleccionada
	 */
	private int variable = 0;
	
	private int delayPeriod = 400;
	
	private int startTime = 0;
	
	private int endTime = -1;
	
	private AnimationBehaviour animationBehaviour = AnimationBehaviour.REPEAT;
	
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
		fireChange("Format");
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
		fireChange("Format");
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
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * @return the animationBehaviour
	 */
	public AnimationBehaviour getAnimationBehaviour() {
		return animationBehaviour;
	}
	
	/**
	 * @param animationBehaviour the animationBehaviour to set
	 */
	public void setAnimationBehaviour(AnimationBehaviour animationBehaviour) {
		this.animationBehaviour = animationBehaviour;
	}
	
	/**
	 * @return the delayPeriod
	 */
	public int getDelayPeriod() {
		return delayPeriod;
	}

	/**
	 * @param delayPeriod the delayPeriod to set
	 */
	public void setDelayPeriod(int delayPeriod) {
		this.delayPeriod = delayPeriod;
	}
	
	/**
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}
	
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(int startTime) {
		fireChange("Start_Time");
		this.startTime = startTime;
	}
	
	/**
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}
	
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(int endTime) {
		fireChange("Time_Bounds");
		this.endTime = endTime;
	}

	/**
	 * @return the visualizemoment
	 */
	public int getVisualizemoment() {
		return visualizemoment;
	}

	/**
	 * @param visualizemoment the visualizemoment to set
	 */
	public void setVisualizemoment(int visualizemoment) {
		this.visualizemoment = visualizemoment;
	}

	/**
	 * @return the sistemacoordenada
	 */
	public int getSistemacoordenada() {
		return sistemacoordenada;
	}

	/**
	 * @param sistemacoordenada the sistemacoordenada to set
	 */
	public void setSistemacoordenada(int sistemacoordenada) {
		this.sistemacoordenada = sistemacoordenada;
	}

	/**
	 * @return the variable
	 */
	public int getVariable() {
		return variable;
	}

	/**
	 * @param variable the variable to set
	 */
	public void setVariable(int variable) {
		this.variable = variable;
	}
	
	
	private transient List<ChangeListener> listeners = new ArrayList<ChangeListener>(0);

	public void addChangeListener(ChangeListener listener) {
		listeners.add(listener);
	}

	public void removeChangeListener(AnimationListener listener) {
		listeners.remove(listener);
	}

	private void fireChange(String source) {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(source));
		}
	}
	
}
