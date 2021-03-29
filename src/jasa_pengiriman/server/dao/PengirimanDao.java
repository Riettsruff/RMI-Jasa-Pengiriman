/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Kota;
import jasa_pengiriman.model.Pengiriman;
import jasa_pengiriman.model.Provinsi;
import jasa_pengiriman.server.helper.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sebagai DAO untuk Pengiriman
 */
public class PengirimanDao {
  
  /**
   * Untuk generate seluruh Pengiriman
   * @return List
   */
  public static List<Pengiriman> getAll() {
    List<Pengiriman> pengirimanList = new ArrayList<Pengiriman>();
    
    try {
      String query = "SELECT x.id_provinsi AS id_provinsi_pengirim, x.nama_provinsi AS nama_provinsi_pengirim, a.id_kota AS id_kota_pengirim, a.nama_kota AS nama_kota_pengirim, b.id_cabang AS id_cabang_pengirim, b.nama_cabang AS nama_cabang_pengirim, c.no_resi, c.isi_barang, c.berat AS berat_barang, c.waktu_kirim, c.nama_penerima, c.alamat_penerima, c.no_hp_penerima, c.biaya, d.id_kota AS id_kota_penerima, d.nama_kota AS nama_kota_penerima, y.id_provinsi AS id_provinsi_penerima, y.nama_provinsi AS nama_provinsi_penerima FROM provinsi x INNER JOIN kota a ON x.id_provinsi = a.id_provinsi INNER JOIN cabang b ON a.id_kota = b.id_kota RIGHT JOIN pengiriman c ON b.id_cabang = c.id_cabang_pengirim LEFT JOIN kota d ON c.id_kota_penerima = d.id_kota INNER JOIN provinsi y ON d.id_provinsi = y.id_provinsi ORDER BY c.waktu_kirim DESC";
      ResultSet rs = DB.query(query);
      
      while(rs.next()) {
        Provinsi provinsiPengirim = new Provinsi();
        Kota kotaPengirim = new Kota();
        Cabang cabangPengirim = new Cabang();
        Pengiriman pengiriman = new Pengiriman();
        Kota kotaPenerima = new Kota();
        Provinsi provinsiPenerima = new Provinsi();
        
        provinsiPengirim.setIdProvinsi(rs.getInt("id_provinsi_pengirim"));
        provinsiPengirim.setNamaProvinsi(rs.getString("nama_provinsi_pengirim"));
        
        kotaPengirim.setIdKota(rs.getInt("id_kota_pengirim"));
        kotaPengirim.setNamaKota(rs.getString("nama_kota_pengirim"));
        kotaPengirim.setProvinsi(provinsiPengirim);
        
        cabangPengirim.setIdCabang(rs.getInt("id_cabang_pengirim"));
        cabangPengirim.setNamaCabang(rs.getString("nama_cabang_pengirim"));
        cabangPengirim.setKota(kotaPengirim);
        
        provinsiPenerima.setIdProvinsi(rs.getInt("id_provinsi_penerima"));
        provinsiPenerima.setNamaProvinsi(rs.getString("nama_provinsi_penerima"));
        
        kotaPenerima.setIdKota(rs.getInt("id_kota_penerima"));
        kotaPenerima.setNamaKota(rs.getString("nama_kota_penerima"));
        kotaPenerima.setProvinsi(provinsiPenerima);
        
        pengiriman.setNoResi(rs.getString("no_resi"));
        pengiriman.setIsiBarang(rs.getString("isi_barang"));
        pengiriman.setBeratBarang(rs.getDouble("berat_barang"));
        pengiriman.setWaktuKirim(rs.getTimestamp("waktu_kirim"));
        pengiriman.setBiaya(rs.getLong("biaya"));
        pengiriman.setNamaPenerima(rs.getString("nama_penerima"));
        pengiriman.setAlamatPenerima(rs.getString("alamat_penerima"));
        pengiriman.setCabangPengirim(cabangPengirim);
        pengiriman.setKotaPenerima(kotaPenerima);
        pengiriman.setNoHpPenerima(rs.getString("no_hp_penerima"));
        
        pengirimanList.add(pengiriman);
      }
    } catch (SQLException ex) {
      Logger.getLogger(PengirimanDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return pengirimanList;
  }
  
  /**
   * Untuk menangani proses insert Pengiriman
   * @param pengiriman
   * @return boolean
   */
  public static boolean insert(Pengiriman pengiriman) {
    try {
      String table = "pengiriman";
      String[] values = {
        pengiriman.getNoResi(),
        String.valueOf(pengiriman.getCabangPengirim().getIdCabang()),
        String.valueOf(pengiriman.getKotaPenerima().getIdKota()),
        pengiriman.getIsiBarang(),
        String.valueOf(pengiriman.getBeratBarang()),
        pengiriman.getWaktuKirim().toString(),
        pengiriman.getNamaPenerima(),
        pengiriman.getAlamatPenerima(),
        pengiriman.getNoHpPenerima(),
        String.valueOf(pengiriman.getBiaya())
      };
      
      return DB.insert(table, values);
    } catch (SQLException ex) {
      Logger.getLogger(PengirimanDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  /**
   * Untuk menangani proses update Pengiriman
   * @param pengiriman
   * @return boolean
   */
  public static boolean update(Pengiriman pengiriman) {
    try {
      String table = "pengiriman";
      String[] fields = {"id_kota_penerima", "isi_barang", "berat", "waktu_kirim", "nama_penerima", "alamat_penerima", "no_hp_penerima", "biaya"};
      String[] values = {
        String.valueOf(pengiriman.getKotaPenerima().getIdKota()),
        pengiriman.getIsiBarang(),
        String.valueOf(pengiriman.getBeratBarang()),
        pengiriman.getWaktuKirim().toString(),
        pengiriman.getNamaPenerima(),
        pengiriman.getAlamatPenerima(),
        pengiriman.getNoHpPenerima(),
        String.valueOf(pengiriman.getBiaya()),
        pengiriman.getNoResi()
      };
      String[] whereStatement = {"no_resi = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(PengirimanDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  /**
   * Untuk menangani proses delete Pengiriman berdasarkan noResi
   * @param noResi
   * @return boolean
   */
  public static boolean deleteByNoResi(String noResi) {
    try {
      String table = "pengiriman";
      String[] whereStatement = {"no_resi = ?"};
      String[] whereStatementSeparator = null;
      String[] values = { noResi };
      
      return DB.delete(table, whereStatement, whereStatementSeparator, values);
    } catch (SQLException ex) {
      Logger.getLogger(PengirimanDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
