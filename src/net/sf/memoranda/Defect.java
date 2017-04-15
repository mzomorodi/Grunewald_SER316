package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;

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
	
	public String getHashId() {
		return _elem.getAttribute("hashID").getValue();
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
	
	public void setTask(String s){
		_elem.getAttribute("task").setValue(s);
	}
	
	public void setDefectType(String s){
		_elem.getAttribute("type").setValue(s);
	}
	
	public void setID(String s){
		_elem.getAttribute("id").setValue(s);
	}
	
	public void setInjectedPhase(String s){
		_elem.getAttribute("inj").setValue(s);
	}
	
	public void setRemovedPhase(String s){
		_elem.getAttribute("rem").setValue(s);
	}
	
	public void setFixedTime(String s){
		_elem.getAttribute("fix").setValue(s);
	}
	
	public void setFixRef(String s){
		_elem.getAttribute("ref").setValue(s);
	}
	
	public void setDesc(String s){
		_elem.getAttribute("desc").setValue(s);
	}
}
