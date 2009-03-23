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
package net.bioclipse.balloon.runner;


/**
 * A class for running Balloon to generate one or more 3D conformations
 * @author ola
 *
 */
public class BalloonRunner {
    
    /**
     * Overridden by platform specific fragments
     * @param inputfile
     * @param outputfile
     * @param numConf
     * @return
     */
    public static String runBalloon(String inputfile, String outputfile, int numConf){
        
        return "Balloon on windowns not implemented";
    }

}
