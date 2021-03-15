/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Akses;
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
public class AksesDao {
  
  public static List<Akses> getAll() {
    List<Akses> aksesList = new ArrayList<Akses>();
    
    try {
      ResultSet rs = DB.get("akses");
      
      while(rs.next()) {
        Akses akses = new Akses();
        
        akses.setIdAkses(rs.getInt("id_akses"));
        akses.setNamaAkses(rs.getString("nama_akses"));
        akses.setOperasi(rs.getString("operasi"));
        
        aksesList.add(akses);
      }
    } catch (Exception e) { 
      Logger.getLogger(AksesDao.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return aksesList;
  }
}
