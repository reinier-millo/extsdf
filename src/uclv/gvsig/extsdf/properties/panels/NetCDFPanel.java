
/*
 * NetCDFPanel.java
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

import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JPanel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;



/**
 * Panel que contiene toda la informacion relacionada con el NetCDF
 * @author dcardoso
 *
 */
public class NetCDFPanel extends AbstractPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;

	public NetCDFPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 468, 0 };
		gridBagLayout.rowHeights = new int[] { 66, 100, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 234, 234, 0 };
		gbl_panel.rowHeights = new int[] { 24, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel_1 = new JLabel(PluginServices.getText(this,
				"sistema_de_coordenada") + ":");
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 0;
		panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

		JComboBox sistema_de_coordenadas = new JComboBox();
		GridBagConstraints gbc_sistema_de_coordenadas = new GridBagConstraints();
		gbc_sistema_de_coordenadas.insets = new Insets(0, 0, 5, 0);
		gbc_sistema_de_coordenadas.fill = GridBagConstraints.BOTH;
		gbc_sistema_de_coordenadas.gridx = 1;
		gbc_sistema_de_coordenadas.gridy = 0;
		panel.add(sistema_de_coordenadas, gbc_sistema_de_coordenadas);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 30, 0, 30);
		gbc_panel.anchor = GridBagConstraints.NORTHWEST;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 1;
		add(panel, gbc_panel);
		
		JLabel lblNewLabel = new JLabel(PluginServices.getText(this,
				"variable") + ":");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 1;
		panel.add(lblNewLabel, gbc_lblNewLabel);
		
		JComboBox comboBox = new JComboBox();
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 0);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 1;
		gbc_comboBox.gridy = 1;
		panel.add(comboBox, gbc_comboBox);
		
		JLabel lblNewLabel_2 = new JLabel(PluginServices.getText(this,
				"x_dimension") + ":");
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 2;
		panel.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		panel.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel(PluginServices.getText(this,
				"y_dimension") + ":");
		GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
		gbc_lblNewLabel_3.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblNewLabel_3.gridx = 0;
		gbc_lblNewLabel_3.gridy = 3;
		panel.add(lblNewLabel_3, gbc_lblNewLabel_3);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 1;
		gbc_textField_1.gridy = 3;
		panel.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
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
