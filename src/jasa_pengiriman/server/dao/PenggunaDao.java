/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Kota;
import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.model.Peran;
import jasa_pengiriman.model.Provinsi;
import jasa_pengiriman.server.helper.DB;
import static jasa_pengiriman.server.service.Conn.Conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sebagai DAO untuk Pengguna
 */
public class PenggunaDao {
  
  /**
   * Untuk generate Pengguna berdasarkan id_cabang, id_peran maupun seluruhnya
   * @param type
   * @param values
   * @return List
   */
  private static List<Pengguna> get(String type, String[] values) {
    List<Pengguna> penggunaList = new ArrayList<Pengguna>();
    
    try {
      String query = "SELECT a.id_pengguna, a.nama, a.email, a.password, a.terakhir_login, b.id_cabang, b.nama_cabang, c.* FROM pengguna a LEFT JOIN cabang b ON a.id_cabang = b.id_cabang LEFT JOIN peran c ON c.id_peran = a.id_peran";
      ResultSet rs = null;
      
      switch(type) {
        case "BY_ID_CABANG":
          query += " WHERE b.id_cabang = ?";
          rs = DB.query(query, values);
        break;
        case "BY_ID_PERAN":
          query += " WHERE c.id_peran = ?";
          rs = DB.query(query, values);
        break;
        case "ALL":
        default:
          rs = DB.query(query);
      }
      
      while(rs.next()) {
        Pengguna pengguna = new Pengguna();
        Cabang cabang = new Cabang();
        Peran peran = new Peran();
        
        cabang.setIdCabang(rs.getInt("id_cabang"));
        cabang.setNamaCabang(rs.getString("nama_cabang"));
        peran.setIdPeran(rs.getInt("id_peran"));
        peran.setNamaPeran(rs.getString("nama_peran"));
        pengguna.setIdPengguna(rs.getInt("id_pengguna"));
        pengguna.setCabang(cabang);
        pengguna.setPeran(peran);
        pengguna.setNama(rs.getString("nama"));
        pengguna.setEmail(rs.getString("email"));
        pengguna.setPassword(rs.getString("password"));
        pengguna.setTerakhirLogin(rs.getTimestamp("terakhir_login"));
        
        penggunaList.add(pengguna);
      }
    } catch (SQLException ex) {
      Logger.getLogger(PenggunaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return penggunaList;
  } 
  
  /**
   * Untuk generate Pengguna berdasarkan idPengguna
   * @param idPengguna
   * @return Pengguna 
   */
  public static Pengguna getByIdPengguna(int idPengguna) {
    try {
      String query = "SELECT a.*, b.id_pengguna, b.nama, b.email, b.password, b.terakhir_login, c.id_cabang, c.nama_cabang, c.alamat AS alamat_cabang, c.no_hp AS no_hp_cabang, d.id_kota, d.nama_kota, e.id_provinsi, e.nama_provinsi FROM peran a INNER JOIN pengguna b ON a.id_peran = b.id_peran LEFT JOIN cabang c ON b.id_cabang = c.id_cabang INNER JOIN kota d ON c.id_kota = d.id_kota INNER JOIN provinsi e ON d.id_provinsi = e.id_provinsi WHERE b.id_pengguna = ?";
      String[] values = {
        String.valueOf(idPengguna)
      };
      
      ResultSet rs = DB.query(query, values);
      
      if(rs.next()) {
        Pengguna pengguna = new Pengguna();
        Cabang cabang = new Cabang();
        Peran peran = new Peran();
        Kota kota = new Kota();
        Provinsi provinsi = new Provinsi();
        
        provinsi.setIdProvinsi(rs.getInt("id_provinsi"));
        provinsi.setNamaProvinsi(rs.getString("nama_provinsi"));
        
        kota.setIdKota(rs.getInt("id_kota"));
        kota.setNamaKota(rs.getString("nama_kota"));
        kota.setProvinsi(provinsi);
        
        cabang.setIdCabang(rs.getInt("id_cabang"));
        cabang.setNamaCabang(rs.getString("nama_cabang"));
        cabang.setKota(kota);
        
        peran.setIdPeran(rs.getInt("id_peran"));
        peran.setNamaPeran(rs.getString("nama_peran"));
        
        pengguna.setIdPengguna(rs.getInt("id_pengguna"));
        pengguna.setCabang(cabang);
        pengguna.setPeran(peran);
        pengguna.setNama(rs.getString("nama"));
        pengguna.setEmail(rs.getString("email"));
        pengguna.setPassword(rs.getString("password"));
        pengguna.setTerakhirLogin(rs.getTimestamp("terakhir_login"));
        
        return pengguna;
      }
    } catch (SQLException ex) {
      Logger.getLogger(PenggunaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return null;
  }
  
  /**
   * Untuk generate seluruh Pengguna
   * @return List
   */
  public static List<Pengguna> getAll() {
    return PenggunaDao.get("ALL", null);
  }
  
  /**
   * Untuk generate seluruh Pengguna berdasarkan idCabang
   * @param idCabang
   * @return List
   */
  public static List<Pengguna> getByIdCabang(int idCabang) {
    String[] values = {Integer.toString(idCabang)};
    return PenggunaDao.get("BY_ID_CABANG", values);
  }
  
  /**
   * Untuk generate seluruh Pengguna berdasarkan idPeran
   * @param idPeran
   * @return List
   */
  public static List<Pengguna> getByIdPeran(int idPeran) {
    String[] values = {Integer.toString(idPeran)};
    return PenggunaDao.get("BY_ID_PERAN", values);
  }
  
  /**
   * Untuk menangani proses insert Pengguna
   * @param pengguna
   * @return boolean
   */
  public static boolean insert(Pengguna pengguna) {
    try {
      String table = "pengguna";
      String[] fields = {"id_cabang", "id_peran", "nama", "email"};
      String[] values = {
        String.valueOf(pengguna.getCabang().getIdCabang()),
        String.valueOf(pengguna.getPeran().getIdPeran()),
        pengguna.getNama(),
        pengguna.getEmail(),
      };
      
      return DB.insert(table, fields, values);
    } catch (SQLException ex) {
      Logger.getLogger(PenggunaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  /**
   * Untuk menangani proses update Pengguna
   * @param pengguna
   * @return boolean
   */
  public static boolean update(Pengguna pengguna) {
    try {
      String table = "pengguna";
      String[] fields = {"id_cabang", "id_peran", "nama", "email", "password"};
      String[] values = {
        String.valueOf(pengguna.getCabang().getIdCabang()),
        String.valueOf(pengguna.getPeran().getIdPeran()),
        pengguna.getNama(),
        pengguna.getEmail(),
        pengguna.getPassword(),
        String.valueOf(pengguna.getIdPengguna())
      };
      String[] whereStatement = {"id_pengguna = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(PenggunaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  /**
   * Untuk menangani proses delete Pengguna berdasarkan idPengguna
   * @param idPengguna
   * @return boolean
   */
  public static boolean deleteByIdPengguna(int idPengguna) {
    try {
      String table = "pengguna";
      String[] whereStatement = {"id_pengguna = ?"};
      String[] whereStatementSeparator = null;
      String[] values = {
        String.valueOf(idPengguna)
      };
      
      return DB.delete(table, whereStatement, whereStatementSeparator, values);
    } catch (SQLException ex) {
      Logger.getLogger(PenggunaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  /**
   * Untuk menangani proses update field terakhir_login berdasarkan idPengguna
   * @param terakhirLogin
   * @param idPengguna
   * @return boolean
   */
  public static boolean updateTerakhirLoginByIdPengguna(Timestamp terakhirLogin, int idPengguna) {
    try {
      String table = "pengguna";
      String[] fields = {"terakhir_login"};
      String[] values = {
        terakhirLogin.toString(),
        String.valueOf(idPengguna)
      };
      String[] whereStatement = {"id_pengguna = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(PenggunaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
