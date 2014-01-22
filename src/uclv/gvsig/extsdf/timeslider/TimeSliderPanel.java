/*
 * TimeSliderPanel.java
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

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.iver.andami.PluginServices;

public class TimeSliderPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField infoField;
	private JButton timeOnMapButton;
	private JButton optionsButton;
	private JButton exportButton;
	private JButton decreaseExtentButton;
	private JButton increaseExtentButton;
	private JButton fullExtentButton;
	private JButton skipBackwardButton;
	private JSlider slider;
	private JButton skipForwardButton;
	private JButton playPauseButton;
	private JButton seekBackwardButton;
	private JButton seekForwardButton;

	/**
	 * Create the panel.
	 */
	public TimeSliderPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] {0, 0};
		gridBagLayout.rowHeights = new int[] {0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		GridBagConstraints gbc_toolBar = new GridBagConstraints();
		gbc_toolBar.insets = new Insets(0, 0, 5, 0);
		gbc_toolBar.anchor = GridBagConstraints.NORTH;
		gbc_toolBar.fill = GridBagConstraints.HORIZONTAL;
		gbc_toolBar.gridx = 0;
		gbc_toolBar.gridy = 0;
		add(toolBar, gbc_toolBar);
		
		toolBar.add(getTimeOnMapButton());
		toolBar.add(getOptionsButton());
		toolBar.add(getExportButton());
		
		toolBar.add(getInfoField());
		getInfoField().setColumns(10);
		
		toolBar.add(getDecreaseExtentButton());
		toolBar.add(getIncreaseExtentButton());
		toolBar.add(getFullExtentButton());
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.anchor = GridBagConstraints.NORTH;
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{54, 200, 54, 44, 0};
		gbl_panel.rowHeights = new int[]{25, 25, 0};
		gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		skipBackwardButton = new JButton("");
		skipBackwardButton.setIcon(PluginServices.getIconTheme().get("skip-backward-icon"));
		GridBagConstraints gbc_skipBackwardButton = new GridBagConstraints();
		gbc_skipBackwardButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_skipBackwardButton.insets = new Insets(0, 0, 5, 5);
		gbc_skipBackwardButton.gridx = 0;
		gbc_skipBackwardButton.gridy = 0;
		panel.add(getSkipBackwardButton(), gbc_skipBackwardButton);
		
		GridBagConstraints gbc_slider = new GridBagConstraints();
		gbc_slider.fill = GridBagConstraints.HORIZONTAL;
		gbc_slider.weightx = 10.0;
		gbc_slider.insets = new Insets(0, 0, 5, 5);
		gbc_slider.gridx = 1;
		gbc_slider.gridy = 0;
		panel.add(getSlider(), gbc_slider);
		
		GridBagConstraints gbc_skipForwardButton = new GridBagConstraints();
		gbc_skipForwardButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_skipForwardButton.insets = new Insets(0, 0, 5, 5);
		gbc_skipForwardButton.gridx = 2;
		gbc_skipForwardButton.gridy = 0;
		panel.add(getSkipForwardButton(), gbc_skipForwardButton);
		
		GridBagConstraints gbc_playPauseButton = new GridBagConstraints();
		gbc_playPauseButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_playPauseButton.insets = new Insets(0, 0, 5, 0);
		gbc_playPauseButton.gridx = 3;
		gbc_playPauseButton.gridy = 0;
		panel.add(getPlayPauseButton(), gbc_playPauseButton);
		
		GridBagConstraints gbc_seekBackwardButton = new GridBagConstraints();
		gbc_seekBackwardButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_seekBackwardButton.insets = new Insets(0, 0, 0, 5);
		gbc_seekBackwardButton.gridx = 0;
		gbc_seekBackwardButton.gridy = 1;
		panel.add(getSeekBackwardButton(), gbc_seekBackwardButton);
		
		GridBagConstraints gbc_seekForwardButton = new GridBagConstraints();
		gbc_seekForwardButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_seekForwardButton.insets = new Insets(0, 0, 0, 5);
		gbc_seekForwardButton.gridx = 2;
		gbc_seekForwardButton.gridy = 1;
		panel.add(getSeekForwardButton(), gbc_seekForwardButton);

	}

	/**
	 * @return the infoField
	 */
	private JTextField getInfoField() {
		if(infoField == null) {
			infoField = new JTextField();
			infoField.setBackground(Color.WHITE);
			infoField.setEditable(false);
		}
		return infoField;
	}

	/**
	 * @return the timeOnMapButton
	 */
	private JButton getTimeOnMapButton() {
		if(timeOnMapButton == null) {
			timeOnMapButton = new JButton("");
			timeOnMapButton.setIcon(PluginServices.getIconTheme().get("time-on-map-icon"));
			timeOnMapButton.setToolTipText(PluginServices.getText(this, "enable_time_on_map"));
		}
		return timeOnMapButton;
	}

	/**
	 * @return the optionsButton
	 */
	private JButton getOptionsButton() {
		if(optionsButton == null){
			optionsButton = new JButton("");
			optionsButton.setIcon(PluginServices.getIconTheme().get("settings-icon"));
			optionsButton.setToolTipText(PluginServices.getText(this, "options"));
		}
		return optionsButton;
	}

	/**
	 * @return the exportButton
	 */
	private JButton getExportButton() {
		if(exportButton == null) {
			exportButton = new JButton("");
			exportButton.setIcon(PluginServices.getIconTheme().get("video-icon"));
			exportButton.setToolTipText(PluginServices.getText(this, "export_video"));
		}
		return exportButton;
	}

	/**
	 * @return the decreaseExtentButton
	 */
	private JButton getDecreaseExtentButton() {
		if(decreaseExtentButton == null) {
			decreaseExtentButton = new JButton("");
			decreaseExtentButton.setIcon(PluginServices.getIconTheme().get("decrease-icon"));
			decreaseExtentButton.setToolTipText(PluginServices.getText(this, "decrease_time_extent"));
			
		}
		return decreaseExtentButton;
	}

	/**
	 * @return the increaseExtentButton
	 */
	private JButton getIncreaseExtentButton() {
		if(increaseExtentButton == null) {
			increaseExtentButton = new JButton("");
			increaseExtentButton.setIcon(PluginServices.getIconTheme().get("increase-icon"));
			increaseExtentButton.setToolTipText(PluginServices.getText(this, "increase_time_extent"));
		}
		return increaseExtentButton;
	}

	/**
	 * @return the fullExtentButton
	 */
	private JButton getFullExtentButton() {
		if(fullExtentButton == null) {
			fullExtentButton = new JButton("");
			fullExtentButton.setIcon(PluginServices.getIconTheme().get("full-icon"));
			fullExtentButton.setToolTipText(PluginServices.getText(this, "full_time_extent"));
		}
		return fullExtentButton;
	}

	/**
	 * @return the skipBackwardButton
	 */
	private JButton getSkipBackwardButton() {
		if(skipBackwardButton == null) {
			skipBackwardButton = new JButton("");
			skipBackwardButton.setIcon(PluginServices.getIconTheme().get("skip-backward-icon"));
		}
		return skipBackwardButton;
	}

	/**
	 * @return the slider
	 */
	private JSlider getSlider() {
		if(slider == null) {
			slider = new JSlider();
		}
		return slider;
	}

	/**
	 * @return the skipForwardButton
	 */
	private JButton getSkipForwardButton() {
		if(skipForwardButton == null) {
			skipForwardButton = new JButton("");
			skipForwardButton.setIcon(PluginServices.getIconTheme().get("skip-forward-icon"));
		}
		return skipForwardButton;
	}

	/**
	 * @return the playPauseButton
	 */
	private JButton getPlayPauseButton() {
		if(playPauseButton == null) {
			playPauseButton = new JButton("");
			playPauseButton.setIcon(PluginServices.getIconTheme().get("start-icon"));
		}
		return playPauseButton;
	}

	/**
	 * @return the seekBackwardButton
	 */
	private JButton getSeekBackwardButton() {
		if(seekBackwardButton == null) {
			seekBackwardButton = new JButton("");
			seekBackwardButton.setIcon(PluginServices.getIconTheme().get("seek-backward-icon"));
		}
		return seekBackwardButton;
	}

	/**
	 * @return the seekForwardButton
	 */
	private JButton getSeekForwardButton() {
		if(seekForwardButton == null) {
			seekForwardButton = new JButton("");
			seekForwardButton.setIcon(PluginServices.getIconTheme().get("seek-forward-icon"));
		}
		return seekForwardButton;
	}

}