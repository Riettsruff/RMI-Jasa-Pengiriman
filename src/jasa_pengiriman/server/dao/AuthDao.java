/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.model.Peran;
import jasa_pengiriman.server.helper.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthDao {
  
  public static Pengguna login(String email, String password) {
    try {
      String[] fields = null;
      String[] whereStatement = {"email = ?", "password = ?"};
      String[] whereStatementSeparator = {"AND"};
      String[] values = {email, password};
      ResultSet rs = DB.get("pengguna", fields, whereStatement, whereStatementSeparator, values);
      
      if(rs.next()) {
        Pengguna pengguna = new Pengguna();
        Cabang cabang = new Cabang();
        Peran peran = new Peran();
        
        cabang.setIdCabang(rs.getInt("id_cabang"));
        peran.setIdPeran(rs.getInt("id_peran")); 
        pengguna.setCabang(cabang);
        pengguna.setPeran(peran);
        pengguna.setIdPengguna(rs.getInt("id_pengguna"));
        pengguna.setNama(rs.getString("nama"));
        pengguna.setEmail(rs.getString("email"));
        pengguna.setTerakhirLogin(rs.getTimestamp("terakhir_login"));
        
        return pengguna;
      }
    } catch (SQLException ex) {
      Logger.getLogger(AuthDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return null;
  }
}
