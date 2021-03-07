/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman;

import jasa_pengiriman.client.view.TesterView;
import jasa_pengiriman.server.service.PenggunaServiceImpl;
import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Riett
 */
public class JasaPengiriman {

  public static void main(String[] args) throws RemoteException {
    Registry registry = LocateRegistry.createRegistry(3001);
    
    registry.rebind("PenggunaService", new PenggunaServiceImpl());
    
    System.out.println("App server started...");
    
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        new TesterView().setVisible(true);
      }
    });
  }
  
}
