/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Pengiriman;
import jasa_pengiriman.server.dao.PengirimanDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Mengimplementasikan PengirimanService
 */
public class PengirimanServiceImpl extends UnicastRemoteObject implements PengirimanService, Serializable {

  public PengirimanServiceImpl() throws RemoteException {}
  
  /**
   * Mengembalikan hasil dari generate seluruh Pengiriman
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Pengiriman> getAll() throws RemoteException {
    return PengirimanDao.getAll();
  }
  
  /**
   * Mengembalikan hasil dari generate Pengiriman berdasarkan noResi
   * @param noResi
   * @return Pengiriman
   * @throws RemoteException 
   */
  @Override
  public Pengiriman getByNoResi(String noResi) throws RemoteException {
    return PengirimanDao.getByNoResi(noResi);
  }

  /**
   * Mengembalikan hasil dari insert Pengiriman
   * @param pengiriman
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean insert(Pengiriman pengiriman) throws RemoteException {
    return PengirimanDao.insert(pengiriman);
  }

  /**
   * Mengembalikan hasil dari update Pengiriman
   * @param pengiriman
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean update(Pengiriman pengiriman) throws RemoteException {
    return PengirimanDao.update(pengiriman);
  }

  /**
   * Mengembalikan hasil dari delete Pengiriman berdasarkan noResi
   * @param noResi
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean deleteByNoResi(String noResi) throws RemoteException {
    return PengirimanDao.deleteByNoResi(noResi);
  }
  
}
