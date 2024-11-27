/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente.Controlador;

import Cliente.Servicio.ServicioEventos;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 *
 * @author Estudiante_MCA
 */
public class ControladorEventos {

    private ServicioEventos servicioEventos;

    public ControladorEventos(ControladorRed controladorRed) {
        this.servicioEventos = new ServicioEventos(controladorRed);
    }

    public void registrarEventos(JFrame frame, JLabel imageLabel) {
        servicioEventos.registrarEventos(frame, imageLabel);
    }
}