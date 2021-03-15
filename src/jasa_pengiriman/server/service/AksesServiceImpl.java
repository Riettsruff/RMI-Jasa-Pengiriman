/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Akses;
import jasa_pengiriman.server.dao.AksesDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Riett
 */
public class AksesServiceImpl extends UnicastRemoteObject implements AksesService, Serializable {

  public AksesServiceImpl() throws RemoteException {}
  
  @Override
  public List<Akses> getAll() throws RemoteException {
    return AksesDao.getAll();
  }
  
}
