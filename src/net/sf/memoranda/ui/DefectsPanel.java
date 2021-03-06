/*
 * DefectsPanel.java
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
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;

import net.sf.memoranda.util.CurrentStorage;
import net.sf.memoranda.util.CSVExport;
import net.sf.memoranda.util.Local;

public class DefectsPanel extends JPanel{
	
	private BorderLayout _borderLayout1 = new BorderLayout();
	private JToolBar _formToolBar = new JToolBar(); 
	private JLabel _defectLabel = new JLabel();
	private JButton _newDefectButton = new JButton();
	private JButton _editDefectButton = new JButton();
	private JButton _deleteDefectButton = new JButton();
	private JButton _exportDefectsButton = new JButton();
	private JScrollPane _scrollPane = new JScrollPane();
	DefectsTable _defectsTable = new DefectsTable();

	/*
	 * Constructor: Initiates default configuration of Defect display
	 */
	public DefectsPanel() {
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
		_scrollPane.getViewport().add(_defectsTable, null);
		
		_defectLabel.setText(Local.getString("PROJECT DEFECT LOG"));
		
		_formToolBar.addSeparator(new Dimension(8, 24));
		_formToolBar.add(_newDefectButton, null);
		_formToolBar.addSeparator(new Dimension(8, 24));
		_formToolBar.add(_editDefectButton, null);
		_formToolBar.addSeparator(new Dimension(8, 24));
		_formToolBar.add(_deleteDefectButton, null);
		_formToolBar.addSeparator(new Dimension(8, 24));
		_formToolBar.add(_exportDefectsButton, null);
		_formToolBar.addSeparator(new Dimension(240, 24));
		_formToolBar.add(_defectLabel);
		
		this.add(_formToolBar, BorderLayout.NORTH);		
		
		_formToolBar.setFloatable(false);
		_newDefectButton.setEnabled(true);
		_newDefectButton.setMaximumSize(new Dimension(24, 24));
		_newDefectButton.setToolTipText("New Defect");
		_newDefectButton.setRequestFocusEnabled(false);
		_newDefectButton.setPreferredSize(new Dimension(24, 24));
		_newDefectButton.setFocusable(false);
		_newDefectButton.setBorderPainted(false);
		_newDefectButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_newDefectButtonClicked(e);
			}
		});
		_newDefectButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
						"resources/icons/addresource.png")));
		
		_editDefectButton.setEnabled(true);
		_editDefectButton.setMaximumSize(new Dimension(24, 24));
		_editDefectButton.setToolTipText("Edit Defect");
		_editDefectButton.setRequestFocusEnabled(false);
		_editDefectButton.setPreferredSize(new Dimension(24, 24));
		_editDefectButton.setFocusable(false);
		_editDefectButton.setBorderPainted(false);
		_editDefectButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_editDefectButtonClicked(e);
			}
		});
		_editDefectButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
						"resources/icons/editproject.png")));
		
		_deleteDefectButton.setEnabled(true);
		_deleteDefectButton.setMaximumSize(new Dimension(24, 24));
		_deleteDefectButton.setToolTipText("Delete Defect");
		_deleteDefectButton.setRequestFocusEnabled(false);
		_deleteDefectButton.setPreferredSize(new Dimension(24, 24));
		_deleteDefectButton.setFocusable(false);
		_deleteDefectButton.setBorderPainted(false);
		_deleteDefectButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_deleteDefectButtonClicked(e);
			}
		});
		_deleteDefectButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource(
						"resources/icons/removeresource.png")));
		
		_exportDefectsButton.setEnabled(true);
		_exportDefectsButton.setMaximumSize(new Dimension(24, 24));
		_exportDefectsButton.setToolTipText("Export Defects to CSV");
		_exportDefectsButton.setRequestFocusEnabled(false);
		_exportDefectsButton.setPreferredSize(new Dimension(24, 24));
		_exportDefectsButton.setFocusable(false);
		_exportDefectsButton.setBorderPainted(false);
		_exportDefectsButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_exportDefectsButtonClicked(e);
			}
		});
		_exportDefectsButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/export.png")));
		
		_defectsTable.setMaximumSize(new Dimension(32767, 32767));
        _defectsTable.setRowHeight(24);
        this.add(_scrollPane);
	}
	
	/*
	 * Opens Defect dialog to enter new defect
	 */
	public void _newDefectButtonClicked(ActionEvent e) {
		DefectDialog dDlg = new DefectDialog(App.getFrame(), Local.getString("New Defect"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dDlg.setLocation((frmSize.width - dDlg.getSize().width)/2 + loc.x, 
				(frmSize.height - dDlg.getSize().height) / 2 + loc.y);
		dDlg.setVisible(true);
		if(dDlg.CANCELLED){
			return;
		} else {
			CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
			_defectsTable.tableChanged();
		}
	}
	
	/*
	 * Opens Defect dialog to edit selected defect
	 */
	public void _editDefectButtonClicked(ActionEvent e) {
		if (_defectsTable.hasSelection()) {
			DefectDialog dDlg = new DefectDialog(App.getFrame(), 
					Local.getString("Edit Defect"), 
					_defectsTable.getCurrentSelection());
			Dimension frmSize = App.getFrame().getSize();
			Point loc = App.getFrame().getLocation();
			dDlg.setLocation((frmSize.width - dDlg.getSize().width)/2 + loc.x, 
					(frmSize.height - dDlg.getSize().height) / 2 + loc.y);
			dDlg.setVisible(true);
			if(dDlg.CANCELLED){
				return;
			} else {
				CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
				int lastSelectedRow = _defectsTable.getLastSelectedRow();
				_defectsTable.tableChanged();
				_defectsTable.getSelectionModel().setSelectionInterval(
						lastSelectedRow, 
						lastSelectedRow);
			}
		}
	}
	
	public void _deleteDefectButtonClicked(ActionEvent e) {
		if (_defectsTable.hasSelection()) {
			int toRemove = _defectsTable.getSelectedRow();
	        String msg = "";
            msg =
                Local.getString("Delete defect: ")
                    + "\n'"
                    + _defectsTable.getModel().getValueAt(toRemove, 0)
                    + "'"
            		+ "\n"
            		+ Local.getString("Are you sure?");
	        int n =
	            JOptionPane.showConfirmDialog(
	                App.getFrame(),
	                msg,
	                Local.getString("Delete defect"),
	                JOptionPane.YES_NO_OPTION);
	        if (n != JOptionPane.YES_OPTION) {
	            return;
	        }
	        /*System.out.println(_defectsTable.getModel().getValueAt(
	        		_defectsTable.getSelectedRow(), DefectsTable.ID_COL).toString());*/
	        Defect d1 = CurrentProject.getDefectList().getDefect(
	                _defectsTable.getModel().getValueAt(
	                		_defectsTable.getSelectedRow(), 
	                		DefectsTable.ID_COL).toString());
	        CurrentProject.getDefectList().removeDefect(d1);
	        _defectsTable.tableChanged();
	        CurrentStorage.get().storeDefectList(CurrentProject.getDefectList(), CurrentProject.get());
		}
	}
	
	public void _exportDefectsButtonClicked(ActionEvent e) {
		
		String filename = CurrentProject.get().getID() + "_Defects.csv";
		JFileChooser fileDialog = new JFileChooser();
		File selectedFile = new File(filename);
		
		
		fileDialog.setDialogTitle("Export Defects to CSV File");
		fileDialog.setSelectedFile(selectedFile);
		int option = fileDialog.showSaveDialog(null);
	   if (option != JFileChooser.APPROVE_OPTION)
	      return;  // User canceled or clicked the dialog's close box.
	   selectedFile = fileDialog.getSelectedFile();
	   if (selectedFile.exists()) {  // Ask the user whether to replace the file.
	      int response = JOptionPane.showConfirmDialog(this,
	            "The file \"" + selectedFile.getName()
	                + "\" already exists.\nDo you want to replace it?", 
	            "Confirm Save",
	            JOptionPane.YES_NO_OPTION, 
	            JOptionPane.WARNING_MESSAGE );
	      if (response != JOptionPane.YES_OPTION)
	         return;  // User does not want to replace the file.
	   }
	   
	   try {
		   CSVExport.export("defect", selectedFile);
		   JOptionPane.showMessageDialog(this, "CSV File Saved", "CSV Export Succeeded", JOptionPane.INFORMATION_MESSAGE);
	   }
	   catch (FileNotFoundException fnfe) {
		   JOptionPane.showMessageDialog(this, "The selected file could not be created",
				   "CSV Export Failed", JOptionPane.ERROR_MESSAGE);
		   return;
	   }
		   
	}
}
