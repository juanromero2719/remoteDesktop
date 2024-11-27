/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor.Controlador;

import Servidor.Servicio.ServidorServicio;

/**
 *
 * @author juanr
 */
public class ControladorGUIServidor {
    
    private ServidorServicio servidorServicio;

    public ControladorGUIServidor(){
        this.servidorServicio = new ServidorServicio();
    }
       
    public void iniciarServidor(int puerto) throws Exception {              
        servidorServicio.iniciarServidor(puerto);       
    }

}
