/*
 * CSVDefectExporter.java
 * Represents CSV configuration for the defect list of the current project.
 * 
 * @author mzomorod
 */

package net.sf.memoranda.util;

import java.io.PrintWriter;
import java.util.*;

import net.sf.memoranda.*;

public class CSVDefectExporter implements CSVExporter{
	
	@Override
	/*
	 * Exports Defect list to specified file. Assumes directory and file exists.
	 * 
	 * @param	output_file file to contain CSV-formatted data
	 * @see net.sf.memoranda.util.CSVExporter#export(net.sf.memoranda.Project, java.io.File)
	 */
	public void export(PrintWriter writer) {
		
		writer.println("ID,Date,Description,Defect Type,Injected Phase,Removed Phase," +
				"Fix Time,Task,Fix Reference");
		
		Vector<Defect> defects = CurrentProject.getDefectList().getAllDefects();
		Defect d;
		for (int i = 0; i < defects.size(); i++) {
			d = defects.get(i);
			writer.print(d.getID() + ",");
			writer.print(d.getDate() + ",");
			writer.print(d.getDesc() + ",");
			writer.print(d.getDefectType() + ",");
			writer.print(d.getInjectedPhase() + ",");
			writer.print(d.getRemovedPhase() + ",");
			writer.print(d.getFixedTime() + ",");
			writer.print(d.getTask() + ",");
			writer.println(d.getFixRef());
		}
		
		writer.flush();
	}

}
