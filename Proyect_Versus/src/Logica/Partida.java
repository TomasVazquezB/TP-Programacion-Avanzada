package Logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import BD.Conexion;


import javax.swing.JOptionPane;

public class Partida {
    private Usuario jugador;
    private Usuario maquina;
    private List<Personaje> jugadorPersonaje;
    private List<Personaje> maquinaPersonaje;
    private Conexion con; 

    public Partida(Usuario jugador, Conexion con) {
        this.jugador = jugador;
        this.con = con;
        this.jugadorPersonaje = cargarPersonajesDesdeBD(jugador);
        this.maquina = new Usuario("Maquina"); 
        this.maquinaPersonaje = cargarPersonajesDesdeBD(jugador);
    }

    public void iniciarPartida() {
        if (jugador.puedeJugar() && maquina.puedeJugar()) {
            System.out.println("¡La partida ha comenzado!");
        } else {
            System.out.println("No puedes iniciar la partida.");
        }
    }

    public void jugar() {
        if (!jugador.puedeJugar() || !maquina.puedeJugar()) {
            System.out.println("No puedes iniciar la partida.");
            return;
        }

        try {
            System.out.println("¡La partida ha comenzado!");

            while (jugadorTieneVida() && maquinaTieneVida()) {
                realizarRonda();
            }

            determinarResultadoPartida();
        } catch (Exception e) {
            System.out.println("Error durante la batalla: " + e.getMessage());
            throw e;
        }
    }


    private void realizarRonda() {
        Collections.shuffle(jugadorPersonaje);
        Collections.shuffle(maquinaPersonaje);
        for (Personaje personaje : jugadorPersonaje) {
            realizarTurno(personaje, maquinaPersonaje);
        }
        for (Personaje personaje : maquinaPersonaje) {
            realizarTurno(personaje, jugadorPersonaje);
        }
    }

    private void realizarTurno(Personaje atacante, List<Personaje> defensores) {
        Personaje defensor = seleccionarDefensorAleatorio(defensores);
        if (defensor.obtenerVida() > 0) {
            realizarAtaque(atacante, defensor);
            if (defensor.obtenerVida() <= 0) {
                defensores.remove(defensor);
                System.out.println("¡" + defensor.getNombre() + " ha sido derrotado!");
            }
        }
    }

    private Personaje seleccionarDefensorAleatorio(List<Personaje> personajes) {
        Collections.shuffle(personajes);
        return personajes.get(0);
    }

    private void realizarAtaque(Personaje atacante, Personaje defensor) {
        int ataque = atacante.getEstadisticas().getAtk();
        int defensa = defensor.getEstadisticas().getEr();
        int danio = Math.max(ataque - defensa, 0);
        int vidaActualDefensor = defensor.obtenerVida();
        int nuevoValorVidaDefensor = vidaActualDefensor - danio;
        defensor.getEstadisticas().setHp(Math.max(nuevoValorVidaDefensor, 0));
        System.out.println(atacante.getNombre() + " atacó a " + defensor.getNombre() + " y causó " + danio + " de daño.");
    }

    private void determinarResultadoPartida() {
        String mensaje = "";

        if (!jugadorTieneVida()) {
            mensaje = "¡La máquina ha ganado la batalla!";
        } else if (!maquinaTieneVida()) {
            mensaje = "¡Has ganado la batalla!";
        } else {
            mensaje = "La batalla terminó en empate.";
        }

        if (!mensaje.isEmpty()) {
            System.out.println(mensaje);
        }
    }

    private boolean jugadorTieneVida() {
        for (Personaje personaje : jugadorPersonaje) {
            if (personaje.obtenerVida() > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean maquinaTieneVida() {
        for (Personaje personaje : maquinaPersonaje) {
            if (personaje.obtenerVida() > 0) {
                return true;
            }
        }
        return false;
    }

    private List<Personaje> cargarPersonajesDesdeBD(Usuario usuario) {
        try {
            Conexion conexion = new Conexion();
            List<String> nombresPersonajesDisponibles = conexion.obtenerNombresPersonajesDisponibles();
            List<Personaje> personajesAleatorios = new ArrayList<>();
            Random random = new Random();
            int cantidadPersonajes = obtenerCantidadPersonajesAleatorios(personajesAleatorios);
            for (int i = 0; i < cantidadPersonajes; i++) {
                int indiceAleatorio = random.nextInt(nombresPersonajesDisponibles.size());
                String nombrePersonaje = nombresPersonajesDisponibles.get(indiceAleatorio);
                Personaje nuevoPersonaje = new Personaje(nombrePersonaje, nombrePersonaje);
                personajesAleatorios.add(nuevoPersonaje);
                nombresPersonajesDisponibles.remove(indiceAleatorio);
            }
            return personajesAleatorios;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cargar personajes aleatorios: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private int obtenerCantidadPersonajesAleatorios(List<Personaje> personajesDisponibles) {
        int maxCantidad = Math.min(4, personajesDisponibles.size());
        return (int) (Math.random() * maxCantidad) + 1;
    }
      
    
}