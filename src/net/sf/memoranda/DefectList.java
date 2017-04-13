package net.sf.memoranda;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;
import nu.xom.Nodes;

/**
 * @author Avery Bowen
 * 
 * Description: A class to hold a list of defects and pass
 * to the UI for display.
 *
 */
public class DefectList {
	private Project _project = null;
    private Document _doc = null;
    private Element _root = null;
    
    private Hashtable _elements = new Hashtable();
    
    public DefectList(Project prj){
    	_root = new Element("defectlist");
    	_doc = new Document(_root);
    	_project = prj;
    }
    
    public DefectList(Document doc, Project prj) {
    	_doc = doc;
        _root = _doc.getRootElement();
        _project = prj;
	}

	public Defect createDefect(Task t, CalendarDate d, String id, String type, String inject, String remove, String fTime, String fRef, String desc){
    	Element e = new Element("defect");
    	String hashID = Util.generateId();
    	e.addAttribute(new Attribute("task", t.getDescription()));
    	e.addAttribute(new Attribute("date", d.toString()));
    	e.addAttribute(new Attribute("id", id));
    	e.addAttribute(new Attribute("type", type));
    	e.addAttribute(new Attribute("inj", inject));
    	e.addAttribute(new Attribute("rem", remove));
    	e.addAttribute(new Attribute("fix", fTime));
    	e.addAttribute(new Attribute("ref", fRef));
    	e.addAttribute(new Attribute("desc", desc));
    	_elements.put(hashID, e);
    	
    	return new Defect(e, this);
    }

	public Document getXMLContent() {
		return _doc;
	}
    
    
    

}
