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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

import net.sf.jnati.NativeCodeException;
import net.sf.jnati.proc.ProcessMonitor;
import net.sf.jnati.proc.ProcessOutput;



/**
 * A class for running Balloon to generate one or more 3D conformations
 * @author ola
 *
 */
public class BalloonRunner {

    private static final Logger logger =Logger.getLogger( BalloonRunner.class );

    /**
     * Overridden by platform specific fragments
     * @param inputfile
     * @param outputfile
     * @param numConf
     * @return
     */
    public static String runBalloon(String inputfile, String outputfile, int numConf){

        try {
            BalloonRunner b = new BalloonRunner();
            ProcessOutput output = b.convert( inputfile, outputfile, ""+numConf);
            if (output==null)
                return "Balloon failed. Probably timed out";
            if (output.getExitValue()==0)
                return null;

            else
                return "Balloon failed: " + output.getMessages();

//            System.out.println("Out: " + output.getOutput());
//            System.out.println("Msgs: " + output.getMessages());
//            System.out.println("exitvalue: " + output.getExitValue());
        } catch ( Exception e ) {
            return e.getMessage();
        }
        

    }


    private File path;
    private String osarch;
    private String id;
    private String version;
    private long timeoutMs;


    private ProcessMonitor createProcessRunner(final String... args) {

        // Generate command/args string
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
     * Constructor. Ensures native code is loaded.
     * @throws IOException
     * @throws NativeCodeException
     * @throws ProcessRunnerException
     */
    public BalloonRunner() throws Exception {

        String fragmentBase="";
        Bundle bundle = Platform.getBundle( "net.bioclipse.balloon.macosx");
        if (bundle!=null){
            URL url = FileLocator.find( bundle, new Path("/exec"),null );
            fragmentBase=FileLocator.toFileURL(url).getFile();
            if (fragmentBase==null){
                System.out.println("Could not get fragment base!");
                return;
            }
        }else{
            fragmentBase="/Users/ola/bin/balloon/balloon_macosx/";
        }

        this.id="balloon";
        this.path = new File(fragmentBase);
        this.osarch = "MACOSX";
        this.version="0.6.15";
        this.timeoutMs=20000;

    }


    public ProcessOutput convert(final String inf, final String outf, final String numConf) throws ExecutionException, InterruptedException, TimeoutException{
        if (inf == null) {
            throw new NullPointerException("Null input file");
        }
        if (outf == null) {
            throw new NullPointerException("Null output file");
        }
        if (numConf == null) {
            throw new IllegalArgumentException("NumConf must be >1");
        }

        logger.info("Running 'balloon --input-file" + inf +" --output-file" + outf + " -c" + numConf + " --strict");

        final ProcessMonitor runner = createProcessRunner("-c"+  numConf , inf, outf );
        runner.setTimeout( 10, TimeUnit.SECONDS );
        try{
            ProcessOutput output = runner.runProcess();
            return output;
        }catch (TimeoutException e){
            return null;
        }
    }

    public static void main( String[] args ) {

        System.out.println("start");

        // If there are no arguments, emit an appropriate usage message.
        //
        if (args.length == 0) {
            System.out.println("Need at least one path to a file containing molecule as input");
        }
        else {
            // Iterate over all the arguments.
            //
            for (int i = 0; i < args.length; ++i) {
                // Construct the URI for the instance file.
                // The argument is treated as a file path only if it denotes an existing file.
                // Otherwise, it's directly treated as a URL.
                //
                File file = new File(args[i]);

                try {
                    BalloonRunner b = new BalloonRunner();
                    ProcessOutput output = b.convert( file.getAbsolutePath(), file.getAbsolutePath(), "1");
                    System.out.println("Out: " + output.getOutput());
                    System.out.println("Msgs: " + output.getMessages());
                    System.out.println("exitvalue: " + output.getExitValue());
                } catch ( Exception e ) {
                    e.printStackTrace();
                    return;
                }
            }        
        }

        System.out.println("end");
    }
}
