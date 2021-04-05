/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.server.dao.AuthDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AuthServiceImpl extends UnicastRemoteObject implements AuthService, Serializable {

  public AuthServiceImpl() throws RemoteException {}
  
  @Override
  public Pengguna login(String email, String password) throws RemoteException {
    return AuthDao.login(email, password);
  }
  
}
