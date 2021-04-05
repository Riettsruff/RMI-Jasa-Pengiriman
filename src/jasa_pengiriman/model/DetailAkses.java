/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.model;

import java.io.Serializable;

public class DetailAkses implements Serializable {
  private int idDetailAkses;
  private Peran peran;
  private Akses akses;
  private String batasanOperasi;
  
  public void setIdDetailAkses(int idDetailAkses) {
    this.idDetailAkses = idDetailAkses;
  }
  
  public int getIdDetailAkses() {
    return idDetailAkses;
  }
  
  public void setPeran(Peran peran) {
    this.peran = peran;
  }
  
  public Peran getPeran() {
    return peran;
  }
  
  public void setAkses(Akses akses) {
    this.akses = akses;
  }
  
  public Akses getAkses() {
    return akses;
  }
  
  public void setBatasanOperasi(String batasanOperasi) {
    this.batasanOperasi = batasanOperasi;
  }
  
  public String getBatasanOperasi() {
    return batasanOperasi;
  }
}
