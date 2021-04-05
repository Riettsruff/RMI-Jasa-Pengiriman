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

public class BiayaServiceImpl extends UnicastRemoteObject implements BiayaService, Serializable {

  public BiayaServiceImpl() throws RemoteException {}
  
  @Override
  public List<Biaya> getAll() throws RemoteException {
    return BiayaDao.getAll();
  }

  @Override
  public boolean insert(Biaya biaya) throws RemoteException {
    return BiayaDao.insert(biaya);
  }

  @Override
  public boolean update(Biaya biaya) throws RemoteException {
    return BiayaDao.update(biaya);
  }

  @Override
  public boolean deleteByIdBiaya(int idBiaya) throws RemoteException {
    return BiayaDao.deleteByIdBiaya(idBiaya);
  }

  @Override
  public Biaya getByRoute(int idKotaAsal, int idKotaTujuan) throws RemoteException {
    return BiayaDao.getByRoute(idKotaAsal, idKotaTujuan);
  }
  
}
