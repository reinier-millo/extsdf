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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import ucar.nc2.dataset.CoordinateAxis1DTime;

import com.iver.andami.PluginServices;

/**
 * @author rmartinez
 * 
 */
public class TimeExtentOptionsPanel extends NetCDFOptionsPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JComboBox restrictFullTimeExtentCB;
	private JSpinner startTimeField;
	private JButton minTimeBtn;
	private JSpinner endTimeField;
	private JButton maxTimeBtn;
	private JLabel restrictFullTimeExtentLabel;
	private JLabel startTimeLabel;
	private JLabel endTimeLabel;

	/**
	 * Create the panel.
	 */
	public TimeExtentOptionsPanel() {
		setLabel("TimeExtent");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 60, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		setLayout(gridBagLayout);
		GridBagConstraints gbc_restrictFullTimeExtentLabel = new GridBagConstraints();
		gbc_restrictFullTimeExtentLabel.insets = new Insets(5, 5, 5, 5);
		gbc_restrictFullTimeExtentLabel.anchor = GridBagConstraints.WEST;
		gbc_restrictFullTimeExtentLabel.gridx = 0;
		gbc_restrictFullTimeExtentLabel.gridy = 0;
		add(getRestrictFullTimeExtentLabel(), gbc_restrictFullTimeExtentLabel);

		GridBagConstraints gbc_restrictFullTimeExtentCB = new GridBagConstraints();
		gbc_restrictFullTimeExtentCB.gridwidth = 2;
		gbc_restrictFullTimeExtentCB.insets = new Insets(5, 5, 5, 0);
		gbc_restrictFullTimeExtentCB.fill = GridBagConstraints.HORIZONTAL;
		gbc_restrictFullTimeExtentCB.gridx = 1;
		gbc_restrictFullTimeExtentCB.gridy = 0;
		add(getRestrictFullTimeExtentCB(), gbc_restrictFullTimeExtentCB);
		GridBagConstraints gbc_startTimeLabel = new GridBagConstraints();
		gbc_startTimeLabel.insets = new Insets(5, 5, 5, 5);
		gbc_startTimeLabel.anchor = GridBagConstraints.WEST;
		gbc_startTimeLabel.gridx = 0;
		gbc_startTimeLabel.gridy = 1;
		add(getStartTimeLabel(), gbc_startTimeLabel);

		GridBagConstraints gbc_startTimeField = new GridBagConstraints();
		gbc_startTimeField.fill = GridBagConstraints.HORIZONTAL;
		gbc_startTimeField.insets = new Insets(5, 5, 5, 5);
		gbc_startTimeField.gridx = 1;
		gbc_startTimeField.gridy = 1;
		add(getStartTimeField(), gbc_startTimeField);

		GridBagConstraints gbc_minTimeBtn = new GridBagConstraints();
		gbc_minTimeBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_minTimeBtn.insets = new Insets(5, 5, 5, 0);
		gbc_minTimeBtn.gridx = 2;
		gbc_minTimeBtn.gridy = 1;
		add(getMinTimeBtn(), gbc_minTimeBtn);
		GridBagConstraints gbc_endTimeLabel = new GridBagConstraints();
		gbc_endTimeLabel.insets = new Insets(5, 5, 0, 5);
		gbc_endTimeLabel.anchor = GridBagConstraints.WEST;
		gbc_endTimeLabel.gridx = 0;
		gbc_endTimeLabel.gridy = 2;
		add(getEndTimeLabel(), gbc_endTimeLabel);

		GridBagConstraints gbc_endTimeField = new GridBagConstraints();
		gbc_endTimeField.fill = GridBagConstraints.HORIZONTAL;
		gbc_endTimeField.insets = new Insets(5, 5, 0, 5);
		gbc_endTimeField.gridx = 1;
		gbc_endTimeField.gridy = 2;
		add(getEndTimeField(), gbc_endTimeField);

		GridBagConstraints gbc_maxTimeBtn = new GridBagConstraints();
		gbc_maxTimeBtn.insets = new Insets(5, 5, 0, 0);
		gbc_maxTimeBtn.fill = GridBagConstraints.HORIZONTAL;
		gbc_maxTimeBtn.gridx = 2;
		gbc_maxTimeBtn.gridy = 2;
		add(getMaxTimeBtn(), gbc_maxTimeBtn);
		setPreferredSize(getPreferredSize());
	}

	/**
	 * @return the restrictFullTimeExtentCB
	 */
	private JComboBox getRestrictFullTimeExtentCB() {
		if (restrictFullTimeExtentCB == null) {
			restrictFullTimeExtentCB = new JComboBox();
		}
		return restrictFullTimeExtentCB;
	}

	/**
	 * @return the startTimeField
	 */
	private JSpinner getStartTimeField() {
		if (startTimeField == null) {
			startTimeField = new JSpinner();
		}
		return startTimeField;
	}

	/**
	 * @return the minTimeBtn
	 */
	private JButton getMinTimeBtn() {
		if (minTimeBtn == null) {
			minTimeBtn = new JButton(PluginServices.getText(this, "min_time")); //$NON-NLS-1$
			minTimeBtn.addActionListener(new MinTimeBtnActionListener());
		}
		return minTimeBtn;
	}

	/**
	 * @return the endTimeField
	 */
	private JSpinner getEndTimeField() {
		if (endTimeField == null) {
			endTimeField = new JSpinner();
		}
		return endTimeField;
	}

	/**
	 * @return the maxTimeBtn
	 */
	private JButton getMaxTimeBtn() {
		if (maxTimeBtn == null) {
			maxTimeBtn = new JButton(PluginServices.getText(this, "max_time")); //$NON-NLS-1$
			maxTimeBtn.addActionListener(new MaxTimeBtnActionListener());
		}
		return maxTimeBtn;
	}

	private JLabel getRestrictFullTimeExtentLabel() {
		if (restrictFullTimeExtentLabel == null) {
			restrictFullTimeExtentLabel = new JLabel(PluginServices.getText(
					this, "restrict_full_time_extent")); //$NON-NLS-1$
		}
		return restrictFullTimeExtentLabel;
	}

	private JLabel getStartTimeLabel() {
		if (startTimeLabel == null) {
			startTimeLabel = new JLabel(PluginServices.getText(this,
					"start_time")); //$NON-NLS-1$
		}
		return startTimeLabel;
	}

	private JLabel getEndTimeLabel() {
		if (endTimeLabel == null) {
			endTimeLabel = new JLabel(PluginServices.getText(this, "end_time")); //$NON-NLS-1$
		}
		return endTimeLabel;
	}

	private CoordinateAxis1DTime getTimeVariable() throws IOException {
		return controller.getParameterForCoordinateSystem(controller
				.getCoordinateSystems()[controller.getCoordinateSystemIndex()]);
	}

	private Date getMinDate() {
		try {
			Date[] dates = getTimeVariable().getTimeDates();
			if (dates.length != 0) {
				return dates[0];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private Date getMaxDate() {
		try {
			Date[] dates = getTimeVariable().getTimeDates();
			if (dates.length != 0) {
				return dates[dates.length - 1];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private class MinTimeBtnActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			startTimeField.getModel().setValue(getMinDate());
		}
	}

	private class MaxTimeBtnActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			endTimeField.getModel().setValue(getMaxDate());
		}
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
		// TODO Auto-generated method stub
		System.out.println(getClass().getName());
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
	 * @see uclv.gvsig.extsdf.timeslider.NetCDFOptionsPanel#postInitialize()
	 */
	@Override
	protected void postInitialize() {
		startTimeField.setModel(new SpinnerDateModel(getMinDate(),
				null, null, Calendar.DAY_OF_YEAR));
		endTimeField.setModel(new SpinnerDateModel(getMaxDate(), null,
				null, Calendar.DAY_OF_YEAR));
	}
}
