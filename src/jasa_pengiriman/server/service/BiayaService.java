/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Biaya;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Sebagai antarmuka BiayaService
 */
public interface BiayaService extends Remote {
  public List<Biaya> getAll() throws RemoteException;
  public boolean insert(Biaya biaya) throws RemoteException;
  public boolean update(Biaya biaya) throws RemoteException;
  public boolean deleteByIdBiaya(int idBiaya) throws RemoteException;
}
