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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.TimeEntry;
import net.sf.memoranda.util.Local;

public class TimesPanel extends JPanel{
	
	private BorderLayout _borderLayout1 = new BorderLayout();
	private JToolBar _formToolBar = new JToolBar();
	private JLabel _timeEntryLabel = new JLabel();
	private JButton _newTimeButton = new JButton();
	private JButton _editTimeButton = new JButton();
	private JButton _deleteTimeEntryButton = new JButton();
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
		_formToolBar.addSeparator(new Dimension(8, 24));
		_formToolBar.add(_deleteTimeEntryButton, null);
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
		
		_deleteTimeEntryButton.setEnabled(true);
		_deleteTimeEntryButton.setMaximumSize(new Dimension(24, 24));
		_deleteTimeEntryButton.setToolTipText("Delete Entry");
		_deleteTimeEntryButton.setRequestFocusEnabled(false);
		_deleteTimeEntryButton.setPreferredSize(new Dimension(24, 24));
		_deleteTimeEntryButton.setFocusable(false);
		_deleteTimeEntryButton.setBorderPainted(false);
		_deleteTimeEntryButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_deleteTimeEntryButtonClicked(e);
			}
		});
		_deleteTimeEntryButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/removeresource.png")));
		
		_timeEntryTable.setMaximumSize(new Dimension(32767, 32767));
        _timeEntryTable.setRowHeight(24);
        this.add(_scrollPane);
	}
	
	/*
	 * Opens TimeEntryDialog to enter new time entry
	 * 
	 */
	public void _newTimeButtonClicked(ActionEvent e){
		TimeEntryDialog dDlg = new TimeEntryDialog(App.getFrame(), Local.getString("New Time"), _timeEntryTable.getCurrentSelection());
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
		if (_timeEntryTable.hasSelection()) {
			TimeEntryDialog tDlg = new TimeEntryDialog(App.getFrame(), Local.getString("Edit Time"), _timeEntryTable.getCurrentSelection());
			Dimension frmSize = App.getFrame().getSize();
			Point loc = App.getFrame().getLocation();
			tDlg.setLocation((frmSize.width - tDlg.getSize().width)/2 + loc.x, (frmSize.height - tDlg.getSize().height) / 2 + loc.y);
			tDlg.setVisible(true);
			if(tDlg.CANCELLED){
				return;
			} else {
				int lastSelectedRow = _timeEntryTable.getLastSelectedRow();
				_timeEntryTable.tableChanged();
				_timeEntryTable.getSelectionModel().setSelectionInterval(lastSelectedRow, lastSelectedRow);
			}
		}
	}
	
	/*
	 * Deletes time entry
	 */
	public void _deleteTimeEntryButtonClicked(ActionEvent e){
		if (_timeEntryTable.hasSelection()) {
			int toRemove = _timeEntryTable.getSelectedRow();
	        String msg = "";
            msg =
                Local.getString("Delete entry: ")
                    + "\n'"
                    + _timeEntryTable.getModel().getValueAt(toRemove, 0)
                    + "'"
            		+ "\n"
            		+ Local.getString("Are you sure?");
	        int n =
	            JOptionPane.showConfirmDialog(
	                App.getFrame(),
	                msg,
	                Local.getString("Delete entry"),
	                JOptionPane.YES_NO_OPTION);
	        if (n != JOptionPane.YES_OPTION)
	            return;
	        /*System.out.println(_timeEntryTable.getModel().getValueAt(
	        		_timeEntryTable.getSelectedRow(), TimeEntryTable.ID_COL).toString());*/
	        TimeEntry te1 = CurrentProject.getTimeEntryList().getTimeEntry(
	        		_timeEntryTable.getModel().getValueAt(
	                		_timeEntryTable.getSelectedRow(), TimeEntryTable.ID_COL).toString());
	        CurrentProject.getTimeEntryList().removeTimeEntry(te1);
	        _timeEntryTable.tableChanged();
		}
	}
}
