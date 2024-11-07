package Manejador;

import Conexion.ServidorConexion;
import Vista.InterfazCliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * Clase encargada de manejar las acciones y visualización del escritorio remoto.
 */
public class ManejadorAccionesCliente extends JPanel {

    private static Dimension serverScreenSize;
    private static InterfazCliente interfazCliente;
    private static EscaladorImagen escaladorImagen;
    private static ServidorConexion servidorConexion;
    private static Image originalImage;

    public static void conectarAlServidor(int puerto, String direccionIp) {
        
        interfazCliente = new InterfazCliente();
        escaladorImagen = new EscaladorImagen();
        servidorConexion = new ServidorConexion(direccionIp, puerto);
        
        interfazCliente.getFrame().addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                escaladorImagen.ajustarTamanoYCentro(interfazCliente.getFrame(), interfazCliente.getImageLabel(), originalImage);
                escaladorImagen.ajustarEscala(interfazCliente.getImageLabel(), serverScreenSize);
            }
        });
           
        new Thread(() -> {
            try (ObjectInputStream inputStream = servidorConexion.conectar()) {
                
                PrintWriter outputWriter = new PrintWriter(servidorConexion.getSocket().getOutputStream(), true);
                
        
                ControladorEventos controladorEventos = new ControladorEventos(servidorConexion.getSocket());
                controladorEventos.registrarEventos(interfazCliente.getFrame(), interfazCliente.getImageLabel());
                
                serverScreenSize = (Dimension) inputStream.readObject();

                while (true) {
                    
                    outputWriter.println("SCREENSHOT");
                    outputWriter.flush();
            
                    byte[] screenshotBytes = (byte[]) inputStream.readObject();
                    
                    if (screenshotBytes != null && screenshotBytes.length > 0) {
                        
                        System.out.println("Recibidos " + screenshotBytes.length + " bytes de la imagen.");
                        originalImage = new ImageIcon(screenshotBytes).getImage(); // Almacena la imagen original
                        
                        // Escala y muestra la imagen en el JLabel
                        Image scaledImage = escaladorImagen.escalarImagen(
                            originalImage,
                            interfazCliente.getImageLabel().getWidth(),
                            interfazCliente.getImageLabel().getHeight()
                        );
                                           
                        SwingUtilities.invokeLater(() -> {
                            interfazCliente.getImageLabel().setIcon(new ImageIcon(scaledImage));
                            interfazCliente.getImageLabel().repaint();
                        });
                        
                    } else {
                        System.out.println("No se recibieron datos de imagen.");
                    }                 
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al conectar con el servidor: " + e.getMessage());
            }
        }).start();
    }

}
