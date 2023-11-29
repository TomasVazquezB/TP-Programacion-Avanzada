package Logica;

import java.sql.*;

import BD.Conexion;

public class SistemaJuego implements InicioDeSesion {

    private Connection conexion;
    private PreparedStatement stmt;

    public SistemaJuego() {
        // Inicializa la conexión a la base de datos
        Conexion con = new Conexion();
        conexion = con.conectar();
    }

    @Override
	public String toString() {
		return "SistemaJuego [conexion=" + conexion + ", stmt=" + stmt + "]";
	}

	public Connection getConexion() {
		return conexion;
	}

	public void setConexion(Connection conexion) {
		this.conexion = conexion;
	}

	public PreparedStatement getStmt() {
		return stmt;
	}

	public void setStmt(PreparedStatement stmt) {
		this.stmt = stmt;
	}

	@Override
    public void menu() {
        // Implementa el menú de opciones después de iniciar sesión aquí
    }

    @Override
    public void cerrarSesion() {
        // Implementa el cierre de sesión aquí
    }

   // Este método agregarlo en la clase Validador 

    public void crearPartida(Usuario jugador1, Usuario jugador2) {
        // Implementa la lógica para crear una partida con jugadores
    }
}
