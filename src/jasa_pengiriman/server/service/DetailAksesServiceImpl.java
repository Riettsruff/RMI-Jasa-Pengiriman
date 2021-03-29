/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.DetailAkses;
import jasa_pengiriman.server.dao.DetailAksesDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Mengimplementasi DetailAksesService
 */
public class DetailAksesServiceImpl extends UnicastRemoteObject implements DetailAksesService, Serializable {

  public DetailAksesServiceImpl() throws RemoteException {}
  
  /**
   * Mengembalikan hasil dari generate DetailAkses berdasarkan idAkses
   * @param idAkses
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<DetailAkses> getByIdAkses(int idAkses) throws RemoteException {
    return DetailAksesDao.getByIdAkses(idAkses);
  }

  /**
   * Mengembalikan hasil dari generate DetailAkses berdasarkan idPeran
   * @param idPeran 
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<DetailAkses> getByIdPeran(int idPeran) throws RemoteException {
    return DetailAksesDao.getByIdPeran(idPeran);
  }

  /**
   * Mengembalikan hasil dari generate seluruh DetailAkses
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<DetailAkses> getAll() throws RemoteException {
    return DetailAksesDao.getAll();
  }

  /**
   * Mengembalikan hasil dari insert DetailAkses
   * @param detailAkses
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean insert(DetailAkses detailAkses) throws RemoteException {
    return DetailAksesDao.insert(detailAkses);
  }

  /**
   * Mengembalikan hasil dari update DetailAkses
   * @param detailAkses
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean update(DetailAkses detailAkses) throws RemoteException {
    return DetailAksesDao.update(detailAkses);
  }

  /**
   * Mengembalikan hasil dari delete DetailAkses berdasarkan idDetailAkses
   * @param idDetailAkses
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean deleteByIdDetailAkses(int idDetailAkses) throws RemoteException {
    return DetailAksesDao.deleteByIdDetailAkses(idDetailAkses);
  }
  
}
