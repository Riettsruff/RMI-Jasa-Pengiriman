/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.service;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Riett
 */
public class Table {
  
  public static void setData(JTable table, Object[][] rowsData, String[] fieldsData, boolean isCellEditable) {
    table.setModel(new DefaultTableModel(rowsData, fieldsData) {
      @Override
      public boolean isCellEditable(int row, int col) {
        return isCellEditable;
      }
    });
  }
  
  public static void setColumnWidths(JTable table, int... widths) {
    TableColumnModel columnModel = table.getColumnModel();
    
    for(int i = 0; i < widths.length; ++i) {
      if(i < columnModel.getColumnCount()) {
        columnModel.getColumn(i).setMaxWidth(widths[i]);
      } else break;
    }
  }
  
  public static void removeColumns(JTable table, int... columns) {
    TableColumnModel columnModel = table.getColumnModel();
    
    for(int column : columns) {
      table.removeColumn(columnModel.getColumn(column));
    }
  }
  
  public static Object getValue(JTable table, int row, int column) {
    TableModel tableModel = table.getModel();
    
    return tableModel.getValueAt(row, column);
  }
}
