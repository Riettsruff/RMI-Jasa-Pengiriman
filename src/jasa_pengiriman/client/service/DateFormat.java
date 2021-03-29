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
 * Untuk penanganan format Date
 */
public class DateFormat {
  
  /**
   * Untuk mengkonversi tipe Date menjadi String dan disesuaikan dengan format yang diperlukan
   * @param date
   * @param format
   * @return String
   */
  public static String dateToString(Date date, String format) {
    return new SimpleDateFormat(format).format(date);
  }
  
  /**
   * Untuk mengkonversi tipe String menjadi Date dan disesuaikan dengan format Datenya
   * @param date
   * @param format
   * @return Date
   */
  public static Date stringToDate(String date, String format) {
    try {
      return new SimpleDateFormat(format).parse(date);
    } catch (ParseException ex) {
      return null;
    }
  }
  
  /**
   * Untuk mengkonversi tipe Timestamp menjadi Date
   * @param timestamp
   * @return Date
   */
  public static Date timestampToDate(Timestamp timestamp) {
    return new Date(timestamp.getTime());
  }
}
