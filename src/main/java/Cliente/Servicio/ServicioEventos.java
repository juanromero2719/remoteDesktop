/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente.Servicio;

/**
 *
 * @author Estudiante_MCA
 */

import Cliente.Controlador.ControladorRed;
import Cliente.Eventos.EventoRaton;
import Cliente.Eventos.EventoTeclado;
import Cliente.Interfaz.IEvento;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServicioEventos {

    private ControladorRed controladorRed;

    public ServicioEventos(ControladorRed controladorRed) {
        this.controladorRed = controladorRed;
    }

    public void registrarEventos(JFrame frame, JLabel imageLabel) {

        frame.setFocusable(true);
        frame.requestFocusInWindow();
        
        frame.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                procesarEventoTeclado("KEY_PRESS", e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                procesarEventoTeclado("KEY_RELEASE", e.getKeyCode());
            }
        });

        imageLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double relativeX = (double) e.getX() / imageLabel.getWidth();
                double relativeY = (double) e.getY() / imageLabel.getHeight();
                procesarEventoRatonMovimiento(relativeX, relativeY);
            }
        });

        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int button = e.getButton();
                procesarEventoRatonClic(button);
            }
        });
    }

    public void procesarEventoTeclado(String tipoEvento, int keyCode) {
        IEvento evento = new EventoTeclado(tipoEvento, keyCode);
        controladorRed.enviarEvento(evento);
    }

    public void procesarEventoRatonMovimiento(double relativeX, double relativeY) {
        IEvento evento = new EventoRaton("MOUSE_MOVE", relativeX, relativeY);
        controladorRed.enviarEvento(evento);
    }

    public void procesarEventoRatonClic(int button) {
        IEvento evento = new EventoRaton("MOUSE_CLICK", button);
        controladorRed.enviarEvento(evento);
    }
}