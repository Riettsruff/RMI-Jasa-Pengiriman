/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.store;

import jasa_pengiriman.model.Pengguna;

public class ActiveUser {
  private static Pengguna pengguna = null;
  
  public static void set(Pengguna pengguna) {
    ActiveUser.pengguna = pengguna;
  }

  public static void remove() {
    ActiveUser.pengguna = null;
  }
  
  public static Pengguna get() {
    return ActiveUser.pengguna;
  }
}
