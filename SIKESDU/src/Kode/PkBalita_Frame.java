package Kode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.DefaultCellEditor;
import javax.swing.JOptionPane;
import javax.swing.table.*;

/**
 *
 * @author SIKESDU Developer
 */
public class PkBalita_Frame extends javax.swing.JFrame {

    public PkBalita_Frame() {
        initComponents();
        tampilTabel();
        data_balita();
        setkolom();
    }
    
    DataKhusus khusus = new DataKhusus();
    String ambil_id, sql, idBalita, idBaru;
    Connection con = (Connection)khusus.sambungDB();
    
    String formatTGL = "yyyy-MM-dd";
    SimpleDateFormat fm = new SimpleDateFormat(formatTGL);
    String tanggal_periksa;
    
    private void kosong(){
       txt_idBalita.setText("");
       combo_nama.setSelectedIndex(0);
       combo_nama.setEnabled(true);
       txt_bb.setText("");
       txt_tinggi.setText("");
       txt_kondisi.setText("");
       jDateChooser1.setDate(null);
       jDateChooser1.setEnabled(true);
       tampilTabel();
    }
    
    private void tampilTabel(){
    
    DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Id Perkembangan");
        model.addColumn("Nama Balita");
        model.addColumn("Id balita");
        model.addColumn("Berat Balita");
        model.addColumn("Tinggi Balita");
        model.addColumn("Tanggal");
        model.addColumn("Kondisi");
       
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        model.setRowCount(0);
       
        
        try{
            sql = "SELECT id_pk_balita,perkembangan_balita.id_balita, data_balita.nama_balita,"
                    + " tgl_periksa, berat_balita, tinggi_balita, kondisi_balita "
                    + "FROM `perkembangan_balita`INNER JOIN data_balita ON"
                    + " perkembangan_balita.id_balita = data_balita.id_balita ORDER by DAYOFYEAR(tgl_periksa) DESC LIMIT 20;";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                model.addRow(
                new Object[] {
                    rs.getString("id_pk_balita"),
                    rs.getString("nama_balita"), rs.getString("id_balita"), 
                    rs.getString("berat_balita"), rs.getString("tinggi_balita"), 
                    rs.getString("tgl_periksa"), rs.getString("kondisi_balita")
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
    private void data_balita(){
        try{
            sql = "SELECT * FROM `data_balita`";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                combo_nama.addItem(rs.getString("nama_balita"));
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    private void tampil_idBalita(){
        try{
            sql = "SELECT * FROM `data_balita` WHERE nama_balita='"+combo_nama.getSelectedItem()+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                txt_idBalita.setText(rs.getString("id_balita"));
                idBalita = rs.getString("id_balita");
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    private void tambahData(){
        try{
            String id = idBalita + idBaru;
            sql = "INSERT INTO `perkembangan_balita` (`id_pk_balita`, `id_balita`,"
                    + " `tgl_periksa`, `berat_balita`, `tinggi_balita`, `kondisi_balita`, `waktu_pengisian`) VALUES "
                    + "('"+id+"', '"+idBalita+"', "
                    + "'"+tanggal_periksa+"', '"+txt_bb.getText()+"', '"+txt_tinggi.getText()+"','"+txt_kondisi.getText()+"', current_timestamp());";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Perkembangan Balita Berhasil Ditambahkan");
            kosong();
            ps.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Perkembangan Balita Tidak Dapat Ditambahkan "+e);
        }
    }
    private void hapusData(){
        try{
            sql = "DELETE FROM `perkembangan_balita` WHERE `id_pk_balita`='"+ambil_id+"';";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Perkembangan Balita Berhasil Dihapus!");
            kosong();
            ps.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Perkembangan Balita Tidak Dapat Dihapus "+e);
        }
    }
    private void ubahData(){
        try{
            sql = "UPDATE `perkembangan_balita` SET "
                + "`berat_balita` = '"+txt_bb.getText()+"', `tinggi_balita` = '"
                    + ""+txt_tinggi.getText()+"', `kondisi_balita` = '"
                    + ""+txt_kondisi.getText()+"' WHERE `perkembangan_balita`.`id_pk_balita` = '"+ambil_id+"';";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Perkembangan Balita Berhasil Diubah!");
            kosong();
            ps.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Perkembangan Balita Tidak Dapat Diubah "+e);
        }
    }
    private void buat_id(){
        try{
            sql = "SELECT * FROM `perkembangan_balita` WHERE "
                + "id_balita='"+txt_idBalita.getText()+"' "
                + "ORDER BY id_pk_balita DESC;";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                String id = r.getString("id_pk_balita").substring(8);
                String AN = ""+(Integer.parseInt(id)+1);
                String nol = "";
                
                if(AN.length()==1){
                    nol = "0";
                }else if(AN.length()==2){
                    nol = "";
                }
                idBaru = nol+AN;
                if(idBaru.length()==2){
                    System.out.println("ID perkembangan = "+idBaru);
                    tambahData();
                }else{
                    System.out.println("ID Tidak Valid!");
                }
            }else{
                idBaru = "01";
                System.out.println("ID Perkembangan = "+idBaru);
                tambahData();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e + " buat id");
        }
    }
    private void pilihData(){
        try{
            sql = "SELECT id_pk_balita, " +
                  "perkembangan_balita.id_balita, data_balita.nama_balita, " +
                  "tgl_periksa, berat_balita, tinggi_balita, kondisi_balita " +
                  "FROM `perkembangan_balita` " +
                  "INNER JOIN data_balita ON perkembangan_balita.id_balita = data_balita.id_balita " +
                   "WHERE id_pk_balita='"+ambil_id+"';";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if(rs.next()){
                ambil_id = rs.getString("id_pk_balita");
                txt_idBalita.setText(rs.getString("id_balita"));
                txt_bb.setText(rs.getString("berat_balita"));
                txt_tinggi.setText(rs.getString("tinggi_balita"));
                txt_kondisi.setText(rs.getString("kondisi_balita"));
                combo_nama.setSelectedItem(rs.getString("nama_balita"));
                jDateChooser1.setDate(rs.getDate("tgl_periksa"));
                jDateChooser1.setEnabled(false);
                combo_nama.setEnabled(false);
                
            }
            st.close();
            rs.close();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    private void cekDataGanda(){
        try{
            sql = "SELECT * FROM `perkembangan_balita` WHERE "
                + "id_balita='"+txt_idBalita.getText()+"' AND "
                + "tgl_periksa='"+tanggal_periksa+"';";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                JOptionPane.showMessageDialog(this, "Data Perkembangan Balita Sudah Ada! \nID = "+r.getString("id_balita"));
            }else{
                String[] opsi = {"YA" , "TIDAK"};
                int jawab = JOptionPane.showOptionDialog(this,
                    "Apakah Data Sudah Benar?" ,
                    "Tambah Data Perkembangan Balita",
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
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btn_simpan = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_rekap = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        txt_idBalita = new javax.swing.JTextField();
        txt_tinggi = new javax.swing.JTextField();
        txt_bb = new javax.swing.JTextField();
        btn_kembali = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        combo_nama = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_kondisi = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_pk = new javax.swing.JTable();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconapp.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jPanel1.add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 420, 110, -1));

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
        jPanel1.add(btn_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(155, 420, -1, -1));

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
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, -1, -1));

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
        jPanel1.add(btn_rekap, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 430, 110, -1));
        jPanel1.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 190, 250, 40));

        txt_idBalita.setEditable(false);
        txt_idBalita.setBackground(new java.awt.Color(217, 217, 217));
        txt_idBalita.setActionCommand("null");
        txt_idBalita.setBorder(null);
        txt_idBalita.setPreferredSize(new java.awt.Dimension(64, 37));
        jPanel1.add(txt_idBalita, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 230, -1));

        txt_tinggi.setBackground(new java.awt.Color(217, 217, 217));
        txt_tinggi.setBorder(null);
        txt_tinggi.setPreferredSize(new java.awt.Dimension(70, 35));
        jPanel1.add(txt_tinggi, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 245, 180, -1));

        txt_bb.setBackground(new java.awt.Color(217, 217, 217));
        txt_bb.setBorder(null);
        txt_bb.setPreferredSize(new java.awt.Dimension(70, 37));
        jPanel1.add(txt_bb, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 302, 180, -1));

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
        jPanel1.add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 430, 100, -1));

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
        jPanel1.add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(345, 420, -1, -1));

        combo_nama.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH" }));
        combo_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_namaActionPerformed(evt);
            }
        });
        jPanel1.add(combo_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 148, 250, 33));

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel1.setText("Nama ");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, -1));

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel2.setText("Tinggi Badan");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 254, -1, -1));

        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel3.setText("Tanggal Periksa");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        jLabel4.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel4.setText("Kondisi Balita");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        jLabel5.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel5.setText("ID Balita");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        txt_kondisi.setBackground(new java.awt.Color(217, 217, 217));
        txt_kondisi.setBorder(null);
        jPanel1.add(txt_kondisi, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 360, 230, 40));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel6.setText("Berat Badan");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon-minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, -10, 30, 30));

        jScrollPane2.setPreferredSize(new java.awt.Dimension(1000, 412));

        tbl_pk.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7"
            }
        ));
        tbl_pk.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbl_pk.setMaximumSize(new java.awt.Dimension(2147, 80));
        tbl_pk.setPreferredSize(new java.awt.Dimension(1000, 80));
        tbl_pk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_pkMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_pk);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 80, 390, 320));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Design_PkBalita.png"))); // NOI18N
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

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        // TODO add your handling code here:this.dispose();
        this.dispose();
        if(khusus.get_jabatan().equalsIgnoreCase("Bidan Posyandu")){
           DashboardBidan_Frame dbbidan= new DashboardBidan_Frame();
           dbbidan.setVisible(true);
        }else if(khusus.get_jabatan().equalsIgnoreCase("Kader Posyandu")){
           DashboardKader_Frame dbkader= new DashboardKader_Frame();
           dbkader.setVisible(true);
        }
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        kosong();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        tanggal_periksa = String.valueOf(fm.format(jDateChooser1.getDate()));
        if(jDateChooser1.getDate()==null || txt_bb.getText().equals("") || 
           txt_tinggi.getText().equals("") || combo_nama.getSelectedItem().equals("PILIH")){
            JOptionPane.showMessageDialog(this, "Seluruh kolom harus di terpenuhi!");
        }else{
           
            cekDataGanda();
            
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void combo_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_namaActionPerformed
        // TODO add your handling code here:
         if(combo_nama.getSelectedItem().equals("PILIH")){
            txt_idBalita.setText("");
        }else{
            tampil_idBalita();
        }
    }//GEN-LAST:event_combo_namaActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        if(ambil_id.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Data yang akan Dihapus");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                        "Data Perkembangan Balita Akan Diperbarui?" ,
                        "Perbarui Data Perkembangan Balita",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
                    if (jawab == JOptionPane.YES_OPTION){
                        tanggal_periksa = String.valueOf(fm.format(jDateChooser1.getDate()));
                        ubahData();
                    }
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        if(ambil_id.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Data yang akan Dihapus");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                "Data Perkembangan Balita Akan Dihapus?" ,
                "Hapus Data Perkembangan Balita",
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

    private void tbl_pkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_pkMouseClicked
        // TODO add your handling code here:
        ambil_id = tbl_pk.getValueAt(tbl_pk.getSelectedRow(), 0).toString();
        pilihData();
    }//GEN-LAST:event_tbl_pkMouseClicked

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
            java.util.logging.Logger.getLogger(PkBalita_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PkBalita_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PkBalita_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PkBalita_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new PkBalita_Frame().setVisible(true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel minimize;
    private javax.swing.JTable tbl_pk;
    private javax.swing.JTextField txt_bb;
    private javax.swing.JTextField txt_idBalita;
    private javax.swing.JTextField txt_kondisi;
    private javax.swing.JTextField txt_tinggi;
    // End of variables declaration//GEN-END:variables
}
