/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Fabrica;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 *
 * @author Estudiante_MCA
 */
public class PrintWriterFabrica {
    
    private static PrintWriter printWriter;

    private PrintWriterFabrica() {}

    public static PrintWriter obtenerPrintWriter(OutputStream outputStream) {
        
        if (printWriter == null) {
            printWriter = new PrintWriter(outputStream, true);
        }
        return printWriter;
    }
}
