/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Pengguna;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Riett
 */
public interface AuthService extends Remote {
  public Pengguna login(String email, String password) throws RemoteException;
  public boolean logout() throws RemoteException;
}
