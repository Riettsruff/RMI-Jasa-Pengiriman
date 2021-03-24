/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.RiwayatPeran;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Riett
 */
public interface RiwayatPeranService extends Remote {
  public List<RiwayatPeran> getByIdPengguna(int idPengguna) throws RemoteException; 
  public boolean insert(RiwayatPeran riwayatPeran) throws RemoteException;
}
