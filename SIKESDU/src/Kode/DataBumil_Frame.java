package Kode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author SIKESDU Developer
 */
public class DataBumil_Frame extends javax.swing.JFrame {

    private Statement st;
    private Connection con;
    private ResultSet rs;

    public DataBumil_Frame() {
        initComponents();
        Tampildata();
    }

    DataKhusus khusus = new DataKhusus();

    private void kosong() {
        txt_nik.setText("");
        txt_ibu_hamil.setText("");
        txt_usia_kehamilan.setText("");
        txt_alamat.setText("");
        txt_BB.setText("");
        txt_no_telp.setText("");
        txt_tinggi.setText("");
        hpl.setDate(null);
        tgl_lahir.setDate(null);
        txt_nik.setEditable(true);
    }
    String formatTGL = "yyyy-MM-dd";
    SimpleDateFormat fm = new SimpleDateFormat(formatTGL);
    String tanggal_lahir;
    String HPL;

    public static java.util.Date getTanggal(JTable table, int kolom) {
        JTable tabel = table;
        String str_tgl = String.valueOf(tabel.getValueAt(tabel.getSelectedRow(), kolom));
        java.util.Date tgl = null;
        try {
            tgl = new SimpleDateFormat("yyyy-MM-dd").parse(str_tgl);
        } catch (Exception e) {
            System.out.println("Error GetTanggal " + e);
        }
        return tgl;
    }

    private void Tampildata() {
        try {
            con = (Connection) khusus.sambungDB();
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `data_ibuhamil`;");

            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("NIK Ibu Hamil");
            model.addColumn("Nama Lengkap");
            model.addColumn("Alamat");
            model.addColumn("Telpon");
            model.addColumn("Usia Kehamilan");

            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);

            while (rs.next()) {
                Object[] data = {
                    rs.getString("nik_ibuhamil"), rs.getString("nama_ibuHamil"), rs.getString("alamat"),
                    rs.getString("telepon"), rs.getString("usia_kehamilan")+"minggu"};
                model.addRow(data);
                tabel_bumil.setModel(model);

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
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
        jScrollPane = new javax.swing.JScrollPane();
        tabel_bumil = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        btn_simpan = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_kembali = new javax.swing.JButton();
        txt_nik = new javax.swing.JTextField();
        txt_ibu_hamil = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        txt_usia_kehamilan = new javax.swing.JTextField();
        hpl = new com.toedter.calendar.JDateChooser();
        tgl_lahir = new com.toedter.calendar.JDateChooser();
        txt_tinggi = new javax.swing.JTextField();
        txt_BB = new javax.swing.JTextField();
        txt_no_telp = new javax.swing.JTextField();
        btn_batal = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        minimize = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconapp.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(900, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane.setMaximumSize(new java.awt.Dimension(1000, 32767));
        jScrollPane.setPreferredSize(new java.awt.Dimension(900, 412));

        tabel_bumil.setBackground(new java.awt.Color(217, 217, 217));
        tabel_bumil.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "NIK Ibu Hamil", "Nama Lengkap", "Title 3", "Title 4", "Title 5"
            }
        ));
        tabel_bumil.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabel_bumil.setMaximumSize(new java.awt.Dimension(600, 80));
        tabel_bumil.setMinimumSize(new java.awt.Dimension(105, 80));
        tabel_bumil.setPreferredSize(new java.awt.Dimension(600, 270));
        tabel_bumil.setSelectionBackground(new java.awt.Color(217, 217, 217));
        tabel_bumil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_bumilMouseClicked(evt);
            }
        });
        jScrollPane.setViewportView(tabel_bumil);

        jPanel1.add(jScrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 400, 310));

        btn_simpan.setBackground(new java.awt.Color(34, 115, 235));
        btn_simpan.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        btn_simpan.setText("Simpan");
        btn_simpan.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        jPanel1.add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 410, -1, -1));

        btn_ubah.setBackground(new java.awt.Color(34, 115, 235));
        btn_ubah.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_ubah.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah.setText("Ubah");
        btn_ubah.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 410, -1, -1));

        btn_hapus.setBackground(new java.awt.Color(34, 115, 235));
        btn_hapus.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setText("Hapus");
        btn_hapus.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, -1, -1));

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
        jPanel1.add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 35, 100, -1));

        txt_nik.setBackground(new java.awt.Color(217, 217, 217));
        txt_nik.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_nik.setActionCommand("null");
        txt_nik.setBorder(null);
        txt_nik.setPreferredSize(new java.awt.Dimension(203, 36));
        jPanel1.add(txt_nik, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 86, 200, -1));

        txt_ibu_hamil.setBackground(new java.awt.Color(217, 217, 217));
        txt_ibu_hamil.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_ibu_hamil.setBorder(null);
        txt_ibu_hamil.setPreferredSize(new java.awt.Dimension(70, 36));
        jPanel1.add(txt_ibu_hamil, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 130, 200, -1));

        txt_alamat.setBackground(new java.awt.Color(217, 217, 217));
        txt_alamat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_alamat.setBorder(null);
        txt_alamat.setPreferredSize(new java.awt.Dimension(70, 34));
        jPanel1.add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(202, 184, 190, -1));

        txt_usia_kehamilan.setBackground(new java.awt.Color(217, 217, 217));
        txt_usia_kehamilan.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_usia_kehamilan.setBorder(null);
        txt_usia_kehamilan.setPreferredSize(new java.awt.Dimension(70, 35));
        jPanel1.add(txt_usia_kehamilan, new org.netbeans.lib.awtextra.AbsoluteConstraints(204, 273, 190, -1));

        hpl.setBackground(new java.awt.Color(217, 217, 217));
        hpl.setPreferredSize(new java.awt.Dimension(88, 40));
        jPanel1.add(hpl, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 320, 220, 30));
        jPanel1.add(tgl_lahir, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 370, 220, 30));

        txt_tinggi.setBackground(new java.awt.Color(217, 217, 217));
        txt_tinggi.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_tinggi.setBorder(null);
        txt_tinggi.setPreferredSize(new java.awt.Dimension(70, 37));
        jPanel1.add(txt_tinggi, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 417, 80, -1));

        txt_BB.setBackground(new java.awt.Color(217, 217, 217));
        txt_BB.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_BB.setBorder(null);
        txt_BB.setPreferredSize(new java.awt.Dimension(70, 37));
        jPanel1.add(txt_BB, new org.netbeans.lib.awtextra.AbsoluteConstraints(308, 417, 70, -1));

        txt_no_telp.setBackground(new java.awt.Color(217, 217, 217));
        txt_no_telp.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_no_telp.setBorder(null);
        txt_no_telp.setPreferredSize(new java.awt.Dimension(70, 35));
        jPanel1.add(txt_no_telp, new org.netbeans.lib.awtextra.AbsoluteConstraints(203, 230, 190, -1));

        btn_batal.setBackground(new java.awt.Color(34, 115, 235));
        btn_batal.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_batal.setForeground(new java.awt.Color(255, 255, 255));
        btn_batal.setText("Batal");
        btn_batal.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_batal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_batalActionPerformed(evt);
            }
        });
        jPanel1.add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 410, -1, -1));

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel2.setText("Nama Lengkap");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, 30));

        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel3.setText("Alamat");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 130, 30));

        jLabel4.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel4.setText("Nomor Telepon");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, 30));

        jLabel5.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel5.setText("Usia Kehamilan");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 270, -1, 30));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel6.setText("HPL");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 320, 120, -1));

        jLabel7.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel7.setText("Tanggal Lahir");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 370, -1, -1));

        jLabel8.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel8.setText("Tinggi ");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, -1, -1));

        jLabel9.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel9.setText("cm");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, -1, -1));

        jLabel10.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel10.setText("BB");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(268, 420, -1, -1));

        jLabel11.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel11.setText("kg");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 420, -1, -1));

        jLabel12.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel12.setText("NIK");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 130, 40));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon-minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, -10, 30, 30));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Design_DataBumil.png"))); // NOI18N
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        try {
            tanggal_lahir = String.valueOf(fm.format(tgl_lahir.getDate()));
            HPL = String.valueOf(fm.format(hpl.getDate()));
            st = con.createStatement();
            if (txt_nik.getText().equals("") || txt_ibu_hamil.getText().equals("") || txt_no_telp.getText().equals("")
                    || txt_alamat.getText().equals("") || tgl_lahir.getDate() == null || txt_usia_kehamilan.equals("")
                    || txt_BB.getText().equals("") || txt_tinggi.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Seluruh kolom harus di terpenuhi!");
            } else {
                st.executeUpdate("INSERT INTO `data_ibuhamil` (`nik_ibuhamil`,"
                        + " `nama_ibuHamil`, `tgl_lahir`, `alamat`, `telepon`, `rencana_kelahiran`, `usia_kehamilan`, `berat_badan`, "
                        + "`tinggi_badan`, `waktu_daftar`) VALUES ('" + txt_nik.getText() + "', '" + txt_ibu_hamil.getText() + "', "
                        + "'" + tanggal_lahir + "', '" + txt_alamat.getText() + "', '" + txt_no_telp.getText() + "', '" + HPL + "', '"
                        + "" + txt_usia_kehamilan.getText() + "', '" + txt_BB.getText() + "', '" + txt_tinggi.getText() + "', current_timestamp());");
                JOptionPane.showMessageDialog(this, "Data Berhasil Disimpan");
                kosong();
                Tampildata();

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        kosong();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        try {
            tanggal_lahir = String.valueOf(fm.format(tgl_lahir.getDate()));
            HPL = String.valueOf(fm.format(hpl.getDate()));
            st = con.createStatement();
            if (txt_nik.equals("")) {
                JOptionPane.showMessageDialog(this, "Silahkan Pilih Data Yang Akan Diperbarui");
            } else {

                st.executeUpdate("UPDATE `data_ibuhamil` SET `nama_ibuHamil` = '" + txt_ibu_hamil.getText() + "', "
                        + "`tgl_lahir` = '" + tanggal_lahir + "', `alamat` = '" + txt_alamat.getText() + "', "
                        + "`telepon` = '" + txt_no_telp.getText() + "', `rencana_kelahiran` = '" + HPL + "', "
                        + "`usia_kehamilan` = '" + txt_usia_kehamilan.getText() + "', `berat_badan` = '" + txt_BB.getText() + "', "
                        + "`tinggi_badan` = '" + txt_tinggi.getText() + "' WHERE `data_ibuhamil`.`nik_ibuhamil` = '" + txt_nik.getText() + "';");
                JOptionPane.showMessageDialog(null, " Data Berhasil Diperbarui");
                kosong();
                Tampildata();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        if (txt_nik.equals("")) {
            JOptionPane.showMessageDialog(this, "silahkan pilih data yang akan dihapus");
        } else {
            String[] opsi = {"YA", "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(null,
                    "Apakah Anda Yakin Akan \nMenghapus Data Ini?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
            if (jawab == JOptionPane.YES_OPTION) {
                try {
                    st = con.createStatement();
                    String sql = "DELETE FROM `data_ibuhamil` WHERE `data_ibuhamil`.`nik_ibuhamil` = '" + txt_nik.getText() + "';";
                    st.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
                    st.close();
                    kosong();
                    Tampildata();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e);

                }
            }
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        // TODO add your handling code here:
        this.dispose();
        if (khusus.get_jabatan().equalsIgnoreCase("Bidan Posyandu")) {
            DashboardBidan_Frame dbbidan = new DashboardBidan_Frame();
            dbbidan.setVisible(true);
        } else if (khusus.get_jabatan().equalsIgnoreCase("Kader Posyandu")) {
            DashboardKader_Frame dbkader = new DashboardKader_Frame();
            dbkader.setVisible(true);
        }
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void minimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_minimizeMouseClicked
        // TODO add your handling code here:
        this.setExtendedState(ICONIFIED);
    }//GEN-LAST:event_minimizeMouseClicked

    private void tabel_bumilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_bumilMouseClicked
        String nik_bumil = tabel_bumil.getValueAt(tabel_bumil.getSelectedRow(), 0).toString();
        try {

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `data_ibuhamil` WHERE `nik_ibuhamil`='" + nik_bumil + "';");
            if (rs.next()) {
                txt_nik.setText(rs.getString("nik_ibuhamil"));
                txt_nik.setEditable(false);
                txt_ibu_hamil.setText(rs.getString("nama_ibuHamil"));
                txt_no_telp.setText(rs.getString("telepon"));
                txt_usia_kehamilan.setText(rs.getString("usia_kehamilan"));
                txt_alamat.setText(rs.getString("alamat"));
                txt_BB.setText(rs.getString("berat_badan"));
                txt_tinggi.setText(rs.getString("tinggi_badan"));
                hpl.setDate(rs.getDate("rencana_kelahiran"));
                tgl_lahir.setDate(rs.getDate("tgl_lahir"));

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_tabel_bumilMouseClicked

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
            java.util.logging.Logger.getLogger(DataBumil_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataBumil_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataBumil_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataBumil_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new DataBumil_Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JButton btn_batal;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_ubah;
    private com.toedter.calendar.JDateChooser hpl;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JLabel minimize;
    private javax.swing.JTable tabel_bumil;
    private com.toedter.calendar.JDateChooser tgl_lahir;
    private javax.swing.JTextField txt_BB;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_ibu_hamil;
    private javax.swing.JTextField txt_nik;
    private javax.swing.JTextField txt_no_telp;
    private javax.swing.JTextField txt_tinggi;
    private javax.swing.JTextField txt_usia_kehamilan;
    // End of variables declaration//GEN-END:variables
}
