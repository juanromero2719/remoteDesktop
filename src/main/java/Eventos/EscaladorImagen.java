/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Eventos;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author Estudiante_MCA
 */
public class EscaladorImagen {
    
    private double scaleX;
    private double scaleY;

    public void ajustarEscala(JLabel imageLabel, Dimension serverScreenSize) {
        
        if (serverScreenSize != null) {
            scaleX = (double) imageLabel.getWidth() / serverScreenSize.width;
            scaleY = (double) imageLabel.getHeight() / serverScreenSize.height;
        }
    }

    public Image escalarImagen(Image imagen, int ancho, int alto) {
        return imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
    }
    
    public void ajustarTamanoYCentro(JFrame frame, JLabel imageLabel, Image originalImage) {
        
        int labelWidth = (int) (frame.getWidth() * 0.8); // Tamaño al 80% de la ventana
        int labelHeight = (int) (frame.getHeight() * 0.8);
        int labelX = (frame.getWidth() - labelWidth) / 2;  // Centrar horizontalmente
        int labelY = (frame.getHeight() - labelHeight) / 2;  // Centrar verticalmente
        
        imageLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
        
        if (originalImage != null) {
            Image scaledImage = escalarImagen(originalImage, labelWidth, labelHeight);
            imageLabel.setIcon(new ImageIcon(scaledImage));
        }
        
        frame.revalidate();  // Actualizar el diseño de la ventana
        frame.repaint();
    }
    
}
