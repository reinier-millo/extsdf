/*
 * TimeDisplayOptionsPanel.java
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

import javax.swing.JPanel;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JComboBox;

import java.awt.Insets;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JButton;

import com.iver.andami.PluginServices;

/**
 * @author rmartinez
 *
 */
public class TimeDisplayOptionsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblTimeZone;
	private JComboBox comboBox;
	private JCheckBox chckbxNewCheckBox;
	private JLabel lblTimeStepInterval;
	private JLabel lblTimeWindow;
	private JTextField textField;
	private JComboBox comboBox_1;
	private JButton btnRestoreDefault;
	private JTextField textField_1;
	private JLabel lblYears;
	private JLabel lblDisplayDateFormat;
	private JLabel lblDisplayTimeFormat;
	private JComboBox comboBox_2;
	private JComboBox comboBox_3;

	/**
	 * Create the panel.
	 */
	public TimeDisplayOptionsPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblTimeZone = new JLabel(PluginServices.getText(this, "time_zone")); //$NON-NLS-1$
		GridBagConstraints gbc_lblTimeZone = new GridBagConstraints();
		gbc_lblTimeZone.insets = new Insets(5, 5, 5, 5);
		gbc_lblTimeZone.anchor = GridBagConstraints.WEST;
		gbc_lblTimeZone.gridx = 0;
		gbc_lblTimeZone.gridy = 0;
		add(lblTimeZone, gbc_lblTimeZone);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.insets = new Insets(5, 5, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		add(comboBox, gbc_comboBox);
		
		chckbxNewCheckBox = new JCheckBox(PluginServices.getText(this, "adjust_for_daylight_saving")); //$NON-NLS-1$
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.gridwidth = 3;
		gbc_chckbxNewCheckBox.insets = new Insets(5, 5, 5, 5);
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.gridx = 1;
		gbc_chckbxNewCheckBox.gridy = 1;
		add(chckbxNewCheckBox, gbc_chckbxNewCheckBox);
		
		lblTimeStepInterval = new JLabel(PluginServices.getText(this, "time_step_interval")); //$NON-NLS-1$
		GridBagConstraints gbc_lblTimeStepInterval = new GridBagConstraints();
		gbc_lblTimeStepInterval.anchor = GridBagConstraints.WEST;
		gbc_lblTimeStepInterval.insets = new Insets(5, 5, 5, 5);
		gbc_lblTimeStepInterval.gridx = 0;
		gbc_lblTimeStepInterval.gridy = 2;
		add(lblTimeStepInterval, gbc_lblTimeStepInterval);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(5, 5, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		comboBox_1 = new JComboBox();
		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.insets = new Insets(5, 5, 5, 5);
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 2;
		add(comboBox_1, gbc_comboBox_1);
		
		btnRestoreDefault = new JButton(PluginServices.getText(this, "restore_default")); //$NON-NLS-1$
		GridBagConstraints gbc_btnRestoreDefault = new GridBagConstraints();
		gbc_btnRestoreDefault.insets = new Insets(0, 0, 5, 0);
		gbc_btnRestoreDefault.gridx = 3;
		gbc_btnRestoreDefault.gridy = 2;
		add(btnRestoreDefault, gbc_btnRestoreDefault);
		
		lblTimeWindow = new JLabel(PluginServices.getText(this, "time_window")); //$NON-NLS-1$
		GridBagConstraints gbc_lblTimeWindow = new GridBagConstraints();
		gbc_lblTimeWindow.anchor = GridBagConstraints.WEST;
		gbc_lblTimeWindow.insets = new Insets(5, 5, 5, 5);
		gbc_lblTimeWindow.gridx = 0;
		gbc_lblTimeWindow.gridy = 3;
		add(lblTimeWindow, gbc_lblTimeWindow);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(5, 5, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 3;
		add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		lblYears = new JLabel(PluginServices.getText(this, "years")); //$NON-NLS-1$
		GridBagConstraints gbc_lblYears = new GridBagConstraints();
		gbc_lblYears.insets = new Insets(5, 5, 5, 5);
		gbc_lblYears.gridx = 2;
		gbc_lblYears.gridy = 3;
		add(lblYears, gbc_lblYears);
		
		lblDisplayDateFormat = new JLabel(PluginServices.getText(this, "display_date_format")); //$NON-NLS-1$
		GridBagConstraints gbc_lblDisplayDateFormat = new GridBagConstraints();
		gbc_lblDisplayDateFormat.anchor = GridBagConstraints.EAST;
		gbc_lblDisplayDateFormat.insets = new Insets(5, 5, 5, 5);
		gbc_lblDisplayDateFormat.gridx = 0;
		gbc_lblDisplayDateFormat.gridy = 4;
		add(lblDisplayDateFormat, gbc_lblDisplayDateFormat);
		
		comboBox_2 = new JComboBox();
		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.gridwidth = 3;
		gbc_comboBox_2.insets = new Insets(5, 5, 5, 5);
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.gridx = 1;
		gbc_comboBox_2.gridy = 4;
		add(comboBox_2, gbc_comboBox_2);
		
		lblDisplayTimeFormat = new JLabel(PluginServices.getText(this, "display_time_format")); //$NON-NLS-1$
		GridBagConstraints gbc_lblDisplayTimeFormat = new GridBagConstraints();
		gbc_lblDisplayTimeFormat.anchor = GridBagConstraints.EAST;
		gbc_lblDisplayTimeFormat.insets = new Insets(5, 5, 5, 5);
		gbc_lblDisplayTimeFormat.gridx = 0;
		gbc_lblDisplayTimeFormat.gridy = 5;
		add(lblDisplayTimeFormat, gbc_lblDisplayTimeFormat);
		
		comboBox_3 = new JComboBox();
		GridBagConstraints gbc_comboBox_3 = new GridBagConstraints();
		gbc_comboBox_3.gridwidth = 3;
		gbc_comboBox_3.insets = new Insets(5, 5, 5, 5);
		gbc_comboBox_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_3.gridx = 1;
		gbc_comboBox_3.gridy = 5;
		add(comboBox_3, gbc_comboBox_3);

	}

}
