/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Provinsi;
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
public class ProvinsiDao {
  public static List<Provinsi> getAll() {
    List<Provinsi> provinsiList = new ArrayList<Provinsi>();
    
    try {
      ResultSet rs = DB.get("provinsi");
      
      while(rs.next()) {
        Provinsi provinsi = new Provinsi();
        
        provinsi.setIdProvinsi(rs.getInt("id_provinsi"));
        provinsi.setNamaProvinsi(rs.getString("nama_provinsi"));
        
        provinsiList.add(provinsi);
      }
    } catch (Exception e) { 
      Logger.getLogger(ProvinsiDao.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return provinsiList;
  }
}
