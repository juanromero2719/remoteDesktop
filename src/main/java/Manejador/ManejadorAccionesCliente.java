package Manejador;

import Cliente.Controlador.ControladorEventos;
import Eventos.EscaladorImagen;
import Cliente.Controlador.ControladorRed;
import Conexion.ConexionRemota;


import Cliente.Vista.ClienteRemotoVista;

import javax.swing.*;
import java.awt.*;
import java.io.*;


import Fabrica.GUIClienteRemotoFabrica;
import Fabrica.EscaladorImagenFabrica;
import Fabrica.PrintWriterFabrica;
import Fabrica.ServidorConexionFabrica;
import Fabrica.SocketFabrica;
import Observador.EscaladorImagenObserver;
import java.net.Socket;
/**
 * Clase encargada de manejar las acciones y visualización del escritorio remoto.
 */
public class ManejadorAccionesCliente extends JPanel {

    private static Dimension serverScreenSize;
    private static ClienteRemotoVista interfazCliente;
    private static EscaladorImagenObserver escaladorObserver;
    private static ConexionRemota conexionRemota;
    private static Image originalImage;

    public static void conectarAlServidor(int puerto, String direccionIp) {
        
        interfazCliente = GUIClienteRemotoFabrica.obtenerInterfazCliente();
        conexionRemota = ServidorConexionFabrica.obtenerServidorConexion(direccionIp, puerto);
                
        new Thread(() -> {
            
            try (ObjectInputStream inputStream = conexionRemota.conectar()) {
                
                PrintWriter outputWriter = PrintWriterFabrica.obtenerPrintWriter(conexionRemota.getSocket().getOutputStream());
                               
                ControladorRed controladorRed = new ControladorRed(conexionRemota.getSocket());
                ControladorEventos controladorEventos = new ControladorEventos(controladorRed);  
                controladorEventos.registrarEventos(interfazCliente.getFrame(), interfazCliente.getImageLabel());
                
                serverScreenSize = (Dimension) inputStream.readObject();             
                escaladorObserver = new EscaladorImagenObserver(interfazCliente, serverScreenSize);
                interfazCliente.addObserver(escaladorObserver);

                while (true) {
                    
                    outputWriter.println("SCREENSHOT");
                    outputWriter.flush();
            
                    byte[] screenshotBytes = (byte[]) inputStream.readObject();
                    
                    if (screenshotBytes != null && screenshotBytes.length > 0) {
                        
                        originalImage = new ImageIcon(screenshotBytes).getImage(); 
                        
                        Image scaledImage = EscaladorImagenFabrica.obtenerEscaladorImagen().escalarImagen(
                            originalImage,
                            interfazCliente.getImageLabel().getWidth(),
                            interfazCliente.getImageLabel().getHeight()
                        );
                                           
                        SwingUtilities.invokeLater(() -> {
                            interfazCliente.getImageLabel().setIcon(new ImageIcon(scaledImage));
                            interfazCliente.getImageLabel().repaint();
                        });
                        
                    } 
                    
                }
                
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al conectar con el servidor: " + e.getMessage());
            }
        }).start();
    }

}
