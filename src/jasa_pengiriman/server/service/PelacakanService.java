/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Pelacakan;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Sebagai antarmuka PelacakanService
 */
public interface PelacakanService extends Remote {
  public List<Pelacakan> getAll() throws RemoteException;
  public List<Pelacakan> getByNoResi(String noResi) throws RemoteException;
  public boolean insert(Pelacakan pelacakan) throws RemoteException;
  public boolean update(Pelacakan pelacakan) throws RemoteException;
  public boolean deleteByIdPelacakan(int idPelacakan) throws RemoteException;
}
