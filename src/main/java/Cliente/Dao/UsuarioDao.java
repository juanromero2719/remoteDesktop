/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Cliente.Dao;

import Cliente.Interfaz.IRepositorioUsuario;
import Conexion.BDConexion;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Estudiante_MCA
 */
public class UsuarioDao implements IRepositorioUsuario{
    
    @Override
    public boolean comprobarCredenciales(String usuario, String password) throws SQLException, IOException {
        
        String query = "SELECT contrasena FROM Usuarios WHERE usuario = ?";
        try (Connection connection = BDConexion.obtenerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, usuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("contrasena");
                    return BCrypt.checkpw(password, hashedPassword);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error al verificar credenciales: " + e.getMessage());
        }

        return false;
    }

    @Override
    public long obtenerIdUsuario(String nombreUsuario) throws SQLException, IOException {
        String query = "SELECT id_usuario FROM usuarios WHERE usuario = ?";
        try (Connection connection = BDConexion.obtenerConexion();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nombreUsuario);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getLong("id_usuario");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener ID del usuario: " + e.getMessage());
        }
        return 0;
    }
}
