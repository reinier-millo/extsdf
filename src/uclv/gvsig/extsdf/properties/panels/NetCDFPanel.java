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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;
import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.gui.beans.panelGroup.panels.AbstractPanel;
import org.gvsig.raster.dataset.io.RasterDriverException;

import ucar.ma2.InvalidRangeException;
import ucar.nc2.Variable;
import ucar.nc2.dataset.CoordinateSystem;
import uclv.gvsig.extsdf.NetCDFConfiguration;
import uclv.gvsig.extsdf.NetCDFController;
import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;
import uclv.gvsig.extsdf.timeslider.DateTimeFormats;
import uclv.gvsig.extsdf.timeslider.TimeSliderWindow;

import com.iver.andami.PluginServices;
import com.iver.andami.ui.mdiManager.IWindow;
import com.iver.cit.gvsig.fmap.layers.FLayer;

/**
 * Panel de propiedades del <i>NetCDF</i>.
 * 
 * @author dcardoso
 */

/**
 * @author dcardoso
 * 
 */
public class NetCDFPanel extends AbstractPanel {

	private FLayer flayer = null;
	private NetCDFController controler = null;
	private Logger logger = Logger.getLogger(NetCDFPanel.class);
	private static final long serialVersionUID = 1L;
	private JLabel lbSistemaDeCoordenada;
	private JComboBox sistema_coordenado;
	private JLabel lbVariable;
	private JComboBox variable;
	private JLabel lbXDimension;
	private JTextField x_dimension;
	private JLabel lbYDimension;
	private JTextField y_dimension;
	private JLabel lbParametroVariable;
	private JTextField parametro_variable;
	private JPanel panel;
	private JPanel paPropiedadesTiempo;
	private JLabel lbLayerTime;
	private JComboBox layer_time;
	private JLabel lbTimeDimension;
	private JLabel lbFieldFormat;
	private JComboBox field_format;
	private JCheckBox chHabilitarTiempo;
	private JLabel lbVisualizarMomento;
	private JPanel paGeneral;
	private JLabel lbHourFormat;
	private JComboBox hour_format;
	private NetCDFConfiguration configuration;
	private NetCDFRasterDataset dataset;
	private JComboBox visualize_moment;
	private JComboBox time_dimension;

	/**
	 * Constructor del panel NetCDF
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
		GridBagConstraints gbc_chHabilitarTiempo = new GridBagConstraints();
		gbc_chHabilitarTiempo.anchor = GridBagConstraints.WEST;
		gbc_chHabilitarTiempo.insets = new Insets(0, 0, 5, 0);
		gbc_chHabilitarTiempo.gridx = 0;
		gbc_chHabilitarTiempo.gridy = 1;
		add(getChHabilitarTiempo(), gbc_chHabilitarTiempo);
		GridBagConstraints gbc_paPropiedadesTiempo = new GridBagConstraints();
		gbc_paPropiedadesTiempo.fill = GridBagConstraints.BOTH;
		gbc_paPropiedadesTiempo.gridx = 0;
		gbc_paPropiedadesTiempo.gridy = 2;
		add(getPaPropiedadesTiempo(), gbc_paPropiedadesTiempo);
	}

	/**
	 * 
	 * @return JPanel
	 */
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 0, 0 };
			gbl_panel.rowHeights = new int[] { 0, 0 };
			gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_paGeneral = new GridBagConstraints();
			gbc_paGeneral.fill = GridBagConstraints.BOTH;
			gbc_paGeneral.gridx = 0;
			gbc_paGeneral.gridy = 0;
			panel.add(getPaGeneral(), gbc_paGeneral);
		}
		return panel;
	}

	/**
	 * 
	 * @return JPanel
	 */
	private JPanel getPaPropiedadesTiempo() {
		if (paPropiedadesTiempo == null) {
			paPropiedadesTiempo = new JPanel();
			paPropiedadesTiempo.setBorder(new TitledBorder(null, PluginServices
					.getText(this, "time_properties"), TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			GridBagLayout gbl_paPropiedadesTiempo = new GridBagLayout();
			gbl_paPropiedadesTiempo.columnWidths = new int[] { 0, 0, 0, 0, 0 };
			gbl_paPropiedadesTiempo.rowHeights = new int[] { 0, 0, 0, 0, 0 };
			gbl_paPropiedadesTiempo.columnWeights = new double[] { 0.0, 1.0,
					1.0, 1.0, Double.MIN_VALUE };
			gbl_paPropiedadesTiempo.rowWeights = new double[] { 0.0, 0.0, 0.0,
					0.0, Double.MIN_VALUE };
			paPropiedadesTiempo.setLayout(gbl_paPropiedadesTiempo);
			GridBagConstraints gbc_lbLayerTime = new GridBagConstraints();
			gbc_lbLayerTime.anchor = GridBagConstraints.WEST;
			gbc_lbLayerTime.insets = new Insets(0, 0, 5, 5);
			gbc_lbLayerTime.gridx = 0;
			gbc_lbLayerTime.gridy = 0;
			paPropiedadesTiempo.add(getLabel_6(), gbc_lbLayerTime);
			GridBagConstraints gbc_layer_time = new GridBagConstraints();
			gbc_layer_time.fill = GridBagConstraints.HORIZONTAL;
			gbc_layer_time.gridwidth = 3;
			gbc_layer_time.insets = new Insets(0, 0, 5, 0);
			gbc_layer_time.gridx = 1;
			gbc_layer_time.gridy = 0;
			paPropiedadesTiempo.add(getLayer_time(), gbc_layer_time);
			GridBagConstraints gbc_lbTimeDimension = new GridBagConstraints();
			gbc_lbTimeDimension.anchor = GridBagConstraints.EAST;
			gbc_lbTimeDimension.insets = new Insets(0, 0, 5, 5);
			gbc_lbTimeDimension.gridx = 0;
			gbc_lbTimeDimension.gridy = 1;
			paPropiedadesTiempo.add(getLabel_1_1(), gbc_lbTimeDimension);
			GridBagConstraints gbc_time_dimension = new GridBagConstraints();
			gbc_time_dimension.gridwidth = 2;
			gbc_time_dimension.insets = new Insets(0, 0, 5, 5);
			gbc_time_dimension.fill = GridBagConstraints.HORIZONTAL;
			gbc_time_dimension.gridx = 1;
			gbc_time_dimension.gridy = 1;
			paPropiedadesTiempo.add(getTime_dimension(), gbc_time_dimension);
			GridBagConstraints gbc_lbFieldFormat = new GridBagConstraints();
			gbc_lbFieldFormat.anchor = GridBagConstraints.WEST;
			gbc_lbFieldFormat.insets = new Insets(0, 0, 5, 5);
			gbc_lbFieldFormat.gridx = 0;
			gbc_lbFieldFormat.gridy = 2;
			paPropiedadesTiempo.add(getLabel_2_1(), gbc_lbFieldFormat);
			GridBagConstraints gbc_field_format = new GridBagConstraints();
			gbc_field_format.fill = GridBagConstraints.HORIZONTAL;
			gbc_field_format.gridwidth = 2;
			gbc_field_format.insets = new Insets(0, 0, 5, 5);
			gbc_field_format.gridx = 1;
			gbc_field_format.gridy = 2;
			paPropiedadesTiempo.add(getField_format(), gbc_field_format);
			GridBagConstraints gbc_lbHourFormat = new GridBagConstraints();
			gbc_lbHourFormat.anchor = GridBagConstraints.WEST;
			gbc_lbHourFormat.insets = new Insets(0, 0, 0, 5);
			gbc_lbHourFormat.gridx = 0;
			gbc_lbHourFormat.gridy = 3;
			paPropiedadesTiempo.add(getLabel_3_2(), gbc_lbHourFormat);
			GridBagConstraints gbc_hour_format = new GridBagConstraints();
			gbc_hour_format.gridwidth = 2;
			gbc_hour_format.insets = new Insets(0, 0, 0, 5);
			gbc_hour_format.fill = GridBagConstraints.HORIZONTAL;
			gbc_hour_format.gridx = 1;
			gbc_hour_format.gridy = 3;
			paPropiedadesTiempo.add(getHour_format(), gbc_hour_format);
			Component[] componet = paPropiedadesTiempo.getComponents();
			for (Component component : componet) {
				component.setEnabled(false);
			}
		}
		return paPropiedadesTiempo;
	}

	/**
	 * 
	 * @return JPanel
	 */
	private JPanel getPaGeneral() {
		if (paGeneral == null) {
			paGeneral = new JPanel();
			paGeneral.setBorder(new TitledBorder(null, PluginServices.getText(
					this, "general"), TitledBorder.LEFT, TitledBorder.TOP,
					null, new Color(59, 59, 59)));
			GridBagLayout gbl_paGeneral = new GridBagLayout();
			gbl_paGeneral.columnWidths = new int[] { 0, 0, 0 };
			gbl_paGeneral.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
			gbl_paGeneral.columnWeights = new double[] { 0.0, 1.0,
					Double.MIN_VALUE };
			gbl_paGeneral.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
					1.0, Double.MIN_VALUE };
			paGeneral.setLayout(gbl_paGeneral);
			GridBagConstraints gbc_lbSistemaDeCoordenada = new GridBagConstraints();
			gbc_lbSistemaDeCoordenada.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lbSistemaDeCoordenada.insets = new Insets(0, 0, 5, 5);
			gbc_lbSistemaDeCoordenada.gridx = 0;
			gbc_lbSistemaDeCoordenada.gridy = 0;
			paGeneral.add(getLabel_1(), gbc_lbSistemaDeCoordenada);
			GridBagConstraints gbc_sistema_coordenado = new GridBagConstraints();
			gbc_sistema_coordenado.fill = GridBagConstraints.HORIZONTAL;
			gbc_sistema_coordenado.insets = new Insets(0, 0, 5, 0);
			gbc_sistema_coordenado.gridx = 1;
			gbc_sistema_coordenado.gridy = 0;
			paGeneral.add(getSistema_coordenado(), gbc_sistema_coordenado);
			GridBagConstraints gbc_lbVariable = new GridBagConstraints();
			gbc_lbVariable.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lbVariable.insets = new Insets(0, 0, 5, 5);
			gbc_lbVariable.gridx = 0;
			gbc_lbVariable.gridy = 1;
			paGeneral.add(getLabel_2(), gbc_lbVariable);
			GridBagConstraints gbc_variable = new GridBagConstraints();
			gbc_variable.fill = GridBagConstraints.HORIZONTAL;
			gbc_variable.insets = new Insets(0, 0, 5, 0);
			gbc_variable.gridx = 1;
			gbc_variable.gridy = 1;
			paGeneral.add(getVariable(), gbc_variable);
			GridBagConstraints gbc_lbXDimension = new GridBagConstraints();
			gbc_lbXDimension.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lbXDimension.insets = new Insets(0, 0, 5, 5);
			gbc_lbXDimension.gridx = 0;
			gbc_lbXDimension.gridy = 2;
			paGeneral.add(getLabel_3(), gbc_lbXDimension);
			GridBagConstraints gbc_x_dimension = new GridBagConstraints();
			gbc_x_dimension.fill = GridBagConstraints.HORIZONTAL;
			gbc_x_dimension.insets = new Insets(0, 0, 5, 0);
			gbc_x_dimension.gridx = 1;
			gbc_x_dimension.gridy = 2;
			paGeneral.add(getX_dimension(), gbc_x_dimension);
			GridBagConstraints gbc_lbYDimension = new GridBagConstraints();
			gbc_lbYDimension.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lbYDimension.insets = new Insets(0, 0, 5, 5);
			gbc_lbYDimension.gridx = 0;
			gbc_lbYDimension.gridy = 3;
			paGeneral.add(getLabel_4(), gbc_lbYDimension);
			GridBagConstraints gbc_y_dimension = new GridBagConstraints();
			gbc_y_dimension.fill = GridBagConstraints.HORIZONTAL;
			gbc_y_dimension.insets = new Insets(0, 0, 5, 0);
			gbc_y_dimension.gridx = 1;
			gbc_y_dimension.gridy = 3;
			paGeneral.add(getY_dimension(), gbc_y_dimension);
			GridBagConstraints gbc_lbParametroVariable = new GridBagConstraints();
			gbc_lbParametroVariable.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			gbc_lbParametroVariable.insets = new Insets(0, 0, 5, 5);
			gbc_lbParametroVariable.gridx = 0;
			gbc_lbParametroVariable.gridy = 4;
			paGeneral.add(getLabel_5(), gbc_lbParametroVariable);
			GridBagConstraints gbc_parametro_variable = new GridBagConstraints();
			gbc_parametro_variable.fill = GridBagConstraints.HORIZONTAL;
			gbc_parametro_variable.insets = new Insets(0, 0, 5, 0);
			gbc_parametro_variable.gridx = 1;
			gbc_parametro_variable.gridy = 4;
			paGeneral.add(getParametro_variable(), gbc_parametro_variable);
			GridBagConstraints gbc_lbVisualizarMomento = new GridBagConstraints();
			gbc_lbVisualizarMomento.anchor = GridBagConstraints.WEST;
			gbc_lbVisualizarMomento.insets = new Insets(0, 0, 0, 5);
			gbc_lbVisualizarMomento.gridx = 0;
			gbc_lbVisualizarMomento.gridy = 5;
			paGeneral.add(getLabel_3_1(), gbc_lbVisualizarMomento);
			GridBagConstraints gbc_visualize_moment = new GridBagConstraints();
			gbc_visualize_moment.fill = GridBagConstraints.HORIZONTAL;
			gbc_visualize_moment.gridx = 1;
			gbc_visualize_moment.gridy = 5;
			paGeneral.add(getVisualize_moment(), gbc_visualize_moment);
		}
		return paGeneral;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_1() {
		if (lbSistemaDeCoordenada == null) {
			lbSistemaDeCoordenada = new JLabel(PluginServices.getText(this,
					"sistema_de_coordenada") + ":");
		}
		return lbSistemaDeCoordenada;
	}

	/**
	 * 
	 * @return JComboBox
	 */
	private JComboBox getSistema_coordenado() {
		if (sistema_coordenado == null) {
			sistema_coordenado = new JComboBox();
			sistema_coordenado
					.addItemListener(new Sistema_coordenadoItemListener());
		}
		return sistema_coordenado;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_2() {
		if (lbVariable == null) {
			lbVariable = new JLabel(PluginServices.getText(this, "variable")
					+ ":");
		}
		return lbVariable;
	}

	/**
	 * 
	 * @return JComboBox
	 */
	private JComboBox getVariable() {
		if (variable == null) {
			variable = new JComboBox();
		}
		return variable;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_3() {
		if (lbXDimension == null) {
			lbXDimension = new JLabel(PluginServices.getText(this,
					"x_dimension") + ":");
		}
		return lbXDimension;
	}

	/**
	 * 
	 * @return JTextField
	 */
	private JTextField getX_dimension() {
		if (x_dimension == null) {
			x_dimension = new JTextField();
			x_dimension.setEditable(false);
			x_dimension.setColumns(10);
		}
		return x_dimension;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_4() {
		if (lbYDimension == null) {
			lbYDimension = new JLabel(PluginServices.getText(this,
					"y_dimension") + ":");
		}
		return lbYDimension;
	}

	/**
	 * 
	 * @return JTextField
	 */
	private JTextField getY_dimension() {
		if (y_dimension == null) {
			y_dimension = new JTextField();
			y_dimension.setEditable(false);
			y_dimension.setColumns(10);
		}
		return y_dimension;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_5() {
		if (lbParametroVariable == null) {
			lbParametroVariable = new JLabel(PluginServices.getText(this,
					"variacion") + ":");
		}
		return lbParametroVariable;
	}

	/**
	 * 
	 * @return JTextField
	 */
	private JTextField getParametro_variable() {
		if (parametro_variable == null) {
			parametro_variable = new JTextField();
			parametro_variable.setEditable(false);
			parametro_variable.setColumns(10);
		}
		return parametro_variable;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_6() {
		if (lbLayerTime == null) {
			lbLayerTime = new JLabel(PluginServices.getText(this, "layer_time")
					+ ":");
		}
		return lbLayerTime;
	}

	/**
	 * 
	 * @return JComboBox
	 */
	private JComboBox getLayer_time() {
		if (layer_time == null) {
			layer_time = new JComboBox();
		}
		return layer_time;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_1_1() {
		if (lbTimeDimension == null) {
			lbTimeDimension = new JLabel(PluginServices.getText(this,
					"time_dimension") + ":");
		}
		return lbTimeDimension;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_2_1() {
		if (lbFieldFormat == null) {
			lbFieldFormat = new JLabel(PluginServices.getText(this,
					"field_format") + ":");
		}
		return lbFieldFormat;
	}

	/**
	 * 
	 * @return JComboBox
	 */
	private JComboBox getField_format() {
		if (field_format == null) {
			field_format = new JComboBox();
		}
		return field_format;
	}

	/**
	 * 
	 * @return JCheckBox
	 */
	private JCheckBox getChHabilitarTiempo() {
		if (chHabilitarTiempo == null) {
			chHabilitarTiempo = new JCheckBox(PluginServices.getText(this,
					"enable_time_on_this_layer"));
			chHabilitarTiempo.addItemListener(new CheckBoxItemListener());
		}
		return chHabilitarTiempo;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_3_1() {
		if (lbVisualizarMomento == null) {
			lbVisualizarMomento = new JLabel(PluginServices.getText(this,
					"visualize_moment") + ":");
		}
		return lbVisualizarMomento;
	}

	/**
	 * 
	 * @return JLabel
	 */
	private JLabel getLabel_3_2() {
		if (lbHourFormat == null) {
			lbHourFormat = new JLabel(PluginServices.getText(this,
					"time_format") + ":");
		}
		return lbHourFormat;
	}

	/**
	 * 
	 * @return JComboBox
	 */
	private JComboBox getHour_format() {
		if (hour_format == null) {
			hour_format = new JComboBox();
		}
		return hour_format;
	}

	/**
	 * 
	 * @return JComboBox
	 */
	private JComboBox getVisualize_moment() {
		if (visualize_moment == null) {
			visualize_moment = new JComboBox();
		}
		return visualize_moment;
	}

	/**
	 * 
	 * @return JComboBox
	 */
	private JComboBox getTime_dimension() {
		if (time_dimension == null) {
			time_dimension = new JComboBox();
		}
		return time_dimension;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#accept()
	 */
	@Override
	public void accept() {
		apply();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gvsig.gui.beans.panelGroup.panels.AbstractPanel#isAlwaysApplicable()
	 */
	public boolean isAlwaysApplicable() {
		return ((sistema_coordenado.getSelectedIndex() != -1)
				&& (variable.getSelectedIndex() != -1) && (visualize_moment
					.getSelectedIndex() != -1));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#apply()
	 */
	@Override
	public void apply() {
		// se carga el instante de tiempo seleccionado
		try {
			controler.setParameter(visualize_moment.getSelectedIndex());
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
		flayer.getMapContext().invalidate();
		// se guardan las configuraciones si se habilito el tiempo
		if (chHabilitarTiempo.isSelected() && controler.hasVariableParameter()) {
			configuration.setEnable(true);
			configuration.setDateformat(field_format.getSelectedIndex());
			configuration.setTimeformat(hour_format.getSelectedIndex());
		} else {
			configuration.setEnable(false);
			// cierra el TimerSlider si estaba abierto y se desabilito el tiempo
			IWindow[] allWindows = PluginServices.getMDIManager()
					.getAllWindows();
			for (IWindow analized : allWindows) {
				if (analized instanceof TimeSliderWindow
						&& ((TimeSliderWindow) analized).getDataset() == dataset) {
					PluginServices.getMDIManager().closeWindow(analized);
				}
			}
		}
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
		// TODO Auto-generated method stub
	}

	/**
	 * Obtiene el objeto FLayer para el NetCDF. Se obtiene tambien el
	 * NetCDFController asociado a este layer
	 * 
	 * @see org.gvsig.gui.beans.panelGroup.panels.AbstractPanel#setReference(java
	 *      .lang.Object)
	 */
	@Override
	public void setReference(Object ref) {
		super.setReference(ref);
		if (!(ref instanceof FLayer))
			return;
		FLayer lyr = (FLayer) ref;
		if (lyr instanceof FLyrRasterSE) {
			flayer = lyr;
			// Obtiene el NetCDFControler asociado al layer y el objeto
			// configuration
			FLyrRasterSE fr = (FLyrRasterSE) flayer;
			dataset = (NetCDFRasterDataset) fr.getDataSource().getDataset(0)[0];
			controler = dataset.getNetCDFController();
			configuration = dataset.getConfiguration();
			init();
		}
	}

	/**
	 * inicializa las componentes del panel general y las propiedades de tiempo
	 * si ya se habian habilitado y configurado
	 */
	private void init() {
		// se muestra la lista de sistemas de coordenadas
		getSistema_coordenado().setModel(
				new DefaultComboBoxModel(controler.getCoordinateSystems()));
		// muestra la lista de variable para el sistema de coordenadas
		// seleccionado
		variable.setModel(new DefaultComboBoxModel(variablesToString()));
		// muestra la X_dimensión para el sistema de coordenadas seleccionado
		try {
			x_dimension.setText(controler.getLatitudeForCoordinateSystem(
					(CoordinateSystem) sistema_coordenado.getSelectedItem())
					.getFullName()
					+ "("
					+ controler.getLatitudeForCoordinateSystem(
							(CoordinateSystem) sistema_coordenado
									.getSelectedItem()).getSize() + ")");
		} catch (RasterDriverException e1) {
			logger.error(e1.getLocalizedMessage());
		}
		// muestra la X_dimensión para el sistema de coordenadas seleccionado
		try {
			y_dimension.setText(controler.getLongitudeForCoordinateSystem(
					(CoordinateSystem) sistema_coordenado.getSelectedItem())
					.getFullName()
					+ "("
					+ controler.getLongitudeForCoordinateSystem(
							(CoordinateSystem) sistema_coordenado
									.getSelectedItem()).getSize() + ")");
		} catch (RasterDriverException e1) {
			logger.error(e1.getLocalizedMessage());
		}
		// muestra el parametro variable para el sistema de coordenadas
		// seleccionado
		try {
			parametro_variable.setText(controler
					.getParameterForCoordinateSystem(
							(CoordinateSystem) sistema_coordenado
									.getSelectedItem()).getFullName()
					+ "("
					+ controler.getParameterForCoordinateSystem(
							(CoordinateSystem) sistema_coordenado
									.getSelectedItem()).getSize() + ")");
		} catch (IOException e1) {
			logger.error(e1.getLocalizedMessage());
		}
		// muestra la lista de momentos posibles a visualizar
		try {
			visualize_moment.setModel(new DefaultComboBoxModel(controler
					.getParameterForCoordinateSystem(
							(CoordinateSystem) sistema_coordenado
									.getSelectedItem()).getTimeDates()));
			visualize_moment.setSelectedIndex(controler.getParameter());
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
		// si se abren las propiedades después de la primera vez se carga la
		// configuracion guardada
		chHabilitarTiempo.setSelected(configuration.getEnabled());
		if (configuration.getEnabled() && controler.hasVariableParameter()) {
			Component[] componet = paPropiedadesTiempo.getComponents();
			for (Component component : componet) {
				if (component instanceof JLabel)
					component.setEnabled(true);
			}
			layer_time.setModel(new DefaultComboBoxModel(
					new String[] { PluginServices.getText(this,
							"layer_has_time_as_a_dimension") }));
			time_dimension.setEnabled(true);
			try {
				time_dimension.setModel(new DefaultComboBoxModel(
						new String[] { controler.getSelectedParameter()
								.getFullName() }));
			} catch (RasterDriverException e1) {
				logger.error(e1.getLocalizedMessage());
			}
			field_format.setEnabled(true);
			field_format.setModel(new DefaultComboBoxModel(
					new DateTimeFormats().getTodayDatesFormat()));
			field_format.setSelectedIndex(configuration.getDateformat());
			hour_format.setEnabled(true);
			hour_format.setModel(new DefaultComboBoxModel(new DateTimeFormats()
					.getTodayHoursFormat()));
			hour_format.setSelectedIndex(configuration.getTimeformat());
		}
	}

	/**
	 * convierte el arreglo de objeto <i>Variable</i> en un arreglo de Cadenas
	 * 
	 * @return la lista de variables para sistema de coordenadas seleccionado
	 */
	private String[] variablesToString() {
		Variable[] variable = controler
				.getVariablesForCoordinateSystem((CoordinateSystem) sistema_coordenado
						.getSelectedItem());
		String[] var = new String[variable.length];
		for (int i = 0; i < variable.length; i++) {
			var[i] = variable[i].getFullName();
		}
		return var;
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
						.getLongitudeForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getFullName()
						+ "("
						+ controler.getLongitudeForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getSize() + ")");
			} catch (RasterDriverException e1) {
				logger.error(e1.getLocalizedMessage());
			}
			try {
				y_dimension.setText(controler
						.getLongitudeForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getFullName()
						+ "("
						+ controler.getLongitudeForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getSize() + ")");
			} catch (RasterDriverException e1) {
				logger.error(e1.getLocalizedMessage());
			}
			try {
				parametro_variable.setText(controler
						.getParameterForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getFullName()
						+ "("
						+ controler.getParameterForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getSize() + ")");
			} catch (IOException e1) {
				logger.error(e1.getLocalizedMessage());
			}
			try {
				visualize_moment.setModel(new DefaultComboBoxModel(controler
						.getParameterForCoordinateSystem(
								(CoordinateSystem) sistema_coordenado
										.getSelectedItem()).getTimeDates()));
			} catch (IOException e1) {
				logger.error(e1.getLocalizedMessage());
			}
		}
	}

	/**
	 * Maneja los eventos cuando se activa el time en la capa
	 * 
	 * @author dcardoso
	 * 
	 */
	private class CheckBoxItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			// si se habilito el time
			if (chHabilitarTiempo.isSelected()) {
				paPropiedadesTiempo.setEnabled(true);
				layer_time.setEnabled(true);
				if (controler.hasVariableParameter()) {
					Component[] componet = paPropiedadesTiempo.getComponents();
					for (Component component : componet) {
						if (component instanceof JLabel)
							component.setEnabled(true);
					}
					layer_time.setModel(new DefaultComboBoxModel(
							new String[] { PluginServices.getText(this,
									"layer_has_time_as_a_dimension") }));
					time_dimension.setEnabled(true);
					try {
						time_dimension.setModel(new DefaultComboBoxModel(
								new String[] { controler.getSelectedParameter()
										.getFullName() }));
					} catch (RasterDriverException e1) {
						logger.error(e1.getLocalizedMessage());
					}
					field_format.setEnabled(true);
					field_format.setModel(new DefaultComboBoxModel(
							new DateTimeFormats().getTodayDatesFormat()));
					hour_format.setEnabled(true);
					hour_format.setModel(new DefaultComboBoxModel(
							new DateTimeFormats().getTodayHoursFormat()));
				} else {
					lbLayerTime.setEnabled(true);
					layer_time.setModel(new DefaultComboBoxModel(
							new String[] { PluginServices.getText(this,
									"layer_has_not_time_as_a_dimension") }));

				}
			} else {
				// si se desabilito el time
				time_dimension.setModel(new DefaultComboBoxModel());
				layer_time.setModel(new DefaultComboBoxModel());
				field_format.setModel(new DefaultComboBoxModel());
				hour_format.setModel(new DefaultComboBoxModel());
				Component[] componet = paPropiedadesTiempo.getComponents();
				for (Component component : componet) {
					component.setEnabled(false);
				}
			}
		}
	}
}
