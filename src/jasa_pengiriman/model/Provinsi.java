/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.model;

import java.io.Serializable;

public class Provinsi implements Serializable {
  private int idProvinsi;
  private String namaProvinsi;
  
  public void setIdProvinsi(int idProvinsi) {
    this.idProvinsi = idProvinsi;
  }
  
  public int getIdProvinsi() {
    return idProvinsi;
  }
  
  public void setNamaProvinsi(String namaProvinsi) {
    this.namaProvinsi = namaProvinsi;
  }
  
  public String getNamaProvinsi() {
    return namaProvinsi;
  }
  
  @Override
  public String toString() {
    return namaProvinsi;
  }
}
