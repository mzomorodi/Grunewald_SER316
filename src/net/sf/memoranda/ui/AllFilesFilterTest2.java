package net.sf.memoranda.ui;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AllFilesFilterTest2 {
	private static File rtfFile = null;
	AllFilesFilter aff = new AllFilesFilter("RTF");
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		rtfFile = File.createTempFile("rtfFille", ".rtf");
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
	public void testGetDescription() {
		assertEquals(aff.getDescription(), "Rich Text Format (*.rtf)");
	}

}
