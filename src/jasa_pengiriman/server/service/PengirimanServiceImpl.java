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

public class PengirimanServiceImpl extends UnicastRemoteObject implements PengirimanService, Serializable {

  public PengirimanServiceImpl() throws RemoteException {}
  
  @Override
  public List<Pengiriman> getAll() throws RemoteException {
    return PengirimanDao.getAll();
  }
  
  @Override
  public Pengiriman getByNoResi(String noResi) throws RemoteException {
    return PengirimanDao.getByNoResi(noResi);
  }

  @Override
  public boolean insert(Pengiriman pengiriman) throws RemoteException {
    return PengirimanDao.insert(pengiriman);
  }

  @Override
  public boolean update(Pengiriman pengiriman) throws RemoteException {
    return PengirimanDao.update(pengiriman);
  }

  @Override
  public boolean deleteByNoResi(String noResi) throws RemoteException {
    return PengirimanDao.deleteByNoResi(noResi);
  }
  
}
