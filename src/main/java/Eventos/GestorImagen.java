/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Eventos;

import Cliente.Vista.ClienteRemotoVista;
import Observador.EscaladorImagenObserver;
import Eventos.EscaladorImagen;
import Fabrica.EscaladorImagenFabrica;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
/**
 *
 * @author Estudiante_MCA
 */
public class GestorImagen {
    
    private ClienteRemotoVista vistaCliente;
    private EscaladorImagen escaladorImagen;
    private EscaladorImagenObserver escaladorObserver;

    public GestorImagen(ClienteRemotoVista vistaCliente, Dimension serverScreenSize) {
        this.vistaCliente = vistaCliente;
        this.escaladorObserver = new EscaladorImagenObserver(vistaCliente, serverScreenSize);
        vistaCliente.addObserver(escaladorObserver);
    }

    public void actualizarImagen(byte[] screenshotBytes) {
    
        
         if (screenshotBytes != null && screenshotBytes.length > 0) {
                        
                        System.out.println("Recibidos " + screenshotBytes.length + " bytes de la imagen.");
                        Image originalImage = new ImageIcon(screenshotBytes).getImage(); // Almacena la imagen original
                        
                        // Escala y muestra la imagen en el JLabel
                        Image scaledImage = EscaladorImagenFabrica.obtenerEscaladorImagen().escalarImagen(
                            originalImage,
                            vistaCliente.getImageLabel().getWidth(),
                            vistaCliente.getImageLabel().getHeight()
                        );
                                           
                        SwingUtilities.invokeLater(() -> {
                            vistaCliente.getImageLabel().setIcon(new ImageIcon(scaledImage));
                            vistaCliente.getImageLabel().repaint();
                        });
                        
                    } else {
                        System.out.println("No se recibieron datos de imagen.");
                    }      
            
    }
}
