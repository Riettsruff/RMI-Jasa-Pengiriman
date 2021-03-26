/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.service;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Riett
 */
public class Table {
  
  public static void setData(JTable table, Object[][] rowsData, String[] fieldsData) {
    table.setModel(new DefaultTableModel(rowsData, fieldsData));
  }
  
  public static void setColumnWidths(JTable table, int... widths) {
    TableColumnModel columnModel = table.getColumnModel();
    
    for(int i = 0; i < widths.length; ++i) {
      if(i < columnModel.getColumnCount()) {
        columnModel.getColumn(i).setMaxWidth(widths[i]);
      } else break;
    }
  }
}
