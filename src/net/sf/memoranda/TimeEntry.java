package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;
import nu.xom.Node;

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
	
	public String getParentId() {
		TimeEntry parent = this.getParentTimeEntry();
		if (parent != null)
			return parent.getHashID();
		return null;
	}
	
	public TimeEntry getParentTimeEntry() {
		Node parentNode = _elem.getParent();
    	if (parentNode instanceof Element) {
    	    Element parent = (Element) parentNode;
        	if (parent.getLocalName().equalsIgnoreCase("time")) 
        	    return new TimeEntry(parent, _tel);
    	}
    	return null;
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
	
	public String getID() {
		return _elem.getAttribute("id").getValue();
	}
	
	public String getHashID() {
		return _elem.getAttribute("hashID").getValue();
	}
	
	public String getLocStart(){
		return _elem.getAttribute("locStart").getValue();
	}
	
	public String getLocEnd(){
		return _elem.getAttribute("locEnd").getValue();
	}
	
	public String getStartTime(){
		return _elem.getAttribute("start").getValue();
	}
	
	public String getStopTime(){
		return _elem.getAttribute("stop").getValue();
	}
	
	public String getInterruptTime(){
		return _elem.getAttribute("interrupt").getValue();
	}
	
	public String getPhase(){
		return _elem.getAttribute("phase").getValue();
	}
	
	public String getComments(){
		return _elem.getAttribute("comments").getValue();
	}

	public void setDate(String d){
		_elem.getAttribute("date").setValue(d);
	}
	
	public void setID(String id){
		_elem.getAttribute("id").setValue(id);
	}
	
	public void setPhase(String p){
		_elem.getAttribute("phase").setValue(p);
	}
	
	public void setLocStart(String locStart){
		_elem.getAttribute("locStart").setValue(locStart);
	}
	
	public void setLocEnd(String locEnd){
		_elem.getAttribute("locEnd").setValue(locEnd);
	}
	
	public void setStartTime(String startTime){
		_elem.getAttribute("start").setValue(startTime);
	}
	
	public void setStopTime(String stopTime){
		_elem.getAttribute("stop").setValue(stopTime);
	}
	
	public void setInterruptTime(String fr){
		_elem.getAttribute("interrupt").setValue(fr);
	}
	
	public void setComments(String d){
		_elem.getAttribute("comments").setValue(d);
	}
}
