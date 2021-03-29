/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.StatusPelacakan;
import jasa_pengiriman.server.dao.StatusPelacakanDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Mengimplementasi StatusPelacakanService
 */
public class StatusPelacakanServiceImpl extends UnicastRemoteObject implements StatusPelacakanService, Serializable {

  public StatusPelacakanServiceImpl() throws RemoteException {}
  
  /**
   * Mengembalikan hasil dari generate seluruh StatusPelacakan
   * @return
   * @throws RemoteException 
   */
  @Override
  public List<StatusPelacakan> getAll() throws RemoteException {
    return StatusPelacakanDao.getAll();
  }
  
}
