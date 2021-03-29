/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.view;

import jasa_pengiriman.client.store.ActiveUser;
import jasa_pengiriman.client.config.RMI;
import jasa_pengiriman.client.service.BasicValidation;
import jasa_pengiriman.client.service.Table;
import jasa_pengiriman.model.Akses;
import jasa_pengiriman.model.DetailAkses;
import jasa_pengiriman.model.Peran;
import jasa_pengiriman.server.service.AksesService;
import jasa_pengiriman.server.service.DetailAksesService;
import jasa_pengiriman.server.service.PeranService;
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
public class AksesView extends javax.swing.JFrame {
    private PeranService peranService;
    private AksesService aksesService;
    private DetailAksesService detailAksesService;
    /**
     * Creates new form AksesView
     */
    public AksesView() {
      initRMIServices();
      initComponents();
      initInputData();
      initAksesTableData();
      initDetailAksesTableData(-1);
    }
    
    private void initRMIServices() {
      try {
        this.peranService = (PeranService) RMI.getService("PeranService");
        this.aksesService = (AksesService) RMI.getService("AksesService");
        this.detailAksesService = (DetailAksesService) RMI.getService("DetailAksesService");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Internal Server Error", "Oops!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }
    
    private void initInputData() {
      namaAksesTextField.setText("");
      operasiTextField.setText("");
      
      peranComboBox.removeAllItems();
      peranComboBox.addItem("- Pilih -");
      peranComboBox.setSelectedIndex(0);
      
      batasanOperasiTextField.setText("");
      
      try {
        List<Peran> peranList = peranService.getAll();
        
        for(Peran peran : peranList) peranComboBox.addItem(peran);
      } catch (RemoteException ex) {
        Logger.getLogger(AksesView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initAksesTableData() {
      try {
        List<Akses> aksesList = aksesService.getAll();
        
        String[] fieldsData = {"No.", "Id Akses", "Nama Akses", "Operasi"};
        
        Object[][] rowsData = new Object[aksesList.size()][fieldsData.length];
        
        for(int i=0; i < aksesList.size(); ++i) {
          rowsData[i][0] = (i + 1) + ".";
          rowsData[i][1] = aksesList.get(i).getIdAkses();
          rowsData[i][2] = aksesList.get(i).getNamaAkses();
          rowsData[i][3] = aksesList.get(i).getOperasi();
        }
        
        Table.setModel(aksesTable, rowsData, fieldsData, false);
        Table.setColumnWidths(aksesTable, 50);
        Table.setCellsHorizontalAlignment(aksesTable, new HashMap<Integer, Integer>(){{
          put(0, JLabel.CENTER);
        }});
        Table.removeColumns(aksesTable, 1, 2);
      } catch (RemoteException ex) {
        Logger.getLogger(AksesView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initDetailAksesTableData(int idAkses) {
      String[] fieldsData = {"No.", "Id Detail Akses", "Nama Peran", "Batasan Operasi"};
      Object[][] rowsData = null;
      
      if(idAkses == -1) {
        Table.setModel(detailAksesTable, rowsData, fieldsData, false);
      } else {
        try {
          List<DetailAkses> detailAksesList = detailAksesService.getByIdAkses(idAkses);
          rowsData = new Object[detailAksesList.size()][fieldsData.length];

          for(int i=0; i < detailAksesList.size(); ++i) {
            rowsData[i][0] = (i + 1) + ".";
            rowsData[i][1] = detailAksesList.get(i).getIdDetailAkses();
            
            Object peran = detailAksesList.get(i).getPeran();
            
            rowsData[i][2] = peran.toString() != null ? peran : "Tidak diketahui";
            rowsData[i][3] = detailAksesList.get(i).getBatasanOperasi();
          }
          
          Table.setModel(detailAksesTable, rowsData, fieldsData, false);
        } catch (RemoteException ex) {
          Logger.getLogger(AksesView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      Table.setCellsHorizontalAlignment(detailAksesTable, new HashMap<Integer, Integer>(){{
        put(0, JLabel.CENTER);
      }});
      Table.setColumnWidths(detailAksesTable, 50);
      Table.removeColumns(detailAksesTable, 1);
    }
    
    private DetailAkses getInputDataDetailAkses() {
      Peran peran = 
        peranComboBox.getSelectedItem() instanceof String ? null : (Peran) peranComboBox.getSelectedItem();
      String batasanOperasi = batasanOperasiTextField.getText();
      
      DetailAkses detailAkses = new DetailAkses();
      detailAkses.setPeran(peran);
      detailAkses.setBatasanOperasi(batasanOperasi);
      
      return detailAkses;
    }
    
    private boolean isDataDetailAksesValid(DetailAkses detailAkses) {
      LinkedHashMap<HashMap<String, Object>, List<String>> data = 
        new LinkedHashMap<HashMap<String, Object>, List<String>>(){{
          put(
            new HashMap<String, Object>(){{ put("Peran", detailAkses.getPeran()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Batasan Operasi", detailAkses.getBatasanOperasi()); }},
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
    keluarButton = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    namaAksesTextField = new javax.swing.JTextField();
    operasiTextField = new javax.swing.JTextField();
    peranComboBox = new javax.swing.JComboBox<>();
    batasanOperasiTextField = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    simpanButton = new javax.swing.JButton();
    updateButton = new javax.swing.JButton();
    hapusButton = new javax.swing.JButton();
    jScrollPane1 = new javax.swing.JScrollPane();
    detailAksesTable = new javax.swing.JTable();
    jScrollPane3 = new javax.swing.JScrollPane();
    aksesTable = new javax.swing.JTable();
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

    keluarButton.setText("Keluar");
    keluarButton.setName("btnKeluar"); // NOI18N
    keluarButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        keluarButtonActionPerformed(evt);
      }
    });

    jLabel1.setText("Nama Akses");

    jLabel2.setText("Operasi");
    jLabel2.setToolTipText("");

    jLabel3.setText("Peran");

    jLabel4.setText("Batasan Operasi");

    namaAksesTextField.setEditable(false);
    namaAksesTextField.setText(" ");
    namaAksesTextField.setDisabledTextColor(new java.awt.Color(51, 51, 51));
    namaAksesTextField.setEnabled(false);
    namaAksesTextField.setName("txtNamaAkses"); // NOI18N

    operasiTextField.setEditable(false);
    operasiTextField.setText(" ");
    operasiTextField.setDisabledTextColor(new java.awt.Color(51, 51, 51));
    operasiTextField.setEnabled(false);
    operasiTextField.setName("txtOperasi"); // NOI18N

    peranComboBox.setName("cmbPeran"); // NOI18N
    peranComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        peranComboBoxActionPerformed(evt);
      }
    });

    batasanOperasiTextField.setText(" ");
    batasanOperasiTextField.setName("txtBatasanOperasi"); // NOI18N

    jLabel5.setFont(new java.awt.Font("Bauhaus 93", 0, 18)); // NOI18N
    jLabel5.setText("Akses");

    jLabel6.setText("Detail Akses");

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

    detailAksesTable.setModel(new javax.swing.table.DefaultTableModel(
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
    detailAksesTable.setName("tblInformasiPeran"); // NOI18N
    detailAksesTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        detailAksesTableMouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(detailAksesTable);

    aksesTable.setModel(new javax.swing.table.DefaultTableModel(
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
    aksesTable.setName("tblAkses"); // NOI18N
    aksesTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        aksesTableMouseClicked(evt);
      }
    });
    jScrollPane3.setViewportView(aksesTable);

    refreshButton.setText("Refresh");
    refreshButton.setName("txtRefresh"); // NOI18N
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
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                  .addGap(31, 31, 31)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(operasiTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                    .addComponent(namaAksesTextField)))
                .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(batasanOperasiTextField)
                    .addComponent(peranComboBox, 0, 152, Short.MAX_VALUE))))
              .addGroup(layout.createSequentialGroup()
                .addComponent(simpanButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(updateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(hapusButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(resetButton)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(jLabel6)))
          .addGroup(layout.createSequentialGroup()
            .addComponent(menuUtamaButton)
            .addGap(179, 179, 179)
            .addComponent(jLabel5)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(keluarButton))
          .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
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
          .addComponent(jLabel5))
        .addGap(29, 29, 29)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel6)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(refreshButton))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel1)
              .addComponent(namaAksesTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel2)
              .addComponent(operasiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(52, 52, 52)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel3)
              .addComponent(peranComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(9, 9, 9)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel4)
              .addComponent(batasanOperasiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(simpanButton)
              .addComponent(hapusButton)
              .addComponent(resetButton)
              .addComponent(updateButton))))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

  private void peranComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_peranComboBoxActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_peranComboBoxActionPerformed

  private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
    initAksesTableData();
    initDetailAksesTableData(-1);
  }//GEN-LAST:event_refreshButtonActionPerformed

  private void aksesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aksesTableMouseClicked
    int selectedRow = aksesTable.getSelectedRow();
    
    namaAksesTextField.setText(Table.getValue(aksesTable, selectedRow, 2).toString());
    operasiTextField.setText(Table.getValue(aksesTable, selectedRow, 3).toString());
    
    initDetailAksesTableData((int) Table.getValue(aksesTable, selectedRow, 1));
  }//GEN-LAST:event_aksesTableMouseClicked

  private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
    DetailAkses detailAkses = getInputDataDetailAkses();
    
    if(isDataDetailAksesValid(detailAkses)) {
      try {
        if(detailAksesService.insert(detailAkses)) {
          JOptionPane
            .showMessageDialog(this, "Detail Akses berhasil ditambahkan.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initAksesTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Detail Akses gagal ditambahkan.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(AksesView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_simpanButtonActionPerformed

  private void detailAksesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_detailAksesTableMouseClicked
    int selectedRow = detailAksesTable.getSelectedRow();
    
    Object peranObj = Table.getValue(detailAksesTable, selectedRow, 2);
    
    if(peranObj instanceof String) peranComboBox.setSelectedIndex(0);
    else peranComboBox.getModel().setSelectedItem(peranObj);
    
    batasanOperasiTextField.setText(Table.getValue(detailAksesTable, selectedRow, 3).toString());
  }//GEN-LAST:event_detailAksesTableMouseClicked

  private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
    DetailAkses detailAkses = getInputDataDetailAkses();
    int selectedRow = detailAksesTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah detail akses terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      detailAkses.setIdDetailAkses((int) Table.getValue(detailAksesTable, selectedRow, 1));
      
      if(isDataDetailAksesValid(detailAkses)) {
        try {
          if(detailAksesService.update(detailAkses)) {
            JOptionPane
              .showMessageDialog(this, "Detail Akses berhasil diupdate.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
            initInputData();
            initAksesTableData();
          } else {
            JOptionPane
              .showMessageDialog(this, "Detail Akses gagal diupdate.", "Oops!", JOptionPane.ERROR_MESSAGE);
          }
        } catch (RemoteException ex) {
          Logger.getLogger(AksesView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_updateButtonActionPerformed

  private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
    int selectedRow = detailAksesTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah detail akses terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      int idDetailAkses = (int) Table.getValue(detailAksesTable, selectedRow, 1);
      
      try {
        if(detailAksesService.deleteByIdDetailAkses(idDetailAkses)) {
          JOptionPane
            .showMessageDialog(this, "Detail Akses berhasil dihapus.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initAksesTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Detail Akses gagal dihapus.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(AksesView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_hapusButtonActionPerformed

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    initInputData();
    
    detailAksesTable.getSelectionModel().clearSelection();
  }//GEN-LAST:event_resetButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AksesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AksesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AksesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AksesView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AksesView().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTable aksesTable;
  private javax.swing.JTextField batasanOperasiTextField;
  private javax.swing.JTable detailAksesTable;
  private javax.swing.JButton hapusButton;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JTable jTable2;
  private javax.swing.JButton keluarButton;
  private javax.swing.JButton menuUtamaButton;
  private javax.swing.JTextField namaAksesTextField;
  private javax.swing.JTextField operasiTextField;
  private javax.swing.JComboBox<Object> peranComboBox;
  private javax.swing.JButton refreshButton;
  private javax.swing.JButton resetButton;
  private javax.swing.JButton simpanButton;
  private javax.swing.JButton updateButton;
  // End of variables declaration//GEN-END:variables
}
