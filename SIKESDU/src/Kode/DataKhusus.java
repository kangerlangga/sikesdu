package Kode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author SIKESDU Developer
 */
class DataKhusus {
    
    //Kumpulan Variabel Private untuk menyimpan data tertentu
    private static String id_pegawai;
    private static String nama_login;
    private static String jabatan_login;
    private String urlDB = "jdbc:mysql://localhost:3306/sikesdu";
    private String userDB = "root";
    private String passDB = "";
    private String nomorPos = "017";
    private Connection sambungMySQL;
    
    //Method khusus untuk MENGAMBIL data user yang telah tersimpan
    //Untuk Kebutuhan di Frame lain dalam Package yang sama
    //Data user yang telah tersimpan pada variabel didalam class ini
    protected String get_id(){
        return id_pegawai;
    }
    
    protected String get_nomorPos(){
        return nomorPos;
    }
    
    protected String get_nama(){
        return nama_login;
    }
    
    protected String get_jabatan(){
        return jabatan_login;
    }

    //==========================================================================
    
    //Method khusus untuk MENYIMPAN data user yang telah berhasil login
    //Data user akan disimpan di variabel private didalam class ini
    protected void set_idPegawai(String id_pegawai1){
        id_pegawai = id_pegawai1;
    }
     
    protected void set_nama(String nama){
        nama_login = nama;
    }
    
    protected void set_jabatan(String jabatan){
        jabatan_login = jabatan;
    }
    
    //==========================================================================
    
    //Sambung Database Lokal MySQL
    public Connection sambungDB(){
        try{
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            sambungMySQL = DriverManager.getConnection(urlDB, userDB, passDB);
        }catch(SQLException e){
            System.err.println("Koneksi Gagal "+e.getMessage());
        }
        return sambungMySQL;
    }
}
