package Servicio;

import Manejador.ManejadorAccionesCliente;

/**
 * Servicio que conecta al servidor y configura el monitor de escritorio remoto.
 */

public class ClienteServicio {
    
    private ManejadorAccionesCliente manejadorAccionesCliente;

    public ClienteServicio() {
        this.manejadorAccionesCliente = new ManejadorAccionesCliente();
    }
    
    public void conectarServidor(int puerto, String direccionIp){

        manejadorAccionesCliente.conectarAlServidor(puerto, direccionIp);
        
    }

   
}
