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

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import net.bioclipse.core.PublishedMethod;
import net.bioclipse.core.Recorded;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.business.IBioclipseManager;


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
    @PublishedMethod(methodSummary="Generated 3D coodrinates for all molecules " +
    		"in the inputfile. " +
    "Inputs: String inputfile = path to inputfile.")
    @Recorded
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
    @PublishedMethod(methodSummary="Generated 3D coodrinates for all molecules " +
        "in the inputfile. " +
    "Inputs: String inputfile = path to inputfile, " +
    "String outputfile = path to outputfile")
    @Recorded
    String generate3Dcoordinates( String inputfile, String outputfile ) throws BioclipseException;

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
    @PublishedMethod(methodSummary="Generated 3D conformations for all molecules " +
        "in the inputfile. " +
    "Inputs: String inputfile = path to inputfile, " +
    "String outputfile = path to outputfile, " +
    "int numConformations = number of conformations to generate per molecule.")
    @Recorded
    String generate3Dconformations( String inputfile, String outputfile, 
                                    int numConformations ) throws BioclipseException;

    /**
     * Generate 3D conformations for one or more inputfiles in a list.
     * @param inputfiles List of paths to the inputfiles.
     * @return
     * @throws BioclipseException 
     */
    @PublishedMethod(methodSummary="Generate 3D conformations for all molecules " +
        "in a list of inputfiles. " +
    "Inputs: List<String> inputfiles = list of Strings with paths to inputfiles")
    @Recorded
    List<String> generate3Dcoordinates( List<String> inputfiles) throws BioclipseException;
    
    /**
     * Generate 3D conformations for one or more inputfiles in a list.
     * @param inputfiles List of paths to the inputfiles.
     * @param numConformations Number of conformations to generate per molecule
     * @param outputfile Path to inputfile as String
     * @return
     * @throws BioclipseException 
     */
    @PublishedMethod(methodSummary="Generate 3D conformations for all molecules " +
                     "in a list of inputfiles. " +
                 "Inputs: List<String> inputfiles = list of Strings with paths to inputfiles, " +
    "int numConformations = number of conformations to generate per molecule.")
    @Recorded
    List<String> generate3Dcoordinates( List<String> inputfiles, int numConformations) throws BioclipseException;

    /**
     * TODO: document
     * @param inputfile
     * @param numConformations
     * @return
     * @throws BioclipseException 
     */
    String generate3Dcoordinates( String inputfile, int numConformations ) throws BioclipseException;

}
