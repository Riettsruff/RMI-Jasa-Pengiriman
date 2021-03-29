/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Kota;
import jasa_pengiriman.server.dao.KotaDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Mengimplementasi KotaService
 */
public class KotaServiceImpl extends UnicastRemoteObject implements KotaService, Serializable {

  public KotaServiceImpl() throws RemoteException {}
  
  /**
   * Mengembalikan hasil dari generate seluruh Kota
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Kota> getAll() throws RemoteException {
    return KotaDao.getAll();
  }

  /**
   * Mengembalikan hasil dari generate Kota berdasarkan idProvinsi
   * @param idProvinsi
   * @return List
   * @throws RemoteException 
   */
  @Override
  public List<Kota> getByIdProvinsi(int idProvinsi) throws RemoteException {
    return KotaDao.getByIdProvinsi(idProvinsi);
  }

  /**
   * Mengembalikan hasil dari insert Kota
   * @param kota
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean insert(Kota kota) throws RemoteException {
    return KotaDao.insert(kota);
  }

  /**
   * Mengembalikan hasil dari update Kota
   * @param kota
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean update(Kota kota) throws RemoteException {
    return KotaDao.update(kota);
  }

  /**
   * Mengembalikan hasil dari delete Kota berdasarkan idKota
   * @param idKota
   * @return boolean
   * @throws RemoteException 
   */
  @Override
  public boolean deleteByIdKota(int idKota) throws RemoteException {
    return KotaDao.deleteByIdKota(idKota);
  }
  
}
