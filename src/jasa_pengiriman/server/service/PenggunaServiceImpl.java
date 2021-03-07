/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.server.dao.PenggunaDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riett
 */
public class PenggunaServiceImpl extends UnicastRemoteObject implements PenggunaService, Serializable {
  
  public PenggunaServiceImpl() throws RemoteException {
  }
  
  @Override
  public List<Pengguna> getAll() throws RemoteException {
    List<Pengguna> listPengguna = null;
    
    try {
      listPengguna = PenggunaDao.getAll();
    } catch (Exception e) {
      Logger.getLogger(PenggunaServiceImpl.class.getName()).log(Level.SEVERE, null, e);
    }
    
    return listPengguna;
  }
}
