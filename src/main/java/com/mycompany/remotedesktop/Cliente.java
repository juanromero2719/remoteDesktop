/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

//private static String SERVER_IP = "192.168.20.21";  // Change this to the server's IP address
package com.mycompany.remotedesktop;



/* CLIENT CODE: This part should be run on the machine acting as the client */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Cliente {
    private static String SERVER_IP = "172.16.201.192";  // Change this to the server's IP address
    private static int SERVER_PORT = 12345;  // Change this port if necessary to match the server

    private static JFrame frame;
    private static JLabel imageLabel;
    private static double scaleX;
    private static double scaleY;
    private static Dimension serverScreenSize;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
        connectToServer();
    }

    private static void createAndShowGUI() {
        frame = new JFrame("Remote Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        imageLabel = new JLabel();
        frame.add(imageLabel, BorderLayout.CENTER);

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                sendEventToServer("KEY_PRESS:" + e.getKeyCode());
            }
        });

        frame.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double relativeX = (double) e.getX() / frame.getWidth();
                double relativeY = (double) e.getY() / frame.getHeight();
                sendEventToServer("MOUSE_MOVE:" + relativeX + ":" + relativeY);
            }
        });

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                sendEventToServer("MOUSE_CLICK:" + e.getButton());
            }
        });

        frame.setVisible(true);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustScaling();
            }
        });
    }

    private static void adjustScaling() {
        Dimension clientSize = frame.getSize();
        scaleX = (double) clientSize.width / serverScreenSize.width;
        scaleY = (double) clientSize.height / serverScreenSize.height;
    }

    private static void connectToServer() {
        new Thread(() -> {
            try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
                 PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

                System.out.println("Conectado al servidor " + SERVER_IP + ":" + SERVER_PORT);

                // Receive server screen size
                serverScreenSize = (Dimension) inputStream.readObject();

                while (true) {
                    outputWriter.println("SCREENSHOT");
                    byte[] screenshotBytes = (byte[]) inputStream.readObject();
                    ImageIcon imageIcon = new ImageIcon(screenshotBytes);
                    Image image = imageIcon.getImage();
                    Dimension clientScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    adjustScaling();
                    Image scaledImage = image.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al conectar con el servidor: " + e.getMessage());
            }
        }).start();
    }

    private static void sendEventToServer(String event) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true)) {
            outputWriter.println(event);
        } catch (IOException e) {
            System.err.println("Error al enviar el evento al servidor: " + e.getMessage());
        }
    }
}