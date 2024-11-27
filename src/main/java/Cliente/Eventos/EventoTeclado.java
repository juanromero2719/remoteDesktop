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

public class EventoTeclado implements IEvento {
    private String tipoEvento;
    private int keyCode;

    public EventoTeclado(String tipoEvento, int keyCode) {
        this.tipoEvento = tipoEvento;
        this.keyCode = keyCode;
    }

    @Override
    public String generarEvento() {
        return tipoEvento + ":" + keyCode;
    }
}
