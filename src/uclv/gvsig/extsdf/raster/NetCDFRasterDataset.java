/*
 * NetCDFRasterDataset.java
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

package uclv.gvsig.extsdf.raster;

import java.awt.geom.AffineTransform;
import java.io.IOException;

import org.cresques.cts.ICoordTrans;
import org.gvsig.raster.dataset.BandAccessException;
import org.gvsig.raster.dataset.BandList;
import org.gvsig.raster.dataset.FileNotOpenException;
import org.gvsig.raster.dataset.GeoInfo;
import org.gvsig.raster.dataset.IBuffer;
import org.gvsig.raster.dataset.InvalidSetViewException;
import org.gvsig.raster.dataset.RasterDataset;
import org.gvsig.raster.dataset.io.RasterDriverException;
import org.gvsig.raster.dataset.properties.DatasetColorInterpretation;
import org.gvsig.raster.dataset.properties.DatasetMetadata;
import org.gvsig.raster.datastruct.Extent;
import org.gvsig.raster.datastruct.Transparency;
import org.gvsig.raster.util.extensionPoints.ExtensionPoint;

import uclv.gvsig.extsdf.NetCDFController;

/**
 * <p>
 * Clase para manipular el datasource de la capa raster del NetCDF.
 * </p>
 * 
 * @author
 * @version 1.0.0
 */
public class NetCDFRasterDataset extends RasterDataset {
	/**
	 * Controlador del archivo NetCDF
	 */
	private NetCDFController controller;

	/**
	 * 
	 */
	private Transparency fileTransparency = null;

	/**
	 * 
	 */
	private Extent viewRequest = null;

	/**
	 * 
	 */
	private DatasetColorInterpretation colorInterpr = null;

	/**
	 * Crea las transformaciones de la capa raster.
	 * 
	 * @return GeoInfo
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#load()
	 */
	@Override
	public GeoInfo load() {
		try {
			// Calcula el incremento de la latitud
			double min = controller.getSelectedLat().getMinValue();
			double incLat = Math.abs(min
					- controller.getSelectedLat().getMaxValue())
					/ (controller.getSelectedLat().getSize() - 1);
			// Verifica si las latitudes aumentan o decrementean
			if (controller.getSelectedLat().getCoordEdge(0) != min)
				incLat = -incLat;

			// Calcula el incremento de la longitud
			min = controller.getSelectedLon().getMinValue();
			double incLon = Math.abs(min
					- controller.getSelectedLon().getMaxValue())
					/ (controller.getSelectedLon().getSize() - 1);
			// Verifica si las latitudes aumentan o decrementean
			if (controller.getSelectedLon().getCoordEdge(0) != min)
				incLon = -incLon;

			// Inicializa las transformacions
			ownTransformation = new AffineTransform(incLon, 0, 0, incLat,
					controller.getSelectedLon().getCoordValue(0), controller
							.getSelectedLat().getCoordValue(0));
			externalTransformation = (AffineTransform) ownTransformation
					.clone();
		} catch (RasterDriverException e) {
			// Do nothing
		}
		return this;
	}

	/**
	 * Cierra el archivo NetCDF
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#close()
	 */
	@Override
	public void close() {
		try {
			controller.close();
		} catch (IOException e) {
			// Do nothing
		}
	}

	/**
	 * Devuelve la cantidad de columnas del b&uacute;ffer de la capa Raster
	 * 
	 * @return cantidad de columnas
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWidth()
	 */
	@Override
	public int getWidth() {
		return controller.getWidth();
	}

	/**
	 * Devuelve la cantidad de filas del b&uacute;ffer de la capa Raster
	 * 
	 * @return Cantidad de filas
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getHeight()
	 */
	@Override
	public int getHeight() {
		return controller.getHeight();
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#reProject(ICoordTrans)
	 */
	@Override
	public void reProject(ICoordTrans rp) {
		// TODO Auto-generated method stub
	}

	/**
	 * Establece el extent de la ventana seleccionada
	 * 
	 * @param e
	 *            Extent de la ventana
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#setView(Extent)
	 */
	@Override
	public void setView(Extent e) {
		viewRequest = new Extent(e);
	}

	/**
	 * Obtiene el extent de la &uacute;ltima ventana seleccionada.
	 * 
	 * @return Extent
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getView()
	 */
	@Override
	public Extent getView() {
		return viewRequest;
	}

	/**
	 * Devuelve el valor que contiene el b&uacute;ffer de la capa Raster en una
	 * fila y columna indicada
	 * 
	 * @param x
	 *            columna correspondiente
	 * @param y
	 *            fila correspondiente
	 * @param band
	 *            banda correspondiente (siempre es 0)
	 * 
	 * @return valor
	 * 
	 * @throws InvalidSetViewException
	 * @throws FileNotOpenException
	 * @throws RasterDriverException
	 * 
	 * @see org.gvsig.raster.driver.RasterDataset#getData(int, int, int)
	 */
	@Override
	public Object getData(int x, int y, int band)
			throws InvalidSetViewException, FileNotOpenException,
			RasterDriverException, InterruptedException {
		return controller.getValue(y, x);
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(double,
	 *      double, double, double, BandList, IBuffer)
	 */
	@Override
	public IBuffer getWindowRaster(double ulx, double uly, double lrx,
			double lry, BandList bandList, IBuffer rasterBuf)
			throws InterruptedException, RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(double,
	 *      double, double, double, BandList, IBuffer, boolean)
	 */
	@Override
	public IBuffer getWindowRaster(double x, double y, double w, double h,
			BandList bandList, IBuffer rasterBuf, boolean adjustToExtent)
			throws InterruptedException, RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(double,
	 *      double, double, double, int, int, BandList, IBuffer, boolean)
	 */
	@Override
	public IBuffer getWindowRaster(double minX, double minY, double maxX,
			double maxY, int bufWidth, int bufHeight, BandList bandList,
			IBuffer rasterBuf, boolean adjustToExtent)
			throws InterruptedException, RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(int, int,
	 *      int, int, BandList, IBuffer)
	 */
	@Override
	public IBuffer getWindowRaster(int x, int y, int w, int h,
			BandList bandList, IBuffer rasterBuf) throws InterruptedException,
			RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(int, int,
	 *      int, int, int, int, BandList, IBuffer)
	 */
	@Override
	public IBuffer getWindowRaster(int x, int y, int w, int h, int bufWidth,
			int bufHeight, BandList bandList, IBuffer rasterBuf)
			throws InterruptedException, RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getBlockSize()
	 */
	@Override
	public int getBlockSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#readCompleteLine(int, int)
	 */
	@Override
	public Object readCompleteLine(int line, int band)
			throws InvalidSetViewException, FileNotOpenException,
			RasterDriverException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#readBlock(int, int)
	 */
	@Override
	public Object readBlock(int pos, int blockHeight)
			throws InvalidSetViewException, FileNotOpenException,
			RasterDriverException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getOverviewCount(int)
	 */
	@Override
	public int getOverviewCount(int band) throws BandAccessException,
			RasterDriverException {
		if (band >= getBandCount())
			throw new BandAccessException("Wrong band");
		return 0;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getOverviewWidth(int, int)
	 */
	@Override
	public int getOverviewWidth(int band, int overview)
			throws BandAccessException, RasterDriverException {
		if (band >= getBandCount())
			throw new BandAccessException("Wrong band");
		return 0;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getOverviewHeight(int, int)
	 */
	@Override
	public int getOverviewHeight(int band, int overview)
			throws BandAccessException, RasterDriverException {
		if (band >= getBandCount())
			throw new BandAccessException("Wrong band");
		return 0;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#overviewsSupport()
	 */
	@Override
	public boolean overviewsSupport() {
		return false;
	}

	/**
	 * M&eacute;todo para registrar el data source de la capa raster del NetCDF.
	 */
	public static void registerDriver() {
		ExtensionPoint point = ExtensionPoint.getExtensionPoint("RasterReader");
		point.register("nc", NetCDFRasterDataset.class);
	}

	/**
	 * <p>
	 * Devuelve el controlador del archivo Netcdf.
	 * </p>
	 * 
	 * @return NetCDFController Controlador del archivo NetCDF
	 * 
	 * @see uclv.gvsig.extsdf.NetCDFController
	 */
	public NetCDFController getNetCDFController() {
		return controller;
	}

	/**
	 * <p>
	 * Obtiene todos los atributos globales del archivo NetCDF.
	 * </p>
	 * 
	 * Estos atributos son mostrados como metadatos en las propiedades de la
	 * capa Raster.
	 * 
	 * @return metadatos NetCDF
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getMetadata()
	 */
	public DatasetMetadata getMetadata() {
		DatasetMetadata dmd = new DatasetMetadata(controller.getFileMetadata(),
				getColorInterpretation());
		dmd.initNoDataByBand(1);
		dmd.setNoDataValue(0, controller.getMissing());
		dmd.setNoDataEnabled(true);

		return dmd;
	}

	/**
	 * Obtiene el objeto que contiene el estado de la transparencia
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getTransparencyDatasetStatus()
	 */
	public Transparency getTransparencyDatasetStatus() {
		if (fileTransparency == null)
			fileTransparency = new Transparency();
		return fileTransparency;
	}

	/**
	 * Obtiene el objeto que contiene que contiene la interpretación de color
	 * por banda
	 * 
	 * @return
	 */
	public DatasetColorInterpretation getColorInterpretation() {
		if (colorInterpr == null) {
			colorInterpr = new DatasetColorInterpretation();
			colorInterpr.initColorInterpretation(getBandCount());
			colorInterpr.setColorInterpValue(0,
					DatasetColorInterpretation.PAL_BAND);
		}
		return colorInterpr;
	}

	/**
	 * Asigna el objeto que contiene que contiene la interpretación de color por
	 * banda
	 * 
	 * @param DatasetColorInterpretation
	 */
	public void setColorInterpretation(
			DatasetColorInterpretation colorInterpretation) {
		this.colorInterpretation = colorInterpretation;
	}
}
