/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.view;

import jasa_pengiriman.client.config.RMI;
import jasa_pengiriman.client.service.BasicValidation;
import jasa_pengiriman.client.service.DateFormat;
import jasa_pengiriman.client.service.Table;
import jasa_pengiriman.client.store.ActiveUser;
import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Pelacakan;
import jasa_pengiriman.model.Pengiriman;
import jasa_pengiriman.model.StatusPelacakan;
import jasa_pengiriman.server.service.PelacakanService;
import jasa_pengiriman.server.service.PengirimanService;
import jasa_pengiriman.server.service.StatusPelacakanService;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
public class PelacakanView extends javax.swing.JFrame {
    StatusPelacakanService statusPelacakanService;
    PelacakanService pelacakanService;
    PengirimanService pengirimanService;
    /**
     * Creates new form PelacakanView
     */
    public PelacakanView() {
      initRMIServices();
      initComponents();
      initInputData();
      initPelacakanTableData("-");
    }
    
    private void initRMIServices() {
      try {
        this.statusPelacakanService = (StatusPelacakanService) RMI.getService("StatusPelacakanService");
        this.pelacakanService = (PelacakanService) RMI.getService("PelacakanService");
        this.pengirimanService = (PengirimanService) RMI.getService("PengirimanService");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Internal Server Error", "Oops!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }
    
    private void initInputData() {
      noResiTextField.setText("");
      
      statusComboBox.removeAllItems();
      statusComboBox.addItem("- Pilih -");
      statusComboBox.setSelectedIndex(0);
      
      keteranganTextArea.setText("");
      
      try {
        List<StatusPelacakan> statusPelacakanList = statusPelacakanService.getAll();
        
        for(StatusPelacakan statusPelacakan : statusPelacakanList) statusComboBox.addItem(statusPelacakan);
      } catch (RemoteException ex) {
        Logger.getLogger(PelacakanView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initPelacakanTableData(String noResi) {
      String[] fieldsData = {"No.", "Id Pelacakan", "No. Resi", "Cabang Pelapor", "Waktu Lapor", "Status", "Keterangan"};
      Object[][] rowsData = null;
      
      try {
        List<Pelacakan> pelacakanList = 
          noResi == "-" ? pelacakanService.getAll() : pelacakanService.getByNoResi(noResi);
        
//        if(noResi != "-") noResiLabel.setText(noResi);
        
        rowsData = new Object[pelacakanList.size()][fieldsData.length];
          
        for(int i=0; i < pelacakanList.size(); ++i) {
          rowsData[i][0] = (i + 1) + ".";
          rowsData[i][1] = pelacakanList.get(i).getIdPelacakan();
          rowsData[i][2] = pelacakanList.get(i).getPengiriman().getNoResi();

          Object cabangPelapor = pelacakanList.get(i).getCabang();

          rowsData[i][3] = cabangPelapor.toString() != null ? cabangPelapor : "Tidak diketahui";
          rowsData[i][4] = 
            DateFormat.dateToString(
              DateFormat.timestampToDate(pelacakanList.get(i).getWaktuLapor()), 
              "dd-MM-yyyy HH:mm:ss"
            );
          rowsData[i][5] = pelacakanList.get(i).getStatusPelacakan();
          rowsData[i][6] = pelacakanList.get(i).getKeterangan();
        }
        
        Table.setModel(pelacakanTable, rowsData, fieldsData, true);
      } catch (RemoteException ex) {
        Logger.getLogger(PelacakanView.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      Table.setCellsHorizontalAlignment(pelacakanTable, new HashMap<Integer, Integer>(){{
        put(0, JLabel.CENTER);
      }});
      Table.setColumnWidths(pelacakanTable, 50);
      Table.removeColumns(pelacakanTable, 1);
    }
    
    private Pelacakan getInputDataPelacakan() {
      String noResi = noResiTextField.getText();
      StatusPelacakan statusPelacakan = 
        statusComboBox.getSelectedItem() instanceof String
          ? null
          : (StatusPelacakan) statusComboBox.getSelectedItem();
      String keterangan = keteranganTextArea.getText();
      Cabang cabangPelapor = ActiveUser.get().getCabang();
      Timestamp waktuLapor = DateFormat.dateToTimestamp(new Date());
      
      Pengiriman pengiriman = new Pengiriman();
      Pelacakan pelacakan = new Pelacakan();
      
      pengiriman.setNoResi(noResi);
      
      pelacakan.setPengiriman(pengiriman);
      pelacakan.setCabang(cabangPelapor);
      pelacakan.setStatusPelacakan(statusPelacakan);
      pelacakan.setWaktuLapor(waktuLapor);
      pelacakan.setKeterangan(keterangan);
      
      return pelacakan;
    }
    
    private boolean isDataPelacakanValid(Pelacakan pelacakan) {
      LinkedHashMap<HashMap<String, Object>, List<String>> data = 
        new LinkedHashMap<HashMap<String, Object>, List<String>>(){{
          put(
            new HashMap<String, Object>(){{ put("No.Resi", pelacakan.getPengiriman().getNoResi()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Status", pelacakan.getStatusPelacakan()); }},
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

    menuUtamaButton = new javax.swing.JButton();
    keluarButton = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    statusComboBox = new javax.swing.JComboBox<>();
    jLabel5 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    keteranganTextArea = new javax.swing.JTextArea();
    noResiTextField = new javax.swing.JTextField();
    lacakButton = new javax.swing.JButton();
    simpanButton = new javax.swing.JButton();
    updateButton = new javax.swing.JButton();
    hapusButton = new javax.swing.JButton();
    refreshButton = new javax.swing.JButton();
    jScrollPane2 = new javax.swing.JScrollPane();
    pelacakanTable = new javax.swing.JTable();
    resetButton = new javax.swing.JButton();
    noResiLabel = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    menuUtamaButton.setText("Menu Utama");
    menuUtamaButton.setName("btnMenuUtama"); // NOI18N
    menuUtamaButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuUtamaButtonActionPerformed(evt);
      }
    });

    keluarButton.setText("Keluar");
    keluarButton.setName("btnKeluar"); // NOI18N
    keluarButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        keluarButtonActionPerformed(evt);
      }
    });

    jLabel1.setFont(new java.awt.Font("Bauhaus 93", 0, 18)); // NOI18N
    jLabel1.setText("Pelacakan");

    jLabel2.setText("No. Resi");

    jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
    jLabel3.setText("Pelaporan");

    jLabel4.setText("Status");

    statusComboBox.setName("cmbStatus"); // NOI18N

    jLabel5.setText("Keterangan");

    keteranganTextArea.setColumns(20);
    keteranganTextArea.setRows(5);
    keteranganTextArea.setName("txtKeterangan"); // NOI18N
    jScrollPane1.setViewportView(keteranganTextArea);

    noResiTextField.setText(" ");
    noResiTextField.setName("txtNoResi"); // NOI18N

    lacakButton.setText("Lacak");
    lacakButton.setName("btnLacak"); // NOI18N
    lacakButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        lacakButtonActionPerformed(evt);
      }
    });

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

    refreshButton.setText("Refresh");
    refreshButton.setName("btnRefresh"); // NOI18N
    refreshButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        refreshButtonActionPerformed(evt);
      }
    });

    pelacakanTable.setModel(new javax.swing.table.DefaultTableModel(
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
    pelacakanTable.setName("tblPelacakan"); // NOI18N
    pelacakanTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        pelacakanTableMouseClicked(evt);
      }
    });
    jScrollPane2.setViewportView(pelacakanTable);

    resetButton.setText("Reset");
    resetButton.setName("btnHapus"); // NOI18N
    resetButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        resetButtonActionPerformed(evt);
      }
    });

    noResiLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(menuUtamaButton)
            .addGap(107, 107, 107)
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 292, Short.MAX_VALUE)
            .addComponent(keluarButton))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                    .addGap(61, 61, 61)
                    .addComponent(noResiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lacakButton))
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                  .addComponent(jLabel4)
                  .addGap(30, 30, 30)
                  .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                  .addComponent(jLabel5)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(jScrollPane1)))
              .addComponent(jLabel3)
              .addComponent(noResiLabel))
            .addGap(0, 0, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addGap(60, 60, 60)
            .addComponent(simpanButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(updateButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(hapusButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(resetButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(refreshButton)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(menuUtamaButton)
          .addComponent(keluarButton)
          .addComponent(jLabel1))
        .addGap(34, 34, 34)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel2)
          .addComponent(noResiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(lacakButton))
        .addGap(18, 18, 18)
        .addComponent(jLabel3)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel4)
          .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(14, 14, 14)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jLabel5)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(resetButton)
            .addComponent(refreshButton))
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(simpanButton)
            .addComponent(updateButton)
            .addComponent(hapusButton)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(noResiLabel)
        .addGap(18, 18, 18)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void menuUtamaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUtamaButtonActionPerformed
    new MenuUtamaView().setVisible(true);
    dispose();
  }//GEN-LAST:event_menuUtamaButtonActionPerformed

  private void keluarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarButtonActionPerformed
    ActiveUser.remove();
    new LoginView().setVisible(true);
    dispose();
  }//GEN-LAST:event_keluarButtonActionPerformed

  private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
    initPelacakanTableData("-");
  }//GEN-LAST:event_refreshButtonActionPerformed

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    initInputData();
    
    pelacakanTable.getSelectionModel().clearSelection();
    initPelacakanTableData("-");
  }//GEN-LAST:event_resetButtonActionPerformed

  private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
    Pelacakan pelacakan = getInputDataPelacakan();
    
    if(isDataPelacakanValid(pelacakan)) {
      try {
        Pengiriman pengiriman = pengirimanService.getByNoResi(pelacakan.getPengiriman().getNoResi());
        
        if(pengiriman == null) {
          JOptionPane
            .showMessageDialog(this, "No. Resi " + pelacakan.getPengiriman().getNoResi() + " tidak ditemukan", "Oops!", JOptionPane.ERROR_MESSAGE);
        } else {
          try {
            if(pelacakanService.insert(pelacakan)) {
              JOptionPane
                .showMessageDialog(this, "Laporan berhasil ditambahkan.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
              initInputData();
              initPelacakanTableData("-");
            } else {
              JOptionPane
                .showMessageDialog(this, "Laporan Gagal ditambahkan.", "Oops!", JOptionPane.ERROR_MESSAGE);
            }
          } catch (RemoteException ex) {
            Logger.getLogger(PelacakanView.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      } catch (RemoteException ex) {
        Logger.getLogger(PelacakanView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_simpanButtonActionPerformed

  private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
    Pelacakan pelacakan = getInputDataPelacakan();
    int selectedRow = pelacakanTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah laporan terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      pelacakan.setIdPelacakan((int) Table.getValue(pelacakanTable, selectedRow, 1));
      
      if(isDataPelacakanValid(pelacakan)) {
        try {
          if(pelacakanService.update(pelacakan)) {
            JOptionPane
              .showMessageDialog(this, "Laporan berhasil diupdate.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
            initInputData();
            initPelacakanTableData("-");
          } else {
            JOptionPane
              .showMessageDialog(this, "Laporan gagal diupdate.", "Oops!", JOptionPane.ERROR_MESSAGE);
          }
        } catch (RemoteException ex) {
          Logger.getLogger(PelacakanView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_updateButtonActionPerformed

  private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
    int selectedRow = pelacakanTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah laporan terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      int idPelacakan = (int) Table.getValue(pelacakanTable, selectedRow, 1);
      
      try {
        if(pelacakanService.deleteByIdPelacakan(idPelacakan)) {
          JOptionPane
            .showMessageDialog(this, "Laporan berhasil dihapus.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initPelacakanTableData("-");
        } else {
          JOptionPane
            .showMessageDialog(this, "Laporan gagal dihapus.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(PelacakanView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_hapusButtonActionPerformed

  private void lacakButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lacakButtonActionPerformed
    String noResi = noResiTextField.getText();
    
    if(noResi.isEmpty()) {
      JOptionPane
        .showMessageDialog(this, "No. Resi wajib diisi", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      try {
        Pengiriman pengiriman = pengirimanService.getByNoResi(noResi);
        
        if(pengiriman == null) {
          JOptionPane
            .showMessageDialog(this, "No. Resi " + noResi + " tidak ditemukan", "Oops!", JOptionPane.ERROR_MESSAGE);
        } else {
          initPelacakanTableData(noResi);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(PelacakanView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_lacakButtonActionPerformed

  private void pelacakanTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pelacakanTableMouseClicked
    int selectedRow = pelacakanTable.getSelectedRow();
    
    noResiTextField.setText(Table.getValue(pelacakanTable, selectedRow, 2).toString());
    
    Object statusObj = Table.getValue(pelacakanTable, selectedRow, 5);
    
    if(statusObj instanceof String) statusComboBox.setSelectedIndex(0);
    else statusComboBox.getModel().setSelectedItem(statusObj);
    
    keteranganTextArea.setText(Table.getValue(pelacakanTable, selectedRow, 6).toString());
  }//GEN-LAST:event_pelacakanTableMouseClicked

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
            java.util.logging.Logger.getLogger(PelacakanView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PelacakanView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PelacakanView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PelacakanView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PelacakanView().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton hapusButton;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JButton keluarButton;
  private javax.swing.JTextArea keteranganTextArea;
  private javax.swing.JButton lacakButton;
  private javax.swing.JButton menuUtamaButton;
  private javax.swing.JLabel noResiLabel;
  private javax.swing.JTextField noResiTextField;
  private javax.swing.JTable pelacakanTable;
  private javax.swing.JButton refreshButton;
  private javax.swing.JButton resetButton;
  private javax.swing.JButton simpanButton;
  private javax.swing.JComboBox<Object> statusComboBox;
  private javax.swing.JButton updateButton;
  // End of variables declaration//GEN-END:variables
}
