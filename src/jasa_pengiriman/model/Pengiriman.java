/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Sebagai Model untuk Pengiriman
 */
public class Pengiriman implements Serializable {
  private String noResi, isiBarang, namaPenerima, alamatPenerima, noHpPenerima;
  private Cabang cabangPengirim;
  private Kota kotaPenerima;
  private double beratBarang;
  private Timestamp waktuKirim;
  private long biaya;
  
  public void setNoResi(String noResi) {
    this.noResi = noResi;
  }
  
  public String getNoResi() {
    return noResi;
  }
  
  public void setIsiBarang(String isiBarang) {
    this.isiBarang = isiBarang;
  }
  
  public String getIsiBarang() {
    return isiBarang;
  }
  
  public void setNamaPenerima(String namaPenerima) {
    this.namaPenerima = namaPenerima;
  }
  
  public String getNamaPenerima() {
    return namaPenerima;
  }
  
  public void setAlamatPenerima(String alamatPenerima) {
    this.alamatPenerima = alamatPenerima;
  }
  
  public String getAlamatPenerima() {
    return alamatPenerima;
  }
  
  public void setNoHpPenerima(String noHpPenerima) {
    this.noHpPenerima = noHpPenerima;
  }
  
  public String getNoHpPenerima() {
    return noHpPenerima;
  }
  
  public void setCabangPengirim(Cabang cabangPengirim) {
    this.cabangPengirim = cabangPengirim;
  }
  
  public Cabang getCabangPengirim() {
    return cabangPengirim;
  }
  
  public void setKotaPenerima(Kota kotaPenerima) {
    this.kotaPenerima = kotaPenerima;
  }
  
  public Kota getKotaPenerima() {
    return kotaPenerima;
  }
  
  public void setBeratBarang(double beratBarang) {
    this.beratBarang = beratBarang;
  }
  
  public double getBeratBarang() {
    return beratBarang;
  }
  
  public void setWaktuKirim(Timestamp waktuKirim) {
    this.waktuKirim = waktuKirim;
  }
  
  public Timestamp getWaktuKirim() {
    return waktuKirim;
  }
  
  public void setBiaya(long biaya) {
    this.biaya = biaya;
  }
  
  public long getBiaya() {
    return biaya;
  }
}
