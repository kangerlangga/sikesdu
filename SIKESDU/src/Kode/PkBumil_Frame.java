package Kode;

import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.*;
/**
 *
 * @author SIKESDU Developer
 */
public class PkBumil_Frame extends javax.swing.JFrame {

    public PkBumil_Frame() {
        initComponents();
        tampilTabel();
        data_ibuhamil();
        setkolom();
    }
    
    DataKhusus khusus = new DataKhusus();
    String ambil_id, sql,id_bumil, idBaru;
    Connection con = (Connection)khusus.sambungDB();
    
    String formatTGL = "yyyy-MM-dd";
    SimpleDateFormat fm = new SimpleDateFormat(formatTGL);
    String tanggal_periksa;
    
    private void kosong(){
        txt_nik.setText("");
        combo_nama.setSelectedIndex(0);
        combo_nama.setEnabled(true);
        txt_keluhan.setText("");
        txt_tensi1.setText("");
        txt_tensi2.setText("");
        txt_BB.setText("");
        txt_usia_kehamilan.setText("");
        jDateChooser1.setDate(null);
        jDateChooser1.setEnabled(true);
        txt_usia_kehamilan.setText("");
        ambil_id = "";
        tampilTabel();
    }
    private void tampilTabel(){
    DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id");
        model.addColumn("Nama");
        model.addColumn("Usia Kehamilan");
        model.addColumn("Berat Badan");
        model.addColumn("Tensi Sistole");
        model.addColumn("Tensi diastole");
        model.addColumn("Tanggal");
        
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        model.setRowCount(0);
        try{
            sql = "SELECT perkembangan_ibuhamil.id_pk_ibuhamil, "
                    + "perkembangan_ibuhamil.nik_ibuhamil, data_ibuhamil.nama_ibuHamil , "
                    + "tgl_periksa, perkembangan_ibuhamil.berat_badan, "
                    + "perkembangan_ibuhamil.usia_kehamilan,tensi_sistole, "
                    + "tensi_diastole,keluhan FROM perkembangan_ibuhamil INNER JOIN data_ibuhamil "
                    + "ON perkembangan_ibuhamil.nik_ibuhamil = data_ibuhamil.nik_ibuhamil"
                    + " ORDER by DAYOFYEAR(tgl_periksa) DESC LIMIT 20;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                model.addRow(
                new Object[] {
                    rs.getString("id_pk_ibuhamil"),
                    rs.getString("nama_ibuHamil"), rs.getString("usia_kehamilan"), 
                    rs.getString("berat_badan"), rs.getString("tensi_sistole"), 
                    rs.getString("tensi_diastole"),rs.getString("tgl_periksa")
                }
                );
            }
            
            
            tbl_pk.setModel(model);
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void setkolom(){
         TableColumnModel col = tbl_pk.getColumnModel();
         col.getColumn(0).setPreferredWidth(150);
         col.getColumn(1).setPreferredWidth(150);
         col.getColumn(2).setPreferredWidth(100);
         col.getColumn(3).setPreferredWidth(150);
         col.getColumn(4).setPreferredWidth(150);
         col.getColumn(5).setPreferredWidth(150);
         col.getColumn(6).setPreferredWidth(150);
    }
    private void data_ibuhamil(){
        try{
            sql = "SELECT * FROM `data_ibuhamil`";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo_nama.addItem(rs.getString("nama_ibuHamil"));
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void tampil_idIbuhamil(){
        try{
            sql = "SELECT * FROM `data_ibuhamil` WHERE nama_ibuHamil='"+combo_nama.getSelectedItem()+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                txt_nik.setText(rs.getString("nik_ibuhamil"));
                id_bumil = rs.getString("nik_ibuhamil");
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    private void tambahData(){
        try{
            String id = id_bumil + idBaru;
            sql = "INSERT INTO `perkembangan_ibuhamil` (`id_pk_ibuhamil`, `nik_ibuhamil`, "
                    + " `tgl_periksa`, `keluhan`, `usia_kehamilan`,"
                    + " `tensi_sistole`, `tensi_diastole`, `berat_badan`, `waktu_pengisian`)"
                    + " VALUES ('"+id+"', '"+id_bumil+"', "
                    + "'"+tanggal_periksa+"', '"+txt_keluhan.getText()+"'"
                    + ", '"+txt_usia_kehamilan.getText()+"', '"+txt_tensi1.getText()+"', "
                    + "'"+txt_tensi2.getText()+"', '"+txt_BB.getText()+"', current_timestamp());";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Perkembangan Ibu Hamil Berhasil Ditambahkan");
            kosong();
            ps.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Perkembangan Ibu Hamil Tidak Dapat Ditambahkan "+e);
        }
    }
    private void hapusData(){
        try{
            sql = "DELETE FROM `perkembangan_ibuhamil` WHERE `id_pk_ibuhamil`='"+ambil_id+"';";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Perkembangan Ibu Hamil Berhasil Dihapus!");
            kosong();
            ps.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Perkembangan Ibu Hamil Tidak Dapat Dihapus "+e);
        }
    }
    private void ubahData(){
        try{
            sql = "UPDATE `perkembangan_ibuhamil` SET "
                + "`berat_badan` = '"+txt_BB.getText()+"', `keluhan` = '"
                    + ""+txt_keluhan.getText()+"' , `usia_kehamilan` = '"+txt_usia_kehamilan.getText()+"',tensi_sistole='"+txt_tensi1.getText()+"',"
              + "`tensi_diastole`='"+txt_tensi2.getText()+"' WHERE `perkembangan_ibuhamil`.`id_pk_ibuhamil` = '"+ambil_id+"';";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Perkembangan Ibu Hamil Berhasil Diubah!");
            kosong();
            ps.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Perkembangan Ibu Hamil Tidak Dapat Diubah "+e);
        }
    }
    private void buat_id(){
        try{
            sql = "SELECT * FROM `perkembangan_ibuhamil` WHERE "
                + "nik_ibuhamil='"+txt_nik.getText()+"' ORDER BY id_pk_ibuhamil DESC;";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                String id = r.getString("id_pk_ibuhamil").substring(16);
                String AN = ""+(Integer.parseInt(id)+1);
                String nol = "";
                
                if(AN.length()==1){
                    nol = "00";
                }else if(AN.length()==2){
                    nol = "0";
                }else if(AN.length()==3){
                    nol = "";
                }
                
                idBaru = nol+AN;
                if(idBaru.length()==3){
                    System.out.println("ID perkembangan = "+idBaru);
                    tambahData();
                }else{
                    System.out.println("ID Tidak Valid!");
                }
            }else{
                idBaru = "001";
                System.out.println("ID Perkembangan = "+idBaru);
                tambahData();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e + " buat id");
        }
    }
    private void cekDataGanda(){
        try{
            sql = "SELECT * FROM `perkembangan_ibuhamil` WHERE "
                + "nik_ibuhamil= '"+txt_nik.getText()+"' AND "
                + "tgl_periksa='"+tanggal_periksa+"';";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                JOptionPane.showMessageDialog(this, "Data Perkembangan Ibu Hamil Sudah Ada! \nID = "+r.getString("id_pk_ibuhamil"));
            }else{
                String[] opsi = {"YA" , "TIDAK"};
                int jawab = JOptionPane.showOptionDialog(this,
                    "Apakah Data Sudah Benar?" ,
                    "Tambah Data Perkembangan Ibu Hamil",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
                if (jawab == JOptionPane.YES_OPTION){
                    buat_id();
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e +"cek data");
        }
    }
    private void pilihData(){
        try{
            sql = "SELECT perkembangan_ibuhamil.id_pk_ibuhamil, "
                + "perkembangan_ibuhamil.nik_ibuhamil, data_ibuhamil.nama_ibuHamil, "
                + "perkembangan_ibuhamil.tgl_periksa, perkembangan_ibuhamil.berat_badan, "
                + "perkembangan_ibuhamil.usia_kehamilan, "
                + "perkembangan_ibuhamil.tensi_sistole, perkembangan_ibuhamil.tensi_diastole, "
                + "perkembangan_ibuhamil.keluhan FROM perkembangan_ibuhamil INNER JOIN "
                + "data_ibuhamil ON perkembangan_ibuhamil.nik_ibuhamil = data_ibuhamil.nik_ibuhamil "
                + "WHERE id_pk_ibuhamil = '"+ambil_id+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                ambil_id = rs.getString("id_pk_ibuhamil");
                txt_nik.setText(rs.getString("nik_ibuhamil"));
                txt_keluhan.setText(rs.getString("keluhan"));
                txt_tensi1.setText(rs.getString("tensi_sistole"));
                txt_tensi2.setText(rs.getString("tensi_diastole"));
                txt_usia_kehamilan.setText(rs.getString("usia_kehamilan"));
                txt_BB.setText(rs.getString("berat_badan"));
                combo_nama.setSelectedItem(rs.getString("nama_ibuHamil"));
                jDateChooser1.setDate(rs.getDate("tgl_periksa"));
                jDateChooser1.setEnabled(false);
                combo_nama.setEnabled(false);
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "error pilih data"+e);
        }
    }
    private void cekusiakehamilan(){
        try {
            sql = "SELECT * FROM `data_ibuhamil` WHERE `nik_ibuhamil`='"+txt_nik.getText()+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                int usia_kehamilan = Integer.parseInt(rs.getString("usia_kehamilan"));
                int usia_kehamilan1 = Integer.parseInt(txt_usia_kehamilan.getText());
                if (usia_kehamilan1 < usia_kehamilan) {
                    JOptionPane.showMessageDialog(this, "usia kehamilan tidak boleh lebih kecil. silahkan masukkan kembali");
                } else {
                    cekDataGanda();
                }
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(this, "error cek kahamilan"+e);
                
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

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_pk = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        btn_simpan = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_rekap = new javax.swing.JButton();
        txt_nik = new javax.swing.JTextField();
        txt_usia_kehamilan = new javax.swing.JTextField();
        txt_tensi2 = new javax.swing.JTextField();
        txt_keluhan = new javax.swing.JTextField();
        btn_kembali = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        txt_BB = new javax.swing.JTextField();
        txt_tensi1 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        combo_nama = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconapp.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setMaximumSize(new java.awt.Dimension(1000, 32767));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(900, 412));

        tbl_pk.setBackground(new java.awt.Color(217, 217, 217));
        tbl_pk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "id", "nama", "id", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        tbl_pk.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbl_pk.setMaximumSize(new java.awt.Dimension(1000, 80));
        tbl_pk.setPreferredSize(new java.awt.Dimension(1000, 300));
        tbl_pk.setSelectionBackground(new java.awt.Color(217, 217, 217));
        tbl_pk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pkMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_pk);
        if (tbl_pk.getColumnModel().getColumnCount() > 0) {
            tbl_pk.getColumnModel().getColumn(2).setPreferredWidth(20);
        }

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 410, 340));

        btn_simpan.setBackground(new java.awt.Color(34, 115, 235));
        btn_simpan.setFont(new java.awt.Font("Malgun Gothic", 1, 15)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        btn_simpan.setText("Simpan");
        btn_simpan.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        jPanel1.add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 440, 110, -1));

        btn_ubah.setBackground(new java.awt.Color(34, 115, 235));
        btn_ubah.setFont(new java.awt.Font("Malgun Gothic", 1, 15)); // NOI18N
        btn_ubah.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah.setText("Ubah");
        btn_ubah.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 440, -1, -1));

        btn_hapus.setBackground(new java.awt.Color(34, 115, 235));
        btn_hapus.setFont(new java.awt.Font("Malgun Gothic", 1, 15)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setText("Hapus");
        btn_hapus.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 440, -1, -1));

        btn_rekap.setBackground(new java.awt.Color(34, 115, 235));
        btn_rekap.setFont(new java.awt.Font("Malgun Gothic", 1, 15)); // NOI18N
        btn_rekap.setForeground(new java.awt.Color(255, 255, 255));
        btn_rekap.setText("Laporan");
        btn_rekap.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_rekap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_rekapActionPerformed(evt);
            }
        });
        jPanel1.add(btn_rekap, new org.netbeans.lib.awtextra.AbsoluteConstraints(745, 440, 120, -1));

        txt_nik.setEditable(false);
        txt_nik.setBackground(new java.awt.Color(217, 217, 217));
        txt_nik.setActionCommand("null");
        txt_nik.setBorder(null);
        txt_nik.setPreferredSize(new java.awt.Dimension(64, 37));
        jPanel1.add(txt_nik, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 240, -1));

        txt_usia_kehamilan.setBackground(new java.awt.Color(217, 217, 217));
        txt_usia_kehamilan.setBorder(null);
        txt_usia_kehamilan.setPreferredSize(new java.awt.Dimension(70, 35));
        jPanel1.add(txt_usia_kehamilan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 360, 70, -1));

        txt_tensi2.setBackground(new java.awt.Color(217, 217, 217));
        txt_tensi2.setBorder(null);
        txt_tensi2.setPreferredSize(new java.awt.Dimension(70, 37));
        jPanel1.add(txt_tensi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 50, -1));

        txt_keluhan.setBackground(new java.awt.Color(217, 217, 217));
        txt_keluhan.setBorder(null);
        txt_keluhan.setPreferredSize(new java.awt.Dimension(70, 37));
        jPanel1.add(txt_keluhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 247, 230, -1));

        btn_kembali.setBackground(new java.awt.Color(34, 115, 235));
        btn_kembali.setFont(new java.awt.Font("Malgun Gothic", 1, 15)); // NOI18N
        btn_kembali.setForeground(new java.awt.Color(255, 255, 255));
        btn_kembali.setText("Kembali");
        btn_kembali.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });
        jPanel1.add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 440, 100, -1));

        btn_batal.setBackground(new java.awt.Color(34, 115, 235));
        btn_batal.setFont(new java.awt.Font("Malgun Gothic", 1, 15)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal.setText("Batal");
        btn_batal.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });
        jPanel1.add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 440, -1, -1));

        txt_BB.setBackground(new java.awt.Color(217, 217, 217));
        txt_BB.setBorder(null);
        txt_BB.setPreferredSize(new java.awt.Dimension(70, 37));
        jPanel1.add(txt_BB, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 50, -1));

        txt_tensi1.setBackground(new java.awt.Color(217, 217, 217));
        txt_tensi1.setBorder(null);
        txt_tensi1.setPreferredSize(new java.awt.Dimension(70, 37));
        jPanel1.add(txt_tensi1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 60, -1));
        jPanel1.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 200, 260, 33));

        combo_nama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH" }));
        combo_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_namaActionPerformed(evt);
            }
        });
        jPanel1.add(combo_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 260, 40));

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel1.setText("Nama Ibu");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 16)); // NOI18N
        jLabel2.setText("Tanggal \nPeriksa");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 202, 140, -1));

        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel3.setText("Keluhan");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 253, -1, -1));

        jLabel4.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel4.setText("Tensi");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        jLabel5.setFont(new java.awt.Font("Malgun Gothic", 1, 16)); // NOI18N
        jLabel5.setText("Usia Kehamilan");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 362, 130, -1));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel6.setText("BB");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 362, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel7.setText("/");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 30, -1));

        jLabel8.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel8.setText("NIK");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon-minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, -10, 30, 30));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Design_PkBumil.png"))); // NOI18N
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 500));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_rekapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_rekapActionPerformed
        // TODO add your handling code here:
        this.dispose();
        PilihanLaporan_Frame laporan = new PilihanLaporan_Frame();
        laporan.setVisible(true);
    }//GEN-LAST:event_btn_rekapActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        kosong();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        // TODO add your handling code here:
        this.dispose();
        if(khusus.get_jabatan().equalsIgnoreCase("Bidan Posyandu")){
           DashboardBidan_Frame dbbidan= new DashboardBidan_Frame();
           dbbidan.setVisible(true);
        }else if(khusus.get_jabatan().equalsIgnoreCase("Kader Posyandu")){
           DashboardKader_Frame dbkader= new DashboardKader_Frame();
           dbkader.setVisible(true);
        }
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void combo_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_namaActionPerformed
        // TODO add your handling code here:
        if(combo_nama.getSelectedItem().equals("PILIH")){
            txt_nik.setText("");
        }else{
            tampil_idIbuhamil();
        }
    }//GEN-LAST:event_combo_namaActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        // TODO add your handling code here:
        tanggal_periksa = String.valueOf(fm.format(jDateChooser1.getDate()));
        if(jDateChooser1.getDate()==null || txt_keluhan.getText().equals("") || 
           txt_BB.getText().equals("") || txt_tensi1.getText().equals("") || 
           txt_tensi2.getText().equals("")|| txt_usia_kehamilan.getText().equals("") || combo_nama.getSelectedItem().equals("PILIH")){
            JOptionPane.showMessageDialog(this, "Seluruh kolom harus di terpenuhi!");
        }else{
           
            cekusiakehamilan();
            
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void tbl_pkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pkMouseClicked
        // TODO add your handling code here:
        ambil_id = tbl_pk.getValueAt(tbl_pk.getSelectedRow(), 0).toString();
        pilihData();
    }//GEN-LAST:event_tbl_pkMouseClicked

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        if(ambil_id.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Data yang akan Dihapus");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                        "Data Ibu Hamil Akan Diperbarui?" ,
                        "Perbarui Data Ibu Hamil",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
                    if (jawab == JOptionPane.YES_OPTION){
                        tanggal_periksa = String.valueOf(fm.format(jDateChooser1.getDate()));
                        ubahData();
                    }
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        if(ambil_id.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Data yang akan Dihapus");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                "Data Perkembangan IBu Hamil Akan Dihapus?" ,
                "Hapus Data Perkembangan Ibu Hamil",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opsi, opsi[1]);
            if (jawab == JOptionPane.YES_OPTION){
                hapusData();
            }
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

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
            java.util.logging.Logger.getLogger(PkBumil_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PkBumil_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PkBumil_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PkBumil_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PkBumil_Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_rekap;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JComboBox<String> combo_nama;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel minimize;
    private javax.swing.JTable tbl_pk;
    private javax.swing.JTextField txt_BB;
    private javax.swing.JTextField txt_keluhan;
    private javax.swing.JTextField txt_nik;
    private javax.swing.JTextField txt_tensi1;
    private javax.swing.JTextField txt_tensi2;
    private javax.swing.JTextField txt_usia_kehamilan;
    // End of variables declaration//GEN-END:variables
}
