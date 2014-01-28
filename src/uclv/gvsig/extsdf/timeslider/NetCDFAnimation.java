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
	private NetCDFAnimationTimerTask timerTask;

	/**
	 * @param layer
	 */
	public NetCDFAnimation(FLyrRasterSE layer) {
		this.layer = layer;
		dataset = (NetCDFRasterDataset) layer.getDataSource().getDataset(0)[0];
		controller = dataset.getNetCDFController();
		configuration = dataset.getConfiguration();
//		timer = new Timer();
//		timerTask = new NetCDFAnimationTimerTask();
	}
	
	public void play() {
		timerTask = new NetCDFAnimationTimerTask();
		timer = new Timer();
		timer.schedule(timerTask, 250, 500);
	}
	
	private int i = 1;
	
	private class NetCDFAnimationTimerTask extends TimerTask {

		/* (non-Javadoc)
		 * @see java.util.TimerTask#run()
		 */
		@Override
		public void run() {
			try {
				controller.setParameter(i);
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
			layer.getMapContext().invalidate();
			i++;
			i %= 120;
			fireChange();
			System.out.println("Change " + i);
		}
		
	}

	/**
	 * 
	 */
	public void pause() {
		timer.cancel();
	}
	
	public void moveForward() {
		try {
			controller.setParameter((controller.getParameter() + 1));
			fireChange();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidRangeException e) {
			e.printStackTrace();
		} catch (RasterDriverException e) {
			e.printStackTrace();
		}
		layer.getMapContext().invalidate();
	}
	
	public void moveBackward() {
		try {
			controller.setParameter((controller.getParameter() - 1));
			fireChange();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidRangeException e) {
			e.printStackTrace();
		} catch (RasterDriverException e) {
			e.printStackTrace();
		}
		layer.getMapContext().invalidate();
	}
	
	public void move(int position) {
		try {
			controller.setParameter(position);
			fireChange();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidRangeException e) {
			e.printStackTrace();
		} catch (RasterDriverException e) {
			e.printStackTrace();
		}
		layer.getMapContext().invalidate();
	}
	
	private List<AnimationListener> listeners = new ArrayList<AnimationListener>(0);
	
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
