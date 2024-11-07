/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Vista.ClienteRemotoVista;

/**
 *
 * @author Estudiante_MCA
 */
public class ClienteRemotoFabrica {
    
    private static ClienteRemotoVista clienteRemotoVista;

    private ClienteRemotoFabrica() {}

    public static ClienteRemotoVista obtenerInterfazCliente() {
        
        if (clienteRemotoVista == null) {
            clienteRemotoVista = new ClienteRemotoVista();
        }
        return clienteRemotoVista;
    }
}
