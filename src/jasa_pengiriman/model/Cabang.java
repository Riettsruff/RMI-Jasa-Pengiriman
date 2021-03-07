/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.model;

/**
 *
 * @author Riett
 */
public class Cabang {
  private int idCabang;
  private String namaCabang, alamat, noHp;
  
  public void setIdCabang(int idCabang) {
    this.idCabang = idCabang;
  }
  
  public int getIdCabang() {
    return idCabang;
  }
  
  public void setNamaCabang(String namaCabang) {
    this.namaCabang = namaCabang;
  }
  
  public String getNamaCabang() {
    return namaCabang;
  }
  
  public void setAlamat(String alamat) {
    this.alamat = alamat;
  }
  
  public String getAlamat() {
    return alamat;
  }
  
  public void setNoHp(String noHp) {
    this.noHp = noHp;
  }
  
  public String getNoHp() {
    return noHp;
  }
}
  

