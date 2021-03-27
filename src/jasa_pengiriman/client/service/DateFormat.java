/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Riett
 */
public class DateFormat {
  public static String dateToString(Date date, String format) {
    return new SimpleDateFormat(format).format(date);
  }
  
  public static Date stringToDate(String date, String format) {
    try {
      return new SimpleDateFormat(format).parse(date);
    } catch (ParseException ex) {
      return null;
    }
  }
  
  public static Date timestampToDate(Timestamp timestamp) {
    return new Date(timestamp.getTime());
  }
}
