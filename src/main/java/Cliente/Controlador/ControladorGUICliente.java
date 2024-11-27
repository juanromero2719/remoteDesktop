/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente.Controlador;

import Cliente.Servicio.ClienteServicio;
import java.awt.AWTException;
import java.io.IOException;

/**
 *
 * @author Estudiante_MCA
 */
public class ControladorGUICliente {
    
    private ClienteServicio clienteServicio;

    public ControladorGUICliente(){
        this.clienteServicio = new ClienteServicio();
    }
    
    public void ConectarServidor(int puerto, String direccionIp) throws IOException, AWTException, ClassNotFoundException {
               
        clienteServicio.conectarServidor(puerto, direccionIp);
        
    }
}
