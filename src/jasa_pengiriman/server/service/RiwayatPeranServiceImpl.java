/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import jasa_pengiriman.model.RiwayatPeran;
import jasa_pengiriman.server.dao.RiwayatPeranDao;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 *
 * @author Riett
 */
public class RiwayatPeranServiceImpl extends UnicastRemoteObject implements RiwayatPeranService, Serializable {

  public RiwayatPeranServiceImpl() throws RemoteException {}
  
  @Override
  public List<RiwayatPeran> getByIdPengguna(int idPengguna) throws RemoteException {
    return RiwayatPeranDao.getByIdPengguna(idPengguna);
  }

  @Override
  public boolean insert(RiwayatPeran riwayatPeran) throws RemoteException {
    return RiwayatPeranDao.insert(riwayatPeran);
  }
  
}
