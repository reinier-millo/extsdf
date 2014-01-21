/*
 * PlaybackOptionsPanel.java
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
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;

/**
 * @author rmartinez
 *
 */
public class PlaybackOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JRadioButton rdbtnDisplayForEach;
	private JSlider speedSlider;
	private JPanel panel_1;
	private JLabel lblSpeed;
	private JRadioButton rdbtnNewRadioButton;
	private JPanel panel_2;
	private JSpinner durationSpinner;
	private JLabel lblAfterPlayingOnce;
	private JComboBox actionAfterPlayingCB;
	private JCheckBox refreshCheckBox;

	/**
	 * Create the panel.
	 */
	public PlaybackOptionsPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 0);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		rdbtnDisplayForEach = new JRadioButton("Display for each timestamp");
		rdbtnDisplayForEach.setSelected(true);
		GridBagConstraints gbc_rdbtnDisplayForEach = new GridBagConstraints();
		gbc_rdbtnDisplayForEach.anchor = GridBagConstraints.WEST;
		gbc_rdbtnDisplayForEach.insets = new Insets(0, 0, 5, 0);
		gbc_rdbtnDisplayForEach.gridx = 0;
		gbc_rdbtnDisplayForEach.gridy = 0;
		panel.add(rdbtnDisplayForEach, gbc_rdbtnDisplayForEach);
		
		panel_1 = new JPanel();
		panel_1.setBorder(null);
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 1;
		panel.add(panel_1, gbc_panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.TRAILING, 5, 5));
		
		lblSpeed = new JLabel("Speed");
		panel_1.add(lblSpeed);
		
		speedSlider = new JSlider();
		panel_1.add(speedSlider);
		
		panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 2;
		panel.add(panel_2, gbc_panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		
		rdbtnNewRadioButton = new JRadioButton("Play in specified duration (seconds)");
		panel_2.add(rdbtnNewRadioButton);
		
		durationSpinner = new JSpinner();
		panel_2.add(durationSpinner);
		
		lblAfterPlayingOnce = new JLabel("After playing once ");
		GridBagConstraints gbc_lblAfterPlayingOnce = new GridBagConstraints();
		gbc_lblAfterPlayingOnce.insets = new Insets(0, 0, 5, 5);
		gbc_lblAfterPlayingOnce.anchor = GridBagConstraints.WEST;
		gbc_lblAfterPlayingOnce.gridx = 0;
		gbc_lblAfterPlayingOnce.gridy = 1;
		add(lblAfterPlayingOnce, gbc_lblAfterPlayingOnce);
		
		actionAfterPlayingCB = new JComboBox();
		GridBagConstraints gbc_actionAfterPlayingCB = new GridBagConstraints();
		gbc_actionAfterPlayingCB.insets = new Insets(0, 0, 5, 0);
		gbc_actionAfterPlayingCB.anchor = GridBagConstraints.WEST;
		gbc_actionAfterPlayingCB.gridx = 1;
		gbc_actionAfterPlayingCB.gridy = 1;
		add(actionAfterPlayingCB, gbc_actionAfterPlayingCB);
		
		refreshCheckBox = new JCheckBox("Refresh the display when dragging the slider interactively.");
		GridBagConstraints gbc_refreshCheckBox = new GridBagConstraints();
		gbc_refreshCheckBox.anchor = GridBagConstraints.WEST;
		gbc_refreshCheckBox.gridwidth = 2;
		gbc_refreshCheckBox.insets = new Insets(0, 0, 0, 5);
		gbc_refreshCheckBox.gridx = 0;
		gbc_refreshCheckBox.gridy = 2;
		add(refreshCheckBox, gbc_refreshCheckBox);

	}

}
