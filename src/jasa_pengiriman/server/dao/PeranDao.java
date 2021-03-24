/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

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
public class PeranDao {
  
  public static List<Peran> getAll() {
    List<Peran> peranList = new ArrayList<Peran>();
    
    try {
      ResultSet rs = DB.get("peran");
      
      while(rs.next()) {
        Peran peran = new Peran();
        
        peran.setIdPeran(rs.getInt("id_peran"));
        peran.setNamaPeran(rs.getString("nama_peran"));
        
        peranList.add(peran);
      }
    } catch (SQLException ex) {
      Logger.getLogger(PeranDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return peranList;
  }
  
  public static boolean insert(Peran peran) {
    try {
      String table = "peran";
      String[] values = {
        String.valueOf(peran.getIdPeran()),
        peran.getNamaPeran()
      };
      
      return DB.insert(table, values);
    } catch (SQLException ex) {
      Logger.getLogger(PeranDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean update(Peran peran) {
    try {
      String table = "peran";
      String[] fields = {"nama_peran"};
      String[] values = {
        peran.getNamaPeran()
      };
      String[] whereStatement = {"id_peran = ?"};
      String[] whereStatementSeparator = null;
      
      return DB.update(table, fields, values, whereStatement, whereStatementSeparator);
    } catch (SQLException ex) {
      Logger.getLogger(PeranDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
  
  public static boolean deleteByIdPeran(int idPeran) {
    try {
      String table = "peran";
      String[] whereStatement = {"id_peran = ?"};
      String[] whereStatementSeparator = null;
      String[] values = {
        String.valueOf(idPeran)
      };
      
      return DB.delete(table, whereStatement, whereStatementSeparator, values);
    } catch (SQLException ex) {
      Logger.getLogger(PeranDao.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    return false;
  }
}
