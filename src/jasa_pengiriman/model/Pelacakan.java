/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Riett
 */
public class Pelacakan implements Serializable {
  private int idPelacakan;
  private Pengiriman pengiriman;
  private Cabang cabang;
  private Timestamp waktuLapor;
  private String status, keterangan;
  
  public void setIdPelacakan(int idPelacakan) {
    this.idPelacakan = idPelacakan;
  }
  
  public int getIdPelacakan() {
    return idPelacakan;
  }
  
  public void setPengiriman(Pengiriman pengiriman) {
    this.pengiriman = pengiriman;
  }
  
  public Pengiriman getPengiriman() {
    return pengiriman;
  }
  
  public void setCabang(Cabang cabang) {
    this.cabang = cabang;
  }
  
  public Cabang getCabang() {
    return cabang;
  }
  
  public void setWaktuLapor(Timestamp waktuLapor) {
    this.waktuLapor = waktuLapor;
  }
  
  public Timestamp getWaktuLapor() {
    return waktuLapor;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setKeterangan(String keterangan) {
    this.keterangan = keterangan;
  }
  
  public String getKeterangan() {
    return keterangan;
  }
}
