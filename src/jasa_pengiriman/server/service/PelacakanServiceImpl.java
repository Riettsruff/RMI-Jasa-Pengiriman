/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Pelacakan;
import jasa_pengiriman.server.dao.PelacakanDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Mengimplementasi PelacakanService
 */
public class PelacakanServiceImpl extends UnicastRemoteObject implements PelacakanService, Serializable {

  public PelacakanServiceImpl() throws RemoteException {}
  
  /**
   * Mengembalikan hasil dari generate seluruh Pelacakan
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Pelacakan> getAll() throws RemoteException {
    return PelacakanDao.getAll();
  }
  
  /**
   * Mengembalikan hasil dari generate Pelacakan berdasarkan noResi
   * @param noResi
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Pelacakan> getByNoResi(String noResi) throws RemoteException {
    return PelacakanDao.getByNoResi(noResi);
  }

  /**
   * Mengembalikan hasil dari insert Pelacakan
   * @param pelacakan
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean insert(Pelacakan pelacakan) throws RemoteException {
    return PelacakanDao.insert(pelacakan);
  }

  /**
   * Mengembalikan hasil dari update Pelacakan
   * @param pelacakan
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean update(Pelacakan pelacakan) throws RemoteException {
    return PelacakanDao.update(pelacakan);
  }

  /**
   * Mengembalikan hasil dari delete Pelacakan berdasarkan idPelacakan
   * @param idPelacakan
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean deleteByIdPelacakan(int idPelacakan) throws RemoteException {
    return PelacakanDao.deleteByIdPelacakan(idPelacakan);
  }
  
}
