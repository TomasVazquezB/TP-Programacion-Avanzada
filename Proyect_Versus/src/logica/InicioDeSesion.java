package logica;

import java.util.ArrayList;
import java.util.List;

public interface InicioDeSesion {

    List<Usuario> usuariosRegistrados = new ArrayList<>();

    default Usuario iniciarSesion(String nombre, String contrasena) {
        // Realizar la autenticación aquí
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getNombre().equals(nombre) && usuario.getContrasena().equals(contrasena)) {
                return usuario; // Credenciales válidas, devuelve el usuario
            }
        }
        return null; // Credenciales no válidas, devuelve null
    }

    void menu();

    void cerrarSesion();
}
