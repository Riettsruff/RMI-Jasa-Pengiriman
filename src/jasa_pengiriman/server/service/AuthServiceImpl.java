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

/**
 * Mengimplementasi AuthService
 */
public class AuthServiceImpl extends UnicastRemoteObject implements AuthService, Serializable {

  public AuthServiceImpl() throws RemoteException {}
  
  /**
   * Mengembalikan hasil dari authentikasi login
   * @param email
   * @param password
   * @return Pengguna
   * @throws RemoteException 
   */
  @Override
  public Pengguna login(String email, String password) throws RemoteException {
    return AuthDao.login(email, password);
  }
  
}
