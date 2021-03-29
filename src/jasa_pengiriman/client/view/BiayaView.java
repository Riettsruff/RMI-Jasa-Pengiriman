package jasa_pengiriman.client.view;

import jasa_pengiriman.client.config.RMI;
import jasa_pengiriman.client.service.BasicValidation;
import jasa_pengiriman.client.service.CurrencyFormat;
import jasa_pengiriman.client.service.Table;
import jasa_pengiriman.client.store.ActiveUser;
import jasa_pengiriman.model.Biaya;
import jasa_pengiriman.model.Kota;
import jasa_pengiriman.model.Provinsi;
import jasa_pengiriman.server.service.BiayaService;
import jasa_pengiriman.server.service.KotaService;
import jasa_pengiriman.server.service.ProvinsiService;
import java.awt.event.ItemEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class BiayaView extends javax.swing.JFrame {
    private ProvinsiService provinsiService;
    private KotaService kotaService;
    private BiayaService biayaService;
    /**
     * Creates new form Biaya
     */
    public BiayaView() {
      initRMIServices();
      initComponents();
      initInputData();
      initBiayaTableData();
    }
    
    private void initRMIServices() {
      try {
        this.provinsiService = (ProvinsiService) RMI.getService("ProvinsiService");
        this.kotaService = (KotaService) RMI.getService("KotaService");
        this.biayaService = (BiayaService) RMI.getService("BiayaService");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Internal Server Error", "Oops!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }
    
    private void initInputData() {
      provinsiAsalComboBox.removeAllItems();
      provinsiAsalComboBox.addItem("- Pilih -");
      provinsiAsalComboBox.setSelectedIndex(0);
      
      kotaAsalComboBox.removeAllItems();
      kotaAsalComboBox.addItem("- Pilih -");
      kotaAsalComboBox.setSelectedIndex(0);
      kotaAsalComboBox.setEnabled(false);
      
      provinsiTujuanComboBox.removeAllItems();
      provinsiTujuanComboBox.addItem("- Pilih -");
      provinsiTujuanComboBox.setSelectedIndex(0);
      
      kotaTujuanComboBox.removeAllItems();
      kotaTujuanComboBox.addItem("- Pilih -");
      kotaTujuanComboBox.setSelectedIndex(0);
      kotaTujuanComboBox.setEnabled(false);
      
      hargaTextField.setText("");
      
      try {
        List<Provinsi> provinsiList = provinsiService.getAll();
        
        for(Provinsi provinsi : provinsiList) {
          provinsiAsalComboBox.addItem(provinsi);
          provinsiTujuanComboBox.addItem(provinsi);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(BiayaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initBiayaTableData() {
      try {
        List<Biaya> biayaList = biayaService.getAll();
        
        String[] fieldsData = {"No.", "Id Biaya", "Provinsi Asal", "Kota Asal", "Provinsi Tujuan", "Kota Tujuan", "Harga"};
        Object[][] rowsData = new Object[biayaList.size()][fieldsData.length];
        
        for(int i=0; i < biayaList.size(); ++i) {
          rowsData[i][0] = (i + 1) + ".";
          rowsData[i][1] = biayaList.get(i).getIdBiaya();
          rowsData[i][2] = biayaList.get(i).getKotaAsal().getProvinsi();
          rowsData[i][3] = biayaList.get(i).getKotaAsal();
          rowsData[i][4] = biayaList.get(i).getKotaTujuan().getProvinsi();
          rowsData[i][5] = biayaList.get(i).getKotaTujuan();
          rowsData[i][6] = CurrencyFormat.getString(biayaList.get(i).getHarga(), "in", "ID");
        }
        
        Table.setModel(biayaTable, rowsData, fieldsData, false);
        Table.setColumnWidths(biayaTable, 50);
        Table.setCellsHorizontalAlignment(biayaTable, new HashMap<Integer, Integer>(){{
          put(0, JLabel.CENTER);
        }});
        Table.removeColumns(biayaTable, 1);
      } catch (RemoteException ex) {
        Logger.getLogger(BiayaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private Biaya getInputDataBiaya() {
      Provinsi provinsiAsal = 
        provinsiAsalComboBox.getSelectedItem() instanceof String
          ? null
          : (Provinsi) provinsiAsalComboBox.getSelectedItem();
      Kota kotaAsal = 
        kotaAsalComboBox.getSelectedItem() instanceof String
          ? null
          : (Kota) kotaAsalComboBox.getSelectedItem();
      Provinsi provinsiTujuan =
        provinsiTujuanComboBox.getSelectedItem() instanceof String
          ? null
          : (Provinsi) provinsiTujuanComboBox.getSelectedItem();
      Kota kotaTujuan = 
        kotaTujuanComboBox.getSelectedItem() instanceof String
        ? null
        : (Kota) kotaTujuanComboBox.getSelectedItem();
      Long harga;
      
      try {
        harga = Long.parseLong(hargaTextField.getText());
      } catch(NumberFormatException ex) {
        harga = hargaTextField.getText().isEmpty() ? Long.MIN_VALUE : Long.MAX_VALUE;
      }
      
      Biaya biaya = new Biaya();
      
      if(kotaAsal != null) kotaAsal.setProvinsi(provinsiAsal);
      if(kotaTujuan != null) kotaTujuan.setProvinsi(provinsiAsal);
      
      biaya.setKotaAsal(kotaAsal);
      biaya.setKotaTujuan(kotaTujuan);
      biaya.setHarga(harga);
      
      return biaya;
    }
    
    private boolean isDataBiayaValid(Biaya biaya) {
      LinkedHashMap<HashMap<String, Object>, List<String>> data = 
        new LinkedHashMap<HashMap<String, Object>, List<String>>(){{
          put(
            new HashMap<String, Object>(){{ put("Kota Asal", biaya.getKotaAsal()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Kota Tujuan", biaya.getKotaTujuan()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ 
              put(
                "Harga", 
                biaya.getHarga() == Long.MIN_VALUE 
                  ? "" 
                  : biaya.getHarga() == Long.MAX_VALUE 
                    ? "-" 
                    : biaya.getHarga()); 
            }},
            new ArrayList<String>(){{ add("REQUIRED"); add("NUMERIC"); }}
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

    jScrollPane1 = new javax.swing.JScrollPane();
    jTable1 = new javax.swing.JTable();
    jLabel1 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    provinsiAsalComboBox = new javax.swing.JComboBox<>();
    jLabel5 = new javax.swing.JLabel();
    kotaAsalComboBox = new javax.swing.JComboBox<>();
    jScrollPane2 = new javax.swing.JScrollPane();
    biayaTable = new javax.swing.JTable();
    simpanButton = new javax.swing.JButton();
    menuUtamaButton = new javax.swing.JButton();
    keluarButton = new javax.swing.JButton();
    jLabel7 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    hargaTextField = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    provinsiTujuanComboBox = new javax.swing.JComboBox<>();
    kotaTujuanComboBox = new javax.swing.JComboBox<>();
    updateButton = new javax.swing.JButton();
    hapusButton = new javax.swing.JButton();
    refreshButton = new javax.swing.JButton();
    resetButton = new javax.swing.JButton();

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

    jLabel1.setFont(new java.awt.Font("Bauhaus 93", 0, 18)); // NOI18N
    jLabel1.setText("Biaya Pengiriman");

    jLabel4.setText("Provinsi");

    provinsiAsalComboBox.setName("cmbProvinsiAsal"); // NOI18N
    provinsiAsalComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        provinsiAsalComboBoxItemStateChanged(evt);
      }
    });
    provinsiAsalComboBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        provinsiAsalComboBoxActionPerformed(evt);
      }
    });

    jLabel5.setText("Kota");

    kotaAsalComboBox.setName("cmbKotaAsal"); // NOI18N

    biayaTable.setModel(new javax.swing.table.DefaultTableModel(
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
    biayaTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        biayaTableMouseClicked(evt);
      }
    });
    jScrollPane2.setViewportView(biayaTable);

    simpanButton.setText("Simpan");
    simpanButton.setName("btnSimpan"); // NOI18N
    simpanButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        simpanButtonActionPerformed(evt);
      }
    });

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

    jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jLabel7.setText("Asal");

    jLabel2.setText("Harga/KG");

    hargaTextField.setText(" ");
    hargaTextField.setName("txtHarga"); // NOI18N

    jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
    jLabel3.setText("Tujuan");

    jLabel6.setText("Provinsi");

    jLabel8.setText("Kota");

    provinsiTujuanComboBox.setName("cmbProvinsiTujuan"); // NOI18N
    provinsiTujuanComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        provinsiTujuanComboBoxItemStateChanged(evt);
      }
    });

    kotaTujuanComboBox.setName("cmbKotaTujuan"); // NOI18N

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
          .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
          .addGroup(layout.createSequentialGroup()
            .addComponent(simpanButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(updateButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(hapusButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(resetButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(refreshButton))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(menuUtamaButton)
              .addComponent(jLabel7)
              .addComponent(jLabel2)
              .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                .addComponent(hargaTextField, javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4))
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                      .addGap(18, 18, 18)
                      .addComponent(provinsiAsalComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                      .addGap(18, 18, 18)
                      .addComponent(kotaAsalComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(keluarButton))
              .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                  .addComponent(jLabel3)
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(provinsiTujuanComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(kotaTujuanComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE)))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel1)
              .addComponent(menuUtamaButton)
              .addComponent(keluarButton))
            .addGap(26, 26, 26)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel7)
              .addComponent(jLabel3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jLabel4)
                  .addComponent(provinsiAsalComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addGap(3, 3, 3))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                  .addComponent(jLabel6)
                  .addComponent(provinsiTujuanComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(kotaTujuanComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
              .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)))
          .addComponent(kotaAsalComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addGap(18, 18, 18)
        .addComponent(jLabel2)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(hargaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(simpanButton)
          .addComponent(updateButton)
          .addComponent(hapusButton)
          .addComponent(resetButton)
          .addComponent(refreshButton))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void provinsiAsalComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_provinsiAsalComboBoxActionPerformed
      
    }//GEN-LAST:event_provinsiAsalComboBoxActionPerformed

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
    Biaya biaya = getInputDataBiaya();
    
    if(isDataBiayaValid(biaya)) {
      try {
        if(biayaService.insert(biaya)) {
          JOptionPane
            .showMessageDialog(this, "Biaya berhasil ditambahkan.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initBiayaTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Biaya gagal ditambahkan.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(BiayaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_simpanButtonActionPerformed

  private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
    Biaya biaya = getInputDataBiaya();
    int selectedRow = biayaTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah biaya terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      biaya.setIdBiaya((int) Table.getValue(biayaTable, selectedRow, 1));
      
      try {
        if(biayaService.update(biaya)) {
          JOptionPane
            .showMessageDialog(this, "Biaya berhasil diupdate.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initBiayaTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Biaya gagal diupdate.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(BiayaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_updateButtonActionPerformed

  private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
    int selectedRow = biayaTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah biaya terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      int idBiaya = (int) Table.getValue(biayaTable, selectedRow, 1);
      
      try {
        if(biayaService.deleteByIdBiaya(idBiaya)) {
          JOptionPane
            .showMessageDialog(this, "Biaya berhasil dihapus.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initBiayaTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Biaya gagal dihapus.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(BiayaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_hapusButtonActionPerformed

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    initInputData();
    
    biayaTable.getSelectionModel().clearSelection();
  }//GEN-LAST:event_resetButtonActionPerformed

  private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
    initBiayaTableData();
  }//GEN-LAST:event_refreshButtonActionPerformed

  private void biayaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_biayaTableMouseClicked
    int selectedRow = biayaTable.getSelectedRow();
    
    provinsiAsalComboBox.getModel().setSelectedItem(Table.getValue(biayaTable, selectedRow, 2));
    kotaAsalComboBox.getModel().setSelectedItem(Table.getValue(biayaTable, selectedRow, 3));
    provinsiTujuanComboBox.getModel().setSelectedItem(Table.getValue(biayaTable, selectedRow, 4));
    kotaTujuanComboBox.getModel().setSelectedItem(Table.getValue(biayaTable, selectedRow, 5));
    
    String harga = 
      Table.getValue(biayaTable, selectedRow, 6).toString().replace(".", "").replace(",00", "").replace("Rp", "");
    hargaTextField.setText(harga);
  }//GEN-LAST:event_biayaTableMouseClicked

  private void provinsiAsalComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_provinsiAsalComboBoxItemStateChanged
    if(evt.getStateChange() == ItemEvent.SELECTED) {
      kotaAsalComboBox.removeAllItems();
      kotaAsalComboBox.addItem("- Pilih -");
      kotaAsalComboBox.setSelectedIndex(0);
      
      if(provinsiAsalComboBox.getSelectedItem() instanceof String) {
        kotaAsalComboBox.setEnabled(false);
      } else {
        try {
          Provinsi provinsiAsal = (Provinsi) provinsiAsalComboBox.getSelectedItem();
          List<Kota> kotaList = kotaService.getByIdProvinsi(provinsiAsal.getIdProvinsi());
          
          kotaAsalComboBox.setEnabled(true);
          
          for(Kota kota : kotaList) kotaAsalComboBox.addItem(kota);
        } catch (RemoteException ex) {
          Logger.getLogger(BiayaView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_provinsiAsalComboBoxItemStateChanged

  private void provinsiTujuanComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_provinsiTujuanComboBoxItemStateChanged
    if(evt.getStateChange() == ItemEvent.SELECTED) {
      kotaTujuanComboBox.removeAllItems();
      kotaTujuanComboBox.addItem("- Pilih -");
      kotaTujuanComboBox.setSelectedIndex(0);
      
      if(provinsiTujuanComboBox.getSelectedItem() instanceof String) {
        kotaTujuanComboBox.setEnabled(false);
      } else {
        try {
          Provinsi provinsiTujuan = (Provinsi) provinsiTujuanComboBox.getSelectedItem();
          List<Kota> kotaList = kotaService.getByIdProvinsi(provinsiTujuan.getIdProvinsi());
          
          kotaTujuanComboBox.setEnabled(true);
          
          for(Kota kota : kotaList) kotaTujuanComboBox.addItem(kota);
        } catch (RemoteException ex) {
          Logger.getLogger(BiayaView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_provinsiTujuanComboBoxItemStateChanged

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
            java.util.logging.Logger.getLogger(BiayaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BiayaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BiayaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BiayaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BiayaView().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTable biayaTable;
  private javax.swing.JButton hapusButton;
  private javax.swing.JTextField hargaTextField;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTable jTable1;
  private javax.swing.JButton keluarButton;
  private javax.swing.JComboBox<Object> kotaAsalComboBox;
  private javax.swing.JComboBox<Object> kotaTujuanComboBox;
  private javax.swing.JButton menuUtamaButton;
  private javax.swing.JComboBox<Object> provinsiAsalComboBox;
  private javax.swing.JComboBox<Object> provinsiTujuanComboBox;
  private javax.swing.JButton refreshButton;
  private javax.swing.JButton resetButton;
  private javax.swing.JButton simpanButton;
  private javax.swing.JButton updateButton;
  // End of variables declaration//GEN-END:variables
}
