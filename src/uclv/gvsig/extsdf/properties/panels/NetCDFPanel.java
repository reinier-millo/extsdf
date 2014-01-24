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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.gui.beans.panelGroup.panels.AbstractPanel;
import org.gvsig.raster.dataset.io.RasterDriverException;

import ucar.nc2.Variable;
import ucar.nc2.dataset.CoordinateSystem;
import uclv.gvsig.extsdf.NetCDFController;
import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;
import uclv.gvsig.extsdf.timeslider.DateTimeFormats;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.layers.FLayer;

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
	private JPanel panel;
	private JPanel panel_1;
	private JLabel label;
	private JComboBox comboBox;
	private JLabel label_1;
	private JList list;
	private JLabel label_2;
	private JComboBox comboBox_1;
	private JCheckBox checkBox;
	private JLabel lblVisualizemoment;
	private JList list_1;
	private JPanel panel_2;
	private JLabel lblHourFormat;
	private JComboBox comboBox_2;

	/**
	 * Constructor
	 */
	public NetCDFPanel() {
		super();
		setLabel("NetCDF");
		initialize();
		setPreferredSize(new Dimension(500, 450));

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 1.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(getPanel(), gbc_panel);
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.anchor = GridBagConstraints.WEST;
		gbc_checkBox.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox.gridx = 0;
		gbc_checkBox.gridy = 1;
		add(getCheckBox(), gbc_checkBox);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 2;
		add(getPanel_1(), gbc_panel_1);
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
			sistema_coordenado
					.addItemListener(new Sistema_coordenadoItemListener());
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

	private class CheckBoxItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (checkBox.isSelected()) {
				comboBox.setEnabled(true);
				if (controler.hasVariableParameter()) {
					Component[] componet = panel_1.getComponents();
					for (Component component : componet) {
						if (component instanceof JLabel)
							component.setEnabled(true);
					}
					comboBox.setModel(new DefaultComboBoxModel(
							new String[] { PluginServices.getText(this,
									"layer_has_time_as_a_dimension") }));
					list.setEnabled(true);
					try {
						list.setListData(new String[] { controler
								.getSelectedParameter().getFullName() });
					} catch (RasterDriverException e1) {
						logger.error(e1.getLocalizedMessage());
					}
					comboBox_1.setEnabled(true);
					comboBox_1.setModel(new DefaultComboBoxModel(
							new DateTimeFormats().getTodayDatesFormat()));
					comboBox_2.setEnabled(true);
					comboBox_2.setModel(new DefaultComboBoxModel(
							new DateTimeFormats().getTodayHoursFormat()));
				} else {
					label.setEnabled(true);
					comboBox.setModel(new DefaultComboBoxModel(
							new String[] { PluginServices.getText(this,
									"layer_has_not_time_as_a_dimension") }));

				}
			} else {
				list.setListData(new String[] {});
				comboBox.setModel(new DefaultComboBoxModel());
				comboBox_1.setModel(new DefaultComboBoxModel());
				comboBox_2.setModel(new DefaultComboBoxModel());
				Component[] componet = panel_1.getComponents();
				for (Component component : componet) {
					component.setEnabled(false);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#accept()
	 */
	@Override
	public void accept() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#apply()
	 */
	@Override
	public void apply() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#cancel()
	 */
	@Override
	public void cancel() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#selected()
	 */
	@Override
	public void selected() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.AbstractPanel#initialize()
	 */
	@Override
	protected void initialize() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gvsig.gui.beans.panelGroup.panels.AbstractPanel#setReference(java
	 * .lang.Object)
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
			NetCDFRasterDataset x = (NetCDFRasterDataset) fr.getDataSource()
					.getDataset(0)[0];
			controler = x.getNetCDFController();
			init();
		}
	}

	/**
	 * 
	 */
	private void init() {
		getSistema_coordenado().setModel(
				new DefaultComboBoxModel(controler.getCoordinateSystems()));
		variable.setModel(new DefaultComboBoxModel(variableToString()));

		try {
			x_dimension.setText(controler.getLatitudeForCoordinateSystem(
					controler.getCoordinateSystems()[0]).getFullName());
		} catch (RasterDriverException e1) {
			logger.error(e1.getLocalizedMessage());
		}
		try {
			y_dimension.setText(controler.getLongitudeForCoordinateSystem(
					controler.getCoordinateSystems()[0]).getFullName());
		} catch (RasterDriverException e1) {
			logger.error(e1.getLocalizedMessage());
		}
		try {
			variacion.setText(controler.getParameterForCoordinateSystem(
					controler.getCoordinateSystems()[0]).getFullName());
		} catch (IOException e1) {
			logger.error(e1.getLocalizedMessage());
		}
	}

	private String[] variableToString() {
		Variable[] variable = controler
				.getVariablesForCoordinateSystem(controler
						.getCoordinateSystems()[0]);
		String[] var = new String[variable.length];
		for (int i = 0; i < variable.length; i++) {
			var[i] = variable[i].getFullName();
		}
		return var;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 0, 0 };
			gbl_panel.rowHeights = new int[] { 0, 0 };
			gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_panel_2 = new GridBagConstraints();
			gbc_panel_2.fill = GridBagConstraints.BOTH;
			gbc_panel_2.gridx = 0;
			gbc_panel_2.gridy = 0;
			panel.add(getPanel_2(), gbc_panel_2);
		}
		return panel;
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(null, PluginServices.getText(
					this, "time_properties"), TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0 };
			gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0 };
			gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0,
					Double.MIN_VALUE };
			gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			panel_1.setLayout(gbl_panel_1);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.WEST;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 0;
			gbc_label.gridy = 0;
			panel_1.add(getLabel_6(), gbc_label);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridwidth = 3;
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 0;
			panel_1.add(getComboBox(), gbc_comboBox);
			GridBagConstraints gbc_label_1 = new GridBagConstraints();
			gbc_label_1.anchor = GridBagConstraints.WEST;
			gbc_label_1.insets = new Insets(0, 0, 5, 5);
			gbc_label_1.gridx = 0;
			gbc_label_1.gridy = 1;
			panel_1.add(getLabel_1_1(), gbc_label_1);
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridwidth = 2;
			gbc_list.insets = new Insets(0, 0, 5, 5);
			gbc_list.gridx = 1;
			gbc_list.gridy = 1;
			panel_1.add(getList(), gbc_list);
			GridBagConstraints gbc_label_2 = new GridBagConstraints();
			gbc_label_2.anchor = GridBagConstraints.WEST;
			gbc_label_2.insets = new Insets(0, 0, 5, 5);
			gbc_label_2.gridx = 0;
			gbc_label_2.gridy = 2;
			panel_1.add(getLabel_2_1(), gbc_label_2);
			GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
			gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_1.gridwidth = 2;
			gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_1.gridx = 1;
			gbc_comboBox_1.gridy = 2;
			panel_1.add(getComboBox_1(), gbc_comboBox_1);
			GridBagConstraints gbc_lblHourFormat = new GridBagConstraints();
			gbc_lblHourFormat.anchor = GridBagConstraints.WEST;
			gbc_lblHourFormat.insets = new Insets(0, 0, 0, 5);
			gbc_lblHourFormat.gridx = 0;
			gbc_lblHourFormat.gridy = 3;
			panel_1.add(getLabel_3_2(), gbc_lblHourFormat);
			GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
			gbc_comboBox_2.gridwidth = 2;
			gbc_comboBox_2.insets = new Insets(0, 0, 0, 5);
			gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_2.gridx = 1;
			gbc_comboBox_2.gridy = 3;
			panel_1.add(getComboBox_2(), gbc_comboBox_2);
			Component[] componet = panel_1.getComponents();
			for (Component component : componet) {
				component.setEnabled(false);
			}
		}
		return panel_1;
	}

	private JLabel getLabel_6() {
		if (label == null) {
			label = new JLabel("layer_time:");
		}
		return label;
	}

	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
		}
		return comboBox;
	}

	private JLabel getLabel_1_1() {
		if (label_1 == null) {
			label_1 = new JLabel("time_dimension:");
		}
		return label_1;
	}

	private JList getList() {
		if (list == null) {
			list = new JList();
		}
		return list;
	}

	private JLabel getLabel_2_1() {
		if (label_2 == null) {
			label_2 = new JLabel("field_format:");
		}
		return label_2;
	}

	private JComboBox getComboBox_1() {
		if (comboBox_1 == null) {
			comboBox_1 = new JComboBox();
		}
		return comboBox_1;
	}

	private JCheckBox getCheckBox() {
		if (checkBox == null) {
			checkBox = new JCheckBox("enable_time_on_this_layer");
			checkBox.addItemListener(new CheckBoxItemListener());
		}
		return checkBox;
	}

	private JLabel getLabel_3_1() {
		if (lblVisualizemoment == null) {
			lblVisualizemoment = new JLabel("visualize_moment");
		}
		return lblVisualizemoment;
	}

	private JList getList_1() {
		if (list_1 == null) {
			list_1 = new JList();
		}
		return list_1;
	}

	private JPanel getPanel_2() {
		if (panel_2 == null) {
			panel_2 = new JPanel();
			panel_2.setBorder(new TitledBorder(null, "general",
					TitledBorder.LEFT, TitledBorder.TOP, null, new Color(59,
							59, 59)));
			GridBagLayout gbl_panel_2 = new GridBagLayout();
			gbl_panel_2.columnWidths = new int[] { 0, 0, 0 };
			gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
			gbl_panel_2.columnWeights = new double[] { 0.0, 1.0,
					Double.MIN_VALUE };
			gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
					1.0, Double.MIN_VALUE };
			panel_2.setLayout(gbl_panel_2);
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 0;
			gbc_lblNewLabel.gridy = 0;
			panel_2.add(getLabel_1(), gbc_lblNewLabel);
			GridBagConstraints gbc_sistema_coordenado = new GridBagConstraints();
			gbc_sistema_coordenado.fill = GridBagConstraints.HORIZONTAL;
			gbc_sistema_coordenado.insets = new Insets(0, 0, 5, 0);
			gbc_sistema_coordenado.gridx = 1;
			gbc_sistema_coordenado.gridy = 0;
			panel_2.add(getSistema_coordenado(), gbc_sistema_coordenado);
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 1;
			panel_2.add(getLabel_2(), gbc_lblNewLabel_1);
			GridBagConstraints gbc_variable = new GridBagConstraints();
			gbc_variable.fill = GridBagConstraints.HORIZONTAL;
			gbc_variable.insets = new Insets(0, 0, 5, 0);
			gbc_variable.gridx = 1;
			gbc_variable.gridy = 1;
			panel_2.add(getVariable(), gbc_variable);
			GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
			gbc_lblNewLabel_2.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_2.gridx = 0;
			gbc_lblNewLabel_2.gridy = 2;
			panel_2.add(getLabel_3(), gbc_lblNewLabel_2);
			GridBagConstraints gbc_x_dimension = new GridBagConstraints();
			gbc_x_dimension.fill = GridBagConstraints.HORIZONTAL;
			gbc_x_dimension.insets = new Insets(0, 0, 5, 0);
			gbc_x_dimension.gridx = 1;
			gbc_x_dimension.gridy = 2;
			panel_2.add(getX_dimension(), gbc_x_dimension);
			GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
			gbc_lblNewLabel_3.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_3.gridx = 0;
			gbc_lblNewLabel_3.gridy = 3;
			panel_2.add(getLabel_4(), gbc_lblNewLabel_3);
			GridBagConstraints gbc_y_dimension = new GridBagConstraints();
			gbc_y_dimension.fill = GridBagConstraints.HORIZONTAL;
			gbc_y_dimension.insets = new Insets(0, 0, 5, 0);
			gbc_y_dimension.gridx = 1;
			gbc_y_dimension.gridy = 3;
			panel_2.add(getY_dimension(), gbc_y_dimension);
			GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
			gbc_lblNewLabel_4.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_4.gridx = 0;
			gbc_lblNewLabel_4.gridy = 4;
			panel_2.add(getLabel_5(), gbc_lblNewLabel_4);
			GridBagConstraints gbc_variacion = new GridBagConstraints();
			gbc_variacion.fill = GridBagConstraints.HORIZONTAL;
			gbc_variacion.insets = new Insets(0, 0, 5, 0);
			gbc_variacion.gridx = 1;
			gbc_variacion.gridy = 4;
			panel_2.add(getVariacion(), gbc_variacion);
			GridBagConstraints gbc_lblVisualizemoment = new GridBagConstraints();
			gbc_lblVisualizemoment.anchor = GridBagConstraints.WEST;
			gbc_lblVisualizemoment.insets = new Insets(0, 0, 0, 5);
			gbc_lblVisualizemoment.gridx = 0;
			gbc_lblVisualizemoment.gridy = 5;
			panel_2.add(getLabel_3_1(), gbc_lblVisualizemoment);
			GridBagConstraints gbc_list_1 = new GridBagConstraints();
			gbc_list_1.fill = GridBagConstraints.BOTH;
			gbc_list_1.gridx = 1;
			gbc_list_1.gridy = 5;
			panel_2.add(getList_1(), gbc_list_1);
		}
		return panel_2;
	}

	private JLabel getLabel_3_2() {
		if (lblHourFormat == null) {
			lblHourFormat = new JLabel("hour format");
		}
		return lblHourFormat;
	}

	private JComboBox getComboBox_2() {
		if (comboBox_2 == null) {
			comboBox_2 = new JComboBox();
		}
		return comboBox_2;
	}
}
