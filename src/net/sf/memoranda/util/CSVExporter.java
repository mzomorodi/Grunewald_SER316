/*
 * CVSExporter.java
 * Defines operations used to export a Memoranda item
 * 
 * @author mzomorod
 */

package net.sf.memoranda.util;

import java.io.PrintWriter;

/*
 * Represents an export interface for csv-format files
 */
public interface CSVExporter {
	
	
	void export(PrintWriter writer);

}
