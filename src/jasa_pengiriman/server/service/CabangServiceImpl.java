/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.server.dao.CabangDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Riett
 */
public class CabangServiceImpl extends UnicastRemoteObject implements CabangService, Serializable {

  public CabangServiceImpl() throws RemoteException {}
  
  @Override
  public List<Cabang> getAll() throws RemoteException {
    return CabangDao.getAll();
  }
  
}
