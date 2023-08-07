package Kode;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author SIKESDU Developer
 */
public class RFID_Frame extends javax.swing.JFrame {

    public RFID_Frame() {
        initComponents();
        reset();
    }
    
    String sql;
    DataKhusus khusus = new DataKhusus();
    
    private void reset(){
        txt_id.setText("");
        txt_id.requestFocus();
    }
    
    private void cekLogin(){
        try{
            sql = "SELECT * FROM `user` WHERE id_kartu=MD5('"+txt_id.getText()+"')";
            java.sql.Connection con = (Connection)khusus.sambungDB();
            java.sql.PreparedStatement ps = con.prepareStatement(sql);
            java.sql.ResultSet r = ps.executeQuery(sql);
            if(r.next()){
                JOptionPane.showMessageDialog(this, 
                        "Login Berhasil! \nAKUN : "+r.getString("jabatan").toUpperCase() , 
                         "Informasi Login" ,
                     JOptionPane.INFORMATION_MESSAGE);
                khusus.set_idPegawai(r.getString("id_pegawai"));
                khusus.set_nama(r.getString("nama_lengkap"));
                khusus.set_jabatan(r.getString("jabatan"));
                this.dispose();
                if(r.getString("jabatan").equalsIgnoreCase("Kepala Posyandu")){
                    DashboardKepala_Frame kepala = new DashboardKepala_Frame();
                    kepala.setVisible(true);
                }else if(r.getString("jabatan").equalsIgnoreCase("Bidan Posyandu")){
                    DashboardBidan_Frame bidan = new DashboardBidan_Frame();
                    bidan.setVisible(true);
                }else if(r.getString("jabatan").equalsIgnoreCase("Kader Posyandu")){
                    DashboardKader_Frame kader = new DashboardKader_Frame();
                    kader.setVisible(true);
                }
                System.out.println("ID = "+khusus.get_id());
                System.out.println("Nama = "+khusus.get_nama());
            }else{
                JOptionPane.showMessageDialog(this, 
                            "Maaf Login Gagal \nID Kartu Tidak Ditemukan!" , 
                            "Informasi Login" , 
                            JOptionPane.INFORMATION_MESSAGE);
                reset();
            }
            
        }catch(SQLException e){
            System.out.println("Gagal Login \n"+e);
            System.out.println(sql);
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
        lbl_login = new javax.swing.JLabel();
        bg = new javax.swing.JLabel();
        txt_id = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(new javax.swing.ImageIcon(getClass().getResource("/Gambar/iconapp.png")).getImage());
        setUndecorated(true);
        setResizable(false);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Malgun Gothic", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("LOGIN");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, -1, -1));

        lbl_login.setFont(new java.awt.Font("Malgun Gothic", 1, 12)); // NOI18N
        lbl_login.setForeground(new java.awt.Color(0, 92, 143));
        lbl_login.setText("Login dengan Username dan Password");
        lbl_login.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_loginMouseClicked(evt);
            }
        });
        jPanel1.add(lbl_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 270, -1, 30));

        bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Gambar/Design_LoginRFID.jpg"))); // NOI18N
        jPanel1.add(bg, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 450, 300));

        txt_id.setForeground(new java.awt.Color(255, 255, 255));
        txt_id.setBorder(null);
        txt_id.setCaretColor(new java.awt.Color(255, 255, 255));
        txt_id.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_idKeyPressed(evt);
            }
        });
        jPanel1.add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 200, 140, 30));

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

    private void lbl_loginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_loginMouseClicked
        // TODO add your handling code here:
        this.dispose();
        Login_Frame login = new Login_Frame();
        login.setVisible(true);
    }//GEN-LAST:event_lbl_loginMouseClicked

    private void txt_idKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idKeyPressed
        // TODO add your handling code here:
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
           cekLogin();
        }
    }//GEN-LAST:event_txt_idKeyPressed

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
            java.util.logging.Logger.getLogger(RFID_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RFID_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RFID_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RFID_Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RFID_Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bg;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_login;
    private javax.swing.JPasswordField txt_id;
    // End of variables declaration//GEN-END:variables
}