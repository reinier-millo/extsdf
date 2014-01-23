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
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import org.cresques.cts.ICoordTrans;
import org.cresques.cts.IProjection;
import org.gvsig.raster.dataset.BandAccessException;
import org.gvsig.raster.dataset.BandList;
import org.gvsig.raster.dataset.FileNotOpenException;
import org.gvsig.raster.dataset.GeoInfo;
import org.gvsig.raster.dataset.IBuffer;
import org.gvsig.raster.dataset.InvalidSetViewException;
import org.gvsig.raster.dataset.NotSupportedExtensionException;
import org.gvsig.raster.dataset.RasterDataset;
import org.gvsig.raster.dataset.io.RasterDriverException;
import org.gvsig.raster.dataset.properties.DatasetColorInterpretation;
import org.gvsig.raster.dataset.properties.DatasetMetadata;
import org.gvsig.raster.datastruct.Extent;
import org.gvsig.raster.datastruct.Transparency;
import org.gvsig.raster.process.RasterTask;
import org.gvsig.raster.process.RasterTaskQueue;
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
	 * 
	 * @see uclv.gvsig.extsdf.NetCDFController
	 */
	private NetCDFController controller;

	/**
	 * @see org.gvsig.raster.datastruct.Transparency
	 */
	private Transparency fileTransparency = null;

	/**
	 * @see org.gvsig.raster.datastruct.Extent
	 */
	private Extent viewRequest = null;

	/**
	 * @see org.gvsig.raster.dataset.properties.DatasetColorInterpretation
	 */
	private DatasetColorInterpretation colorInterpr = null;

	/**
	 * Constructor. Abre el dataset.
	 * 
	 * @param proj
	 *            Proyecci&oacute;n
	 * @param fName
	 *            Nombre del fichero ecw
	 * @throws NotSupportedExtensionException
	 */
	public NetCDFRasterDataset(IProjection proj, Object param)
			throws NotSupportedExtensionException {
		super(proj, ((String) param));

		setParam(param);
		try {
			// Verifica que el archivo exista y todo eso
			if (!new File(((String) param)).exists())
				throw new NotSupportedExtensionException(
						"Extension not supported");

			// Inicializa el controlador de archivos NetCDF
			controller = new NetCDFController((String) param);

			// Inicializa la capa y la cantidad de bandas (siempre es 1)
			load();
			bandCount = 1;

			// Inicializa la transparencia de la capa
			getTransparencyDatasetStatus();

			// Establece el tipo de dato según la variable de dato
			int[] dt = new int[bandCount];
			for (int i = 0; i < bandCount; i++)
				dt[i] = controller.getDataType();
			setDataType(dt);
			try {
				loadFromRmf(getRmfBlocksManager());
			} catch (Exception e) {
				// No lee desde rmf
			}
			super.init();
		} catch (Exception e) {
			throw new NotSupportedExtensionException("Extension not supported");
		}
	}

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
			if (controller.getSelectedLat().getCoordValue(0) != min)
				incLat = -incLat;

			// Calcula el incremento de la longitud
			min = controller.getSelectedLon().getMinValue();
			double incLon = Math.abs(min
					- controller.getSelectedLon().getMaxValue())
					/ (controller.getSelectedLon().getSize() - 1);
			// Verifica si las latitudes aumentan o decrementean
			if (controller.getSelectedLon().getCoordValue(0) != min)
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
	 * Devuelve el b&uacute;ffer del Raster
	 * 
	 * Esta funci&oacute;n es llamada cuando se muestra el mapa en su
	 * tama&ntilde;o normal o cuando se le aumenta el zoom
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(double,
	 *      double, double, double, org.gvsig.raster.dataset.BandList,
	 *      org.gvsig.raster.dataset.IBuffer)
	 */
	@Override
	public IBuffer getWindowRaster(double ulx, double uly, double lrx,
			double lry, BandList bandList, IBuffer rasterBuf)
			throws InterruptedException, RasterDriverException {
		// Calcula los puntos diagonales del rectángulo representado en la vista
		Point2D p1 = new Point2D.Double(ulx, uly);
		Point2D p2 = new Point2D.Double(lrx, lry);

		// Calula las filas y columnas iniciales y finales de la vista
		int iRow, iCol, eRow, eCol;
		try {
			externalTransformation.inverseTransform(p1, p1);
			externalTransformation.inverseTransform(p2, p2);
			iCol = (int) Math.min(p1.getX(), p2.getX());
			iRow = (int) Math.min(p1.getY(), p2.getY());
			eCol = (int) Math.max(p1.getX(), p2.getX());
			eRow = (int) Math.max(p1.getY(), p2.getY());
			ownTransformation.transform(p1, p1);
			ownTransformation.transform(p2, p2);
		} catch (NoninvertibleTransformException e) {
			throw new RasterDriverException("Noninvertible transform");
		}

		// Establece la vista según las nuevas dimensiones
		Extent selectedExtent = new Extent(p1, p2);
		setView(selectedExtent);

		// Calcula los incrementos por filas y columnas
		double incCol = (double) (eCol - iCol - 1)
				/ (double) rasterBuf.getWidth();
		double incRow = (double) (eRow - iRow - 1)
				/ (double) rasterBuf.getHeight();

		// Carga los datos de la vista
		try {
			controller.readDataToBuffer(rasterBuf, iRow, iCol, incRow, incCol);
		} catch (Exception e) {
			throw new RasterDriverException(
					"Formato de archivo NetCDF no soportado");
		}
		return rasterBuf;
	}

	/**
	 * Devuelve el b&uacute;ffer del Raster
	 * 
	 * <i>No detectada la operaci&oacute;n que la dispara</i>
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(double,
	 *      double, double, double, BandList, IBuffer, boolean)
	 */
	@Override
	public IBuffer getWindowRaster(double ulx, double uly, double w, double h,
			BandList bandList, IBuffer rasterBuf, boolean adjustToExtent)
			throws InterruptedException, RasterDriverException {
		// Calcula las dimensiones de la capa Raster
		Extent ext = getExtent();
		Point2D pInit = rasterToWorld(new Point2D.Double(0, 0));
		Point2D pEnd = rasterToWorld(new Point2D.Double(getWidth(), getHeight()));
		double wRaster = Math.abs(pEnd.getX() - pInit.getX());
		double hRaster = Math.abs(pEnd.getY() - pInit.getY());
		double lrx = (((ext.getULX() - wRaster) > ext.maxX()) || ((ext.getULX() - wRaster) < ext
				.minX())) ? (ulx + w) : (ulx - w);
		double lry = (((ext.getULY() - hRaster) > ext.maxY()) || ((ext.getULY() - hRaster) < ext
				.minY())) ? (uly + h) : (uly - h);

		// Calcula los puntos diagonales del rectángulo representado en la vista
		Point2D p1 = new Point2D.Double(ulx, uly);
		Point2D p2 = new Point2D.Double(lrx, lry);

		// Calula las filas y columnas iniciales y finales de la vista
		int iRow, iCol, eRow, eCol;
		try {
			externalTransformation.inverseTransform(p1, p1);
			externalTransformation.inverseTransform(p2, p2);
			iCol = (int) Math.min(p1.getX(), p2.getX());
			iRow = (int) Math.min(p1.getY(), p2.getY());
			eCol = (int) Math.max(p1.getX(), p2.getX());
			eRow = (int) Math.max(p1.getY(), p2.getY());
			ownTransformation.transform(p1, p1);
			ownTransformation.transform(p2, p2);
		} catch (NoninvertibleTransformException e) {
			throw new RasterDriverException("Noninvertible transform");
		}

		// Establece la vista según las nuevas dimensiones
		Extent selectedExtent = new Extent(p1.getX(), p1.getY(), p2.getX(),
				p2.getY());
		setView(selectedExtent);

		// Calcula los incrementos por filas y columnas
		double incCol = (double) (eCol - iCol - 1)
				/ (double) rasterBuf.getWidth();
		double incRow = (double) (eRow - iRow - 1)
				/ (double) rasterBuf.getHeight();

		// Carga los datos de la vista
		try {
			controller.readDataToBuffer(rasterBuf, iRow, iCol, incRow, incCol);
		} catch (Exception e) {
			throw new RasterDriverException(
					"Formato de archivo NetCDF no soportado");
		}
		return rasterBuf;
	}

	/**
	 * Devuelve el b&uacute;ffer del Raster
	 * 
	 * Este función es llamada cuando se muestra el mapa en su tama&ntilde;o
	 * peque&ntilde;o, o sea, cuando se le disminuye el zoom.
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(double,
	 *      double, double, double, int, int, BandList, IBuffer, boolean)
	 */
	@Override
	public IBuffer getWindowRaster(double ulx, double uly, double lrx,
			double lry, int bufWidth, int bufHeight, BandList bandList,
			IBuffer rasterBuf, boolean adjustToExtent)
			throws InterruptedException, RasterDriverException {
		// Calcula los puntos diagonales del rectángulo representado en la vista
		Point2D p1 = new Point2D.Double(ulx, uly);
		Point2D p2 = new Point2D.Double(lrx, lry);

		// Calula las filas y columnas iniciales y finales de la vista
		int iRow, iCol, eRow, eCol;
		try {
			externalTransformation.inverseTransform(p1, p1);
			externalTransformation.inverseTransform(p2, p2);
			iCol = (int) Math.min(p1.getX(), p2.getX());
			iRow = (int) Math.min(p1.getY(), p2.getY());
			eCol = (int) Math.max(p1.getX(), p2.getX());
			eRow = (int) Math.max(p1.getY(), p2.getY());
			ownTransformation.transform(p1, p1);
			ownTransformation.transform(p2, p2);
		} catch (NoninvertibleTransformException e) {
			throw new RasterDriverException("Noninvertible transform");
		}

		// Establece la vista según las nuevas dimensiones
		Extent selectedExtent = new Extent(p1, p2);
		setView(selectedExtent);

		// Calcula los incrementos por filas y columnas
		double incCol = (double) (eCol - iCol - 1)
				/ (double) rasterBuf.getWidth();
		double incRow = (double) (eRow - iRow - 1)
				/ (double) rasterBuf.getHeight();

		// Carga los datos de la vista
		try {
			controller.readDataToBuffer(rasterBuf, iRow, iCol, incRow, incCol);
		} catch (Exception e) {
			throw new RasterDriverException(
					"Formato de archivo NetCDF no soportado");
		}
		return rasterBuf;
	}

	/**
	 * Devuelve el b&uacute;ffer del Raster
	 * 
	 * <i>No detectada la operaci&oacute;n que la dispara</i>
	 * 
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(int, int,
	 *      int, int, BandList, IBuffer)
	 */
	@Override
	public IBuffer getWindowRaster(int x, int y, int w, int h,
			BandList bandList, IBuffer rasterBuf) throws InterruptedException,
			RasterDriverException {
		// Calcula los puntos diagonales del rectángulo representado en la vista
		Point2D init = this.rasterToWorld(new Point2D.Double(x, y));
		Point2D end = this.rasterToWorld(new Point2D.Double(x + w, y + h));

		// Calula las filas y columnas iniciales y finales de la vista
		int iRow, iCol, eRow, eCol;
		try {
			externalTransformation.inverseTransform(init, init);
			externalTransformation.inverseTransform(end, end);
			iCol = (int) Math.min(init.getX(), end.getX());
			iRow = (int) Math.min(init.getY(), end.getY());
			eCol = (int) Math.max(init.getX(), end.getX());
			eRow = (int) Math.max(init.getY(), end.getY());
			ownTransformation.transform(init, init);
			ownTransformation.transform(end, end);
		} catch (NoninvertibleTransformException e) {
			throw new RasterDriverException("Noninvertible transform");
		}

		// Establece la vista según las nuevas dimensiones
		Extent selectedExtent = new Extent(init.getX(), init.getY(),
				end.getX(), end.getY());
		setView(selectedExtent);

		// Calcula los incrementos por filas y columnas
		double incCol = (double) (eCol - iCol - 1)
				/ (double) rasterBuf.getWidth();
		double incRow = (double) (eRow - iRow - 1)
				/ (double) rasterBuf.getHeight();

		// Carga los datos de la vista
		try {
			controller.readDataToBuffer(rasterBuf, iRow, iCol, incRow, incCol);
		} catch (Exception e) {
			throw new RasterDriverException(
					"Formato de archivo NetCDF no soportado");
		}
		return rasterBuf;
	}

	/**
	 * Devuelve el b&uacute;ffer del Raster
	 * 
	 * <i>No detectada la operaci&oacute;n que la dispara</i>
	 *   
	 * @see org.gvsig.raster.dataset.RasterDataset#getWindowRaster(int, int,
	 *      int, int, int, int, BandList, IBuffer)
	 */
	@Override
	public IBuffer getWindowRaster(int x, int y, int w, int h, int bufWidth,
			int bufHeight, BandList bandList, IBuffer rasterBuf)
			throws InterruptedException, RasterDriverException {
		// Calcula los puntos diagonales del rectángulo representado en la vista
		Point2D init = this.rasterToWorld(new Point2D.Double(x, y));
		Point2D end = this.rasterToWorld(new Point2D.Double(x + w, y + h));

		// Calula las filas y columnas iniciales y finales de la vista
		int iRow, iCol, eRow, eCol;
		try {
			externalTransformation.inverseTransform(init, init);
			externalTransformation.inverseTransform(end, end);
			iCol = (int) Math.min(init.getX(), end.getX());
			iRow = (int) Math.min(init.getY(), end.getY());
			eCol = (int) Math.max(init.getX(), end.getX());
			eRow = (int) Math.max(init.getY(), end.getY());
			ownTransformation.transform(init, init);
			ownTransformation.transform(end, end);
		} catch (NoninvertibleTransformException e) {
			throw new RasterDriverException("Noninvertible transform");
		}

		// Establece la vista según las nuevas dimensiones
		Extent selectedExtent = new Extent(init.getX(), init.getY(),
				end.getX(), end.getY());
		setView(selectedExtent);

		// Calcula los incrementos por filas y columnas
		double incCol = (double) (eCol - iCol - 1)
				/ (double) rasterBuf.getWidth();
		double incRow = (double) (eRow - iRow - 1)
				/ (double) rasterBuf.getHeight();

		// Carga los datos de la vista
		try {
			controller.readDataToBuffer(rasterBuf, iRow, iCol, incRow, incCol);
		} catch (Exception e) {
			throw new RasterDriverException(
					"Formato de archivo NetCDF no soportado");
		}
		return rasterBuf;
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
		// Verifica que la línea se válida
		if (line == controller.getHeight() || band == getBandCount())
			throw new InvalidSetViewException("Request out of grid");

		// Lee una línea en dependencia del tipo de datos
		switch (controller.getDataType()) {
		case IBuffer.TYPE_DOUBLE:
			return controller.readLineDouble(line);
		case IBuffer.TYPE_FLOAT:
			return controller.readLineFloat(line);
		case IBuffer.TYPE_INT:
			return controller.readLineInteger(line);
		case IBuffer.TYPE_SHORT:
			return controller.readLineShort(line);
		default:
			return controller.readLineByte(line);
		}
	}

	/**
	 * Devuelve un subconjunto de filas del archivo de datos
	 * 
	 * @param pos
	 *            fila inicial
	 * @param blockHeight
	 *            cantidad de filas a leer
	 * 
	 * @return datos del archivo NetCDF
	 * 
	 * @throws InvalidSetViewException
	 * @throws FileNotOpenException
	 * @throws RasterDriverException
	 * @throws InterruptedException
	 * @see org.gvsig.raster.dataset.RasterDataset#readBlock(int, int)
	 */
	@Override
	public Object readBlock(int pos, int blockHeight)
			throws InvalidSetViewException, FileNotOpenException,
			RasterDriverException, InterruptedException {
		RasterTask task = RasterTaskQueue
				.get(Thread.currentThread().toString());

		try {
			controller.calculaMinMax();
		} catch (Exception e) {
			new RasterDriverException("Formato de archivo NetCDF no soportado");
		}

		// Verifica que la posición sea válida
		if (pos < 0)
			throw new InvalidSetViewException("Request out of grid");

		// Verifica que la cantidad de filas a leer sea válida
		if ((pos + blockHeight) > getHeight())
			blockHeight = Math.abs(getHeight() - pos);

		// Lee los datos en dependencia del tipo de dato del NetCDF
		switch (controller.getDataType()) {
		// Datos tipo byte
		case IBuffer.TYPE_BYTE:
			if (task.getEvent() != null)
				task.manageEvent(task.getEvent());
			return controller.readBlockByte(pos, blockHeight);
			// Datos tipo short
		case IBuffer.TYPE_SHORT:
			if (task.getEvent() != null)
				task.manageEvent(task.getEvent());
			return controller.readBlockShort(pos, blockHeight);
			// Datos tipo int
		case IBuffer.TYPE_INT:
			if (task.getEvent() != null)
				task.manageEvent(task.getEvent());
			return controller.readBlockInteger(pos, blockHeight);
			// Datos tipo float
		case IBuffer.TYPE_FLOAT:
			if (task.getEvent() != null)
				task.manageEvent(task.getEvent());
			return controller.readBlockFloat(pos, blockHeight);
			// Datos tipo double
		default:
			if (task.getEvent() != null)
				task.manageEvent(task.getEvent());
			return controller.readBlockDouble(pos, blockHeight);
		}
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
