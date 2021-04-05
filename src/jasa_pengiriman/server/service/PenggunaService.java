/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Pengguna;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;

public interface PenggunaService extends Remote {
  public List<Pengguna> getAll() throws RemoteException;
  public List<Pengguna> getByIdCabang(int idCabang) throws RemoteException;
  public List<Pengguna> getByIdPeran(int idPeran) throws RemoteException;
  public Pengguna getByIdPengguna(int idPengguna) throws RemoteException;
  public int getMaxIdPengguna() throws RemoteException;
  public boolean insert(Pengguna pengguna) throws RemoteException;
  public boolean update(Pengguna pengguna) throws RemoteException;
  public boolean deleteByIdPengguna(int idPengguna) throws RemoteException;
  public boolean updateTerakhirLoginByIdPengguna(Timestamp terakhirLogin, int idPengguna) throws RemoteException;
}