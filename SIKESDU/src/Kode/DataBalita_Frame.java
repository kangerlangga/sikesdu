package Kode;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.*;

/**
 *
 * @author SIKESDU Developer
 */
public class DataBalita_Frame extends javax.swing.JFrame {
    private Statement st ;
    private Connection con;
    private ResultSet rs;
    DataKhusus khusus = new DataKhusus();
    String ambil_id, sql, idBaru, nik="NULL", pbl="NULL", bbl="NULL";
    
    public DataBalita_Frame() {
        initComponents();
        reset();
        setkolom();
    }
    private void reset(){
        txt_id_balita.setText("");
        txt_NIK.setText("");
        txt_nama_balita.setText("");
        txt_nama_ibu.setText("");
        txt_alamat.setText("");
        txt_BBL.setText("");
        txt_PBL.setText("");
        combo_jenis_kelamin.setSelectedIndex(0);
        tgl_lahir.setDate(null);
        nik="NULL"; pbl="NULL"; bbl="NULL";
        tanggal_lahir = "";
        ambil_id = "";
        idBaru = "";
        Tampildata();
    }
    
    String formatTGL = "yyyy-MM-dd";
    SimpleDateFormat fm = new SimpleDateFormat(formatTGL);
    String tanggal_lahir;
    
    private void Tampildata() {
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("ID Balita");
            model.addColumn("Nama Balita");
            model.addColumn("Alamat");
            model.addColumn("Jenis Kelamin");
            model.addColumn("Tanggal Lahir");
            
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            model.setRowCount(0);
            
            try{
                sql = "SELECT * FROM `data_balita` ORDER BY waktu_daftar DESC;";
                Connection con = (Connection)khusus.sambungDB();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()){
                    model.addRow(
                    new Object[] {
                        rs.getString("id_balita"),rs.getString("nama_balita"),rs.getString("alamat"),
                        rs.getString("jenis_kelamin"),rs.getString("tgl_lahir_balita")
                    }
                    );
                }
                tabel_balita.setModel(model);
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e);
            }
    }
    private void setkolom(){
         TableColumnModel col = tabel_balita.getColumnModel();
         col.getColumn(0).setPreferredWidth(350);
         col.getColumn(1).setPreferredWidth(350);
         col.getColumn(2).setPreferredWidth(350);
         col.getColumn(3).setPreferredWidth(350);
         col.getColumn(4).setPreferredWidth(350);
    }
    private void pilihData(){
        try{
            sql = "SELECT * FROM `data_balita` WHERE id_balita='"+ambil_id+"';";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                txt_id_balita.setText(r.getString("id_balita"));
                txt_NIK.setText(r.getString("nik_balita"));
                txt_nama_balita.setText(r.getString("nama_balita"));
                txt_nama_ibu.setText(r.getString("nama_ortu"));
                txt_alamat.setText(r.getString("alamat"));
                tgl_lahir.setDate(r.getDate("tgl_lahir_balita"));
                combo_jenis_kelamin.setSelectedItem((r.getString("jenis_kelamin")));
                txt_BBL.setText(r.getString("bbl_balita"));
                txt_PBL.setText(r.getString("pbl_balita"));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void tambahData(){
        try{
            sql = "INSERT INTO `data_balita` "
            + "(`id_balita`, `nik_balita`, `nama_balita`, `nama_ortu`, `alamat`, `jenis_kelamin`, `tgl_lahir_balita`, "
            + "`bbl_balita`, `pbl_balita`, `waktu_daftar`) VALUES "
            + "('"+idBaru+"', "+nik+", '"+txt_nama_balita.getText()+"', "
            + "'"+txt_nama_ibu.getText()+"', '"+txt_alamat.getText()+"', '"+combo_jenis_kelamin.getSelectedItem()+"', "
            + "'"+tanggal_lahir+"', "+bbl+", "+pbl+", current_timestamp());";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Balita Berhasil Ditambahkan");
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, "Data Balita Tidak Dapat Ditambahkan "+e);
        }
    }
    
    private void ubahData(){
        try{
            sql = "UPDATE `data_balita` SET "
                + "`nik_balita` = "+nik+", "
                + "`nama_balita` = '"+txt_nama_balita.getText()+"', "
                + "`nama_ortu` = '"+txt_nama_ibu.getText()+"', "
                + "`alamat` = '"+txt_alamat.getText()+"', "
                + "`jenis_kelamin` = '"+combo_jenis_kelamin.getSelectedItem()+"', "
                + "`tgl_lahir_balita` = '"+tanggal_lahir+"', "
                + "`bbl_balita` = "+bbl+", "
                + "`pbl_balita` = "+pbl+" "
                + "WHERE `data_balita`.`id_balita` = '"+ambil_id+"';";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Balita Berhasil Diperbarui!");
            reset();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Data Balita Gagal Diperbarui "+e);
        }
    }
    
    private void hapusData(){
        try {
            sql = "DELETE FROM `data_balita` WHERE `data_balita`.`id_balita` = '"+ambil_id+"';";
            Connection con = (Connection)khusus.sambungDB();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.execute();
            JOptionPane.showMessageDialog(this, "Data Balita Berhasil Dihapus!");
            reset();
        }catch ( SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    private void cekDataGanda(){
        try{
            sql = "SELECT * FROM `data_balita` WHERE "
                + "nama_balita='"+txt_nama_balita.getText()+"' AND "
                + "nama_ortu='"+txt_nama_ibu.getText()+"' AND "
                + "tgl_lahir_balita='"+tanggal_lahir+"';";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                JOptionPane.showMessageDialog(this, "Data Balita Sudah Ada! \nID = "+r.getString("id_balita"));
            }else{
                String[] opsi = {"YA" , "TIDAK"};
                int jawab = JOptionPane.showOptionDialog(this,
                    "Apakah Data Sudah Benar?" ,
                    "Tambah Data Balita",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
                if (jawab == JOptionPane.YES_OPTION){
                    buat_id();
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }
    
    private void buat_id(){
        try{
            sql = "SELECT * FROM `data_balita` ORDER BY id_balita DESC;";
            Connection con = (Connection)khusus.sambungDB();
            Statement st = con.createStatement();
            ResultSet r = st.executeQuery(sql);
            if(r.next()){
                String id = r.getString("id_balita").substring(5);
                String AN = ""+(Integer.parseInt(id)+1);
                String nol = "";
                
                if(AN.length()==1){
                    nol = "00";
                }else if(AN.length()==2){
                    nol = "0";
                }else if(AN.length()==3){
                    nol = "";
                }
                idBaru = "BL"+khusus.get_nomorPos()+nol+AN;
                if(idBaru.length()==8){
                    System.out.println("ID Balita = "+idBaru);
                    tambahData();
                }else{
                    System.out.println("ID Tidak Valid!");
                }
            }else{
                idBaru = "BL"+khusus.get_nomorPos()+"001";
                System.out.println("ID Balita = "+idBaru);
                tambahData();
            }
        }catch(Exception e){
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
        tgl_lahir = new com.toedter.calendar.JDateChooser();
        txt_nama_balita = new javax.swing.JTextField();
        txt_NIK = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        txt_PBL = new javax.swing.JTextField();
        txt_BBL = new javax.swing.JTextField();
        txt_id_balita = new javax.swing.JTextField();
        txt_nama_ibu = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        btn_kembali = new javax.swing.JButton();
        btn_ubah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        combo_jenis_kelamin = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        btn_batal = new javax.swing.JButton();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_balita = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        bg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconapp.png")).getImage());
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setPreferredSize(new java.awt.Dimension(900, 500));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tgl_lahir.setBackground(new java.awt.Color(217, 217, 217));
        tgl_lahir.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel1.add(tgl_lahir, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 150, 30));

        txt_nama_balita.setBackground(new java.awt.Color(217, 217, 217));
        txt_nama_balita.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_nama_balita.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_nama_balita.setBorder(null);
        jPanel1.add(txt_nama_balita, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 192, 260, 40));
        txt_nama_balita.getAccessibleContext().setAccessibleName("");

        txt_NIK.setBackground(new java.awt.Color(217, 217, 217));
        txt_NIK.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_NIK.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_NIK.setBorder(null);
        txt_NIK.setPreferredSize(new java.awt.Dimension(70, 21));
        jPanel1.add(txt_NIK, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 143, 260, 39));

        txt_alamat.setBackground(new java.awt.Color(217, 217, 217));
        txt_alamat.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_alamat.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_alamat.setBorder(null);
        jPanel1.add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 300, 250, 38));

        txt_PBL.setBackground(new java.awt.Color(217, 217, 217));
        txt_PBL.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_PBL.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_PBL.setBorder(null);
        jPanel1.add(txt_PBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 408, 40, 39));

        txt_BBL.setBackground(new java.awt.Color(217, 217, 217));
        txt_BBL.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_BBL.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_BBL.setBorder(null);
        jPanel1.add(txt_BBL, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 353, 40, 40));

        txt_id_balita.setEditable(false);
        txt_id_balita.setBackground(new java.awt.Color(217, 217, 217));
        txt_id_balita.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_id_balita.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_id_balita.setBorder(null);
        jPanel1.add(txt_id_balita, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 93, 260, 40));

        txt_nama_ibu.setBackground(new java.awt.Color(217, 217, 217));
        txt_nama_ibu.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txt_nama_ibu.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txt_nama_ibu.setBorder(null);
        txt_nama_ibu.setPreferredSize(new java.awt.Dimension(70, 21));
        jPanel1.add(txt_nama_ibu, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 245, 250, 39));

        btn_simpan.setBackground(new java.awt.Color(34, 115, 235));
        btn_simpan.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_simpan.setForeground(new java.awt.Color(255, 255, 255));
        btn_simpan.setText("Tambah");
        btn_simpan.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        jPanel1.add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(488, 410, -1, -1));

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
        jPanel1.add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 40, 100, -1));

        btn_ubah.setBackground(new java.awt.Color(34, 115, 235));
        btn_ubah.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        btn_ubah.setForeground(new java.awt.Color(255, 255, 255));
        btn_ubah.setText("Perbarui");
        btn_ubah.setPreferredSize(new java.awt.Dimension(85, 35));
        btn_ubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ubahActionPerformed(evt);
            }
        });
        jPanel1.add(btn_ubah, new org.netbeans.lib.awtextra.AbsoluteConstraints(583, 410, -1, -1));

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
        jPanel1.add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(678, 410, -1, -1));

        combo_jenis_kelamin.setBackground(new java.awt.Color(217, 217, 217));
        combo_jenis_kelamin.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        combo_jenis_kelamin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PILIH", "Laki - Laki", "Perempuan" }));
        jPanel1.add(combo_jenis_kelamin, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 410, 150, 38));

        jLabel2.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel2.setText("cm");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 409, 30, 30));

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
        jPanel1.add(btn_batal, new org.netbeans.lib.awtextra.AbsoluteConstraints(773, 410, -1, -1));

        jLabel3.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel3.setText("Nama Balita");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 194, 110, 30));

        jLabel4.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel4.setText("ID Balita");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 94, 120, 30));

        jLabel5.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel5.setText("Alamat");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 304, 120, 30));

        jLabel6.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel6.setText("Tanggal Lahir");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 362, -1, -1));

        jLabel7.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel7.setText("Jenis Kelamin\n");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 412, -1, 30));

        jLabel8.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel8.setText("BBL");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, 40, -1));

        jLabel9.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel9.setText("NIK");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 144, 120, 30));

        jLabel10.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel10.setText("Nama Ibu");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 120, 30));

        jLabel11.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel11.setText("kg");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 356, 30, 30));

        jLabel12.setFont(new java.awt.Font("Malgun Gothic", 1, 17)); // NOI18N
        jLabel12.setText("PBL");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 414, 40, 30));

        minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/icon-minimize.png"))); // NOI18N
        minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                minimizeMouseClicked(evt);
            }
        });
        jPanel1.add(minimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, -10, 30, 30));

        jScrollPane1.setMaximumSize(new java.awt.Dimension(1000, 32767));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(900, 412));

        tabel_balita.setBackground(new java.awt.Color(217, 217, 217));
        tabel_balita.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_balita.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabel_balita.setMaximumSize(new java.awt.Dimension(1000, 80));
        tabel_balita.setPreferredSize(new java.awt.Dimension(1000, 270));
        tabel_balita.setSelectionBackground(new java.awt.Color(217, 217, 217));
        tabel_balita.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabel_balitaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabel_balita);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 80, 350, 310));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Design_DataBalita.jpg"))); // NOI18N
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        this.dispose();
        if(khusus.get_jabatan().equalsIgnoreCase("Bidan Posyandu")){
           DashboardBidan_Frame dbbidan= new DashboardBidan_Frame();
           dbbidan.setVisible(true);
        }else if(khusus.get_jabatan().equalsIgnoreCase("Kader Posyandu")){
           DashboardKader_Frame dbkader= new DashboardKader_Frame();
           dbkader.setVisible(true);
        }
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void btn_ubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ubahActionPerformed
        if(ambil_id.equals("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Data Balita yang akan Diperbarui");
        }else{
            if(txt_nama_balita.getText().equals("") || txt_nama_ibu.getText().equals("") 
              || txt_alamat.getText().equals("") || tgl_lahir.getDate()==null
              || combo_jenis_kelamin.getSelectedItem().equals("PILIH")){
                JOptionPane.showMessageDialog(this, "Seluruh kolom harus di terpenuhi!");
            }else{
                if(txt_NIK.getText().length()!=0 && txt_NIK.getText().length()==16){
                    nik = "'"+txt_NIK.getText()+"'";
                    if(txt_BBL.getText().length()!=0){
                        bbl = "'"+txt_BBL.getText()+"'";
                    }
                    if(txt_PBL.getText().length()!=0){
                        pbl = "'"+txt_PBL.getText()+"'";
                    }
                    String[] opsi = {"YA" , "TIDAK"};
                    int jawab = JOptionPane.showOptionDialog(this,
                        "Data Balita Akan Diperbarui?" ,
                        "Perbarui Data Balita",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
                    if (jawab == JOptionPane.YES_OPTION){
                        tanggal_lahir = String.valueOf(fm.format(tgl_lahir.getDate()));
                        ubahData();
                    }

                }else if(txt_NIK.getText().length()==0 || txt_NIK.getText().equals("")){
                    if(txt_BBL.getText().length()!=0){
                        bbl = "'"+txt_BBL.getText()+"'";
                    }
                    if(txt_PBL.getText().length()!=0){
                        pbl = "'"+txt_PBL.getText()+"'";
                    }
                    String[] opsi = {"YA" , "TIDAK"};
                    int jawab = JOptionPane.showOptionDialog(this,
                        "Data Balita Akan Diperbarui?" ,
                        "Perbarui Data Balita",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, opsi, opsi[1]);
                    if (jawab == JOptionPane.YES_OPTION){
                        tanggal_lahir = String.valueOf(fm.format(tgl_lahir.getDate()));
                        ubahData();
                    }

                }else{
                    JOptionPane.showMessageDialog(this, "Panjang NIK Harus 16 Digit!");
                    txt_NIK.requestFocus();
                }
            }
        }
    }//GEN-LAST:event_btn_ubahActionPerformed

    private void btn_batalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_batalActionPerformed
        reset();
    }//GEN-LAST:event_btn_batalActionPerformed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        if(txt_nama_balita.getText().equals("") || txt_nama_ibu.getText().equals("") 
          || txt_alamat.getText().equals("") || tgl_lahir.getDate()==null
          || combo_jenis_kelamin.getSelectedItem().equals("PILIH")){
            JOptionPane.showMessageDialog(this, "Seluruh kolom harus di terpenuhi!");
        }else{
            if(txt_NIK.getText().length()!=0 && txt_NIK.getText().length()==16){
                nik = "'"+txt_NIK.getText()+"'";
                if(txt_BBL.getText().length()!=0){
                    bbl = "'"+txt_BBL.getText()+"'";
                }
                if(txt_PBL.getText().length()!=0){
                    pbl = "'"+txt_PBL.getText()+"'";
                }
                tanggal_lahir = String.valueOf(fm.format(tgl_lahir.getDate()));
                cekDataGanda();
                
            }else if(txt_NIK.getText().length()==0 || txt_NIK.getText().equals("")){
                if(txt_BBL.getText().length()!=0){
                    bbl = "'"+txt_BBL.getText()+"'";
                }
                if(txt_PBL.getText().length()!=0){
                    pbl = "'"+txt_PBL.getText()+"'";
                }
                tanggal_lahir = String.valueOf(fm.format(tgl_lahir.getDate()));
                cekDataGanda();
                
            }else{
                JOptionPane.showMessageDialog(this, "Panjang NIK Harus 16 Digit!");
                txt_NIK.requestFocus();
            }
        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        if(ambil_id.equals("")){
            JOptionPane.showMessageDialog(this, "Silahkan Pilih Data Balita yang akan Dihapus");
        }else{
            String[] opsi = {"YA" , "TIDAK"};
            int jawab = JOptionPane.showOptionDialog(this,
                "Balita Dengan ID="+ambil_id+" Akan Dihapus?" ,
                "Hapus Data Balita",
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

    private void tabel_balitaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabel_balitaMouseClicked
        // TODO add your handling code here:
        ambil_id = tabel_balita.getValueAt(tabel_balita.getSelectedRow(), 0).toString();
        pilihData();
    }//GEN-LAST:event_tabel_balitaMouseClicked

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
            java.util.logging.Logger.getLogger(DataBalita_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataBalita_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataBalita_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataBalita_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new DataBalita_Frame().setVisible(true);
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
    private javax.swing.JComboBox<String> combo_jenis_kelamin;
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
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel minimize;
    private javax.swing.JTable tabel_balita;
    private com.toedter.calendar.JDateChooser tgl_lahir;
    private javax.swing.JTextField txt_BBL;
    private javax.swing.JTextField txt_NIK;
    private javax.swing.JTextField txt_PBL;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_id_balita;
    private javax.swing.JTextField txt_nama_balita;
    private javax.swing.JTextField txt_nama_ibu;
    // End of variables declaration//GEN-END:variables
}
