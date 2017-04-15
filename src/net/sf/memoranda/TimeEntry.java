package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

/**
 * @author Matthew Seiler
 * 
 * Description: Back-end for TimeEntry management
 *
 */
public class TimeEntry {
	private Element _elem = null;
	private TimeEntryList _tel = null;
		
	public TimeEntry(Element timeElem, TimeEntryList tel){
		_elem = timeElem;
		_tel = tel;
	}
	
	public Element getContent(){
		return _elem;
	}
	
	public CalendarDate getDate(){
		String t = _elem.getAttribute("date").getValue();
		if(t != "") {
			return new CalendarDate(_elem.getAttribute("date").getValue());
		}
		else{
			return null;
		}
	}
	
	public String getTimeEntryLocStart(){
		return _elem.getAttribute("locStart").getValue();
	}
	
	public String getTimeEntryLocEnd(){
		return _elem.getAttribute("locEnd").getValue();
	}
	
	public String getTimeEntryStartTime(){
		return _elem.getAttribute("start").getValue();
	}
	
	public String getTimeEntryStopTime(){
		return _elem.getAttribute("stop").getValue();
	}
	
	public String getTimeEntryInterruptTime(){
		return _elem.getAttribute("interrupt").getValue();
	}
	
	public String getTimeEntryPhase(){
		return _elem.getAttribute("phase").getValue();
	}
	
	public String getTimeEntryComments(){
		return _elem.getAttribute("comments").getValue();
	}
}
