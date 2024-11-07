/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Conexion.ServidorConexion;

/**
 *
 * @author Estudiante_MCA
 */
public class ServidorConexionFabrica {
    
    private ServidorConexionFabrica() {}

    public static ServidorConexion obtenerServidorConexion(String direccionIp, int puerto) {      
        return new ServidorConexion(direccionIp, puerto);
    }
}
