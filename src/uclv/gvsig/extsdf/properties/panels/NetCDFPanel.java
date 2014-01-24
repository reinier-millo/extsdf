/*
 * NetCDFPanel.java
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
package uclv.gvsig.extsdf.properties.panels;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;
import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.gui.beans.panelGroup.panels.AbstractPanel;
import org.gvsig.raster.dataset.io.RasterDriverException;

import ucar.nc2.dataset.CoordinateSystem;
import uclv.gvsig.extsdf.NetCDFController;
import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.layers.FLayer;
import javax.swing.border.TitledBorder;

/**
 * Panel de propiedades del <i>NetCDF</i>.
 * 
 * @author dcardoso
 * 
 */

public class NetCDFPanel extends AbstractPanel {
	/**
	 * Atributos
	 */
	FLayer flayer = null;
	NetCDFController controler = null;
	private Logger logger = Logger.getLogger(NetCDFPanel.class);
	private static final long serialVersionUID = 1L;
	private JLabel lblNewLabel;
	private JComboBox sistema_coordenado;
	private JLabel lblNewLabel_1;
	private JComboBox variable;
	private JLabel lblNewLabel_2;
	private JTextField x_dimension;
	private JLabel lblNewLabel_3;
	private JTextField y_dimension;
	private JLabel lblNewLabel_4;
	private JTextField variacion;

	/**
	 * Constructor
	 */
	public NetCDFPanel() {
		super();
		setLabel("NetCDF");
		initialize();
		setPreferredSize(new Dimension(500,450));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		add(getLabel_1(), gbc_lblNewLabel);
		
		GridBagConstraints gbc_sistema_coordenado = new GridBagConstraints();
		gbc_sistema_coordenado.insets = new Insets(0, 0, 5, 0);
		gbc_sistema_coordenado.fill = GridBagConstraints.HORIZONTAL;
		gbc_sistema_coordenado.gridx = 1;
		gbc_sistema_coordenado.gridy = 0;
		add(getSistema_coordenado(), gbc_sistema_coordenado);
		
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		add(getLabel_2(), gbc_lblNewLabel_1);
		
		GridBagConstraints gbc_variable = new GridBagConstraints();
		gbc_variable.insets = new Insets(0, 0, 5, 0);
		gbc_variable.fill = GridBagConstraints.HORIZONTAL;
		gbc_variable.gridx = 1;
		gbc_variable.gridy = 1;
		add(getVariable(), gbc_variable);
		
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		add(getLabel_3(), gbc_lblNewLabel_2);
		
		GridBagConstraints gbc_x_dimension = new GridBagConstraints();
		gbc_x_dimension.insets = new Insets(0, 0, 5, 0);
		gbc_x_dimension.fill = GridBagConstraints.HORIZONTAL;
		gbc_x_dimension.gridx = 1;
		gbc_x_dimension.gridy = 2;
		add(getX_dimension(), gbc_x_dimension);
		
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		add(getLabel_4(), gbc_lblNewLabel_3);
		
		GridBagConstraints gbc_y_dimension = new GridBagConstraints();
		gbc_y_dimension.insets = new Insets(0, 0, 5, 0);
		gbc_y_dimension.fill = GridBagConstraints.HORIZONTAL;
		gbc_y_dimension.gridx = 1;
		gbc_y_dimension.gridy = 3;
		add(getY_dimension(), gbc_y_dimension);
		
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_4.gridx = 0;
		gbc_lblNewLabel_4.gridy = 4;
		add(getLabel_5(), gbc_lblNewLabel_4);
		
		GridBagConstraints gbc_variacion = new GridBagConstraints();
		gbc_variacion.fill = GridBagConstraints.HORIZONTAL;
		gbc_variacion.gridx = 1;
		gbc_variacion.gridy = 4;
		add(getVariacion(), gbc_variacion);
	}

	
	private JLabel getLabel_1() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("New label");
			lblNewLabel.setText(PluginServices.getText(this,
					"sistema_de_coordenada") + ":");
		}
		return lblNewLabel;
	}

	/**
	 * 
	 * @return sistemas de coordenada del NetCDF
	 */
	private JComboBox getSistema_coordenado() {
		if (sistema_coordenado == null) {
			sistema_coordenado = new JComboBox();
//			sistema_coordenado
//					.addItemListener(new Sistema_coordenadoItemListener());
//			sistema_coordenado.setModel(new DefaultComboBoxModel(controler
//					.getCoordinateSystems()));
		}
		return sistema_coordenado;
	}

	private JLabel getLabel_2() {
		if (lblNewLabel_1 == null) {
			lblNewLabel_1 = new JLabel("New label");
			lblNewLabel_1.setText(PluginServices.getText(this, "variable")
					+ ":");
		}
		return lblNewLabel_1;
	}

	private JComboBox getVariable() {
		if (variable == null) {
			variable = new JComboBox();
		}
		return variable;
	}

	private JLabel getLabel_3() {
		if (lblNewLabel_2 == null) {
			lblNewLabel_2 = new JLabel("New label");
			lblNewLabel_2.setText(PluginServices.getText(this, "x_dimension")
					+ ":");
		}
		return lblNewLabel_2;
	}

	private JTextField getX_dimension() {
		if (x_dimension == null) {
			x_dimension = new JTextField();
			x_dimension.setEditable(false);
			x_dimension.setColumns(10);
		}
		return x_dimension;
	}

	private JLabel getLabel_4() {
		if (lblNewLabel_3 == null) {
			lblNewLabel_3 = new JLabel("New label");
			lblNewLabel_3.setText(PluginServices.getText(this, "y_dimension")
					+ ":");
		}
		return lblNewLabel_3;
	}

	private JTextField getY_dimension() {
		if (y_dimension == null) {
			y_dimension = new JTextField();
			y_dimension.setEditable(false);
			y_dimension.setColumns(10);
		}
		return y_dimension;
	}

	private JLabel getLabel_5() {
		if (lblNewLabel_4 == null) {
			lblNewLabel_4 = new JLabel("New label");
			lblNewLabel_4.setText(PluginServices.getText(this, "variacion")
					+ ":");
		}
		return lblNewLabel_4;
	}

	private JTextField getVariacion() {
		if (variacion == null) {
			variacion = new JTextField();
			variacion.setEditable(false);
			variacion.setColumns(10);
		}
		return variacion;
	}

	/**
	 * Maneja los eventos cuando es seleccionado un sistema de corrdenadas.
	 * Muestra la lista de Variables, la x_dimension, y_dimension y el parametro
	 * variable
	 * 
	 * @author dcardoso
	 * 
	 */
	private class Sistema_coordenadoItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			variable.setModel(new DefaultComboBoxModel(
					controler
							.getVariablesForCoordinateSystem((CoordinateSystem) sistema_coordenado
									.getSelectedItem())));
			try {
				x_dimension.setText(controler
						.getLatitudeForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getFullName());
			} catch (RasterDriverException e1) {
				logger.error(e1.getLocalizedMessage());
			}
			try {
				y_dimension.setText(controler
						.getLatitudeForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getFullName());
			} catch (RasterDriverException e1) {
				logger.error(e1.getLocalizedMessage());
			}
			try {
				variacion.setText(controler
						.getParameterForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getFullName());
			} catch (IOException e1) {
				logger.error(e1.getLocalizedMessage());
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#accept()
	 */
	@Override
	public void accept() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#apply()
	 */
	@Override
	public void apply() {
		System.out.println("NetCDF");
		
	}


	/* (non-Javadoc)
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#cancel()
	 */
	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#selected()
	 */
	@Override
	public void selected() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see org.gvsig.gui.beans.panelGroup.panels.AbstractPanel#initialize()
	 */
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub		
	}

	/* (non-Javadoc)
	 * @see org.gvsig.gui.beans.panelGroup.panels.AbstractPanel#setReference(java.lang.Object)
	 */
	@Override
	public void setReference(Object ref) {
		super.setReference(ref);

		if (!(ref instanceof FLayer))
			return;

		FLayer lyr = (FLayer) ref;

		if (lyr instanceof FLyrRasterSE) {
			flayer = lyr;

			// Obtiene el NetCDFControler asociado al layer
			FLyrRasterSE fr = (FLyrRasterSE) flayer;
			NetCDFRasterDataset x = (NetCDFRasterDataset) fr.getDataSource().getDataset(0)[0];
			controler = x.getNetCDFController();
		}
	}

}
