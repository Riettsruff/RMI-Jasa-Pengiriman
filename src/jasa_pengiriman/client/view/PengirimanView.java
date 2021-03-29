package jasa_pengiriman.client.view;

import jasa_pengiriman.client.config.RMI;
import jasa_pengiriman.client.service.BasicValidation;
import jasa_pengiriman.client.service.CurrencyFormat;
import jasa_pengiriman.client.service.DateFormat;
import jasa_pengiriman.client.service.Table;
import jasa_pengiriman.client.store.ActiveUser;
import jasa_pengiriman.model.Biaya;
import jasa_pengiriman.model.Cabang;
import jasa_pengiriman.model.Kota;
import jasa_pengiriman.model.Pengguna;
import jasa_pengiriman.model.Pengiriman;
import jasa_pengiriman.model.Provinsi;
import jasa_pengiriman.server.service.BiayaService;
import jasa_pengiriman.server.service.KotaService;
import jasa_pengiriman.server.service.PenggunaService;
import jasa_pengiriman.server.service.PengirimanService;
import jasa_pengiriman.server.service.ProvinsiService;
import java.awt.event.ItemEvent;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class PengirimanView extends javax.swing.JFrame {
    ProvinsiService provinsiService;
    KotaService kotaService;
    PengirimanService pengirimanService;
    BiayaService biayaService;
    PenggunaService penggunaService;
            
    /**
     * Creates new form Pengiriman
     */
    public PengirimanView() {
      initRMIServices();
      initComponents();
      initInputData();
      initPengirimanTableData();
    }
    
    private void initRMIServices() {
      try {
        this.provinsiService = (ProvinsiService) RMI.getService("ProvinsiService");
        this.kotaService = (KotaService) RMI.getService("KotaService");
        this.pengirimanService = (PengirimanService) RMI.getService("PengirimanService");
        this.biayaService = (BiayaService) RMI.getService("BiayaService");
        this.penggunaService = (PenggunaService) RMI.getService("PenggunaService");
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Internal Server Error", "Oops!", JOptionPane.ERROR_MESSAGE);
        System.exit(1);
      }
    }
    
    private void initInputData() {
      noResiTextField.setText("RS" + DateFormat.dateToString(new Date(), "yyyyMMddHHmmss"));
      isiBarangTextField.setText("");
      beratTextField.setText("");
      biayaLabel.setText("Rp0,00");
      
      namaTextField.setText("");
      alamatTextArea.setText("");
      noHpTextField.setText("");
      
      provinsiComboBox.removeAllItems();
      provinsiComboBox.addItem("- Pilih -");
      provinsiComboBox.setSelectedIndex(0);
      
      kotaComboBox.removeAllItems();
      kotaComboBox.addItem("- Pilih -");
      kotaComboBox.setSelectedIndex(0);
      kotaComboBox.setEnabled(false);
      
      try {
        List<Provinsi> provinsiList = provinsiService.getAll();
        List<Kota> kotaList = kotaService.getAll();
        
        for(Provinsi provinsi : provinsiList) provinsiComboBox.addItem(provinsi);
        for(Kota kota : kotaList) kotaComboBox.addItem(kota);
      } catch (RemoteException ex) {
        Logger.getLogger(PengirimanView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private void initPengirimanTableData() {
      try {
        List<Pengiriman> pengirimanList = pengirimanService.getAll();
        String[] fieldsData = {"No.", "No. Resi", "Nama Penerima", "Provinsi Penerima", "Alamat Penerima", "No.Hp Penerima", "Isi Barang", "Berat Barang", "Kota Asal", "Kota Tujuan", "Biaya"};
        Object[][] rowsData = new Object[pengirimanList.size()][fieldsData.length];
        
        for(int i=0; i < pengirimanList.size(); ++i) {
          rowsData[i][0] = (i + 1) + ".";
          rowsData[i][1] = pengirimanList.get(i).getNoResi();
          rowsData[i][2] = pengirimanList.get(i).getNamaPenerima();
          
          Object cabangPengirimObj = pengirimanList.get(i).getCabangPengirim();
          Object kotaPengirimObj = cabangPengirimObj.toString() != null 
            ? pengirimanList.get(i).getCabangPengirim().getKota()
            : null;
          Object kotaPenerimaObj = pengirimanList.get(i).getKotaPenerima();
          
          rowsData[i][3] = 
            kotaPenerimaObj.toString() != null 
              ? pengirimanList.get(i).getKotaPenerima().getProvinsi().toString() != null 
                ? pengirimanList.get(i).getKotaPenerima().getProvinsi()
                : "Tidak diketahui"
              : "Tidak diketahui";
          
          rowsData[i][4] = pengirimanList.get(i).getAlamatPenerima();
          rowsData[i][5] = pengirimanList.get(i).getNoHpPenerima();
          rowsData[i][6] = pengirimanList.get(i).getIsiBarang();
          rowsData[i][7] = pengirimanList.get(i).getBeratBarang() + " kg";
          rowsData[i][8] = kotaPengirimObj != null ? kotaPengirimObj : "Tidak diketahui";
          rowsData[i][9] = kotaPenerimaObj.toString() != null ? kotaPenerimaObj : "Tidak diketahui";
          rowsData[i][10] = CurrencyFormat.getString(pengirimanList.get(i).getBiaya(), "in", "ID");
        }
        
        Table.setModel(pengirimanTable, rowsData, fieldsData, true);
        Table.setColumnWidths(pengirimanTable, 50);
        Table.setCellsHorizontalAlignment(pengirimanTable, new HashMap<Integer, Integer>(){{
          put(0, JLabel.CENTER);
          put(1, JLabel.CENTER);
        }});
        Table.removeColumns(pengirimanTable, 2, 2, 2, 2);
      } catch (RemoteException ex) {
        Logger.getLogger(PengirimanView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    private Pengiriman getInputDataPengiriman() {
      String noResi = noResiTextField.getText();
      String isiBarang = isiBarangTextField.getText();
      Double beratBarang;
      
      try {
        beratBarang = Double.parseDouble(beratTextField.getText());
      } catch(NumberFormatException ex) {
        beratBarang = beratTextField.getText().isEmpty() ? Double.MIN_VALUE : Double.MAX_VALUE;
      }
      
      Long biaya = 
        Long.parseLong(biayaLabel.getText().replace("Rp", "").replace(",00", "").replace(".", "").trim());
      
      String namaPenerima = namaTextField.getText();
      
      Provinsi provinsiPenerima = 
        provinsiComboBox.getSelectedItem() instanceof String
          ? null
          : (Provinsi) provinsiComboBox.getSelectedItem();
      Kota kotaPenerima = 
        kotaComboBox.getSelectedItem() instanceof String
          ? null
          : (Kota) kotaComboBox.getSelectedItem();
      
      String alamatPenerima = alamatTextArea.getText();
      String noHpPenerima = noHpTextField.getText();
      Timestamp waktuKirim = DateFormat.dateToTimestamp(new Date());
      
      Pengiriman pengiriman = new Pengiriman();
      pengiriman.setNoResi(noResi);
      pengiriman.setIsiBarang(isiBarang);
      pengiriman.setBeratBarang(beratBarang);
      pengiriman.setBiaya(biaya);
      pengiriman.setNamaPenerima(namaPenerima);
      pengiriman.setNoHpPenerima(noHpPenerima);
      pengiriman.setAlamatPenerima(alamatPenerima);
      pengiriman.setKotaPenerima(kotaPenerima);
      pengiriman.setWaktuKirim(waktuKirim);
      pengiriman.setCabangPengirim(ActiveUser.get().getCabang());
      
      return pengiriman;
    }
    
    private boolean isDataPengirimanValid(Pengiriman pengiriman) {
      LinkedHashMap<HashMap<String, Object>, List<String>> data = 
        new LinkedHashMap<HashMap<String, Object>, List<String>>(){{
          put(
            new HashMap<String, Object>(){{ put("Isi Barang", pengiriman.getIsiBarang()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ 
              put(
                "Berat Barang", 
                pengiriman.getBeratBarang() == Double.MIN_VALUE 
                  ? "" 
                  : pengiriman.getBeratBarang() == Double.MAX_VALUE 
                    ? "-" 
                    : pengiriman.getBeratBarang()); 
            }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Nama Penerima", pengiriman.getNamaPenerima()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Kota Penerima", pengiriman.getKotaPenerima()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("Alamat Penerima", pengiriman.getAlamatPenerima()); }},
            new ArrayList<String>(){{ add("REQUIRED"); }}
          );
          put(
            new HashMap<String, Object>(){{ put("No.Hp Penerima", pengiriman.getNoHpPenerima()); }},
            new ArrayList<String>(){{ add("REQUIRED"); add("NUMERIC"); }}
          );
        }};
      
      return BasicValidation.isValid(data);
    }
    
    private void updateBiayaLabel(boolean showErrorMsg) {
      Double berat;
      
      try {
        berat = Double.parseDouble(beratTextField.getText().trim());
      } catch(NumberFormatException ex) {
        berat = 0.0;
        biayaLabel.setText(CurrencyFormat.getString(0, "in", "ID"));
      }
      
      Kota kotaPenerima = 
        kotaComboBox.getSelectedItem() instanceof String 
          ? null
          : (Kota) kotaComboBox.getSelectedItem();
      
      if(kotaPenerima != null) {
        try {
          Pengguna pengirim = penggunaService.getByIdPengguna(ActiveUser.get().getIdPengguna());
          
          if(pengirim != null && pengirim.getCabang() != null ) {
            Biaya biaya = biayaService.getByRoute(
              pengirim.getCabang().getKota().getIdKota(), 
              kotaPenerima.getIdKota()
            );
            
            if(biaya != null) {
              biayaLabel.setText(CurrencyFormat.getString((long) (biaya.getHarga() * berat), "in", "ID"));
            } else {
              if(showErrorMsg) {
                JOptionPane.showMessageDialog(
                  this, 
                  "Biaya dari " + pengirim.getCabang().getKota().getNamaKota() + " ke " + kotaPenerima.getNamaKota() + " belum ditetapkan.", "Oops!", JOptionPane.ERROR_MESSAGE
                );
                kotaComboBox.setSelectedIndex(0);
              }
            }
          }
        } catch (RemoteException ex) {
          Logger.getLogger(PengirimanView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
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
    pengirimanTable = new javax.swing.JTable();
    simpanButton = new javax.swing.JButton();
    updateButton = new javax.swing.JButton();
    hapusButton = new javax.swing.JButton();
    jLabel2 = new javax.swing.JLabel();
    noResiTextField = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    isiBarangTextField = new javax.swing.JTextField();
    jLabel6 = new javax.swing.JLabel();
    jLabel8 = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    beratTextField = new javax.swing.JTextField();
    namaTextField = new javax.swing.JTextField();
    jLabel10 = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    noHpTextField = new javax.swing.JTextField();
    refreshButton = new javax.swing.JButton();
    jLabel13 = new javax.swing.JLabel();
    provinsiComboBox = new javax.swing.JComboBox<>();
    kotaComboBox = new javax.swing.JComboBox<>();
    menuUtamaButton = new javax.swing.JButton();
    keluarButton = new javax.swing.JButton();
    jLabel7 = new javax.swing.JLabel();
    jScrollPane2 = new javax.swing.JScrollPane();
    alamatTextArea = new javax.swing.JTextArea();
    biayaLabel = new javax.swing.JLabel();
    resetButton = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jLabel1.setFont(new java.awt.Font("Bauhaus 93", 0, 18)); // NOI18N
    jLabel1.setText("Pengiriman");

    pengirimanTable.setModel(new javax.swing.table.DefaultTableModel(
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
    pengirimanTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        pengirimanTableMouseClicked(evt);
      }
    });
    jScrollPane1.setViewportView(pengirimanTable);

    simpanButton.setText("Simpan");
    simpanButton.setName("txtSimpan"); // NOI18N
    simpanButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        simpanButtonActionPerformed(evt);
      }
    });

    updateButton.setText("Update");
    updateButton.setName("txtUpdate"); // NOI18N
    updateButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        updateButtonActionPerformed(evt);
      }
    });

    hapusButton.setText("Hapus");
    hapusButton.setName("txtHapus"); // NOI18N
    hapusButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        hapusButtonActionPerformed(evt);
      }
    });

    jLabel2.setText("No Resi");

    noResiTextField.setEditable(false);
    noResiTextField.setDisabledTextColor(new java.awt.Color(51, 51, 51));
    noResiTextField.setEnabled(false);

    jLabel3.setText("Provinsi");

    jLabel4.setText("Kota");

    jLabel5.setText("Isi Barang");

    jLabel6.setText("Berat");

    jLabel8.setText("Nama");

    jLabel9.setText("Alamat");

    beratTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        beratTextFieldKeyPressed(evt);
      }
      public void keyReleased(java.awt.event.KeyEvent evt) {
        beratTextFieldKeyReleased(evt);
      }
      public void keyTyped(java.awt.event.KeyEvent evt) {
        beratTextFieldKeyTyped(evt);
      }
    });

    jLabel10.setText("No. HP");

    jLabel11.setText("Biaya");

    refreshButton.setText("Refresh");
    refreshButton.setName("txtRefresh"); // NOI18N
    refreshButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        refreshButtonActionPerformed(evt);
      }
    });

    jLabel13.setText("kg");

    provinsiComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        provinsiComboBoxItemStateChanged(evt);
      }
    });

    kotaComboBox.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(java.awt.event.ItemEvent evt) {
        kotaComboBoxItemStateChanged(evt);
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
    jLabel7.setText("Informasi Penerima");
    jLabel7.setToolTipText("");

    alamatTextArea.setColumns(20);
    alamatTextArea.setRows(5);
    alamatTextArea.setName("txtAlamat"); // NOI18N
    jScrollPane2.setViewportView(alamatTextArea);

    biayaLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    biayaLabel.setText("Rp0,00");

    resetButton.setText("Reset");
    resetButton.setName("txtHapus"); // NOI18N
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
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(menuUtamaButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel1)
            .addGap(277, 277, 277)
            .addComponent(keluarButton))
          .addGroup(layout.createSequentialGroup()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel5)
              .addComponent(jLabel6)
              .addComponent(jLabel2)
              .addComponent(jLabel11))
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addComponent(beratTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13))
              .addComponent(isiBarangTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(noResiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(biayaLabel))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel8))
                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3)))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(provinsiComboBox, 0, 261, Short.MAX_VALUE)
                  .addComponent(namaTextField)))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(jLabel9)
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(jLabel10)
                    .addGap(18, 18, 18)
                    .addComponent(noHpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))
                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(jLabel4)
                    .addGap(18, 18, 18)
                    .addComponent(kotaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE))))))
          .addComponent(jScrollPane1)
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(simpanButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(updateButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(hapusButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(resetButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jLabel7)
        .addGap(198, 198, 198))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(18, 18, 18)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(menuUtamaButton)
          .addComponent(keluarButton))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addGap(74, 74, 74)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel2)
              .addComponent(noResiTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(16, 16, 16)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel5)
              .addComponent(isiBarangTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(12, 12, 12)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel6)
              .addComponent(jLabel13)
              .addComponent(beratTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(42, 42, 42)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addComponent(jLabel11))
              .addComponent(biayaLabel))
            .addGap(53, 53, 53)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(simpanButton)
              .addComponent(updateButton)
              .addComponent(hapusButton)
              .addComponent(resetButton)))
          .addGroup(layout.createSequentialGroup()
            .addGap(36, 36, 36)
            .addComponent(jLabel7)
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel8)
              .addComponent(namaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel3)
              .addComponent(provinsiComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(15, 15, 15)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel4)
              .addComponent(kotaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel9)
              .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(jLabel10)
              .addComponent(noHpTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addComponent(refreshButton)))
        .addGap(18, 18, 18)
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
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
    initPengirimanTableData();
  }//GEN-LAST:event_refreshButtonActionPerformed

  private void pengirimanTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pengirimanTableMouseClicked
    int selectedRow = pengirimanTable.getSelectedRow();
    
    noResiTextField.setText(Table.getValue(pengirimanTable, selectedRow, 1).toString());
    isiBarangTextField.setText(Table.getValue(pengirimanTable, selectedRow, 6).toString());
    beratTextField.setText(Table.getValue(pengirimanTable, selectedRow, 7).toString().replace("kg", "").trim());
    biayaLabel.setText(Table.getValue(pengirimanTable, selectedRow, 10).toString());
    namaTextField.setText(Table.getValue(pengirimanTable, selectedRow, 2).toString());
    
    Object provinsiObj = Table.getValue(pengirimanTable, selectedRow, 3);
    
    if(provinsiObj instanceof String) provinsiComboBox.setSelectedIndex(0);
    else provinsiComboBox.getModel().setSelectedItem(provinsiObj);
    
    Object kotaObj = Table.getValue(pengirimanTable, selectedRow, 9);
    
    if(kotaObj instanceof String) kotaComboBox.setSelectedIndex(0);
    else kotaComboBox.getModel().setSelectedItem(kotaObj);
    
    alamatTextArea.setText(Table.getValue(pengirimanTable, selectedRow, 4).toString());
    noHpTextField.setText(Table.getValue(pengirimanTable, selectedRow, 5).toString());
  }//GEN-LAST:event_pengirimanTableMouseClicked

  private void simpanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanButtonActionPerformed
    Pengiriman pengiriman = getInputDataPengiriman();
    
    if(isDataPengirimanValid(pengiriman)) {
      try {
        if(pengirimanService.insert(pengiriman)) {
          JOptionPane
            .showMessageDialog(this, "Pengiriman berhasil ditambahkan.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initPengirimanTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Pengiriman gagal ditambahkan.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(PengirimanView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_simpanButtonActionPerformed

  private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
    Pengiriman pengiriman = getInputDataPengiriman();
    int selectedRow = pengirimanTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah pengiriman terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      pengiriman.setNoResi(Table.getValue(pengirimanTable, selectedRow, 1).toString());
      
      if(isDataPengirimanValid(pengiriman)) {
        try {
          if(pengirimanService.update(pengiriman)) {
            JOptionPane
              .showMessageDialog(this, "Pengiriman berhasil diupdate.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
            initInputData();
            initPengirimanTableData();
          } else {
            JOptionPane
              .showMessageDialog(this, "Pengiriman gagal diupdate.", "Oops!", JOptionPane.ERROR_MESSAGE);
          }
        } catch (RemoteException ex) {
          Logger.getLogger(PengirimanView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_updateButtonActionPerformed

  private void hapusButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusButtonActionPerformed
    int selectedRow = pengirimanTable.getSelectedRow();
    
    if(selectedRow == -1) {
      JOptionPane
        .showMessageDialog(this, "Pilihlah salah pengiriman terlebih dahulu.", "Oops!", JOptionPane.ERROR_MESSAGE);
    } else {
      String noResi = Table.getValue(pengirimanTable, selectedRow, 1).toString();
      
      try {
        if(pengirimanService.deleteByNoResi(noResi)) {
          JOptionPane
            .showMessageDialog(this, "Pengiriman berhasil dihapus.", "Sukses!", JOptionPane.INFORMATION_MESSAGE);
          initInputData();
          initPengirimanTableData();
        } else {
          JOptionPane
            .showMessageDialog(this, "Pengiriman gagal dihapus.", "Oops!", JOptionPane.ERROR_MESSAGE);
        }
      } catch (RemoteException ex) {
        Logger.getLogger(PengirimanView.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  }//GEN-LAST:event_hapusButtonActionPerformed

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
    initInputData();
    
    pengirimanTable.getSelectionModel().clearSelection();
  }//GEN-LAST:event_resetButtonActionPerformed

  private void provinsiComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_provinsiComboBoxItemStateChanged
    if(evt.getStateChange() == ItemEvent.SELECTED) {
      kotaComboBox.removeAllItems();
      kotaComboBox.addItem("- Pilih -");
      kotaComboBox.setSelectedIndex(0);
      
      if(provinsiComboBox.getSelectedItem() instanceof String) {
        kotaComboBox.setEnabled(false);
      } else {
        try {
          Provinsi provinsi = (Provinsi) provinsiComboBox.getSelectedItem();
          List<Kota> kotaList = kotaService.getByIdProvinsi(provinsi.getIdProvinsi());
          
          kotaComboBox.setEnabled(true);
          
          for(Kota kota : kotaList) kotaComboBox.addItem(kota);
        } catch (RemoteException ex) {
          Logger.getLogger(PengirimanView.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
  }//GEN-LAST:event_provinsiComboBoxItemStateChanged

  private void beratTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beratTextFieldKeyPressed
    
  }//GEN-LAST:event_beratTextFieldKeyPressed

  private void beratTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beratTextFieldKeyTyped
    
  }//GEN-LAST:event_beratTextFieldKeyTyped

  private void beratTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beratTextFieldKeyReleased
    updateBiayaLabel(false);
  }//GEN-LAST:event_beratTextFieldKeyReleased

  private void kotaComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_kotaComboBoxItemStateChanged
    if(evt.getStateChange() == ItemEvent.SELECTED) updateBiayaLabel(true);
  }//GEN-LAST:event_kotaComboBoxItemStateChanged

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
            java.util.logging.Logger.getLogger(PengirimanView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PengirimanView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PengirimanView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PengirimanView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PengirimanView().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextArea alamatTextArea;
  private javax.swing.JTextField beratTextField;
  private javax.swing.JLabel biayaLabel;
  private javax.swing.JButton hapusButton;
  private javax.swing.JTextField isiBarangTextField;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel10;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel13;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JButton keluarButton;
  private javax.swing.JComboBox<Object> kotaComboBox;
  private javax.swing.JButton menuUtamaButton;
  private javax.swing.JTextField namaTextField;
  private javax.swing.JTextField noHpTextField;
  private javax.swing.JTextField noResiTextField;
  private javax.swing.JTable pengirimanTable;
  private javax.swing.JComboBox<Object> provinsiComboBox;
  private javax.swing.JButton refreshButton;
  private javax.swing.JButton resetButton;
  private javax.swing.JButton simpanButton;
  private javax.swing.JButton updateButton;
  // End of variables declaration//GEN-END:variables
}
