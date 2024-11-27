/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Servidor.Controlador;

import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 *
 * @author Estudiante_MCA
 */

public class ControladorEventosServidor {
    private Robot robot;
    private Dimension screenSize;

    public ControladorEventosServidor(Robot robot, Dimension screenSize) {
        this.robot = robot;
        this.screenSize = screenSize;
    }

    public void procesarEvento(String event) {
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
                return InputEvent.BUTTON1_DOWN_MASK;
            case "2":
                return InputEvent.BUTTON2_DOWN_MASK;
            case "3":
                return InputEvent.BUTTON3_DOWN_MASK;
            default:
                throw new IllegalArgumentException("Invalid button: " + buttonStr);
        }
    }
}
