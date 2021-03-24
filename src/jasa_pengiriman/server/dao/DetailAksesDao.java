/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Akses;
import jasa_pengiriman.model.DetailAkses;
import jasa_pengiriman.model.Peran;
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
public class DetailAksesDao {
  private static List<DetailAkses> get(String type, String[] values) {
    List<DetailAkses> detailAksesList = new ArrayList<DetailAkses>();
    
    try {
      String query = "SELECT a.*, b.id_detail_akses, b.batasan_operasi, c.* FROM akses a INNER JOIN detail_akses b ON a.id_akses = b.id_akses INNER JOIN peran c ON b.id_peran = c.id_peran";
      ResultSet rs = null;
      
      switch(type) {
        case "BY_ID_AKSES":
          query += " WHERE a.id_akses = ?";
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
        Peran peran = new Peran();
        Akses akses = new Akses();
        DetailAkses detailAkses = new DetailAkses();
        
        peran.setIdPeran(rs.getInt("id_peran"));
        peran.setNamaPeran(rs.getString("nama_peran"));
        akses.setIdAkses(rs.getInt("id_akses"));
        akses.setNamaAkses(rs.getString("nama_akses"));
        akses.setOperasi(rs.getString("operasi"));
        detailAkses.setIdDetailAkses(rs.getInt("id_detail_akses"));
        detailAkses.setPeran(peran);
        detailAkses.setAkses(akses);
        detailAkses.setBatasanOperasi(rs.getString("batasan_operasi"));
        
        detailAksesList.add(detailAkses);
      }
    } catch (SQLException ex) {
      Logger.getLogger(DetailAksesDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return detailAksesList;
  }
  
  public static List<DetailAkses> getByIdAkses(int idAkses) {
    String[] values = {Integer.toString(idAkses)};
    return get("BY_ID_AKSES", values);
  }
  
  public static List<DetailAkses> getByIdPeran(int idPeran) {
    String[] values = {Integer.toString(idPeran)};
    return get("BY_ID_PERAN", values);
  }
  
  public static List<DetailAkses> getAll() {
    return get("ALL", null);
  }
  
  public static boolean insert(DetailAkses detailAkses) {
    try {
      String table = "detail_akses";
      String[] values = {
        String.valueOf(detailAkses.getIdDetailAkses()),
        String.valueOf(detailAkses.getPeran().getIdPeran()),
        String.valueOf(detailAkses.getAkses().getIdAkses()),
        detailAkses.getBatasanOperasi()
      };
      
      return DB.insert(table, values);
    } catch (SQLException ex) {
      Logger.getLogger(DetailAksesDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean update(DetailAkses detailAkses) {
    try {
      String table = "detail_akses";
      String[] fields = {"id_peran", "id_akses", "batasan_operasi"};
      String[] values = {
        String.valueOf(detailAkses.getPeran().getIdPeran()),
        String.valueOf(detailAkses.getAkses().getIdAkses()),
        detailAkses.getBatasanOperasi(),
        String.valueOf(detailAkses.getIdDetailAkses())
      };
      String[] whereStatement = {"id_detail_akses = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(DetailAksesDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean deleteByIdDetailAkses(int idDetailAkses) {
    try {
      String table = "detail_akses";
      String[] whereStatement = {"id_detail_akses = ?"};
      String[] whereStatementSeparator = null;
      String[] values = {
        String.valueOf(idDetailAkses)
      };
      
      return DB.delete(table, whereStatement, whereStatementSeparator, values);
    } catch (SQLException ex) {
      Logger.getLogger(DetailAksesDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
