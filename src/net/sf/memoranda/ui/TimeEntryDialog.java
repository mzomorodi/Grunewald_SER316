/*
 * DefectDialog.java
 * Dialog window for entering a new defect
 * 
 */

package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;

public class TimeEntryDialog extends JDialog {
	
	// Window components
	private JPanel mainPanel = new JPanel();
	private JPanel upperPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel commentsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel labelsPanel = new JPanel();
	private JPanel fieldsPanel = new JPanel();
	private JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
	private JButton okB = new JButton();
    private JButton cancelB = new JButton();
	public boolean CANCELLED = true;
	private Border textBorder = BorderFactory.createEtchedBorder(Color.white, 
            new Color(178, 178, 178));
	
	// Date Components
	private CalendarFrame calFrame = new CalendarFrame();
	private JSpinner dateSpinner = new JSpinner();
	private JLabel dateLabel = new JLabel();
	CalendarDate dateMin = CurrentProject.get().getStartDate();
	CalendarDate dateMax = CurrentProject.get().getEndDate();
	boolean ignoreDateChanged = false;
    
    // Time Components
    private JLabel timeLabel = new JLabel();
    private JTextField timeField = new JTextField();

	public TimeEntryDialog(Frame frame, String title) {
		super(frame, title, true);
        try {
            jbInit(title);            
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
        }
	}
	
	public void jbInit(String t) throws Exception {
		
		this.setResizable(false);
    	this.setSize(new Dimension(430,300));
    	
    	dateLabel.setText(Local.getString("Date"));
        dateLabel.setMinimumSize(new Dimension(60, 16));
        dateLabel.setMaximumSize(new Dimension(100, 16));
    	
    	dateSpinner = new JSpinner(
    			new SpinnerDateModel(new Date(),null,null,Calendar.DAY_OF_WEEK));
        dateSpinner.setPreferredSize(new Dimension(80, 24));                
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf = (SimpleDateFormat)DateFormat.getDateInstance(DateFormat.SHORT);
		
		dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, sdf.toPattern()));
        dateSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
            	SpinnerDateModel sdm = new SpinnerDateModel((Date)dateSpinner.getModel().getValue(), null, null,Calendar.DAY_OF_WEEK);
            	dateSpinner.setModel(sdm);

                if (ignoreDateChanged)
                    return;
                ignoreDateChanged = true;
                Date sd = (Date) dateSpinner.getModel().getValue();
                
				if ((dateMax != null) && sd.after(dateMax.getDate())) {
					dateSpinner.getModel().setValue(dateMax.getDate());
                    sd = dateMax.getDate();
				}
                if ((dateMin != null) && sd.before(dateMin.getDate())) {
                    dateSpinner.getModel().setValue(dateMin.getDate());
                    sd = dateMin.getDate();
                }
                calFrame.cal.set(new CalendarDate(sd));
                ignoreDateChanged = false;
            }
        });
        
        datePanel.add(dateLabel, BorderLayout.WEST);
        datePanel.add(dateSpinner, BorderLayout.EAST);
        
        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(datePanel, BorderLayout.NORTH);
        
        labelsPanel.setLayout(new BorderLayout());
        labelsPanel.setPreferredSize(new Dimension(80, 100));
        
        fieldsPanel.setLayout(new BorderLayout());
        fieldsPanel.setPreferredSize(new Dimension(320, 100));
        
        okB.setEnabled(true);
        okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(okB);
        
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setText(Local.getString("Cancel"));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
        
        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(labelsPanel, BorderLayout.WEST);
        centerPanel.add(fieldsPanel, BorderLayout.EAST);
        
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(centerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        this.setLayout(new BorderLayout());
        this.add(upperPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.SOUTH);
        this.setPreferredSize(new Dimension(400, 500));
	}
	
	void okB_actionPerformed(ActionEvent e) {
    	CANCELLED = false;
    	
    	// for testing
    	CurrentProject.getTimeEntryList().createTimeEntry(
    			"id1", new CalendarDate(), "locStart1", "locEnd1", 
    			"startTime1", "stopTime1", "interruptTime1", "phase1", "comments1");
    	
    	CurrentStorage.get().storeTimeEntryList(CurrentProject.getTimeEntryList(), CurrentProject.get());
        this.dispose();
    }
	
	void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
