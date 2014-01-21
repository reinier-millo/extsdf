
/*
 * TimePanel.java
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

import org.gvsig.gui.beans.panelGroup.panels.AbstractPanel;

import com.iver.andami.PluginServices;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * Panel para el tiempo del NetCDF
 * 
 * @author dcardoso
 * 
 */
public class TimePanel extends AbstractPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public TimePanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 612, 0 };
		gridBagLayout.rowHeights = new int[] { 219, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, PluginServices.getText(this,
				"time_properties"), TitledBorder.LEFT, TitledBorder.TOP, null,
				null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 19, 159, 117, 14, 100, 0, 25, 26, 0 };
		gbl_panel.rowHeights = new int[] { 24, 35, 15, 24, 24, 25, 23, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel(PluginServices.getText(this,
				"layer_time") + ":");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridwidth = 5;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 0;
		panel.add(comboBox, gbc_comboBox);

		JLabel lblNewLabel_1 = new JLabel(PluginServices.getText(this,
				"time_dimension") + ":");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JList list = new JList();
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.fill = GridBagConstraints.BOTH;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.gridwidth = 3;
		gbc_list.gridx = 2;
		gbc_list.gridy = 2;
		panel.add(list, gbc_list);

		JLabel lblNewLabel_2 = new JLabel(PluginServices.getText(this,
				"field_format") + ":");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 3;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

		JComboBox comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.gridwidth = 3;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 3;
		panel.add(comboBox_1, gbc_comboBox_1);

		JLabel lblNewLabel_3 = new JLabel(PluginServices.getText(this,
				"time_step_interval") + ":");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_3.gridx = 1;
		gbc_lblNewLabel_3.gridy = 4;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 4;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);

		JComboBox comboBox_2 = new JComboBox();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.gridwidth = 2;
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.gridx = 3;
		gbc_comboBox_2.gridy = 4;
		panel.add(comboBox_2, gbc_comboBox_2);

		JLabel lblNewLabel_4 = new JLabel(PluginServices.getText(this,
				"layer_time_extend") + ":");
		GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
		gbc_lblNewLabel_4.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_4.gridx = 1;
		gbc_lblNewLabel_4.gridy = 5;
		panel.add(lblNewLabel_4, gbc_lblNewLabel_4);

		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 2;
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 5;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel(PluginServices.getText(this, "to")
				+ ":");
		GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
		gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_5.gridx = 4;
		gbc_lblNewLabel_5.gridy = 5;
		panel.add(lblNewLabel_5, gbc_lblNewLabel_5);

		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 3;
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.insets = new Insets(0, 0, 5, 0);
		gbc_textField_2.gridx = 5;
		gbc_textField_2.gridy = 5;
		panel.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);

		JButton btnNewButton = new JButton(PluginServices.getText(this,
				"calculate"));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.gridwidth = 2;
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.gridx = 6;
		gbc_btnNewButton.gridy = 6;
		panel.add(btnNewButton, gbc_btnNewButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, PluginServices.getText(this,
				"advanced_settings"), TitledBorder.LEFT, TitledBorder.TOP,
				null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{22, 159, 108, 0, 0, 0, 33, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel time_zone = new JLabel(PluginServices.getText(this,
				"time_zone"+":"));
		GridBagConstraints gbc_time_zone = new GridBagConstraints();
		gbc_time_zone.anchor = GridBagConstraints.WEST;
		gbc_time_zone.insets = new Insets(0, 0, 5, 5);
		gbc_time_zone.gridx = 1;
		gbc_time_zone.gridy = 0;
		panel_1.add(time_zone, gbc_time_zone);
		
		JComboBox comboBox_3 = new JComboBox();
		GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
		gbc_comboBox_3.gridwidth = 7;
		gbc_comboBox_3.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3.gridx = 2;
		gbc_comboBox_3.gridy = 0;
		panel_1.add(comboBox_3, gbc_comboBox_3);
		
		JLabel time_offset = new JLabel(PluginServices.getText(this,
				"time_offset"+":"));
		GridBagConstraints gbc_time_offset = new GridBagConstraints();
		gbc_time_offset.anchor = GridBagConstraints.WEST;
		gbc_time_offset.insets = new Insets(0, 0, 0, 5);
		gbc_time_offset.gridx = 1;
		gbc_time_offset.gridy = 2;
		panel_1.add(time_offset, gbc_time_offset);
		
		textField_3 = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 0, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 2;
		gbc_textField_3.gridy = 2;
		panel_1.add(textField_3, gbc_textField_3);
		textField_3.setColumns(10);
		
		JComboBox comboBox_4 = new JComboBox();
		GridBagConstraints gbc_comboBox_4 = new GridBagConstraints();
		gbc_comboBox_4.gridwidth = 4;
		gbc_comboBox_4.insets = new Insets(0, 0, 0, 5);
		gbc_comboBox_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_4.gridx = 3;
		gbc_comboBox_4.gridy = 2;
		panel_1.add(comboBox_4, gbc_comboBox_4);
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

}
