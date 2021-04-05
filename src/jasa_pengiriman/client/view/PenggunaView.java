package jasa_pengiriman.client.view;

import jasa_pengiriman.JasaPengiriman;
import jasa_pengiriman.client.config.RMI;
import jasa_pengiriman.client.service.BasicValidation;
import jasa_pengiriman.client.service.DateFormat;
import jasa_pengiriman.client.service.Table;
import jasa_pengiriman.client.store.ActiveUser;
import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.model.Peran;
import jasa_pengiriman.model.RiwayatPeran;
import jasa_pengiriman.server.service.CabangService;
import jasa_pengiriman.server.service.PenggunaService;
import jasa_pengiriman.server.service.PeranService;
import jasa_pengiriman.server.service.RiwayatPeranService;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
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
public class PenggunaView extends javax.swing.JFrame {
    private PeranService peranService;
    private CabangService cabangService;
    private PenggunaService penggunaService;
    private RiwayatPeranService riwayatPeranService;
    /**
     * Creates new form Pengguna
     */
    public PenggunaView() {
      if(ActiveUser.get() != null) {
        initRMIServices();
        initComponents();
        initInputData();
        initPenggunaTableData();
        initRiwayatPeranTableData(-1);
      } else {
        try {
          JasaPengiriman.main(null);
        } catch (RemoteException ex) {
          Logger.getLogger(PenggunaView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    
    private void initRMIServices() {
      try {
        this.peranService = (PeranService) RMI.getService("PeranService");
        this.cabangService = (CabangService) RMI.getService("CabangService");
        this.penggunaService = (PenggunaService) RMI.getService("PenggunaService");
        this.riwayatPeranService = (RiwayatPeranService) RMI.getService("RiwayatPeranService");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Internal Server Error", "Oops!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }
    
    private void initInputData() {
      namaTextField.setText("");
      emailTextField.setText("");
      
      peranComboBox.removeAllItems();
      peranComboBox.addItem("- Pilih -");
      peranComboBox.setSelectedIndex(0);
      
      cabangComboBox.removeAllItems();
      cabangComboBox.addItem("- Pilih -");
      cabangComboBox.setSelectedIndex(0);
      
      passwordPasswordField.setText("secret");
      passwordPasswordField.setEditable(false);
      
      try {
        List<Peran> peranList = peranService.getAll();
        List<Cabang> cabangList = cabangService.getAll();
        
        for(Peran peran : peranList) peranComboBox.addItem(peran);
        for(Cabang cabang : cabangList) cabangComboBox.addItem(cabang);
      } catch (RemoteException ex) {
        Logger.getLogger(PenggunaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initPenggunaTableData() {
      try {
        List<Pengguna> penggunaList = penggunaService.getAll();
        String[] fieldsData = {"No.", "Id Pengguna", "Nama", "Email", "Peran", "Cabang", "Password", "Terakhir Login"};
        Object[][] rowsData = new Object[penggunaList.size()][fieldsData.length];
        
        for(int i=0; i < penggunaList.size(); ++i) {
          rowsData[i][0] = (i + 1) + ".";
          rowsData[i][1] = penggunaList.get(i).getIdPengguna();
          rowsData[i][2] = penggunaList.get(i).getNama();
          rowsData[i][3] = penggunaList.get(i).getEmail();
          
          Object peran = penggunaList.get(i).getPeran();
          Object cabang = penggunaList.get(i).getCabang();
          Timestamp terakhirLogin = penggunaList.get(i).getTerakhirLogin();
          
          rowsData[i][4] = peran.toString() != null ? peran : "Tidak diketahui";
          rowsData[i][5] = cabang.toString() != null ? cabang : "Tidak diketahui";
          rowsData[i][6] = penggunaList.get(i).getPassword();
          rowsData[i][7] = 
            terakhirLogin != null 
              ? DateFormat.dateToString(DateFormat.timestampToDate(terakhirLogin), "dd-MM-yyyy HH:mm:ss")
              : "Belum pernah";
        }
        
        Table.setModel(penggunaTable, rowsData, fieldsData, false);
        Table.setColumnWidths(penggunaTable, 50);
        Table.setCellsHorizontalAlignment(penggunaTable, new HashMap<Integer, Integer>(){{
          put(0, JLabel.CENTER);
        }});
        Table.removeColumns(penggunaTable, 1, 5);
      } catch (RemoteException ex) {
        Logger.getLogger(PenggunaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initRiwayatPeranTableData(int idPengguna) {
      String[] fieldsData = {"No.", "Id Riwayat Peran", "Nama Pengguna", "Peran", "Tanggal mulai"};
      Object[][] rowsData = null;
      
      if(idPengguna == -1) {
        Table.setModel(riwayatPeranTable, rowsData, fieldsData, false);
      } else {
        try {
          List<RiwayatPeran> riwayatPeranList = riwayatPeranService.getByIdPengguna(idPengguna);
          rowsData = new Object[riwayatPeranList.size()][fieldsData.length];
          
          for(int i=0; i < riwayatPeranList.size(); ++i) {
            rowsData[i][0] = (i + 1) + ".";
            rowsData[i][1] = riwayatPeranList.get(i).getIdRiwayatPeran();
            rowsData[i][2] = riwayatPeranList.get(i).getPengguna();
            
            Object peran = riwayatPeranList.get(i).getPeran();
            
            rowsData[i][3] = peran.toString() != null ? peran : "Tidak diketahui";
            rowsData[i][4] = DateFormat.dateToString(riwayatPeranList.get(i).getTanggalMulai(), "dd-MM-yyyy");
          }
          
          Table.setModel(riwayatPeranTable, rowsData, fieldsData, false);
        } catch (RemoteException ex) {
          Logger.getLogger(PenggunaView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
      
      Table.setCellsHorizontalAlignment(riwayatPeranTable, new HashMap<Integer, Integer>(){{
        put(0, JLabel.CENTER);
      }});
      Table.setColumnWidths(riwayatPeranTable, 50);
      Table.removeColumns(riwayatPeranTable, 1);
    }
    
    private Pengguna getInputDataPengguna() {
      String nama = namaTextField.getText();
      String email = emailTextField.getText();
      Peran peran = 
        peranComboBox.getSelectedItem() instanceof String ? null : (Peran) peranComboBox.getSelectedItem();
      Cabang cabang =
        cabangComboBox.getSelectedItem() instanceof String ? null : (Cabang) cabangComboBox.getSelectedItem();
      String password = String.valueOf(passwordPasswordField.getPassword());
      
      Pengguna pengguna = new Pengguna();
      pengguna.setNama(nama);
      pengguna.setEmail(email);
      pengguna.setPeran(peran);
      pengguna.setCabang(cabang);
      pengguna.setPassword(password);
      
      return pengguna;
    }
    
    private boolean isDataPenggunaValid(Pengguna pengguna) {
      LinkedHashMap<HashMap<String, Object>, List<String>> data = 
        new LinkedHashMap<HashMap<String, Object>, List<String>>(){{
          put(
            new HashMap<String, Object>(){{ put("Nama", pengguna.getNama()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Email", pengguna.getEmail()); }},
            new ArrayList<String>(){{ add("REQUIRED"); add("EMAIL"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Peran", pengguna.getPeran()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Cabang", pengguna.getCabang()); }},
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

    jLabel1 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    riwayatPeranTable = new javax.swing.JTable();
    simpanButton = new javax.swing.JButton();
    updateButton = new javax.swing.JButton();
    hapusButton = new javax.swing.JButton();
    refreshButton = new javax.swing.JButton();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    namaTextField = new javax.swing.JTextField();
    jLabel6 = new javax.swing.JLabel();
    emailTextField = new javax.swing.JTextField();
    cabangComboBox = new javax.swing.JComboBox<>();
    peranComboBox = new javax.swing.JComboBox<>();
    menuUtamaButton = new javax.swing.JButton();
    keluarButton = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    jScrollPane2 = new javax.swing.JScrollPane();
    penggunaTable = new javax.swing.JTable();
    resetButton = new javax.swing.JButton();
    jLabel7 = new javax.swing.JLabel();
    passwordPasswordField = new javax.swing.JPasswordField();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jLabel1.setFont(new java.awt.Font("Bauhaus 93", 0, 18)); // NOI18N
    jLabel1.setText("Pengguna");

    riwayatPeranTable.setModel(new javax.swing.table.DefaultTableModel(
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
    riwayatPeranTable.setEnabled(false);
    riwayatPeranTable.setName("tabRiwayat"); // NOI18N
    jScrollPane1.setViewportView(riwayatPeranTable);

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
    refreshButton.setActionCommand("");
    refreshButton.setName("btnRefresh"); // NOI18N
    refreshButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        refreshButtonActionPerformed(evt);
      }
    });

    jLabel3.setText("Cabang");

    jLabel4.setText("Peran");

    jLabel5.setText("Nama");

    namaTextField.setName("txtNamaPengguna"); // NOI18N

    jLabel6.setText("E-mail");

    emailTextField.setName("txtEmail"); // NOI18N

    cabangComboBox.setName("cmbCabang"); // NOI18N

    peranComboBox.setName("cmbPeran"); // NOI18N

    menuUtamaButton.setText("Menu Utama");
    menuUtamaButton.setName("btnMenuUtama"); // NOI18N
    menuUtamaButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        menuUtamaButtonActionPerformed(evt);
      }
    });

    keluarButton.setText("Keluar");
    keluarButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        keluarButtonActionPerformed(evt);
      }
    });

    jLabel2.setText("Riwayat Peran");

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
    penggunaTable.setName("tabPengguna"); // NOI18N
    penggunaTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        penggunaTableMouseClicked(evt);
      }
    });
    jScrollPane2.setViewportView(penggunaTable);

    resetButton.setText("Reset");
    resetButton.setName("btnHapus"); // NOI18N
    resetButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        resetButtonActionPerformed(evt);
      }
    });

    jLabel7.setText("Password");

    passwordPasswordField.setEditable(false);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jScrollPane2)
          .addGroup(layout.createSequentialGroup()
            .addComponent(menuUtamaButton)
            .addGap(276, 276, 276)
            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(keluarButton))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel5)
              .addComponent(jLabel3)
              .addComponent(jLabel7)
              .addComponent(jLabel4)
              .addComponent(jLabel6))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                  .addComponent(emailTextField)
                  .addComponent(peranComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 242, Short.MAX_VALUE)
                  .addComponent(cabangComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addComponent(namaTextField)
                  .addComponent(passwordPasswordField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 517, javax.swing.GroupLayout.PREFERRED_SIZE)
                  .addComponent(jLabel2))))))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(menuUtamaButton)
          .addComponent(keluarButton))
        .addGap(15, 15, 15)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addGroup(layout.createSequentialGroup()
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(namaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(jLabel5))
            .addGap(10, 10, 10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(emailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(jLabel6))
            .addGap(10, 10, 10)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(peranComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(jLabel4))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(cabangComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(jLabel3))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel7)
              .addComponent(passwordPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(simpanButton)
          .addComponent(hapusButton)
          .addComponent(updateButton)
          .addComponent(resetButton)
          .addComponent(refreshButton))
        .addGap(18, 18, 18)
        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
      initPenggunaTableData();
      initRiwayatPeranTableData(-1);
    }//GEN-LAST:event_refreshButtonActionPerformed

  private void menuUtamaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuUtamaButtonActionPerformed
    new MenuUtamaView().setVisible(true);
    dispose();
  }//GEN-LAST:event_menuUtamaButtonActionPerformed

  private void keluarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keluarButtonActionPerformed
    ActiveUser.remove();
    new LoginView().setVisible(true);
    dispose();
  }//GEN-LAST:event_keluarButtonActionPerformed

  private void penggunaTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_penggunaTableMouseClicked
    int selectedRow = penggunaTable.getSelectedRow();
    
    namaTextField.setText(Table.getValue(penggunaTable, selectedRow, 2).toString());
    emailTextField.setText(Table.getValue(penggunaTable, selectedRow, 3).toString());
    
    Object peranObj = Table.getValue(penggunaTable, selectedRow, 4);
    
    if(peranObj instanceof String) peranComboBox.setSelectedIndex(0);
    else peranComboBox.getModel().setSelectedItem(peranObj);
    
    passwordPasswordField.setText(Table.getValue(penggunaTable, selectedRow, 6).toString());
    passwordPasswordField.setEditable(true);
    
    Object cabangObj = Table.getValue(penggunaTable, selectedRow, 5);
    
    if(cabangObj instanceof String) cabangComboBox.setSelectedIndex(0);
    else cabangComboBox.getModel().setSelectedItem(cabangObj);
    
    initRiwayatPeranTableData((int) Table.getValue(penggunaTable, selectedRow, 1));
  }//GEN-LAST:event_penggunaTableMouseClicked

  private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
    Pengguna pengguna = getInputDataPengguna();
    
    if(isDataPenggunaValid(pengguna)) {
      try {
        if(penggunaService.insert(pengguna)) {
          JOptionPane
            .showMessageDialog(this, "Pengguna berhasil ditambahkan.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initPenggunaTableData();
          
          try {
            int lastIdPengguna = penggunaService.getMaxIdPengguna();
            pengguna.setIdPengguna(lastIdPengguna);
            
            RiwayatPeran riwayatPeran = new RiwayatPeran();
            riwayatPeran.setIdRiwayatPeran(Types.NULL);
            riwayatPeran.setPengguna(pengguna);
            riwayatPeran.setPeran(pengguna.getPeran());
            riwayatPeran.setTanggalMulai(new Date());
            
            riwayatPeranService.insert(riwayatPeran);
          } catch (RemoteException ex) {
            Logger.getLogger(PenggunaView.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else {
          JOptionPane
            .showMessageDialog(this, "Pengguna gagal ditambahkan.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(PenggunaView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_simpanButtonActionPerformed

  private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
    Pengguna pengguna = getInputDataPengguna();
    int selectedRow = penggunaTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah pengguna terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      pengguna.setIdPengguna((int) Table.getValue(penggunaTable, selectedRow, 1));
      
      if(isDataPenggunaValid(pengguna)) {
        try {
          Pengguna prevPengguna = penggunaService.getByIdPengguna(pengguna.getIdPengguna());
          
          if(penggunaService.update(pengguna)) {
            JOptionPane
              .showMessageDialog(this, "Pengguna berhasil diupdate.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
            initInputData();
            initPenggunaTableData();
            
            if(prevPengguna.getPeran().getIdPeran() != pengguna.getPeran().getIdPeran()) {
              try {
                RiwayatPeran riwayatPeran = new RiwayatPeran();
                riwayatPeran.setIdRiwayatPeran(Types.NULL);
                riwayatPeran.setPengguna(pengguna);
                riwayatPeran.setPeran(pengguna.getPeran());
                riwayatPeran.setTanggalMulai(new Date());

                riwayatPeranService.insert(riwayatPeran);
              } catch (RemoteException ex) {
                Logger.getLogger(PenggunaView.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
          } else {
            JOptionPane
              .showMessageDialog(this, "Pengguna gagal diupdate.", "Oops!", JOptionPane.ERROR_MESSAGE);
          }
        } catch (RemoteException ex) {
          Logger.getLogger(PenggunaView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_updateButtonActionPerformed

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    initInputData();
    
    penggunaTable.getSelectionModel().clearSelection();
    Table.deleteAllRows(riwayatPeranTable);
  }//GEN-LAST:event_resetButtonActionPerformed

  private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
    int selectedRow = penggunaTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah pengguna terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      int idPengguna = (int) Table.getValue(penggunaTable, selectedRow, 1);
      
      try {
        if(penggunaService.deleteByIdPengguna(idPengguna)) {
          JOptionPane
            .showMessageDialog(this, "Pengguna berhasil dihapus.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initPenggunaTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Pengguna gagal dihapus.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(PenggunaView.class.getName()).log(Level.SEVERE, null, ex);
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
            java.util.logging.Logger.getLogger(PenggunaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PenggunaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PenggunaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PenggunaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PenggunaView().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JComboBox<Object> cabangComboBox;
  private javax.swing.JTextField emailTextField;
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
  private javax.swing.JButton keluarButton;
  private javax.swing.JButton menuUtamaButton;
  private javax.swing.JTextField namaTextField;
  private javax.swing.JPasswordField passwordPasswordField;
  private javax.swing.JTable penggunaTable;
  private javax.swing.JComboBox<Object> peranComboBox;
  private javax.swing.JButton refreshButton;
  private javax.swing.JButton resetButton;
  private javax.swing.JTable riwayatPeranTable;
  private javax.swing.JButton simpanButton;
  private javax.swing.JButton updateButton;
  // End of variables declaration//GEN-END:variables
}
