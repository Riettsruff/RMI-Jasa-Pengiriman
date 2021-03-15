/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.model.Peran;
import jasa_pengiriman.server.helper.DB;
import static jasa_pengiriman.server.service.Conn.Conn;
import java.sql.PreparedStatement;
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
public class PenggunaDao {
  
  private static List<Pengguna> get(String type, String[] psSet) {
    List<Pengguna> penggunaList = new ArrayList<Pengguna>();
    
    try {
      String query = "SELECT a.id_pengguna, a.nama, a.email, a.terakhir_login, b.id_cabang, b.nama_cabang, c.* FROM pengguna a LEFT JOIN cabang b ON a.id_cabang = b.id_cabang LEFT JOIN peran c ON c.id_peran = a.id_peran";
      ResultSet rs = null;
      
      switch(type) {
        case "BY_ID_CABANG":
          query += " WHERE b.id_cabang = ?";
          rs = DB.query(query, psSet);
        break;
        case "BY_ID_PERAN":
          query += " WHERE c.id_peran = ?";
          rs = DB.query(query, psSet);
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
        pengguna.setTerakhirLogin(rs.getTimestamp("terakhir_login"));
        
        penggunaList.add(pengguna);
      }
    } catch (Exception e) { 
      Logger.getLogger(PenggunaDao.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return penggunaList;
  } 
  
  public static List<Pengguna> getAll() {
    return PenggunaDao.get("ALL", null);
  }
  
  public static List<Pengguna> getByIdCabang(int idCabang) {
    String[] psSet = {Integer.toString(idCabang)};
    return PenggunaDao.get("BY_ID_CABANG", psSet);
  }
  
  public static List<Pengguna> getByIdPeran(int idPeran) {
    String[] psSet = {Integer.toString(idPeran)};
    return PenggunaDao.get("BY_ID_PERAN", psSet);
  }
}
