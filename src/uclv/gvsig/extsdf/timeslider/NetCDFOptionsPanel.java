/*
 * NetCDFOptionsPanel.java
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

import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.gui.beans.panelGroup.panels.AbstractPanel;

import uclv.gvsig.extsdf.NetCDFConfiguration;
import uclv.gvsig.extsdf.NetCDFController;
import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;

/**
 * @author rmartinez
 *
 */
public abstract class NetCDFOptionsPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected FLyrRasterSE layer;
	protected NetCDFRasterDataset dataset;
	protected NetCDFConfiguration configuration;
	protected NetCDFController controller;
	
	/* (non-Javadoc)
	 * @see org.gvsig.gui.beans.panelGroup.panels.AbstractPanel#setReference(java.lang.Object)
	 */
	@Override
	public void setReference(Object ref) {
		super.setReference(ref);
		layer = (FLyrRasterSE) ref;
		dataset = (NetCDFRasterDataset) layer.getDataSource().getDataset(0)[0];
		configuration = dataset.getConfiguration();
		controller = dataset.getNetCDFController();
		postInitialize();
	}
	
	/**
	 * Código de postinicialización. En este momento ya ha sido pasada
	 * la referencia al panel mediante el método setReference();
	 */
	protected void postInitialize() {}
}
