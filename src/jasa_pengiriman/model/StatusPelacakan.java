/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.model;

import java.io.Serializable;

public class StatusPelacakan implements Serializable {
  private int idStatusPelacakan;
  private String namaStatus;

  public int getIdStatusPelacakan() {
    return idStatusPelacakan;
  }

  public void setIdStatusPelacakan(int idStatusPelacakan) {
    this.idStatusPelacakan = idStatusPelacakan;
  }

  public String getNamaStatus() {
    return namaStatus;
  }

  public void setNamaStatus(String namaStatus) {
    this.namaStatus = namaStatus;
  }
  
  @Override
  public String toString() {
    return namaStatus;
  }
}
