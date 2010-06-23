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

import net.bioclipse.core.util.LogUtils;

import org.apache.log4j.Logger;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "net.bioclipse.balloon.business";

    // The shared instance
    private static Activator plugin;

    private static final Logger logger = Logger.getLogger(Activator.class);

    //Preference string for timeout
    public static final String BALLOON_TIMEOUT = "balloon.timout";

    //Default timeout is 1 minute
    public static final int DEFAULT_BALLOON_TIMEOUT = 60;

    private ServiceTracker javaScriptFinderTracker;
    private ServiceTracker javaFinderTracker;
    
    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;

        javaScriptFinderTracker 
            = new ServiceTracker( context, 
                                  IJavaScriptBalloonManager.class.getName(), 
                                  null );
        javaScriptFinderTracker.open();
        
        javaFinderTracker 
            = new ServiceTracker( context, 
                                  IJavaBalloonManager.class.getName(), 
                                  null );
        javaFinderTracker.open();
        
        getPreferenceStore().setDefault(BALLOON_TIMEOUT, DEFAULT_BALLOON_TIMEOUT);

    }

    /*
     * (non-Javadoc)
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given
     * plug-in relative path
     *
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    public IBalloonManager getJavaBalloonManager() {
        IBalloonManager manager = null;
        try {
            manager = (IBalloonManager) 
                      javaFinderTracker.waitForService(1000*10);
        } catch (InterruptedException e) {
            logger.error("Exception occurred while attempting to " +
            		        "get the BalloonManager", e);
        }
        if(manager == null) {
            throw new IllegalStateException(
                          "Could not get the java balloon manager");
        }
        return manager;
    }
    
    public IBalloonManager getJavaScriptBalloonManager() {
        IBalloonManager manager = null;
        try {
            manager = (IBalloonManager) 
                      javaScriptFinderTracker.waitForService(3000*10);
        } catch (InterruptedException e) {
            logger.error("Exception occurred while attempting to " +
                        "get the JavaScript BalloonManager", e);
        }
        if (manager == null) {
            throw new IllegalStateException(
                          "Could not get the java balloon manager");
        }
        return manager;
    }

    /** 
     * Initializes a preference store with default preference values 
     * for this plug-in.
     */
    protected void initializeDefaultPreferences(IPreferenceStore store) {
        store.setDefault(BALLOON_TIMEOUT, DEFAULT_BALLOON_TIMEOUT);
        logger.debug( "Default balloon preferences set timeout: " 
                      + DEFAULT_BALLOON_TIMEOUT );
    }
}
