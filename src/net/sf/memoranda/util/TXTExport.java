package net.sf.memoranda.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
/*
 * Exports a note to a text file
 * Author: William Schull
 */
public class TXTExport {

	File f = null;
	Document doc = null;
	
	/*
	 *  @parameter File f - the file that will contain the note
	 *  @parameter Document doc - contains the note to be exported
	 */
	public TXTExport(File f, Document doc) throws BadLocationException, IOException {
	
		this.f = f;
		this.doc = doc;
		
		if (f.getName().indexOf(".txt") == -1) { 
			String dir = f.getPath();
			String ext = ".txt";
			String nfile = dir + ext;

			f = new File(nfile);                    	
		}
		
		try { 
			int length = doc.getLength();
			
			String contents = doc.getText(0, length);
			
			FileWriter fwrite = new FileWriter(f, true);
			fwrite.write(contents);
			fwrite.close();
			JOptionPane.showMessageDialog(null,Local.getString("Note add succesful"));
			
		} catch (BadLocationException ex) {
			System.err.println("BadLocationException " + ex.getMessage());
			
		}
		
	}
}