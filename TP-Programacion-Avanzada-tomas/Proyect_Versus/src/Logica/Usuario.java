package Logica;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
import BD.*;

public class Usuario extends Jugador {
	
    private String nombre;
    private String contrasena;
    private int nivelCuenta;
    private int nivelClasificatorias;
    private int jugador_id;
    private List<Partida> historial; 
    private Personaje personaje;
    private int equipoId;  
    private Conexion con = new Conexion();
    private Connection conexion = con.conectar();
    private PreparedStatement stmt;
    private Equipo equipoSeleccionado; // Agrega esta línea

    @Override
    public void crearEquipoAleatorio() {
        // Implementación del método
    }
	  
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

    public int getEquipoId() {
        return equipoId;
    }

    public void setEquipoId(int equipoId) {
        this.equipoId = equipoId;
    }

    public Equipo getEquipoSeleccionado() { 
        return equipoSeleccionado;
    }

    public void setEquipoSeleccionado(Equipo equipoSeleccionado) { 
        this.equipoSeleccionado = equipoSeleccionado;
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
        	JOptionPane.showMessageDialog(null, "Error al guardar", "Error",JOptionPane.ERROR_MESSAGE);
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
        	JOptionPane.showMessageDialog(null, "Error al guardar", "Error",JOptionPane.ERROR_MESSAGE);
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
        	JOptionPane.showMessageDialog(null, "Error al guardar", "Error",JOptionPane.ERROR_MESSAGE);
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
        	JOptionPane.showMessageDialog(null, "Error al guardar", "Error",JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public void armarEquipo(Usuario usuario) {
        List<Personaje> personajesDisponibles = obtenerPersonajesDisponibles();
        List<Personaje> equipo = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Personaje seleccionado = mostrarPersonajesYObtenerSeleccion(personajesDisponibles);
            if (seleccionado == null) {
                JOptionPane.showMessageDialog(null, "Selección de personajes cancelada.", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
                return; 
            }
            equipo.add(seleccionado);
            personajesDisponibles.remove(seleccionado);
        }
        if (!equipo.isEmpty()) {
            String nombreEquipo = JOptionPane.showInputDialog("Por favor, ingresa un nombre para tu equipo:");
            if (nombreEquipo == null || nombreEquipo.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "El nombre del equipo no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            con.eliminarEquipo(usuario);
            boolean exito = con.guardarEquipoEnBaseDeDatos(usuario, nombreEquipo, equipo);
            if (exito) {
                JOptionPane.showMessageDialog(null, "Equipo guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Error al guardar el equipo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    
    public List<Personaje> obtenerPersonajesDisponibles() {
        List<Personaje> personajesDisponibles = new ArrayList<>();
        try {
            String sql = "SELECT nombre, tipo_de_personaje FROM personaje";
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String tipo_de_personaje = rs.getString("tipo_de_personaje");

                Personaje personaje = new Personaje(nombre, tipo_de_personaje);
                personajesDisponibles.add(personaje);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personajesDisponibles;
        
    }
    
    public Personaje mostrarPersonajesYObtenerSeleccion(List<Personaje> personajesDisponibles) {
        if (personajesDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay personajes disponibles", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } 
        String[] nombresYTipos = personajesDisponibles.stream()
                .map(personaje -> personaje.getNombre() + " - " + personaje.getTipo())
                .toArray(String[]::new);
        JComboBox<String> comboBox = new JComboBox<>(nombresYTipos);
        int seleccion = JOptionPane.showOptionDialog(null, comboBox,"Selecciona un personaje para tu equipo:", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Aceptar"}, null);
        if (seleccion == JOptionPane.OK_OPTION) {
            String selectedItem = (String) comboBox.getSelectedItem();
            String[] parts = selectedItem.split(" - ");
            String nombreSeleccionado = parts[0];
            for (Personaje personaje : personajesDisponibles) {
                if (personaje.getNombre().equals(nombreSeleccionado)) {
                    return personaje;
                }
            }
            JOptionPane.showMessageDialog(null, "Personaje no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return null;
    }

    public List<Personaje> getEquipo() {
        return equipo;
    }

    
    
    public void setEquipo(String nombreEquipo, List<Personaje> equipo) {
        if (equipo.size() > 4) {
            throw new IllegalArgumentException("El equipo no puede tener más de 4 personajes");
        }
        this.equipo = equipo;
        System.out.println("Equipo establecido: " + this.equipo);
    }

    public void seleccionarOArmarEquipo(Usuario usuario) {
        List<Equipo> equiposGuardados = obtenerEquiposGuardados(usuario);

        if (!equiposGuardados.isEmpty()) {
            int seleccion = JOptionPane.showOptionDialog(null, "Tienes equipos guardados. ¿Quieres usar uno de ellos o armar uno nuevo?", "Equipos guardados", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Usar equipo existente", "Armar nuevo equipo", "Cancelar"}, null);
            switch (seleccion) {
                case JOptionPane.YES_OPTION:
                    Equipo equipoSeleccionado = mostrarEquiposYObtenerSeleccion(equiposGuardados);
                    if (equipoSeleccionado != null) {
                        String nombreEquipo = equipoSeleccionado.getNombre();
                        usuario.setEquipo(nombreEquipo, equipoSeleccionado.getPersonajes());
                        usuario.setEquipoSeleccionado(equipoSeleccionado); // Guarda el equipo seleccionado
                    }
                    break;
                case JOptionPane.NO_OPTION:
                    usuario.armarEquipo(usuario);
                    break;
                default:
                    break;
            }
        } else {
            usuario.armarEquipo(usuario);
        }
    }

    public boolean puedeJugar() {
        Equipo equipoSeleccionado = this.getEquipoSeleccionado();
        if (equipoSeleccionado == null || equipoSeleccionado.getPersonajes().isEmpty()) {
            JOptionPane.showMessageDialog(null,"Debes seleccionar o crear un equipo antes de jugar una partida.","Seleccion Personaje",JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        System.out.println("Equipo después de jugar: " + this.equipo); // Agrega esta línea
        return true;
    }

    
    public List<Equipo> obtenerEquiposGuardados(Usuario usuario) {
        List<Equipo> equipos = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = con.conectar();
            ps = conexion.prepareStatement("SELECT DISTINCT id, nombre_equipo FROM equipo WHERE jugador_id = ?");
            ps.setInt(1, usuario.getJugador_id()); 
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombreEquipo = rs.getString("nombre_equipo");
                List<Personaje> personajes = obtenerPersonajesPorEquipoId(id);
                equipos.add(new Equipo(id, nombreEquipo, personajes));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return equipos;
    }
    
    public List<Personaje> obtenerPersonajesPorEquipoId(int id) {
        List<Personaje> personajes = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conexion = con.conectar();
            ps = conexion.prepareStatement("SELECT personaje_nombre FROM personaje_equipo WHERE equipo_id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String nombrePersonaje = rs.getString("personaje_nombre");
                Personaje personaje = obtenerPersonajePorNombre(nombrePersonaje);
                personajes.add(personaje);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return personajes;
    }


    public Personaje obtenerPersonajePorNombre(String nombre) {
        Personaje personaje = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = con.conectar();
            ps = conexion.prepareStatement("SELECT * FROM personaje WHERE nombre = ?");
            ps.setString(1, nombre); 
            rs = ps.executeQuery();

            if (rs.next()) {
                // Aquí necesitarías obtener los valores de las columnas del ResultSet
                String nombrePersonaje = rs.getString("nombre");
                int nivelDeHabilidad = rs.getInt("nivel_de_habilidad");
                String tipoDePersonaje = rs.getString("tipo_de_personaje");
                int jugadorId = rs.getInt("jugador_id");
                int estadisticaId = rs.getInt("Estadistica_id_Estadistica");

                // Luego, puedes usar estos valores para crear un nuevo objeto Personaje
                personaje = new Personaje(nombrePersonaje, nivelDeHabilidad, tipoDePersonaje, jugadorId, estadisticaId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return personaje;
    }


    
    public Equipo mostrarEquiposYObtenerSeleccion(List<Equipo> equiposDisponibles) {
        if (equiposDisponibles.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay equipos disponibles", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        } 
        String[] nombresEquipos = equiposDisponibles.stream()
                .map(equipo -> equipo.getNombre())
                .toArray(String[]::new);
        JComboBox<String> comboBox = new JComboBox<>(nombresEquipos);

        int seleccion = JOptionPane.showOptionDialog(null, comboBox,"Selecciona un equipo:", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE, null, new Object[]{"Aceptar"}, null);
        if (seleccion == JOptionPane.OK_OPTION) {
            String nombreSeleccionado = (String) comboBox.getSelectedItem();
            for (Equipo equipo : equiposDisponibles) {
                if (equipo.getNombre().equals(nombreSeleccionado)) {
                    return equipo;
                }
            }
            JOptionPane.showMessageDialog(null, "Equipo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return null; 
    }
    
    public Personaje obtenerPersonajePorId(int id) {
        Personaje personaje = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conexion = con.conectar();
            ps = conexion.prepareStatement("SELECT * FROM personaje WHERE id = ?");
            ps.setInt(1, id); 
            rs = ps.executeQuery();

            if (rs.next()) {
                // Aquí necesitarías obtener los valores de las columnas del ResultSet
                String nombrePersonaje = rs.getString("nombre");
                int nivelDeHabilidad = rs.getInt("nivel_de_habilidad");
                String tipoDePersonaje = rs.getString("tipo_de_personaje");
                int jugadorId = rs.getInt("jugador_id");
                int estadisticaId = rs.getInt("Estadistica_id_Estadistica");

                // Luego, puedes usar estos valores para crear un nuevo objeto Personaje
                personaje = new Personaje(nombrePersonaje, nivelDeHabilidad, tipoDePersonaje, jugadorId, estadisticaId);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return personaje;
    }

    
}
