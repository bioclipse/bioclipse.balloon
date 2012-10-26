package net.bioclipse.balloon.tests;

import net.bioclipse.balloon.business.IJavaScriptBalloonManager;
import net.bioclipse.cdk.business.IJavaScriptCDKManager;
import net.bioclipse.ui.business.IJSUIManager;
import net.bioclipse.ui.business.IJavaUIManager;

import org.junit.BeforeClass;

public class JavaScriptBalloonManagerPluginTest extends
		AbstractBalloonManagerPluginTest {

	@BeforeClass
	public static void setupBallonManagerPluginTest() throws Exception {
		balloon = getManager(IJavaScriptBalloonManager.class);
		cdk = getManager(IJavaScriptCDKManager.class);
		ui = getManager(IJSUIManager.class);
	}
}
