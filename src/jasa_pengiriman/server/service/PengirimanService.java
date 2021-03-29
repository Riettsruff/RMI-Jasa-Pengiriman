/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Pengiriman;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Sebagai antarmuka PengirimanService
 */
public interface PengirimanService extends Remote {
  public List<Pengiriman> getAll() throws RemoteException;
  public boolean insert(Pengiriman pengiriman) throws RemoteException;
  public boolean update(Pengiriman pengiriman) throws RemoteException;
  public boolean deleteByNoResi(String noResi) throws RemoteException;
}
