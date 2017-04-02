/**
 * JNCalendarCellRenderer.java
 * Created on 14.02.2003, 0:09:11 Alex
 * Package: net.sf.memoranda.ui
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.ui;
import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.EventImpl;
import net.sf.memoranda.EventsManager;
import net.sf.memoranda.Task;
import net.sf.memoranda.TaskImpl;
import net.sf.memoranda.TaskListImpl;
import net.sf.memoranda.date.CalendarDate;
/**
 *
 */
/*$Id: JNCalendarCellRenderer.java,v 1.5 2004/10/11 08:48:20 alexeya Exp $*/
public class JNCalendarCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
    private CalendarDate d = null;
    boolean disabled = false;
    //ImageIcon evIcon = new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/en.png"));
    Task t = null;
    
    public void setTask(Task _t) {
        t = _t;
    }
    
    public Task getTask() {
        return t;
    }

    public Component getTableCellRendererComponent(
        JTable table,
        Object value,
        boolean isSelected,
        boolean hasFocus,
        int row,
        int column) {
        
		JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		label.setFont(new java.awt.Font("Dialog", 1, 12));
		String currentPanel = ((AppFrame)App.getFrame()).workPanel.dailyItemsPanel.getCurrentPanel();

		if (d == null) {
            label.setEnabled(false);
			label.setIcon(null);
            label.setBackground(new Color(0xE0,0xE0,0xE0));
            return label;
        }
        
		if (!isSelected) {
			CalendarDate cpsd = CurrentProject.get().getStartDate();
            CalendarDate cped = CurrentProject.get().getEndDate();
            if (!(((d.after(cpsd)) && (d.before(cped))) || (d.equals(cpsd)) || (d.equals(cped)))) {
				label.setBackground(new Color(0xF0,0xF0,0xF0));
				return label;
			}
        }		


		label.setHorizontalTextPosition(2);
		label.setEnabled(true);
		


        if (d.equals(CalendarDate.today())) {
            label.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 128)));
        }
        
		// set foreground color
		if (d.getCalendar().get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            label.setForeground(new Color(255, 0, 0));
        }
		else { 		
			label.setForeground(Color.BLACK);
		}

		// set background color
		if (currentPanel == null){
			label.setBackground(Color.WHITE);
			if(d.equals(CalendarDate.today())){
				label.setBackground(Color.PINK);
			}
		}
		
		else if (currentPanel.equals("TASKS") && (t != null) && 
			(d.inPeriod(t.getStartDate(), t.getEndDate()))){ 
				label.setBackground( new Color(230, 255, 230));
		}
		
		else if(currentPanel.equals("NOTES") && 
		CurrentProject.getNoteList().getNoteForDate(d) != null) 
					label.setBackground(new Color(255,245,200));
		
		else if(currentPanel.equals("EVENTS") && 
		(!(EventsManager.getEventsForDate(d).isEmpty()))) 
					label.setBackground(new Color(255,230,230));
		
		else if(!isSelected)
			label.setBackground(Color.WHITE);
		
		if(EventsManager.isNREventsForDate(d) || (t != null)){
			String str = setLabel();
			label.setText("<html>" + label.getText() + "<br>" + str + "</html>");
		}
		
		return label;
    }
    
    public String setLabel(){
    	String s = "";
    	if (EventsManager.isNREventsForDate(d)){
			Collection evCol = EventsManager.getEventsForDate(d);
			int evCount = 0;
			for(Object o : evCol){
				if (evCount == 3) {
					s += ("<br>...");
					break;
				}
				
				EventImpl e = (EventImpl)o;
				String evText = e.getText();
				if (evText.length() > 20) {
					s += ("<br>-" + evText.substring(0, 20) + "...");
				} else {
					s += ("<br>-" + evText);
				}
				evCount++;
			}
    	}
    	if((t != null) && (d.inPeriod(t.getStartDate(), t.getEndDate()))){		
			String tText = t.getText();
			if(tText.length() > 20){
				s += ("<br><span style=\"color: blue;\">" + tText.substring(0, 20) + "...</span>");
			}
			else{
				s += ("<br><span style=\"color: blue;\">" + tText + "</span>");
			}
    	}
    	return s;
    
    }
    

    public void setDate(CalendarDate date) {
        d = date;
    }
}
