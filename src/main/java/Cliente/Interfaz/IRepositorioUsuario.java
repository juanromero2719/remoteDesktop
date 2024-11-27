/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Cliente.Interfaz;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author Estudiante_MCA
 */
public interface IRepositorioUsuario {

    boolean comprobarCredenciales(String usuario, String password) throws SQLException, IOException;

    long obtenerIdUsuario(String nombreUsuario) throws SQLException, IOException;
}
