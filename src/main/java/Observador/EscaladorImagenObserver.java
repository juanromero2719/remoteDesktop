/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Observador;

import Eventos.EscaladorImagen;
import Fabrica.EscaladorImagenFabrica;
import Cliente.Vista.ClienteRemotoVista;
import java.awt.Dimension;
import java.awt.Image;

/**
 *
 * @author Estudiante_MCA
 */
public class EscaladorImagenObserver implements IRedimensionObserver {

    private final ClienteRemotoVista interfazCliente;
    private final Dimension serverScreenSize;
    private static EscaladorImagen escaladorImagen;

    public EscaladorImagenObserver(ClienteRemotoVista interfazCliente, Dimension serverScreenSize) {
        
        this.interfazCliente = interfazCliente;
        this.serverScreenSize = serverScreenSize;
        this.escaladorImagen = EscaladorImagenFabrica.obtenerEscaladorImagen();
    }

    @Override
    public void onResize(Dimension newSize, Image originalImage) {
        
        escaladorImagen.ajustarTamanoYCentro(interfazCliente.getFrame(), interfazCliente.getImageLabel(), originalImage);
        escaladorImagen.ajustarEscala(interfazCliente.getImageLabel(), serverScreenSize);
    }
    
}
