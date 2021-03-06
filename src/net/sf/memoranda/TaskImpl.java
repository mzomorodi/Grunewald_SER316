/**
 * DefaultTask.java
 * Created on 12.02.2003, 15:30:40 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;

import java.util.Collection;
import java.util.Vector;
import java.text.DecimalFormat;
import java.util.Calendar;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.date.CurrentDate;
import net.sf.memoranda.util.Util;
import nu.xom.Attribute;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Node;

/**
 *
 */
/*$Id: TaskImpl.java,v 1.15 2005/12/01 08:12:26 alexeya Exp $*/
public class TaskImpl implements Task, Comparable {

    private Element _element = null;
    private TaskList _tl = null;

    /**
     * Constructor for DefaultTask.
     */
    public TaskImpl(Element taskElement, TaskList tl) {
        _element = taskElement;
        _tl = tl;
    }

    public Element getContent() {
        return _element;
    }

    public CalendarDate getStartDate() {
        return new CalendarDate(_element.getAttribute("startDate").getValue());
    }

    public void setStartDate(CalendarDate date) {
           setAttr("startDate", date.toString());
    }

    public CalendarDate getEndDate() {
		String ed = _element.getAttribute("endDate").getValue();
		if (!ed.equals("")) {
			return new CalendarDate(_element.getAttribute("endDate").getValue());
		}
		Task parent = this.getParentTask();
		if (parent != null) {
			return parent.getEndDate();
		}
		Project pr = this._tl.getProject();
		if (pr.getEndDate() != null) {
			return pr.getEndDate();
		}
		return this.getStartDate();
        
    }

    public void setEndDate(CalendarDate date) {
		if (date == null) {
			setAttr("endDate", "");
		} else {
			setAttr("endDate", date.toString());
		}
    }

    public long getEffort() {
    	Attribute attr = _element.getAttribute("effort");
    	if (attr == null) {
    		return 0;
    	} else {
    		try {
        		return Long.parseLong(attr.getValue());
    		} catch (NumberFormatException e) {
    			return 0;
    		}
    	}
    }
    

    // US 2
    // Added this section for 100% coverage of unit tests.
    //**********************************************************************
    public long getActEffortNullTest(){
    	_element.removeAttribute(_element.getAttribute("actualEffort"));
    	return getActEffort();
    }
    
    public long getActEffortExcTest(){
    	setAttr("actualEffort", "test");
    	return getActEffort();
    }
    
    public long getEffortNullTest(){
    	_element.removeAttribute(_element.getAttribute("effort"));
    	return getEffort();
    }
    
    public long getEffortExcTest(){
    	setAttr("effort", "test");
    	return getEffort();
    }
    
    public void remChildElement(String attr){
    	_element.removeChild(_element.getFirstChildElement(attr));
    }
    //***********************************************************************
    
    public long getActEffort() {
    	Attribute attr = _element.getAttribute("actualEffort");
    	if (attr == null) {
    		return 1;
    	} else {
    		try {
        		return Long.parseLong(attr.getValue());
    		} catch (NumberFormatException e) {
    			return 1;
    		}
    	}
    }
    
    private double getDoubleEffort(){
    	Attribute attr = _element.getAttribute("actualEffort");
    	if(attr == null){
    		return 1;
    	} else{
    		try{
    			return Double.parseDouble(attr.getValue());
    		} catch (NumberFormatException e){
    			return 1;
    		}
    	}
    }
    
    private double getDoubleLOC(){
    	Attribute attr = _element.getAttribute("loc");
    	if(attr == null){
    		return 0;
    	} else{
    		try{
    			return Double.parseDouble(attr.getValue());
    		} catch (NumberFormatException e){
    			return 0;
    		}
    	}
    }

    public void setEffort(long effort) {
    	if (effort > 0){
    		setAttr("effort", String.valueOf(effort));
    	}
    }
    
    // US 2
    public void setActEffort(long actEffort) {
    	if(actEffort > 0){
    		setAttr("actualEffort", String.valueOf(actEffort));
    	}
    }
	
    // US 1
    public long getLOC() {
    	Attribute attr = _element.getAttribute("loc");
    	if (attr == null) {
    		return 0;
    	} else {
    		try {
        		return Long.parseLong(attr.getValue());
    		} catch (NumberFormatException e) {
    			return 0;
    		}
    	}
    }
    
    public double getProductivity(){
    	//double prod = 0.0;
    	//prod = (double)this.getLOC()/Double.parseDouble(Util.getHoursFromMillis(this.getActEffort()));
    	
    	double prod, num, den;
    	num = (double)this.getLOC();
    	den = Double.parseDouble(Util.getHoursFromMillis(this.getActEffort()));
    	//System.out.println("LOC: " + this.getLOC() + "\nActual Hours: " + Util.getHoursFromMillis(this.getActEffort()));
    	prod = num/den;
    	
    	return prod;    	
    	//String p = "Screw this";
    	//return p; 
    }

    // US 1
    public void setLOC(long loc) {
        setAttr("loc", String.valueOf(loc));
    }
    
    // US 3
	  public int getNumDefects() {
		  Attribute attr = _element.getAttribute("numDefects");
		  if (attr == null) {
			  return 0;
		  } else {
			  try {
				  return Integer.parseInt(attr.getValue());
			  } catch (NumberFormatException e) {
				  return 0;
			  }
		  }
	  }

	// US 3
	public void setNumDefects(int numDefects) {
		setAttr("numDefects", Integer.toString(numDefects));
	}
    
	/* 
	 * @see net.sf.memoranda.Task#getParentTask()
	 */
	public Task getParentTask() {
		Node parentNode = _element.getParent();
    	if (parentNode instanceof Element) {
    	    Element parent = (Element) parentNode;
        	if (parent.getLocalName().equalsIgnoreCase("task")) {
        	    return new TaskImpl(parent, _tl);
        	}
    	}
    	return null;
	}
	
	public String getParentId() {
		Task parent = this.getParentTask();
		if (parent != null) {
			return parent.getID();
		}
		return null;
	}

    public String getDescription() {
    	Element thisElement = _element.getFirstChildElement("description");
    	if (thisElement == null) {
    		return null;
    	} else {
       		return thisElement.getValue();
    	}
    }

    public void setDescription(String s) {
    	Element desc = _element.getFirstChildElement("description");
    	if (desc == null) {
        	desc = new Element("description");
            desc.appendChild(s);
            _element.appendChild(desc);    	
    	} else {
            desc.removeChildren();
            desc.appendChild(s);    	
    	}
    }

    /**s
     * @see net.sf.memoranda.Task#getStatus()
     */
    public int getStatus(CalendarDate date) {
        CalendarDate start = getStartDate();
        CalendarDate end = getEndDate();
        if (isFrozen()) {
            return Task.FROZEN;
        }
        if (isCompleted()) {
                return Task.COMPLETED;
        }
        
		if (date.inPeriod(start, end)) {
            if (date.equals(end)) {
                return Task.DEADLINE;
            } else {
                return Task.ACTIVE;
            }
        } else if(date.before(start)) {
				return Task.SCHEDULED;
		}
		
		if(start.after(end)) {
			return Task.ACTIVE;
		}

        return Task.FAILED;
    }
    /**
     * Method isDependsCompleted.
     * @return boolean
     */
/*
    private boolean isDependsCompleted() {
        Vector v = (Vector) getDependsFrom();
        boolean check = true;
        for (Enumeration en = v.elements(); en.hasMoreElements();) {
            Task t = (Task) en.nextElement();
            if (t.getStatus() != Task.COMPLETED)
                check = false;
        }
        return check;
    }
*/
    private boolean isFrozen() {
        return _element.getAttribute("frozen") != null;
    }

    private boolean isCompleted() {
        return getProgress() == 100;
    }

    /**
     * @see net.sf.memoranda.Task#getID()
     */
    public String getID() {
        return _element.getAttribute("id").getValue();
    }

    /**
     * @see net.sf.memoranda.Task#getText()
     */
    public String getText() {
        return _element.getFirstChildElement("text").getValue();
    }

    public String toString() {
        return getText();
    }
    
    /**
     * @see net.sf.memoranda.Task#setText()
     */
    public void setText(String s) {
        _element.getFirstChildElement("text").removeChildren();
        _element.getFirstChildElement("text").appendChild(s);
    }

    /**
     * @see net.sf.memoranda.Task#freeze()
     */
    public void freeze() {
        setAttr("frozen", "yes");
    }

    /**
     * @see net.sf.memoranda.Task#unfreeze()
     */
    public void unfreeze() {
        if (this.isFrozen()) {
            _element.removeAttribute(new Attribute("frozen", "yes"));
        }
    }

    /**
     * @see net.sf.memoranda.Task#getDependsFrom()
     */
    public Collection getDependsFrom() {
        Vector v = new Vector();
        Elements deps = _element.getChildElements("dependsFrom");
        for (int i = 0; i < deps.size(); i++) {
            String id = deps.get(i).getAttribute("idRef").getValue();
            Task t = _tl.getTask(id);
            if (t != null) {
                v.add(t);
            }
        }
        return v;
    }
    /**
     * @see net.sf.memoranda.Task#addDependsFrom(net.sf.memoranda.Task)
     */
    public void addDependsFrom(Task task) {
        Element dep = new Element("dependsFrom");
        dep.addAttribute(new Attribute("idRef", task.getID()));
        _element.appendChild(dep);
    }
    /**
     * @see net.sf.memoranda.Task#removeDependsFrom(net.sf.memoranda.Task)
     */
    public void removeDependsFrom(Task task) {
        Elements deps = _element.getChildElements("dependsFrom");
        for (int i = 0; i < deps.size(); i++) {
            String id = deps.get(i).getAttribute("idRef").getValue();
            if (id.equals(task.getID())) {
                _element.removeChild(deps.get(i));
                return;
            }
        }
    }
    /**
     * @see net.sf.memoranda.Task#getProgress()
     */
    public int getProgress() {
        return new Integer(_element.getAttribute("progress").getValue()).intValue();
    }
    /**
     * @see net.sf.memoranda.Task#setProgress(int)
     */
    public void setProgress(int p) {
        if ((p >= 0) && (p <= 100)) {
            setAttr("progress", new Integer(p).toString());
        }
    }
    /**
     * @see net.sf.memoranda.Task#getPriority()
     */
    public int getPriority() {
        Attribute pa = _element.getAttribute("priority");
        if (pa == null) {
            return Task.PRIORITY_NORMAL;
        }
        return new Integer(pa.getValue()).intValue();
    }
    /**
     * @see net.sf.memoranda.Task#setPriority(int)
     */
    public void setPriority(int p) {
        setAttr("priority", String.valueOf(p));
    }

    private void setAttr(String a, String value) {
        Attribute attr = _element.getAttribute(a);
        if (attr == null) {
           _element.addAttribute(new Attribute(a, value));
        } else {
            attr.setValue(value);
        }
    }

	/**
	 * A "Task rate" is an informal index of importance of the task
	 * considering priority, number of days to deadline and current 
	 * progress. 
	 * 
	 * rate = (100-progress) / (numOfDays+1) * (priority+1)
	 * @param CalendarDate
	 * @return long
	 */

	private long calcTaskRate(CalendarDate d) {
		Calendar endDateCal = getEndDate().getCalendar();
		Calendar dateCal = d.getCalendar();
		int numOfDays = (
				endDateCal.get(Calendar.YEAR)*365 
				+ endDateCal.get(Calendar.DAY_OF_YEAR)) 
				- (dateCal.get(Calendar.YEAR)*365 
						+ dateCal.get(Calendar.DAY_OF_YEAR));
		if (numOfDays < 0) { 
			return -1; //Something wrong ?
		}
		return (100-getProgress()) / (numOfDays+1) * (getPriority()+1);
	}

    /**
     * @see net.sf.memoranda.Task#getRate()
     */
	 
     public long getRate() {
/*	   Task t = (Task)task;
	   switch (mode) {
		   case BY_IMP_RATE: return -1*calcTaskRate(t, date);
		   case BY_END_DATE: return t.getEndDate().getDate().getTime();
		   case BY_PRIORITY: return 5-t.getPriority();
		   case BY_COMPLETION: return 100-t.getProgress();
	   }
       return -1;
*/
		return -1*calcTaskRate(CurrentDate.get());
	 }
	   
	 /*
	  * Comparable interface
	  */
	  
	 public int compareTo(Object o) {
		 Task task = (Task) o;
		 	if(getRate() > task.getRate()) {
				return 1;
		 	} else if(getRate() < task.getRate()) {
				return -1;
		 	} else {
				return 0;
		 	}
	 }
	 
	 public boolean equals(Object o) {
	     return ((o instanceof Task) && (((Task)o).getID().equals(this.getID())));
	 }

	/* 
	 * @see net.sf.memoranda.Task#getSubTasks()
	 */
	public Collection getSubTasks() {
		Elements subTasks = _element.getChildElements("task");
            return convertToTaskObjects(subTasks);
	}

	private Collection convertToTaskObjects(Elements tasks) {
        Vector v = new Vector();
        for (int i = 0; i < tasks.size(); i++) {
            Task t = new TaskImpl(tasks.get(i), _tl);
            v.add(t);
        }
        return v;
    }
	
	/* 
	 * @see net.sf.memoranda.Task#getSubTask(java.lang.String)
	 */
	public Task getSubTask(String id) {
		Elements subTasks = _element.getChildElements("task");
		for (int i = 0; i < subTasks.size(); i++) {
			if (subTasks.get(i).getAttribute("id").getValue().equals(id)) {
				return new TaskImpl(subTasks.get(i), _tl);
			}
		}
		return null;
	}

	/* 
	 * @see net.sf.memoranda.Task#hasSubTasks()
	 */
	public boolean hasSubTasks(String id) {
		Elements subTasks = _element.getChildElements("task");
		for (int i = 0; i < subTasks.size(); i++)  {
			if (subTasks.get(i).getAttribute("id").getValue().equals(id)) {
				return true;
			}
		}
		return false;
	}

	public String getPhase() {
		Attribute attr = _element.getAttribute("phase");
    	if (attr == null) {
    		return null;
    	}
    	else {
        	return attr.getValue();
    	}
	}

	public void setPhase(String phase) {
		setAttr("phase", phase);
	}

}
