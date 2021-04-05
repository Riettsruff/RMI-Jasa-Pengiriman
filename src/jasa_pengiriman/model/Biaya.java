/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.model;

import java.io.Serializable;

public class Biaya implements Serializable {
  private int idBiaya;
  private Kota kotaAsal, kotaTujuan;
  private long harga;
  
  public void setIdBiaya(int idBiaya) {
    this.idBiaya = idBiaya;
  }
  
  public int getIdBiaya() {
    return idBiaya;
  }
  
  public void setKotaAsal(Kota kotaAsal) {
    this.kotaAsal = kotaAsal;
  }
  
  public Kota getKotaAsal() {
    return kotaAsal;
  } 
  
  public void setKotaTujuan(Kota kotaTujuan) {
    this.kotaTujuan = kotaTujuan;
  }
  
  public Kota getKotaTujuan() {
    return kotaTujuan;
  }
  
  public void setHarga(long harga) {
    this.harga = harga;
  }
  
  public long getHarga() {
    return harga;
  }
  
}
  

