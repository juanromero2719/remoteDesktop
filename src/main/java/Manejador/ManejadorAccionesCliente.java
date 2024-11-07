package Manejador;

import Eventos.EscaladorImagen;
import Eventos.ControladorEventos;
import Conexion.ServidorConexion;

import Vista.ClienteRemotoVista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


import Fabrica.ClienteRemotoFabrica;
import Fabrica.EscaladorImagenFabrica;
import Fabrica.ServidorConexionFabrica;
import Observador.EscaladorImagenObserver;
/**
 * Clase encargada de manejar las acciones y visualización del escritorio remoto.
 */
public class ManejadorAccionesCliente extends JPanel {

    private static Dimension serverScreenSize;
    private static ClienteRemotoVista interfazCliente;
    private static EscaladorImagenObserver escaladorObserver;
    private static ServidorConexion servidorConexion;
    private static Image originalImage;

    public static void conectarAlServidor(int puerto, String direccionIp) {
        
        interfazCliente = ClienteRemotoFabrica.obtenerInterfazCliente();
        servidorConexion = ServidorConexionFabrica.obtenerServidorConexion(direccionIp, puerto);
                
        new Thread(() -> {
            try (ObjectInputStream inputStream = servidorConexion.conectar()) {
                
                PrintWriter outputWriter = new PrintWriter(servidorConexion.getSocket().getOutputStream(), true);
                        
                ControladorEventos controladorEventos = new ControladorEventos(servidorConexion.getSocket());
                controladorEventos.registrarEventos(interfazCliente.getFrame(), interfazCliente.getImageLabel());
                
                serverScreenSize = (Dimension) inputStream.readObject();             
                escaladorObserver = new EscaladorImagenObserver(interfazCliente, serverScreenSize);
                interfazCliente.addObserver(escaladorObserver);

                while (true) {
                    
                    outputWriter.println("SCREENSHOT");
                    outputWriter.flush();
            
                    byte[] screenshotBytes = (byte[]) inputStream.readObject();
                    
                    if (screenshotBytes != null && screenshotBytes.length > 0) {
                        
                        System.out.println("Recibidos " + screenshotBytes.length + " bytes de la imagen.");
                        originalImage = new ImageIcon(screenshotBytes).getImage(); // Almacena la imagen original
                        
                        // Escala y muestra la imagen en el JLabel
                        Image scaledImage = EscaladorImagenFabrica.obtenerEscaladorImagen().escalarImagen(
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
