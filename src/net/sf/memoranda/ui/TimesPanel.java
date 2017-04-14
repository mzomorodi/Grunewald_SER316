/*
 * TimesPanel.java
 * Represents the display for the PSP Defect Log.
 * 
 * @author mzomorod
 */

package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.util.Local;

public class TimesPanel extends JPanel{
	
	private BorderLayout _borderLayout1 = new BorderLayout();
	private JToolBar _formToolBar = new JToolBar(); 
	private JButton _newFormButton = new JButton();
	private JButton _editFormButton = new JButton();
	private JScrollPane _scrollPane = new JScrollPane();
	//DefectsTable _defectsTable = new DefectsTable();

	/*
	 * Constructor: Initiates default configuration of Defect display
	 */
	public TimesPanel() {
		try{
			jbInit();
		}
		catch(Exception e){
			new ExceptionDialog(e);
		}
	}
	
	/*
	 * Initiates components for Defect display
	 * 
	 * @throws Exception generic Exception
	 */
	public void jbInit() throws Exception {
		this.setLayout(_borderLayout1);
		_scrollPane.getViewport().setBackground(Color.WHITE);
		
		_formToolBar.addSeparator(new Dimension(8, 24));
		_formToolBar.add(_newFormButton, null);
		_formToolBar.addSeparator(new Dimension(8, 24));
		_formToolBar.add(_editFormButton, null);
		this.add(_formToolBar, BorderLayout.NORTH);		
		
		_formToolBar.setFloatable(false);
		_newFormButton.setEnabled(true);
		_newFormButton.setMaximumSize(new Dimension(24, 24));
		_newFormButton.setToolTipText("New Time");
		_newFormButton.setRequestFocusEnabled(false);
		_newFormButton.setPreferredSize(new Dimension(24, 24));
		_newFormButton.setFocusable(false);
		_newFormButton.setBorderPainted(false);
		_newFormButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_newFormButtonClicked(e);
			}
		});
		_newFormButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/addresource.png")));
		
		_editFormButton.setEnabled(true);
		_editFormButton.setMaximumSize(new Dimension(24, 24));
		_editFormButton.setToolTipText("Edit Time");
		_editFormButton.setRequestFocusEnabled(false);
		_editFormButton.setPreferredSize(new Dimension(24, 24));
		_editFormButton.setFocusable(false);
		_editFormButton.setBorderPainted(false);
		_editFormButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_editFormButtonClicked(e);
			}
		});
		_editFormButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/editproject.png")));
		
		//_defectsTable.setMaximumSize(new Dimension(32767, 32767));
       // _defectsTable.setRowHeight(24);
        //this.add(_defectsTable);
	}
	
	/*
	 * Opens Defect dialog to enter new defect
	 * 
	 * NEEDS TIMEFORMDIALOG ADDED
	 */
	public void _newFormButtonClicked(ActionEvent e){
		DefectFormDialog dDlg = new DefectFormDialog(App.getFrame(), Local.getString("New Time"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dDlg.setLocation((frmSize.width - dDlg.getSize().width)/2 + loc.x, (frmSize.height - dDlg.getSize().height) / 2 + loc.y);
		dDlg.setVisible(true);
		if(dDlg.CANCELLED){
			return;
		} else {
			//_defectsTable.tableChanged();
		}
	}
	
	/*
	 * Opens Defect dialog to edit selected defect
	 * 
	 * NEEDS TIMEFORMDIALOG ADDED
	 */
	public void _editFormButtonClicked(ActionEvent e){
		DefectFormDialog dDlg = new DefectFormDialog(App.getFrame(), Local.getString("Edit Time"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dDlg.setLocation((frmSize.width - dDlg.getSize().width)/2 + loc.x, (frmSize.height - dDlg.getSize().height) / 2 + loc.y);
		dDlg.setVisible(true);
		if(dDlg.CANCELLED){
			return;
		} else {
			//_defectsTable.tableChanged();
		}
	}

}
