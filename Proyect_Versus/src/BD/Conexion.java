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
			
			// Establece la conexión a la base de datos
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3463/bd juego por turnos", "root", "");
	        
	        // JOptionPane.showMessageDialog(null, "Conexión exitosa");
	    } catch (ClassNotFoundException e) {
	        // Si el controlador no se encuentra
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al cargar el controlador de la base de datos");
	    } catch (SQLException e) {
	        // Si hay un error al conectarse a la base de datos
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos");
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

public boolean guardarEquipoEnBaseDeDatos(Usuario usuario, List<Personaje> equipo) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        // Obtén una conexión a la base de datos
        conn = conectar();

     
        
        // Verificar que el jugador_id exista en la tabla jugador
        if (!existeJugador(conn, usuario.getJugador_id())) {
            JOptionPane.showMessageDialog(null, "El jugador con ID " + usuario.getJugador_id() + " no existe.");
            // Puedes manejar esto de la manera que prefieras, lanzar una excepción, mostrar un mensaje, etc.
            return false;
        }

        // Comienza una transacción
        conn.setAutoCommit(false);

        // Para cada personaje en el equipo...
        for (Personaje personaje : equipo) {
            try {
                // Crea una sentencia SQL para insertar el personaje en la tabla Equipo
                String sql = "INSERT INTO equipo (personaje_nombre, jugador_id) VALUES (?, ?)";

                // Prepara la sentencia
                stmt = conn.prepareStatement(sql);

                // Establece los valores de los parámetros
                stmt.setString(1, personaje.getNombre());
                stmt.setInt(2, usuario.getJugador_id());

                // Ejecuta la sentencia
                stmt.executeUpdate();
            } catch (SQLException e) {
                // Si hay un error al insertar un personaje, imprime el error
                e.printStackTrace();

                // Realiza un rollback para deshacer la transacción
                conn.rollback();
                return false;
            } finally {
                // Cierra el PreparedStatement
                if (stmt != null) {
                    stmt.close();
                }
            }
        }

        // Si todo salió bien, confirma la transacción
        conn.commit();
        return true;
    } catch (SQLException e) {
        // Si hay un error, imprime el error
        e.printStackTrace();
        return false;
    } finally {
        // Cierra la conexión
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Si hay un error al cerrar la conexión, imprime el error
                e.printStackTrace();
            }
        }
    }
}

// Método para verificar la existencia de un jugador por ID
private boolean existeJugador(Connection conn, int jugadorId) {
    try {
        String sql = "SELECT id FROM jugador WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, jugadorId);

        ResultSet rs = stmt.executeQuery();
        return rs.next(); // Devuelve true si hay al menos una fila (jugador encontrado), false de lo contrario
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}


public int obtenerIdJugador(Connection conn, String nombre, String contrasena) {
    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        String sql = "SELECT jugador_id FROM usuario WHERE nombre = ? AND contrasena = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, nombre);
        stmt.setString(2, contrasena);

        rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("jugador_id");
        } else {
            JOptionPane.showMessageDialog(null, "El jugador no existe o las credenciales son incorrectas.");
            return -1; // Indica un error o credenciales incorrectas
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al obtener el ID del jugador.");
        return -1; // Indica un error
    } finally {
        try {
            // Cierra los recursos en un bloque finally
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public boolean eliminarEquipo(Usuario usuario) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        // Obtén una conexión a la base de datos
        conn = conectar();

        // Verificar que el jugador_id exista en la tabla jugador
        if (!existeJugador(conn, usuario.getJugador_id())) {
            JOptionPane.showMessageDialog(null, "El jugador con ID " + usuario.getJugador_id() + " no existe.");
            return false;
        }

        // Comienza una transacción
        conn.setAutoCommit(false);

        try {
            // Crea una sentencia SQL para eliminar los personajes del equipo del usuario
            String sql = "DELETE FROM equipo WHERE jugador_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuario.getJugador_id());

            // Ejecuta la sentencia
            stmt.executeUpdate();

            // Confirma la transacción
            conn.commit();
            return true;
        } catch (SQLException e) {
            // Si hay un error, realiza un rollback para deshacer la transacción
            conn.rollback();
            e.printStackTrace();
            return false;
        } finally {
            // Cierra el PreparedStatement
            if (stmt != null) {
                stmt.close();
            }
        }
    } catch (SQLException e) {
        // Si hay un error al conectar a la base de datos
        e.printStackTrace();
        return false;
    } finally {
        // Cierra la conexión
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

}
