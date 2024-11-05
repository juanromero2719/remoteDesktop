/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Manejador;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Insets;
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
import javax.imageio.ImageIO;
/**
 *
 * @author juanr
 */
public class ManejadorAccionesServidor implements Runnable {
    
    private Socket clientSocket;
    private Robot robot;
    private Dimension screenSize;

    public ManejadorAccionesServidor(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            this.robot = new Robot();
            this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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

            String clientMessage;
            while ((clientMessage = inputReader.readLine()) != null) {
                if (clientMessage.equals("SCREENSHOT")) {
                    sendScreenshot(outputStream);
                } else {
                    handleRemoteEvent(clientMessage);
                }
            }
        } catch (IOException e) {
            System.err.println("Conexión finalizada con el cliente: " + clientSocket.getInetAddress());
        }
    }

    private void sendScreenshot(ObjectOutputStream outputStream) {
        try {
            

            Rectangle captureRect = new Rectangle(
                0,
                0,  
                screenSize.width, 
                screenSize.height 
            );

            BufferedImage screenshot = robot.createScreenCapture(captureRect);
            
             // Convertir la captura de pantalla a un arreglo de bytes
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(screenshot, "jpg", byteArrayOutputStream);
            byte[] screenshotBytes = byteArrayOutputStream.toByteArray();

            // Enviar la captura de pantalla al cliente
            outputStream.writeObject(screenshotBytes);
            outputStream.flush();
            
        } catch (IOException e) {
            System.err.println("Error al enviar la captura de pantalla: " + e.getMessage());
        }
    }

    private void handleRemoteEvent(String event) {
        try {
            String[] eventParts = event.split(":");
            String eventType = eventParts[0];

            switch (eventType) {
                case "MOUSE_MOVE":
                    double relativeX = Double.parseDouble(eventParts[1]);
                    double relativeY = Double.parseDouble(eventParts[2]);
                    int x = (int) (relativeX * screenSize.width);
                    int y = (int) (relativeY * screenSize.height);
                    robot.mouseMove(Math.min(x, screenSize.width - 1), Math.min(y, screenSize.height - 1));
                    break;
                case "MOUSE_CLICK":
                    int button = getMouseButton(eventParts[1]);
                    robot.mousePress(button);
                    robot.mouseRelease(button);
                    break;
                case "KEY_PRESS":
                    int keyCode = Integer.parseInt(eventParts[1]);
                    robot.keyPress(keyCode);
                    robot.keyRelease(keyCode);
                    break;
                default:
                    System.err.println("Evento desconocido: " + event);
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el evento remoto: " + e.getMessage());
        }
    }

    private int getMouseButton(String buttonStr) {
        switch (buttonStr) {
            case "1":
                return java.awt.event.InputEvent.BUTTON1_DOWN_MASK;
            case "2":
                return java.awt.event.InputEvent.BUTTON2_DOWN_MASK;
            case "3":
                return java.awt.event.InputEvent.BUTTON3_DOWN_MASK;
            default:
                throw new IllegalArgumentException("Invalid button: " + buttonStr);
        }
    }
}
