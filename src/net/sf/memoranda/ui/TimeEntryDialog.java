/*
 * DefectDialog.java
 * Dialog window for entering a new defect
 * 
 */

package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.TimeEntry;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;

public class TimeEntryDialog extends JDialog {
	private String[] currSelection;
	
	// Window components
	private JPanel mainPanel = new JPanel();
	private JPanel upperPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel commentsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
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
    private JLabel startTimeLabel = new JLabel();
    private JTextField startTimeField = new JTextField();
    
    private JLabel endTimeLabel = new JLabel();
    private JTextField stopTimeField = new JTextField();
    
    private JLabel intTimeLabel = new JLabel();
    private JTextField intTimeField = new JTextField();
    
    // LOC components
    private JLabel startLOCLabel = new JLabel();
    private JTextField startLOCField = new JTextField();
    
    private JLabel endLOCLabel = new JLabel();
    private JTextField endLOCField = new JTextField();
    
    // misc components
    public String[] phases = new String[]{Local.getString("PLANNING"),
    		Local.getString("DESIGN"), Local.getString("CODE"),
    		Local.getString("CODE_REVIEW"), Local.getString("COMPILE"),
    		Local.getString("TEST"), Local.getString("POSTMORTEM")};
    private JLabel phLabel = new JLabel();
    private JComboBox phChooser = new JComboBox();
    private JLabel idLabel = new JLabel();
    private JTextField idField = new JTextField();
    JLabel commentsLabel = new JLabel();
	JTextArea commentsField = new JTextArea();
    JScrollPane commentScrollPane = new JScrollPane(commentsField);

	public TimeEntryDialog(Frame frame, String title, String[] currentSelection) {
		super(frame, title, true);
		currSelection = currentSelection;
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
    	
    	if(t.equals("New Time")){
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
    		
    		idLabel.setText(Local.getString("Entry ID"));
    		idLabel.setPreferredSize(new Dimension(60, 16));
    		idField.setPreferredSize(new Dimension(60, 24));
    		idField.setBorder(textBorder);

    		datePanel.add(idLabel);
    		datePanel.add(idField);
    		datePanel.add(dateLabel);
    		datePanel.add(dateSpinner);
    		
    		commentsLabel.setMaximumSize(new Dimension(60, 16));
        	commentsLabel.setMinimumSize(new Dimension(60, 16));
        	commentsLabel.setText(Local.getString("Comments"));
        	commentsField.setPreferredSize(new Dimension(65, 150));
        	commentsField.setLineWrap(true);
        	commentsField.setWrapStyleWord(true);        
            commentScrollPane.setPreferredSize(new Dimension(65,100));
            commentsPanel.setLayout(new BorderLayout());
            commentsPanel.add(commentsLabel, BorderLayout.NORTH);
            commentsPanel.add(commentScrollPane, BorderLayout.SOUTH);
            commentsPanel.setPreferredSize(new Dimension(100, 130));
    		
            upperPanel.setLayout(new BorderLayout());
    		upperPanel.add(datePanel, BorderLayout.NORTH);
    		upperPanel.add(commentsPanel, BorderLayout.SOUTH);
    		
    		for(int i = 0; i < phases.length; i++){
    			phChooser.addItem(phases[i]);
    		}
    		phChooser.setBorder(textBorder);
    		
    		phLabel.setText(Local.getString("Phase"));
    		phLabel.setMinimumSize(new Dimension(60, 16));
    		phLabel.setPreferredSize(new Dimension(100, 16));
    		
    		startLOCLabel.setText(Local.getString("LOC at Start"));
    		startLOCLabel.setPreferredSize(new Dimension(100, 16));
    		startLOCField.setPreferredSize(new Dimension(30, 24));
    		startLOCField.setBorder(textBorder);
    		
    		endLOCLabel.setText(Local.getString("LOC at End"));
    		endLOCLabel.setPreferredSize(new Dimension(100, 16));
    		endLOCField.setPreferredSize(new Dimension(30, 24));
    		endLOCField.setBorder(textBorder);
    		
    		startTimeLabel.setText(Local.getString("Time at Start"));
    		startTimeLabel.setPreferredSize(new Dimension(100, 16));
    		startTimeField.setPreferredSize(new Dimension(30, 24));
    		startTimeField.setBorder(textBorder);
    		
    		endTimeLabel.setText(Local.getString("Time at End"));
    		endTimeLabel.setPreferredSize(new Dimension(100, 16));
    		stopTimeField.setPreferredSize(new Dimension(30, 24));
    		stopTimeField.setBorder(textBorder);
    		
    		intTimeLabel.setText(Local.getString("Total Interruption Time"));
    		intTimeLabel.setPreferredSize(new Dimension(100, 16));
    		intTimeField.setPreferredSize(new Dimension(30, 24));
    		intTimeField.setBorder(textBorder);
    		    		
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
    		/*
    		if(t.equals("Edit Time")){
    	        DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
    	        Date date = df.parse(currSelection[0]);
    	        phChooser.setSelectedIndex(phases.indexOf(currSelection[1]));
    	        
    	        
            }
            */

    		buttonsPanel.add(okB);
    		buttonsPanel.add(cancelB);
    				
    		centerPanel.setLayout(new GridLayout(0,2));
    		centerPanel.setPreferredSize(new Dimension(300, 160));;
    		centerPanel.add(phLabel);
    		centerPanel.add(phChooser);
    		centerPanel.add(startLOCLabel);
    		centerPanel.add(startLOCField);
    		centerPanel.add(endLOCLabel);
    		centerPanel.add(endLOCField);
    		centerPanel.add(startTimeLabel);
    		centerPanel.add(startTimeField);
    		centerPanel.add(endTimeLabel);
    		centerPanel.add(stopTimeField);
    		centerPanel.add(intTimeLabel);
    		centerPanel.add(intTimeField);
    		
    		mainPanel.add(upperPanel);
    		mainPanel.add(centerPanel, BorderLayout.CENTER);
    		mainPanel.add(buttonsPanel);
    		

    		this.add(mainPanel);
    		this.setPreferredSize(new Dimension(400, 500));
    	}	
	}
	
	void okB_actionPerformed(ActionEvent e) {
    	CANCELLED = false;
    	
    	/* for testing
    	CurrentProject.getTimeEntryList().createTimeEntry("id1", new CalendarDate(), "locStart1", "locEnd1", 
    			"startTime1", "stopTime1", "interruptTime1", "phase1", "comments1");
    	*/
    	String id = idField.getText();
    	Date jDate = (Date) dateSpinner.getModel().getValue();
    	CalendarDate d = new CalendarDate(jDate);
    	String locStart = startLOCField.getText();
    	String locEnd = endLOCField.getText();
    	String startTime = startTimeField.getText();
    	String stopTime = stopTimeField.getText();
    	String interruptTime = intTimeField.getText();
    	String phase = phChooser.getSelectedItem().toString();
    	String comments = commentsField.getText();
    	if(getTitle().equals("New Time")){
    		CurrentProject.getTimeEntryList().createTimeEntry(id, d, locStart, locEnd, startTime, stopTime, interruptTime, phase, comments);
    	}
    	else{
    		TimeEntry te = CurrentProject.getTimeEntryList().getTimeEntry(currSelection[TimeEntryTable.ID_COL]);
    		te.setID(id);
    		te.setPhase(phase);
    		te.setDate(d.toString());
    		te.setLocStart(locStart);
    		te.setLocEnd(locEnd);
    		te.setStartTime(startTime);
    		te.setStopTime(stopTime);
    		te.setInterruptTime(interruptTime);
    		te.setComments(comments);
    	}
    	CurrentStorage.get().storeTimeEntryList(CurrentProject.getTimeEntryList(), CurrentProject.get());
        this.dispose();
    }
	
	void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
