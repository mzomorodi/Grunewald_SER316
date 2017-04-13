package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;

/**
 * @author Avery Bowen
 * 
 * Description: A class to get information from user about indiv. defects,
 * 
 *
 */
public class DefectFormDialog extends JDialog{
	private JPanel _dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel _header = new JLabel();
    private CalendarDate _d = new CalendarDate();
    private JLabel _taskLab = new JLabel();
    private JComboBox _taskChooser = new JComboBox();
    private JLabel _typeLab = new JLabel();
    private JComboBox _typeChooser = new JComboBox();
    private JComboBox _injPhaseChooser = new JComboBox();
    private JComboBox _remPhaseChooser = new JComboBox();
    private JTextField _defID = new JTextField();
    private JTextField _fixTime = new JTextField();
    private JTextField _fixID = new JTextField();
    private JTextField _desc = new JTextField();
    
    private JPanel _areaPanel = new JPanel();
    private JLabel _jLabel1 = new JLabel();
    private JPanel _buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    private JButton _okB = new JButton();
    private JButton _cancelB = new JButton();
    public boolean CANCELLED = true;
    
    private String[] arr = new String[]{Local.getString("10: Documentation"), Local.getString("20: Syntax"), Local.getString("30: Build, Package"), Local.getString("40: Assignment"), 
    		Local.getString("50: Interface"), Local.getString("60: Checking"), Local.getString("70: Data"), Local.getString("80: Function"), Local.getString("90: System"), Local.getString("100: Environment")};
    public String[] _phases = new String[]{Local.getString("PLANNING"), Local.getString("DESIGN"), Local.getString("CODE"), Local.getString("CODE_REVIEW"), Local.getString("COMPILE"), Local.getString("TEST"), Local.getString("POSTMORTEM")};
    
    
    public DefectFormDialog(Frame frame, String title){
    	super(frame, title, true);
        try {
            jbInit(title);
            pack();
        }
        catch (Exception ex) {
            new ExceptionDialog(ex);
            ex.printStackTrace();
        }
    }
    
    public void jbInit(String t) throws Exception{
    	this.setResizable(false);
        _dialogTitlePanel.setBackground(Color.WHITE);
        _dialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        _header.setFont(new java.awt.Font("Dialog", 0, 20));
        _header.setForeground(new Color(0, 0, 124));
        _header.setText(Local.getString(t));
        _header.setIcon(new ImageIcon(net.sf.memoranda.ui.AddResourceDialog.class.getResource(
            "resources/icons/resource48.png")));
        _dialogTitlePanel.add(_header);
        this.getContentPane().add(_dialogTitlePanel, BorderLayout.NORTH);
        
        if(t.equals("New Defect")){
// NEED TO ADD THE DEFECT INPUT BOXES HERE (something like this?):
        	
	        for(int i = 0; i < arr.length; i++){
	        	_typeChooser.addItem(arr[i]);
	        }
	        _typeLab.setText("Select defect type: ");
	        
	        
	        
	        _areaPanel.add(_typeLab);
	        _areaPanel.add(_typeChooser);
	        this.getContentPane().add(_areaPanel, BorderLayout.CENTER);

// ---------------------------------
        }
        
        _okB.setEnabled(true);
        _okB.setMaximumSize(new Dimension(100, 26));
        _okB.setMinimumSize(new Dimension(100, 26));
        _okB.setPreferredSize(new Dimension(100, 26));
        _okB.setText(Local.getString("Ok"));
        _okB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(_okB);
        _cancelB.setMaximumSize(new Dimension(100, 26));
        _cancelB.setMinimumSize(new Dimension(100, 26));
        _cancelB.setPreferredSize(new Dimension(100, 26));
        _cancelB.setText(Local.getString("Cancel"));
        _cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        
        
        _buttonsPanel.add(_okB);
        _buttonsPanel.add(_cancelB);
		enableFields();
        this.getContentPane().add(_buttonsPanel, BorderLayout.SOUTH);
        
    }
    
    void okB_actionPerformed(ActionEvent e) {
    	CANCELLED = false;
        this.dispose();
    }

	/**
	 * close the dialog window
	 */
	 
    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
    
    void enableFields() {
		 _jLabel1.setEnabled(true);
	}
    
    void typeChosen_actionPerformed(ActionEvent e){
    	
    }

}
