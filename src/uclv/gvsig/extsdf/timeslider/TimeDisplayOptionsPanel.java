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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.TimeZone;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gvsig.gui.beans.panelGroup.panels.AbstractPanel;

import com.iver.andami.PluginServices;

/**
 * @author rmartinez
 *
 */
/**
 * @author rmartinez
 * 
 */
public class TimeDisplayOptionsPanel extends AbstractPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox timeZoneComboBox;
	private JCheckBox daylightSavingCheckBox;
	private JTextField timeStepIntervalTF;
	private JComboBox measureUnitIntervalCB;
	private JButton restoreDefaultBtn;
	private JTextField timeWindowTF;
	private JLabel measureUnitLabel;
	private JComboBox dateFormatCB;
	private JComboBox timeFormatCB;
	private JLabel timeZoneLabel;
	private JLabel timeStepIntervalLabel;
	private JLabel timeWindowLabel;
	private JLabel displayDateFormatLabel;
	private JLabel displayTimeFormatLabel;

	/**
	 * Create the panel.
	 */
	public TimeDisplayOptionsPanel() {
		setLabel("Time Display");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		GridBagConstraints gbc_timeZoneLabel = new GridBagConstraints();
		gbc_timeZoneLabel.insets = new Insets(5, 5, 5, 5);
		gbc_timeZoneLabel.anchor = GridBagConstraints.WEST;
		gbc_timeZoneLabel.gridx = 0;
		gbc_timeZoneLabel.gridy = 0;
		add(getTimeZoneLabel(), gbc_timeZoneLabel);

		GridBagConstraints gbc_timeZoneComboBox = new GridBagConstraints();
		gbc_timeZoneComboBox.gridwidth = 3;
		gbc_timeZoneComboBox.insets = new Insets(5, 5, 5, 0);
		gbc_timeZoneComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeZoneComboBox.gridx = 1;
		gbc_timeZoneComboBox.gridy = 0;
		add(getTimeZoneComboBox(), gbc_timeZoneComboBox);

		GridBagConstraints gbc_daylightSavingCheckBox = new GridBagConstraints();
		gbc_daylightSavingCheckBox.gridwidth = 3;
		gbc_daylightSavingCheckBox.insets = new Insets(5, 5, 5, 0);
		gbc_daylightSavingCheckBox.anchor = GridBagConstraints.WEST;
		gbc_daylightSavingCheckBox.gridx = 1;
		gbc_daylightSavingCheckBox.gridy = 1;
		add(getDaylightSavingCheckBox(), gbc_daylightSavingCheckBox);
		GridBagConstraints gbc_timeStepIntervalLabel = new GridBagConstraints();
		gbc_timeStepIntervalLabel.insets = new Insets(5, 5, 5, 5);
		gbc_timeStepIntervalLabel.anchor = GridBagConstraints.WEST;
		gbc_timeStepIntervalLabel.gridx = 0;
		gbc_timeStepIntervalLabel.gridy = 2;
		add(getTimeStepIntervalLabel(), gbc_timeStepIntervalLabel);

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
		GridBagConstraints gbc_timeWindowLabel = new GridBagConstraints();
		gbc_timeWindowLabel.insets = new Insets(5, 5, 5, 5);
		gbc_timeWindowLabel.anchor = GridBagConstraints.WEST;
		gbc_timeWindowLabel.gridx = 0;
		gbc_timeWindowLabel.gridy = 3;
		add(getTimeWindowLabel(), gbc_timeWindowLabel);

		GridBagConstraints gbc_timeWindowTF = new GridBagConstraints();
		gbc_timeWindowTF.insets = new Insets(5, 5, 5, 5);
		gbc_timeWindowTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeWindowTF.gridx = 1;
		gbc_timeWindowTF.gridy = 3;
		add(getTimeWindowTF(), gbc_timeWindowTF);
		getTimeWindowTF().setColumns(10);

		measureUnitLabel = new JLabel(PluginServices.getText(this, "years")); //$NON-NLS-1$
		GridBagConstraints gbc_measureUnitLabel = new GridBagConstraints();
		gbc_measureUnitLabel.insets = new Insets(5, 5, 5, 5);
		gbc_measureUnitLabel.gridx = 2;
		gbc_measureUnitLabel.gridy = 3;
		add(measureUnitLabel, gbc_measureUnitLabel);
		GridBagConstraints gbc_displayDateFormatLabel = new GridBagConstraints();
		gbc_displayDateFormatLabel.insets = new Insets(5, 5, 5, 5);
		gbc_displayDateFormatLabel.anchor = GridBagConstraints.WEST;
		gbc_displayDateFormatLabel.gridx = 0;
		gbc_displayDateFormatLabel.gridy = 4;
		add(getDisplayDateFormatLabel(), gbc_displayDateFormatLabel);

		GridBagConstraints gbc_dateFormatCB = new GridBagConstraints();
		gbc_dateFormatCB.gridwidth = 3;
		gbc_dateFormatCB.insets = new Insets(5, 5, 5, 0);
		gbc_dateFormatCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateFormatCB.gridx = 1;
		gbc_dateFormatCB.gridy = 4;
		add(getDateFormatCB(), gbc_dateFormatCB);
		GridBagConstraints gbc_displayTimeFormatLabel = new GridBagConstraints();
		gbc_displayTimeFormatLabel.insets = new Insets(5, 5, 0, 5);
		gbc_displayTimeFormatLabel.anchor = GridBagConstraints.WEST;
		gbc_displayTimeFormatLabel.gridx = 0;
		gbc_displayTimeFormatLabel.gridy = 5;
		add(getDisplayTimeFormatLabel(), gbc_displayTimeFormatLabel);

		GridBagConstraints gbc_timeFormatCB = new GridBagConstraints();
		gbc_timeFormatCB.gridwidth = 3;
		gbc_timeFormatCB.insets = new Insets(5, 5, 0, 0);
		gbc_timeFormatCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeFormatCB.gridx = 1;
		gbc_timeFormatCB.gridy = 5;
		add(getTimeFormatCB(), gbc_timeFormatCB);
		setPreferredSize(getPreferredSize());
	}

	/**
	 * @return the timeZoneComboBox
	 */
	private JComboBox getTimeZoneComboBox() {
		if (timeZoneComboBox == null) {
			timeZoneComboBox = new JComboBox(TimeZone.getAvailableIDs());
		}
		return timeZoneComboBox;
	}

	/**
	 * @return the daylightSavingCheckBox
	 */
	private JCheckBox getDaylightSavingCheckBox() {
		if (daylightSavingCheckBox == null) {
			daylightSavingCheckBox = new JCheckBox(PluginServices.getText(this,
					"adjust_for_daylight_saving")); //$NON-NLS-1$
		}
		return daylightSavingCheckBox;
	}

	/**
	 * @return the timeStepIntervalTF
	 */
	private JTextField getTimeStepIntervalTF() {
		if (timeStepIntervalTF == null) {
			timeStepIntervalTF = new JTextField();
		}
		return timeStepIntervalTF;
	}

	/**
	 * @return the measureUnitIntervalCB
	 */
	private JComboBox getMeasureUnitIntervalCB() {
		if (measureUnitIntervalCB == null) {
			measureUnitIntervalCB = new JComboBox();
			measureUnitIntervalCB
					.addItemListener(new MeasureUnitIntervalCBItemListener());
		}
		return measureUnitIntervalCB;
	}

	/**
	 * @return the restoreDefaultBtn
	 */
	private JButton getRestoreDefaultBtn() {
		if (restoreDefaultBtn == null) {
			restoreDefaultBtn = new JButton(PluginServices.getText(this,
					"restore_default")); //$NON-NLS-1$
		}
		return restoreDefaultBtn;
	}

	/**
	 * @return the timeWindowTF
	 */
	private JTextField getTimeWindowTF() {
		if (timeWindowTF == null) {
			timeWindowTF = new JTextField();
		}
		return timeWindowTF;
	}
	
	
//	DateTimeFormats dateFormats = new DateTimeFormats(new String[] {"dd/MM/yyyy"});
	/**
	 * @return the dateFormatCB
	 */
	private JComboBox getDateFormatCB() {
		if (dateFormatCB == null) {
			dateFormatCB = new JComboBox();
//			dateFormatCB.setSelectedIndex(dateFormats.getDefaultFormatIndex());
		}
		return dateFormatCB;
	}
	
//	DateTimeFormats timeFormats = new DateTimeFormats(new String[] {"HH:mm:ss"});

	/**
	 * @return the timeFormatCB
	 */
	private JComboBox getTimeFormatCB() {
		if (timeFormatCB == null) {
			timeFormatCB = new JComboBox();
//			timeFormatCB.setSelectedIndex(timeFormats.getDefaultFormatIndex());
		}
		return timeFormatCB;
	}

	/**
	 * @return the label for the Time Zone option.
	 */
	private JLabel getTimeZoneLabel() {
		if (timeZoneLabel == null) {
			timeZoneLabel = new JLabel("time_zone");
		}
		return timeZoneLabel;
	}

	/**
	 * @return the label for the Time Step Interval option.
	 */
	private JLabel getTimeStepIntervalLabel() {
		if (timeStepIntervalLabel == null) {
			timeStepIntervalLabel = new JLabel("time_step_interval");
		}
		return timeStepIntervalLabel;
	}

	/**
	 * @return the label for the Time Window option.
	 */
	private JLabel getTimeWindowLabel() {
		if (timeWindowLabel == null) {
			timeWindowLabel = new JLabel("time_window");
		}
		return timeWindowLabel;
	}

	/**
	 * @return the label for the Display Date Format option.
	 */
	private JLabel getDisplayDateFormatLabel() {
		if (displayDateFormatLabel == null) {
			displayDateFormatLabel = new JLabel("display_date_format");
		}
		return displayDateFormatLabel;
	}

	/**
	 * @return the label for the Display Time Format option.
	 */
	private JLabel getDisplayTimeFormatLabel() {
		if (displayTimeFormatLabel == null) {
			displayTimeFormatLabel = new JLabel("display_time_format");
		}
		return displayTimeFormatLabel;
	}

	/**
	 * Acción ejecutada al cambiar la unidad de medida utilizada para definir el
	 * intervalo de tiempo en el <code>JComboBox</code> de la opción.
	 * 
	 * @author rmartinez
	 * 
	 */
	private class MeasureUnitIntervalCBItemListener implements ItemListener {
		public void itemStateChanged(ItemEvent e) {
			measureUnitLabel.setText((String) measureUnitIntervalCB
					.getSelectedItem());
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
		System.out.println(getClass().getName());
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
}
