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
	private JComboBox timeZoneComboBox;
	private JCheckBox daylightSavingCheckBox;
	private JLabel lblTimeStepInterval;
	private JLabel lblTimeWindow;
	private JTextField timeStepIntervalTF;
	private JComboBox measureUnitIntervalCB;
	private JButton restoreDefaultBtn;
	private JTextField timeWindowTF;
	private JLabel lblYears;
	private JLabel lblDisplayDateFormat;
	private JLabel lblDisplayTimeFormat;
	private JComboBox dateFormatCB;
	private JComboBox timeFormatCB;

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
		
		GridBagConstraints gbc_timeZoneComboBox = new GridBagConstraints();
		gbc_timeZoneComboBox.gridwidth = 3;
		gbc_timeZoneComboBox.insets = new Insets(5, 5, 5, 5);
		gbc_timeZoneComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeZoneComboBox.gridx = 1;
		gbc_timeZoneComboBox.gridy = 0;
		add(getTimeZoneComboBox(), gbc_timeZoneComboBox);
		
		GridBagConstraints gbc_daylightSavingCheckBox = new GridBagConstraints();
		gbc_daylightSavingCheckBox.gridwidth = 3;
		gbc_daylightSavingCheckBox.insets = new Insets(5, 5, 5, 5);
		gbc_daylightSavingCheckBox.anchor = GridBagConstraints.WEST;
		gbc_daylightSavingCheckBox.gridx = 1;
		gbc_daylightSavingCheckBox.gridy = 1;
		add(getDaylightSavingCheckBox(), gbc_daylightSavingCheckBox);
		
		lblTimeStepInterval = new JLabel(PluginServices.getText(this, "time_step_interval")); //$NON-NLS-1$
		GridBagConstraints gbc_lblTimeStepInterval = new GridBagConstraints();
		gbc_lblTimeStepInterval.anchor = GridBagConstraints.WEST;
		gbc_lblTimeStepInterval.insets = new Insets(5, 5, 5, 5);
		gbc_lblTimeStepInterval.gridx = 0;
		gbc_lblTimeStepInterval.gridy = 2;
		add(lblTimeStepInterval, gbc_lblTimeStepInterval);
		
		GridBagConstraints gbc_timeStepIntervalTF = new GridBagConstraints();
		gbc_timeStepIntervalTF.insets = new Insets(5, 5, 5, 5);
		gbc_timeStepIntervalTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeStepIntervalTF.gridx = 1;
		gbc_timeStepIntervalTF.gridy = 2;
		add(getTimeStepIntervalTF(), gbc_timeStepIntervalTF);
		getTimeStepIntervalTF().setColumns(10);
		
		GridBagConstraints gbc_measureUnitIntervalCB = new GridBagConstraints();
		gbc_measureUnitIntervalCB.insets = new Insets(5, 5, 5, 5);
		gbc_measureUnitIntervalCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_measureUnitIntervalCB.gridx = 2;
		gbc_measureUnitIntervalCB.gridy = 2;
		add(getMeasureUnitIntervalCB(), gbc_measureUnitIntervalCB);
		
		GridBagConstraints gbc_restoreDefaultBtn = new GridBagConstraints();
		gbc_restoreDefaultBtn.insets = new Insets(0, 0, 5, 0);
		gbc_restoreDefaultBtn.gridx = 3;
		gbc_restoreDefaultBtn.gridy = 2;
		add(getRestoreDefaultBtn(), gbc_restoreDefaultBtn);
		
		lblTimeWindow = new JLabel(PluginServices.getText(this, "time_window")); //$NON-NLS-1$
		GridBagConstraints gbc_lblTimeWindow = new GridBagConstraints();
		gbc_lblTimeWindow.anchor = GridBagConstraints.WEST;
		gbc_lblTimeWindow.insets = new Insets(5, 5, 5, 5);
		gbc_lblTimeWindow.gridx = 0;
		gbc_lblTimeWindow.gridy = 3;
		add(lblTimeWindow, gbc_lblTimeWindow);
		
		GridBagConstraints gbc_timeWindowTF = new GridBagConstraints();
		gbc_timeWindowTF.insets = new Insets(5, 5, 5, 5);
		gbc_timeWindowTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeWindowTF.gridx = 1;
		gbc_timeWindowTF.gridy = 3;
		add(getTimeWindowTF(), gbc_timeWindowTF);
		getTimeWindowTF().setColumns(10);
		
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
		
		GridBagConstraints gbc_dateFormatCB = new GridBagConstraints();
		gbc_dateFormatCB.gridwidth = 3;
		gbc_dateFormatCB.insets = new Insets(5, 5, 5, 5);
		gbc_dateFormatCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateFormatCB.gridx = 1;
		gbc_dateFormatCB.gridy = 4;
		add(getDateFormatCB(), gbc_dateFormatCB);
		
		lblDisplayTimeFormat = new JLabel(PluginServices.getText(this, "display_time_format")); //$NON-NLS-1$
		GridBagConstraints gbc_lblDisplayTimeFormat = new GridBagConstraints();
		gbc_lblDisplayTimeFormat.anchor = GridBagConstraints.EAST;
		gbc_lblDisplayTimeFormat.insets = new Insets(5, 5, 5, 5);
		gbc_lblDisplayTimeFormat.gridx = 0;
		gbc_lblDisplayTimeFormat.gridy = 5;
		add(lblDisplayTimeFormat, gbc_lblDisplayTimeFormat);
		
		GridBagConstraints gbc_timeFormatCB = new GridBagConstraints();
		gbc_timeFormatCB.gridwidth = 3;
		gbc_timeFormatCB.insets = new Insets(5, 5, 5, 5);
		gbc_timeFormatCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeFormatCB.gridx = 1;
		gbc_timeFormatCB.gridy = 5;
		add(getTimeFormatCB(), gbc_timeFormatCB);

	}
	
	/**
	 * @return the timeZoneComboBox
	 */
	private JComboBox getTimeZoneComboBox() {
		if(timeZoneComboBox == null) {
			timeZoneComboBox = new JComboBox();
		}
		return timeZoneComboBox;
	}

	/**
	 * @return the daylightSavingCheckBox
	 */
	private JCheckBox getDaylightSavingCheckBox() {
		if(daylightSavingCheckBox == null) {
			daylightSavingCheckBox = new JCheckBox(PluginServices.getText(this, "adjust_for_daylight_saving")); //$NON-NLS-1$
		}
		return daylightSavingCheckBox;
	}

	/**
	 * @return the timeStepIntervalTF
	 */
	private JTextField getTimeStepIntervalTF() {
		if(timeStepIntervalTF == null) {
			timeStepIntervalTF = new JTextField();
		}
		return timeStepIntervalTF;
	}

	/**
	 * @return the measureUnitIntervalCB
	 */
	private JComboBox getMeasureUnitIntervalCB() {
		if(measureUnitIntervalCB == null) {
			measureUnitIntervalCB = new JComboBox();
		}
		return measureUnitIntervalCB;
	}

	/**
	 * @return the restoreDefaultBtn
	 */
	private JButton getRestoreDefaultBtn() {
		if(restoreDefaultBtn == null) {
			restoreDefaultBtn = new JButton(PluginServices.getText(this, "restore_default")); //$NON-NLS-1$
		}
		return restoreDefaultBtn;
	}

	/**
	 * @return the timeWindowTF
	 */
	private JTextField getTimeWindowTF() {
		if(timeWindowTF == null) {
			timeWindowTF = new JTextField();
		}
		return timeWindowTF;
	}

	/**
	 * @return the dateFormatCB
	 */
	private JComboBox getDateFormatCB() {
		if(dateFormatCB == null) {
			dateFormatCB = new JComboBox();
		}
		return dateFormatCB;
	}

	/**
	 * @return the timeFormatCB
	 */
	private JComboBox getTimeFormatCB() {
		if(timeFormatCB == null) {
			timeFormatCB = new JComboBox();
		}
		return timeFormatCB;
	}

}
