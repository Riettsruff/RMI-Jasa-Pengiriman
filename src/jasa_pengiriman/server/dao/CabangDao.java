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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riett
 */
public class CabangDao {
  
  public static List<Cabang> getAll() {
    List<Cabang> cabangList = new ArrayList<Cabang>();
    
    try {
      ResultSet rs = DB.query("SELECT a.id_cabang, a.nama_cabang, a.alamat, a.no_hp, b.id_kota, b.nama_kota, c.id_provinsi, c.nama_provinsi FROM cabang a INNER JOIN kota b ON a.id_kota = b.id_kota INNER JOIN provinsi c ON b.id_provinsi = c.id_provinsi");
      
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
    } catch (Exception e) { 
      Logger.getLogger(CabangDao.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return cabangList;
  }
}
