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
import javax.swing.JTextField;

import com.iver.andami.PluginServices;

/**
 * @author rmartinez
 * 
 */
public class TimeDisplayOptionsPanel extends NetCDFOptionsPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox dateFormatCB;
	private JComboBox timeFormatCB;
	private JLabel displayDateFormatLabel;
	private JLabel displayTimeFormatLabel;

	/**
	 * Create the panel.
	 */
	public TimeDisplayOptionsPanel() {
		setLabel(PluginServices.getText(this, "time_display")); //$NON-NLS-1$
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		GridBagConstraints gbc_displayDateFormatLabel = new GridBagConstraints();
		gbc_displayDateFormatLabel.insets = new Insets(5, 5, 5, 5);
		gbc_displayDateFormatLabel.anchor = GridBagConstraints.WEST;
		gbc_displayDateFormatLabel.gridx = 0;
		gbc_displayDateFormatLabel.gridy = 0;
		add(getDisplayDateFormatLabel(), gbc_displayDateFormatLabel);

		GridBagConstraints gbc_dateFormatCB = new GridBagConstraints();
		gbc_dateFormatCB.gridwidth = 3;
		gbc_dateFormatCB.insets = new Insets(5, 5, 5, 0);
		gbc_dateFormatCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateFormatCB.gridx = 1;
		gbc_dateFormatCB.gridy = 0;
		add(getDateFormatCB(), gbc_dateFormatCB);
		GridBagConstraints gbc_displayTimeFormatLabel = new GridBagConstraints();
		gbc_displayTimeFormatLabel.insets = new Insets(5, 5, 0, 5);
		gbc_displayTimeFormatLabel.anchor = GridBagConstraints.WEST;
		gbc_displayTimeFormatLabel.gridx = 0;
		gbc_displayTimeFormatLabel.gridy = 1;
		add(getDisplayTimeFormatLabel(), gbc_displayTimeFormatLabel);

		GridBagConstraints gbc_timeFormatCB = new GridBagConstraints();
		gbc_timeFormatCB.gridwidth = 3;
		gbc_timeFormatCB.insets = new Insets(5, 5, 0, 0);
		gbc_timeFormatCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_timeFormatCB.gridx = 1;
		gbc_timeFormatCB.gridy = 1;
		add(getTimeFormatCB(), gbc_timeFormatCB);
		setPreferredSize(getPreferredSize());
	}

	// TODO Move this from here
	private String[] measureUnits = new String[] {
			PluginServices.getText(this, "milliseconds"), //$NON-NLS-1$
			PluginServices.getText(this, "seconds"), //$NON-NLS-1$
			PluginServices.getText(this, "minutes"), //$NON-NLS-1$
			PluginServices.getText(this, "hours"), //$NON-NLS-1$
			PluginServices.getText(this, "days"), //$NON-NLS-1$
			PluginServices.getText(this, "weeks"), //$NON-NLS-1$
			PluginServices.getText(this, "months"), //$NON-NLS-1$
			PluginServices.getText(this, "years"), //$NON-NLS-1$
			PluginServices.getText(this, "decades"), //$NON-NLS-1$
			PluginServices.getText(this, "centuries") //$NON-NLS-1$
	};

	DateTimeFormats formats = new DateTimeFormats();

	/**
	 * @return the dateFormatCB
	 */
	private JComboBox getDateFormatCB() {
		if (dateFormatCB == null) {
			dateFormatCB = new JComboBox(formats.getTodayDatesFormat());
		}
		return dateFormatCB;
	}

	/**
	 * @return the timeFormatCB
	 */
	private JComboBox getTimeFormatCB() {
		if (timeFormatCB == null) {
			timeFormatCB = new JComboBox(formats.getTodayHoursFormat());
		}
		return timeFormatCB;
	}

	/**
	 * @return the label for the Display Date Format option.
	 */
	private JLabel getDisplayDateFormatLabel() {
		if (displayDateFormatLabel == null) {
			displayDateFormatLabel = new JLabel(PluginServices.getText(this,
					"display_date_format")); //$NON-NLS-1$
		}
		return displayDateFormatLabel;
	}

	/**
	 * @return the label for the Display Time Format option.
	 */
	private JLabel getDisplayTimeFormatLabel() {
		if (displayTimeFormatLabel == null) {
			displayTimeFormatLabel = new JLabel(PluginServices.getText(this,
					"display_time_format")); //$NON-NLS-1$
		}
		return displayTimeFormatLabel;
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
	 * @see org.gvsig.gui.beans.panelGroup.panels.IPanel#apply()
	 */
	@Override
	public void apply() {
		configuration.setDateformat(dateFormatCB.getSelectedIndex());
		configuration.setTimeformat(timeFormatCB.getSelectedIndex());
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


	/* (non-Javadoc)
	 * @see uclv.gvsig.extsdf.timeslider.NetCDFOptionsPanel#postInitialize()
	 */
	protected void postInitialize() {
		getTimeFormatCB().setSelectedIndex(
				dataset.getConfiguration().getTimeformat());
		getDateFormatCB().setSelectedIndex(
				dataset.getConfiguration().getDateformat());
	}
}
