/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import java.util.Properties;

/**
 *
 * @author Estudiante_MCA
 */
public class PropiedadesFabrica {
    
    private static Properties propiedades;

    private PropiedadesFabrica() {}

    public static Properties obtenerPropiedades() {
        
        if (propiedades == null) {
            propiedades = new Properties();
        }
        
        return propiedades;
    }
}



