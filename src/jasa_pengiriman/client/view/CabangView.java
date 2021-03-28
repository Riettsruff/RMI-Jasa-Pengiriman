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
import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Kota;
import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.model.Provinsi;
import jasa_pengiriman.server.service.CabangService;
import jasa_pengiriman.server.service.KotaService;
import jasa_pengiriman.server.service.PenggunaService;
import jasa_pengiriman.server.service.ProvinsiService;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.sql.Types;
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
public class CabangView extends javax.swing.JFrame {
    private ProvinsiService provinsiService;
    private KotaService kotaService;
    private CabangService cabangService;
    private PenggunaService penggunaService;
    /**
     * Creates new form CabangView
     */
    public CabangView() {
      if(ActiveUser.get() != null) {
        initRMIServices();
        initComponents();
        initInputData();
        initCabangTableData();
        initPenggunaTableData(-1);
      } else {
        try {
          JasaPengiriman.main(null);
        } catch (RemoteException ex) {
          Logger.getLogger(CabangView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    
    private void initRMIServices() {
      try {
        this.provinsiService = (ProvinsiService) RMI.getService("ProvinsiService");
        this.kotaService = (KotaService) RMI.getService("KotaService");
        this.cabangService = (CabangService) RMI.getService("CabangService");
        this.penggunaService = (PenggunaService) RMI.getService("PenggunaService");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Internal Server Error", "Oops!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }
    
    private void initInputData() {
      namaCabangTextField.setText("");
      alamatTextField.setText("");
      noHpTextField.setText("");
      
      provinsiComboBox.removeAllItems();
      provinsiComboBox.addItem("- Pilih -");
      
      kotaComboBox.removeAllItems();
      kotaComboBox.addItem("- Pilih -");
      
      try {
        List<Provinsi> provinsiList = provinsiService.getAll();
        List<Kota> kotaList = kotaService.getAll();
        
        for(Provinsi provinsi : provinsiList) provinsiComboBox.addItem(provinsi);
        for(Kota kota : kotaList) kotaComboBox.addItem(kota);
      } catch (RemoteException ex) {
        Logger.getLogger(CabangView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initCabangTableData() {
      try {
        List<Cabang> cabangList = cabangService.getAll();
        String[] fieldsData = {"No.", "Id Cabang", "Nama Cabang", "Provinsi", "Kota", "Alamat", "No.HP"};
        
        Object[][] rowsData = new Object[cabangList.size()][fieldsData.length];
        
        for(int i=0; i < cabangList.size(); ++i) {
          rowsData[i][0] = (i + 1) + ".";
          rowsData[i][1] = cabangList.get(i).getIdCabang();
          rowsData[i][2] = cabangList.get(i).getNamaCabang();
          rowsData[i][3] = cabangList.get(i).getKota().getProvinsi();
          rowsData[i][4] = cabangList.get(i).getKota();
          rowsData[i][5] = cabangList.get(i).getAlamat();
          rowsData[i][6] = cabangList.get(i).getNoHp();
        }
        
        Table.setModel(cabangTable, rowsData, fieldsData, false);
        Table.setColumnWidths(cabangTable, 50);
        Table.setCellsHorizontalAlignment(cabangTable, new HashMap<Integer, Integer>(){{
          put(0, JLabel.CENTER);
        }});
        Table.removeColumns(cabangTable, 1);
      } catch (RemoteException ex) {
        Logger.getLogger(CabangView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initPenggunaTableData(int idCabang) {
      String[] fieldsData = {"No.", "Id Pengguna", "Nama", "Email", "Terakhir Login"};
      Object[][] rowsData = null;
      
      if(idCabang == -1) {
        Table.setModel(penggunaTable, rowsData, fieldsData, false);
      } else {
        try {
          List<Pengguna> penggunaList = penggunaService.getByIdCabang(idCabang);
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
    
    private Cabang getInputDataCabang() {
      String namaCabang = namaCabangTextField.getText();
      String alamat = alamatTextField.getText();
      String noHp = noHpTextField.getText();
      Provinsi provinsi = 
        provinsiComboBox.getSelectedItem() instanceof String 
          ? null 
          : (Provinsi) provinsiComboBox.getSelectedItem();
      Kota kota = 
        kotaComboBox.getSelectedItem() instanceof String
          ? null
          : (Kota) kotaComboBox.getSelectedItem();
      
      Cabang cabang = new Cabang();
      cabang.setNamaCabang(namaCabang);
      cabang.setAlamat(alamat);
      cabang.setNoHp(noHp);
      
      if(kota != null) kota.setProvinsi(provinsi);
      cabang.setKota(kota);
      
      return cabang;
    }
    
    private boolean isDataCabangValid(Cabang cabang) {
      LinkedHashMap<HashMap<String, Object>, List<String>> data = 
        new LinkedHashMap<HashMap<String, Object>, List<String>>(){{
          put(
            new HashMap<String, Object>(){{ put("Nama Cabang", cabang.getNamaCabang()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Alamat", cabang.getAlamat()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("No.HP", cabang.getNoHp()); }},
            new ArrayList<String>(){{ add("REQUIRED"); add("NUMERIC"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Provinsi", cabang.getKota() != null ? cabang.getKota().getProvinsi() : null); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Kota", cabang.getKota()); }},
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

    jTextField2 = new javax.swing.JTextField();
    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    menuUtamaButton = new javax.swing.JButton();
    keluarButton = new javax.swing.JButton();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    namaCabangTextField = new javax.swing.JTextField();
    alamatTextField = new javax.swing.JTextField();
    noHpTextField = new javax.swing.JTextField();
    provinsiComboBox = new javax.swing.JComboBox<>();
    kotaComboBox = new javax.swing.JComboBox<>();
    simpanButton = new javax.swing.JButton();
    updateButton = new javax.swing.JButton();
    hapusButton = new javax.swing.JButton();
    jLabel7 = new javax.swing.JLabel();
    jScrollPane2 = new javax.swing.JScrollPane();
    penggunaTable = new javax.swing.JTable();
    jScrollPane3 = new javax.swing.JScrollPane();
    cabangTable = new javax.swing.JTable();
    refreshButton = new javax.swing.JButton();
    resetButton = new javax.swing.JButton();

    jTextField2.setText("jTextField2");

    jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
    jScrollPane1.setViewportView(jTable1);

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
    jLabel1.setText("Cabang");

    jLabel2.setText("Nama Cabang");

    jLabel3.setText("Alamat");

    jLabel4.setText("No. HP");

    jLabel5.setText("Provinsi");

    jLabel6.setText("Kota");

    namaCabangTextField.setText(" ");
    namaCabangTextField.setName("txtNamaCabang"); // NOI18N

    alamatTextField.setText(" ");
    alamatTextField.setName("txtAlamat"); // NOI18N

    noHpTextField.setText(" ");
    noHpTextField.setName("txtNoHP"); // NOI18N

    provinsiComboBox.setName("cmbProvinsi"); // NOI18N

    kotaComboBox.setName("cmbKota"); // NOI18N

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

    jLabel7.setText("Informasi Pengguna");

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
    jScrollPane2.setViewportView(penggunaTable);

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
    cabangTable.setName("tblCabang"); // NOI18N
    cabangTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        cabangTableMouseClicked(evt);
      }
    });
    jScrollPane3.setViewportView(cabangTable);

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
          .addComponent(jScrollPane3)
          .addGroup(layout.createSequentialGroup()
            .addComponent(menuUtamaButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1)
            .addGap(336, 336, 336)
            .addComponent(keluarButton))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabel2)
                  .addComponent(jLabel3)
                  .addComponent(jLabel4))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(alamatTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(noHpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addComponent(namaCabangTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(kotaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(provinsiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createSequentialGroup()
                  .addGap(0, 0, Short.MAX_VALUE)
                  .addComponent(simpanButton)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(updateButton)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(hapusButton)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                  .addComponent(resetButton))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addGap(565, 565, 565)
                .addComponent(refreshButton)
                .addGap(0, 0, Short.MAX_VALUE))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jLabel7)
                  .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE))))))
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
        .addGap(15, 15, 15)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel7)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addComponent(refreshButton))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jLabel2)
                  .addComponent(namaCabangTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4))
              .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(alamatTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(noHpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel5)
              .addComponent(provinsiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(9, 9, 9)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel6)
              .addComponent(kotaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(simpanButton)
              .addComponent(updateButton)
              .addComponent(hapusButton)
              .addComponent(resetButton))))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(14, Short.MAX_VALUE))
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

  private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
    Cabang cabang = getInputDataCabang();
    
    if(isDataCabangValid(cabang)) {
      try {
        if(cabangService.insert(cabang)) {
          JOptionPane
            .showMessageDialog(this, "Cabang berhasil ditambahkan.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initCabangTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Cabang gagal ditambahkan.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(CabangView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_simpanButtonActionPerformed

  private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
    Cabang cabang = getInputDataCabang();
    int selectedRow = cabangTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah cabang terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      cabang.setIdCabang((int) Table.getValue(cabangTable, selectedRow, 1));
      
      if(isDataCabangValid(cabang)) {
        try {
          if(cabangService.update(cabang)) {
            JOptionPane
              .showMessageDialog(this, "Cabang berhasil diupdate.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
            initInputData();
            initCabangTableData();
          } else {
            JOptionPane
              .showMessageDialog(this, "Cabang gagal diupdate.", "Oops!", JOptionPane.ERROR_MESSAGE);
          }
        } catch (RemoteException ex) {
          Logger.getLogger(CabangView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_updateButtonActionPerformed

  private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
    int selectedRow = cabangTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah cabang terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      int idCabang = (int) Table.getValue(cabangTable, selectedRow, 1);
      
      try {
        if(cabangService.deleteByIdCabang(idCabang)) {
          JOptionPane
            .showMessageDialog(this, "Cabang berhasil dihapus.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initCabangTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Cabang gagal dihapus.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(CabangView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_hapusButtonActionPerformed

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    initInputData();
    
    cabangTable.getSelectionModel().clearSelection();
    Table.deleteAllRows(penggunaTable);
  }//GEN-LAST:event_resetButtonActionPerformed

  private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
    initCabangTableData();
    initPenggunaTableData(-1);
  }//GEN-LAST:event_refreshButtonActionPerformed

  private void cabangTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cabangTableMouseClicked
    int selectedRow = cabangTable.getSelectedRow();
    
    namaCabangTextField.setText(Table.getValue(cabangTable, selectedRow, 2).toString());
    alamatTextField.setText(Table.getValue(cabangTable, selectedRow, 5).toString());
    noHpTextField.setText(Table.getValue(cabangTable, selectedRow, 6).toString());
    provinsiComboBox.getModel().setSelectedItem(Table.getValue(cabangTable, selectedRow, 3));
    kotaComboBox.getModel().setSelectedItem(Table.getValue(cabangTable, selectedRow, 4));
    
    initPenggunaTableData((int) Table.getValue(cabangTable, selectedRow, 1));
  }//GEN-LAST:event_cabangTableMouseClicked

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
            java.util.logging.Logger.getLogger(CabangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CabangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CabangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CabangView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CabangView().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField alamatTextField;
  private javax.swing.JTable cabangTable;
  private javax.swing.JButton hapusButton;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JTable jTable1;
  private javax.swing.JTextField jTextField2;
  private javax.swing.JButton keluarButton;
  private javax.swing.JComboBox<Object> kotaComboBox;
  private javax.swing.JButton menuUtamaButton;
  private javax.swing.JTextField namaCabangTextField;
  private javax.swing.JTextField noHpTextField;
  private javax.swing.JTable penggunaTable;
  private javax.swing.JComboBox<Object> provinsiComboBox;
  private javax.swing.JButton refreshButton;
  private javax.swing.JButton resetButton;
  private javax.swing.JButton simpanButton;
  private javax.swing.JButton updateButton;
  // End of variables declaration//GEN-END:variables
}
