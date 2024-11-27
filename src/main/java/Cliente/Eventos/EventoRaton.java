/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente.Eventos;

import Cliente.Interfaz.IEvento;

/**
 *
 * @author Estudiante_MCA
 */

public class EventoRaton implements IEvento {
    
    private String tipoEvento;
    private double xRelativo;
    private double yRelativo;
    private Integer button;

    public EventoRaton(String tipoEvento, double xRelativo, double yRelativo) {
        this.tipoEvento = tipoEvento;
        this.xRelativo = xRelativo;
        this.yRelativo = yRelativo;
        this.button = null;
    }
    
    public EventoRaton(String tipoEvento, Integer button) {
        this.tipoEvento = tipoEvento;
        this.button = button;
    }

    @Override
    public String generarEvento() {
        
        if (button != null) {
            return tipoEvento + ":" + button;  
        } else {
            return tipoEvento + ":" + xRelativo + ":" + yRelativo;
        }
    }
}
