/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.StatusPelacakan;
import jasa_pengiriman.server.helper.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StatusPelacakanDao {
  
  public static List<StatusPelacakan> getAll() {
    List<StatusPelacakan> statusPelacakanList = new ArrayList<StatusPelacakan>();
    
    try {
      ResultSet rs = DB.get("status_pelacakan");
      
      while(rs.next()) {
        StatusPelacakan statusPelacakan = new StatusPelacakan();
        
        statusPelacakan.setIdStatusPelacakan(rs.getInt("id_status_pelacakan"));
        statusPelacakan.setNamaStatus(rs.getString("nama_status"));
        
        statusPelacakanList.add(statusPelacakan);
      }
    } catch (SQLException ex) {
      Logger.getLogger(StatusPelacakanDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return statusPelacakanList;
  }
}
