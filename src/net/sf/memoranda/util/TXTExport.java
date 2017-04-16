package net.sf.memoranda.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class TXTExport {

	File f = null;
	Document doc = null;
	

	public TXTExport(File f, Document doc) throws BadLocationException, IOException {
	
		this.f = f;
		this.doc = doc;
		
		if(f.getName().indexOf(".txt") == -1)
		{
			String dir = f.getPath();
			String ext = ".txt";
			String nfile = dir + ext;

			f = new File(nfile);                    	
		}
		
		try{
			int length = doc.getLength();
			
			String contents = doc.getText(0, length);
			
			FileWriter fwrite = new FileWriter(f, true);
			fwrite.write(contents);
			fwrite.close();
			JOptionPane.showMessageDialog(null,Local.getString("Note added succesfully"));
			
		} catch (BadLocationException ex){
			
			
		}
		
	}
}