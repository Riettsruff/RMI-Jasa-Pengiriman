/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.model;

import java.io.Serializable;

/**
 * Sebagai Model untuk Akses
 */
public class Akses implements Serializable {
  private int idAkses;
  private String namaAkses, operasi;
  
  public void setIdAkses(int idAkses) {
    this.idAkses = idAkses;
  }
  
  public int getIdAkses() {
    return idAkses;
  }
  
  public void setNamaAkses(String namaAkses) {
    this.namaAkses = namaAkses;
  }
  
  public String getNamaAkses() {
    return namaAkses;
  }
  
  public void setOperasi(String operasi) {
    this.operasi = operasi;
  }
  
  public String getOperasi() {
    return operasi;
  }
}
