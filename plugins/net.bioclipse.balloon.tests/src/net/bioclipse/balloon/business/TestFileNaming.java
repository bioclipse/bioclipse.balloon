package net.bioclipse.balloon.business;

import static net.bioclipse.balloon.business.BalloonManager.constructOutputFile;
import static net.bioclipse.balloon.business.BalloonManager.constructOutputFilename;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TestFileNaming {

	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();
	
	@Test
	public void testConstructOutputFile() throws IOException {
		File singleFile = testFolder.newFile("test.mdl");
		singleFile.createNewFile();
		
		testFileAsPath(singleFile,"test_3d.mdl");
		testFileAsPath(singleFile,"test_3d_1.mdl");
		testFileAsPath(singleFile,"test_3d_2.mdl");
		
		testFileAsString(singleFile,"test_3d_3.mdl");
	}
	
	private void testFileAsPath(File file,String out) throws IOException {
		File root = testFolder.getRoot();
		IPath rootPath = new Path(root.getAbsolutePath());
		
		IPath input = new Path(file.getAbsolutePath());
		IPath output = constructOutputFile(input, 1);
		assertEquals(out,output.makeRelativeTo(rootPath).toOSString());
		output.toFile().createNewFile();
	}
	private void testFileAsString(File file,String out) throws IOException {
		File root = testFolder.getRoot();
		IPath rootPath = new Path(root.getAbsolutePath());
		
		String input = file.getAbsolutePath();
		String output = constructOutputFilename(input, 1);
		assertEquals(rootPath.append(out).toOSString(),output);
		new File(output).createNewFile();
	}

}
