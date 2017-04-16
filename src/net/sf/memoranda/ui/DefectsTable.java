package net.sf.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.TimeEntryList;
import net.sf.memoranda.ui.table.TableSorter;
import net.sf.memoranda.util.Local;

/**
 * @author Matthew Seiler
 * 
 * Description: A class for presenting a list of defects in a table.
 *
 */
public class DefectsTable extends JTable{
	
	private Vector<Defect> _defects = null;
	private TableSorter _sorter = null;
	private ListSelectionModel _listSelectionModel = null;
	private boolean _hasSelection;
	private int _lastSelectedRow;
	private String[] _currentSelection = {"","","","","","","","","",""};
	
	/**
	 * Constructor for DefectFormTable.
	 */
	public DefectsTable() {
		super();
		
		_hasSelection = false;

		/* table properties */
		setCellSelectionEnabled(true);
		setRowHeight(30);
		setFont(new Font("Dialog",0,11));
		
		initTable();
		_sorter = new TableSorter(new DefectsTableModel());
		_sorter.addMouseListenerToHeaderInTable(this);
		setModel(_sorter);
		
        _listSelectionModel = getSelectionModel();
        _listSelectionModel.addListSelectionListener(new TableListSelectionHandler());
        setSelectionModel(_listSelectionModel);
        
        addMouseListener(new MouseTableListener());
      
        CurrentProject.addProjectListener(new ProjectListener() {
        	public void projectChange(Project prj, NoteList nl, TaskList tl, ResourcesList rl, DefectList dl,
					TimeEntryList tel) {}

			public void projectWasChanged() {
				tableChanged();
			}			
        });
	}
	
	public boolean hasSelection() {
		return _hasSelection;
	}
	
	public int getLastSelectedRow() {
		return _lastSelectedRow;
	}
	
	public String[] getCurrentSelection() {
		return _currentSelection;
	}
	
	public void initTable() {
		initColumnsWidth();
		Vector<Defect> v = CurrentProject.getDefectList().getAllDefects();
        _defects = new Vector<Defect>();
        for (int i = 0; i < v.size(); i++) {
            Defect d = (Defect)v.get(i);
            _defects.add(d);
        }
	}
	
	void initColumnsWidth() {
		int columnCount = this.getModel().getColumnCount();
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(100);
            }
            else {
                column.setMinWidth(100);
                column.setPreferredWidth(100);
            }
        }
    }
	
	public void tableChanged() {
        initTable();
        ListSelectionModel lsm = this.getSelectionModel();
        TableModel model = this.getModel();
        int row = lsm.getMinSelectionIndex();
		int numColumns = model.getColumnCount() + 1;
		
		
        _sorter.tableChanged(null);
        initColumnsWidth();
        updateUI();
        
        for(int i = 0; i < numColumns; i++) {
			_currentSelection[i] = (String) model.getValueAt(row, i);
		}
    }
	
	public TableCellRenderer getCellRenderer(int row, int column) {
        return new DefaultTableCellRenderer() {

            public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column) {
                JLabel comp;

                comp = (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                return comp;
            }
        };
    }
	
	class DefectsTableModel extends AbstractTableModel {
		
		String[] columnNames = {
                Local.getString("ID"),
                Local.getString("Type"),
                Local.getString("Date"),
                Local.getString("Task"),
                Local.getString("Injected"),
                Local.getString("Removed"),
                Local.getString("Fix Time"),
                Local.getString("Fix Ref"),
                Local.getString("Description"),
                "Hash ID"};
		
		public DefectsTableModel() {
			super();
		}

		public int getColumnCount() {
			return columnNames.length-1;
		}
		
		public String getColumnName(int i) {
            return columnNames[i];
        }

		public int getRowCount() {
			return _defects.size();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Defect d = (Defect)_defects.get(rowIndex);
            if (columnIndex == 0){
            	return d.getID();
            } else if (columnIndex == 1) {
            	return d.getDefectType();
            } else if (columnIndex == 2) {
            	return d.getDate().toString();
            } else if (columnIndex == 3) {
            	return d.getTask();
            } else if (columnIndex == 4) {
            	return d.getInjectedPhase();
            } else if (columnIndex == 5) {
            	return d.getRemovedPhase();
            } else if (columnIndex == 6) {
            	return d.getFixedTime();
            } else if (columnIndex == 7) {
            	return d.getFixRef();
            } else if (columnIndex == 8) {
            	return d.getDesc();
            } else if (columnIndex == 9) {
            	return d.getHashId();
            }
            
            return null;
		}
	}
	
	class MouseTableListener implements MouseInputListener {
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				_hasSelection = true;
				JTable jt = (JTable) e.getSource();
				TableModel model = jt.getModel();
				int row = jt.getSelectedRow();
				int numColumns = model.getColumnCount() + 1;
				
				for(int i = 0; i < numColumns; i++) {
					_currentSelection[i] = (String) model.getValueAt(row, i);
				}
			}
		}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseDragged(MouseEvent e) {}

		public void mouseMoved(MouseEvent e) {}
	}
	
	class TableListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) { 
        	ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        	if (lsm.isSelectionEmpty()) {
        		_hasSelection = false;
        	} else {
        		_hasSelection = true;
        		_lastSelectedRow = lsm.getMinSelectionIndex();
        	}
        }
    }
}
