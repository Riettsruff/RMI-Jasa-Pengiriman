/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.server.helper.DB;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riett
 */
public class AuthDao {
  
  public static Pengguna login(String email, String password) {
    try {
      String[] fields = {"id_pengguna", "nama", "email", "id_peran", "terakhir_login"};
      String[] whereStatement = {"email=?", "password=?"};
      String[] whereStatementOperator = {"AND"};
      String[] ps = {email, password};
      ResultSet rs = DB.get("pengguna", fields, whereStatement, whereStatementOperator, ps);
      
      if(rs.next()) {
        Pengguna pengguna = new Pengguna();
        
        pengguna.setIdPengguna(rs.getInt("id_pengguna"));
        pengguna.setNama(rs.getString("nama"));
        pengguna.setEmail(rs.getString("email"));
        pengguna.setPeran(PeranDao.getByIdPeran(rs.getString("id_peran")));
        pengguna.setTerakhirLogin(rs.getTimestamp("terakhir_login"));
        
        return pengguna;
      }
    } catch (Exception e) { 
      Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return null;
  }
}
