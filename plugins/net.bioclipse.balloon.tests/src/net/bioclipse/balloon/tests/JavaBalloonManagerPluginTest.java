package net.bioclipse.balloon.tests;

import org.junit.BeforeClass;
import net.bioclipse.balloon.business.IJavaBalloonManager;
import net.bioclipse.cdk.business.IJavaCDKManager;
import net.bioclipse.ui.business.IJavaUIManager;
import net.bioclipse.ui.business.IUIManager;

public class JavaBalloonManagerPluginTest extends
		AbstractBalloonManagerPluginTest {
	
	@BeforeClass
	public static void setupBallonManagerPluginTest() throws Exception {
		balloon = getManager(IJavaBalloonManager.class);
		cdk = getManager(IJavaCDKManager.class);
		ui = getManager(IUIManager.class);
	}

}
