/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman;

import jasa_pengiriman.client.view.LoginView;
import jasa_pengiriman.client.view.TesterView;
import jasa_pengiriman.server.service.AksesServiceImpl;
import jasa_pengiriman.server.service.AuthServiceImpl;
import jasa_pengiriman.server.service.CabangServiceImpl;
import jasa_pengiriman.server.service.KotaServiceImpl;
import jasa_pengiriman.server.service.PenggunaServiceImpl;
import jasa_pengiriman.server.service.PeranServiceImpl;
import jasa_pengiriman.server.service.ProvinsiServiceImpl;
import jasa_pengiriman.server.service.RiwayatPeranServiceImpl;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Riett
 */
public class JasaPengiriman {

  public static void main(String[] args) throws RemoteException {
    Registry registry = LocateRegistry.createRegistry(3001);
    
    registry.rebind("AuthService", new AuthServiceImpl());
    registry.rebind("AksesService", new AksesServiceImpl());
    registry.rebind("CabangService", new CabangServiceImpl());
    registry.rebind("KotaService", new KotaServiceImpl());
    registry.rebind("PenggunaService", new PenggunaServiceImpl());
    registry.rebind("PeranService", new PeranServiceImpl());
    registry.rebind("ProvinsiService", new ProvinsiServiceImpl());
    registry.rebind("RiwayatPeranService", new RiwayatPeranServiceImpl());
    
    System.out.println("App server started...");
    
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new LoginView().setVisible(true);
      }
    });
  }
  
}
