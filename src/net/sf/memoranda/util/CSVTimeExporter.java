/*
 * CSVTimeExporter.java
 * Represents CSV configuration for the time list of the current project.
 * 
 * @author mzomorod
 */

package net.sf.memoranda.util;

import java.io.PrintWriter;
import java.util.*;

import net.sf.memoranda.*;

public class CSVTimeExporter implements CSVExporter{

	@Override
	/*
	 * Exports TimeEntry list to specified file. Assumes directory and file exists.
	 * 
	 * @param	output_file file to contain CSV-formatted data
	 * @see net.sf.memoranda.util.CSVExporter#export(java.io.PrintWriter)
	 */
	public void export(PrintWriter writer) {
		writer.println("ID,Date,Comments,Phase,LOC Start,LOC End," +
				"Start Time,End Time,Interrupt Time");
		
		Vector<TimeEntry> times = CurrentProject.getTimeEntryList().getAllTimeEntries();
		TimeEntry t;
		for (int i = 0; i < times.size(); i++) {
			t = times.get(i);
			writer.print(t.getID() + ",");
			writer.print(t.getDate() + ",");
			writer.print(t.getComments() + ",");
			writer.print(t.getPhase() + ",");
			writer.print(t.getLocStart() + ",");
			writer.print(t.getLocEnd() + ",");
			writer.print(t.getStartTime() + ",");
			writer.print(t.getStopTime() + ",");
			writer.println(t.getInterruptTime());
		}
		
		writer.flush();
		
	}

}
