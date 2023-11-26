package Interfaz;

import javax.swing.*;


import BD.Conexion;

import java.sql.*;
import Logica.*;
import java.util.ArrayList;
import java.util.List;

public class Proyect_versus {

    private static List<Partida> partidasActivas = new ArrayList<>();

    public static void main(String[] args) {

        Conexion conexion = new Conexion();
        Connection connection = conexion.conectar();
        Validador sistema = new Validador(connection);
        PantallaDeInicio inicio = new PantallaDeInicio();
		inicio.run();
		
        while (true) {

            String[] opciones = { "Registrar Usuario", "Iniciar Sesion", "Salir" };

            int seleccion = JOptionPane.showOptionDialog(null, "Bienvenido al menu del juego", "Menu de inicio",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            switch (seleccion) {

                case 0:
                    registrarUsuario(sistema);
                    break;

                case 1:
                    Usuario usuarioLogueado = iniciarSesion(sistema);
                    if (usuarioLogueado != null) {
                        mostrarMenu(usuarioLogueado);
                    } else {
                        JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Credenciales incorrectas.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    break;

                case 2:
                    JOptionPane.showMessageDialog(null, "Gracias por jugar. ¡Hasta Luego!", "Hasta Luego",
                            JOptionPane.QUESTION_MESSAGE);
                    System.exit(0);
                    break;

                default:
                    break;
            }

        }
    }

    private static void registrarUsuario(Validador sistema) {
        String nombreUsuario, contrasenaUsuario;
        JOptionPane.showMessageDialog(null, "Registrese con sus datos", "Registro", JOptionPane.QUESTION_MESSAGE);

        nombreUsuario = JOptionPane.showInputDialog(null, "Ingrese el nombre de usuario que desea", "Registro nombre",
                JOptionPane.QUESTION_MESSAGE);

        if (nombreUsuario == null || nombreUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre de usuario no puede estar vacío. Intente nuevamente.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        contrasenaUsuario = JOptionPane.showInputDialog(null, "Ingrese la contraseña que va a utilizar", "Registro contraseña",
                JOptionPane.QUESTION_MESSAGE);

        if (contrasenaUsuario == null || contrasenaUsuario.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La contraseña no puede estar vacía. Intente nuevamente.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Usuario nuevoUsuario = new Usuario(nombreUsuario, contrasenaUsuario);
        if (sistema.registrarUsuario(nuevoUsuario)) {
            JOptionPane.showMessageDialog(null, "Registro exitoso", "Registro finalizado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "El nombre de usuario ya está en uso. Intente con otro nombre.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Usuario iniciarSesion(Validador sistema) {
        String nombreIngreso, contrasenaIngreso;

        JOptionPane.showMessageDialog(null, "Ingrese sus datos para iniciar sesión", "Ingreso de datos",
                JOptionPane.QUESTION_MESSAGE);
        nombreIngreso = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario", "Iniciar Sesión",
                JOptionPane.QUESTION_MESSAGE);
        contrasenaIngreso = JOptionPane.showInputDialog(null, "Ingrese su contraseña", "Iniciar Sesión",
                JOptionPane.QUESTION_MESSAGE);

        return sistema.ValidarIngreso(nombreIngreso, contrasenaIngreso);
    }

    private static void mostrarMenu(Usuario usuario) {
        String[] opcionesLogueado = { "Armar equipo", "Jugar una partida", "Cerrar Sesión" };
        int seleccionLogueado = JOptionPane.showOptionDialog(null, "¿Qué desea realizar?", "Opciones",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesLogueado, opcionesLogueado[0]);

        switch (seleccionLogueado) {
            case 0:
                break;
            case 1:
                Partida nuevaPartida = new Partida(usuario);
                nuevaPartida.jugar();
                
                Partida.armarEquipo(usuario);
                Partida.armarEquipoCPU(usuario); 
            
                break;

            case 2:
                JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!", "Sesion Cerrada",
                        JOptionPane.QUESTION_MESSAGE);
                break;
            default:
                break;
        }
    }

    private static Personaje seleccionarPersonaje(Usuario jugador) {
        List<Personaje> personajesDisponibles = obtenerPersonajesDisponibles();

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
        personajesDisponibles.add(new Personaje("Personaje1", "Tipo1", null, 0));
        personajesDisponibles.add(new Personaje("Personaje2", "Tipo2", null, 0));
        return personajesDisponibles;
    }
}