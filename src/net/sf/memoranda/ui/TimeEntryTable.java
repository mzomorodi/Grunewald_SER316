package net.sf.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.TimeEntry;
import net.sf.memoranda.TimeEntryList;
import net.sf.memoranda.ui.table.TableSorter;
import net.sf.memoranda.util.Local;

/**
 * @author Matthew Seiler
 * 
 * Description: A class for presenting a list of time entries in a table.
 *
 */
public class TimeEntryTable extends JTable {
	
	static int ID_COL = 9;
	private Vector<TimeEntry> _times = null;
	private TableSorter _sorter = null;
	private ListSelectionModel _listSelectionModel = null;
	private boolean _hasSelection;
	private int _lastSelectedRow;
	private String[] _currentSelection = {"","","","","","","","","",""};
	
	/**
	 * Constructor for TimeEntryTable.
	 */
	public TimeEntryTable() {
		super();
		
		/* table properties */
		setCellSelectionEnabled(true);
		setRowHeight(30);
		setAutoCreateRowSorter(true);
		
		initTable();
        setModel(new TimeEntryTableModel());
      
        setFont(new Font("Dialog",0,11));
        
        CurrentProject.addProjectListener(new ProjectListener() {
			public void projectChange(Project prj, NoteList nl, TaskList tl, ResourcesList rl, DefectList dl, TimeEntryList tel) {
				
			}

			public void projectWasChanged() {
				tableChanged();
			}
        });
	}
	
	/**
	 * hasSelection returns whether the able has a selection or not
	 * 
	 * @return the table's selection state
	 */
	public boolean hasSelection() {
		return _hasSelection;
	}
	
	/**
	 * getLastSelectedRow returns whether the last row selected in table
	 * 
	 * @return the table's last selected row
	 */
	public int getLastSelectedRow() {
		return _lastSelectedRow;
	}
	
	/**
	 * getCurrentSelection returns the row of data selected in the table
	 * 
	 * @return array of strings, each string contains one cell from the selected table row
	 */
	public String[] getCurrentSelection() {
		return _currentSelection;
	}
	
	/**
	 * initTable initializes the table be setting the column widths and 
	 * populating the data object with defects from storage
	 */
	public void initTable() {
		initColumnsWidth();
		Vector<TimeEntry> v = CurrentProject.getTimeEntryList().getAllTimeEntries();
        _times = new Vector<TimeEntry>();
        for (int i = 0; i < v.size(); i++) {
            TimeEntry t = (TimeEntry)v.get(i);
            _times.add(t);
        }
	}
	
	/**
	 * initColumnsWidth sets the initial column widths of the table
	 */
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
	
	/**
	 * tableChanged is called for updating table UI after table data has been modified
	 */
	public void tableChanged() {
        initTable();
        ListSelectionModel lsm = this.getSelectionModel();
        TableModel model = this.getModel();
        int row = lsm.getMinSelectionIndex();
        if (row < 0) {
        	row = 0;
        }
		int numColumns = model.getColumnCount() + 1;
		
        _sorter.tableChanged(null);
        initColumnsWidth();
        updateUI();
        
        for(int i = 0; i < numColumns; i++) {
			_currentSelection[i] = (String) model.getValueAt(row, i);
		}
    }
	
	/**
	 * getCellRenderer returns the cell renderer for the table
	 */
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
	
	/**
	 * TimeEntryTableModel class represents the table's model
	 */
	class TimeEntryTableModel extends AbstractTableModel {
		
		String[] columnNames = {
                Local.getString("Date"),
                Local.getString("Phase"),
                Local.getString("LOC Start"),
                Local.getString("LOC End"),
                Local.getString("Start Time"),
                Local.getString("Stop Time"),
                Local.getString("Interrupt Time"),
                Local.getString("Comments"),
                "Hash ID"};
		
		public TimeEntryTableModel() {
			super();
		}

		public int getColumnCount() {
			return columnNames.length;
		}
		
		public String getColumnName(int i) {
            return columnNames[i];
        }

		public int getRowCount() {
			return _times.size();
		}

		/** getValueAt returns the value of the cell in the given row and column indexes
		 * @return the selected cell at the given row and column indexes
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			TimeEntry t = (TimeEntry)_times.get(rowIndex);
            if (columnIndex == 0){
            	return t.getDate();
            } else if (columnIndex == 1) {
            	return t.getTimeEntryPhase();
            } else if (columnIndex == 2) {
            	return t.getTimeEntryLocStart();
            } else if (columnIndex == 3) {
            	return t.getTimeEntryLocEnd();
            } else if (columnIndex == 4) {
            	return t.getTimeEntryStartTime();
            } else if (columnIndex == 5) {
            	return t.getTimeEntryStopTime();
            } else if (columnIndex == 6) {
            	return t.getTimeEntryInterruptTime();
            } else if (columnIndex == 7) {
            	return t.getTimeEntryComments();
            } else if (columnIndex == 8) {
            	return t.getHashID();
            }
            
            return null;
		}
	}
}
