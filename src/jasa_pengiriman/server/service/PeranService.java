/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Peran;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Sebagai antarmuka PeranService
 */
public interface PeranService extends Remote {
  public List<Peran> getAll() throws RemoteException;
  public boolean insert(Peran peran) throws RemoteException;
  public boolean update(Peran peran) throws RemoteException;
  public boolean deleteByIdPeran(int idPeran) throws RemoteException;
}
