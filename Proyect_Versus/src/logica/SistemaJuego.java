package logica;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class SistemaJuego implements InicioDeSesion {

    private List<Usuario> usuariosRegistrados;
    private static List<Partida> partidasActivas;

    public SistemaJuego() {
        this.usuariosRegistrados = new ArrayList<>();
        this.partidasActivas = new ArrayList<>();
    }

    @Override
    public Usuario iniciarSesion(String nombreIngreso, String contrasenaIngreso) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getNombre().equals(nombreIngreso) && usuario.getContrasena().equals(contrasenaIngreso)) {
                return usuario; // Credenciales válidas, devuelve el usuario
            }
        }
        return null; // Credenciales no válidas, devuelve null
    }

    @Override
    public void menu() {
        // Implementa el menú de opciones después de iniciar sesión aquí
    }

    @Override
    public void cerrarSesion() {
        // Implementa el cierre de sesión aquí
    }

    public boolean registrarUsuario(Usuario usuario) {
        // Verifica si el nombre de usuario ya existe
        if (!nombreDeUsuarioExistente(usuario.getNombre())) {
            usuariosRegistrados.add(usuario);
            return true; // Registro exitoso
        } else {
            return false; // El nombre de usuario ya está en uso
        }
    }

    public static void crearPartida(Usuario jugador) {
        // Mostrar la lista de personajes disponibles para el jugador
        Personaje personajeJugador = seleccionarPersonaje(jugador);

        // Verificar que se seleccionó un personaje válido
        if (personajeJugador != null) {
            // Crear la partida con el jugador y la CPU
            Partida partida = new Partida(jugador, new Usuario("CPU", "contrasenaCPU"));

            // Puedes asignar el personaje al jugador en la partida
            jugador.setPersonaje(personajeJugador);

            

            // Agregar la partida a la lista de partidas activas
            partidasActivas.add(partida);

            // Puedes mostrar un mensaje indicando que la partida ha sido creada
            JOptionPane.showMessageDialog(null, "Partida contra la CPU creada con éxito. ¡Que comience el combate!", "Partida Creada", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Mostrar un mensaje indicando que la selección de personaje no fue válida
            JOptionPane.showMessageDialog(null, "Error en la selección de personaje. Intenta nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para seleccionar un personaje para el combate
    private static Personaje seleccionarPersonaje(Usuario jugador) {
        List<Personaje> personajesDisponibles = obtenerPersonajesDisponibles();

        // Mostrar una lista de personajes y permitir al jugador elegir uno
        Object[] opcionesPersonajes = personajesDisponibles.toArray();
        Object seleccion = JOptionPane.showInputDialog(null, "Selecciona un personaje para el combate, " + jugador.getNombre() + ":",
                "Selección de Personaje", JOptionPane.QUESTION_MESSAGE, null, opcionesPersonajes, opcionesPersonajes[0]);

        // Obtener el personaje seleccionado
        if (seleccion != null) {
            return (Personaje) seleccion;
        } else {
            return null; // El jugador canceló la selección
        }
    }

    // Método para obtener la lista de personajes disponibles (puedes implementar según tu lógica)
    private static List<Personaje> obtenerPersonajesDisponibles() {
        // Implementa la lógica para obtener la lista de personajes disponibles (puedes cargarla desde una base de datos, por ejemplo)
        // Aquí simplemente se crea una lista de ejemplo
        List<Personaje> personajesDisponibles = new ArrayList<>();
        personajesDisponibles.add(new Personaje("Personaje1", "Tipo1"));
        personajesDisponibles.add(new Personaje("Personaje2", "Tipo2"));
        // Agrega más personajes según sea necesario
        return personajesDisponibles;
    }

    public Usuario buscarUsuarioPorNombre(String nombre) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        return null; // Usuario no encontrado
    }

    // Método auxiliar para verificar si un nombre de usuario ya existe
    private boolean nombreDeUsuarioExistente(String nombre) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getNombre().equals(nombre)) {
                return true; // El nombre de usuario ya está en uso
            }
        }
        return false; // El nombre de usuario no está en uso
    }
}

