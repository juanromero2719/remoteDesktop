/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor.Servicio;

import Manejador.ManejadorAccionesServidor;
import Modelo.Usuario;
import java.io.*;
import java.net.*;
import java.util.concurrent.*;


public class ServidorServicio {
    
    private int puerto;
    private static final int MAX_CLIENTS = 5;
    private ExecutorService clientThreadPool;

    public ServidorServicio() {
        this.clientThreadPool = Executors.newFixedThreadPool(MAX_CLIENTS);
    }

    public void iniciarServidor(int puerto) throws Exception {
        
        try (ServerSocket serverSocket = new ServerSocket(puerto)) {
            System.out.println("Servidor iniciado en el puerto " + puerto);
            
            long idUsuario = Usuario.getInstancia().getIdUsuario();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clientSocket.getInetAddress());
                clientThreadPool.execute(new ManejadorAccionesServidor(clientSocket, idUsuario)); 
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }
}
