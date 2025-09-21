package CapaLogica;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author santi
 */
public class Hashutil {
   
    public static String hashPassword(String pass){
    
        return BCrypt.hashpw(pass, BCrypt.gensalt(12));
    }
}
