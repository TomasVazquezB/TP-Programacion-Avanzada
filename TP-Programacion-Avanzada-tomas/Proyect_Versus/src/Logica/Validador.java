package Logica;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

public class Validador {

    private Connection conexion;
    private PreparedStatement stmt;
	private String nombre;
	private String contrasena;
	private int jugador_id;
	private int nivelCuenta;
	private List<Partida> historial;

    public Validador(Connection conexion) {
        this.conexion = conexion;
    }
  
    public Validador(String nombre, String contrasena, int jugador_id, int nivelCuenta, List<Partida> historial) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.jugador_id = jugador_id;
		this.nivelCuenta = nivelCuenta;
		this.historial = historial;
	}


	@Override
	public String toString() {
		return "Validador [nombre=" + nombre + ", contrasena=" + contrasena + ", jugador_id=" + jugador_id
				+ ", nivelCuenta=" + nivelCuenta + ", historial=" + historial + "]";
	}

	public String getNombre() {
    	return nombre;
    }
    
    public void setNombre(String nombre) {
    	this.nombre = nombre;
    }
    
    public String getContrasena() {
    	return contrasena;
    }
    
    public void setContrasena(String contrasena) {
    	this.contrasena = contrasena;
    }
    
    public int getNivelCuenta() {
    	return nivelCuenta;
    }
    
    public void setNivelCuenta(int nivelCuenta) {
    	this.nivelCuenta = nivelCuenta;
    }
    
    public int getJugador_id() {
    	return jugador_id;
    }
    
    public void setJugador_id(int jugador_id) {
    	this.jugador_id = jugador_id;
    }
    
    public List<Partida> getHistorial() {
    	return historial;
    }
    
    public void setHistorial(List<Partida> historial) {
    	this.historial = historial;
    }
     
	public Usuario ValidarIngreso(String nombre, String contrasena) {
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre o contraseña vacíos", "Campos Vacios",JOptionPane.INFORMATION_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "Error al validar inicio de sesión: " + e.getMessage(), "Error al validar",JOptionPane.ERROR_MESSAGE);
            }
        }
		return null;
    }

    public boolean ValidarEditar(String nombre, String contrasena, int jugador_id,int nivelCuenta) {
        if (nombre.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre o contraseña vacíos"," Campos Vacios",JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {
            try {
                String sql = "UPDATE usuario SET contrasena = ?, jugador_id = ?, nivelCuenta = ? WHERE nombre = ?";
                stmt = conexion.prepareStatement(sql);
                stmt.setString(1, nombre);
                stmt.setString(2, contrasena);
                stmt.setInt(3, jugador_id);
                stmt.setInt(4, nivelCuenta);
                int filasActualizadas = stmt.executeUpdate();
                return filasActualizadas > 0;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al editar usuario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            return false; 
        }
    }

    public boolean ValidarEliminar(String nombre) {
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre vacío", "Campo Vacio",JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {
            try {
                String sql = "DELETE FROM usuario WHERE nombre = ?";
                stmt = conexion.prepareStatement(sql);
                stmt.setString(1, nombre);
                int filasEliminadas = stmt.executeUpdate();
                return filasEliminadas > 0;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            }
            return false; 
        }
    }

    public LinkedList<Usuario> Mostrar() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        try {
            String sql = "SELECT * FROM usuario";
            stmt = conexion.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                setNombre(resultado.getString("nombre"));
                setContrasena(resultado.getString("contrasena"));
                setJugador_id(resultado.getInt("jugador_id"));
                setNivelCuenta(resultado.getInt("nivelCuenta"));
                setHistorial(obtenerHistorialPorUsuario(resultado.getInt("id")));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al mostrar usuarios: " + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
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
               
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el historial de usuario: " + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
        }
        return historial;
    }
    
    public boolean registrarUsuario(Usuario usuario) {
        try {
            String selectUsuarioSQL = "SELECT COUNT(*) FROM usuario WHERE nombre = ?";
            stmt = conexion.prepareStatement(selectUsuarioSQL);
            stmt.setString(1, usuario.getNombre());
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            String selectMaxJugadorIdSQL = "SELECT MAX(id) FROM jugador";
            stmt = conexion.prepareStatement(selectMaxJugadorIdSQL);
            rs = stmt.executeQuery();
            int jugadorId = 1; 

            if (rs.next()) {
                jugadorId = rs.getInt(1) + 1;
            }

            String insertJugadorSQL = "INSERT INTO jugador (id, nivel) VALUES (?, ?)";
            stmt = conexion.prepareStatement(insertJugadorSQL);
            stmt.setInt(1, jugadorId);
            stmt.setInt(2, usuario.getNivelCuenta());
            stmt.executeUpdate();

            String insertUsuarioSQL = "INSERT INTO usuario (nombre, contrasena, jugador_id, nivelCuenta) VALUES (?, ?, ?, ?)";
            stmt = conexion.prepareStatement(insertUsuarioSQL);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getContrasena());
            stmt.setInt(3, jugadorId);
            stmt.setInt(4, usuario.getNivelCuenta());
            stmt.executeUpdate();
            return true; 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
            return false; 
        }
    }
}
