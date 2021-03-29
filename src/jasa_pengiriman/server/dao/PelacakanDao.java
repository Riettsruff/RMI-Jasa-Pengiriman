/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Kota;
import jasa_pengiriman.model.Pelacakan;
import jasa_pengiriman.model.Pengiriman;
import jasa_pengiriman.model.Provinsi;
import jasa_pengiriman.model.StatusPelacakan;
import jasa_pengiriman.server.helper.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sebagai DAO untuk Pelacakan
 */
public class PelacakanDao {
  
  /**
   * Untuk generate seluruh Pelacakan
   * @return List
   */
  public static List<Pelacakan> getAll() {
    List<Pelacakan> pelacakanList = new ArrayList<Pelacakan>();
    
    try {
      String query = "SELECT a.*, b.id_pelacakan, b.no_resi, b.waktu_lapor, b.keterangan, c.id_cabang AS id_cabang_pelapor, c.nama_cabang AS nama_cabang_pelapor, c.alamat AS alamat_cabang_pelapor, c.no_hp AS no_hp_cabang_pelapor, d.id_kota AS id_kota_pelapor, d.nama_kota AS nama_kota_pelapor, e.id_provinsi AS id_provinsi_pelapor, e.nama_provinsi AS nama_provinsi_pelapor FROM status_pelacakan a INNER JOIN pelacakan b ON a.id_status_pelacakan = b.id_status_pelacakan LEFT JOIN cabang c ON b.id_cabang = c.id_cabang INNER JOIN kota d ON c.id_kota = d.id_kota INNER JOIN provinsi e ON d.id_provinsi = e.id_provinsi ORDER BY b.waktu_lapor DESC";
      ResultSet rs = DB.query(query);
      
      while(rs.next()) {
        StatusPelacakan statusPelacakan = new StatusPelacakan();
        Pelacakan pelacakan = new Pelacakan();
        Pengiriman pengiriman = new Pengiriman();
        Cabang cabangPelapor = new Cabang();
        Kota kotaPelapor = new Kota();
        Provinsi provinsiPelapor = new Provinsi();
        
        statusPelacakan.setIdStatusPelacakan(rs.getInt("id_status_pelacakan"));
        statusPelacakan.setNamaStatus(rs.getString("nama_status"));
        
        pengiriman.setNoResi(rs.getString("no_resi"));
        
        provinsiPelapor.setIdProvinsi(rs.getInt("id_provinsi_pelapor"));
        provinsiPelapor.setNamaProvinsi(rs.getString("nama_provinsi_pelapor"));
        
        kotaPelapor.setIdKota(rs.getInt("id_kota_pelapor"));
        kotaPelapor.setNamaKota(rs.getString("nama_kota_pelapor"));
        kotaPelapor.setProvinsi(provinsiPelapor);
        
        cabangPelapor.setIdCabang(rs.getInt("id_cabang_pelapor"));
        cabangPelapor.setNamaCabang(rs.getString("nama_cabang_pelapor"));
        cabangPelapor.setNoHp(rs.getString("no_hp_cabang_pelapor"));
        cabangPelapor.setAlamat(rs.getString("alamat_cabang_pelapor"));
        cabangPelapor.setKota(kotaPelapor);
        
        pelacakan.setIdPelacakan(rs.getInt("id_pelacakan"));
        pelacakan.setPengiriman(pengiriman);
        pelacakan.setWaktuLapor(rs.getTimestamp("waktu_lapor"));
        pelacakan.setKeterangan(rs.getString("keterangan"));
        pelacakan.setStatusPelacakan(statusPelacakan);
        pelacakan.setCabang(cabangPelapor);
        
        pelacakanList.add(pelacakan);
      }
    } catch (SQLException ex) {
      Logger.getLogger(PelacakanDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return pelacakanList;
  }
  
  /**
   * Untuk menangani proses insert Pelacakan
   * @param pelacakan
   * @return boolean
   */
  public static boolean insert(Pelacakan pelacakan) {
    try {
      String table = "pelacakan";
      String[] values = {
        String.valueOf(pelacakan.getIdPelacakan()),
        pelacakan.getPengiriman().getNoResi(),
        String.valueOf(pelacakan.getCabang().getIdCabang()),
        String.valueOf(pelacakan.getStatusPelacakan().getIdStatusPelacakan()),
        pelacakan.getWaktuLapor().toString(),
        pelacakan.getKeterangan()
      };
      
      return DB.insert(table, values);
    } catch (SQLException ex) {
      Logger.getLogger(PelacakanDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  /**
   * Untuk menangani proses update Pelacakan
   * @param pelacakan
   * @return boolean
   */
  public static boolean update(Pelacakan pelacakan) {
    try {
      String table = "pelacakan";
      String[] fields = {"no_resi", "id_status_pelacakan", "waktu_lapor", "keterangan"};
      String[] values = {
        pelacakan.getPengiriman().getNoResi(),
        String.valueOf(pelacakan.getStatusPelacakan().getIdStatusPelacakan()),
        pelacakan.getWaktuLapor().toString(),
        pelacakan.getKeterangan(),
        String.valueOf(pelacakan.getIdPelacakan())
      };
      String[] whereStatement = {"id_pelacakan = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(PelacakanDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  /**
   * Untuk menangani proses delete Pelcakan berdasarkan idPelacakan
   * @param idPelacakan
   * @return boolean
   */
  public static boolean deleteByIdPelacakan(int idPelacakan) {
    try {
      String table = "pelacakan";
      String[] whereStatement = {"id_pelacakan = ?"};
      String[] whereStatementSeparator = null;
      String[] values = { 
        String.valueOf(idPelacakan)
      };
      
      return DB.delete(table, whereStatement, whereStatementSeparator, values);
    } catch (SQLException ex) {
      Logger.getLogger(PelacakanDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
