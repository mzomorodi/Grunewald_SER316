/*
  File:	FileImport.java
  Author: Alex V. Alishevskikh, alex@openmechanics.net, 
  Author: Matthew Seiler	
  Date: 4/12/2017
  
  Description: Handles file imports to notes.
*/
package net.sf.memoranda.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.HTMLEditor;

/**
Class:	FileImport

Description: This class handles file imports for txt, html, and htm files into Notes.
*/
public class FileImport {
	
	private static final Set<String> VALID_TYPES = new HashSet<String>(Arrays.asList(
			new String[] {"htm","html","txt"}
	));
	
	private static final Set<String> HTML_TYPE = new HashSet<String>(Arrays.asList(
			new String[] {"htm","html"}
	));

	/**
	  Method: Constructor for FileImport
	  @param f  File to be imported
	  @param editor  HTMLEditor to which the file's content is imported/pasted to
	  Returns: none

	  Description: The constructor handles reading and importing of the file (f) given
	*/
    public FileImport(File f, HTMLEditor editor) {
        String text = "";
        String ext = "";
        String fName = f.getName();
        int extDot = fName.lastIndexOf('.');
        BufferedReader in;
        
        if (extDot > 0) {
            ext = fName.substring(extDot + 1);
        }
        
        if (VALID_TYPES.contains(ext)) {
        	try {
	            //in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
	            in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
	            String line = in.readLine();
	            while (line != null) {
	            	if (HTML_TYPE.contains(ext)) {
	            		text = text + line + "\n";
	            	} else {
	            		text = text + line + "<br>";
	            	}
	                line = in.readLine();
	            }
	            in.close();         
	        }
	        catch (Exception e) {
	            new ExceptionDialog(e, "Failed to import "+f.getPath(), "");
	            return;
	        }
	        
	        if (HTML_TYPE.contains(ext)) {
		        text = Pattern.compile("<body(.*?)>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
		           .split(text)[1];
		        text = Pattern.compile("</body>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
		            .split(text)[0];
		        //text = text.substring(p1, p2);
	        }
	        editor.insertHTML(text, editor.editor.getCaretPosition());
        } else {
        	JOptionPane.showMessageDialog(null,Local.getString("Import for \"" + ext + "\" files is not supported."));
        	System.out.println("Import failed: file type not supported");
        }
    }
}
