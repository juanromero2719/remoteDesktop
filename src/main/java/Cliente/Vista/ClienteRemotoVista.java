package Cliente.Vista;

import Observador.IRedimensionObserver;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List; 

public class ClienteRemotoVista {

    private JFrame frame;
    private JLabel imageLabel;
    private List<IRedimensionObserver> observers = new ArrayList<>();


    public ClienteRemotoVista() {
        
        frame = new JFrame("Remote Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout()); 

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        frame.add(imageLabel, BorderLayout.CENTER);
        
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                notifyObservers();
            }
        });

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
    
     public void addObserver(IRedimensionObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IRedimensionObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        Dimension newSize = frame.getSize();
        Image originalImage = imageLabel.getIcon() != null ? ((ImageIcon) imageLabel.getIcon()).getImage() : null;
        for (IRedimensionObserver observer : observers) {
            observer.onResize(newSize, originalImage);
        }
    }
}
