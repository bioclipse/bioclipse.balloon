package net.bioclipse.balloon.tests;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.junit.Assert;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

import net.bioclipse.balloon.business.IBalloonManager;
import net.bioclipse.cdk.business.ICDKManager;
import net.bioclipse.cdk.business.IJavaCDKManager;
import net.bioclipse.cdk.domain.ICDKMolecule;
import net.bioclipse.core.business.BioclipseException;
import net.bioclipse.core.domain.IMolecule;
import net.bioclipse.managers.business.IBioclipseManager;
import net.bioclipse.ui.business.IJavaUIManager;
import net.bioclipse.ui.business.IUIManager;

public class AbstractBalloonManagerPluginTest {
	static IBalloonManager balloon;
	static ICDKManager cdk;
	static IUIManager ui;
	
	@Test
	public void generate3DconformationsMolecule() throws BioclipseException {
		IMolecule mol = getMolecule();
		List<ICDKMolecule> mols = balloon.generate3Dconformations(mol, 5);
		Assert.assertTrue( mols.size()>1);
	}
	public void generate3DconformationsList() {
//		generate3Dconformations(List<String>, int)
	}
	public void generate3DconformationsString() {
		IFile molFile = null;
//		generate3Dconformations(String, int)
	}
	@Test
	public void generate3DconformationsStringString() throws CoreException, BioclipseException, IOException {
		Bundle b = FrameworkUtil.getBundle(this.getClass());
		Enumeration<URL> urls = b.findEntries("", "*.cml", true);
		if(!urls.hasMoreElements()) System.out.println("Nothing found!");
		else System.out.println("Found: ");
		while(urls.hasMoreElements()) {
			System.out.println(urls.nextElement().toString());
		}
		
		URL cml = b.getEntry("bin/testFiles/0037.cml");
		
		
		String project = ui.newProject("Test Project");
		String fileName = cml.getFile();
		System.out.println(fileName);
		System.out.println(new Path("/"+project).append(new Path(fileName).removeFirstSegments(2)).toString());
		IFile cmlFile = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path("/"+project).append(new Path(fileName).removeFirstSegments(2)));
		if(!cmlFile.exists()) {
			cmlFile.create(cml.openStream(), true, null);
		}
		
		String input = cmlFile.getLocation().toOSString();
		String output = balloon.generate3Dconformations(input, null, 1);
		
		List<ICDKMolecule> molecules = cdk.loadMolecules(output);
		Assert.assertEquals(1, molecules.size());
		
	}
	public void generate3DcoordinatesIFile() {
//		generate3Dcoordinates(IFile, BioclipseUIJob<IFile>)
	}
	public void generate3DcoordinatesMolecule() {
//		generate3Dcoordinates(IMolecule)
	}
	public void generate3DcoordinatesListOfString() {
//		generate3Dcoordinates(List<String>)
	}
	public void generate3DcoordinatesString() {
		IFile molFile = null;
//		generate3Dcoordinates(String)
	}
	public void generate3DcoordinatesStringString() {
		IFile molFile = null;
		IFile outFile = null;
//		generate3Dcoordinates(String, String)
	}
	public void generateMultiple3DconformationsList() {
//		generateMultiple3Dconformations(List<IMolecule>, int)
	}
	public void generateMultiple3DconformationsListProgress() {
//		generateMultiple3Dconformations(List<IMolecule>, int, IProgressMonitor)
	}
	public void generateMultiple3DcoordinatesList() {
//		generateMultiple3Dcoordinates(List<IMolecule>)
	}
	public void generateMultiple3DcoordinatesListProgress() {
//		generateMultiple3Dcoordinates(List<IMolecule>, IProgressMonitor)
	}
	
	private IMolecule getMolecule() throws BioclipseException {
		ICDKManager cdk = getManager(IJavaCDKManager.class);
		return cdk.fromSMILES("c1cccccc1OCNCP");
	}
	
	static <T extends IBioclipseManager> T getManager(Class<T> managerInterface) {
		BundleContext bundleContext = FrameworkUtil.getBundle(managerInterface).getBundleContext();
		ServiceReference<T> serviceRef = bundleContext.getServiceReference(managerInterface);
		return bundleContext.getService(serviceRef);
		
	}
}
