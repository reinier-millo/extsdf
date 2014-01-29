/*
 * NetCDFAnimation.java
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.raster.dataset.io.RasterDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ucar.ma2.InvalidRangeException;
import uclv.gvsig.extsdf.NetCDFConfiguration;
import uclv.gvsig.extsdf.NetCDFController;
import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;

/**
 * @author rmartinez
 * 
 */
public class NetCDFAnimation {

	private FLyrRasterSE layer;
	private NetCDFRasterDataset dataset;
	private NetCDFController controller;
	private NetCDFConfiguration configuration;
	private Timer timer = new Timer();
	private TimerTask timerTask;
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * @param layer
	 */
	public NetCDFAnimation(FLyrRasterSE layer) {
		this.layer = layer;
		dataset = (NetCDFRasterDataset) layer.getDataSource().getDataset(0)[0];
		controller = dataset.getNetCDFController();
		configuration = dataset.getConfiguration();
	}

	/**
	 * Reproducir la animación.
	 */
	public void play() {
		int period = configuration.getDelayPeriod();
		if (timerTask != null)
			timerTask.cancel();
		timerTask = new PlayTimerTask();
		timer = new Timer();
		timer.schedule(timerTask, period, period);
	}

	private class PlayTimerTask extends TimerTask {

		private int i;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			if(configuration.getVisualizemoment() < configuration.getStartTime()) {
				configuration.setVisualizemoment(configuration.getEndTime());
			} else if (configuration.getVisualizemoment() > configuration.getEndTime()) {
				configuration.setVisualizemoment(configuration.getStartTime());
			}
			
			i = configuration.getVisualizemoment();
			move(i);
			configuration.setVisualizemoment(configuration.getVisualizemoment() + 1);
		}

	}

	public void playInReverse() {
		int period = configuration.getDelayPeriod();
		if (timerTask != null)
			timerTask.cancel();
		timerTask = new PlayBackwardsTimerTask();
		timer = new Timer();
		timer.schedule(timerTask, period, period);
	}

	private class PlayBackwardsTimerTask extends TimerTask {

		private int i;

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			if(configuration.getVisualizemoment() < configuration.getStartTime()) {
				configuration.setVisualizemoment(configuration.getStartTime());
			} else if (configuration.getVisualizemoment() > configuration.getEndTime()) {
				configuration.setVisualizemoment(configuration.getEndTime());
			}
			
			i = configuration.getVisualizemoment();
			move(i);
			configuration.setVisualizemoment(configuration.getVisualizemoment() - 1);
		}

	}

	/**
	 * Pausa la animación
	 */
	public void pause() {
		timer.cancel();
	}

	/**
	 * Mover un paso hacia adelante a partir de de la posición actual.
	 */
	public void moveForward() {
		move(configuration.getVisualizemoment() + 1);
	}

	/**
	 * Mover un paso hacia atrás a partir de la posición actual.
	 */
	public void moveBackward() {
		move(configuration.getVisualizemoment() - 1);
	}

	/**
	 * Permite visualizar la capa en un instante de tiempo pasado como
	 * párametro.
	 * 
	 * @param position
	 *            El índice de la fecha en el parámetro variable que indica el
	 *            instante de tiempo a visualizar.
	 */
	public void move(int position) {
		configuration.setVisualizemoment(position);
		try {
			controller.setParameter(position);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidRangeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RasterDriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fireChange();
		layer.getMapContext().invalidate();
	}

	private List<AnimationListener> listeners = new ArrayList<AnimationListener>(
			0);

	public void addAnimationListener(AnimationListener listener) {
		listeners.add(listener);
	}

	public void removeAnimationListener(AnimationListener listener) {
		listeners.remove(listener);
	}

	private void fireChange() {
		for (AnimationListener e : listeners) {
			e.animationStateChanged();
		}
	}

}
