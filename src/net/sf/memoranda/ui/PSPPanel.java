/**
 * PSPPanel.java
 * 
 * @author mzomorod
 */
package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import net.sf.memoranda.util.Local;

/**
 * The PSP Panel represents the container for creating
 * Defect and Time forms for the current Project.
 */
public class PSPPanel extends JPanel {
	
	JPanel formPanel = new JPanel();
	DefectsPanel defectsPanel = new DefectsPanel();
	TimesPanel timesPanel = new TimesPanel();
	JToolBar newFormToolBar = new JToolBar();
	JButton defectB = new JButton();
	JButton timeB = new JButton();
	CardLayout cardLayout1 = new CardLayout();
	BorderLayout borderLayout1 = new BorderLayout();

	/**
	 * Constructor- used to configure panel
	 */
	public PSPPanel() {
		try {
            jbInit();
        	}
        catch (Exception ex) {
            ex.printStackTrace();
        	}
	}
	
	/**
	 * Instantiates all objects associated with the PSP panel
	 * 
	 * @throws Exception
	 */
	void jbInit() throws Exception {
		
		defectB.setIcon(
	            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource(
	            		"resources/icons/psp_defect.png")));
        defectB.setEnabled(true);
        defectB.setMaximumSize(new Dimension(120, 30));
        defectB.setMinimumSize(new Dimension(26, 32));
        defectB.setToolTipText(Local.getString("Open Defect View"));
        defectB.setRequestFocusEnabled(false);
        defectB.setPreferredSize(new Dimension(120, 30));
        defectB.setFocusable(false);
        defectB.setText("Defect Log");
        defectB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                defectB_actionPerformed(e);
            }
        });
        defectB.setBorderPainted(false);
        
        timeB.setIcon(
	            new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource(
	            		"resources/icons/psp_time.png")));
        timeB.setEnabled(true);
        timeB.setMaximumSize(new Dimension(120, 30));
        timeB.setMinimumSize(new Dimension(26, 32));
        timeB.setToolTipText(Local.getString("Open Time View"));
        timeB.setRequestFocusEnabled(false);
        timeB.setPreferredSize(new Dimension(120, 30));
        timeB.setFocusable(false);
        timeB.setText("Time Log");
        timeB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeB_actionPerformed(e);
            }
        });
        timeB.setBorderPainted(false);		
		
		newFormToolBar.setFloatable(false);
		newFormToolBar.addSeparator(new Dimension(8, 24));
		newFormToolBar.add(defectB, null);
		newFormToolBar.addSeparator(new Dimension(8, 24));
		newFormToolBar.add(timeB, null);
		
		this.setLayout(borderLayout1);
		
		formPanel.setLayout(cardLayout1);
		formPanel.add(defectsPanel, "DEFECTS");
		formPanel.add(timesPanel, "TIMES");
		
		this.add(newFormToolBar, BorderLayout.NORTH);
		this.add(formPanel, BorderLayout.CENTER);
		
	}
	
	/**
	 * Toggles PSP panel to display Defect log 
	 * 
	 * @param e the event associated with this action
	 */
	public void defectB_actionPerformed(ActionEvent e) {
		cardLayout1.show(formPanel, "DEFECTS");
	}
	
	/**
	 * Toggles PSP panel to display Time log
	 * 
	 * @param ethe event associated with this action
	 */
	public void timeB_actionPerformed(ActionEvent e) {
		cardLayout1.show(formPanel, "TIMES");
	}

}
