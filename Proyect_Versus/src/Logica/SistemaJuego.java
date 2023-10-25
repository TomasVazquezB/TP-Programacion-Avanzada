package Logica;

import Logica.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class SistemaJuego implements InicioDeSesion {

    private Connection conexion;
    private PreparedStatement stmt;

    public SistemaJuego() {
        // Inicializa la conexión a la base de datos
        Conexion con = new Conexion();
        conexion = con.conectar();
    }

    @Override
    public void menu() {
        // Implementa el menú de opciones después de iniciar sesión aquí
    }

    @Override
    public void cerrarSesion() {
        // Implementa el cierre de sesión aquí
    }

   // Este método agregarlo en la clase Validador public boolean registrarUsuario(Usuario usuario) {
      //  String sql = "INSERT INTO usuario (nombre, contrasena, nivelCuenta, nivelClasificatorias) VALUES (?, ?, ?, ?)";
        //try {
          //  stmt = conexion.prepareStatement(sql);
            //stmt.setString(1, usuario.getNombre());
            //stmt.setString(2, usuario.getContrasena());
            //stmt.setInt(3, usuario.getNivelCuenta());
            //stmt.setInt(4, usuario.getNivelClasificatorias());
            //stmt.executeUpdate();
            //return true; // Registro exitoso
        //} catch (Exception e) {
          //  JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
            //return false; // Error en el registro
        //}
    //}

    public void crearPartida(Usuario jugador1, Usuario jugador2) {
        // Implementa la lógica para crear una partida con jugadores
    }
}

