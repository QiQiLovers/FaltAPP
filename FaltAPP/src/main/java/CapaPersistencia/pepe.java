/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaPersistencia;

/**
 *
 * @author santi
 */
public class pepe {
    public static void main(String[] args) {
        CrearUsuario jose=new CrearUsuario();
        try {
            jose.CrearUsu();
        } catch (Exception ex) {
            System.getLogger(pepe.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }
}
