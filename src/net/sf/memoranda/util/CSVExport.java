/*
 * CSVExport.java
 * Represents a generator for CSV files
 * 
 * @author mzomorod
 */

package net.sf.memoranda.util;

import java.io.*;

import net.sf.memoranda.*;

/*
 * CSVExport creates CSV-formatted files for different items in Memoranda
 */
public class CSVExport {
	
	static CSVExporter exporter;
	static PrintWriter writer;

	public CSVExport() {}
	
	/*
	 * Writes specified list to specified file in CSV format. Assumes the directory
	 * and filename exists.
	 * 
	 * @param	list_type the type of Memoranda list to export
	 * @param	output_file the file to write the list to
	 */
	public static void export(String list_type, File output_file)
			throws FileNotFoundException{
		
		writer = new PrintWriter(output_file);		
		writer.println("Project ID,Project Title,Project Description");
		writer.println(CurrentProject.get().getID() + "," +
				CurrentProject.get().getTitle() + "," +
				CurrentProject.get().getDescription());
		
		if (list_type == "defect")
			exporter = new CSVDefectExporter();
		else if (list_type == "time")
			exporter = new CSVTimeExporter();
		else
			throw new FileNotFoundException();
		
		exporter.export(writer);
		writer.close();
	}

}
