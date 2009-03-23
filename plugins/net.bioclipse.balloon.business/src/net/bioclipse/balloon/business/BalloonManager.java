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
package net.bioclipse.balloon.business;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;

import net.bioclipse.balloon.runner.BalloonRunner;
import net.bioclipse.core.ResourcePathTransformer;
import net.bioclipse.core.business.BioclipseException;

/**
 * A Bioclipse Manager for invoking Balloon
 * (http://web.abo.fi/~mivainio/balloon/index.php) Fromm Balloon homepage:
 * Balloon creates 3D atomic coordinates from molecular connectivity via
 * distance geometry and confomer ensembles using a multi-objective genetic
 * algorithm. The input can be SMILES, SDF or MOL2 format. Output is SDF or
 * MOL2. Flexibility of aliphatic rings and stereochemistry about double bonds
 * and tetrahedral chiral atoms is handled.
 * 
 * @author ola
 */
public class BalloonManager implements IBalloonManager {

    private static final Logger logger =Logger.getLogger( BalloonManager.class );

    public String getNamespace() {

        return "balloon";
    }

    public List<String> generate3Dcoordinates( List<String> inputfiles ) throws BioclipseException {

        return generate3Dcoordinates( inputfiles, 1 );
    }

    public List<String> generate3Dcoordinates( List<String> inputfiles,
                                               int numConformations ) throws BioclipseException {

        List<String> outputfiles = new ArrayList<String>();
        for ( String inputfile : inputfiles ) {
            logger.debug( "Running ballon on file: " + inputfile );
            String ret = generate3Dcoordinates( inputfile , 1);
            outputfiles.add( ret );
        }
        return outputfiles;
    }

    public String generate3Dcoordinates( String inputfile ) throws BioclipseException {
        return generate3Dcoordinates( inputfile, 1 );
    }


    public String generate3Dcoordinates( String inputfile , int numConformations) throws BioclipseException {

        return generate3Dconformations( inputfile, 
                                        constructOutputFilename( inputfile, 
                                                                 numConformations ), 
                                                                 numConformations );

    }

    public String generate3Dcoordinates( String inputfile, String outputfile ) {

        return BalloonRunner.runBalloon( inputfile, outputfile, 1 );
    }

    public String generate3Dconformations( String inputfile, String outputfile,
                                           int numConformations ) throws BioclipseException {

        String ret=BalloonRunner.runBalloon( inputfile, outputfile,
                                             numConformations );

        if (ret!=null){
            throw new BioclipseException("Balloon faild: " + ret);
        }
        else{
            logger.debug( "Balloon returned: " + ret );
            logger.debug("Balloon success, wrote file: " + outputfile);
            return outputfile;
        }
    }





    /**
     * Helper method to construct output filename from inputfilename +_3d
     * @param inputfile
     * @param numConformations
     * @return
     */
    public String constructOutputFilename(String inputfile, int numConformations){

        int lastpathsep=inputfile.lastIndexOf( File.separator );
        String path=inputfile.substring( 0, lastpathsep );
        String name=inputfile.substring( lastpathsep+1, inputfile.length()-4 );

        String ext="";
        if (numConformations>1) ext=".sdf";
        else ext=".mol";

        String pathfile=path + File.separator + name;

        int cnt=0;
        String outfile=getAFilename( pathfile, ext, cnt );
        File file=new File(outfile);
        while(file.exists()){
            cnt++;
            outfile=getAFilename( pathfile, ext, cnt );
            file=new File(outfile);
        }
        return outfile;
    }

    /**
     * Increment file numbering to get unique file
     * @param pathname
     * @param ext
     * @param cnt
     * @return
     */
    private String getAFilename(String pathname, String ext, int cnt){

        if (cnt<=0)
            return pathname+"_3d" + ext;
        else
            return pathname+"_3d_" + cnt + ext;

    }

}
