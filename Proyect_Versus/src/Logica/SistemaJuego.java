package logica;

import java.util.ArrayList;
import java.util.List;

public class SistemaJuego implements InicioDeSesion {

    private List<Usuario> usuariosRegistrados;
    private List<Partida> partidasActivas;

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

    public void crearPartida(Usuario jugador1, Usuario jugador2) {
        Partida partida = new Partida(jugador1, jugador2);
        partidasActivas.add(partida);
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

