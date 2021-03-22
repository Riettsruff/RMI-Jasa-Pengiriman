/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.DetailAkses;
import jasa_pengiriman.server.dao.DetailAksesDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Riett
 */
public class DetailAksesServiceImpl extends UnicastRemoteObject implements DetailAksesService, Serializable {

  public DetailAksesServiceImpl() throws RemoteException {}
  
  @Override
  public List<DetailAkses> getByIdAkses(int idAkses) throws RemoteException {
    return DetailAksesDao.getByIdAkses(idAkses);
  }

  @Override
  public List<DetailAkses> getByIdPeran(int idPeran) throws RemoteException {
    return DetailAksesDao.getByIdPeran(idPeran);
  }

  @Override
  public List<DetailAkses> getAll() throws RemoteException {
    return DetailAksesDao.getAll();
  }

  @Override
  public boolean insert(DetailAkses detailAkses) throws RemoteException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean update(DetailAkses detailAkses) throws RemoteException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean deleteByIdDetailAkses(int idDetailAkses) throws RemoteException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
