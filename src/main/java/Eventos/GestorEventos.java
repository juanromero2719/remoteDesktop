/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Eventos;

import Cliente.Controlador.ControladorEventos;
import Cliente.Controlador.ControladorRed;
import Cliente.Vista.ClienteRemotoVista;

/**
 *
 * @author Estudiante_MCA
 */
public class GestorEventos {
    
    private ControladorEventos controladorEventos;
    private ControladorRed controladorRed;

    public void registrarEventos(ClienteRemotoVista vistaCliente) {
        controladorEventos.registrarEventos(vistaCliente.getFrame(), vistaCliente.getImageLabel());
    }
}
