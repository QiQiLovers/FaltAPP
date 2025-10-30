/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaPersistencia;

import CapaExepcion.DBException;
import CapaLogica.Hashutil;
import CapaLogica.licencia;
import CapaLogica.Profe;
import CapaLogica.curso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
/**
 *
 * @author tomas
 */
public class Persistencia {

    
    public class CrearUsuario{
    public Conexion cone=new Conexion();
    public PreparedStatement ps;
    public ResultSet rs;
    private static final String CrearUsuario="INSERT INTO FaltAPP.Administrador(Usuario,Contrasenia) VALUES(?,?)";
    private static final String IniciarSesion = "SELECT Contrasenia FROM FaltAPP.Administrador WHERE Usuario=?";
    private String user="Admin"; //El primer usuario creado es User=Admin y contra=1234 ESTO MISMO NO SE PUEDE REPETIR, PORQUE EN LA DB SOLO SE PERMITE UN USUSRIO UNICO
    private String contra="123";

// Profes

private static final String BUSCAR_PROFES = 
    "SELECT * FROM FaltAPP.Profesor WHERE CI = ?;";

private static final String ANADIR_PROFES = 
    "INSERT INTO FaltAPP.Profesor (CI, Nombre, Apellido) VALUES (?, ?, ?);";

private static final String ELIMINAR_PROFES = 
    "DELETE FROM FaltAPP.Profesor WHERE CI = ?;";

private static final String MODIFICAR_PROFES = 
    "UPDATE FaltAPP.Profesor SET Nombre = ?, Apellido = ? WHERE CI = ?;";


// Licencias

private static final String BUSCAR_LICENCIAS = 
    "SELECT * FROM FaltAPP.Inasistencia WHERE CiProfe = ? AND Fecha_inicio = ?;";

private static final String ELIMINAR_LICENCIAS = 
    "DELETE FROM FaltAPP.Inasistencia WHERE CiProfe = ? AND Fecha_inicio = ?;";

private static final String ANADIR_LICENCIAS = 
    "INSERT INTO FaltAPP.Inasistencia (CiProfe, Observacion, Fecha_inicio, Fecha_fin, Razon) VALUES (?, ?, ?, ?, ?);";

private static final String MODIFICAR_LICENCIAS = 
    "UPDATE FaltAPP.Inasistencia SET Observacion = ?, Fecha_fin = ?, Razon = ? WHERE CiProfe = ? AND Fecha_inicio = ?;";


// Cursos
private static final String ANADIR_CURSOS = 
    "INSERT INTO FaltAPP.Curso (Ci_Profesor, Materia, Grupo) VALUES (?, ?, ?);";

private static final String MODIFICAR_CURSOS = 
    "UPDATE FaltAPP.Curso SET Materia = ?, Grupo = ? WHERE ID = ? AND Ci_Profesor = ?;";

private static final String BUSCAR_CURSOS = 
    "SELECT * FROM FaltAPP.Curso WHERE ID = ? AND Ci_Profesor = ?;";

private static final String ELIMINAR_CURSOS = 
    "DELETE FROM FaltAPP.Curso WHERE ID = ? AND Ci_Profesor = ?;";

    
    
    public void CrearUsu()throws Exception,SQLException{
        
        String passhasheada=Hashutil.encriptarPass(contra);
        
        try{
            Connection con=cone.getConnection();
            
            ps=(PreparedStatement)con.prepareStatement(CrearUsuario);
            ps.setString(1, user);
            ps.setString(2, passhasheada);
            ps.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Usuario Creado con exito");
            con.close();
        
        }catch (Exception e) {
            throw new Exception("Error a la hora de crear usuario "+e.getMessage());
        }


    }
    
    public boolean loginUsu(String username, String password) throws SQLException {
    
        try (            
            Connection con=Conexion.getConnection();
            PreparedStatement ps = con.prepareStatement(IniciarSesion)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hash = rs.getString("Contrasenia");
                return Hashutil.checkPass(password, hash);
            }

            con.close();
        } catch (SQLException e) {
            throw new SQLException("Error a la hora de iniciar sesion "+e.getMessage());

        }
        return false;
    }

    public void BuscarProfe(Profe BusCI) throws Exception{
    try{
            Connection con=cone.getConnection();
            
            ps=(PreparedStatement)con.prepareStatement(BUSCAR_PROFES);
            ps.setInt(1, BusCI.getCI());
            ps.executeUpdate();
            rs=ps.executeQuery();
            if (rs.next()) {
                int Ci=rs.getInt("CI");
                String nombre= rs.getString("Nombre"); 
                String Apellido= rs.getString("Apellido");

            }else{
                throw new DBException("No se obtuvo el profesor");                
            }
            
            JOptionPane.showMessageDialog(null, "Profe encontrado");
            con.close();
        
        }catch (Exception e) {
            throw new Exception("Error a la hora de buscar profe "+e.getMessage());
        }
    
    }
    
    
    
    
    
    }
}
