/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Biaya;
import jasa_pengiriman.server.dao.BiayaDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Mengimplementasi BiayaService
 */
public class BiayaServiceImpl extends UnicastRemoteObject implements BiayaService, Serializable {

  public BiayaServiceImpl() throws RemoteException {}
  
  /**
   * Mengembalikan hasil dari generate seluruh Biaya
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Biaya> getAll() throws RemoteException {
    return BiayaDao.getAll();
  }

  /**
   * Mengembalikan hasil dari insert Biaya
   * @param biaya
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean insert(Biaya biaya) throws RemoteException {
    return BiayaDao.insert(biaya);
  }

  /**
   * Mengembalikan hasil dari update Biaya
   * @param biaya
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean update(Biaya biaya) throws RemoteException {
    return BiayaDao.update(biaya);
  }

  /**
   * Mengembalikan hasil dari delete Biaya berdasarkan idBiaya
   * @param idBiaya
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean deleteByIdBiaya(int idBiaya) throws RemoteException {
    return BiayaDao.deleteByIdBiaya(idBiaya);
  }
  
}
