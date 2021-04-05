/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.service;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormat {
  
  public static String getString(long currency, String languageCode, String countryCode) {
    return NumberFormat.getCurrencyInstance(new Locale(languageCode, countryCode)).format(currency);
  }
}
