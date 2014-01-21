/*
 * TimeExtentOptionsPanel.java
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

import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JButton;

import com.iver.andami.PluginServices;

/**
 * @author rmartinez
 *
 */
public class TimeExtentOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblRestrictFullTime;
	private JComboBox comboBox;
	private JLabel lblStartTime;
	private JTextField textField;
	private JSpinner spinner;
	private JButton btnMinTime;
	private JTextField textField_1;
	private JSpinner spinner_1;
	private JButton btnMaxTime;

	/**
	 * Create the panel.
	 */
	public TimeExtentOptionsPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		lblRestrictFullTime = new JLabel(PluginServices.getText(this, "restrict_full_time_extent")); //$NON-NLS-1$
		GridBagConstraints gbc_lblRestrictFullTime = new GridBagConstraints();
		gbc_lblRestrictFullTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblRestrictFullTime.anchor = GridBagConstraints.WEST;
		gbc_lblRestrictFullTime.gridx = 0;
		gbc_lblRestrictFullTime.gridy = 0;
		add(lblRestrictFullTime, gbc_lblRestrictFullTime);
		
		comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 0;
		add(comboBox, gbc_comboBox);
		
		lblStartTime = new JLabel(PluginServices.getText(this, "start_time")); //$NON-NLS-1$
		GridBagConstraints gbc_lblStartTime = new GridBagConstraints();
		gbc_lblStartTime.anchor = GridBagConstraints.WEST;
		gbc_lblStartTime.insets = new Insets(0, 0, 5, 5);
		gbc_lblStartTime.gridx = 0;
		gbc_lblStartTime.gridy = 1;
		add(lblStartTime, gbc_lblStartTime);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 1;
		add(textField, gbc_textField);
		textField.setColumns(10);
		
		spinner = new JSpinner();
		GridBagConstraints gbc_spinner = new GridBagConstraints();
		gbc_spinner.insets = new Insets(0, 0, 5, 5);
		gbc_spinner.gridx = 2;
		gbc_spinner.gridy = 1;
		add(spinner, gbc_spinner);
		
		btnMinTime = new JButton(PluginServices.getText(this, "min_time")); //$NON-NLS-1$
		GridBagConstraints gbc_btnMinTime = new GridBagConstraints();
		gbc_btnMinTime.insets = new Insets(0, 0, 5, 0);
		gbc_btnMinTime.gridx = 3;
		gbc_btnMinTime.gridy = 1;
		add(btnMinTime, gbc_btnMinTime);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 0, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 2;
		add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		spinner_1 = new JSpinner();
		GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
		gbc_spinner_1.insets = new Insets(0, 0, 0, 5);
		gbc_spinner_1.gridx = 2;
		gbc_spinner_1.gridy = 2;
		add(spinner_1, gbc_spinner_1);
		
		btnMaxTime = new JButton(PluginServices.getText(this, "max_time")); //$NON-NLS-1$
		GridBagConstraints gbc_btnMaxTime = new GridBagConstraints();
		gbc_btnMaxTime.gridx = 3;
		gbc_btnMaxTime.gridy = 2;
		add(btnMaxTime, gbc_btnMaxTime);

	}

}
