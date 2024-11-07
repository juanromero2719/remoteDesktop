/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Observador;

import java.awt.Dimension;
import java.awt.Image;

/**
 *
 * @author Estudiante_MCA
 */
public interface IRedimensionObserver {
    void onResize(Dimension newSize, Image originalImage);
}
