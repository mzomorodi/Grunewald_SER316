package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;

import net.sf.memoranda.Defect;
import net.sf.memoranda.Task;
import net.sf.memoranda.Defect.Phase;
import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;

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
    
    private JComboBox _taskChooser = new JComboBox();
    private JComboBox _typeChooser = new JComboBox();
    private JComboBox _injPhaseChooser = new JComboBox();
    private JComboBox _remPhaseChooser = new JComboBox();
    private JTextField _defID = new JTextField();
    private JTextField _fixTime = new JTextField();
    private JTextField _fixID = new JTextField();
    private JTextField _desc = new JTextField();
    
    private JPanel _areaPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints _gbc;
    private JLabel _jLabel1 = new JLabel();
    private JPanel _buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    private JButton _okB = new JButton();
    private JButton _cancelB = new JButton();
    public boolean CANCELLED = true;
    
    int id; 
    Task t; 
    CalendarDate d;
    int type;
    Phase iPh;
    Phase rPh;
    int fTime;
    int fRef;
    String desc;
    
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
        
// NEED TO ADD THE DEFECT INPUT BOXES HERE:
        List<String> l = Defect.getValues();
        String[] arr = l.toArray(new String[0]);
        for(int i = 0; i < arr.length; i++){
        	_typeChooser.addItem(arr[i]);
        }
        _typeChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                typeChosen_actionPerformed(e);
            }
        });
 
        
        
// ---------------------------------        
        
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
