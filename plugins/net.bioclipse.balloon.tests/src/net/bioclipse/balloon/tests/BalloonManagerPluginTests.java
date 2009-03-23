/*******************************************************************************
 * Copyright (c) 2009 Ola Spjuth.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ola Spjuth - initial API and implementation
 ******************************************************************************/
package net.bioclipse.balloon.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import net.bioclipse.balloon.business.Activator;
import net.bioclipse.balloon.business.IBalloonManager;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.business.BioclipseException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.junit.Test;
import org.openscience.cdk.exception.CDKException;


public class BalloonManagerPluginTests {

    @Test
    public void testGenerate3D() throws URISyntaxException, MalformedURLException, IOException, BioclipseException, CDKException, CoreException{
        
        IBalloonManager ballon = Activator.getDefault().getBalloonManager();
        ICDKManager cdk = net.bioclipse.cdk.business.Activator.getDefault().getCDKManager();
        
        URI uri = getClass().getResource("/testFiles/polycarpol.mol").toURI();
        URL url=FileLocator.toFileURL(uri.toURL());
        String path=url.getFile();
        
        ICDKMolecule mol  = cdk.loadMolecule( path );
        assertTrue( cdk.has2d( mol ));
        assertFalse( cdk.has3d( mol ));

        String res=ballon.generate3Dcoordinates( path );

        System.out.println("wrote file: " + res);
        
        File file=new File(res);
        assertTrue( file.exists() );
        assertTrue( file.canRead() );
        
        ICDKMolecule mol2 = cdk.loadMolecule(res);
        assertTrue( cdk.has3d( mol2 ));
        assertFalse( cdk.has2d( mol2 ));

        res=ballon.generate3Dcoordinates( path );

        System.out.println("wrote file: " + res);
        
        file=new File(res);
        assertTrue( file.exists() );
        assertTrue( file.canRead() );
        
        mol2 = cdk.loadMolecule(res);
        assertTrue( cdk.has3d( mol2 ));
        assertFalse( cdk.has2d( mol2 ));

    }

}
