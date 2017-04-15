package net.sf.memoranda;

import java.util.Collection;
import java.util.Vector;
import java.util.Calendar;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

/**
 * @author Avery Bowen
 * 
 * Description: The back end for Defect management
 *
 */
public class Defect {
	private Element _elem = null;
	private DefectList _dl = null;
		
	public Defect(Element defElem, DefectList dl){
		_elem = defElem;
		_dl = dl;
	}
	
	public Element getContent(){
		return _elem;
	}
	
	public CalendarDate getDate(){
		String d = _elem.getAttribute("date").getValue();
		if(d != ""){
			return new CalendarDate(_elem.getAttribute("date").getValue());
		}
		else{
			return null;
		}
	}
	
	public String getTask(){
		return _elem.getAttribute("task").getValue();
		
	}
	
	public String getDefectType(){
		return _elem.getAttribute("type").getValue();
	}
	
	public String getID(){
		return _elem.getAttribute("id").getValue();
	}
	
	public String getInjectedPhase(){
		return _elem.getAttribute("inj").getValue();
	}
	
	public String getRemovedPhase(){
		return _elem.getAttribute("rem").getValue();
	}
	
	public String getFixedTime(){
		return _elem.getAttribute("fix").getValue();
	}
	
	public String getFixRef(){
		return _elem.getAttribute("ref").getValue();
	}
	
	public String getDesc(){
		return _elem.getAttribute("desc").getValue();
	}
	
	
	
	
}
