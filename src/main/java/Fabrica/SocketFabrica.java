/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author Estudiante_MCA
 */
public class SocketFabrica {
      
    private SocketFabrica() {}

    public static Socket obtenerSocket(String direccionIp, int puerto) throws IOException {      
        return new Socket(direccionIp, puerto);
    }
}
