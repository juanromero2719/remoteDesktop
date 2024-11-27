/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Conexion;

import Fabrica.PropiedadesFabrica;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Estudiante_MCA
 */

public class BDConexion {
    
    private static Connection conexion;
    private static final String archivoCredenciales = "db.properties";
    
    private BDConexion() {}

    public static Connection obtenerConexion() throws SQLException, IOException {
        
        if (conexion == null || conexion.isClosed()) {
            
            try {
                
                Properties props = cargarPropiedades();
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");                        
                conexion = DriverManager.getConnection(url, user, password);
                
            } catch (SQLException excepcion) {
                throw new RuntimeException("Error al establecer la conexión con la base de datos", excepcion);
            }
                
        }
        return conexion;
    }
    
    private static Properties cargarPropiedades() throws IOException {
        
        Properties propiedades = PropiedadesFabrica.obtenerPropiedades();
        
        try (InputStream inputStream = new FileInputStream(archivoCredenciales)) {
            
            if (inputStream == null) {
                throw new IOException("No se encontró el archivo de configuración '" + archivoCredenciales + "'.");
            }
            
            propiedades.load(inputStream);
        }
        
        return propiedades;
    }
        
    
}

