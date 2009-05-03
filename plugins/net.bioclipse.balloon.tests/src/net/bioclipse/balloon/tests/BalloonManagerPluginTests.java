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
import java.util.List;

import net.bioclipse.balloon.business.Activator;
import net.bioclipse.balloon.business.IBalloonManager;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.ResourcePathTransformer;
import net.bioclipse.core.business.BioclipseException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.junit.Test;
import org.openscience.cdk.exception.CDKException;


public class BalloonManagerPluginTests {

    @Test
    public void testGenerate3DonAbsoluteFile() throws URISyntaxException, MalformedURLException, IOException, BioclipseException, CDKException, CoreException{
        
        IBalloonManager ballon = Activator.getDefault().getBalloonManager();
        ICDKManager cdk = net.bioclipse.cdk.business.Activator.getDefault().getCDKManager();
        
        URI uri = getClass().getResource("/testFiles/polycarpol.mol").toURI();
        URL url=FileLocator.toFileURL(uri.toURL());
        String path=url.getFile();
        
        IFile molfile = ResourcePathTransformer.getInstance().transform( path );
        
//        ICDKMolecule mol  = cdk.loadMolecule( path );
        ICDKMolecule mol  = cdk.loadMolecule( molfile, new NullProgressMonitor() );
        assertTrue( cdk.has2d( mol ));
        assertFalse( cdk.has3d( mol ));

        String res=ballon.generate3Dcoordinates( path );
        assertNotNull( "Balloon returned null", res );

        System.out.println("wrote file: " + res);
        
        File file=new File(res);
        assertTrue( file.exists() );
        assertTrue( file.canRead() );
        
//        ICDKMolecule mol2 = cdk.loadMolecule(res);
        IFile molfile2 = ResourcePathTransformer.getInstance().transform( res );
        ICDKMolecule mol2  = cdk.loadMolecule( molfile2, new NullProgressMonitor() );

        assertTrue( cdk.has3d( mol2 ));
        assertFalse( cdk.has2d( mol2 ));
    }

    @Test
    public void testGenerate3DonCML() throws URISyntaxException, MalformedURLException, IOException, BioclipseException, CDKException, CoreException{
        
        IBalloonManager ballon = Activator.getDefault().getBalloonManager();
        ICDKManager cdk = net.bioclipse.cdk.business.Activator.getDefault().getCDKManager();
        
        URI uri = getClass().getResource("/testFiles/0037.cml").toURI();
        URL url=FileLocator.toFileURL(uri.toURL());
        String path=url.getFile();
        
        IFile molfile = ResourcePathTransformer.getInstance().transform( path );
        
//        ICDKMolecule mol  = cdk.loadMolecule( path );
        ICDKMolecule mol  = cdk.loadMolecule( molfile, new NullProgressMonitor() );
        assertTrue( cdk.has2d( mol ));
        assertFalse( cdk.has3d( mol ));

        String res=ballon.generate3Dcoordinates( path );
        assertNotNull( "Balloon returned null", res );

        System.out.println("wrote file: " + res);
        
        File file=new File(res);
        assertTrue( file.exists() );
        assertTrue( file.canRead() );
        
//        ICDKMolecule mol2 = cdk.loadMolecule(res);
        IFile molfile2 = ResourcePathTransformer.getInstance().transform( res );
        ICDKMolecule mol2  = cdk.loadMolecule( molfile2, new NullProgressMonitor() );

        assertTrue( cdk.has3d( mol2 ));
        assertFalse( cdk.has2d( mol2 ));

    }
    
    @Test
    public void testGenerate3DonSDF() throws URISyntaxException, MalformedURLException, IOException, BioclipseException, CDKException, CoreException{
        
        IBalloonManager ballon = Activator.getDefault().getBalloonManager();
        ICDKManager cdk = net.bioclipse.cdk.business.Activator.getDefault().getCDKManager();
        
        URI uri = getClass().getResource("/testFiles/Fragments2.sdf").toURI();
        URL url=FileLocator.toFileURL(uri.toURL());
        String path=url.getFile();
        
        IFile molfile = ResourcePathTransformer.getInstance().transform( path );
        
//        ICDKMolecule mol  = cdk.loadMolecule( path );
        List<ICDKMolecule> mols  = cdk.loadMolecules( molfile, new NullProgressMonitor() );
        System.out.println("SDF contained: " + mols.size() + " mols");
        for ( ICDKMolecule mol : mols ) {
            assertTrue( cdk.has2d( mol ));
            assertFalse( cdk.has3d( mol ));
        }

        String res=ballon.generate3Dcoordinates( path );
        assertNotNull( "Balloon returned null", res );

        System.out.println("Balloon returned file: " + res);
        
        File file=new File(res);
        assertTrue( file.exists() );
        assertTrue( file.canRead() );


//        ICDKMolecule mol2 = cdk.loadMolecule(res);
        IFile molfile2 = ResourcePathTransformer.getInstance().transform( res );
        List<ICDKMolecule> mols2  = cdk.loadMolecules( molfile2, new NullProgressMonitor() );
        System.out.println("Returned SDF contained: " + mols2.size() + " mols");
        for ( ICDKMolecule mol2 : mols2 ) {
            assertTrue( cdk.has3d( mol2 ));
            assertFalse( cdk.has2d( mol2 ));
        }


    }

    @Test
    public void testGenerate3DonSmiles() throws URISyntaxException, MalformedURLException, IOException, BioclipseException, CDKException, CoreException{
        
        IBalloonManager ballon = Activator.getDefault().getBalloonManager();
        ICDKManager cdk = net.bioclipse.cdk.business.Activator.getDefault().getCDKManager();
        
      URI uri = getClass().getResource("/testFiles/sample30.smi").toURI();
      URL url=FileLocator.toFileURL(uri.toURL());
      String path=url.getFile();
      
      IFile molfile = ResourcePathTransformer.getInstance().transform( path );
      
      List<ICDKMolecule> mols  = cdk.loadSMILESFile( molfile, new NullProgressMonitor());
        System.out.println("Input SMILES file contained: " + mols.size() + " mols");
        for ( ICDKMolecule mol : mols ) {
            assertFalse( cdk.has2d( mol ));
            assertFalse( cdk.has3d( mol ));
        }

        String res=ballon.generate3Dcoordinates( path );
        assertNotNull( "Balloon returned null", res );

        System.out.println("Balloon returned file: " + res);
        
        File file=new File(res);
        assertTrue( file.exists() );
        assertTrue( file.canRead() );


//        ICDKMolecule mol2 = cdk.loadMolecule(res);
        IFile molfile2 = ResourcePathTransformer.getInstance().transform( res );
        List<ICDKMolecule> mols2  = cdk.loadMolecules( molfile2, new NullProgressMonitor() );
        System.out.println("Returned SDF contained: " + mols2.size() + " mols");
        for ( ICDKMolecule mol2 : mols2 ) {
            assertTrue( cdk.has3d( mol2 ));
            assertFalse( cdk.has2d( mol2 ));
        }


    }
    

}
