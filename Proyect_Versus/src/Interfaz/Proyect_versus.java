package interfaz;

import javax.swing.*;

import java.sql.*;
import logica.*;
import BD.*;

public class Proyect_versus {

    public static void main(String[] args) {

        SistemaJuego sistema = new SistemaJuego();

        while (true) {

            String[] opciones = { "Registrar Usuario", "Iniciar Sesion", "Salir" };

            int seleccion = JOptionPane.showOptionDialog(null, "Bienvenido al menu del juego", "Menu de inicio",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            switch (seleccion) {

                case 0:
                    // Registro de usuario
                    registrarUsuario(sistema);
                    break;

                case 1:
                    // Iniciar sesión
                    Usuario usuarioLogueado = iniciarSesion(sistema);
                    if (usuarioLogueado != null) {
                        mostrarMenu(usuarioLogueado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Credenciales incorrectas.", "Error",JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 2:
                    JOptionPane.showMessageDialog(null, "Gracias por jugar. ¡Hasta Luego!","Hasta Luego",JOptionPane.QUESTION_MESSAGE);
                    System.exit(0);
                    break;

                default:
                    break;
            }

        }
    }

    private static void registrarUsuario(SistemaJuego sistema) {
        String nombreUsuario, contrasenaUsuario;
        JOptionPane.showMessageDialog(null, "Registrese con sus datos", "Registro", JOptionPane.QUESTION_MESSAGE);
        
        nombreUsuario = JOptionPane.showInputDialog(null, "Ingrese el nombre de usuario que desea", "Registro nombre",JOptionPane.QUESTION_MESSAGE);
        
        // Validar que el nombre de usuario no esté vacío
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre de usuario no puede estar vacío. Intente nuevamente.", "Error",JOptionPane.ERROR_MESSAGE);
            return; // Salir del método sin registrar
        }

        contrasenaUsuario = JOptionPane.showInputDialog(null, "Ingrese la contraseña que va a utilizar","Registro contraseña", JOptionPane.QUESTION_MESSAGE);

        // Validar que la contraseña no esté vacía
        if (contrasenaUsuario == null || contrasenaUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La contraseña no puede estar vacía. Intente nuevamente.", "Error",JOptionPane.ERROR_MESSAGE);
            return; // Salir del método sin registrar
        }

        Usuario nuevoUsuario = new Usuario(nombreUsuario, contrasenaUsuario);
        if (sistema.registrarUsuario(nuevoUsuario)) {
            JOptionPane.showMessageDialog(null, "Registro exitoso", "Registro finalizado",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "El nombre de usuario ya está en uso. Intente con otro nonmbre.", "Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Usuario iniciarSesion(SistemaJuego sistema) {
        String nombreIngreso, contrasenaIngreso;
        
        JOptionPane.showMessageDialog(null, "Ingrese sus datos para iniciar sesión", "Ingreso de datos",JOptionPane.QUESTION_MESSAGE);
        nombreIngreso = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario", "Iniciar Sesión",JOptionPane.QUESTION_MESSAGE);
        contrasenaIngreso = JOptionPane.showInputDialog(null, "Ingrese su contraseña", "Iniciar Sesión",JOptionPane.QUESTION_MESSAGE);
        
        return sistema.iniciarSesion(nombreIngreso, contrasenaIngreso);
    }

    private static void mostrarMenu(Usuario usuario) {
        String[] opcionesLogueado = { "Armar equipo", "Jugar una partida", "Cerrar Sesión" };
        int seleccionLogueado = JOptionPane.showOptionDialog(null, "¿Qué desea realizar?", "Opciones",JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE, null, opcionesLogueado, opcionesLogueado[0]);

        switch (seleccionLogueado) {
            case 0:
                // Lógica para armar equipo
                break;
            case 1:
                // Lógica para jugar una partida
                break;
            case 2:
                JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!","Sesion Cerrada",JOptionPane.QUESTION_MESSAGE);
                break;
            default:
                break;
        }
    }
}