package Logica;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import BD.Conexion;

public class Usuario implements InicioDeSesion {
	
    private String nombre;
    private String contrasena;
    private int nivelCuenta;
    private int nivelClasificatorias;
    private int jugador_id;
    private List<Partida> historial; //Hacer una sentencia que muestre el historial de los usuarios por ID
    private Personaje personaje;

    // Agrega la instancia de conexión a la base de datos
    private Conexion con = new Conexion();
    private Connection conexion = con.conectar();
    private PreparedStatement stmt;
    
    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.nivelCuenta = 1;
        this.nivelClasificatorias = 1;
        this.historial = new LinkedList<>();
        this.jugador_id = 0;
    }

    //Crear el constructor
   
    public Usuario (String nombre, String contrasena, int nivelCuenta, int nivelClasificatorias, int jugador_id,List<Partida> historial) {
    	this.nombre = nombre;
    	this.contrasena = contrasena;
    	this.nivelCuenta = nivelCuenta;
    	this.nivelClasificatorias = nivelClasificatorias;
    	this.jugador_id = jugador_id;
    	this.historial = historial;
    }
      
    public Usuario(String nombre) {
        this.nombre = nombre;
        this.contrasena = "contrasena_maquina"; // Contraseña para la máquina (puedes personalizarla)
        this.nivelCuenta = 1; // Nivel inicial para la máquina (puedes personalizarlo)
        this.nivelClasificatorias = 10; // Nivel de clasificatorias para la máquina (puedes personalizarlo)
    }

	public boolean guardar() {
        String sql = "INSERT INTO `usuario`(`nombre`, `contrasena`, `jugador_id`, `nivelCuenta`, `nivelClasificatorias`) VALUES (?,?,?,?,?)";
        try {
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, this.getNombre());
            stmt.setString(2, this.getContrasena());
            stmt.setLong(3, this.getJugador_id());
            stmt.setLong(4, this.getNivelCuenta());
            stmt.setLong(5, this.getNivelClasificatorias());
            stmt.executeUpdate();
            conexion.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar");
            return false;
        }
    }

	public boolean editar() {
        String sql = "UPDATE `usuario` SET `nombre`=?,`contrasena`=?,`jugador_id`=? ,`nivelCuenta`=?,`nivelClasificatorias`=? WHERE nombre = ?";
        try {
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, this.getNombre());
            stmt.setString(2, this.getContrasena());
            stmt.setLong(3, this.getJugador_id());
            stmt.setLong(4, this.getNivelCuenta());
            stmt.setLong(5, this.getNivelClasificatorias());
            stmt.executeUpdate();
            conexion.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar");
            return false;
        }
    }

    public boolean eliminar() {
        String sql = "DELETE FROM `usuario` WHERE nombre=?";
        try {
            stmt = conexion.prepareStatement(sql);
            stmt.setString(1, this.getNombre());
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Error al guardar");
            return false;
        }
    }

    public LinkedList<Usuario> mostrar() {
        LinkedList<Usuario> usuarios = new LinkedList<>();
        String sql = "SELECT * FROM `usuario` WHERE 1";

        String[] datos = new String[4];
        try {
            stmt = conexion.prepareStatement(sql);
            ResultSet resultados = stmt.executeQuery();
            while (resultados.next()) {
                datos[0] = resultados.getString(1);
                datos[1] = resultados.getString(2);
                datos[2] = resultados.getString(3);
                datos[3] = resultados.getString(4);
                usuarios.add(new Usuario(datos[0], datos[1]));
            }
            if (usuarios.isEmpty()) {
                return null;
            } else {
                return usuarios;
            }
        } catch (Exception e) {
            System.out.println("Error al guardar");
            return null;
        }
    }
    
    
    public void armarEquipo(Usuario usuario) {
	    List<Personaje> personajesDisponibles = obtenerPersonajesDisponibles();
	    List<Personaje> equipo = new ArrayList<>();

	    for (int i = 0; i < 4; i++) {
	        // Muestra los personajes disponibles y permite al usuario seleccionar uno
	        Personaje seleccionado = mostrarPersonajesYObtenerSeleccion(personajesDisponibles);
	        if (seleccionado != null) {
	            equipo.add(seleccionado);
	            personajesDisponibles.remove(seleccionado);
	        } else {
	            // El usuario canceló la selección de personajes
	            return;
	        }
	    }

	    // Guarda el equipo en la base de datos
	    guardarEquipoEnBaseDeDatos(usuario, equipo);
	}
    
    public List<Personaje> obtenerPersonajesDisponibles() {
    List<Personaje> personajesDisponibles = new ArrayList<>();
    String jdbcUrl = "jdbc:mysql://localhost:3463/BD Juego Por Turnos";
    String usuario = "tuUsuario";
    String contrasena = "tuContrasena";

    try {
        Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
        String sql = "SELECT * FROM Personajes";
        Statement statement = conexion.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String nombre = rs.getString("nombre");
            int vida = rs.getInt("vida");
            // Asume que tienes un constructor en la clase Personaje que acepta nombre y vida
            Personaje personaje = new Personaje(nombre, nombre, null, vida); 
            personajesDisponibles.add(personaje);
        }

        rs.close();
        statement.close();
        conexion.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return personajesDisponibles;
}

	public Personaje mostrarPersonajesYObtenerSeleccion(List<Personaje> personajesDisponibles) {
        Object[] opcionesPersonajes = personajesDisponibles.toArray();
        Object seleccion = JOptionPane.showInputDialog(null, "Selecciona un personaje para tu equipo:",
                "Selección de Personaje", JOptionPane.QUESTION_MESSAGE, null, opcionesPersonajes, opcionesPersonajes[0]);

        if (seleccion != null) {
            return (Personaje) seleccion;
        } else {
            return null;
        }
    }

    public void guardarEquipoEnBaseDeDatos(Usuario usuario, List<Personaje> equipo) {
        String jdbcUrl = "jdbc:mysql://localhost:3463/BD Juego Por Turnos";
        String usuarioDB = "tuUsuario";
        String contrasenaDB = "tuContrasena";

        try {
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuarioDB, contrasenaDB);
            for (Personaje personaje : equipo) {
                String sql = "INSERT INTO equipo (usuario_id, personaje_id) VALUES (?, ?)";
                PreparedStatement statement = conexion.prepareStatement(sql);
                statement.setInt(1, this.getJugador_id());
                statement.setInt(2, personaje.getId()); // Asume que tienes un método getId() en Personaje
                statement.executeUpdate();
                statement.close();
            }
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getNivelCuenta() {
        return nivelCuenta;
    }

    public void setNivelCuenta(int nivelCuenta) {
        this.nivelCuenta = nivelCuenta;
    }

    public int getNivelClasificatorias() {
        return nivelClasificatorias;
    }

    public void setNivelClasificatorias(int nivelClasificatorias) {
        this.nivelClasificatorias = nivelClasificatorias;
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
	
	 public void setPersonaje(Personaje personaje) {
	        this.personaje = personaje;
	    }

	 public Personaje getPersonaje() {
		    return personaje;
		}

	@Override
    public void menu() {
        // Implementa el menú de Usuario
    }

    @Override
    public void cerrarSesion() {
        // Implementa la lógica para cerrar la sesión
    }

	public void setEquipo(List<Personaje> equipo) {
		// TODO Auto-generated method stub
		
	}

	
}
