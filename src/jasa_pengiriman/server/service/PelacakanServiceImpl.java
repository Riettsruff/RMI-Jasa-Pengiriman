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

public class PelacakanServiceImpl extends UnicastRemoteObject implements PelacakanService, Serializable {

  public PelacakanServiceImpl() throws RemoteException {}
  
  @Override
  public List<Pelacakan> getAll() throws RemoteException {
    return PelacakanDao.getAll();
  }
  
  @Override
  public List<Pelacakan> getByNoResi(String noResi) throws RemoteException {
    return PelacakanDao.getByNoResi(noResi);
  }

  @Override
  public boolean insert(Pelacakan pelacakan) throws RemoteException {
    return PelacakanDao.insert(pelacakan);
  }

  @Override
  public boolean update(Pelacakan pelacakan) throws RemoteException {
    return PelacakanDao.update(pelacakan);
  }

  @Override
  public boolean deleteByIdPelacakan(int idPelacakan) throws RemoteException {
    return PelacakanDao.deleteByIdPelacakan(idPelacakan);
  }
  
}
