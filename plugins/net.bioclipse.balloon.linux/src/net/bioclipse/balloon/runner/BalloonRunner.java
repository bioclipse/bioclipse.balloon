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
package net.bioclipse.balloon.runner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.sf.jnati.proc.ProcessMonitor;
import net.sf.jnati.proc.ProcessOutput;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;


/**
 * A class for running Balloon to generate one or more 3D conformations
 * @author ola
 *
 */
public class BalloonRunner {

    private static final Logger logger =Logger.getLogger( BalloonRunner.class );

    //Hardcoded constants
    private final static String FRAGMENT_ID="net.bioclipse.balloon.linux";
    private final static String osarch="LINUX";
//    private final static String id="balloon";
//    private final static String version="0.6.15";

    //Filled from constructor
    private long timeoutMs;

    //Constructed in constructor
    private File path;

    
    /**
     * Overridden by platform specific fragments
     * @param inputfile
     * @param outputfile
     * @param numConf
     * @return String errormessage or Null if success
     * @throws TimeoutException 
     * @throws InterruptedException 
     * @throws ExecutionException 
     */
    public boolean runBalloon(String inputfile, String outputfile, int numConf) throws ExecutionException, InterruptedException, TimeoutException{

//            BalloonRunner b = new BalloonRunner();
//            ProcessOutput output = b.convert( inputfile, outputfile, ""+numConf);
            
            if (inputfile == null) {
                throw new NullPointerException("Null input file");
            }
            if (outputfile == null) {
                throw new NullPointerException("Null output file");
            }
            if (numConf <=0) {
                throw new IllegalArgumentException("NumConf must be >1");
            }

            logger.info("Running 'balloon -c " + numConf + " " + inputfile + " " + outputfile);

            final ProcessMonitor runner = createProcessRunner("-c"+  numConf , inputfile, outputfile );
            
//            try{
                //Run the Process
                ProcessOutput output = runner.runProcess();

                //React on result
                if (output==null)
                    return false;
                if (output.getExitValue()==0)
                    return true;
                else
                    return false;
//                    return "Balloon failed: " + output.getMessages();
//            }catch (TimeoutException e){
//                return "Balloon timed out: " + e.getMessage();
//            } catch ( ExecutionException e ) {
//                return "Balloon timed out: " + e.getMessage();
//            } catch ( InterruptedException e ) {
//                return "Balloon was interrupted: " + e.getMessage();
//            }

    }



    /**
     * Set up a runner for Ballon on the current arch
     * @param args
     * @return
     */
    private ProcessMonitor createProcessRunner(final String... args) {

        // Generate command/args string for different archs
        final String[] command = new String[args.length + 1];
        System.arraycopy(args, 0, command, 1, args.length);

        String exe;
        if (osarch.startsWith("WINDOWS")) {
            exe = "balloon.exe";
        }
        else if (osarch.startsWith("LINUX")) {
            exe = "balloon";
        }
        else if (osarch.startsWith("MAC")) {
            exe = "balloon";
        }
        else {
            throw new RuntimeException("Unknown OS: " + osarch);
        }

        command[0] = new File(path, exe).getPath();
        final ProcessMonitor runner = new ProcessMonitor(command);
        runner.setTimeout(timeoutMs, TimeUnit.MILLISECONDS);
        return runner;
    }

    /**
     * Constructor. Constructs path to ballon native executable
     * @param timeoutMs Number of milisaeconds until timeout is reached
     * @throws IOException 
     */
    public BalloonRunner(Long timeoutMs) throws IOException {

        if (timeoutMs<=0)
            this.timeoutMs=20000;
        else
            this.timeoutMs=timeoutMs;

        //Construct path to balloon for this installation
        String fragmentBase="";
        Bundle bundle = Platform.getBundle( FRAGMENT_ID);
        if (bundle!=null){
            URL url = FileLocator.find( bundle, new Path("/exec"),null );
            fragmentBase=FileLocator.toFileURL(url).getFile();
            if (fragmentBase==null){
                System.out.println("Could not get fragment base!");
                return;
            }
        }else{
            //This could be set for testing purposes with no OSGI
//            fragmentBase="hardcoded/path/to/ballon_macosx/";
            fragmentBase="/Users/ola/bin/balloon/balloon_linux/";
        }

        //Stor econstructed path to balloon
        this.path = new File(fragmentBase);

    }


}
