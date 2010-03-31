/* *****************************************************************************
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

import java.util.List;

import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.PublishedClass;
import net.bioclipse.core.PublishedMethod;
import net.bioclipse.core.Recorded;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.managers.business.IBioclipseManager;


@PublishedClass( "Contains methods related to Balloon")
/**
 * An interface for a Bioclipse Manager for invoking Balloon
 * (http://web.abo.fi/~mivainio/balloon/index.php)
 * 
 * @author ola
 *
 */
public interface IBalloonManager extends IBioclipseManager {

    /**
     * Generate 3D coordinates for an inputfile. If a multimol file (e.g. SDF)
     * then an SDF will be generated back.
     * @param inputfile Path to inputfile as String
     * @return
     * @throws BioclipseException 
     */
    @Recorded
    @PublishedMethod(
        params = "String inputfile",
        methodSummary = "Generated 3D coodrinates for all molecules in the " +
        		            "inputfile. Creates a default output file. Inputs: " +
        		            "inputfile = path to inputfile.")
    String generate3Dcoordinates( String inputfile ) throws BioclipseException;

    /**
     * Generate 3D coordinates for an inputfile to a dedicated outputfile
     * If a multimol file (e.g. SDF)
     * then an SDF will be generated back.
     * @param inputfile Path to inputfile as String
     * @param outputfile Path to inputfile as String
     * @return
     * @throws BioclipseException 
     */
    @PublishedMethod(
        params = "String inputfile, String outputfile",
        methodSummary = "Generated 3D coodrinates for all molecules in the " +
        		            "inputfile and saves to outputfile Inputs: inputfile " +
        		            "= path to inputfile, outputfile = path to outputfile")
    @Recorded
    String generate3Dcoordinates( String inputfile, String outputfile ) 
           throws BioclipseException;

    /**
     * Generate 3D conformations for one or more inputfiles in a list.
     * @param inputfiles List of paths to the inputfiles.
     * @return
     * @throws BioclipseException 
     */
    @PublishedMethod(
        params = "List<String> inputfiles",
        methodSummary = "Generate 3D coordinates for all molecules in a list " +
        		            "of inputfiles. Creates a default output file. " +
        		            "Inputs: inputfiles = list of Strings with paths " +
        		            "to inputfiles" )
    @Recorded
    List<String> generate3Dcoordinates( List<String> inputfiles) 
                 throws BioclipseException;

    /**
     * Generate 3D conformations for one or more inputfiles in a list.
     * @param inputfiles List of paths to the inputfiles.
     * @param numConformations Number of conformations to generate per molecule
     * @return
     * @throws BioclipseException 
     */
    @PublishedMethod(
        params = "List<String> inputfiles, int numConformations",
        methodSummary = "Generate 3D conformations for all molecules in a " +
        		            "list of inputfiles. inputfiles = list of Strings " +
        		            "with paths to inputfiles, numConformations = number " +
        		            "of conformations to generate per molecule." )
    @Recorded
    List<String> generate3Dconformations( List<String> inputfiles, 
                                          int numConformations) 
                 throws BioclipseException;

    /**
     * Generate 3D conformations for an inputfile
     * @param inputfile Paths to the inputfile
     * @param numConformations Number of conformations to generate per molecule
     * @return
     * @throws BioclipseException 
     */
    @PublishedMethod(
        params = "String inputfile, int numConformations",
        methodSummary = "Generate 3D conformations for all molecules in an " +
        		            "inputfile. Creates a default output filename. " +
        		            "inputfile = paths to inputfile, numConformations " +
        		            "= number of conformations to generate per molecule." )
    @Recorded
    String generate3Dconformations( String inputfile, int numConformations ) 
           throws BioclipseException;

    /**
     * Generate 3D conformations for an inputfile to a dedicated outputfile
     * If a multimol file (e.g. SDF)
     * then an SDF will be generated back.
     * @param inputfile Path to inputfile as String
     * @param numConformations Number of conformations to generate per molecule
     * @param outputfile Path to inputfile as String
     * @return
     * @throws BioclipseException 
     */
    @PublishedMethod(
        params = "String inputfile, String outputfile, int numConformations",
        methodSummary = "Generated 3D conformations for all molecules " +
                        "in the inputfile. \n" +
                        "Inputs: inputfile = path to inputfile, outputfile " +
                        "= path to outputfile, numConformations = number of " +
                        "conformations to generate per molecule.")
    @Recorded
    String generate3Dconformations( String inputfile, 
                                    String outputfile, 
                                    int numConformations ) 
           throws BioclipseException;
    
 
    @PublishedMethod(
                     params = "IMolecule molecule",
                     methodSummary = "Return a new molecule with 3D " +
                     		"coordinates generated by Balloon.")
    @Recorded
    public IMolecule generate3Dcoordinates( IMolecule molecule ) 
    throws BioclipseException;

    @PublishedMethod(
                     params = "IMolecule molecule, int numConf",
                     methodSummary = "Return a list of molecule with  selected " +
                     		"number of target 3D " +
                        "conformations generated by Balloon.")
    @Recorded
    public List<ICDKMolecule> generate3Dconformations( IMolecule molecule, int numConf) 
    throws BioclipseException;
 
    @PublishedMethod(
                     params = "List<IMolecule> molecules",
                     methodSummary = "Return a list of molecules with 3D " +
                        "coordinates generated by Balloon.")
    @Recorded
    public List<IMolecule> generateMultiple3Dcoordinates( 
                                                     List<IMolecule> molecules ) 
    throws BioclipseException;

    @PublishedMethod(
                     params = "List<IMolecule> molecules, int numConf",
                     methodSummary = "Return a list of molecule with the " +
                     		"selected " +
                        "number of target 3D " +
                        "conformations generated by Balloon.")
    @Recorded
    public List<IMolecule> generateMultiple3Dconformations( 
                                                      List<IMolecule> molecules, 
                                                      int numConf) 
    throws BioclipseException;

}