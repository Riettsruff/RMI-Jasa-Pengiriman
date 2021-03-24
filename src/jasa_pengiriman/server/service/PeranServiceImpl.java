/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Peran;
import jasa_pengiriman.server.dao.PeranDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Riett
 */
public class PeranServiceImpl extends UnicastRemoteObject implements PeranService, Serializable {

  public PeranServiceImpl() throws RemoteException {}
  
  @Override
  public List<Peran> getAll() throws RemoteException {
    return PeranDao.getAll();
  }

  @Override
  public boolean insert(Peran peran) throws RemoteException {
    return PeranDao.insert(peran);
  }

  @Override
  public boolean update(Peran peran) throws RemoteException {
    return PeranDao.update(peran);
  }

  @Override
  public boolean deleteByIdPeran(int idPeran) throws RemoteException {
    return PeranDao.deleteByIdPeran(idPeran);
  }
  
}
