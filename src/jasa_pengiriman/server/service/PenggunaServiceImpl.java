/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.server.dao.PenggunaDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Mengimplementasi PenggunaService
 */
public class PenggunaServiceImpl extends UnicastRemoteObject implements PenggunaService, Serializable {
  
  public PenggunaServiceImpl() throws RemoteException {}
  
  /**
   * Mengembalikan hasil dari generate seluruh Pengguna
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Pengguna> getAll() throws RemoteException {
    return PenggunaDao.getAll();
  }

  /**
   * Mengembalikan hasil dari generate Pengguna berdasarkan idCabang
   * @param idCabang
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Pengguna> getByIdCabang(int idCabang) throws RemoteException {
    return PenggunaDao.getByIdCabang(idCabang);
  }

  /**
   * Mengembalikan hasil dari generate Pengguna berdasarkan idPeran
   * @param idPeran
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Pengguna> getByIdPeran(int idPeran) throws RemoteException {
    return PenggunaDao.getByIdPeran(idPeran);
  }

  /**
   * Mengembalikan hasil dari insert Pengguna
   * @param pengguna
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean insert(Pengguna pengguna) throws RemoteException {
    return PenggunaDao.insert(pengguna);
  }

  /**
   * Mengembalikan hasil dari update Pengguna
   * @param pengguna
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean update(Pengguna pengguna) throws RemoteException {
    return PenggunaDao.update(pengguna);
  }

  
  /**
   * Mengembalikan hasil dari delete Pengguna berdasarkan idPengguna
   * @param idPengguna
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean deleteByIdPengguna(int idPengguna) throws RemoteException {
    return PenggunaDao.deleteByIdPengguna(idPengguna);
  }

  /**
   * Mengembalikan hasil dari update field terakhir_login berdasarkan idPengguna
   * @param terakhirLogin
   * @param idPengguna
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean updateTerakhirLoginByIdPengguna(Timestamp terakhirLogin, int idPengguna) throws RemoteException {
    return PenggunaDao.updateTerakhirLoginByIdPengguna(terakhirLogin, idPengguna);
  }
  
}
