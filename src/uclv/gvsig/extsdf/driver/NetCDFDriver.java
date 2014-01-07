/*
 * NetCDFDriver.java
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
 * Copyright (C) 2013 Reinier Millo Sánchez <rmillo@uclv.cu>
 *                    Universidad Central "Marta Abreu" de Las Villas
 *
 * This file is part of the gvSIG extension extSDF, which is distributed
 * under the terms of the GNU General Public License version 2.
 */

package uclv.gvsig.extsdf.driver;

import java.io.File;

import com.hardcode.gdbms.driver.exceptions.CloseDriverException;
import com.hardcode.gdbms.driver.exceptions.OpenDriverException;
import com.hardcode.gdbms.driver.exceptions.ReadDriverException;
import com.hardcode.gdbms.driver.exceptions.WriteDriverException;
import com.hardcode.gdbms.engine.data.edition.DataWare;
import com.iver.cit.gvsig.fmap.drivers.DriverAttributes;
import com.iver.cit.gvsig.fmap.drivers.MemoryDriver;
import com.iver.cit.gvsig.fmap.drivers.VectorialFileDriver;
import com.iver.cit.gvsig.fmap.drivers.WithDefaultLegend;
import com.iver.cit.gvsig.fmap.rendering.ILegend;
import com.iver.cit.gvsig.fmap.rendering.styling.labeling.ILabelingStrategy;

public class NetCDFDriver extends MemoryDriver implements VectorialFileDriver, WithDefaultLegend{

    public NetCDFDriver(){
        super();
    }
    
    @Override
    public DriverAttributes getDriverAttributes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isWritable() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int[] getPrimaryKeys() throws ReadDriverException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void write(DataWare arg0) throws WriteDriverException,
            ReadDriverException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public String getName() {
        return "gvSIG NetCDF Memory Driver";
    }

    @Override
    public int getShapeType() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ILabelingStrategy getDefaultLabelingStrategy() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ILegend getDefaultLegend() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean accept(File arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void close() throws CloseDriverException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public File getFile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initialize() throws ReadDriverException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void open(File arg0) throws OpenDriverException {
        // TODO Auto-generated method stub
        
    }

}
