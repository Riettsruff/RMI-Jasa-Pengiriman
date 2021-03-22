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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riett
 */
public class DetailAksesDao {
  private static List<DetailAkses> get(String type, String[] psSet) {
    List<DetailAkses> detailAksesList = new ArrayList<DetailAkses>();
    
    try {
      String query = "SELECT a.*, b.id_detail_akses, b.batasan_operasi, c.* FROM akses a INNER JOIN detail_akses b ON a.id_akses = b.id_akses INNER JOIN peran c ON b.id_peran = c.id_peran";
      ResultSet rs = null;
      
      switch(type) {
        case "BY_ID_AKSES":
          query += " WHERE a.id_akses = ?";
          rs = DB.query(query, psSet);
        break;
        case "BY_ID_PERAN":
          query += " WHERE c.id_peran = ?";
          rs = DB.query(query, psSet);
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
    } catch (Exception e) { 
      Logger.getLogger(DetailAksesDao.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return detailAksesList;
  }
  
  public static List<DetailAkses> getByIdAkses(int idAkses) {
    String[] psSet = {Integer.toString(idAkses)};
    return get("BY_ID_AKSES", psSet);
  }
  
  public static List<DetailAkses> getByIdPeran(int idPeran) {
    String[] psSet = {Integer.toString(idPeran)};
    return get("BY_ID_PERAN", psSet);
  }
  
  public static List<DetailAkses> getAll() {
    return get("ALL", null);
  }
}
