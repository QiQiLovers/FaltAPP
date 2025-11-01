/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaPersistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import CapaLogica.Hashutil;
import CapaPersistencia.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CrearPrimerUsu {

    public Conexion cone = new Conexion();
    public PreparedStatement ps;
    private static final String CrearUsuario = "INSERT INTO FaltAPP.Administrador(Usuario,Contrasenia) VALUES(?,?)";
    private static final String IniciarSesion = "SELECT Contrasenia FROM FaltAPP.Administrador WHERE Usuario = ?";
    private String user = "Admin"; //El primer usuario creado es User=Admin y contra=1234 ESTO MISMO NO SE PUEDE REPETIR, PORQUE EN LA DB SOLO SE PERMITE UN USUSRIO UNICO
    private String contra = "123";

    public void CrearUsu() throws Exception, SQLException {

        String passhasheada = Hashutil.encriptarPass(contra);

        try {
            Connection con = cone.getConnection();

            ps = (PreparedStatement) con.prepareStatement(CrearUsuario);
            ps.setString(1, user);
            ps.setString(2, passhasheada);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario Creado con exito");
            con.close();

        } catch (Exception e) {
            throw new Exception("Error a la hora de crear usuario " + e.getMessage());
        }

    }

    public boolean loginUsu(String username, String password) throws SQLException {

        try (
                Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(IniciarSesion)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("Contrasenia");
                return Hashutil.checkPass(password, hash);
            }

            con.close();
        } catch (SQLException e) {
            throw new SQLException("Error a la hora de iniciar sesion " + e.getMessage());

        }
        return false;
    }

    public static void main(String[] args) throws Exception {
        CrearPrimerUsu usu=new CrearPrimerUsu();
        usu.CrearUsu();
    }

}
