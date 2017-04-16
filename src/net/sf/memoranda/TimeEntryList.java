package net.sf.memoranda;

import java.util.Hashtable;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;

/**
 * @author Matthew Seiler
 * 
 * Description: A class for holding a list of time entries to pass
 * to the UI for display
 *
 */
public class TimeEntryList {
	private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
    
    private Hashtable<String, Element> _elements = new Hashtable<String, Element>();
    
    public TimeEntryList(Project prj){
    	_root = new Element("timelist");
    	_doc = new Document(_root);
    	_project = prj;
    }
    
    public TimeEntryList(Document doc, Project prj) {
    	_doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
        buildElements(_root);
	}

    public TimeEntryList(){
    	_root = new Element("timelist");
    }
    
    /**
     * Returns all time entries from list
     * @return Vector<TimeEntry>  a vector that contains all time entries from list
     */
    public Vector<TimeEntry> getAllTimeEntries() {
        Elements timeEntries = _root.getChildElements("time");
        Vector<TimeEntry> v = new Vector<TimeEntry>();

        for (int i = 0; i < timeEntries.size(); i++) {
            TimeEntry t = new TimeEntry(timeEntries.get(i), this);
            v.add(t);
        }
        
        return v;
    }
	
	/*
	 * Description: creates a TimeEntry object
	 */
	public TimeEntry createTimeEntry(
			String id, CalendarDate d, String locStart, String locEnd, String startTime, 
			String stopTime, String interruptTime, String phase, String comments) {
		Element e = new Element("time");
		String hashID = Util.generateId();
		e.addAttribute(new Attribute("id", id));
    	e.addAttribute(new Attribute("date", d.toString()));
    	e.addAttribute(new Attribute("phase", phase));
    	e.addAttribute(new Attribute("locStart", locStart));
    	e.addAttribute(new Attribute("locEnd", locEnd));
    	e.addAttribute(new Attribute("start", startTime));
    	e.addAttribute(new Attribute("stop", stopTime));
    	e.addAttribute(new Attribute("interrupt", interruptTime));
    	e.addAttribute(new Attribute("comments", comments));
    	e.addAttribute(new Attribute("hashID", hashID));
    	_elements.put(hashID, e);
    	_root.appendChild(e);
    	return new TimeEntry(e, this);
	}
	
	private void buildElements(Element parent) {
		Elements els = parent.getChildElements("defect");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			_elements.put(el.getAttribute("hashID").getValue(), el);
			buildElements(el);
		}
	}
	
	public TimeEntry getTimeEntry(String id) {
        Util.debug("Getting entry " + id);          
        return new TimeEntry(getTimeEntryElement(id), this);          
    }
    
    private Element getTimeEntryElement(String id) {
		Element el = (Element)_elements.get(id);
		
		if (el == null) {
			Util.debug("Entry " + id + " cannot be found in project " + _project.getTitle());
		}
		
		return el;
    }

	public Document getXMLContent() {
		return _doc;
	}
}
