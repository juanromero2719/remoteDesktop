/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import Eventos.EscaladorImagen;

/**
 *
 * @author Estudiante_MCA
 */
public class EscaladorImagenFabrica {
    
    private static EscaladorImagen escaladorImagen;

    private EscaladorImagenFabrica() {}

    public static EscaladorImagen obtenerEscaladorImagen() {
        
        if (escaladorImagen == null) {
            escaladorImagen = new EscaladorImagen();
        }
        return escaladorImagen;
    }
}
