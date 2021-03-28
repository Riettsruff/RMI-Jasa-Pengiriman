/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Cabang;
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
public class CabangDao {
  
  private static List<Cabang> get(String type, String[] values) {
    List<Cabang> cabangList = new ArrayList<Cabang>();
    
    try {
      String query = "SELECT a.id_cabang, a.nama_cabang, a.alamat, a.no_hp, b.id_kota, b.nama_kota, c.id_provinsi, c.nama_provinsi FROM cabang a INNER JOIN kota b ON a.id_kota = b.id_kota INNER JOIN provinsi c ON b.id_provinsi = c.id_provinsi";
      ResultSet rs = null;
      
      switch(type) {
        case "BY_ID_KOTA":
          query += " WHERE b.id_kota = ?";
          rs = DB.query(query, values);
        break;
        case "ALL":
        default:
          rs = DB.query(query);
      }
      
      while(rs.next()) {
        Cabang cabang = new Cabang();
        Kota kota = new Kota();
        Provinsi provinsi = new Provinsi();
        
        provinsi.setIdProvinsi(rs.getInt("id_provinsi"));
        provinsi.setNamaProvinsi(rs.getString("nama_provinsi"));
        
        kota.setIdKota(rs.getInt("id_kota"));
        kota.setNamaKota(rs.getString("nama_kota"));
        kota.setProvinsi(provinsi);
        
        cabang.setIdCabang(rs.getInt("id_cabang"));
        cabang.setKota(kota);
        cabang.setNamaCabang(rs.getString("nama_cabang"));
        cabang.setAlamat(rs.getString("alamat"));
        cabang.setNoHp(rs.getString("no_hp"));
        
        cabangList.add(cabang);
      }
    } catch (SQLException ex) {
      Logger.getLogger(CabangDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return cabangList;
  }
  
  public static List<Cabang> getAll() {
    return get("ALL", null);
  }
  
  public static List<Cabang> getByIdKota(int idKota) {
    String[] values = {Integer.toString(idKota)};
    return get("BY_ID_KOTA", values);
  }
  
  public static boolean insert(Cabang cabang) {
    try {
      String table = "cabang";
      String[] values = {
        String.valueOf(cabang.getIdCabang()),
        String.valueOf(cabang.getKota().getIdKota()),
        cabang.getNamaCabang(),
        cabang.getAlamat(),
        cabang.getNoHp()
      };
      
      return DB.insert(table, values);
    } catch (SQLException ex) {
      Logger.getLogger(CabangDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean update(Cabang cabang) {
    try {
      String table = "cabang";
      String[] fields = {"id_kota", "nama_cabang", "alamat", "no_hp"};
      String[] values = {
        String.valueOf(cabang.getKota().getIdKota()),
        cabang.getNamaCabang(),
        cabang.getAlamat(),
        cabang.getNoHp(),
        String.valueOf(cabang.getIdCabang())
      };
      String[] whereStatement = {"id_cabang = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(CabangDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean deleteByIdCabang(int idCabang) {
    try {
      String table = "cabang";
      String[] whereStatement = {"id_cabang = ?"};
      String[] whereStatementSeparator = null;
      String[] values = {
        String.valueOf(idCabang)
      };
      
      return DB.delete(table, whereStatement, whereStatementSeparator, values);
    } catch (SQLException ex) {
      Logger.getLogger(CabangDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
