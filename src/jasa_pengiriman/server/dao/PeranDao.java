/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Peran;
import jasa_pengiriman.server.helper.DB;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riett
 */
public class PeranDao {
  public static Peran getByIdPeran(String idPeran) {
    try {
      String[] fields = null;
      String[] whereStatement = {"id_peran=?"};
      String[] whereStatementSeparator = null;
      String[] ps = {idPeran};
      ResultSet rs = DB.get("peran", fields, whereStatement, whereStatementSeparator, ps);
      
      if(rs.next()) {
        Peran peran = new Peran();
        peran.setIdPeran(rs.getInt("id_peran"));
        peran.setNamaPeran(rs.getString("nama_peran"));
        
        return peran;
      }
    } catch (Exception e) { 
      Logger.getLogger(PeranDao.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return null;
  }
}
