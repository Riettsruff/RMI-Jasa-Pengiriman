/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao.helper;

import static jasa_pengiriman.server.service.Conn.Conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riett
 */
public class DB {
  
  public static ResultSet getAll(String table) {
    ResultSet rs = null;
    
    try {
      String query = "SELECT * FROM " + table;

      PreparedStatement ps = Conn().prepareStatement(query);
      rs = ps.executeQuery();
      
    } catch (SQLException se) {
      Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, se);
    }

    return rs;
  }
}
