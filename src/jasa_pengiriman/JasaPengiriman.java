/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman;

import jasa_pengiriman.client.config.RMI;
import jasa_pengiriman.client.view.LoginView;
import jasa_pengiriman.server.service.AksesServiceImpl;
import jasa_pengiriman.server.service.AuthServiceImpl;
import jasa_pengiriman.server.service.BiayaServiceImpl;
import jasa_pengiriman.server.service.CabangServiceImpl;
import jasa_pengiriman.server.service.DetailAksesServiceImpl;
import jasa_pengiriman.server.service.KotaServiceImpl;
import jasa_pengiriman.server.service.PelacakanServiceImpl;
import jasa_pengiriman.server.service.PenggunaServiceImpl;
import jasa_pengiriman.server.service.PengirimanServiceImpl;
import jasa_pengiriman.server.service.PeranServiceImpl;
import jasa_pengiriman.server.service.ProvinsiServiceImpl;
import jasa_pengiriman.server.service.RiwayatPeranServiceImpl;
import jasa_pengiriman.server.service.StatusPelacakanServiceImpl;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Riett
 */
public class JasaPengiriman {

  /**
   * Method yang akan dieksekusi pertama kali saat program dijalankan
   * @param args
   * @throws RemoteException 
   */
  public static void main(String[] args) throws RemoteException {
    Registry registry = LocateRegistry.createRegistry(RMI.getPort());
    
    registry.rebind("AuthService", new AuthServiceImpl());
    registry.rebind("AksesService", new AksesServiceImpl());
    registry.rebind("CabangService", new CabangServiceImpl());
    registry.rebind("KotaService", new KotaServiceImpl());
    registry.rebind("PenggunaService", new PenggunaServiceImpl());
    registry.rebind("PeranService", new PeranServiceImpl());
    registry.rebind("DetailAksesService", new DetailAksesServiceImpl());
    registry.rebind("ProvinsiService", new ProvinsiServiceImpl());
    registry.rebind("RiwayatPeranService", new RiwayatPeranServiceImpl());
    registry.rebind("StatusPelacakanService", new StatusPelacakanServiceImpl());
    registry.rebind("BiayaService", new BiayaServiceImpl());
    registry.rebind("PengirimanService", new PengirimanServiceImpl());
    registry.rebind("PelacakanService", new PelacakanServiceImpl());
    
    System.out.println("App server started...");
    
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new LoginView().setVisible(true);
      }
    });
  }
  
}
