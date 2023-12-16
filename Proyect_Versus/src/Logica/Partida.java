package Logica;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import BD.*;
import javax.swing.*;

public class Partida {
    private Usuario jugador;
    private Usuario maquina;
    private List<Personaje> jugadorPersonaje;
    private List<Personaje> maquinaPersonaje;
    private Conexion con; 

    public Partida(Usuario jugador, Conexion con) {
        this.jugador = jugador;
        this.setCon(con);
        this.jugadorPersonaje = cargarPersonajesDesdeBD(jugador);
        this.maquina = new Usuario("Maquina"); 
        this.maquinaPersonaje = cargarPersonajesDesdeBD(jugador);
    }
    
    public Conexion getCon() {
    	return con;
    }
    
    public void setCon(Conexion con) {
    	this.con = con;
    }
    
    public Usuario getJugador() {
		return jugador;
	}
    
	public void setJugador(Usuario jugador) {
		this.jugador = jugador;
	}

	public Usuario getMaquina() {
		return maquina;
	}

	public void setMaquina(Usuario maquina) {
		this.maquina = maquina;
	}

	public List<Personaje> getJugadorPersonaje() {
		return jugadorPersonaje;
	}

	public void setJugadorPersonaje(List<Personaje> jugadorPersonaje) {
		this.jugadorPersonaje = jugadorPersonaje;
	}

	public List<Personaje> getMaquinaPersonaje() {
		return maquinaPersonaje;
	}

	public void setMaquinaPersonaje(List<Personaje> maquinaPersonaje) {
		this.maquinaPersonaje = maquinaPersonaje;
	}

	public void iniciarPartida() {
        if (jugador.puedeJugar() && maquina.puedeJugar()) {
        	JOptionPane.showMessageDialog(null, "¡La partida ha comenzado!" , "Partida Empezada", JOptionPane.DEFAULT_OPTION);
        } else {
        	JOptionPane.showMessageDialog(null, "No puedes iniciar la partida.", "Error al iniciar partida", JOptionPane.ERROR_MESSAGE);
        }
    }
	
    public void jugar() {
        List<Personaje> equipoAleatorio = cargarPersonajesDesdeBD(maquina);
        maquina.setEquipo(equipoAleatorio);

        if (!jugador.puedeJugar() || !maquina.puedeJugar()) {
        	JOptionPane.showMessageDialog(null, "No puedes iniciar la partida.", "Error al iniciar partida",JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
        	JOptionPane.showMessageDialog(null, "¡La partida ha comenzado!", "Partida Iniciada",JOptionPane.DEFAULT_OPTION);
        	
            while (jugadorTieneVida() && maquinaTieneVida()) {
                realizarRonda();
            }

            determinarResultadoPartida();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Error durante la batalla: " + e.getMessage(), "Error",JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null,"¡" + defensor.getNombre() + " ha sido derrotado!", "Derrotado", JOptionPane.INFORMATION_MESSAGE);
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
        JOptionPane.showMessageDialog(null, atacante.getNombre() + " atacó a " + defensor.getNombre() + " y causó " + danio + " de daño.","Ataque",JOptionPane.INFORMATION_MESSAGE);
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
        	JOptionPane.showMessageDialog(null, mensaje, "Resultado",JOptionPane.INFORMATION_MESSAGE);
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
            JOptionPane.showMessageDialog(null, "Error al cargar personajes aleatorios: " + e.getMessage(), "Error cargar personajes", JOptionPane.ERROR_MESSAGE);
            return new ArrayList<>();
        }
    }

    private int obtenerCantidadPersonajesAleatorios(List<Personaje> personajesDisponibles) {
        int maxCantidad = Math.min(4, personajesDisponibles.size());
        return (int) (Math.random() * maxCantidad) + 1;
    }
}