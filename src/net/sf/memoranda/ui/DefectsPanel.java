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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.util.Local;

public class DefectsPanel extends JPanel{
	
	private BorderLayout _borderLayout1 = new BorderLayout();
	private JToolBar _formToolBar = new JToolBar(); 
	private JLabel _defectLabel = new JLabel();
	private JButton _newDefectButton = new JButton();
	private JButton _editDefectButton = new JButton();
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
		_newDefectButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_newDefectButtonClicked(e);
			}
		});
		_newDefectButton.setIcon(new ImageIcon(
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/addresource.png")));
		
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
				net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/editproject.png")));
		
		_defectsTable.setMaximumSize(new Dimension(32767, 32767));
        _defectsTable.setRowHeight(24);
        this.add(_scrollPane);
	}
	
	/*
	 * Opens Defect dialog to enter new defect
	 */
	public void _newDefectButtonClicked(ActionEvent e){
		DefectDialog dDlg = new DefectDialog(App.getFrame(), Local.getString("New Defect"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dDlg.setLocation((frmSize.width - dDlg.getSize().width)/2 + loc.x, (frmSize.height - dDlg.getSize().height) / 2 + loc.y);
		dDlg.setVisible(true);
		if(dDlg.CANCELLED){
			return;
		} else {
			_defectsTable.initTable();
		}
	}
	
	/*
	 * Opens Defect dialog to edit selected defect
	 */
	public void _editDefectButtonClicked(ActionEvent e){
		DefectFormDialog dDlg = new DefectFormDialog(App.getFrame(), Local.getString("Edit Defect"));
		Dimension frmSize = App.getFrame().getSize();
		Point loc = App.getFrame().getLocation();
		dDlg.setLocation((frmSize.width - dDlg.getSize().width)/2 + loc.x, (frmSize.height - dDlg.getSize().height) / 2 + loc.y);
		dDlg.setVisible(true);
		if(dDlg.CANCELLED){
			return;
		} else {
			_defectsTable.initTable();
		}
	}

}