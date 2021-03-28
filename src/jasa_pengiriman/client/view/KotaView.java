/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.view;

import jasa_pengiriman.JasaPengiriman;
import jasa_pengiriman.client.config.RMI;
import jasa_pengiriman.client.service.BasicValidation;
import jasa_pengiriman.client.service.Table;
import jasa_pengiriman.client.store.ActiveUser;
import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Kota;
import jasa_pengiriman.model.Provinsi;
import jasa_pengiriman.server.service.CabangService;
import jasa_pengiriman.server.service.KotaService;
import jasa_pengiriman.server.service.ProvinsiService;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class KotaView extends javax.swing.JFrame {
    private ProvinsiService provinsiService;
    private KotaService kotaService;
    private CabangService cabangService;
    /**
     * Creates new form KotaView
     */
    public KotaView() {
      if(ActiveUser.get() != null) {
        initRMIServices();
        initComponents();
        initInputData();
        initKotaTableData();
        initCabangTableData(-1);
      } else {
        try {
          JasaPengiriman.main(null);
        } catch (RemoteException ex) {
          Logger.getLogger(KotaView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    
    private void initRMIServices() {
      try {
        this.provinsiService = (ProvinsiService) RMI.getService("ProvinsiService");
        this.kotaService = (KotaService) RMI.getService("KotaService");
        this.cabangService = (CabangService) RMI.getService("CabangService");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Internal Server Error", "Oops!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }
    
    private void initInputData() {
      provinsiComboBox.removeAllItems();
      provinsiComboBox.addItem("- Pilih -");
      provinsiComboBox.setSelectedIndex(0);
      
      namaKotaTextField.setText("");
      
      try {
        List<Provinsi> provinsiList = provinsiService.getAll();
        
        for(Provinsi provinsi : provinsiList) provinsiComboBox.addItem(provinsi);
      } catch (RemoteException ex) {
        Logger.getLogger(KotaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initKotaTableData() {
      try {
        List<Kota> kotaList = kotaService.getAll();
        String[] fieldsData = {"No.", "Id Kota", "Nama Provinsi", "Nama Kota"};
        
        Object[][] rowsData = new Object[kotaList.size()][fieldsData.length];
        
        for(int i=0; i < kotaList.size(); ++i) {
          rowsData[i][0] = (i + 1) + ".";
          rowsData[i][1] = kotaList.get(i).getIdKota();
          rowsData[i][2] = kotaList.get(i).getProvinsi();
          rowsData[i][3] = kotaList.get(i).getNamaKota();
        }
        
        Table.setModel(kotaTable, rowsData, fieldsData, false);
        Table.setColumnWidths(kotaTable, 50);
        Table.setCellsHorizontalAlignment(kotaTable, new HashMap<Integer, Integer>(){{
          put(0, JLabel.CENTER);
        }});
        Table.removeColumns(kotaTable, 1);
      } catch (RemoteException ex) {
        Logger.getLogger(KotaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initCabangTableData(int idKota) {
      String[] fieldsData = {"No.", "Id Cabang", "Nama Cabang", "Alamat", "No.Hp"};
      Object[][] rowsData = null;
      
      if(idKota == -1) {
        Table.setModel(cabangTable, rowsData, fieldsData, false);
      } else {
        try {
          List<Cabang> cabangList = cabangService.getByIdKota(idKota);
          rowsData = new Object[cabangList.size()][fieldsData.length];
          
          for(int i=0; i < cabangList.size(); ++i) {
            rowsData[i][0] = (i + 1) + ".";
            rowsData[i][1] = cabangList.get(i).getIdCabang();
            rowsData[i][2] = cabangList.get(i).getNamaCabang();
            rowsData[i][3] = cabangList.get(i).getAlamat();
            rowsData[i][4] = cabangList.get(i).getNoHp();
          }
          
          Table.setModel(cabangTable, rowsData, fieldsData, false);
        } catch (RemoteException ex) {
          Logger.getLogger(KotaView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      Table.setCellsHorizontalAlignment(cabangTable, new HashMap<Integer, Integer>(){{
        put(0, JLabel.CENTER);
      }});
      Table.setColumnWidths(cabangTable, 50);
      Table.removeColumns(cabangTable, 1);
    }
    
    private Kota getInputDataKota() {
      Provinsi provinsi = 
        provinsiComboBox.getSelectedItem() instanceof String 
          ? null 
          : (Provinsi) provinsiComboBox.getSelectedItem();
      String namaKota = namaKotaTextField.getText();
      
      Kota kota = new Kota();
      kota.setProvinsi(provinsi);
      kota.setNamaKota(namaKota);
      
      return kota;
    }
    
    private boolean isDataKotaValid(Kota kota) {
      LinkedHashMap<HashMap<String, Object>, List<String>> data = 
        new LinkedHashMap<HashMap<String, Object>, List<String>>(){{
          put(
            new HashMap<String, Object>(){{ put("Provinsi", kota.getProvinsi()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Nama Kota", kota.getNamaKota()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
        }};
      
      return BasicValidation.isValid(data);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane2 = new javax.swing.JScrollPane();
    jTable2 = new javax.swing.JTable();
    menuUtamaButton = new javax.swing.JButton();
    exitButton = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    provinsiComboBox = new javax.swing.JComboBox<>();
    namaKotaTextField = new javax.swing.JTextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    cabangTable = new javax.swing.JTable();
    jLabel4 = new javax.swing.JLabel();
    simpanButton = new javax.swing.JButton();
    updateButton = new javax.swing.JButton();
    hapusButton = new javax.swing.JButton();
    jScrollPane3 = new javax.swing.JScrollPane();
    kotaTable = new javax.swing.JTable();
    refreshButton = new javax.swing.JButton();
    resetButton = new javax.swing.JButton();

    jTable2.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    jScrollPane2.setViewportView(jTable2);

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    menuUtamaButton.setText("Menu Utama");
    menuUtamaButton.setName("btnMenuUtama"); // NOI18N
    menuUtamaButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuUtamaButtonActionPerformed(evt);
      }
    });

    exitButton.setText("Keluar");
    exitButton.setName("btnKeluar"); // NOI18N
    exitButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exitButtonActionPerformed(evt);
      }
    });

    jLabel1.setFont(new java.awt.Font("Bauhaus 93", 0, 18)); // NOI18N
    jLabel1.setText("Kota");

    jLabel2.setText("Provinsi");

    jLabel3.setText("Nama Kota");

    provinsiComboBox.setName("cmbProvinsi"); // NOI18N

    namaKotaTextField.setText(" ");
    namaKotaTextField.setName("txtKota"); // NOI18N

    cabangTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    cabangTable.setEnabled(false);
    cabangTable.setName("tblCabang"); // NOI18N
    jScrollPane1.setViewportView(cabangTable);

    jLabel4.setText("Daftar Cabang");

    simpanButton.setText("Simpan");
    simpanButton.setName("btnSimpan"); // NOI18N
    simpanButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        simpanButtonActionPerformed(evt);
      }
    });

    updateButton.setText("Update");
    updateButton.setName("btnUpdate"); // NOI18N
    updateButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        updateButtonActionPerformed(evt);
      }
    });

    hapusButton.setText("Hapus");
    hapusButton.setName("btnHapus"); // NOI18N
    hapusButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        hapusButtonActionPerformed(evt);
      }
    });

    kotaTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null},
        {null, null, null, null}
      },
      new String [] {
        "Title 1", "Title 2", "Title 3", "Title 4"
      }
    ));
    kotaTable.setName("tblKota"); // NOI18N
    kotaTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        kotaTableMouseClicked(evt);
      }
    });
    jScrollPane3.setViewportView(kotaTable);

    refreshButton.setText("Refresh");
    refreshButton.setName("btnRefresh"); // NOI18N
    refreshButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        refreshButtonActionPerformed(evt);
      }
    });

    resetButton.setText("Reset");
    resetButton.setName("btnHapus"); // NOI18N
    resetButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        resetButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(refreshButton))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
              .addGroup(layout.createSequentialGroup()
                .addComponent(simpanButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hapusButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resetButton))
              .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabel3)
                  .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(namaKotaTextField)
                  .addComponent(provinsiComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
              .addComponent(menuUtamaButton))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel4)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(layout.createSequentialGroup()
                  .addComponent(jLabel1)
                  .addGap(355, 355, 355)
                  .addComponent(exitButton))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 511, javax.swing.GroupLayout.PREFERRED_SIZE)))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(menuUtamaButton)
          .addComponent(exitButton)
          .addComponent(jLabel1))
        .addGap(36, 36, 36)
        .addComponent(jLabel4)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(5, 5, 5)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel2)
              .addComponent(provinsiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel3)
              .addComponent(namaKotaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(simpanButton)
              .addComponent(updateButton)
              .addComponent(hapusButton)
              .addComponent(resetButton)))
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(31, 31, 31)
        .addComponent(refreshButton)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void menuUtamaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUtamaButtonActionPerformed
    new MenuUtamaView().setVisible(true);
    dispose();
  }//GEN-LAST:event_menuUtamaButtonActionPerformed

  private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
    ActiveUser.remove();
    new LoginView().setVisible(true);
    dispose();
  }//GEN-LAST:event_exitButtonActionPerformed

  private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
    initKotaTableData();
    initCabangTableData(-1);
  }//GEN-LAST:event_refreshButtonActionPerformed

  private void kotaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kotaTableMouseClicked
    int selectedRow = kotaTable.getSelectedRow();
    
    provinsiComboBox.getModel().setSelectedItem(Table.getValue(kotaTable, selectedRow, 2));
    namaKotaTextField.setText(Table.getValue(kotaTable, selectedRow, 3).toString());
    
    initCabangTableData((int) Table.getValue(kotaTable, selectedRow, 1));
  }//GEN-LAST:event_kotaTableMouseClicked

  private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
    Kota kota = getInputDataKota();
    
    if(isDataKotaValid(kota)) {
      try {
        if(kotaService.insert(kota)) {
          JOptionPane
            .showMessageDialog(this, "Kota berhasil ditambahkan.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initKotaTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Kota gagal ditambahkan.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(KotaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_simpanButtonActionPerformed

  private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
    Kota kota = getInputDataKota();
    int selectedRow = kotaTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah kota terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      kota.setIdKota((int) Table.getValue(kotaTable, selectedRow, 1));
      
      if(isDataKotaValid(kota)) {
        try {
          if(kotaService.update(kota)) {
            JOptionPane
              .showMessageDialog(this, "Kota berhasil diupdate.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
            initInputData();
            initKotaTableData();
          } else {
            JOptionPane
              .showMessageDialog(this, "Kota gagal diupdate.", "Oops!", JOptionPane.ERROR_MESSAGE);
          }
        } catch (RemoteException ex) {
          Logger.getLogger(KotaView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_updateButtonActionPerformed

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    initInputData();
    
    kotaTable.getSelectionModel().clearSelection();
    Table.deleteAllRows(cabangTable);
  }//GEN-LAST:event_resetButtonActionPerformed

  private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
    int selectedRow = kotaTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah kota terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      int idKota = (int) Table.getValue(kotaTable, selectedRow, 1);
      
      try {
        if(kotaService.deleteByIdKota(idKota)) {
          JOptionPane
            .showMessageDialog(this, "Kota berhasil dihapus.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initKotaTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Kota gagal dihapus.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(KotaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_hapusButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(KotaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KotaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KotaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KotaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KotaView().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTable cabangTable;
  private javax.swing.JButton exitButton;
  private javax.swing.JButton hapusButton;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JTable jTable2;
  private javax.swing.JTable kotaTable;
  private javax.swing.JButton menuUtamaButton;
  private javax.swing.JTextField namaKotaTextField;
  private javax.swing.JComboBox<Object> provinsiComboBox;
  private javax.swing.JButton refreshButton;
  private javax.swing.JButton resetButton;
  private javax.swing.JButton simpanButton;
  private javax.swing.JButton updateButton;
  // End of variables declaration//GEN-END:variables
}
