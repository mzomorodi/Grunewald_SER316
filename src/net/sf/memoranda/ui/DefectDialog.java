/*
 * DefectDialog.java
 * Dialog window for entering a new defect
 * 
 * @author mzomorod
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
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.Local;

public class DefectDialog extends JDialog{
	
	private String[] currSelection;
	
	// Window components
	private JPanel mainPanel = new JPanel();
	private JPanel upperPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	//private JPanel labelsPanel = new JPanel();
	//private JPanel fieldsPanel = new JPanel();
	private JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
	private JButton okB = new JButton();
    private JButton cancelB = new JButton();
	public boolean CANCELLED = true;
	private Border textBorder = BorderFactory.createEtchedBorder(Color.white, 
            new Color(178, 178, 178));
	
	//id field components
	private JTextField idField = new JTextField();
	private JLabel idLabel = new JLabel();
	
	// Date Field Components
	private CalendarFrame calFrame = new CalendarFrame();
	private JSpinner dateSpinner = new JSpinner();
	private JLabel dateLabel = new JLabel();
	CalendarDate dateMin = CurrentProject.get().getStartDate();
	CalendarDate dateMax = CurrentProject.get().getEndDate();
	boolean ignoreDateChanged = false;
	
	// Description Field Components
	JLabel descriptionLabel = new JLabel();
	JTextArea descriptionField = new JTextArea();
    JScrollPane descriptionScrollPane = new JScrollPane(descriptionField);
    
    // Type Field Components
    JLabel typeLabel = new JLabel();
    private JComboBox typeChooser = new JComboBox();
    private static final ArrayList<String> TYPES = new ArrayList<String>(Arrays.asList(
			new String[] {Local.getString("10: Documentation"),
		    		Local.getString("20: Syntax"), Local.getString("30: Build/ Package"),
		    		Local.getString("40: Assignment"), Local.getString("50: Interface"),
		    		Local.getString("60: Checking"), Local.getString("70: Data"),
		    		Local.getString("80: Function"), Local.getString("90: System"),
		    		Local.getString("100: Environment")}
	));
    
    // Injected and Removed Components
    private JLabel injectedLabel = new JLabel();
    private JLabel removedLabel = new JLabel();
    private JComboBox injectedChooser = new JComboBox();
    private JComboBox removedChooser = new JComboBox();
    private static final ArrayList<String> PHASES = new ArrayList<String>(Arrays.asList(
			new String[] {Local.getString("PLANNING"),
		    		Local.getString("DESIGN"), Local.getString("CODE"),
		    		Local.getString("CODE_REVIEW"), Local.getString("COMPILE"),
		    		Local.getString("TEST"), Local.getString("POSTMORTEM")}
	));
    
    // Time and Reference Components
    private JLabel timeLabel = new JLabel();
    private JLabel referenceLabel = new JLabel();
    private JTextField timeField = new JTextField();
    private JTextField referenceField = new JTextField();

    public DefectDialog(Frame frame, String title) {
		super(frame, title, true);
        try {
            jbInit(title);            
            pack();
        } catch (Exception ex) {
            new ExceptionDialog(ex);
        }
	}
    
	public DefectDialog(Frame frame, String title, String[] currentSelection) {
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
    	
    	idLabel.setText(Local.getString("Defect ID"));
    	idField.setPreferredSize(new Dimension(60,24));
    	
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
            	
            	SpinnerDateModel sdm = new SpinnerDateModel(
            			(Date)dateSpinner.getModel().getValue(),
            			null, null, Calendar.DAY_OF_WEEK);
            	dateSpinner.setModel(sdm);

                if (ignoreDateChanged) {
                    return;
                }
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
        datePanel.add(idLabel);
        datePanel.add(idField);
        datePanel.add(dateLabel);
        datePanel.add(dateSpinner);
   
        descriptionLabel.setMaximumSize(new Dimension(60, 16));
    	descriptionLabel.setMinimumSize(new Dimension(60, 16));
    	descriptionLabel.setText(Local.getString("Description"));
    	descriptionField.setPreferredSize(new Dimension(65, 150));
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);        
        descriptionScrollPane.setPreferredSize(new Dimension(65, 100));
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(descriptionScrollPane, BorderLayout.SOUTH);
        descriptionPanel.setPreferredSize(new Dimension(100, 130));
        
        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(datePanel, BorderLayout.NORTH);
        upperPanel.add(descriptionPanel, BorderLayout.SOUTH);
        
        typeLabel.setBorder(textBorder);
        typeLabel.setText(Local.getString("Defect Type"));
        typeLabel.setMinimumSize(new Dimension(60, 16));
        typeLabel.setPreferredSize(new Dimension(60, 16));
        typeChooser.setBorder(textBorder);     	        	
        for(int i = 0; i < TYPES.size(); i++){
        	typeChooser.addItem(TYPES.get(i));
    	}
        
        injectedLabel.setText(Local.getString("Injected"));
        injectedLabel.setMinimumSize(new Dimension(60, 16));
        injectedLabel.setPreferredSize(new Dimension(60, 16));
        removedLabel.setText(Local.getString("Removed"));
        removedLabel.setMinimumSize(new Dimension(60, 16));
        removedLabel.setPreferredSize(new Dimension(60, 16));
        injectedChooser.setBorder(textBorder);
        removedChooser.setBorder(textBorder);   
        
        for(int i = 0; i < PHASES.size(); i++){
        	injectedChooser.addItem(PHASES.get(i));
        	removedChooser.addItem(PHASES.get(i));
        }
        
        timeLabel.setText(Local.getString("Fix Time"));
        timeLabel.setMinimumSize(new Dimension(60, 16));
        timeLabel.setPreferredSize(new Dimension(60, 16));
        referenceLabel.setText(Local.getString("Reference ID"));
        referenceLabel.setMinimumSize(new Dimension(60, 16));
        referenceLabel.setPreferredSize(new Dimension(60, 16));
        timeField.setBorder(textBorder);
        timeField.setPreferredSize(new Dimension(30, 24));
        referenceField.setBorder(textBorder);
        referenceField.setPreferredSize(new Dimension(30, 24));
        
        if(t.equals("Edit Defect")){
	        DateFormat df = new SimpleDateFormat("MM/dd/yy"); 
	        Date defectDate = df.parse(currSelection[2]);
	        
	        idField.setText(currSelection[0]);
	        typeChooser.setSelectedIndex(TYPES.indexOf(currSelection[1]));
	        dateSpinner.getModel().setValue(defectDate);
	        injectedChooser.setSelectedIndex(PHASES.indexOf(currSelection[4]));
	        removedChooser.setSelectedIndex(PHASES.indexOf(currSelection[5]));
	        timeField.setText(currSelection[6]);
	        referenceField.setText(currSelection[7]);
	        descriptionField.setText(currSelection[8]);
        }
        
        centerPanel.setLayout(new GridLayout(0,2));
        centerPanel.add(typeLabel);
        centerPanel.add(typeChooser);
        centerPanel.add(injectedLabel);
        centerPanel.add(injectedChooser);
        centerPanel.add(removedLabel);
        centerPanel.add(removedChooser);
        centerPanel.add(timeLabel);
        centerPanel.add(timeField);
        centerPanel.add(referenceLabel);
        centerPanel.add(referenceField);
        
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
        
        upperPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        mainPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        
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
    	Date jdate = (Date) dateSpinner.getModel().getValue();
    	CalendarDate d = new CalendarDate(jdate);
    	String id = idField.getText();
    	String ty = typeChooser.getSelectedItem().toString();
    	String inj = injectedChooser.getSelectedItem().toString();
    	String rm = removedChooser.getSelectedItem().toString();
    	String fTime = timeField.getText();
    	String fRef = referenceField.getText();
    	String desc = descriptionField.getText();
    	
    	if(getTitle().equals("New Defect")){
    		CurrentProject.getDefectList().createDefect(d, id, ty, inj, rm, fTime, fRef, desc);
    	} else {
    		Defect defect = CurrentProject.getDefectList().getDefect(
    				currSelection[DefectsTable.ID_COL]);
    		defect.setID(id);
    		defect.setDefectType(ty);
    		defect.setDate(d.toString());
    		defect.setInjectedPhase(inj);
    		defect.setRemovedPhase(rm);
    		defect.setFixedTime(fTime);
    		defect.setFixRef(fRef);
    		defect.setDesc(desc);
    	}
    	
    	CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
        this.dispose();
    }
	
	void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
