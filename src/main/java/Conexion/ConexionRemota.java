/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import Fabrica.SocketFabrica;
import java.io.*;
import java.net.*;

/**
 *
 * @author Estudiante_MCA
 */
public class ConexionRemota {
    
    private String direccionIp;
    private int puerto;
    private Socket socket;

    public ConexionRemota(String direccionIp, int puerto) {
        this.direccionIp = direccionIp;
        this.puerto = puerto;
    }

    public ObjectInputStream conectar() throws IOException {
               
        socket = SocketFabrica.obtenerSocket(direccionIp, puerto);
        return new ObjectInputStream(socket.getInputStream());
        
    }

    public Socket getSocket() {
        return socket;
    }

}
