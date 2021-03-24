/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Provinsi;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Riett
 */
public interface ProvinsiService extends Remote {
  public List<Provinsi> getAll() throws RemoteException;
  public boolean insert(Provinsi provinsi) throws RemoteException;
  public boolean update(Provinsi provinsi) throws RemoteException;
  public boolean deleteByIdProvinsi(int idProvinsi) throws RemoteException;
}
