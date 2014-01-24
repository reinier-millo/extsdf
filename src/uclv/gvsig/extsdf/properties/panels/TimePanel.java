/*
 * TimerPanel.java
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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.DateFormatter;

import org.apache.log4j.Logger;
import org.gvsig.fmap.raster.layers.FLyrRasterSE;
import org.gvsig.gui.beans.panelGroup.panels.AbstractPanel;
import org.gvsig.raster.dataset.io.RasterDriverException;

import uclv.gvsig.extsdf.NetCDFController;
import uclv.gvsig.extsdf.raster.NetCDFRasterDataset;

import com.iver.andami.PluginServices;
import com.iver.cit.gvsig.fmap.layers.FLayer;

import javax.swing.JCheckBox;

import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

/**
 * Panel para el tiempo del NetCDF
 * 
 * @author dcardoso
 * 
 */
public class TimePanel extends AbstractPanel {
	/**
	 * Atributos
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	FLayer flayer = null;
	/**
	 * Objeto que maneja el NetCDF
	 */
	NetCDFController controler = null;
	/**
	 * 
	 */
	private Logger logger = Logger.getLogger(NetCDFPanel.class);
	/**
	 * Componentes visuales
	 */
	private JPanel panel;
	private JLabel label;
	private JComboBox comboBox;
	private JLabel label_1;
	private JTextField textField;
	private JComboBox comboBox_1;
	private JPanel panel_1;
	private JLabel label_2;
	private JComboBox comboBox_2;
	private JLabel label_3;
	private JLabel label_4;
	private JComboBox comboBox_3;
	private JLabel label_5;
	private JTextField textField_2;
	private JComboBox comboBox_4;
	private JLabel label_6;
	private JTextField textField_3;
	private JLabel label_7;
	private JTextField textField_4;
	private JButton button;
	private JList list;
	private JCheckBox chckbxNewCheckBox;

	public TimePanel() {
		super();
		setLabel("Time");
		setPreferredSize(new Dimension(500,450));
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 396, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 185, 78, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 0;
		add(getChckbxNewCheckBox(), gbc_chckbxNewCheckBox);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.anchor = GridBagConstraints.NORTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		add(getPanel_1(), gbc_panel_1);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 2;
		add(getPanel(), gbc_panel);
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
		// TODO Auto-generated method stub
		System.out.println("Time");
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.gvsig.gui.beans.panelGroup.panels.AbstractPanel#setReference(java
	 * .lang.Object)
	 */
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
		}
	}

	/**
	 * 
	 * @return
	 */
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBorder(new TitledBorder(null, PluginServices.getText(this,
					"advanced_settings"), TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 0, 0, 0, 0 };
			gbl_panel.rowHeights = new int[] { 0, 0, 0 };
			gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0,
					Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			panel.setLayout(gbl_panel);
			GridBagConstraints gbc_label = new GridBagConstraints();
			gbc_label.anchor = GridBagConstraints.WEST;
			gbc_label.insets = new Insets(0, 0, 5, 5);
			gbc_label.gridx = 0;
			gbc_label.gridy = 0;
			panel.add(getLabel_1(), gbc_label);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridwidth = 2;
			gbc_comboBox.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox.gridx = 1;
			gbc_comboBox.gridy = 0;
			panel.add(getComboBox(), gbc_comboBox);
			GridBagConstraints gbc_label_1 = new GridBagConstraints();
			gbc_label_1.anchor = GridBagConstraints.WEST;
			gbc_label_1.insets = new Insets(0, 0, 0, 5);
			gbc_label_1.gridx = 0;
			gbc_label_1.gridy = 1;
			panel.add(getLabel_1_1(), gbc_label_1);
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.insets = new Insets(0, 0, 0, 5);
			gbc_textField.gridx = 1;
			gbc_textField.gridy = 1;
			panel.add(getTextField(), gbc_textField);
			GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
			gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_1.gridx = 2;
			gbc_comboBox_1.gridy = 1;
			panel.add(getComboBox_1(), gbc_comboBox_1);
			Component[] componets = panel.getComponents();
			for (Component component : componets) {
				component.setEnabled(false);
			}
		}
		return panel;
	}

	/**
	 * 
	 * @return
	 */
	private JLabel getLabel_1() {
		if (label == null) {
			label = new JLabel("New label");
			label.setText(PluginServices.getText(this, "time_zone") + ":");
		}
		return label;
	}

	/**
	 * 
	 * @return
	 */
	private JComboBox getComboBox() {
		if (comboBox == null) {
			comboBox = new JComboBox();
		}
		return comboBox;
	}

	/**
	 * 
	 * @return
	 */
	private JLabel getLabel_1_1() {
		if (label_1 == null) {
			label_1 = new JLabel("New label");
			label_1.setText(PluginServices.getText(this, "time_offset") + ":");
		}
		return label_1;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTextField() {
		if (textField == null) {
			textField = new JTextField();
			textField.setColumns(10);
		}
		return textField;
	}

	/**
	 * 
	 * @return
	 */
	private JComboBox getComboBox_1() {
		if (comboBox_1 == null) {
			comboBox_1 = new JComboBox();
		}
		return comboBox_1;
	}

	/**
	 * 
	 * @return
	 */
	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			panel_1.setBorder(new TitledBorder(null, PluginServices.getText(
					this, "time_properties"), TitledBorder.LEADING,
					TitledBorder.TOP, null, null));
			GridBagLayout gbl_panel_1 = new GridBagLayout();
			gbl_panel_1.columnWidths = new int[] { 0, 0, 0, 0, 0 };
			gbl_panel_1.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
			gbl_panel_1.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0,
					Double.MIN_VALUE };
			gbl_panel_1.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0,
					0.0, Double.MIN_VALUE };
			panel_1.setLayout(gbl_panel_1);
			GridBagConstraints gbc_label_2 = new GridBagConstraints();
			gbc_label_2.anchor = GridBagConstraints.WEST;
			gbc_label_2.insets = new Insets(0, 0, 5, 5);
			gbc_label_2.gridx = 0;
			gbc_label_2.gridy = 0;
			panel_1.add(getLabel_2(), gbc_label_2);
			GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
			gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_2.gridwidth = 3;
			gbc_comboBox_2.insets = new Insets(0, 0, 5, 0);
			gbc_comboBox_2.gridx = 1;
			gbc_comboBox_2.gridy = 0;
			panel_1.add(getComboBox_2(), gbc_comboBox_2);
			GridBagConstraints gbc_label_3 = new GridBagConstraints();
			gbc_label_3.anchor = GridBagConstraints.WEST;
			gbc_label_3.insets = new Insets(0, 0, 5, 5);
			gbc_label_3.gridx = 0;
			gbc_label_3.gridy = 1;
			panel_1.add(getLabel_3(), gbc_label_3);
			GridBagConstraints gbc_list = new GridBagConstraints();
			gbc_list.gridwidth = 2;
			gbc_list.insets = new Insets(0, 0, 5, 5);
			gbc_list.fill = GridBagConstraints.BOTH;
			gbc_list.gridx = 1;
			gbc_list.gridy = 1;
			panel_1.add(getList(), gbc_list);
			GridBagConstraints gbc_label_4 = new GridBagConstraints();
			gbc_label_4.anchor = GridBagConstraints.WEST;
			gbc_label_4.insets = new Insets(0, 0, 5, 5);
			gbc_label_4.gridx = 0;
			gbc_label_4.gridy = 2;
			panel_1.add(getLabel_4(), gbc_label_4);
			GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
			gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_3.gridwidth = 2;
			gbc_comboBox_3.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_3.gridx = 1;
			gbc_comboBox_3.gridy = 2;
			panel_1.add(getComboBox_3(), gbc_comboBox_3);
			GridBagConstraints gbc_label_5 = new GridBagConstraints();
			gbc_label_5.anchor = GridBagConstraints.WEST;
			gbc_label_5.insets = new Insets(0, 0, 5, 5);
			gbc_label_5.gridx = 0;
			gbc_label_5.gridy = 3;
			panel_1.add(getLabel_5(), gbc_label_5);
			GridBagConstraints gbc_textField_2 = new GridBagConstraints();
			gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_2.insets = new Insets(0, 0, 5, 5);
			gbc_textField_2.gridx = 1;
			gbc_textField_2.gridy = 3;
			panel_1.add(getTextField_2(), gbc_textField_2);
			GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
			gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox_4.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox_4.gridx = 2;
			gbc_comboBox_4.gridy = 3;
			panel_1.add(getComboBox_4(), gbc_comboBox_4);
			GridBagConstraints gbc_label_6 = new GridBagConstraints();
			gbc_label_6.anchor = GridBagConstraints.WEST;
			gbc_label_6.insets = new Insets(0, 0, 5, 5);
			gbc_label_6.gridx = 0;
			gbc_label_6.gridy = 4;
			panel_1.add(getLabel_6(), gbc_label_6);
			GridBagConstraints gbc_textField_3 = new GridBagConstraints();
			gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_3.insets = new Insets(0, 0, 5, 5);
			gbc_textField_3.gridx = 1;
			gbc_textField_3.gridy = 4;
			panel_1.add(getTextField_3(), gbc_textField_3);
			GridBagConstraints gbc_label_7 = new GridBagConstraints();
			gbc_label_7.insets = new Insets(0, 0, 5, 5);
			gbc_label_7.gridx = 2;
			gbc_label_7.gridy = 4;
			panel_1.add(getLabel_7(), gbc_label_7);
			GridBagConstraints gbc_textField_4 = new GridBagConstraints();
			gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField_4.insets = new Insets(0, 0, 5, 0);
			gbc_textField_4.gridx = 3;
			gbc_textField_4.gridy = 4;
			panel_1.add(getTextField_4(), gbc_textField_4);
			GridBagConstraints gbc_button = new GridBagConstraints();
			gbc_button.gridx = 3;
			gbc_button.gridy = 5;
			panel_1.add(getButton(), gbc_button);
			Component[] componets = panel_1.getComponents();
			for (Component component : componets) {
				component.setEnabled(false);
			}
		}

		return panel_1;
	}

	/**
	 * 
	 * @return
	 */
	private JLabel getLabel_2() {
		if (label_2 == null) {
			label_2 = new JLabel("New label");
			label_2.setText(PluginServices.getText(this, "layer_time")+":");
		}
		return label_2;
	}

	/**
	 * 
	 * @return
	 */
	private JComboBox getComboBox_2() {
		if (comboBox_2 == null) {
			comboBox_2 = new JComboBox();
		}
		return comboBox_2;
	}

	/**
	 * 
	 * @return
	 */
	private JLabel getLabel_3() {
		if (label_3 == null) {
			label_3 = new JLabel("New label");
			label_3.setText(PluginServices.getText(this, "time_dimension")
					+ ":");
		}
		return label_3;
	}

	/**
	 * 
	 * @return
	 */
	private JLabel getLabel_4() {
		if (label_4 == null) {
			label_4 = new JLabel("New label");
			label_4.setText(PluginServices.getText(this, "field_format") + ":");
		}
		return label_4;
	}

	/**
	 * 
	 * @return
	 */
	private JComboBox getComboBox_3() {
		if (comboBox_3 == null) {
			comboBox_3 = new JComboBox();
		}
		return comboBox_3;
	}

	/**
	 * 
	 * @return
	 */
	private JLabel getLabel_5() {
		if (label_5 == null) {
			label_5 = new JLabel("New label");
			label_5.setText(PluginServices.getText(this, "time_step_interval")
					+ ":");
		}
		return label_5;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTextField_2() {
		if (textField_2 == null) {
			textField_2 = new JTextField();
			textField_2.setColumns(10);
		}
		return textField_2;
	}

	/**
	 * 
	 * @return
	 */
	private JComboBox getComboBox_4() {
		if (comboBox_4 == null) {
			comboBox_4 = new JComboBox();
		}
		return comboBox_4;
	}

	/**
	 * 
	 * @return
	 */
	private JLabel getLabel_6() {
		if (label_6 == null) {
			label_6 = new JLabel("New label");
			label_6.setText(PluginServices.getText(this, "layer_time_extend")
					+ ":");
		}
		return label_6;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTextField_3() {
		if (textField_3 == null) {
			textField_3 = new JTextField();
			textField_3.setColumns(10);
		}
		return textField_3;
	}

	/**
	 * 
	 * @return
	 */
	private JLabel getLabel_7() {
		if (label_7 == null) {
			label_7 = new JLabel("New label");
			label_7.setText(PluginServices.getText(this, "to") + ":");
		}
		return label_7;
	}

	/**
	 * 
	 * @return
	 */
	private JTextField getTextField_4() {
		if (textField_4 == null) {
			textField_4 = new JTextField();
			textField_4.setColumns(10);
		}
		return textField_4;
	}

	/**
	 * 
	 * @return
	 */
	private JButton getButton() {
		if (button == null) {
			button = new JButton("New button");
			button.setText(PluginServices.getText(this, "calculate"));
		}
		return button;
	}

	/**
	 * 
	 * @return
	 */
	private JList getList() {
		if (list == null) {
			list = new JList();
		}
		return list;
	}

	/**
	 * 
	 * @return
	 */
	private JCheckBox getChckbxNewCheckBox() {
		if (chckbxNewCheckBox == null) {
			chckbxNewCheckBox = new JCheckBox("New check box");
			chckbxNewCheckBox
					.addItemListener(new ChckbxNewCheckBoxItemListener());
			chckbxNewCheckBox.setText(PluginServices.getText(this,
					"enable_time_on_this_layer"));
		}
		return chckbxNewCheckBox;
	}

	/**
	 * 
	 * @author dcardoso
	 * 
	 */
	private class ChckbxNewCheckBoxItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			if (chckbxNewCheckBox.isSelected()) {
				comboBox_2.setEnabled(true);
				// si el layer tiene una dimension tiempo se activan las
				// componentes
				if (controler.hasVariableParameter()) {
					comboBox_2.setModel(new DefaultComboBoxModel(
							new String[] { PluginServices.getText(this,
									"layer_has_time_as_a_dimension") }));
					list.setEnabled(true);
					try {
						list.setListData(new String[] { controler
								.getSelectedParameter().getFullName() });
					} catch (RasterDriverException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					comboBox_3.setEnabled(true);
				} else
					comboBox_2.setModel(new DefaultComboBoxModel(
							new String[] { PluginServices.getText(this,
									"layer_has_not_time_as_a_dimension") }));

			} else {
				Component[] componets = panel_1.getComponents();
				for (Component component : componets) {
					component.setEnabled(false);
				}
				componets = panel.getComponents();
				for (Component component : componets) {
					component.setEnabled(false);
				}
			}
		}
	}
}
