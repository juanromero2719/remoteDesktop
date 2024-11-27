/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente.Controlador;

import Cliente.Interfaz.IEvento;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Estudiante_MCA
 */

public class ControladorRed {
    
    private PrintWriter outputWriter;

    public ControladorRed(Socket socket) throws IOException {
        outputWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    public void enviarEvento(IEvento evento) {
        
        if (outputWriter != null) {
            String eventoStr = evento.generarEvento(); 
            System.out.println("Enviando evento: " + eventoStr);
            outputWriter.println(eventoStr);
            outputWriter.flush();
        }
    }
}
