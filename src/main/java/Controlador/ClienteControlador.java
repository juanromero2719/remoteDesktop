/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Servicio.ClienteServicio;
import java.awt.AWTException;
import java.io.IOException;

/**
 *
 * @author Estudiante_MCA
 */
public class ClienteControlador {
    
    private ClienteServicio clienteServicio;

    public ClienteControlador(){
        this.clienteServicio = new ClienteServicio();
    }
    
    public void ConectarServidor(int puerto, String direccionIp) throws IOException, AWTException, ClassNotFoundException {
               
        clienteServicio.conectarServidor(puerto, direccionIp);
        
    }
}
