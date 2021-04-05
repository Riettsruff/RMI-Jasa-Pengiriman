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

public class KotaServiceImpl extends UnicastRemoteObject implements KotaService, Serializable {

  public KotaServiceImpl() throws RemoteException {}
  
  @Override
  public List<Kota> getAll() throws RemoteException {
    return KotaDao.getAll();
  }

  @Override
  public List<Kota> getByIdProvinsi(int idProvinsi) throws RemoteException {
    return KotaDao.getByIdProvinsi(idProvinsi);
  }

  @Override
  public boolean insert(Kota kota) throws RemoteException {
    return KotaDao.insert(kota);
  }

  @Override
  public boolean update(Kota kota) throws RemoteException {
    return KotaDao.update(kota);
  }

  @Override
  public boolean deleteByIdKota(int idKota) throws RemoteException {
    return KotaDao.deleteByIdKota(idKota);
  }
  
}
