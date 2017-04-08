/**
 * 
 */
package net.sf.memoranda.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import net.sf.memoranda.util.Util;


/**
 * @author Avery
 * 
 */
public class FormPanel extends JPanel {
	private BorderLayout _borderLayout1 = new BorderLayout();
	private JToolBar _formToolBar = new JToolBar(); 
	private JButton _newFormButton = new JButton();
	private JButton _editFormButton = new JButton();
	private JScrollPane _scrollPane = new JScrollPane();
	private JMenuItem _newForm = new JMenuItem();
	private JMenuItem _editForm = new JMenuItem();
	public JPopupMenu formPopMenu = new JPopupMenu();
		
	public FormPanel(){
		try{
			jbInit();
		}
		catch(Exception e){
			new ExceptionDialog(e);
		}
	}
	
	public void jbInit() throws Exception{
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
		_newFormButton.setToolTipText("New Form");
		_newFormButton.setRequestFocusEnabled(false);
		_newFormButton.setPreferredSize(new Dimension(24, 24));
		_newFormButton.setFocusable(false);
		_newFormButton.setBorderPainted(false);
		_newFormButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_newFormButtonClicked(e);
			}
		});
		_newFormButton.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/addresource.png")));
		_newFormButton.setToolTipText("Start a new form");
		
		_editFormButton.setEnabled(true);
		_editFormButton.setMaximumSize(new Dimension(24, 24));
		_editFormButton.setToolTipText("New Form");
		_editFormButton.setRequestFocusEnabled(false);
		_editFormButton.setPreferredSize(new Dimension(24, 24));
		_editFormButton.setFocusable(false);
		_editFormButton.setBorderPainted(false);
		_editFormButton.addActionListener(new java.awt.event.ActionListener(){
			public void actionPerformed(ActionEvent e){
				_editFormButtonClicked(e);
			}
		});
		_editFormButton.setIcon(new ImageIcon(net.sf.memoranda.ui.AppFrame.class.getResource("resources/icons/editproject.png")));
		_editFormButton.setToolTipText("Edit form");
		
		
	}
	
	public void _newFormButtonClicked(ActionEvent e){
		Util.debug("Clicked the new form button");
	}
	
	public void _editFormButtonClicked(ActionEvent e){
		Util.debug("Clicked the edit form button");
	}
	
}
