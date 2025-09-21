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
   
    public static String encriptarPass(String pass){
    
        return BCrypt.hashpw(pass, BCrypt.gensalt(12));
    }
    
     public static boolean checkPass(String pass, String hash) {
        return BCrypt.checkpw(pass, hash);
    }
}
