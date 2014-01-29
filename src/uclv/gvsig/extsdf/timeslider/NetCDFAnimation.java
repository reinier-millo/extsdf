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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.raster.dataset.io.RasterDriverException;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.project.documents.view.gui.IView;
import com.iver.cit.gvsig.project.documents.view.gui.View;

import ch.randelshofer.media.quicktime.QuickTimeWriter;
import ch.randelshofer.media.quicktime.QuickTimeWriter.VideoFormat;
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
	private QuickTimeWriter writer;
	private boolean recording;
	IView view;
	private File file;
	private int height;
	private int width;

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

		if (recording) {
			try {
				writer = new QuickTimeWriter(file);
				IWindow activeWindow = PluginServices.getMDIManager()
						.getActiveWindow();
				if (activeWindow instanceof View) {
					view = (IView) activeWindow;
					BufferedImage image = view.getMapControl().getImage();
					width = image.getWidth();
					height = image.getHeight();
					writer.addVideoTrack(VideoFormat.RLE, 4, width, height);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (timerTask != null)
			timerTask.cancel();
		timerTask = new PlayTimerTask();
		timer = new Timer();
		timer.schedule(timerTask, 0, period);
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
			if (configuration.getVisualizemoment() < configuration
					.getStartTime()) {
				configuration.setVisualizemoment(configuration.getEndTime());
			} else if (configuration.getVisualizemoment() > configuration
					.getEndTime()) {
				configuration.setVisualizemoment(configuration.getStartTime());
			}

			i = configuration.getVisualizemoment();

			writeFrame();

			move(i);
			configuration
					.setVisualizemoment(configuration.getVisualizemoment() + 1);
		}

	}

	/**
	 * Escribe un cuadro al video cuando se está grabando.
	 */
	private void writeFrame() {
		if (recording) {
			try {
				BufferedImage image = view.getMapControl().getImage();
				if (image.getHeight() != height || image.getWidth() != width) {
					Image image2 = image.getScaledInstance(width, height,
							Image.SCALE_DEFAULT);
					image = new BufferedImage(width, height,
							BufferedImage.TYPE_INT_RGB);
					image.getGraphics().drawImage(image2, 0, 0, null);
				}
				writer.writeFrame(0, image, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Reproduce la animación en orden inverso.
	 */
	public void playInReverse() {
		int period = configuration.getDelayPeriod();
		if (timerTask != null)
			timerTask.cancel();
		timerTask = new PlayBackwardsTimerTask();
		timer = new Timer();
		timer.schedule(timerTask, 0, period);
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
			if (configuration.getVisualizemoment() < configuration
					.getStartTime()) {
				configuration.setVisualizemoment(configuration.getStartTime());
			} else if (configuration.getVisualizemoment() > configuration
					.getEndTime()) {
				configuration.setVisualizemoment(configuration.getEndTime());
			}

			i = configuration.getVisualizemoment();

			writeFrame();

			move(i);
			configuration
					.setVisualizemoment(configuration.getVisualizemoment() - 1);
		}

	}

	/**
	 * Pausa la animación
	 */
	public void pause() {
		if (recording) {
			recording = false;
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		timer.cancel();
	}

	/**
	 * Mover un paso hacia adelante a partir de de la posición actual.
	 */
	public void moveForward() {
		configuration
				.setVisualizemoment(configuration.getVisualizemoment() + 1);
		if (configuration.getVisualizemoment() < configuration.getStartTime()) {
			configuration.setVisualizemoment(configuration.getEndTime());
		} else if (configuration.getVisualizemoment() > configuration
				.getEndTime()) {
			configuration.setVisualizemoment(configuration.getStartTime());
		}
		move(configuration.getVisualizemoment());
	}

	/**
	 * Mover un paso hacia atrás a partir de la posición actual.
	 */
	public void moveBackward() {
		configuration
				.setVisualizemoment(configuration.getVisualizemoment() - 1);
		;
		if (configuration.getVisualizemoment() < configuration.getStartTime()) {
			configuration.setVisualizemoment(configuration.getEndTime());
		} else if (configuration.getVisualizemoment() > configuration
				.getEndTime()) {
			configuration.setVisualizemoment(configuration.getStartTime());
		}
		move(configuration.getVisualizemoment());
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
			e.printStackTrace();
		} catch (InvalidRangeException e) {
			e.printStackTrace();
		} catch (RasterDriverException e) {
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

	/**
	 * @param recording
	 *            the recording to set
	 */
	public void setRecording(boolean recording) {
		this.recording = recording;
	}

	/**
	 * @return the recording
	 */
	public boolean isRecording() {
		return recording;
	}

	/**
	 * Establece el archivo donde se escribirá el video exportado.
	 * 
	 * @param selectedFile
	 */
	public void setOutputFile(File selectedFile) {
		file = selectedFile;
	}

}
