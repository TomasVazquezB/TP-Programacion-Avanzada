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
    private List<Partida> historial; 
    private Personaje personaje;
    private Conexion con = new Conexion();
    private Connection conexion = con.conectar();
    private PreparedStatement stmt;
	private List<Personaje> equipo;
    
    public Usuario(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.nivelCuenta = 1;
        this.nivelClasificatorias = 1;
        this.historial = new LinkedList<>();
        this.jugador_id = con.obtenerIdJugador(conexion,nombre, contrasena);
        
    }
  
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
        this.contrasena = "contrasena_maquina"; 
        this.nivelCuenta = 1; 
        this.nivelClasificatorias = 10; 
    }

    @Override
    public String toString() {
    	return "Usuario [nombre=" + nombre + ", contrasena=" + contrasena + ", nivelCuenta=" + nivelCuenta
    			+ ", nivelClasificatorias=" + nivelClasificatorias + ", jugador_id=" + jugador_id + ", historial="
    			+ historial + ", personaje=" + personaje + "]";
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
    	
    	con.eliminarEquipo(usuario);
    	
        List<Personaje> personajesDisponibles = obtenerPersonajesDisponibles();
        List<Personaje> equipo = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Personaje seleccionado = mostrarPersonajesYObtenerSeleccion(personajesDisponibles);
            if (seleccionado != null) {
                equipo.add(seleccionado);
                personajesDisponibles.remove(seleccionado);
            } else {
                JOptionPane.showMessageDialog(null, "Selección de personajes cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        boolean exito = con.guardarEquipoEnBaseDeDatos(usuario, equipo);
        if (exito) {
            JOptionPane.showMessageDialog(null, "Equipo guardado exitosamente en la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "Error al guardar el equipo en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<Personaje> obtenerPersonajesDisponibles() {
        List<Personaje> personajesDisponibles = new ArrayList<>();

        try {
            String sql = "SELECT p.nombre AS nombre,e.hp AS hp " +"FROM personaje p " + "INNER JOIN estadistica e ON p.Estadistica_id_Estadistica = e.id_Estadistica";
            
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int hp = rs.getInt("hp");
                Personaje personaje = new Personaje(nombre, nombre, null, hp);
                personajesDisponibles.add(personaje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return personajesDisponibles;
    }

    public Personaje mostrarPersonajesYObtenerSeleccion(List<Personaje> personajesDisponibles) {
        String[] opcionesPersonajes = new String[personajesDisponibles.size()];
        for (int i = 0; i < personajesDisponibles.size(); i++) {
            Personaje personaje = personajesDisponibles.get(i);
            opcionesPersonajes[i] = personaje.toString(); 
        }

       String seleccion = (String) JOptionPane.showInputDialog(null, "Selecciona un personaje para tu equipo:","Selección de Personaje", JOptionPane.QUESTION_MESSAGE, null, opcionesPersonajes, opcionesPersonajes[0]);

        if (seleccion != null) {
            for (Personaje personaje : personajesDisponibles) {
                if (personaje.toString().equals(seleccion)) {
                    return personaje;
                }
            }
        }

        return null;
    }

    public List<Personaje> getEquipo() {
		return equipo;
	}

	public void setEquipo(List<Personaje> equipo) {
        if (equipo.size() > 4) {
            throw new IllegalArgumentException("El equipo no puede tener más de 4 personajes");
        }
        
        boolean exito = con.guardarEquipoEnBaseDeDatos(this, equipo);
        if (!exito) {
            throw new RuntimeException("Hubo un problema al guardar el equipo en la base de datos");
        }
        
        this.equipo = equipo;
    }

	@Override
    public void menu() {
        // Implementa el menú de Usuario
    }

    @Override
    public void cerrarSesion() {
        // Implementa la lógica para cerrar la sesión
    }
}
