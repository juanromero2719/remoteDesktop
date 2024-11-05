/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Servicio.ServidorServicio;

/**
 *
 * @author juanr
 */
public class ServidorControlador {
    
    private ServidorServicio servidorServicio;

    public ServidorControlador(){
        this.servidorServicio = new ServidorServicio();
    }
       
    public void iniciarServidor(int puerto) {              
        servidorServicio.iniciarServidor(puerto);       
    }

}
