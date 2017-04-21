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

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;

import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.Util;


/**
 * File: AddFormDialog.java
 * @author Avery Bowen
 * Date: 4/9/17
 * 
 * Description: Adds the dialog boxes for new & edit forms
 *
 */
public class AddFormDialog extends JDialog {
	private JPanel _dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JLabel _header = new JLabel();
    private JRadioButton _locTrackFormB = new JRadioButton();
    private JRadioButton _timeTrackFormB = new JRadioButton();
    private JRadioButton _defTrackFormB = new JRadioButton();
    private JRadioButton _prodTrackFormB = new JRadioButton();
    
    private ButtonGroup _buttonGroup1 = new ButtonGroup();
    private JPanel _areaPanel = new JPanel(new GridBagLayout());
    private GridBagConstraints _gbc;
    private JLabel _jLabel1 = new JLabel();
    private JPanel _buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    private JButton _okB = new JButton();
    private JButton _cancelB = new JButton();
    public boolean CANCELLED = true;
    
    public AddFormDialog(Frame frame, String title) {
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
        _buttonGroup1.add(_locTrackFormB);
        _buttonGroup1.add(_timeTrackFormB);
        _buttonGroup1.add(_defTrackFormB);
        _buttonGroup1.add(_prodTrackFormB);
           
        if(t.equals("New Form")){
        	_locTrackFormB.setText(Local.getString("Start new LOC tracking form"));
        	_timeTrackFormB.setText(Local.getString("Start new time tracking form"));
        	_defTrackFormB.setText(Local.getString("Start new defect tracking form"));
        	_prodTrackFormB.setText(Local.getString("Start new productivity tracking form"));
        	
        }
        if(t.equals("Edit Form")){
        	_locTrackFormB.setText(Local.getString("Edit LOC tracking form"));
        	_timeTrackFormB.setText(Local.getString("Edit time tracking form"));
        	_defTrackFormB.setText(Local.getString("Edit defect tracking form"));
        	_prodTrackFormB.setText(Local.getString("Edit productivity tracking form"));
        }
        
        
        _gbc = new GridBagConstraints();
        _gbc.gridwidth = 2;
        _gbc.gridx = 0; _gbc.gridy = 0;
        _gbc.insets = new Insets(10, 15, 5, 15);
        _gbc.anchor = GridBagConstraints.WEST;
        _gbc.fill = GridBagConstraints.HORIZONTAL;
        _areaPanel.add(_locTrackFormB, _gbc);
        _areaPanel.add(_timeTrackFormB, _gbc);
        _areaPanel.add(_defTrackFormB, _gbc);
        _areaPanel.add(_prodTrackFormB, _gbc);
        
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
    	
    } //End jbInit()
    
    /**
	 * set CANCELLED variable to false so we can know the user 
	 * pressed the ok buton and close this dialog.
	 */
	 
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
    
    void newForm_actionPerformed(ActionEvent e){
    	enableFields();
    	Util.debug("clicked the new button");
    }
    
    void editForm_actionPerformed(ActionEvent e){
    	enableFields();
    	Util.debug("clicked the edit button");
    }


}
