package Manejador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

/**
 * Clase encargada de manejar las acciones y visualización del escritorio remoto.
 */
public class ManejadorAccionesCliente extends JPanel {

    private static Dimension serverScreenSize;
    private static JLabel imageLabel;
    private static JFrame frame;
    private static double scaleX;
    private static double scaleY;

    public static void connectToServer(int puerto, String direccionIp) {
        if (frame == null) {
            // Configuración de la ventana principal (JFrame)
            frame = new JFrame("Remote Client");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLayout(null);  // Usamos null layout para posicionar el imageLabel en el centro
            frame.setLocationRelativeTo(null);  // Centrar la ventana en la pantalla

            // Configuración del JLabel (pantalla remota) para ocupar el 60% del tamaño de la ventana
            imageLabel = new JLabel();
            frame.add(imageLabel);
            adjustImageLabelSize();  // Ajusta el tamaño de imageLabel
            frame.setVisible(true);

            // Ajustar el tamaño y posición de imageLabel al redimensionar la ventana
            frame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    adjustImageLabelSize();
                    adjustScaling();
                }
            });

            // Capturar eventos de teclado y enviarlos al servidor
            frame.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    sendEventToServer("KEY_PRESS:" + e.getKeyCode(), direccionIp, puerto);
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    sendEventToServer("KEY_RELEASE:" + e.getKeyCode(), direccionIp, puerto);
                }
            });
            frame.setFocusable(true);
            frame.requestFocusInWindow();

            // Configura los eventos de movimiento y clic del mouse dentro de imageLabel
            imageLabel.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseMoved(MouseEvent e) {
                    Point p = e.getPoint();
                    // Calcular la posición relativa solo dentro de la zona de la pantalla remota (60%)
                    if (p.x >= 0 && p.x <= imageLabel.getWidth() &&
                        p.y >= 0 && p.y <= imageLabel.getHeight()) {
                        double relativeX = (double) p.x / imageLabel.getWidth();
                        double relativeY = (double) p.y / imageLabel.getHeight();
                        sendEventToServer("MOUSE_MOVE:" + relativeX + ":" + relativeY, direccionIp, puerto);
                    }
                }
            });

            imageLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point p = e.getPoint();
                    // Limitar los clics a la zona visible del JLabel
                    if (p.x >= 0 && p.x <= imageLabel.getWidth() &&
                        p.y >= 0 && p.y <= imageLabel.getHeight()) {
                        sendEventToServer("MOUSE_CLICK:" + e.getButton(), direccionIp, puerto);
                    }
                }
            });
        }

        new Thread(() -> {
            try (Socket socket = new Socket(direccionIp, puerto);
                 PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true);
                 ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

                System.out.println("Conectado al servidor " + direccionIp + ": " + puerto);

                // Recibe el tamaño de pantalla del servidor
                serverScreenSize = (Dimension) inputStream.readObject();

                while (true) {
                    outputWriter.println("SCREENSHOT");
                    byte[] screenshotBytes = (byte[]) inputStream.readObject();
                    ImageIcon imageIcon = new ImageIcon(screenshotBytes);
                    Image image = imageIcon.getImage();
                    adjustScaling();
                    // Escala la imagen al tamaño de imageLabel (60% de la ventana)
                    Image scaledImage = image.getScaledInstance(imageLabel.getWidth(), imageLabel.getHeight(), Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaledImage));
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al conectar con el servidor: " + e.getMessage());
            }
        }).start();
    }

    private static void adjustImageLabelSize() {
        // Ajusta el tamaño de imageLabel al 60% del tamaño de la ventana principal y lo centra
        int labelWidth = (int) (frame.getWidth() * 0.8);
        int labelHeight = (int) (frame.getHeight() * 0.8);
        int labelX = (frame.getWidth() - labelWidth) / 2;  // Centrar horizontalmente
        int labelY = (frame.getHeight() - labelHeight) / 2;  // Centrar verticalmente
        imageLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
        frame.revalidate();  // Actualizar el diseño de la ventana
    }

    private static void adjustScaling() {
        // Calcula la escala en función del tamaño del JLabel (pantalla remota) y el tamaño del servidor
        if (serverScreenSize != null) {
            scaleX = (double) imageLabel.getWidth() / serverScreenSize.width;
            scaleY = (double) imageLabel.getHeight() / serverScreenSize.height;
        }
    }

    private static void sendEventToServer(String event, String direccionIp, int puerto) {
        try (Socket socket = new Socket(direccionIp, puerto);
             PrintWriter outputWriter = new PrintWriter(socket.getOutputStream(), true)) {
            outputWriter.println(event);
        } catch (IOException e) {
            System.err.println("Error al enviar el evento al servidor: " + e.getMessage());
        }
    }
}
