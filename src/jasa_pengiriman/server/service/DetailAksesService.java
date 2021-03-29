/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.DetailAkses;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Sebagai antarmuka DetailAksesService
 */
public interface DetailAksesService extends Remote {
  public List<DetailAkses> getByIdAkses(int idAkses) throws RemoteException;
  public List<DetailAkses> getByIdPeran(int idPeran) throws RemoteException;
  public List<DetailAkses> getAll() throws RemoteException;
  public boolean insert(DetailAkses detailAkses) throws RemoteException;
  public boolean update(DetailAkses detailAkses) throws RemoteException;
  public boolean deleteByIdDetailAkses(int idDetailAkses) throws RemoteException;
}
