/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.Kota;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author Riett
 */
public interface KotaService extends Remote {
  public List<Kota> getAll() throws RemoteException;
  public List<Kota> getByIdProvinsi(int idProvinsi) throws RemoteException;
}
