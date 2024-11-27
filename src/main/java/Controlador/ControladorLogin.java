/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Cliente.Servicio.LoginServicio;
import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Estudiante_MCA
 */
public class ControladorLogin {
    
    private LoginServicio loginServicio;
    
    public ControladorLogin(){
        this.loginServicio = new LoginServicio();
    }
    
    public boolean ComprobarCredenciales(String usuario, String password) throws SQLException, IOException{
        return loginServicio.ComprobarCredenciales(usuario, password);
    }
    
    public long obtenerIdUsuario(String nombreUsuario) throws SQLException, IOException {
        return loginServicio.obtenerIdUsuario(nombreUsuario);
    }
}
