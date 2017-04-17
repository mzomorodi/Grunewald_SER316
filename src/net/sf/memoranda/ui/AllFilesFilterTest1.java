package net.sf.memoranda.ui;

import static org.junit.Assert.*;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import net.sf.memoranda.util.Local;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AllFilesFilterTest1 {
	private static File txtFile = null;
	AllFilesFilter aff = new AllFilesFilter("TXT");
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		txtFile = File.createTempFile("txtfile", ".txt");
		
		txtFile.deleteOnExit();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		assertTrue(aff.accept(txtFile));
	}

}
