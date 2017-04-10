/**
 * FileImport.java
 */
package net.sf.memoranda.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import net.sf.memoranda.ui.ExceptionDialog;
import net.sf.memoranda.ui.htmleditor.HTMLEditor;

/**
 * 
 */
public class FileImport {

    /**
     * Constructor for FileImport.
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
        
        try {
            //in = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UTF-8"));
            in = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
            String line = in.readLine();
            while (line != null) {
            	if (ext.equals("html") || ext.equals("htm")) {
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
        
        if (ext.equals("htm") || ext.equals("html")) {
	        text = Pattern.compile("<body(.*?)>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
	           .split(text)[1];
	        text = Pattern.compile("</body>", java.util.regex.Pattern.DOTALL + java.util.regex.Pattern.CASE_INSENSITIVE)
	            .split(text)[0];
	        //text = text.substring(p1, p2);
        }
        
        editor.insertHTML(text, editor.editor.getCaretPosition());        

    }
}
