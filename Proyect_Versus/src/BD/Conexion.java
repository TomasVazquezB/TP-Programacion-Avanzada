package BD;

import javax.swing.*;
import java.util.ArrayList;

import Logica.Estadistica;
import Logica.Habilidad;
import Logica.Personaje;
import Logica.Usuario;

import java.sql.*;
import java.util.List;

public class Conexion {

Connection con ;
	
	public Connection conectar() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
	        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bd juego por turnos", "root", "");
	        
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al cargar el controlador de la base de datos","Error al Cargar Datos",JOptionPane.ERROR_MESSAGE);
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al conectarse a la base de datos","Error al Conectarse",JOptionPane.ERROR_MESSAGE);
	    }

	    return con;
	}

public boolean validarConexion() {
    Connection con = null;

    try {
        con = conectar();
        if (con != null) {
            JOptionPane.showMessageDialog(null, "Conexión exitosa","Exito",JOptionPane.DEFAULT_OPTION);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectarse","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al conectarse","Error",JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}

public boolean guardarEquipoEnBaseDeDatos(Usuario usuario, List<Personaje> equipo) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
     
        conn = conectar();

        if (!existeJugador(conn, usuario.getJugador_id())) {
            JOptionPane.showMessageDialog(null, "El jugador con ID " + usuario.getJugador_id() + " no existe.");
           
            return false;
        }

        conn.setAutoCommit(false);

        for (Personaje personaje : equipo) {
            try {
               
                String sql = "INSERT INTO equipo (personaje_nombre, jugador_id) VALUES (?, ?)";

                stmt = conn.prepareStatement(sql);

                stmt.setString(1, personaje.getNombre());
                stmt.setInt(2, usuario.getJugador_id());

                stmt.executeUpdate();
            } catch (SQLException e) {
          
                e.printStackTrace();

                conn.rollback();
                return false;
            } finally {
             
                if (stmt != null) {
                    stmt.close();
                }
            }
        }

        conn.commit();
        return true;
    } catch (SQLException e) {
 
        e.printStackTrace();
        return false;
    } finally {
    
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
               
                e.printStackTrace();
            }
        }
    }
}

private boolean existeJugador(Connection conn, int jugadorId) {
    try {
        String sql = "SELECT id FROM jugador WHERE id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, jugadorId);

        ResultSet rs = stmt.executeQuery();
        return rs.next(); 
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
            //JOptionPane.showMessageDialog(null, "El jugador no existe o las credenciales son incorrectas.");
            return -1; 
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al obtener el ID del jugador." ,"Error",JOptionPane.ERROR_MESSAGE);
        return -1; 
    } finally {
        try {
         
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
      
        conn = conectar();

        if (!existeJugador(conn, usuario.getJugador_id())) {
            JOptionPane.showMessageDialog(null, "El jugador con ID " + usuario.getJugador_id() + " no existe.");
            return false;
        }

        conn.setAutoCommit(false);

        try {
           
            String sql = "DELETE FROM equipo WHERE jugador_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuario.getJugador_id());

       
            stmt.executeUpdate();

            conn.commit();
            return true;
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
            return false;
        } finally {
           
            if (stmt != null) {
                stmt.close();
            }
        }
    } catch (SQLException e) {
    
        e.printStackTrace();
        return false;
    } finally {
        
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

public boolean registrarPartida(Usuario usuario, String resultado) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = conectar(); 

        if (!existeUsuario(conn, usuario.getNombre())) {
            JOptionPane.showMessageDialog(null, "El usuario no existe.","Usuario no encontrado",JOptionPane.DEFAULT_OPTION);
            return false;
        }

        String sql = "INSERT INTO batalla (usuario_id, resultado) VALUES (?, ?)";
        stmt = conn.prepareStatement(sql);

        stmt.setInt(1, usuario.getJugador_id());
        stmt.setString(2, resultado);

        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas > 0) {
            JOptionPane.showMessageDialog(null, "Partida registrada exitosamente.","Registro Exitoso",JOptionPane.DEFAULT_OPTION);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al registrar la partida.","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al registrar la partida.","Error",JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

private boolean existeUsuario(Connection conn, String nombreUsuario) {
    try {
        String sql = "SELECT jugador_id FROM usuario WHERE nombre = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, nombreUsuario);
        ResultSet rs = stmt.executeQuery();
        boolean existe = rs.next();
        rs.close();
        stmt.close();
        return existe;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

public boolean actualizarResultadoPartida(Usuario usuario, String resultado) {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = conectar(); 

        if (!existeUsuario(conn, usuario.getNombre())) {
            JOptionPane.showMessageDialog(null, "El usuario no existe.","Usuario no encontrado",JOptionPane.DEFAULT_OPTION);
            return false;
        }

        String sql = "UPDATE batalla SET resultado = ? WHERE jugador_id = ?";
        stmt = conn.prepareStatement(sql);

        stmt.setString(1, resultado);
        stmt.setInt(2, usuario.getJugador_id());

        int filasAfectadas = stmt.executeUpdate();

        if (filasAfectadas > 0) {
            //JOptionPane.showMessageDialog(null, "Resultado de la partida actualizado exitosamente.","Resultado",JOptionPane.DEFAULT_OPTION);
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al actualizar el resultado de la partida.","Error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error al actualizar el resultado de la partida.","Error",JOptionPane.ERROR_MESSAGE);
        return false;
    } finally {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public List<String> cargarPersonajesDesdeBD(Usuario usuario) {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    List<String> nombresPersonajes = new ArrayList<>();

    try {
        conn = conectar();

        String sql = "SELECT personaje_nombre FROM equipo WHERE jugador_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, usuario.getJugador_id());

        rs = stmt.executeQuery();

        while (rs.next()) {
            String nombrePersonaje = rs.getString("personaje_nombre");
            nombresPersonajes.add(nombrePersonaje);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return nombresPersonajes;
}

public List<String> obtenerNombresPersonajesDisponibles() {
    List<String> nombresPersonajes = new ArrayList<>();

    try {
        con = conectar(); 

        if (con != null && !con.isClosed()) {
           
            String sql = "SELECT nombre FROM personaje";
            try (PreparedStatement stmt = con.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    nombresPersonajes.add(rs.getString("nombre"));
                }
            }
        } else {

            System.err.println("Error: La conexión no está abierta.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
}

    return nombresPersonajes;
}

public Estadistica obtenerEstadisticasPorNombre(String nombrePersonaje) {
    Estadistica estadistica = null;
    String query = "SELECT hp, def, er, em FROM estadistica JOIN personaje ON estadistica.id_Estadistica = personaje.Estadistica_id_Estadistica WHERE nombre = ?";

    try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
        preparedStatement.setString(1, nombrePersonaje);

        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                int hp = resultSet.getInt("hp");
                int def = resultSet.getInt("def");
                int er = resultSet.getInt("er");
                int em = resultSet.getInt("em");

                estadistica = new Estadistica(hp, def, er, em);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return estadistica;
}

public List<Habilidad> obtenerHabilidadesPorNombre(String nombrePersonaje) {
    List<Habilidad> habilidades = new ArrayList<>();

    try {
        
    	String query = "SELECT nombre, descripcion, efecto, personaje_nombre " +
                "FROM habilidades " +
                "WHERE personaje_nombre = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
    
            stmt.setString(1, nombrePersonaje);

            try (ResultSet rs = stmt.executeQuery()) {
               
                while (rs.next()) {
                    String nombreHabilidad = rs.getString("nombre");
                    String descripcion = rs.getString("descripcion");
                    int efecto = rs.getInt("efecto");
                    String personaje_nombre = rs.getString("personaje_nombre");

                    Habilidad habilidad = new Habilidad(nombreHabilidad, descripcion, efecto, personaje_nombre);
                    habilidades.add(habilidad);
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return habilidades;
}

private void cerrarConexion() {
    try {
        if (con != null) {
            con.close();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public Connection obtenerConexion() {
    return con;
}

}
