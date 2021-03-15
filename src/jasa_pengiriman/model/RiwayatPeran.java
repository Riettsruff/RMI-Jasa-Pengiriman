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
public class RiwayatPeran implements Serializable {
  private int idRiwayatPeran;
  private Pengguna pengguna;
  private Peran peran;
  private Timestamp tanggalDibuat;
  
  public void setIdRiwayatPeran(int idRiwayatPeran) {
    this.idRiwayatPeran = idRiwayatPeran;
  }
  
  public int getIdRiwayatPeran() {
    return idRiwayatPeran;
  }
  
  public void setPengguna(Pengguna pengguna) {
    this.pengguna = pengguna;
  }
  
  public Pengguna getPengguna() {
    return pengguna;
  }
  
  public void setPeran(Peran peran) {
    this.peran = peran;
  }
  
  public Peran getPeran() {
    return peran;
  }
  
  public void setTanggalDibuat(Timestamp tanggalDibuat) {
    this.tanggalDibuat = tanggalDibuat;
  }
  
  public Timestamp getTanggalDibuat() {
    return tanggalDibuat;
  }
}
