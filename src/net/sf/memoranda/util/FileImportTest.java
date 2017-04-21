/*
  File:	FileImportTest.java
  Author: Matthew Seiler
  Date:	4/12/2017
  
  Description: JUnit tests for added import txt files to Notes functionality
*/
package net.sf.memoranda.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.ui.htmleditor.HTMLEditor;

/**
Class:	FileImportTest

Description: tests txt file import to notes functionality
*/
public class FileImportTest {
	
	private static File pngFile = null;
	private static File txtFile = null;
	private static File htmlFile = null;
	private static File htmFile = null;
	private static HTMLEditor editor = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		pngFile = File.createTempFile("pngfile", ".png");
		txtFile = File.createTempFile("txtfile", ".txt");
		htmlFile = File.createTempFile("htmlfile", ".html");
		htmFile = File.createTempFile("htmfile", ".htm");
		
		pngFile.deleteOnExit();
		txtFile.deleteOnExit();
		htmlFile.deleteOnExit();
		htmFile.deleteOnExit();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		editor = new HTMLEditor();
	}
	
	/**
     * testTxtImport checks if ".txt" are imported
	 * @throws BadLocationException 
     */
	@Test
	public void testTxtImport() throws IOException, BadLocationException {
		String editorText = "";
		String testText = "file text";
		FileWriter fw = null;

		try {
			fw = new FileWriter(txtFile);
			fw.write(testText);
	        fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fw.close();
		}
		new FileImport(txtFile, editor);
		editorText = editor.document.getText(0, editor.document.getLength());
		assertTrue(editorText.equals("\n" + testText + " "));
	}
	
	/**
     * testHtmlImport checks if ".html" are imported
	 * @throws IOException 
     */
	@Test
	public void testHtmlImport() throws IOException {
		String editorText = "";
		String testText = "<body>file text</body>";
		FileWriter fw = null;

		try {
			fw = new FileWriter(htmlFile);
			fw.write(testText);
	        fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fw.close();
		}
		
		new FileImport(htmlFile, editor);
		
		try {
			editorText = editor.document.getText(0, editor.document.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		testText = Pattern.compile("<body(.*?)>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
				.split(testText)[1];
		testText = Pattern.compile("</body>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
		        .split(testText)[0];
		
		assertTrue(editorText.equals("\n" + testText));
	}
	
	/**
     * testHtmImport checks if ".htm" are imported
     * @throws IOException 
     */
	@Test
	public void testHtmImport() throws IOException {
		String editorText = "";
		String testText = "<body>file text</body>";
		FileWriter fw = null;

		try {
			fw = new FileWriter(htmFile);
			fw.write(testText);
	        fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fw.close();
		}
		
		new FileImport(htmFile, editor);
		
		try {
			editorText = editor.document.getText(0, editor.document.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		testText = Pattern.compile("<body(.*?)>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
				.split(testText)[1];
		testText = Pattern.compile("</body>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
		        .split(testText)[0];
		
		assertTrue(editorText.equals("\n" + testText));
	}
	/**
     * testFileFilter checks if file types that are not supported are not imported
	 * @throws IOException 
     */
	@Test
	public void testInvalidFileType() throws IOException {
		String editorTextBefore = "";
		String editorTextAfter = "";
		String testText = "file text";
		FileWriter fw = null;
		
		try {
			editorTextBefore = editor.document.getText(0, editor.document.getLength());
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}

		try {
			fw = new FileWriter(pngFile);
			fw.write(testText);
	        fw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			fw.close();
		}
		
		new FileImport(pngFile, editor);
		
		try {
			editorTextAfter = editor.document.getText(0, editor.document.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		assertTrue(editorTextBefore.equals(editorTextAfter));
	}
}
