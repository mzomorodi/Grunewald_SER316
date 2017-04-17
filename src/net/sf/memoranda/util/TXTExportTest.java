package net.sf.memoranda.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TXTExportTest {

		private static File txtFile = null;
		private static Document doc = new PlainDocument();
		private static String thisString = "This is a test";
		
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		txtFile = File.createTempFile("txtfile", ".txt");
		
		txtFile.deleteOnExit();
		
		doc.insertString(0, thisString, null);
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
	public void testTxtExport() {
		char [] theArray = new char[14];
		try {
			new TXTExport(txtFile, doc);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileReader fReader = new FileReader(txtFile);
			fReader.read(theArray);
			
			fReader.close();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String theString = String.valueOf(theArray);
		assertEquals(thisString, theString);
		
	}

	
	
	

}
