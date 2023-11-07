package Logica;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

public class Validador {

    private Connection conexion;
    private PreparedStatement stmt;

    public Validador(Connection conexion) {
        this.conexion = conexion;
    }

    public Usuario ValidarIngreso(String nombre, String contrasena) {
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre o contraseña vacíos");
            return null;
        } else {
        	 String[] datos = new String[4];
        
            try {
                String sql = "SELECT * FROM usuario WHERE nombre = ? AND contrasena = ?";
                stmt = conexion.prepareStatement(sql);
                stmt.setString(1, nombre);
                stmt.setString(2, contrasena);
                ResultSet resultado = stmt.executeQuery();
        
                    while (resultado.next()) {
                        datos[0] = resultado.getString(1);
                        datos[1] = resultado.getString(2);
                        datos[2] = resultado.getString(3);
                        datos[3] = resultado.getString(4);
                        Usuario usuario = new Usuario(datos[0], datos[1]);
                        return usuario;
                    }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al validar inicio de sesión: " + e.getMessage());
            }
        }
		return null;
    }

    public boolean ValidarEditar(String nombre, String contrasena, int jugador_id,int nivelCuenta, int nivelClasificatorias) {
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre o contraseña vacíos");
            return false;
        } else {
            try {
                String sql = "UPDATE usuario SET contrasena = ?, jugador_id = ?, nivelCuenta = ?, nivelClasificatorias = ? WHERE nombre = ?";
                stmt = conexion.prepareStatement(sql);
                stmt.setString(1, nombre);
                stmt.setString(2, contrasena);
                stmt.setInt(3, jugador_id);
                stmt.setInt(4, nivelCuenta);
                stmt.setInt(5, nivelClasificatorias);
                int filasActualizadas = stmt.executeUpdate();
                return filasActualizadas > 0;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al editar usuario: " + e.getMessage());
            }
            return false; // Error al editar
        }
    }

    public boolean ValidarEliminar(String nombre) {
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre vacío");
            return false;
        } else {
            try {
                String sql = "DELETE FROM usuario WHERE nombre = ?";
                stmt = conexion.prepareStatement(sql);
                stmt.setString(1, nombre);
                int filasEliminadas = stmt.executeUpdate();
                return filasEliminadas > 0;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
            }
            return false; // Error al eliminar
        }
    }

    public LinkedList<Usuario> Mostrar() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        try {
            String sql = "SELECT * FROM usuario";
            stmt = conexion.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                String nombre = resultado.getString("nombre");
                String contrasena = resultado.getString("contrasena");
                int jugador_id = resultado.getInt("jugador_id");
                int nivelCuenta = resultado.getInt("nivelCuenta");
                int nivelClasificatorias = resultado.getInt("nivelClasificatorias");
                List<Partida> historial = obtenerHistorialPorUsuario(resultado.getInt("id"));
                // agregar el constructor en la clase Usuario usuarios.add(new Usuario(nombre, contrasena, nivelCuenta, nivelClasificatorias, historial));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    public List<Partida> obtenerHistorialPorUsuario(int idUsuario) {
        List<Partida> historial = new LinkedList<>();
        try {
            String sql = "SELECT * FROM partida WHERE idUsuario = ?";
            stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, idUsuario);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                // Recupera información de cada partida y agrégala al historial
                // Agrega partidas a la lista historial
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el historial de usuario: " + e.getMessage());
        }
        return historial;
    }
    
    public boolean registrarUsuario(Usuario usuario) {
        try {
            // Verificar si el nombre de usuario ya existe en la base de datos
            String selectUsuarioSQL = "SELECT COUNT(*) FROM usuario WHERE nombre = ?";
            stmt = conexion.prepareStatement(selectUsuarioSQL);
            stmt.setString(1, usuario.getNombre());
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // El nombre de usuario ya existe, por lo que no se puede registrar.
                return false;
            }

            // Obtener el ID máximo actual en la tabla 'jugador'
            String selectMaxJugadorIdSQL = "SELECT MAX(id) FROM jugador";
            stmt = conexion.prepareStatement(selectMaxJugadorIdSQL);
            rs = stmt.executeQuery();
            int jugadorId = 1; // Valor predeterminado si no hay jugadores existentes.

            if (rs.next()) {
                jugadorId = rs.getInt(1) + 1;
            }

            // Insertar el nuevo jugador
            String insertJugadorSQL = "INSERT INTO jugador (id, nivel) VALUES (?, ?)";
            stmt = conexion.prepareStatement(insertJugadorSQL);
            stmt.setInt(1, jugadorId);
            stmt.setInt(2, usuario.getNivelCuenta());
            stmt.executeUpdate();

            // Insertar el usuario con el jugador_id obtenido
            String insertUsuarioSQL = "INSERT INTO usuario (nombre, contrasena, jugador_id, nivelCuenta, nivelClasificatorias) VALUES (?, ?, ?, ?, ?)";
            stmt = conexion.prepareStatement(insertUsuarioSQL);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContrasena());
            stmt.setInt(3, jugadorId);
            stmt.setInt(4, usuario.getNivelCuenta());
            stmt.setInt(5, usuario.getNivelClasificatorias());

            stmt.executeUpdate();
            return true; // Registro exitoso
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage());
            return false; // Error en el registro
        }
    }
}
