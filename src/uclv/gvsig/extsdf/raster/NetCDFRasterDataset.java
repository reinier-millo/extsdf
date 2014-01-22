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

import org.cresques.cts.ICoordTrans;
import org.gvsig.raster.dataset.BandAccessException;
import org.gvsig.raster.dataset.BandList;
import org.gvsig.raster.dataset.FileNotOpenException;
import org.gvsig.raster.dataset.GeoInfo;
import org.gvsig.raster.dataset.IBuffer;
import org.gvsig.raster.dataset.InvalidSetViewException;
import org.gvsig.raster.dataset.RasterDataset;
import org.gvsig.raster.dataset.io.RasterDriverException;
import org.gvsig.raster.datastruct.Extent;
import org.gvsig.raster.util.extensionPoints.ExtensionPoint;

import uclv.gvsig.extsdf.NetCDFController;


/**
 * <p>
 * Clase para manipular el datasource de la capa raster del NetCDF.
 * </p>
 * @author
 * @version 1.0.0
 */
public class NetCDFRasterDataset extends RasterDataset{
	/**
	 * Controlador del archivo NetCDF
	 */
	private NetCDFController controller;
	
	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#load()
	 */
	@Override
	public GeoInfo load() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#clone()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWidth()
	 */
	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getHeight()
	 */
	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
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
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#setView(Extent)
	 */
	@Override
	public void setView(Extent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getView()
	 */
	@Override
	public Extent getView() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getData(int, int, int)
	 */
	@Override
	public Object getData(int x, int y, int band)
			throws InvalidSetViewException, FileNotOpenException,
			RasterDriverException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(double, double, double, double, BandList, IBuffer)
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
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(double, double, double, double, BandList, IBuffer, boolean)
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
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(double, double, double, double, int, int, BandList, IBuffer, boolean)
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
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(int, int, int, int, BandList, IBuffer)
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
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(int, int, int, int, int, int, BandList, IBuffer)
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * (non javadoc)
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#overviewsSupport()
	 */
	@Override
	public boolean overviewsSupport() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * M&eacute;todo para registrar el data source de la capa raster del NetCDF.
	 * @author afmoya
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
	 * @return NetCDFController
	 * 
	 * @see uclv.gvsig.extsdf.NetCDFController
	 */
	public NetCDFController getNetCDFController(){
	      return controller;
	  }
}
