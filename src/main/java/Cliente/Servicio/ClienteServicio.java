package Cliente.Servicio;

import Manejador.ManejadorAccionesCliente;

public class ClienteServicio {
    
    private ManejadorAccionesCliente manejadorAccionesCliente;

    public ClienteServicio() {
        this.manejadorAccionesCliente = new ManejadorAccionesCliente();
    }
    
    public void conectarServidor(int puerto, String direccionIp){

        manejadorAccionesCliente.conectarAlServidor(puerto, direccionIp);
        
    }

   
}
