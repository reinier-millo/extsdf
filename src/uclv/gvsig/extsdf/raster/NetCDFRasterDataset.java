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


/**
 * Clase para manipular el datasource del raster del NetCDF.
 * */
public class NetCDFRasterDataset extends RasterDataset{

	@Override
	public GeoInfo load() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void reProject(ICoordTrans rp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setView(Extent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Extent getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getData(int x, int y, int band)
			throws InvalidSetViewException, FileNotOpenException,
			RasterDriverException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBuffer getWindowRaster(double ulx, double uly, double lrx,
			double lry, BandList bandList, IBuffer rasterBuf)
			throws InterruptedException, RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBuffer getWindowRaster(double x, double y, double w, double h,
			BandList bandList, IBuffer rasterBuf, boolean adjustToExtent)
			throws InterruptedException, RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBuffer getWindowRaster(double minX, double minY, double maxX,
			double maxY, int bufWidth, int bufHeight, BandList bandList,
			IBuffer rasterBuf, boolean adjustToExtent)
			throws InterruptedException, RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBuffer getWindowRaster(int x, int y, int w, int h,
			BandList bandList, IBuffer rasterBuf) throws InterruptedException,
			RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBuffer getWindowRaster(int x, int y, int w, int h, int bufWidth,
			int bufHeight, BandList bandList, IBuffer rasterBuf)
			throws InterruptedException, RasterDriverException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBlockSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object readCompleteLine(int line, int band)
			throws InvalidSetViewException, FileNotOpenException,
			RasterDriverException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object readBlock(int pos, int blockHeight)
			throws InvalidSetViewException, FileNotOpenException,
			RasterDriverException, InterruptedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getOverviewCount(int band) throws BandAccessException,
			RasterDriverException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOverviewWidth(int band, int overview)
			throws BandAccessException, RasterDriverException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOverviewHeight(int band, int overview)
			throws BandAccessException, RasterDriverException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean overviewsSupport() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * Metodo para registrar el data source de la capa raster del NetCDF.
	 * */
	public static void registerDriver() {
	    ExtensionPoint point = ExtensionPoint.getExtensionPoint("RasterReader");
	    point.register("nc", NetCDFRasterDataset.class);
	  }
}
