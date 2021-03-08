/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.server.helper;

import static jasa_pengiriman.server.service.Conn.Conn;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Riett
 */
public class DB {
  
  private static ResultSet executeQuery(PreparedStatement ps) throws SQLException {
    return ps.executeQuery();
  }
  
  private static boolean executeUpdate(PreparedStatement ps) throws SQLException {
    return ps.executeUpdate() > 0;
  }
  
  private static PreparedStatement prepStatement(String query, String[] psSet) throws SQLException {
    PreparedStatement ps = Conn().prepareStatement(query);
      
    if(psSet != null) {
      int idx = 0;
      for(String s : psSet) ps.setString(++idx, s);
    }
    
    return ps;
  }
  
  private static String selectBuilder(
    String table, 
    String[] fields, 
    String[] whereStatement, 
    String[] whereStatementSeparator
  ) {
    String query = "SELECT ";
    
    if(fields == null) query += "* FROM " + table;
    else query += String.join(", ", fields) + " FROM " + table;
    
    if(whereStatement != null) {
      query += " WHERE ";
      
      if(whereStatementSeparator == null) {
        query += whereStatement[0];
      } else {
        int totalStatement = whereStatement.length;
        int totalLogicOperator = whereStatementSeparator.length;
        
        for(int i = 0; i < totalStatement; ++i) {
          query += whereStatement[i];
          if(i < totalLogicOperator) query += " " + whereStatementSeparator[i] + " ";
        }
      }
    }
    
    return query;
  }
  
  private static String insertBuilder(
    String table,
    String[] fields,
    String[] values
  ) {
    String query = "INSERT INTO " + table;
    
    if(fields != null) {
      query += " (" + String.join(", ", fields) + ")";
    }
    
    query += " VALUES (" + String.join(", ", values) + ") ";
    
    return query;
  }
  
  private static String deleteBuilder(
    String table, 
    String[] whereStatement, 
    String[] whereStatementSeparator
  ) {
    String query = "DELETE FROM " + table;
    
    if(whereStatement != null) {
      query += " WHERE ";
      
      if(whereStatementSeparator == null) {
        query += whereStatement[0];
      } else {
        int totalStatement = whereStatement.length;
        int totalLogicOperator = whereStatementSeparator.length;
        
        for(int i = 0; i < totalStatement; ++i) {
          query += whereStatement[i];
          if(i < totalLogicOperator) query += " " + whereStatementSeparator[i] + " ";
        }
      }
    }
    
    return query;
  }
  
  private static String updateBuilder(
    String table,
    String[] values,
    String[] whereStatement,
    String[] whereStatementSeparator
  ) {
    String query = "UPDATE " + table + " SET (" + String.join(", ", values) + ")";
    
    if(whereStatement != null) {
      query += " WHERE ";
      
      if(whereStatementSeparator == null) {
        query += whereStatement[0];
      } else {
        int totalStatement = whereStatement.length;
        int totalLogicOperator = whereStatementSeparator.length;
        
        for(int i = 0; i < totalStatement; ++i) {
          query += whereStatement[i];
          if(i < totalLogicOperator) query += " " + whereStatementSeparator[i] + " ";
        }
      }
    }
    
    return query;
  }
  
  public static ResultSet get(String table) throws SQLException {
    String query = DB.selectBuilder(table, null, null, null);
    PreparedStatement ps = DB.prepStatement(query, null);
    
    return DB.executeQuery(ps);
  }
  
  public static ResultSet get(String table, String[] fields) throws SQLException {
    String query = DB.selectBuilder(table, fields, null, null);
    PreparedStatement ps = DB.prepStatement(query, null);
    
    return DB.executeQuery(ps);
  }
  
  public static ResultSet get(
    String table, 
    String[] fields, 
    String[] whereStatement, 
    String[] whereOperator, 
    String[] psSet
  ) throws SQLException {
    String query = DB.selectBuilder(table, fields, whereStatement, whereOperator);
    PreparedStatement ps = DB.prepStatement(query, psSet);
    
    return DB.executeQuery(ps);
  }
  
  public static boolean delete(String table) throws SQLException {
    String query = DB.deleteBuilder(table, null, null);
    PreparedStatement ps = DB.prepStatement(query, null);
    
    return DB.executeUpdate(ps);
  }
  
  public static boolean delete(
    String table, 
    String[] whereStatement, 
    String[] whereLogicOperator, 
    String[] psSet
  ) throws SQLException {
    String query = DB.deleteBuilder(table, whereStatement, whereLogicOperator);
    PreparedStatement ps = DB.prepStatement(query, psSet);
    
    return DB.executeUpdate(ps);
  }
  
  public static boolean insert(String table, String[] values, String[] psSet) throws SQLException {
    String query = DB.insertBuilder(table, null, values);
    PreparedStatement ps = DB.prepStatement(query, psSet);
    
    return DB.executeUpdate(ps);
  }
  
  public static boolean insert(
    String table, 
    String[] fields, 
    String[] values,
    String[] psSet
  ) throws SQLException {
    String query = DB.insertBuilder(table, fields, values);
    PreparedStatement ps = DB.prepStatement(query, psSet);
    
    return DB.executeUpdate(ps);
  }
  
  public static boolean update(String table, String[] values, String[] psSet) throws SQLException {
    String query = DB.updateBuilder(table, values, null, null);
    PreparedStatement ps = DB.prepStatement(query, psSet);
    
    return DB.executeUpdate(ps);
  }
  
  public static boolean update(
    String table, 
    String[] values, 
    String[] psSet,
    String[] whereStatement,
    String[] whereStatementSeparator
  ) throws SQLException {
    String query = DB.updateBuilder(table, values, whereStatement, whereStatementSeparator);
    PreparedStatement ps = DB.prepStatement(query, psSet);
    
    return DB.executeUpdate(ps);
  }
  
  public static ResultSet query(String query) throws SQLException {
    PreparedStatement ps = DB.prepStatement(query, null);
    
    return DB.executeQuery(ps);
  }
  
  public static ResultSet query(String query, String[] psSet) throws SQLException {
    PreparedStatement ps = DB.prepStatement(query, psSet);
    
    return DB.executeQuery(ps);
  }
}
