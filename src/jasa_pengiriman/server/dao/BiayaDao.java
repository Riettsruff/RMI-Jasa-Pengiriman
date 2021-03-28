/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Biaya;
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
public class BiayaDao {
  public List<Biaya> getAll() {
    List<Biaya> biayaList = new ArrayList<Biaya>();
    
    try {
      String query = "SELECT a.id_provinsi AS id_provinsi_asal, a.nama_provinsi AS nama_provinsi_asal, b.id_kota AS id_kota_asal, b.nama_kota AS nama_kota_asal, c.harga, d.id_kota AS id_kota_tujuan, d.nama_kota AS nama_kota_tujuan, e.id_provinsi AS id_provinsi_tujuan, e.nama_provinsi AS nama_provinsi_tujuan FROM provinsi a INNER JOIN kota b ON a.id_provinsi = b.id_provinsi INNER JOIN biaya c ON b.id_kota = c.id_kota_asal INNER JOIN kota d ON c.id_kota_tujuan = d.id_kota INNER JOIN provinsi e ON d.id_provinsi = e.id_provinsi";
      ResultSet rs = DB.query(query);
      
      while(rs.next()) {
        Kota kotaAsal = new Kota();
        Provinsi provinsiAsal = new Provinsi();
        Kota kotaTujuan = new Kota();
        Provinsi provinsiTujuan = new Provinsi();
        Biaya biaya = new Biaya();
        
        provinsiAsal.setIdProvinsi(rs.getInt("id_provinsi_asal"));
        provinsiAsal.setNamaProvinsi(rs.getString("nama_provinsi_asal"));
        kotaAsal.setIdKota(rs.getInt("id_kota_asal"));
        kotaAsal.setNamaKota(rs.getString("nama_kota_asal"));
        provinsiTujuan.setIdProvinsi(rs.getInt("id_provinsi_tujuan"));
        provinsiTujuan.setNamaProvinsi(rs.getString("nama_provinsi_tujuan"));
        kotaTujuan.setIdKota(rs.getInt("id_kota_tujuan"));
        kotaTujuan.setNamaKota(rs.getString("nama_kota_tujuan"));
        biaya.setHarga(rs.getLong("harga"));
        biaya.setKotaAsal(kotaAsal);
        biaya.setKotaTujuan(kotaTujuan);
        
        biayaList.add(biaya);
      }
    } catch (SQLException ex) {
      Logger.getLogger(BiayaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return biayaList;
  }
  
  public static boolean insert(Biaya biaya) {
    try {
      String table = "biaya";
      String[] values = {
        String.valueOf(biaya.getIdBiaya()),
        String.valueOf(biaya.getKotaAsal().getIdKota()),
        String.valueOf(biaya.getKotaTujuan().getIdKota()),
        String.valueOf(biaya.getHarga())
      };
      
      return DB.insert(table, values);
    } catch (SQLException ex) {
      Logger.getLogger(BiayaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean update(Biaya biaya) {
    try {
      String table = "biaya";
      String[] fields = {"id_kota_asal", "id_kota_tujuan", "harga"};
      String[] values = {
        String.valueOf(biaya.getKotaAsal().getIdKota()),
        String.valueOf(biaya.getKotaTujuan().getIdKota()),
        String.valueOf(biaya.getHarga()),
        String.valueOf(biaya.getIdBiaya())
      };
      String[] whereStatement = {"id_biaya = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(BiayaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean deleteByIdBiaya(int idBiaya) {
    try {
      String table = "biaya";
      String[] whereStatement = {"id_biaya = ?"};
      String[] whereStatementSeparator = null;
      String[] values = {
        String.valueOf(idBiaya)
      };
      
      return DB.delete(table, whereStatement, whereStatementSeparator, values);
    } catch (SQLException ex) {
      Logger.getLogger(BiayaDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
