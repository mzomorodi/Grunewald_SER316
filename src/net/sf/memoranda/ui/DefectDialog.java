/*
 * DefectDialog.java
 * Dialog window for entering a new defect
 * 
 * @author mzomorod
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

public class DefectDialog extends JDialog{
	
	// Window components
	private JPanel mainPanel = new JPanel();
	private JPanel upperPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel descriptionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel labelsPanel = new JPanel();
	private JPanel fieldsPanel = new JPanel();
	private JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
	private JButton okB = new JButton();
    private JButton cancelB = new JButton();
	public boolean CANCELLED = true;
	private Border textBorder = BorderFactory.createEtchedBorder(Color.white, 
            new Color(178, 178, 178));
	
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
    private String[] types = new String[]{Local.getString("10: Documentation"),
    		Local.getString("20: Syntax"), Local.getString("30: Build, Package"),
    		Local.getString("40: Assignment"), Local.getString("50: Interface"),
    		Local.getString("60: Checking"), Local.getString("70: Data"),
    		Local.getString("80: Function"), Local.getString("90: System"),
    		Local.getString("100: Environment")};
    
    // Injected and Removed Components
    private JLabel injectedLabel = new JLabel();
    private JLabel removedLabel = new JLabel();
    private JComboBox injectedChooser = new JComboBox();
    private JComboBox removedChooser = new JComboBox();
    public String[] phases = new String[]{Local.getString("PLANNING"),
    		Local.getString("DESIGN"), Local.getString("CODE"),
    		Local.getString("CODE_REVIEW"), Local.getString("COMPILE"),
    		Local.getString("TEST"), Local.getString("POSTMORTEM")};
    
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
            	
            	SpinnerDateModel sdm = new SpinnerDateModel((Date)dateSpinner.getModel().getValue(),null,null,Calendar.DAY_OF_WEEK);
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
        
        descriptionLabel.setMaximumSize(new Dimension(100, 16));
    	descriptionLabel.setMinimumSize(new Dimension(60, 16));
    	descriptionLabel.setText(Local.getString("Description"));
    	descriptionField.setPreferredSize(new Dimension(375, 387));
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);        
        descriptionScrollPane.setPreferredSize(new Dimension(375,100));
        descriptionPanel.setLayout(new BorderLayout());
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);
        descriptionPanel.add(descriptionScrollPane, BorderLayout.SOUTH);
        descriptionPanel.setPreferredSize(new Dimension(400, 130));
        
        upperPanel.setLayout(new BorderLayout());
        upperPanel.add(datePanel, BorderLayout.NORTH);
        upperPanel.add(descriptionPanel, BorderLayout.SOUTH);
        
        typeLabel.setBorder(textBorder);
        typeLabel.setText(Local.getString("Defect Type"));
        typeLabel.setMinimumSize(new Dimension(60, 16));
        typeLabel.setPreferredSize(new Dimension(60, 16));
        typeChooser.setBorder(textBorder);
        if(t.equals("New Defect")){        	        	
	        for(int i = 0; i < types.length; i++){
	        	typeChooser.addItem(types[i]);
        	}
        }
        
        injectedLabel.setText(Local.getString("Injected"));
        injectedLabel.setMinimumSize(new Dimension(60, 16));
        injectedLabel.setPreferredSize(new Dimension(60, 16));
        removedLabel.setText(Local.getString("Injected"));
        removedLabel.setMinimumSize(new Dimension(60, 16));
        removedLabel.setPreferredSize(new Dimension(60, 16));
        injectedChooser.setBorder(textBorder);
        removedChooser.setBorder(textBorder);
        if(t.equals("New Defect")){        	        	
	        for(int i = 0; i < phases.length; i++){
	        	injectedChooser.addItem(phases[i]);
	        	removedChooser.addItem(phases[i]);
        	}
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
        
        labelsPanel.setLayout(new BorderLayout());
        labelsPanel.add(typeLabel, BorderLayout.WEST);
        labelsPanel.add(injectedLabel, BorderLayout.WEST);
        labelsPanel.add(removedLabel, BorderLayout.WEST);
        labelsPanel.add(timeLabel, BorderLayout.WEST);
        labelsPanel.add(referenceLabel, BorderLayout.WEST);
        labelsPanel.setPreferredSize(new Dimension(80, 100));
        
        fieldsPanel.setLayout(new BorderLayout());
        fieldsPanel.add(typeChooser, BorderLayout.WEST);
        fieldsPanel.add(injectedChooser, BorderLayout.WEST);
        fieldsPanel.add(removedChooser, BorderLayout.WEST);
        fieldsPanel.add(timeField, BorderLayout.WEST);
        fieldsPanel.add(referenceField, BorderLayout.WEST);
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
    	CurrentProject.getDefectList().createDefect();
    	CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
        this.dispose();
    }
	
	void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}
