package Vista;

import javax.swing.*;
import java.awt.*;

public class InterfazCliente {

    private JFrame frame;
    private JLabel imageLabel;

    public InterfazCliente() {
        frame = new JFrame("Remote Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        frame.setLayout(new BorderLayout()); // Usar BorderLayout

        // Crear el JLabel y agregarlo en el centro del BorderLayout
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(imageLabel, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public JLabel getImageLabel() {
        return imageLabel;
    }

    public void ajustarTamanoLabel() {
        frame.revalidate();
        frame.repaint();
    }
}
