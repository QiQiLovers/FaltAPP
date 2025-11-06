/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaPersistencia;

import CapaLogica.*;

/**
 *
 * @author tomas
 */
public class ClaseCompuesta {

    private Profe pro;
    private curso cur;
    private licencia lic;

    public ClaseCompuesta(Profe pro, curso cur, licencia lic) {
        this.pro = pro;
        this.lic = lic;
        this.cur = cur;

    }

    public Profe getPro() {
        return pro;
    }

    public void setPro(Profe pro) {
        this.pro = pro;
    }

    public curso getCur() {
        return cur;
    }

    public void setCur(curso cur) {
        this.cur = cur;
    }

    public licencia getLic() {
        return lic;
    }

    public void setLic(licencia lic) {
        this.lic = lic;
    }
}
