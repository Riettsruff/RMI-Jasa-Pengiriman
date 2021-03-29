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
 * Sebagai DB Helper
 */
public class DB {
  
  /**
   * Mengembalikan hasil dari executeQueyr
   * @param ps
   * @return ResultSet
   * @throws SQLException 
   */
  private static ResultSet executeQuery(PreparedStatement ps) throws SQLException {
    return ps.executeQuery();
  }
  
  /**
   * Mengembalikan hasil dari executeUpdate
   * @param ps
   * @return boolean
   * @throws SQLException 
   */
  private static boolean executeUpdate(PreparedStatement ps) throws SQLException {
    return ps.executeUpdate() > 0;
  }
  
  /**
   * Menyiapkan suatu statement berdasarkan query yang dibentuk dan value yang di set
   * @param query
   * @param psSet
   * @return PreparedStatement
   * @throws SQLException 
   */
  private static PreparedStatement prepStatement(String query, String[] psSet) throws SQLException {
    PreparedStatement ps = Conn().prepareStatement(query);
      
    if(psSet != null) {
      int idx = 0;
      for(String s : psSet) ps.setString(++idx, s);
    }
    
    return ps;
  }
  
  /**
   * Sebagai pembentuk query select
   * @param table
   * @param fields
   * @param whereStatement
   * @param whereStatementSeparator
   * @return String
   */
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
  
  /**
   * Sebagai pembentuk query insert
   * @param table
   * @param fields
   * @param values
   * @return String
   */
  private static String insertBuilder(
    String table,
    String[] fields,
    String[] values
  ) {
    String query = "INSERT INTO " + table;
    
    if(fields != null) {
      query += " (" + String.join(", ", fields) + ")";
    }
    
    query += " VALUES (";
    
    for(int i=0; i < values.length; ++i) {
      query += "?";
      if(i < values.length - 1) query += ", ";
    }
    
    query += ") ";
    
    return query;
  }
  
  /**
   * Sebagai pembentuk query delete
   * @param table
   * @param whereStatement
   * @param whereStatementSeparator
   * @return String
   */
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
  
  /**
   * Sebagai pembentuk query update
   * @param table
   * @param fields
   * @param whereStatement
   * @param whereStatementSeparator
   * @return String
   */
  private static String updateBuilder(
    String table,
    String[] fields,
    String[] whereStatement,
    String[] whereStatementSeparator
  ) {
    String query = "UPDATE " + table + " SET ";
            
    for(int i=0; i < fields.length; ++i) {
      query += fields[i] + " = ?";
      if(i < fields.length - 1) query += ", ";
    }
    
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
  
  /**
   * Sebagai layanan public untuk generate seluruh data berdasarkan tabel tertentu
   * @param table
   * @return ResultSet
   * @throws SQLException 
   */
  public static ResultSet get(String table) throws SQLException {
    String query = selectBuilder(table, null, null, null);
    PreparedStatement ps = prepStatement(query, null);
    
    return executeQuery(ps);
  }
  
  /**
   * Sebagai layanan public untuk generate data berdasarkan fields tertentu
   * @param table
   * @param fields
   * @return ResultSet
   * @throws SQLException 
   */
  public static ResultSet get(String table, String[] fields) throws SQLException {
    String query = selectBuilder(table, fields, null, null);
    PreparedStatement ps = prepStatement(query, null);
    
    return executeQuery(ps);
  }
  
  /**
   * Sebagai layanan public untuk generate data berdasarkan fields tertentu maupun kondisi tertentu
   * @param table
   * @param fields
   * @param whereStatement
   * @param whereStatementSeparator
   * @param values
   * @return ResultSet
   * @throws SQLException 
   */
  public static ResultSet get(
    String table, 
    String[] fields, 
    String[] whereStatement, 
    String[] whereStatementSeparator, 
    String[] values
  ) throws SQLException {
    String query = selectBuilder(table, fields, whereStatement, whereStatementSeparator);
    PreparedStatement ps = prepStatement(query, values);
    
    return executeQuery(ps);
  }
  
  /**
   * Sebagai layanan public untuk delete seluruh data berdasarkan tabel tertentu
   * @param table
   * @return
   * @throws SQLException 
   */
  public static boolean delete(String table) throws SQLException {
    String query = deleteBuilder(table, null, null);
    PreparedStatement ps = prepStatement(query, null);
    
    return executeUpdate(ps);
  }
  
  /**
   * Sebagai layanan public untuk delete data berdasarkan kondisi tertentu
   * @param table
   * @param whereStatement
   * @param whereStatementSeparator
   * @param values
   * @return boolean
   * @throws SQLException 
   */
  public static boolean delete(
    String table, 
    String[] whereStatement, 
    String[] whereStatementSeparator, 
    String[] values
  ) throws SQLException {
    String query = deleteBuilder(table, whereStatement, whereStatementSeparator);
    PreparedStatement ps = prepStatement(query, values);
    
    return executeUpdate(ps);
  }
  
  /**
   * Sebagai layanan public untuk insert data ke seluruh fields sesuai urutannya pada tabel
   * @param table
   * @param values
   * @return boolean
   * @throws SQLException 
   */
  public static boolean insert(String table, String[] values) throws SQLException {
    String query = insertBuilder(table, null, values);
    PreparedStatement ps = prepStatement(query, values);
    
    return executeUpdate(ps);
  }
  
  /**
   * Sebagai layanan public untuk insert data ke fields tertentu pada suatu table
   * @param table
   * @param fields
   * @param values
   * @return boolean
   * @throws SQLException 
   */
  public static boolean insert(
    String table, 
    String[] fields, 
    String[] values
  ) throws SQLException {
    String query = insertBuilder(table, fields, values);
    PreparedStatement ps = prepStatement(query, values);
    
    return executeUpdate(ps);
  }
  
  /**
   * Sebagai layanan public untuk update data berdasarkan fields tertentu
   * @param table
   * @param fields
   * @param values
   * @return boolean
   * @throws SQLException 
   */
  public static boolean update(String table, String[] fields, String[] values) throws SQLException {
    String query = updateBuilder(table, fields, null, null);
    PreparedStatement ps = prepStatement(query, values);
    
    return executeUpdate(ps);
  }
  
  /**
   * Sebagai layanan public untuk update data berdasarkan fields tertentu maupun kondisi tertentu
   * @param table
   * @param fields
   * @param values
   * @param whereStatement
   * @param whereStatementSeparator
   * @return boolean
   * @throws SQLException 
   */
  public static boolean update(
    String table, 
    String[] fields, 
    String[] values,
    String[] whereStatement,
    String[] whereStatementSeparator
  ) throws SQLException {
    String query = updateBuilder(table, fields, whereStatement, whereStatementSeparator);
    PreparedStatement ps = prepStatement(query, values);
    
    return executeUpdate(ps);
  }
  
  /**
   * Sebagai layanan public untuk generate data berdasarkan query tertentu
   * @param query
   * @return ResultSet
   * @throws SQLException 
   */
  public static ResultSet query(String query) throws SQLException {
    PreparedStatement ps = prepStatement(query, null);
    
    return executeQuery(ps);
  }
  
  /**
   * Sebagai layanan public untuk generate data berdasarkan query tertentu dan menyesuaikan nilai yang ditetapkan
   * @param query
   * @param values
   * @return ResultSet
   * @throws SQLException 
   */
  public static ResultSet query(String query, String[] values) throws SQLException {
    PreparedStatement ps = prepStatement(query, values);
    
    return executeQuery(ps);
  }
}
