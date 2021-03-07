/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.server.helper.DB;
import static jasa_pengiriman.server.service.Conn.Conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riett
 */
public class PenggunaDao {
  private static final String TABLE_NAME = "pengguna";
  
  public static List<Pengguna> getAll() {
    List<Pengguna> listPengguna = new ArrayList<Pengguna>();
    
    try {
      ResultSet rs = DB.getAll(TABLE_NAME);
      
      while(rs.next()) {
        Pengguna pengguna = new Pengguna();
        
        pengguna.setIdPengguna(rs.getInt("id_pengguna"));
//        pengguna.setCabang(null);
//        pengguna.setPeran(null);
        pengguna.setEmail(rs.getString("email"));
        pengguna.setPassword(rs.getString("password"));
        pengguna.setTerakhirLogin(rs.getTimestamp("terakhir_login"));
        
        listPengguna.add(pengguna);
      }
    } catch (Exception e) { 
      Logger.getLogger(PenggunaDao.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return listPengguna;
  }
}
