/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman;

import jasa_pengiriman.client.view.TesterView;
import jasa_pengiriman.server.service.AuthServiceImpl;
import jasa_pengiriman.server.service.PenggunaServiceImpl;
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
    registry.rebind("PenggunaService", new PenggunaServiceImpl());
    
    System.out.println("App server started...");
    
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          new TesterView().setVisible(true);
        } catch (NotBoundException ex) {
          Logger.getLogger(JasaPengiriman.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
          Logger.getLogger(JasaPengiriman.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
          Logger.getLogger(JasaPengiriman.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    });
  }
  
}
