package net.bioclipse.balloon.handlers;

import java.util.ArrayList;
import java.util.List;

import net.bioclipse.balloon.business.Activator;
import net.bioclipse.balloon.business.IBalloonManager;
import net.bioclipse.balloon.ui.BalloonDialog;
import net.bioclipse.core.business.BioclipseException;

import org.apache.log4j.Logger;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.handlers.HandlerUtil;

public class BalloonConformerHandler extends AbstractHandler{

    private static final Logger logger = Logger.getLogger(BalloonConformerHandler.class);

    public Object execute( ExecutionEvent event ) throws ExecutionException {

        ISelection sel = HandlerUtil.getCurrentSelection( event );
        if (sel==null) return null;
        if (!( sel instanceof StructuredSelection )) return null;
        IStructuredSelection ssel = (IStructuredSelection)sel;

        //We operate on files and IMolecules
        List<String> filenames=new ArrayList<String>();
        List<IResource> foldersToRefresh=new ArrayList<IResource>();
        //Collect files
        for (Object obj : ssel.toList()){
            if ( obj instanceof IFile ) {
                IFile file = (IFile) obj;
                //                filenames.add( file.getRawLocation().toOSString() );
                filenames.add( file.getFullPath().toOSString() );
                foldersToRefresh.add( file.getParent() );
            }
        }

        logger.debug( "Balloon selection contained: " + filenames.size() + " files." );

        if (filenames.size()<=0) return null;
        
        BalloonDialog dlg = new BalloonDialog(HandlerUtil.getActiveShell( event ));
        int ret=dlg.open();
        if (ret==Window.CANCEL) return null;
        
        final int numconf=dlg.getNumConformers();
        
        logger.debug("User selected: " + numconf + " confomers.");
        
        final List<String> final_fnames = filenames;
        final List<IResource> final_foldersToRefresh = foldersToRefresh;

        //Set up a job
        Job job = new Job("Ballon 3D conformer generation") {
            protected IStatus run(IProgressMonitor monitor) {

                monitor.beginTask( "Running Balloon 3D conformer generation: " + numconf + " conformers for " + final_fnames + " files", 2 );
                monitor.worked( 1 );

                //Run balloon on the files
                IBalloonManager balloon = Activator.getDefault().getJavaBalloonManager();
                List<String> ret=null;
                try {
                    ret = balloon.generate3Dconformations( final_fnames, numconf);
                } catch ( BioclipseException e ) {
                    logger.error("Balloon failed: " + e.getMessage());
                    return new Status(IStatus.ERROR,Activator.PLUGIN_ID,"Balloon failed: " + e.getMessage());
                }

                if (ret==null){
                    logger.error( "Balloon failed: " + ret );
                    return new Status(IStatus.ERROR,Activator.PLUGIN_ID,"Balloon failed.");
                }
                for (String r : ret){
                    logger.debug( "Balloon wrote output file: " + r  );
                }

                //Refresh folders in UI thread
                Display.getDefault().syncExec( new Runnable(){
                    public void run() {
                        for (IResource res : final_foldersToRefresh){
                            try {
                                res.refreshLocal( IResource.DEPTH_ONE, new NullProgressMonitor() );
                            } catch ( CoreException e ) {
                                logger.error( "Could not refresh resource: " + res + " - " + e.getMessage() );
                            }
                        }

                    }
                });

                monitor.done();

                return Status.OK_STATUS;
            }
        };
        job.setPriority(Job.LONG);
        job.setUser( true );
        job.schedule(); // start as soon as possible

        return null;
    }
}
