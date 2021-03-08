/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.model;

import java.io.Serializable;

/**
 *
 * @author Riett
 */
public class Kota implements Serializable {
  private int idKota;
  private Provinsi provinsi;
  private String namaKota;
  
  public void setIdKota(int idKota) {
    this.idKota = idKota;
  }
  
  public int getIdKota() {
    return idKota;
  }
  
  public void setProvinsi(Provinsi provinsi) {
    this.provinsi = provinsi;
  }
  
  public Provinsi getProvinsi() {
    return provinsi;
  }
  
  public void setNamaKota(String namaKota) {
    this.namaKota = namaKota;
  }
  
  public String getNamaKota() {
    return namaKota;
  }
}
