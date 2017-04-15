/*
 * TimesPanel.java
 * Represents the display for the PSP TimeEntry Log
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.util.Local;

public class TimesPanel extends JPanel{
	
	private BorderLayout _borderLayout1 = new BorderLayout();
	private JToolBar _formToolBar = new JToolBar();
	private JLabel _timeEntryLabel = new JLabel();
	private JButton _newTimeButton = new JButton();
	private JButton _editTimeButton = new JButton();
	private JScrollPane _scrollPane = new JScrollPane();
	TimeEntryTable _timeEntryTable = new TimeEntryTable();

	/*
	 * Constructor: Initiates default configuration of TimeEntry display
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
	 * Initiates components for TimeEntry display
	 * 
	 * @throws Exception generic Exception
	 */
	public void jbInit() throws Exception {
		this.setLayout(_borderLayout1);
		_scrollPane.getViewport().setBackground(Color.WHITE);
		_scrollPane.getViewport().add(_timeEntryTable, null);
		
		_timeEntryLabel.setText(Local.getString("PROJECT TIME LOG"));
		
		_formToolBar.addSeparator(new Dimension(8, 24));
		_formToolBar.add(_newTimeButton, null);
		_formToolBar.addSeparator(new Dimension(8, 24));
		_formToolBar.add(_editTimeButton, null);
		_formToolBar.addSeparator(new Dimension(240, 24));
		_formToolBar.add(_timeEntryLabel);
		
		this.add(_formToolBar, BorderLayout.NORTH);		
		
		_formToolBar.setFloatable(false);
		_newTimeButton.setEnabled(true);
		_newTimeButton.setMaximumSize(new Dimension(24, 24));
		_newTimeButton.setToolTipText("New Time");
		_newTimeButton.setRequestFocusEnabled(false);
		_newTimeButton.setPreferredSize(new Dimension(24, 24));
		_newTimeButton.setFocusable(false);
		_newTimeButton.setBorderPainted(false);
		_newTimeButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_newTimeButtonClicked(e);
			}
		});
		_newTimeButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/addresource.png")));
		
		_editTimeButton.setEnabled(true);
		_editTimeButton.setMaximumSize(new Dimension(24, 24));
		_editTimeButton.setToolTipText("Edit Time");
		_editTimeButton.setRequestFocusEnabled(false);
		_editTimeButton.setPreferredSize(new Dimension(24, 24));
		_editTimeButton.setFocusable(false);
		_editTimeButton.setBorderPainted(false);
		_editTimeButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_editTimeButtonClicked(e);
			}
		});
		_editTimeButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/editproject.png")));
		
		_timeEntryTable.setMaximumSize(new Dimension(32767, 32767));
        _timeEntryTable.setRowHeight(24);
        this.add(_scrollPane);
	}
	
	/*
	 * Opens TimeEntryDialog to enter new time entry
	 * 
	 */
	public void _newTimeButtonClicked(ActionEvent e){
		TimeEntryDialog dDlg = new TimeEntryDialog(App.getFrame(), Local.getString("New Time"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dDlg.setLocation((frmSize.width - dDlg.getSize().width)/2 + loc.x, (frmSize.height - dDlg.getSize().height) / 2 + loc.y);
		dDlg.setVisible(true);
		if(dDlg.CANCELLED){
			return;
		} else {
			_timeEntryTable.tableChanged();
		}
	}
	
	/*
	 * Opens TimeEntryDialog to edit selected time entry
	 * 
	 */
	public void _editTimeButtonClicked(ActionEvent e){
		TimeEntryDialog dDlg = new TimeEntryDialog(App.getFrame(), Local.getString("Edit Time"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dDlg.setLocation((frmSize.width - dDlg.getSize().width)/2 + loc.x, (frmSize.height - dDlg.getSize().height) / 2 + loc.y);
		dDlg.setVisible(true);
		if(dDlg.CANCELLED){
			return;
		} else {
			_timeEntryTable.tableChanged();
		}
	}

}
