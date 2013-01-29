/* *****************************************************************************
 * Copyright (c) 2002 Arvid Berg.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Arvid Berg - initial implementation
 ******************************************************************************/
package net.bioclipse.balloon.ui;

import net.bioclipse.balloon.business.Activator;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.osgi.service.prefs.Preferences;

/**
 * @author arvid
 *
 */
public class BalloonPreferenceInitializer extends AbstractPreferenceInitializer {

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		Preferences node = DefaultScope.INSTANCE.getNode(Activator.PLUGIN_ID);
		node.put(Activator.BALLOON_TIMEOUT,Integer.toString(Activator.DEFAULT_BALLOON_TIMEOUT));
	}

}
