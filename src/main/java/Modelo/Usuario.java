/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Estudiante_MCA
 */
public class Usuario {
    
    private static Usuario instancia;
    private long idUsuario;
    private String nombreUsuario;

    private Usuario() {}

    public static Usuario getInstancia() {
        if (instancia == null) {
            instancia = new Usuario();
        }
        return instancia;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void limpiarSesion() {
        idUsuario = 0;
        nombreUsuario = null;
    }
}
