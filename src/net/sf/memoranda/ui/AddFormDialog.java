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
	JPanel dialogTitlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JLabel header = new JLabel();
    JButton newB = new JButton();
    ButtonGroup buttonGroup1 = new ButtonGroup();
    JPanel areaPanel = new JPanel(new GridBagLayout());
    GridBagConstraints gbc;
    JLabel jLabel1 = new JLabel();
    JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
    JButton okB = new JButton();
    JButton cancelB = new JButton();
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
        dialogTitlePanel.setBackground(Color.WHITE);
        dialogTitlePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        header.setFont(new java.awt.Font("Dialog", 0, 20));
        header.setForeground(new Color(0, 0, 124));
        header.setText(Local.getString(t));
        header.setIcon(new ImageIcon(net.sf.memoranda.ui.AddResourceDialog.class.getResource(
            "resources/icons/resource48.png")));
        dialogTitlePanel.add(header);
        this.getContentPane().add(dialogTitlePanel, BorderLayout.NORTH);
        
        buttonGroup1.add(newB);
        
        if(t.equals("New Form")){
        	newB.setText(Local.getString("Start new form"));
        	newB.addActionListener(new java.awt.event.ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			newForm_actionPerformed(e);
        		}
        	});
        }
        if(t.equals("Edit Form")){
        	newB.setText(Local.getString("Edit form"));
        	newB.addActionListener(new java.awt.event.ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			editForm_actionPerformed(e);
        		}
        	});
        }
        
        
        gbc = new GridBagConstraints();
        gbc.gridwidth = 2;
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 5, 15);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        areaPanel.add(newB, gbc);
        
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
        buttonsPanel.add(newB);
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
		enableFields();
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
    	
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
		 jLabel1.setEnabled(true);
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
