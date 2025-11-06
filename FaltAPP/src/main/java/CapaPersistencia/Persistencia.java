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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import CapaPersistencia.ClaseCompuesta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Santi
 */
public class Persistencia {
// Todo lo que necesita para andar de manera correcta

    public Conexion cone = new Conexion();
    public PreparedStatement ps;
    public ResultSet rs;

// Usuario
    private static final String CrearUsuario
            = "INSERT INTO FaltAPP.Administrador(Usuario,Contrasenia) VALUES(?,?)";

    private static final String IniciarSesion
            = "SELECT Contrasenia FROM FaltAPP.Administrador WHERE Usuario=?";

    // Profes
    private static final String BUSCAR_PROFES
            = "SELECT * FROM FaltAPP.Profesor WHERE CI = ?;";

    private static final String ANADIR_PROFES
            = "INSERT INTO FaltAPP.Profesor (CI, Nombre, Apellido) VALUES (?, ?, ?);";

    private static final String ELIMINAR_PROFES
            = "DELETE FROM FaltAPP.Profesor WHERE CI = ?;";

    private static final String MODIFICAR_PROFES
            = "UPDATE FaltAPP.Profesor SET Nombre = ?, Apellido = ? WHERE CI = ?;";

// Licencias
    private static final String BUSCAR_LICENCIAS
            = "SELECT * FROM FaltAPP.Inasistencia WHERE CiProfe = ? AND Fecha_inicio = ?;";

    private static final String ELIMINAR_LICENCIAS
            = "DELETE FROM FaltAPP.Inasistencia WHERE CiProfe = ? AND Fecha_inicio = ?;";

    private static final String ANADIR_LICENCIAS
            = "INSERT INTO FaltAPP.Inasistencia (CiProfe, Observacion, Fecha_inicio, Fecha_fin, Razon) VALUES (?, ?, ?, ?, ?);";

    private static final String MODIFICAR_LICENCIAS
            = "UPDATE FaltAPP.Inasistencia SET Observacion = ?, Fecha_fin = ?, Razon = ? WHERE CiProfe = ? AND Fecha_inicio = ?;";

    private static final String LLAMAR_TABLA
            = "SELECT Nombre,Apellido,Materia,ID,Fecha_inicio,Fecha_fin,Razon,Observacion FROM FaltAPP.Profesor p INNER JOIN FaltAPP.Curso c ON p.CI=c.Ci_Profesor INNER JOIN FaltAPP.Inasistencia i ON p.CI=i.CiProfe; ";

// Cursos
    private static final String ANADIR_CURSOS
            = "INSERT INTO FaltAPP.Curso (Ci_Profesor, ID, Materia) VALUES (?, ?, ?);";

    private static final String MODIFICAR_CURSOS
            = "UPDATE FaltAPP.Curso SET Materia = ? WHERE ID = ? AND Ci_Profesor = ?;";

    private static final String BUSCAR_CURSOS
            = "SELECT * FROM FaltAPP.Curso WHERE Ci_Profesor = ?;";

    private static final String ELIMINAR_CURSOS
            = "DELETE FROM FaltAPP.Curso WHERE ID = ? AND Ci_Profesor = ?;";

    //COSAS DE USU INI
    public boolean CrearUsu(String user, String contra) throws Exception, SQLException {

        String passhasheada = Hashutil.encriptarPass(contra);

        try {
            Connection con = cone.getConnection();

            ps = (PreparedStatement) con.prepareStatement(CrearUsuario);
            ps.setString(1, user);
            ps.setString(2, passhasheada);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(null, "Usuario Creado con exito");
            con.close();
            return true;

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

    //COSAS DE USU FIN
    // COSAS DE PROFE INI
    public void IngresarProfe(Profe pro) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {

            int resultado = 0;
            ps = con.prepareStatement(ANADIR_PROFES);
            ps.setInt(1, pro.getCI());
            ps.setString(2, pro.getNombre());
            ps.setString(3, pro.getApellido());
            resultado = ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error a la hora de añadir profes" + e.getMessage());
        }

    }

    public Profe BuscarProfe(Profe BusCI) throws Exception, SQLException {
        Profe pro = new Profe();
        try {

            Connection con = cone.getConnection();

            ps = con.prepareStatement(BUSCAR_PROFES);
            ps.setInt(1, BusCI.getCI());
            rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("Nombre");
                String Apellido = rs.getString("Apellido");

                pro.setNombre(nombre);
                pro.setApellido(Apellido);

            } else {
                throw new DBException("No se obtuvo el profesor");
            }

            JOptionPane.showMessageDialog(null, "Profe encontrado");
            con.close();

        } catch (Exception e) {
            throw new Exception("Error a la hora de buscar profe " + e.getMessage());
        }
        return pro;

    }

    public Profe EliminarProfe(Profe ci) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {
            int help = ci.getCI();
            ps = (PreparedStatement) con.prepareStatement(ELIMINAR_PROFES);
            ps.setInt(1, help);
            int filasEliminadas = ps.executeUpdate();

            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "Profesor eliminado con exito");
            } else {
                JOptionPane.showMessageDialog(null, "No se ah encontrado ningun profesor con ese ci: " + ci);
            }

        } catch (SQLException e) {
            throw new Exception("Error a la hora de borrar profesor " + e.getMessage());
        }
        return null;

    }

    public Profe ModificarProfe(int Ci, String nombre, String apellido) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {
            ps = con.prepareStatement(MODIFICAR_PROFES);
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setInt(3, Ci);
            int filasActu = ps.executeUpdate();

            if (filasActu > 0) {
                JOptionPane.showMessageDialog(null, "Datos modificados correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ah encontrado ningun profe con la ci:" + Ci);
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el profesor: " + e.getMessage());
        }
        return null;
    }

    // COSAS DE PROFE FIN
    // COSAS DE LICENCIA INI
    public void IngresarLicencias(licencia lis) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {
            int resultado = 0;
            ps = (PreparedStatement) con.prepareStatement(ANADIR_LICENCIAS);
            ps.setInt(1, lis.getCI());
            ps.setString(2, lis.getAclaracion());
            ps.setDate(3, new java.sql.Date(lis.getPeriodoInicio().getTime()));
            ps.setDate(4, new java.sql.Date(lis.getPeriodoFin().getTime()));
            ps.setString(5, lis.getRazon());
            resultado = ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error a la hora de añadir licencias " + e.getMessage());
        }

    }

    public licencia BuscarLicencia(licencia bus) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {

            ps = (PreparedStatement) con.prepareStatement(BUSCAR_LICENCIAS);
            ps.setInt(1, bus.getCI());
            ps.setDate(2, (Date) bus.getPeriodoInicio());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int ci = rs.getInt("CiProfe");
                    String observacion = rs.getString("Observacion");
                    Date fechaFin = rs.getDate("Fecha_fin");
                    Date fechaInicio = rs.getDate("Fecha_inicio");
                    String Razon = rs.getString("Razon");

                    bus.setCI(ci);
                    bus.setAclaracion(observacion);
                    bus.setPeriodoFin(fechaFin);
                    bus.setPeriodoInicio(fechaInicio);
                    bus.setRazon(Razon);
                }

            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la inasistencia: " + e.getMessage());
        }

        return bus;
    }

    public licencia EliminarLicencia(int ci, Date fechaIni) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {
            ps = (PreparedStatement) con.prepareStatement(ELIMINAR_LICENCIAS);
            ps.setInt(1, ci);
            ps.setDate(2, fechaIni);
            int filasEliminadas = ps.executeUpdate();

            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "Licencia eliminada");
            } else {
                JOptionPane.showMessageDialog(null, "No se ah encontrado ninguna licencia con ese ci: " + ci + "\nNi con esa fecha de inicio: " + fechaIni);
            }

        } catch (SQLException e) {
            throw new Exception("Error a la hora de borrar licencia " + e.getMessage());
        }
        return null;

    }

    public licencia ModificarLicencias(int Ciprofe, String obser, Date fechaFin, Date fechaIni, String Razon) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {

            ps = con.prepareStatement(MODIFICAR_LICENCIAS);
            ps.setString(1, obser);
            ps.setDate(2, (Date) fechaFin);
            ps.setString(3, Razon);
            ps.setInt(4, Ciprofe);
            ps.setDate(5, (Date) fechaIni);
            int filasActu = ps.executeUpdate();
            if (filasActu > 0) {
                JOptionPane.showMessageDialog(null, "Datos modificados correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ah encontrado ninguna licencia con el ci:" + Ciprofe);
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar el profesor: " + e.getMessage());
        }
        return null;

    }

    public List<ClaseCompuesta> DatosHorarios() throws SQLException {
        List<ClaseCompuesta> lista = new ArrayList<>();

        try (Connection con = cone.getConnection()) {
            ps = (PreparedStatement) con.prepareStatement(LLAMAR_TABLA);
            rs = ps.executeQuery();
            while (rs.next()) {
                Profe pro = new Profe();
                pro.setNombre(rs.getString("Nombre"));
                pro.setApellido(rs.getString("Apellido"));

                curso cur = new curso();
                cur.setMateria(rs.getString("Materia"));
                cur.setId(rs.getString("ID"));

                licencia li = new licencia();
                li.setPeriodoInicio(rs.getDate("Fecha_inicio"));
                li.setPeriodoFin(rs.getDate("Fecha_fin"));
                li.setRazon(rs.getString("Razon"));
                li.setAclaracion(rs.getString("Observacion"));

                lista.add(new ClaseCompuesta(pro, cur, li));

            }

        }
        return lista;
    }
    // COSAS DE LICENCIA FIN

    //COSAS DE CURSOS INI
    public void IngresarCurso(curso cur) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {
            int resultado = 0;
            ps = (PreparedStatement) con.prepareStatement(ANADIR_CURSOS);
            ps.setInt(1, cur.getCIProfe());
            ps.setString(2, cur.getId());
            ps.setString(3, cur.getMateria());
            resultado = ps.executeUpdate();

        } catch (SQLException e) {
            throw new Exception("Error a la hora de añadir el curso" + e.getMessage());
        }

    }

    public curso BuscarCurso(curso cur) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {

            ps = (PreparedStatement) con.prepareStatement(BUSCAR_CURSOS);
            ps.setInt(1, cur.getCIProfe());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                   cur.setMateria(rs.getString("Materia"));
                   cur.setId(rs.getString("ID"));
                }

        }
        return cur;
    }
    }
    public curso EliminarCurso(String id, int Ci) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {
            ps = con.prepareStatement(ELIMINAR_CURSOS);
            ps.setString(1, id);
            ps.setInt(2, Ci);
            int filasEliminadas = 0;
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "Curso eliminado");
            } else {
                JOptionPane.showMessageDialog(null, "No se ah encontrado ningun curso con ese ci: " + Ci + "\nNi con ese id" + id);
            }
        }
        return null;
    }

    public curso ModificarCurso(String Materia, String Id, int ci) throws Exception, SQLException {

        try (Connection con = cone.getConnection()) {
            ps = con.prepareStatement(MODIFICAR_CURSOS);
            ps.setString(1, Materia);
            ps.setString(2, Id);
            ps.setInt(3, ci);
            int filasActu = ps.executeUpdate();

            if (filasActu > 0) {
                JOptionPane.showMessageDialog(null, "Datos modificados correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "No se ah encontrado ningun curso con el id: " + Id);
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar curso: " + e.getMessage());
        }
        return null;

    }

    //COSAS DE CURSO FIN
}
