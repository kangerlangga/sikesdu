package Kode;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SIKESDU Developer
 */

public class ManajemenUser_Frame extends javax.swing.JFrame {
    DataKhusus khusus = new DataKhusus();
    String ambil_id, sql, kode_jabatan, jabatan, idBaru, pass_default, id_kartu;
 
    public ManajemenUser_Frame() {
        initComponents();
        reset();
    }
    
    public void reset(){
        txt_nama.setText("");
        txt_tlp.setText("");
        txt_rfid.setText("");
        box_jabatan.setEnabled(true);
        box_jabatan.setSelectedIndex(0);
        txt_rfid.setEditable(true);
        ambil_id = "";
        idBaru = "";
        kode_jabatan = "";
        pass_default = "";
        id_kartu = "";
        TampilData();
    }
    
    private void TampilData(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID Pegawai");
        model.addColumn("Nama Pegawai");
        model.addColumn("Telepon");
        model.addColumn("Jabatan");
        model.addColumn("ID Kartu");
        
        
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        model.setRowCount(0);
        try{
            sql = "SELECT * FROM `user`";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                model.addRow(
                new Object[] {
                    rs.getString("id_pegawai"), rs.getString("nama_lengkap"),
                    rs.getString("telpon"), rs.getString("jabatan"), rs.getString("id_kartu")
                }
                );
            }
            tabel_user.setModel(model);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void pilihData(){
        try{
            sql = "SELECT * FROM `user` WHERE id_pegawai='"+ambil_id+"';";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                txt_nama.setText(r.getString("nama_lengkap"));
                txt_tlp.setText(r.getString("telpon"));
                txt_rfid.setText(r.getString("id_kartu"));
                box_jabatan.setSelectedItem(r.getString("jabatan"));
                box_jabatan.setEnabled(false);
                txt_rfid.setEditable(false);
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void buat_id(){
        try{
            sql = "SELECT * FROM `user` WHERE id_pegawai LIKE '"+kode_jabatan+"%' ORDER BY id_pegawai DESC;";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                String id = r.getString("id_pegawai").substring(5);
                String AN = ""+(Integer.parseInt(id)+1);
                String nol = "";
                
                if(AN.length()==1){
                    nol = "0";
                }else if(AN.length()==2){
                    nol = "";
                }
                idBaru = kode_jabatan+khusus.get_nomorPos()+nol+AN;
                if(idBaru.length()==7){
                    System.out.println("ID = "+idBaru);
                    tambahUser();
                }else{
                    System.out.println("ID Tidak Valid!");
                }
            }else{
                idBaru = kode_jabatan+khusus.get_nomorPos()+"01";
                System.out.println("ID = "+idBaru);
                tambahUser();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void tambahUser(){
        try{
            if(txt_rfid.getText().length()==0 || txt_rfid.getText().equals("")){
                id_kartu = "NULL";
            }else{
                id_kartu = "MD5('"+txt_rfid.getText()+"')";
            }
            sql = "INSERT INTO `user` "
                    + "(`id_pegawai`, `nama_lengkap`, `password`, `jabatan`, `telpon`, `id_kartu`) "
                    + "VALUES ('"+idBaru+"', '"+txt_nama.getText()+"', "
                    + "MD5('POS"+khusus.get_nomorPos()+"*"+idBaru+"'), '"+box_jabatan.getSelectedItem()+"', "
                    + "'"+txt_tlp.getText()+"', "+id_kartu+");";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "User Berhasil Ditambahkan \nID = "+idBaru);
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "User Tidak Dapat Ditambahkan "+e);
        }
    }
    
    private void hapusUser(){
        try{
            sql = "DELETE FROM `user` WHERE `id_pegawai`='"+ambil_id+"';";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "User Berhasil Dihapus!");
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "User Tidak Dapat Dihapus "+e);
        }
    }
    
    private void editUser(){
        try{
            sql = "UPDATE `user` SET "
                    + "`nama_lengkap` = '"+txt_nama.getText()+"', "
                    + "`telpon` = '"+txt_tlp.getText()+"' "
                    + "WHERE `user`.`id_pegawai` = '"+ambil_id+"';";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data User Berhasil Diubah!");
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data User Gagal Diubah "+e);
        }
    }
    
    private void resetPass(){
        try{
            pass_default = "POS"+khusus.get_nomorPos()+"*"+ambil_id;
            sql = "UPDATE `user` SET "
                    + "`password` = MD5('"+pass_default+"') "
                    + "WHERE `user`.`id_pegawai` = '"+ambil_id+"';";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Password User Berhasil Direset!");
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Password Gagal Direset "+e);
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
        tabel_user = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        box_jabatan = new javax.swing.JComboBox<>();
        btn_tambah = new javax.swing.JButton();
        btn_batal = new javax.swing.JButton();
        btn_resetPassword = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        label_tlp = new javax.swing.JLabel();
        label_jabatan = new javax.swing.JLabel();
        txt_nama = new javax.swing.JTextField();
        btn_kembali = new javax.swing.JLabel();
        txt_tlp = new javax.swing.JTextField();
        label_nama1 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        label_tlp1 = new javax.swing.JLabel();
        txt_rfid = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconapp.png")).getImage());
        setUndecorated(true);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabel_user.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Pegawai", "Nama Lengkap", "Telepon", "Jabatan"
            }
        ));
        tabel_user.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_userMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_user);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 120, 420, 350));

        box_jabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH", "Kepala Posyandu", "Kader Posyandu", "Bidan Posyandu" }));
        jPanel1.add(box_jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 330, 230, 30));

        btn_tambah.setBackground(new java.awt.Color(0, 51, 102));
        btn_tambah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 430, 120, 28));

        btn_batal.setBackground(new java.awt.Color(0, 51, 102));
        btn_batal.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });
        jPanel1.add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 390, 110, 28));

        btn_resetPassword.setBackground(new java.awt.Color(0, 51, 102));
        btn_resetPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_resetPassword.setForeground(new java.awt.Color(255, 255, 255));
        btn_resetPassword.setText("Reset Password");
        btn_resetPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_resetPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(btn_resetPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, 250, 28));

        btn_hapus.setBackground(new java.awt.Color(0, 51, 102));
        btn_hapus.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, 110, 28));

        btn_ubah.setBackground(new java.awt.Color(0, 51, 102));
        btn_ubah.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btn_ubah.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah.setText("Ubah");
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 120, 28));

        label_tlp.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        label_tlp.setForeground(new java.awt.Color(255, 255, 255));
        label_tlp.setText("ID Kartu          :");
        jPanel1.add(label_tlp, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, 120, 30));

        label_jabatan.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        label_jabatan.setForeground(new java.awt.Color(255, 255, 255));
        label_jabatan.setText("Jabatan          :");
        jPanel1.add(label_jabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 330, 120, 30));

        txt_nama.setBackground(new java.awt.Color(204, 204, 204));
        txt_nama.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        txt_nama.setBorder(null);
        jPanel1.add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 140, 220, 30));

        btn_kembali.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        btn_kembali.setForeground(new java.awt.Color(255, 255, 255));
        btn_kembali.setText("Kembali");
        btn_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_kembaliMouseClicked(evt);
            }
        });
        jPanel1.add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, -1, -1));

        txt_tlp.setBackground(new java.awt.Color(204, 204, 204));
        txt_tlp.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        txt_tlp.setBorder(null);
        jPanel1.add(txt_tlp, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 220, 30));

        label_nama1.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        label_nama1.setForeground(new java.awt.Color(255, 255, 255));
        label_nama1.setText("Nama Lengkap :");
        jPanel1.add(label_nama1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, -1, 30));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon-minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, -10, 30, 30));

        label_tlp1.setFont(new java.awt.Font("Malgun Gothic", 1, 14)); // NOI18N
        label_tlp1.setForeground(new java.awt.Color(255, 255, 255));
        label_tlp1.setText("Telepon          :");
        jPanel1.add(label_tlp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 120, 30));

        txt_rfid.setBackground(new java.awt.Color(204, 204, 204));
        txt_rfid.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        txt_rfid.setBorder(null);
        jPanel1.add(txt_rfid, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 270, 210, 30));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Design_ManajemenUser.jpg"))); // NOI18N
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 900, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        if(txt_nama.getText().equals("") || txt_tlp.getText().equals("") || 
           box_jabatan.getSelectedItem().equals("PILIH")){
            JOptionPane.showMessageDialog(this, "Seluruh kolom harus di isi!");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                "Apakah Data Sudah Benar?" ,
                "Tambah User",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
            if (jawab == JOptionPane.YES_OPTION){
                jabatan = box_jabatan.getSelectedItem().toString();
                if(jabatan.equals("Kepala Posyandu")){
                    kode_jabatan = "KP";
                    buat_id();
                }else if(jabatan.equals("Bidan Posyandu")){
                    kode_jabatan = "BI";
                    buat_id();
                }else if(jabatan.equals("Kader Posyandu")){
                    kode_jabatan = "KD";
                    buat_id();
                }
            }
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_resetPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_resetPasswordActionPerformed
        // TODO add your handling code here:
        if (ambil_id.equals("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Akun Yang Akan Direset Password");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this, 
                "Password User "+ambil_id+" Akan Direset?" , 
                "Reset Password User", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opsi, opsi[1]);
            if (jawab == JOptionPane.YES_OPTION){
                resetPass();
            }
        }
    }//GEN-LAST:event_btn_resetPasswordActionPerformed

    private void tabel_userMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_userMouseClicked
        ambil_id = tabel_user.getValueAt(tabel_user.getSelectedRow(), 0).toString();
        pilihData();
    }//GEN-LAST:event_tabel_userMouseClicked

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
        if(ambil_id.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih User yang akan Dihapus");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                "User "+ambil_id+" Akan Dihapus?" ,
                "Hapus User",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opsi, opsi[1]);
            if (jawab == JOptionPane.YES_OPTION){
                hapusUser();
            }
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_kembaliMouseClicked
        // TODO add your handling code here:
        this.dispose();
        DashboardKepala_Frame dashboard = new DashboardKepala_Frame();
        dashboard.setVisible(true);
    }//GEN-LAST:event_btn_kembaliMouseClicked

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
        if(ambil_id.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih User yang akan Diedit");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                "Data User "+ambil_id+" Akan Diubah?" ,
                "Ubah Data User",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
            if (jawab == JOptionPane.YES_OPTION){
                editUser();
            }
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

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
            java.util.logging.Logger.getLogger(ManajemenUser_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ManajemenUser_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ManajemenUser_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManajemenUser_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new ManajemenUser_Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JComboBox<String> box_jabatan;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JLabel btn_kembali;
    private javax.swing.JButton btn_resetPassword;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_jabatan;
    private javax.swing.JLabel label_nama1;
    private javax.swing.JLabel label_tlp;
    private javax.swing.JLabel label_tlp1;
    private javax.swing.JLabel minimize;
    private javax.swing.JTable tabel_user;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_rfid;
    private javax.swing.JTextField txt_tlp;
    // End of variables declaration//GEN-END:variables
}
