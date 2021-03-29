/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.service;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Sebagai Koneksi untuk terhubung ke database
 */
public class Conn {
  private static Connection conn;
  
  /**
   * Constructor statis untuk suatu koneksi database
   * @return Connection
   */
  public static Connection Conn() {
    if(conn == null) {
      try {
        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("db_jasa_pengiriman");
        dataSource.setUser("root");
        dataSource.setPassword("");

        conn = (Connection) dataSource.getConnection();
      } catch(SQLException se){
        se.printStackTrace();
      } catch(Exception e){
        e.printStackTrace();
      }
    }

    return conn;
  }
}