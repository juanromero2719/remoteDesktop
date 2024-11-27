/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manejador;

import Servidor.Controlador.ControladorEventosServidor;
import Eventos.GrabadorPantalla;
import Eventos.RegistroActividades;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
/**
 *
 * @author juanr
 */
public class ManejadorAccionesServidor implements Runnable {
    
    private Socket clientSocket;
    private Robot robot;
    private Dimension screenSize;
    private ControladorEventosServidor controladorEventos;
    private RegistroActividades registroActividades;
   private GrabadorPantalla grabadorPantalla;

    public ManejadorAccionesServidor(Socket clientSocket, long idUsuario) throws Exception {
        
        this.clientSocket = clientSocket;
        this.registroActividades = new RegistroActividades(clientSocket.getInetAddress().getHostAddress()); 
        this.registroActividades.setIdUsuario(idUsuario);
        
        try {
            
            this.robot = new Robot();
            this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            this.controladorEventos = new ControladorEventosServidor(robot, screenSize);
            
            String ip = clientSocket.getInetAddress().getHostAddress();
            String fechaHoraActual = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String nombreVideo = ip + "_" + fechaHoraActual + ".mp4";

            this.grabadorPantalla = new GrabadorPantalla(nombreVideo, 60, 10, idUsuario);
            
            
        } catch (AWTException e) {
            
            System.err.println("Error al crear el robot: " + e.getMessage());
        }
    }
    
     @Override
    public void run() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
             BufferedReader inputReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            outputStream.writeObject(screenSize);
            outputStream.flush();
            
            grabadorPantalla.iniciarGrabacion();

            String clientMessage;
            while ((clientMessage = inputReader.readLine()) != null) {
                
                if (clientMessage.equals("SCREENSHOT")) {
                    comunicarCliente(outputStream);
                } else {
                    controladorEventos.procesarEvento(clientMessage);
                    registroActividades.registrar("Evento recibido del cliente: " + clientMessage);
                }
                                             
            }
        } catch (IOException e) {
            
            System.err.println("Conexión finalizada con el cliente: " + clientSocket.getInetAddress());
        } catch (Exception ex) {
            Logger.getLogger(ManejadorAccionesServidor.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
                  
            registroActividades.guardar(); 
            detenerGrabacionServidor();
           
        }
    }

    private void comunicarCliente(ObjectOutputStream outputStream) {
        try {
            Rectangle captureRect = new Rectangle(0, 0, screenSize.width, screenSize.height);
            BufferedImage screenshot = robot.createScreenCapture(captureRect);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(screenshot, "jpg", byteArrayOutputStream);
            byte[] screenshotBytes = byteArrayOutputStream.toByteArray();

            outputStream.writeObject(screenshotBytes);
            outputStream.flush();

        } catch (IOException e) {
            System.err.println("Error al enviar la captura de pantalla: " + e.getMessage());
        }
    }
 
    private void detenerGrabacionServidor(){
        
        try {
            grabadorPantalla.detenerGrabacion();
        } catch (Exception ex) {
            System.out.println("error: " + ex);
        }
    }
}
