/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaLogica;

import CapaPersistencia.Persistencia;
import java.sql.Date;

/**
 *
 * @author Tomas
 */
public class fachada {

    // Profes
    public static void IngresarProfe(Profe ci) throws Exception {

        Persistencia Ingre = new Persistencia();
        Ingre.IngresarProfe(ci);

    }

    public static Profe BuscarProfe(Profe CI) throws Exception {
        Persistencia per = new Persistencia();
        CI= per.BuscarProfe(CI);
        return CI;

    }

    public static Profe EliminarProfe(Profe Ci) throws Exception {
        Persistencia borrarPro = new Persistencia();
        Ci = borrarPro.EliminarProfe(Ci);
        return Ci;

    }

    public static Profe ModificarProfe(int Ci, String Nombre, String apellido) throws Exception {
        Persistencia modProf = new Persistencia();
        Profe prof = new Profe();
        prof = modProf.ModificarProfe(Ci, Nombre, apellido);
        return prof;

    }

    // Licencias
    public static void IngresarLicencia(licencia inas) throws Exception {
        Persistencia Ingre = new Persistencia();
        Ingre.IngresarLicencias(inas);

    }

    public static licencia BuscarLicencia(licencia buscarLicencia) throws Exception {
        Persistencia per = new Persistencia();
        buscarLicencia = per.BuscarLicencia(buscarLicencia);
        return buscarLicencia;
    }

    public static licencia EliminarLicencia(int Ci, Date fechaIni) throws Exception {
        licencia good = null;
        good.setCI(Ci);
        good.setPeriodoInicio(fechaIni);
        Persistencia per = new Persistencia();
        good = per.EliminarLicencia(Ci, fechaIni);
        return good;
    }

    public static licencia ModificarLicencia(int Ciprofe, String obser, Date fechaFin, Date fechaIni, String Razon) throws Exception {
        Persistencia modLice = new Persistencia();
        licencia Lice = new licencia();
        Lice = modLice.ModificarLicencias(Ciprofe, obser, fechaFin, fechaIni, Razon);
        return Lice;
    }

    // Cursos
    public static void IngresarCurso(curso Curs) throws Exception {
        Persistencia Ingre = new Persistencia();
        Ingre.IngresarCurso(Curs);

    }

    public static curso BuscarCurso(curso BuscarCurso) throws Exception {
        Persistencia per = new Persistencia();
        BuscarCurso = per.BuscarCurso(BuscarCurso);
        return BuscarCurso;
    }

    public static curso EliminarCurso(String id, int Ci) throws Exception {
        curso pe = null;
        pe.setCIProfe(Ci);
        pe.setId(id);
        Persistencia per = new Persistencia();
        pe = per.EliminarCurso(id, Ci);
        return pe;
    }

    public static curso ModificarCurso(String Materia, String Id, int ci) throws Exception {
        Persistencia modCur = new Persistencia();
        curso cur = new curso();
        cur = modCur.ModificarCurso(Materia, Id, ci);
        return cur;
    }

}
