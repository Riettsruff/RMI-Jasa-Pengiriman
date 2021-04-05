/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.config;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMI {
  private static final String HOST = "localhost";
  private static final int PORT = 3001;
  
  public static String getHost() {
    return HOST;
  }

  public static int getPort() {
    return PORT;
  }
  
  public static Object getService(String service) 
    throws NotBoundException, MalformedURLException, RemoteException {
    return Naming.lookup("rmi://" + HOST + ":" + PORT + "/" + service);
  }
}