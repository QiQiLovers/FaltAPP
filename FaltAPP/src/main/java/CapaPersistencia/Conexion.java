/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CapaPersistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author santi
 */
public class Conexion {
    
    
    public static Connection getConnection() throws SQLException{
        Connection con=null;
        try{
        con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=CONVERT_TO_NULL","root","");
        
        }catch(SQLException sqle){
            throw new SQLException("Error");
        }
        return con;
    }
    

}
