/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Conexion.ConexionRemota;

/**
 *
 * @author Estudiante_MCA
 */
public class ServidorConexionFabrica {
    
    private ServidorConexionFabrica() {}

    public static ConexionRemota obtenerServidorConexion(String direccionIp, int puerto) {      
        return new ConexionRemota(direccionIp, puerto);
    }
}
