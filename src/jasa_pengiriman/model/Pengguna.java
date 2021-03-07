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
public class Pengguna implements Serializable {
  private int idPengguna;
  private Cabang cabang;
  private Peran peran;
  private String email, password;
  private Timestamp terakhirLogin;
  
  public int getIdPengguna() {
    return idPengguna;
  }
  
  public void setIdPengguna(int idPengguna) {
    this.idPengguna = idPengguna;
  }
  
  public Cabang getCabang() {
    return cabang;
  }
  
  public void setCabang(Cabang cabang) {
    this.cabang = cabang;
  }
  
  public Peran getPeran() {
    return peran;
  }
  
  public void setPeran(Peran peran) {
    this.peran = peran;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public Timestamp getTerakhirLogin() {
    return terakhirLogin;
  }
  
  public void setTerakhirLogin(Timestamp terakhirLogin) {
    this.terakhirLogin = terakhirLogin;
  }
}
