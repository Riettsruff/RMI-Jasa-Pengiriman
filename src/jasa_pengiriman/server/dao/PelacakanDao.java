/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.dao;

import jasa_pengiriman.model.Pelacakan;
import java.util.ArrayList;
import java.util.List;

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
    
    return pelacakanList;
  }
  
  /**
   * Untuk menangani proses insert Pelacakan
   * @param pelacakan
   * @return boolean
   */
  public static boolean insert(Pelacakan pelacakan) {
    return false;
  }
  
  /**
   * Untuk menangani proses update Pelacakan
   * @param pelacakan
   * @return boolean
   */
  public static boolean update(Pelacakan pelacakan) {
    return false;
  }
  
  /**
   * Untuk menangani proses delete Pelcakan berdasarkan idPelacakan
   * @param idPelacakan
   * @return boolean
   */
  public static boolean deleteByIdPelacakan(int idPelacakan) {
    return false;
  }
}
