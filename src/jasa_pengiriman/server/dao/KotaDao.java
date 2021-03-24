/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Kota;
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
public class KotaDao {
  private static List<Kota> get(String type, String[] values) {
    List<Kota> kotaList = new ArrayList<Kota>();
    
    try {
      String query = "SELECT a.id_kota, a.nama_kota, b.* FROM kota a INNER JOIN provinsi b ON a.id_provinsi = b.id_provinsi";
      ResultSet rs = null;
      
      switch(type) {
        case "BY_ID_PROVINSI":
          query += " WHERE b.id_provinsi = ?";
          rs = DB.query(query, values);
        break;
        case "ALL":
        default:
          rs = DB.query(query);
      }
      
      while(rs.next()) {
        Kota kota = new Kota();
        Provinsi provinsi = new Provinsi();
        
        provinsi.setIdProvinsi(rs.getInt("id_provinsi"));
        provinsi.setNamaProvinsi(rs.getString("nama_provinsi"));
        kota.setIdKota(rs.getInt("id_kota"));
        kota.setProvinsi(provinsi);
        kota.setNamaKota(rs.getString("nama_kota"));
        
        kotaList.add(kota);
      }
    } catch (SQLException ex) {
      Logger.getLogger(KotaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return kotaList;
  }
  
  public static List<Kota> getAll() {
    return get("ALL", null);
  }
  
  public static List<Kota> getByIdProvinsi(int idProvinsi) {
    String[] values = {Integer.toString(idProvinsi)};
    return get("BY_ID_PROVINSI", values);
  }
  
  public static boolean insert(Kota kota) {
    try {
      String table = "kota";
      String[] values = {
        String.valueOf(kota.getIdKota()),
        String.valueOf(kota.getProvinsi().getIdProvinsi()),
        kota.getNamaKota()
      };
      
      return DB.insert(table, values);
    } catch (SQLException ex) {
      Logger.getLogger(KotaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean update(Kota kota) {
    try {
      String table = "kota";
      String[] fields = {"id_provinsi", "nama_kota"};
      String[] values = {
        String.valueOf(kota.getProvinsi().getIdProvinsi()),
        kota.getNamaKota(),
        String.valueOf(kota.getIdKota())
      };
      String[] whereStatement = {"id_kota = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(KotaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean deleteByIdKota(int idKota) {
    try {
      String table = "kota";
      String[] whereStatement = {"id_kota"};
      String[] whereStatementSeparator = null;
      String[] values = {
        String.valueOf(idKota)
      };
       
      return DB.delete(table, whereStatement, whereStatementSeparator, values);
    } catch (SQLException ex) {
      Logger.getLogger(KotaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
