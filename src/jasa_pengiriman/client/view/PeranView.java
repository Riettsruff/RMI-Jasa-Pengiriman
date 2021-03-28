/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jasa_pengiriman.client.view;

import jasa_pengiriman.JasaPengiriman;
import jasa_pengiriman.client.config.RMI;
import jasa_pengiriman.client.service.BasicValidation;
import jasa_pengiriman.client.service.DateFormat;
import jasa_pengiriman.client.service.Table;
import jasa_pengiriman.client.store.ActiveUser;
import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.model.Peran;
import jasa_pengiriman.server.service.PenggunaService;
import jasa_pengiriman.server.service.PeranService;
import java.rmi.RemoteException;
import java.sql.Timestamp;
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
public class PeranView extends javax.swing.JFrame {
    private PeranService peranService;
    private PenggunaService penggunaService;
    /**
     * Creates new form PeranView
     */
    public PeranView() {
      if(ActiveUser.get() != null) {
        initRMIServices();
        initComponents();
        initInputData();
        initPeranTableData();
      } else {
        try {
          JasaPengiriman.main(null);
        } catch (RemoteException ex) {
          Logger.getLogger(PeranView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    
    private void initRMIServices() {
      try {
        this.peranService = (PeranService) RMI.getService("PeranService");
        this.penggunaService = (PenggunaService) RMI.getService("PenggunaService");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Internal Server Error", "Oops!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }
    
    private void initInputData() {
      namaPeranTextField.setText("");
    }
    
    private void initPeranTableData() {
      try {
        List<Peran> peranList = peranService.getAll();
        String[] fieldsData = {"No.", "Id Peran", "Nama Peran"};
        
        Object[][] rowsData = new Object[peranList.size()][fieldsData.length];
        
        for(int i=0; i < peranList.size(); ++i) {
          rowsData[i][0] = (i + 1) + ".";
          rowsData[i][1] = peranList.get(i).getIdPeran();
          rowsData[i][2] = peranList.get(i).getNamaPeran();
        }
        
        Table.setModel(peranTable, rowsData, fieldsData, false);
        Table.setColumnWidths(peranTable, 50);
        Table.setCellsHorizontalAlignment(peranTable, new HashMap<Integer, Integer>(){{
          put(0, JLabel.CENTER);
        }});
        Table.removeColumns(peranTable, 1);
      } catch (RemoteException ex) {
        Logger.getLogger(PeranView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initPenggunaTableData(int idPeran) {
      String[] fieldsData = {"No.", "Id Pengguna", "Nama", "Email", "Terakhir Login"};
      Object[][] rowsData = null;
      
      if(idPeran == -1) {
        Table.setModel(penggunaTable, rowsData, fieldsData, false);
      } else {
        try {
          List<Pengguna> penggunaList = penggunaService.getByIdPeran(idPeran);
          rowsData = new Object[penggunaList.size()][fieldsData.length];
          
          for(int i=0; i < penggunaList.size(); ++i) {
            rowsData[i][0] = (i + 1) + ".";
            rowsData[i][1] = penggunaList.get(i).getIdPengguna();
            rowsData[i][2] = penggunaList.get(i).getNama();
            rowsData[i][3] = penggunaList.get(i).getEmail();
            
            Timestamp terakhirLogin = penggunaList.get(i).getTerakhirLogin();
            
            rowsData[i][4] = 
              terakhirLogin.toString() != null 
                ? DateFormat.dateToString(DateFormat.timestampToDate(terakhirLogin), "dd-MM-yyyy HH:mm:ss")
                : "Belum pernah";
            
          }
          
          Table.setModel(penggunaTable, rowsData, fieldsData, false);
        } catch (RemoteException ex) {
          Logger.getLogger(PeranView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      Table.setCellsHorizontalAlignment(penggunaTable, new HashMap<Integer, Integer>(){{
        put(0, JLabel.CENTER);
      }});
      Table.setColumnWidths(penggunaTable, 50);
      Table.removeColumns(penggunaTable, 1);
    }
    
    private Peran getInputDataPeran() {
      String namaPeran = namaPeranTextField.getText();
      
      Peran peran = new Peran();
      peran.setNamaPeran(namaPeran);
      
      return peran;
    }
    
    private boolean isDataPeranValid(Peran peran) {
      LinkedHashMap<HashMap<String, Object>, List<String>> data = 
        new LinkedHashMap<HashMap<String, Object>, List<String>>(){{
          put(
            new HashMap<String, Object>(){{ put("Nama Peran", peran.getNamaPeran()); }},
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
    namaPeranTextField = new javax.swing.JTextField();
    simpanButton = new javax.swing.JButton();
    updateButton = new javax.swing.JButton();
    hapusButton = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    penggunaTable = new javax.swing.JTable();
    jScrollPane2 = new javax.swing.JScrollPane();
    peranTable = new javax.swing.JTable();
    refreshButton = new javax.swing.JButton();
    jLabel3 = new javax.swing.JLabel();
    resetButton = new javax.swing.JButton();

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

    jLabel1.setText("Nama Peran");

    namaPeranTextField.setName("txtNamaPeran"); // NOI18N

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

    jLabel2.setText("Informasi Pengguna");

    penggunaTable.setModel(new javax.swing.table.DefaultTableModel(
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
    penggunaTable.setName("tblInformasiPengguna"); // NOI18N
    jScrollPane1.setViewportView(penggunaTable);

    peranTable.setModel(new javax.swing.table.DefaultTableModel(
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
    peranTable.setName("tblPeran"); // NOI18N
    peranTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        peranTableMouseClicked(evt);
      }
    });
    jScrollPane2.setViewportView(peranTable);

    refreshButton.setText("Refresh");
    refreshButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        refreshButtonActionPerformed(evt);
      }
    });

    jLabel3.setFont(new java.awt.Font("Bauhaus 93", 0, 18)); // NOI18N
    jLabel3.setText("Peran");

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
            .addComponent(menuUtamaButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(333, 333, 333)
            .addComponent(keluarButton))
          .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 993, Short.MAX_VALUE)
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
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(namaPeranTextField)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel2)
              .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(keluarButton)
          .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(menuUtamaButton)
            .addComponent(jLabel3)))
        .addGap(21, 21, 21)
        .addComponent(jLabel2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel1)
              .addComponent(namaPeranTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(simpanButton)
              .addComponent(updateButton)
              .addComponent(hapusButton)
              .addComponent(resetButton))))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(refreshButton)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(39, Short.MAX_VALUE))
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
    initPeranTableData();
    initPenggunaTableData(-1);
  }//GEN-LAST:event_refreshButtonActionPerformed

  private void peranTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_peranTableMouseClicked
    int selectedRow = peranTable.getSelectedRow();
    
    namaPeranTextField.setText(Table.getValue(peranTable, selectedRow, 2).toString());
    
    initPenggunaTableData((int) Table.getValue(peranTable, selectedRow, 1));
    
  }//GEN-LAST:event_peranTableMouseClicked

  private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
    Peran peran = getInputDataPeran();
    
    if(isDataPeranValid(peran)) {
      try {
        if(peranService.insert(peran)) {
          JOptionPane
            .showMessageDialog(this, "Peran berhasil ditambahkan.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initPeranTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Peran gagal ditambahkan.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(PeranView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_simpanButtonActionPerformed

  private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
    Peran peran = getInputDataPeran();
    int selectedRow = peranTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah peran terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      peran.setIdPeran((int) Table.getValue(peranTable, selectedRow, 1));
      
      if(isDataPeranValid(peran)) {
        try {
          if(peranService.update(peran)) {
            JOptionPane
              .showMessageDialog(this, "Peran berhasil diupdate.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
            initInputData();
            initPeranTableData();
          } else {
            JOptionPane
              .showMessageDialog(this, "Peran gagal diupdate.", "Oops!", JOptionPane.ERROR_MESSAGE);
          }
        } catch (RemoteException ex) {
          Logger.getLogger(PeranView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_updateButtonActionPerformed

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    initInputData();
    
    peranTable.getSelectionModel().clearSelection();
    Table.deleteAllRows(penggunaTable);
  }//GEN-LAST:event_resetButtonActionPerformed

  private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
    int selectedRow = peranTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah peran terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      int idPeran = (int) Table.getValue(peranTable, selectedRow, 1);
      
      try {
        if(peranService.deleteByIdPeran(idPeran)) {
          JOptionPane
            .showMessageDialog(this, "Peran berhasil dihapus.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initPeranTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Peran gagal dihapus.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(PeranView.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(PeranView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PeranView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PeranView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PeranView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PeranView().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton hapusButton;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JButton keluarButton;
  private javax.swing.JButton menuUtamaButton;
  private javax.swing.JTextField namaPeranTextField;
  private javax.swing.JTable penggunaTable;
  private javax.swing.JTable peranTable;
  private javax.swing.JButton refreshButton;
  private javax.swing.JButton resetButton;
  private javax.swing.JButton simpanButton;
  private javax.swing.JButton updateButton;
  // End of variables declaration//GEN-END:variables
}
