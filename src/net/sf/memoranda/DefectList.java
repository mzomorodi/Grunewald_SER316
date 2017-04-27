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
        buildElements(_root);
	}
 // default constructor for testing:
    public DefectList(){
    	_root = new Element("defectlist");
    }
    
    public Vector<Defect> getAllDefects() {
        Elements defects = _root.getChildElements("defect");
        Vector<Defect> v = new Vector<Defect>();

        for (int i = 0; i < defects.size(); i++) {
            Defect d = new Defect(defects.get(i), this);
            v.add(d);
        }
        
        return v;
    }
    
    private void buildElements(Element parent) {
		Elements els = parent.getChildElements("defect");
		for (int i = 0; i < els.size(); i++) {
			Element el = els.get(i);
			_elements.put(el.getAttribute("hashID").getValue(), el);
			buildElements(el);
		}
	}
    
    public void removeDefect(Defect defect) {
        String parentDefectId = defect.getParentId();
        if (parentDefectId == null) {
            _root.removeChild(defect.getContent());            
        } else {
            Element parentNode = getDefectElement(parentDefectId);
            parentNode.removeChild(defect.getContent());
        }
        
		_elements.remove(defect.getHashId());
    }
    
    public Defect getDefect(String id) {
        Util.debug("Getting defect " + id);          
        return new Defect(getDefectElement(id), this);          
    }
    
    private Element getDefectElement(String id) {
		Element el = (Element)_elements.get(id);
		
		if (el == null) {
			Util.debug(
					"Defect " 
					+ id 
					+ " cannot be found in project " 
					+ _project.getTitle());
		}
		
		return el;
    }

	public Defect createDefect(
			String t, CalendarDate d, String id, String type, String inject, 
			String remove, String fTime, String fRef, String desc){
    	Element e = new Element("defect");
    	String hashID = Util.generateId();
    	e.addAttribute(new Attribute("hashID", hashID));
    	e.addAttribute(new Attribute("task", t));
    	e.addAttribute(new Attribute("date", d.toString()));
    	e.addAttribute(new Attribute("id", id));
    	e.addAttribute(new Attribute("type", type));
    	e.addAttribute(new Attribute("inj", inject));
    	e.addAttribute(new Attribute("rem", remove));
    	e.addAttribute(new Attribute("fix", fTime));
    	e.addAttribute(new Attribute("ref", fRef));
    	e.addAttribute(new Attribute("desc", desc));
    	_root.appendChild(e);
    	_elements.put(hashID, e);
    	
    	return new Defect(e, this);
    }
	
// EMPTY CONSTRUCTOR FOR TESTING PURPOSES! -----------------------------	
	public Defect createDefect(){
		Element e = new Element("defect");
		String hashID = Util.generateId();
    	e.addAttribute(new Attribute("task", "Task1"));
    	e.addAttribute(new Attribute("date", new CalendarDate().today().toString()));
    	e.addAttribute(new Attribute("id", "def1"));
    	e.addAttribute(new Attribute("type", "10: Documentation"));
    	e.addAttribute(new Attribute("inj", "Design"));
    	e.addAttribute(new Attribute("rem", "Code Review"));
    	e.addAttribute(new Attribute("fix", "2 hours"));
    	e.addAttribute(new Attribute("ref", "task 3"));
    	e.addAttribute(new Attribute("desc", "recoded buttons"));
    	_elements.put(hashID, e);
    	_root.appendChild(e);
    	return new Defect(e, this);
	}
	
// CONSTRUCTOR FOR DEFECT NOT ASSOCIATED WITH TASK:
	public Defect createDefect(
			CalendarDate d, String id, String type, String inject, 
			String remove, String fTime, String fRef, String desc){
		Element e = new Element("defect");
		String hashID = Util.generateId();
		e.addAttribute(new Attribute("hashID", hashID));
    	e.addAttribute(new Attribute("task", "Not associated"));
    	e.addAttribute(new Attribute("date", d.toString()));
    	e.addAttribute(new Attribute("id", id));
    	e.addAttribute(new Attribute("type", type));
    	e.addAttribute(new Attribute("inj", inject));
    	e.addAttribute(new Attribute("rem", remove));
    	e.addAttribute(new Attribute("fix", fTime));
    	e.addAttribute(new Attribute("ref", fRef));
    	e.addAttribute(new Attribute("desc", desc));
    	_elements.put(hashID, e);
    	_root.appendChild(e);
    	return new Defect(e, this);
	}

	public Document getXMLContent() {
		return _doc;
	}
	
}
