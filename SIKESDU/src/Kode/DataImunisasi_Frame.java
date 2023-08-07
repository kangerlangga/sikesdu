package Kode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SIKESDU Developer
 */
public class DataImunisasi_Frame extends javax.swing.JFrame {
    DataKhusus khusus = new DataKhusus();
    String ambil_id, ambil_nama, sql,idBaru, kode_lvl;

    public DataImunisasi_Frame() {
        initComponents();
        reset();
    }
    
    private void reset(){
        txt_NamaVaksin.setText("");
        txt_dosis.setText("");
        box_level.setSelectedItem("PILIH");
        ambil_id = "";
        ambil_nama = "";
        kode_lvl = "";
        TampilData();
    }
     
    private void TampilData(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Vaksin");
        model.addColumn("Dosis /mL");
        model.addColumn("level");
        
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        model.setRowCount(0);
        try{
            sql = "SELECT * FROM `data_imunisasi`";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                model.addRow(
                new Object[] {
                    rs.getString("nama_vaksin"),
                    rs.getString("dosis"), rs.getString("level")
                }
                );
            }
            tabel_DataImunisasi.setModel(model);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void pilihData(){
        try{
            sql = "SELECT * FROM `data_imunisasi` WHERE nama_vaksin='"+ambil_nama+"';";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                txt_NamaVaksin.setText(r.getString("nama_vaksin"));
                txt_dosis.setText(r.getString("dosis"));
                box_level.setSelectedItem(r.getString("level"));
                ambil_id = r.getString("id_imunisasi");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void editData(){
        try{
            sql = "UPDATE `data_imunisasi` SET "
                    + "`nama_vaksin` = '"+txt_NamaVaksin.getText()+"', "
                    + "`dosis` = '"+txt_dosis.getText()+"', "
                    + "`level` = '"+box_level.getSelectedItem()+"' "
                    + "WHERE `data_imunisasi`.`id_imunisasi` = '"+ambil_id+"';";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Imunisasi Berhasil Diubah!");
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Imunisasi Gagal Diubah "+e);
        }
    }
    
    private void tambahData(){
        try{
            sql = "INSERT INTO `data_imunisasi` (`id_imunisasi`, `nama_vaksin`, `dosis`, `level`) VALUES "
                + "('"+idBaru+"', '"+txt_NamaVaksin.getText()+"', '"+txt_dosis.getText()+"', '"+box_level.getSelectedItem()+"');";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Berhasil Ditambahkan \nID = "+idBaru);
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Tidak Dapat Ditambahkan "+e);
        }
    }
    
     private void buat_id(){
        try{
            sql = "SELECT * FROM `data_imunisasi` WHERE id_imunisasi LIKE '"+kode_lvl+"%' ORDER BY id_imunisasi DESC;";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                String id = r.getString("id_imunisasi").substring(4);
                String AN = ""+(Integer.parseInt(id)+1);
                String nol = "";
                
                if(AN.length()==1){
                    nol = "00";
                }else if(AN.length()==2){
                    nol = "0";
                }else if(AN.length()==3){
                    nol = "";
                }
                idBaru = kode_lvl+nol+AN;
                if(idBaru.length()==7){
                    System.out.println("ID = "+idBaru);
                    tambahData();
                }else{
                    System.out.println("ID Tidak Valid!");
                }
            }else{
                idBaru = kode_lvl+"001";
                System.out.println("ID = "+idBaru);
                tambahData();
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
     
      private void hapusData(){
        try{
            sql = "DELETE FROM `data_imunisasi` WHERE `id_imunisasi`='"+ambil_id+"';";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Imunisasi Berhasil Dihapus!");
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Imunisasi Tidak Dapat Dihapus "+e);
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
        jLabel1 = new javax.swing.JLabel();
        btn_batal = new javax.swing.JButton();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        label_dosis = new javax.swing.JLabel();
        txt_dosis = new javax.swing.JTextField();
        label_level = new javax.swing.JLabel();
        label_NamaVaksin = new javax.swing.JLabel();
        box_level = new javax.swing.JComboBox<>();
        txt_NamaVaksin = new javax.swing.JTextField();
        btn_kembali = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_DataImunisasi = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        minimize2 = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconapp.png")).getImage());
        setUndecorated(true);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        jLabel1.setText("/mL");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 230, 30, 30));

        btn_batal.setBackground(new java.awt.Color(0, 51, 102));
        btn_batal.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal.setText("Batal");
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });
        jPanel1.add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 440, 100, 30));

        btn_tambah.setBackground(new java.awt.Color(0, 51, 102));
        btn_tambah.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_tambah.setForeground(new java.awt.Color(255, 255, 255));
        btn_tambah.setText("Tambah");
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 440, 100, 30));

        btn_hapus.setBackground(new java.awt.Color(0, 51, 102));
        btn_hapus.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setText("Hapus");
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 440, 100, 30));

        btn_ubah.setBackground(new java.awt.Color(0, 51, 102));
        btn_ubah.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_ubah.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah.setText("Ubah");
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 440, 100, 30));

        label_dosis.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        label_dosis.setForeground(new java.awt.Color(255, 255, 255));
        label_dosis.setText("Dosis            :");
        jPanel1.add(label_dosis, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 130, 30));

        txt_dosis.setBackground(new java.awt.Color(217, 217, 217));
        txt_dosis.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        txt_dosis.setBorder(null);
        txt_dosis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_dosisActionPerformed(evt);
            }
        });
        jPanel1.add(txt_dosis, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 160, 30));

        label_level.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        label_level.setForeground(new java.awt.Color(255, 255, 255));
        label_level.setText("Level            :");
        jPanel1.add(label_level, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, -1, 40));

        label_NamaVaksin.setFont(new java.awt.Font("Malgun Gothic", 1, 18)); // NOI18N
        label_NamaVaksin.setForeground(new java.awt.Color(255, 255, 255));
        label_NamaVaksin.setText("Nama Vaksin :");
        jPanel1.add(label_NamaVaksin, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 140, 30));

        box_level.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        box_level.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH", "Dasar Lengkap Bayi", "Lanjutan Bayi", "Lanjutan Anak", "Tambahan" }));
        box_level.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                box_levelActionPerformed(evt);
            }
        });
        jPanel1.add(box_level, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 303, 210, 40));

        txt_NamaVaksin.setBackground(new java.awt.Color(217, 217, 217));
        txt_NamaVaksin.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        txt_NamaVaksin.setBorder(null);
        jPanel1.add(txt_NamaVaksin, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 190, 30));

        btn_kembali.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_kembali.setForeground(new java.awt.Color(255, 255, 255));
        btn_kembali.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_kembali.setText("Kembali");
        btn_kembali.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_kembaliMouseClicked(evt);
            }
        });
        jPanel1.add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 20, 80, 30));

        tabel_DataImunisasi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Nama vaksin", "Dosis", "level"
            }
        ));
        tabel_DataImunisasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_DataImunisasiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_DataImunisasi);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, 380, 340));

        minimize2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon-minimize.png"))); // NOI18N
        minimize2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimize2MouseClicked(evt);
            }
        });
        jPanel1.add(minimize2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, -10, 30, 30));

        background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Design_DataImunisasi.jpg"))); // NOI18N
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

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        // TODO add your handling code here:
         if(ambil_id.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Data yang akan Diedit");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                "Data Imunisasi "+ambil_id+" Akan Diubah?" ,
                "Ubah Data Imunisasi",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
            if (jawab == JOptionPane.YES_OPTION){
                editData();
            }
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void minimize2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimize2MouseClicked
        // TODO add your handling code here:
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_minimize2MouseClicked

    private void tabel_DataImunisasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_DataImunisasiMouseClicked
        // TODO add your handling code here:
        ambil_nama = tabel_DataImunisasi.getValueAt(tabel_DataImunisasi.getSelectedRow(), 0).toString();
        pilihData();
    }//GEN-LAST:event_tabel_DataImunisasiMouseClicked

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        // TODO add your handling code here:
        int level = box_level.getSelectedIndex();
        if(level == 1){
            kode_lvl = "VB01";
            buat_id();
        }else if(level == 2){
            kode_lvl = "VB02";
            buat_id();
        }else if(level == 3){
            kode_lvl = "VB03";
            buat_id();
        }else if(level == 4){
            kode_lvl = "VB04";
            buat_id();
        }
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        // TODO add your handling code here:
         if(ambil_id.equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih data yang akan Dihapus");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                "Data Imunisasi "+ambil_id+" Akan Dihapus?" ,
                "Hapus Data Imunisasi",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null, opsi, opsi[1]);
            if (jawab == JOptionPane.YES_OPTION){
                hapusData();
            }
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_kembaliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_kembaliMouseClicked
          this.dispose();
          DashboardBidan_Frame dashboard= new DashboardBidan_Frame();
          dashboard.setVisible(true);
           
    }//GEN-LAST:event_btn_kembaliMouseClicked

    private void box_levelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_box_levelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_box_levelActionPerformed

    private void txt_dosisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_dosisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_dosisActionPerformed

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
            java.util.logging.Logger.getLogger(DataImunisasi_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataImunisasi_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataImunisasi_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataImunisasi_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataImunisasi_Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JComboBox<String> box_level;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JLabel btn_kembali;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_ubah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label_NamaVaksin;
    private javax.swing.JLabel label_dosis;
    private javax.swing.JLabel label_level;
    private javax.swing.JLabel minimize2;
    private javax.swing.JTable tabel_DataImunisasi;
    private javax.swing.JTextField txt_NamaVaksin;
    private javax.swing.JTextField txt_dosis;
    // End of variables declaration//GEN-END:variables
}
