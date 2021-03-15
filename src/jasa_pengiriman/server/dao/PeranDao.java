/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Peran;
import jasa_pengiriman.server.helper.DB;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riett
 */
public class PeranDao {
  
  public static List<Peran> getAll() {
    List<Peran> peranList = new ArrayList<Peran>();
    
    try {
      ResultSet rs = DB.get("peran");
      
      while(rs.next()) {
        Peran peran = new Peran();
        
        peran.setIdPeran(rs.getInt("id_peran"));
        peran.setNamaPeran(rs.getString("nama_peran"));
        
        peranList.add(peran);
      }
    } catch (Exception e) { 
      Logger.getLogger(PeranDao.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return peranList;
  }
}
