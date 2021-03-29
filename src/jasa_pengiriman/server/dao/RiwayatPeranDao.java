/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.client.service.DateFormat;
import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.model.Peran;
import jasa_pengiriman.model.RiwayatPeran;
import jasa_pengiriman.server.helper.DB;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Sebagai DAO untuk RiwayatPeran
 */
public class RiwayatPeranDao {
  
  /**
   * Untuk generate seluruh RiwayatPeran berdasarkan idPengguna
   * @param idPengguna
   * @return List
   */
  public static List<RiwayatPeran> getByIdPengguna(int idPengguna) {
    List<RiwayatPeran> riwayatPeranList = new ArrayList<RiwayatPeran>();
    
    try {
      String[] values = {Integer.toString(idPengguna)};
      ResultSet rs = DB.query("SELECT a.id_riwayat_peran, a.tanggal_mulai, b.id_pengguna, b.nama, c.* FROM riwayat_peran a INNER JOIN pengguna b ON a.id_pengguna = b.id_pengguna LEFT JOIN peran c ON a.id_peran = c.id_peran WHERE a.id_pengguna = ? ORDER BY a.tanggal_mulai DESC", values);
      
      while(rs.next()) {
        RiwayatPeran riwayatPeran = new RiwayatPeran();
        Pengguna pengguna = new Pengguna();
        Peran peran = new Peran();
        
        pengguna.setIdPengguna(rs.getInt("id_pengguna"));
        pengguna.setNama(rs.getString("nama"));
        peran.setIdPeran(rs.getInt("id_peran"));
        peran.setNamaPeran(rs.getString("nama_peran"));
        riwayatPeran.setIdRiwayatPeran(rs.getInt("id_riwayat_peran"));
        riwayatPeran.setPengguna(pengguna);
        riwayatPeran.setPeran(peran);
        riwayatPeran.setTanggalMulai(rs.getDate("tanggal_mulai"));
        
        riwayatPeranList.add(riwayatPeran);
      }
    } catch (SQLException ex) {
      Logger.getLogger(RiwayatPeranDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return riwayatPeranList;
  }
  
  /**
   * Untuk menangani proses insert RiwayatPeran
   * @param riwayatPeran
   * @return boolean
   */
  public static boolean insert(RiwayatPeran riwayatPeran) {
    try {
      String table = "riwayat_peran";
      String[] values = {
        String.valueOf(riwayatPeran.getIdRiwayatPeran()),
        String.valueOf(riwayatPeran.getPengguna().getIdPengguna()),
        String.valueOf(riwayatPeran.getPeran().getIdPeran()),
        DateFormat.dateToString(riwayatPeran.getTanggalMulai(), "yyyy-MM-dd")
      };
      
      return DB.insert(table, values);
    } catch (SQLException ex) {
      Logger.getLogger(RiwayatPeranDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
