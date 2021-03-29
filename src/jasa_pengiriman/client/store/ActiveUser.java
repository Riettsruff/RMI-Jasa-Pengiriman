/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.store;

import jasa_pengiriman.model.Pengguna;

/**
 * Sebagai Store untuk informasi pengguna
 */
public class ActiveUser {
  private static Pengguna pengguna = null;
  
  /**
   * Untuk set informasi pengguna
   * @param pengguna 
   */
  public static void set(Pengguna pengguna) {
    ActiveUser.pengguna = pengguna;
  }
  
  /**
   * Untuk remove informasi pengguna
   */
  public static void remove() {
    ActiveUser.pengguna = null;
  }
  
  /**
   * Untuk get informasi pengguna
   * @return Pengguna
   */
  public static Pengguna get() {
    return ActiveUser.pengguna;
  }
}
