/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manejador;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;

/**
 *
 * @author Estudiante_MCA
 */
public class ControladorEventos {
    
    private PrintWriter outputWriter;

    public ControladorEventos(Socket socket) {
        try {
            outputWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error al obtener el flujo de salida: " + e.getMessage());
        }
    }

    public void registrarEventos(JFrame frame, JLabel imageLabel) {
        // Asegurar que el frame tiene foco para capturar eventos de teclado
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        // Agregar listeners de teclado
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                enviarEvento("KEY_PRESS:" + e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                enviarEvento("KEY_RELEASE:" + e.getKeyCode());
            }
        });

        // Eventos de movimiento del mouse
        imageLabel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                enviarMovimientoMouse(e, imageLabel);
            }
        });

        // Eventos de clic del mouse
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                enviarEvento("MOUSE_CLICK:" + e.getButton());
            }
        });
    }

    private void enviarEvento(String evento) {
        if (outputWriter != null) {
            System.out.println("Enviando evento: " + evento); // Depuración
            outputWriter.println(evento);
            outputWriter.flush(); // Asegura que el evento se envía inmediatamente
        }
    }

    private void enviarMovimientoMouse(MouseEvent e, JLabel imageLabel) {
        Point p = e.getPoint();
        if (p.x >= 0 && p.x <= imageLabel.getWidth() && p.y >= 0 && p.y <= imageLabel.getHeight()) {
            double relativeX = (double) p.x / imageLabel.getWidth();
            double relativeY = (double) p.y / imageLabel.getHeight();
            enviarEvento("MOUSE_MOVE:" + relativeX + ":" + relativeY);
        }
    }
}
