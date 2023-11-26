package BD;

import javax.swing.*;

import Logica.Personaje;
import Logica.Usuario;

import java.sql.*;
import java.util.List;

public class Conexion {

Connection con ;
	
	public Connection conectar() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3463/bd juego por turnos","root","");
			//JOptionPane.showMessageDialog(null, "Se conecto");
		} catch (Exception e) {
	
			JOptionPane.showMessageDialog(null, "Error al conectarse");
		}
		return con;
	}

public boolean validarConexion() {
    Connection con = null;

    try {
        con = conectar();
        if (con != null) {
            JOptionPane.showMessageDialog(null, "Conexión exitosa");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectarse");
            return false;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al conectarse");
        return false;
    } finally {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión");
        }
    }
}

public void guardarEquipoEnBaseDeDatos(Usuario usuario, List<Personaje> equipo) {
    try {
        // Obtén una conexión a la base de datos
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3463/bd juego por turnos","root","");

        // Comienza una transacción
        conn.setAutoCommit(false);

        // Para cada personaje en el equipo...
        for (Personaje personaje : equipo) {
            // Crea una sentencia SQL para insertar el personaje en la tabla Equipo
            String sql = "INSERT INTO Equipo (jugador_id, nombre) VALUES (?, ?)";

            // Prepara la sentencia
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Establece los valores de los parámetros
            stmt.setInt(1, usuario.getJugador_id());
            stmt.setString(2, personaje.getNombre());

            // Ejecuta la sentencia
            stmt.executeUpdate();
        }

        // Si todo salió bien, confirma la transacción
        conn.commit();

        // Cierra la conexión
        conn.close();
    } catch (SQLException e) {
        // Si algo salió mal, imprime el error
        e.printStackTrace();
    }
}


}
