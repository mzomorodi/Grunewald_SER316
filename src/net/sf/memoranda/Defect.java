package net.sf.memoranda;

import net.sf.memoranda.date.CalendarDate;
import nu.xom.Element;
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
	
	public String getParentId() {
		Defect parent = this.getParentDefect();
		if (parent != null)
			return parent.getHashId();
		return null;
	}
	
	public Defect getParentDefect() {
		Node parentNode = _elem.getParent();
    	if (parentNode instanceof Element) {
    	    Element parent = (Element) parentNode;
        	if (parent.getLocalName().equalsIgnoreCase("defect")) 
        	    return new Defect(parent, _dl);
    	}
    	return null;
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
	
	public void setDate(String d){
		_elem.getAttribute("date").setValue(d);
	}
	
	public void setTask(String t){
		_elem.getAttribute("task").setValue(t);
	}
	
	public void setDefectType(String dt){
		_elem.getAttribute("type").setValue(dt);
	}
	
	public void setID(String id){
		_elem.getAttribute("id").setValue(id);
	}
	
	public void setInjectedPhase(String ip){
		_elem.getAttribute("inj").setValue(ip);
	}
	
	public void setRemovedPhase(String rp){
		_elem.getAttribute("rem").setValue(rp);
	}
	
	public void setFixedTime(String ft){
		_elem.getAttribute("fix").setValue(ft);
	}
	
	public void setFixRef(String fr){
		_elem.getAttribute("ref").setValue(fr);
	}
	
	public void setDesc(String desc){
		_elem.getAttribute("desc").setValue(desc);
	}
}
