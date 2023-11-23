package logica;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Partida {
    private Usuario jugador;
    private Usuario maquina;
    private Personaje jugadorPersonaje;
    private Personaje maquinaPersonaje;

    public Partida(Usuario jugador) {
        this.jugador = jugador;
    }

    public void jugar() {
        jugadorPersonaje = seleccionarPersonaje(jugador);
        maquinaPersonaje = seleccionarPersonajeMaquina();

        while (jugadorPersonaje.getVida() > 0 && maquinaPersonaje.getVida() > 0) {
            realizarAtaque(jugador, jugadorPersonaje, maquina, maquinaPersonaje);
            if (maquinaPersonaje.getVida() <= 0) {
                registrarPartida(jugador, "Victoria");
                JOptionPane.showMessageDialog(null, "¡Has derrotado a la máquina!");
                break;
            }

            realizarAtaqueMaquina(jugador, jugadorPersonaje, maquina, maquinaPersonaje);
            if (jugadorPersonaje.getVida() <= 0) {
                registrarPartida(jugador, "Derrota");
                JOptionPane.showMessageDialog(null, "¡La máquina te ha derrotado!");
                break;
            }
        }
    }

    private Personaje seleccionarPersonaje(Usuario jugador) {
        List<Personaje> personajesDisponibles = obtenerPersonajesDisponibles();

        if (personajesDisponibles.isEmpty()) {
            // Maneja el caso en el que no hay personajes disponibles (puedes mostrar un mensaje de error, por ejemplo).
            return null;
        }

        Object[] opcionesPersonajes = personajesDisponibles.toArray();
        Object seleccion = JOptionPane.showInputDialog(null, "Selecciona un personaje para el combate, " + jugador.getNombre() + ":",
                "Selección de Personaje", JOptionPane.QUESTION_MESSAGE, null, opcionesPersonajes, opcionesPersonajes[0]);

        if (seleccion != null) {
            return (Personaje) seleccion;
        } else {
            return null;
        }
    }

    private static List<Personaje> obtenerPersonajesDisponibles() {
        List<Personaje> personajesDisponibles = new ArrayList<>();
        String jdbcUrl = "jdbc:mysql://localhost:3463/BD Juego Por Turnos";
        String usuario = "tuUsuario";
        String contrasena = "tuContrasena";

        try {
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuario, contrasena);
            String sql = "SELECT nombre, vida FROM Personajes";
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int vida = rs.getInt("vida");
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

    private Personaje seleccionarPersonajeMaquina() {
        List<Personaje> personajesDisponibles = obtenerPersonajesDisponibles();
        int index = (int) (Math.random() * personajesDisponibles.size());
        return personajesDisponibles.get(index);
    }

    private void realizarAtaque(Usuario atacante, Personaje atacantePersonaje, Usuario defensor, Personaje defensorPersonaje) {
        int dano = 10; // Daño fijo
        defensorPersonaje.reducirVida(dano);
        JOptionPane.showMessageDialog(null, atacante.getNombre() + " ataca a " + defensor.getNombre() + " y causa " + dano + " de daño.");
    }

    private void realizarAtaqueMaquina(Usuario jugador, Personaje jugadorPersonaje, Usuario maquina, Personaje maquinaPersonaje) {
        int dano = 8; // Daño fijo de la máquina
        jugadorPersonaje.reducirVida(dano);
        JOptionPane.showMessageDialog(null, maquina.getNombre() + " ataca a " + jugador.getNombre() + " y causa " + dano + " de daño.");
    }

    private void registrarPartida(Usuario usuario, String resultado) {
        String jdbcUrl = "jdbc:mysql://localhost:3463/BD Juego Por Turnos";
        String usuarioDB = "tuUsuario";
        String contrasenaDB = "tuContrasena";

        try {
            Connection conexion = DriverManager.getConnection(jdbcUrl, usuarioDB, contrasenaDB);
            String sql = "INSERT INTO Partidas (usuario_id, resultado) VALUES (?, ?)";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setInt(1, usuario.getJugador_id()); // Asume que tienes un método getJugadorId() en Usuario
            statement.setString(2, resultado);
            statement.executeUpdate();

            statement.close();
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void armarEquipo(Usuario usuario) {
        List<Personaje> personajesDisponibles = obtenerPersonajesDisponibles();
        List<Personaje> equipo = new ArrayList<>();

        while (true) {
            Object[] opcionesPersonajes = personajesDisponibles.toArray();
            Object seleccion = JOptionPane.showInputDialog(null, "Selecciona un personaje para tu equipo, " + usuario.getNombre() + ":",
                    "Selección de Personaje", JOptionPane.QUESTION_MESSAGE, null, opcionesPersonajes, opcionesPersonajes[0]);

            if (seleccion != null) {
                equipo.add((Personaje) seleccion);
                personajesDisponibles.remove(seleccion);
            } else {
                break;
            }

            if (equipo.size() == 4) { // El tamaño máximo del equipo es 4
                break;
            }
        }

        // Aquí puedes guardar el equipo en la base de datos o en el objeto Usuario
        usuario.setEquipo(equipo);
    }

    public static void armarEquipoCPU(Usuario cpu) {
        List<Personaje> personajesDisponibles = obtenerPersonajesDisponibles();
        List<Personaje> equipo = new ArrayList<>();

        while (equipo.size() < 4) { // El tamaño máximo del equipo es 4
            int index = (int) (Math.random() * personajesDisponibles.size());
            equipo.add(personajesDisponibles.get(index));
            personajesDisponibles.remove(index);
        }

        // Aquí puedes guardar el equipo en la base de datos o en el objeto Usuario
        cpu.setEquipo(equipo);
    }
    
    
    
    
    
    
}

