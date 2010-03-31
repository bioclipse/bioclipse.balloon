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
package net.bioclipse.balloon.ui;

import net.bioclipse.balloon.business.Activator;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


public class BallonPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage{

    public void init(IWorkbench workbench) {
        //Initialize the preference store we wish to use
        setPreferenceStore(Activator.getDefault().getPreferenceStore());
      }

    @Override
    protected void createFieldEditors() {

        IntegerFieldEditor timeoutEditor=new IntegerFieldEditor(Activator.BALLOON_TIMEOUT, "Timeout (s)",getFieldEditorParent());
        timeoutEditor.setValidRange( 1, 999999999 );
        addField( timeoutEditor );
        
    }
}
