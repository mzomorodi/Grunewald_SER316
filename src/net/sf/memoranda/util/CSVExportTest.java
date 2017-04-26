/*
 * CSVExportTest.java
 * Verifies that CSVExport exports defect and time logs of current project
 * to CSV format. Test created in support of US-14, Tasks 53 and 54.
 * 
 * @author mzomorod
 */

package net.sf.memoranda.util;

import static org.junit.Assert.*;

import java.io.*;
import java.util.*;

import org.junit.Before;
import org.junit.Test;

public class CSVExportTest {
	
	File defect_file, time_file;
	Scanner defect_reader, time_reader;
	String path;

	@Before
	public void setUp() throws Exception {
		
		path = CSVExport.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		defect_file = new File(path + "defects.csv");
		time_file = new File(path + "times.csv");
		
		try {
			CSVExport.export("defect", defect_file);
			CSVExport.export("time", time_file);
		}
		catch (FileNotFoundException fnfe) {
			System.err.println("Cannot export file.");
		}
		
		defect_reader = new Scanner(defect_file);
		time_reader = new Scanner(time_file);
		
	}

	@Test
	public void testExport() {
		assertTrue(defect_file.exists());
		assertTrue(defect_reader.hasNext());
		assertTrue(time_file.exists());
		assertTrue(time_reader.hasNext());
	}

}
