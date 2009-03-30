package net.bioclipse.balloon.ui;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;


public class BalloonDialog extends TitleAreaDialog{

    public BalloonDialog(Shell parentShell) {
        super( parentShell );
    }
    
    @Override
    protected Control createDialogArea( Composite parent ) {

        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.verticalSpacing = 0;
        layout.horizontalSpacing = 0;
        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        composite.setFont(parent.getFont());
        // Build the separator line
        Label titleBarSeparator = new Label(composite, SWT.HORIZONTAL
            | SWT.SEPARATOR);
        titleBarSeparator.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        
        
        Label lblNumConf=new Label(composite,SWT.NONE);
        lblNumConf.setText( "Number of conformations" );
        lblNumConf.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
        
        Text txtNumConf=new Text(composite, SWT.NONE);
        GridData gd=new GridData(SWT.LEFT, SWT.FILL, false, true);
        gd.widthHint=200;
        txtNumConf.setLayoutData(gd);

        return composite;
    }
    
}
