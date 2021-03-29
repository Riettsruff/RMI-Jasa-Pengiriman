/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * Untuk penanganan sebuah validasi sederhana
 */
public class BasicValidation {
  private static final String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
  private static final String NUMERIC_REGEX = "-?\\d+(\\.\\d+)?";
  
  /**
   * Untuk mengecek validitas suatu inputan
   * @param data berupa informasi yang diperlukan terkait inputan beserta formatnya
   * @return boolean
   */
  public static boolean isValid(LinkedHashMap<HashMap<String, Object>, List<String>> data) {
    for(Map.Entry<HashMap<String, Object>, List<String>> entry : data.entrySet()) {
      Map.Entry<String, Object> input = entry.getKey().entrySet().iterator().next();
      List<String> options = entry.getValue();
      
      String inputLabel = input.getKey();
      String inputValue = input.getValue() == null ? "" : input.getValue().toString();
      
      for(String o : options) {
        switch(o) {
          case "REQUIRED":
            if(inputValue.isEmpty()) {
              JOptionPane
                .showMessageDialog(null, inputLabel + " wajib diisi.", "Oops!", JOptionPane.ERROR_MESSAGE);
              return false;
            }
            break;
          case "EMAIL":
            if(!inputValue.matches(EMAIL_REGEX)) {
              JOptionPane
                .showMessageDialog(null, "Format " + inputLabel + " tidak sesuai.", "Oops!", JOptionPane.ERROR_MESSAGE);
              return false;
            }
            break;
          case "NUMERIC":
            if(!inputValue.matches(NUMERIC_REGEX)) {
              JOptionPane
                .showMessageDialog(null, inputLabel + " wajib berupa angka.", "Oops!", JOptionPane.ERROR_MESSAGE);
              return false;
            }
        }
      }
    }
    
    return true;
  }
}
