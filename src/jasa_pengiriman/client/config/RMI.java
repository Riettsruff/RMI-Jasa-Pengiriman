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

/**
 * Mengenerate informasi terkait RMI di sisi client
 */
public class RMI {
  private static final String HOST = "localhost";
  private static final int PORT = 3001;
  
  /**
   * Generate RMI HOST
   * @return String
   */
  public static String getHost() {
    return HOST;
  }
  
  /**
   * Generate RMI PORT
   * @return int
   */
  public static int getPort() {
    return PORT;
  }
  
  /**
   * Generate RMI Service
   * @param service
   * @return Object
   * @throws NotBoundException
   * @throws MalformedURLException
   * @throws RemoteException 
   */
  public static Object getService(String service) 
    throws NotBoundException, MalformedURLException, RemoteException {
    return Naming.lookup("rmi://" + HOST + ":" + PORT + "/" + service);
  }
}