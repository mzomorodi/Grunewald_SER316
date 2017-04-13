package net.sf.memoranda.ui;

import java.awt.Component;
import java.awt.Font;
import java.io.File;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.sf.memoranda.CurrentProject;
import net.sf.memoranda.Defect;
import net.sf.memoranda.DefectList;
import net.sf.memoranda.NoteList;
import net.sf.memoranda.Project;
import net.sf.memoranda.ProjectListener;
import net.sf.memoranda.Resource;
import net.sf.memoranda.ResourcesList;
import net.sf.memoranda.TaskList;
import net.sf.memoranda.ui.ResourcesTable.ResourcesTableModel;
import net.sf.memoranda.ui.table.TableSorter;
import net.sf.memoranda.util.Local;
import net.sf.memoranda.util.MimeTypesList;

/**
 * @author Matthew Seiler
 * 
 * Description: A class for presenting a list of defects in a table.
 *
 */
public class DefectsTable extends JTable {
	
	private Vector<Defect> _defects = null;
	private TableSorter _sorter = null;
	
	/**
	 * Constructor for DefectFormTable.
	 */
	public DefectsTable() {
		super();
		
		/* table properties */
		setCellSelectionEnabled(true);
		setRowHeight(30);
		
		initTable();
		_sorter = new TableSorter(new FormTableModel());
        _sorter.addMouseListenerToHeaderInTable(this);
        setModel(_sorter);
        setFont(new Font("Dialog",0,11));
        
        CurrentProject.addProjectListener(new ProjectListener() {
			@Override
			public void projectChange(Project prj, NoteList nl, TaskList tl, ResourcesList rl, DefectList dl) {
				
			}

			@Override
			public void projectWasChanged() {
				tableChanged();
			}
        });
	}
	
	private void initTable() {
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
                column.setPreferredWidth(32767);
            }
            else {
                column.setMinWidth(100);
                column.setPreferredWidth(100);
            }
        }
    }
	
	public void tableChanged() {
        initTable();
        _sorter.tableChanged(null);
        updateUI();
    }
	
	public TableCellRenderer getCellRenderer(int row, int column) {
        return new javax.swing.table.DefaultTableCellRenderer() {

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
	
	class FormTableModel extends AbstractTableModel {
		
		String[] columnNames = {
                Local.getString("ID"),
                Local.getString("Type"),
                Local.getString("Date"),
                Local.getString("Task"),
                Local.getString("Injected"),
                Local.getString("Removed"),
                Local.getString("Fix Time"),
                Local.getString("Ref"),
                Local.getString("Description")};
		
		public FormTableModel() {
			super();
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}
		
		public String getColumnName(int i) {
            return columnNames[i];
        }

		@Override
		public int getRowCount() {
			//return _defects.size();
			return 0;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return null;
		}
	}
}
