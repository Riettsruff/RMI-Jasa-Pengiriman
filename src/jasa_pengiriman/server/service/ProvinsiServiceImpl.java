/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Provinsi;
import jasa_pengiriman.server.dao.ProvinsiDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Mengimplementasi ProvinsiService
 */
public class ProvinsiServiceImpl extends UnicastRemoteObject implements ProvinsiService, Serializable {

  public ProvinsiServiceImpl() throws RemoteException {}
  
  /**
   * Mengembalikan hasil dari generate seluruh Provinsi
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Provinsi> getAll() throws RemoteException {
    return ProvinsiDao.getAll();
  }

  /**
   * Mengembalikan hasil dari insert Provinsi
   * @param provinsi
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean insert(Provinsi provinsi) throws RemoteException {
    return ProvinsiDao.insert(provinsi);
  }

  /**
   * Mengembalikan hasil dari update Provinsi
   * @param provinsi
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean update(Provinsi provinsi) throws RemoteException {
    return ProvinsiDao.update(provinsi);
  }

  /**
   * Mengembalikan hasil dari delete Provinsi berdasarkan idProvinsi
   * @param idProvinsi
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean deleteByIdProvinsi(int idProvinsi) throws RemoteException {
    return ProvinsiDao.deleteByIdProvinsi(idProvinsi);
  }
  
}
