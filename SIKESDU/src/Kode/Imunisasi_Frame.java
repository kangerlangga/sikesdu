package Kode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SIKESDU Developer
 */
public class Imunisasi_Frame extends javax.swing.JFrame {

    public Imunisasi_Frame() {
        initComponents();
        reset();
        data_balita();
        data_vaksin();
        data_vaksinator();
    }
    
    DataKhusus khusus = new DataKhusus();
    String ambil_id, sql, idBalita, idVaksin, idVaksinator, ambil_nama, ambil_vaksin;
    Connection con = (Connection)khusus.sambungDB();
    
    String formatTGL = "yyyy-MM-dd";
    SimpleDateFormat fm = new SimpleDateFormat(formatTGL);
    String tanggal_vaksin;
    
    private void reset(){
        txt_nama.setText("");
        combo_id.setSelectedIndex(0);
        date_vaksin.setDate(null);
        combo_jenisVaksin.setSelectedIndex(0);
        txt_kondisi.setText("");
        combo_vaksinator.setSelectedIndex(0);
        tanggal_vaksin = "";
        ambil_id = "";
        ambil_nama = "";
        ambil_vaksin = "";
        sql = "";
        idBalita = "";
        idVaksin = "";
        idVaksinator = "";
        tampilTabel();
        enableKolom();
    }
    
    private void cekDataGanda(){
        try{
            sql = "SELECT * FROM `imunisasi_balita` WHERE id_balita='"+idBalita+"' AND id_vaksin='"+idVaksin+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                JOptionPane.showMessageDialog(this, "Data Sudah Ada!");
            }else{
                String[] opsi = {"YA" , "TIDAK"};
                int jawab = JOptionPane.showOptionDialog(this,
                    "Apakah Data Sudah Benar?" ,
                    "Tambah Data Imunisasi",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
                if (jawab == JOptionPane.YES_OPTION){
                    tambahData();
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }
    
    private void tampilTabel(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Balita");
        model.addColumn("Jenis Vaksin");
        model.addColumn("Vaksinator");
        model.addColumn("Kondisi Tubuh");
        model.addColumn("Tanggal");
        
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        model.setRowCount(0);
        try{
            sql = "SELECT id_imunBalita, " +
                  "imunisasi_balita.id_balita, data_balita.nama_balita, " +
                  "imunisasi_balita.id_vaksin, data_imunisasi.nama_vaksin, " +
                  "imunisasi_balita.id_vaksinator, user.nama_lengkap, " +
                  "tgl_imunisasi, kondisi_balita " +
                  "FROM `imunisasi_balita` " +
                  "INNER JOIN data_balita ON imunisasi_balita.id_balita = data_balita.id_balita " +
                  "INNER JOIN data_imunisasi ON imunisasi_balita.id_vaksin = data_imunisasi.id_imunisasi " +
                  "INNER JOIN user ON imunisasi_balita.id_vaksinator = user.id_pegawai " +
                  "ORDER by DAYOFYEAR(tgl_imunisasi) DESC;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                model.addRow(
                new Object[] {
                    rs.getString("nama_balita"), rs.getString("nama_vaksin"), 
                    rs.getString("nama_lengkap"), rs.getString("kondisi_balita"), 
                    rs.getString("tgl_imunisasi")
                }
                );
            }
            tabel_imunisasi.setModel(model);
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void data_balita(){
        try{
            sql = "SELECT * FROM `data_balita`";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo_id.addItem(rs.getString("id_balita"));
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void data_vaksin(){
        try{
            sql = "SELECT * FROM `data_imunisasi`";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo_jenisVaksin.addItem(rs.getString("nama_vaksin"));
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void data_vaksinator(){
        try{
            sql = "SELECT * FROM `user` WHERE jabatan='Bidan Posyandu';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo_vaksinator.addItem(rs.getString("nama_lengkap"));
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void tampil_namaBalita(){
        try{
            sql = "SELECT * FROM `data_balita` WHERE id_balita='"+combo_id.getSelectedItem()+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                txt_nama.setText(rs.getString("nama_balita"));
                idBalita = rs.getString("id_balita");
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void ambil_idVaksin(){
        try{
            sql = "SELECT * FROM `data_imunisasi` WHERE nama_vaksin='"+combo_jenisVaksin.getSelectedItem()+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                idVaksin = rs.getString("id_imunisasi");
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void ambil_idVaksinator(){
        try{
            sql = "SELECT * FROM `user` WHERE nama_lengkap='"+combo_vaksinator.getSelectedItem()+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                idVaksinator = rs.getString("id_pegawai");
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void tambahData(){
        try{
            tanggal_vaksin = String.valueOf(fm.format(date_vaksin.getDate()));
            String id = idBalita + idVaksin;
            sql = "INSERT INTO `imunisasi_balita` "
                + "(`id_imunBalita`, `id_balita`, `id_vaksin`, `id_vaksinator`, `tgl_imunisasi`, `kondisi_balita`, `waktu_pengisian`) "
                + "VALUES "+ "('"+id+"', '"+idBalita+"', '"+idVaksin+"', '"+idVaksinator+"', '"+tanggal_vaksin+"', "
                + "'"+txt_kondisi.getText()+"', current_timestamp());";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Imunisasi Berhasil Ditambahkan");
            reset();
            ps.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Imunisasi Tidak Dapat Ditambahkan "+e);
        }
    }
    
    private void hapusData(){
        try{
            sql = "DELETE FROM `imunisasi_balita` WHERE `id_imunBalita`='"+ambil_id+"';";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Imunisasi Berhasil Dihapus!");
            reset();
            ps.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Imunisasi Tidak Dapat Dihapus "+e);
        }
    }
    
    private void pilihData(){
        try{
            sql = "SELECT id_imunBalita, " +
                  "imunisasi_balita.id_balita, data_balita.nama_balita, " +
                  "imunisasi_balita.id_vaksin, data_imunisasi.nama_vaksin, " +
                  "imunisasi_balita.id_vaksinator, user.nama_lengkap, " +
                  "tgl_imunisasi, kondisi_balita " +
                  "FROM `imunisasi_balita` " +
                  "INNER JOIN data_balita ON imunisasi_balita.id_balita = data_balita.id_balita " +
                  "INNER JOIN data_imunisasi ON imunisasi_balita.id_vaksin = data_imunisasi.id_imunisasi " +
                  "INNER JOIN user ON imunisasi_balita.id_vaksinator = user.id_pegawai " +
                  "WHERE nama_balita='"+ambil_nama+"' AND nama_vaksin='"+ambil_vaksin+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                ambil_id = rs.getString("id_imunBalita");
                txt_nama.setText(rs.getString("nama_balita"));
                txt_kondisi.setText(rs.getString("kondisi_balita"));
                combo_id.setSelectedItem(rs.getString("id_balita"));
                combo_jenisVaksin.setSelectedItem(rs.getString("nama_vaksin"));
                combo_vaksinator.setSelectedItem(rs.getString("nama_lengkap"));
                date_vaksin.setDate(rs.getDate("tgl_imunisasi"));
                disableKolom();
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void disableKolom(){
        combo_id.setEnabled(false);
        combo_jenisVaksin.setEnabled(false);
        combo_vaksinator.setEnabled(false);
        date_vaksin.setEnabled(false);
        txt_kondisi.setEditable(false);
    }
    
    private void enableKolom(){
        combo_id.setEnabled(true);
        combo_jenisVaksin.setEnabled(true);
        combo_vaksinator.setEnabled(true);
        date_vaksin.setEnabled(true);
        txt_kondisi.setEditable(true);
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        combo_vaksinator = new javax.swing.JComboBox<>();
        combo_id = new javax.swing.JComboBox<>();
        combo_jenisVaksin = new javax.swing.JComboBox<>();
        date_vaksin = new com.toedter.calendar.JDateChooser();
        btn_kembali = new javax.swing.JButton();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        txt_kondisi = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_imunisasi = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        minimize = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconapp.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nama Vaksinator");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 150, 30));

        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Balita");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 120, 30));

        jLabel4.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ID Balita");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 120, 30));

        jLabel5.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Tanggal Vaksinasi");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 160, 30));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Jenis Imunisasi");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, 120, 30));

        jLabel7.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Kondisi Tubuh");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 170, 120, 30));

        txt_nama.setEditable(false);
        txt_nama.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel1.add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 300, 30));

        combo_vaksinator.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combo_vaksinator.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH" }));
        combo_vaksinator.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_vaksinatorActionPerformed(evt);
            }
        });
        jPanel1.add(combo_vaksinator, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, 300, 30));

        combo_id.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combo_id.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH" }));
        combo_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_idActionPerformed(evt);
            }
        });
        jPanel1.add(combo_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 300, 30));

        combo_jenisVaksin.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combo_jenisVaksin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH" }));
        combo_jenisVaksin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_jenisVaksinActionPerformed(evt);
            }
        });
        jPanel1.add(combo_jenisVaksin, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 130, 300, 30));

        date_vaksin.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel1.add(date_vaksin, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 300, 30));

        btn_kembali.setBackground(new java.awt.Color(0, 51, 102));
        btn_kembali.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        btn_kembali.setForeground(new java.awt.Color(255, 255, 255));
        btn_kembali.setText("KEMBALI");
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });
        jPanel1.add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 320, 140, 30));

        btn_tambah.setBackground(new java.awt.Color(0, 51, 102));
        btn_tambah.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setText("TAMBAH");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 320, 140, 30));

        btn_hapus.setBackground(new java.awt.Color(0, 51, 102));
        btn_hapus.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setText("HAPUS");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 140, 30));

        btn_batal.setBackground(new java.awt.Color(0, 51, 102));
        btn_batal.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal.setText("BATAL");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });
        jPanel1.add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 320, 140, 30));

        txt_kondisi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jPanel1.add(txt_kondisi, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 200, 300, 30));

        tabel_imunisasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Nama Balita", "Jenis Vaksin", "Vaksinator", "Kondisi Tubuh", "Tanggal"
            }
        ));
        tabel_imunisasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_imunisasiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_imunisasi);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 650, 110));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon-minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, -10, 30, 30));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Design_ImunisasiFrame.jpg"))); // NOI18N
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 500));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 500));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        reset();
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

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void combo_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_idActionPerformed
        // TODO add your handling code here:
        if(combo_id.getSelectedItem().equals("PILIH")){
            txt_nama.setText("");
        }else{
            tampil_namaBalita();
        }
    }//GEN-LAST:event_combo_idActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        if(date_vaksin.getDate()==null || txt_kondisi.getText().equals("") || 
           txt_nama.getText().equals("") || combo_jenisVaksin.getSelectedItem().equals("PILIH") || 
           combo_vaksinator.getSelectedItem().equals("PILIH") || combo_id.getSelectedItem().equals("PILIH")){
            JOptionPane.showMessageDialog(this, "Seluruh kolom harus di terpenuhi!");
        }else{
            cekDataGanda();
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void combo_jenisVaksinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_jenisVaksinActionPerformed
        // TODO add your handling code here:
        if(combo_jenisVaksin.getSelectedItem().equals("PILIH")){
            idVaksin = "";
        }else{
            ambil_idVaksin();
        }
    }//GEN-LAST:event_combo_jenisVaksinActionPerformed

    private void combo_vaksinatorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_vaksinatorActionPerformed
        // TODO add your handling code here:
        if(combo_vaksinator.getSelectedItem().equals("PILIH")){
            idVaksinator = "";
        }else{
            ambil_idVaksinator();
        }
    }//GEN-LAST:event_combo_vaksinatorActionPerformed

    private void tabel_imunisasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_imunisasiMouseClicked
        // TODO add your handling code here:
        ambil_nama = tabel_imunisasi.getValueAt(tabel_imunisasi.getSelectedRow(), 0).toString();
        ambil_vaksin = tabel_imunisasi.getValueAt(tabel_imunisasi.getSelectedRow(), 1).toString();
        pilihData();
    }//GEN-LAST:event_tabel_imunisasiMouseClicked

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        if(ambil_id.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Data yang akan Dihapus");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                "Data Imunisasi Akan Dihapus?" ,
                "Hapus Data Imunisasi",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opsi, opsi[1]);
            if (jawab == JOptionPane.YES_OPTION){
                hapusData();
            }
        }
    }//GEN-LAST:event_btn_hapusActionPerformed
    
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
            java.util.logging.Logger.getLogger(Imunisasi_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Imunisasi_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Imunisasi_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Imunisasi_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Imunisasi_Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JComboBox<String> combo_id;
    private javax.swing.JComboBox<String> combo_jenisVaksin;
    private javax.swing.JComboBox<String> combo_vaksinator;
    private com.toedter.calendar.JDateChooser date_vaksin;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel minimize;
    private javax.swing.JTable tabel_imunisasi;
    private javax.swing.JTextField txt_kondisi;
    private javax.swing.JTextField txt_nama;
    // End of variables declaration//GEN-END:variables
}
