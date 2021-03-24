/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Provinsi;
import jasa_pengiriman.server.helper.DB;
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
    } catch (SQLException ex) {
      Logger.getLogger(ProvinsiDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return provinsiList;
  }
  
  public static boolean insert(Provinsi provinsi) {
    try {
      String table = "provinsi";
      String[] values = {
        String.valueOf(provinsi.getIdProvinsi()),
        provinsi.getNamaProvinsi()
      };
      
      return DB.insert(table, values);
    } catch (SQLException ex) {
      Logger.getLogger(ProvinsiDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean update(Provinsi provinsi) {
    try {
      String table = "provinsi";
      String[] fields = {"nama_provinsi"};
      String[] values = {
        provinsi.getNamaProvinsi(),
        String.valueOf(provinsi.getIdProvinsi())
      };
      String[] whereStatement = {"id_provinsi = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(ProvinsiDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean deleteByIdProvinsi(int idProvinsi) {
    try {
      String table = "provinsi";
      String[] whereStatement = {"id_provinsi = ?"};
      String[] whereStatementSeparator = null;
      String[] values = {
        String.valueOf(idProvinsi)
      };
      
      return DB.delete(table, whereStatement, whereStatementSeparator, values);
    } catch (SQLException ex) {
      Logger.getLogger(ProvinsiDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
