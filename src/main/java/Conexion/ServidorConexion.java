/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexion;

import java.io.*;
import java.net.*;

/**
 *
 * @author Estudiante_MCA
 */
public class ServidorConexion {
    
    private String direccionIp;
    private int puerto;
    private Socket socket;

    public ServidorConexion(String direccionIp, int puerto) {
        this.direccionIp = direccionIp;
        this.puerto = puerto;
    }

    public ObjectInputStream conectar() throws IOException {
        System.out.println("Intentando conectar al servidor en " + direccionIp + ":" + puerto);
        socket = new Socket(direccionIp, puerto);
        System.out.println("Conexión exitosa al servidor");
        return new ObjectInputStream(socket.getInputStream());
    }

    public Socket getSocket() {
        return socket;
    }

    // Método enviarEvento puede permanecer igual
}
