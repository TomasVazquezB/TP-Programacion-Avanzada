package Logica;

import BD.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import java.util.Random;

public class Partida {
    private Usuario jugador;
    private Usuario maquina;
    private List<Personaje> jugadorPersonaje;
    private List<Personaje> maquinaPersonaje;
    private Conexion con; 

    public Partida(Usuario jugador, Conexion con) {
        this.jugador = jugador;
        this.jugadorPersonaje = cargarPersonajesDesdeBD(jugador);
        this.maquina = new Usuario("Maquina"); 
        this.maquinaPersonaje = cargarPersonajesAleatorios();
        this.con = con;  
    }

    public void iniciarPartida() {
        if (jugador.puedeJugar() && maquina.puedeJugar()) {
        	JOptionPane.showMessageDialog(null,"¡La partida ha comenzado!");
        	 
        } else {
        	JOptionPane.showMessageDialog(null,"No puedes iniciar la partida.");
        }
    }
    
    public void jugar() {
        try { 
            while (jugadorTieneVida() && maquinaTieneVida()) {
                realizarRonda(jugador, jugadorPersonaje, maquina, maquinaPersonaje);
            }
            determinarResultadoPartida();
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Error durante la batalla: " + e.getMessage());
        }
    }

    private void realizarRonda(Usuario jugador, List<Personaje> jugadorPersonajes, Usuario maquina, List<Personaje> maquinaPersonajes) {
        Collections.shuffle(jugadorPersonajes);
        Collections.shuffle(maquinaPersonajes);
        for (Personaje personaje : jugadorPersonajes) {
            realizarTurno(jugador, jugadorPersonajes, maquina, maquinaPersonajes, personaje);
        }
        for (Personaje personaje : maquinaPersonajes) {
            realizarTurnoMaquina(jugador, jugadorPersonajes, maquina, maquinaPersonajes, personaje);
        }
    }

    private void realizarTurno(Usuario jugador, List<Personaje> jugadorPersonajes, Usuario maquina, List<Personaje> maquinaPersonajes, Personaje atacante) {
        Personaje defensor = seleccionarDefensorAleatorio(maquinaPersonajes);
        realizarAtaque(atacante, jugador, defensor);
        if (defensor.getVida() <= 0) {
            maquinaPersonajes.remove(defensor);
            JOptionPane.showMessageDialog(null, "¡" + defensor.getNombre() + " ha sido derrotado!");
        }
    }

    private void realizarTurnoMaquina(Usuario jugador, List<Personaje> jugadorPersonajes, Usuario maquina, List<Personaje> maquinaPersonajes, Personaje atacante) {
        Personaje defensor = seleccionarDefensorAleatorio(jugadorPersonajes);
        realizarAtaque(atacante, maquina, defensor);
        if (defensor.getVida() <= 0) {
            jugadorPersonajes.remove(defensor);
           JOptionPane.showMessageDialog(null, "¡" + defensor.getNombre() + " ha sido derrotado por la máquina!"); 
        }
    }

    private Personaje seleccionarDefensorAleatorio(List<Personaje> personajes) {
        Collections.shuffle(personajes);
        return personajes.get(0);
    }

    private void realizarAtaque(Personaje atacante, Usuario defensorUsuario, Personaje defensor) {
        int ataque = atacante.getEstadisticas().getAtk();
        int defensa = defensor.getEstadisticas().getEr();
        int danio = Math.max(ataque - defensa, 0);
        int vidaActualDefensor = defensor.getVida();
        int nuevoValorVidaDefensor = vidaActualDefensor - danio;
        defensor.setVida(Math.max(nuevoValorVidaDefensor, 0));
        JOptionPane.showMessageDialog(null, atacante.getNombre() + " atacó a " + defensor.getNombre() + " y causó " + danio + " de daño.");
    }
    
    private void determinarResultadoPartida() {
        String resultado = obtenerResultadoAleatorio();
        String mensaje = "";
        
        switch (resultado) {
            case "Derrota":
                if (!jugadorTieneVida()) {
                    mensaje = "¡La máquina ha ganado la batalla!";
                }
                break;
            case "Victoria":
                if (!maquinaTieneVida()) {
                    mensaje = "¡Has ganado la batalla!";
                }
                break;
            default:
                if (!jugadorTieneVida() && !maquinaTieneVida()) {
                    resultado = "Empate";
                    mensaje = "La batalla terminó en empate.";
                }
                break;
        }
        
        if (!mensaje.isEmpty()) {
            JOptionPane.showMessageDialog(null, mensaje);
        }
    }
    
    private String obtenerResultadoAleatorio() {
        String[] resultados = {"Derrota", "Victoria", "Empate"};
        int indiceAleatorio = new Random().nextInt(resultados.length);
        return resultados[indiceAleatorio];
    }

    private boolean jugadorTieneVida() {
        for (Personaje personaje : jugadorPersonaje) {
            if (personaje.getVida() > 0) {
                return true;
            }
        }
        return false;
    }

    private boolean maquinaTieneVida() {
        for (Personaje personaje : maquinaPersonaje) {
            if (personaje.getVida() > 0) {
                return true;
            }
        }
        return false;
    }
    
    private List<Personaje> cargarPersonajesDesdeBD(Usuario usuario) {
        Conexion conexion = new Conexion();
        List<String> nombresPersonajes = conexion.cargarPersonajesDesdeBD(usuario);
        List<Personaje> personajes = new ArrayList<>();
        for (String nombrePersonaje : nombresPersonajes) {
            Personaje nuevoPersonaje = new Personaje(nombrePersonaje, nombrePersonaje);
            personajes.add(nuevoPersonaje);
        }

        return personajes;
    }

    private List<Personaje> cargarPersonajesAleatorios() {
        try {
            Conexion conexion = new Conexion();
            List<String> nombresPersonajesDisponibles = conexion.obtenerNombresPersonajesDisponibles();
            List<Personaje> personajesAleatorios = new ArrayList<>();
            int cantidadPersonajes = obtenerCantidadPersonajesAleatorios(personajesAleatorios);
            Random random = new Random();
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

    private void realizarAtaque(Usuario atacante, List<Personaje> atacantePersonajes, Usuario defensor, List<Personaje> defensorPersonajes) {
        try {
            Random random = new Random();
            int indexAtacante = random.nextInt(atacantePersonajes.size());
            int indexDefensor = random.nextInt(defensorPersonajes.size());
            Personaje personajeAtacante = atacantePersonajes.get(indexAtacante);
            Personaje personajeDefensor = defensorPersonajes.get(indexDefensor);
            int defensa = personajeDefensor.getEstadisticas().getDef();
            List<Habilidad> habilidades = personajeAtacante.getHabilidades();
            int indexHabilidad = random.nextInt(habilidades.size());
            Habilidad habilidadAtaque = habilidades.get(indexHabilidad);
            int danio = habilidadAtaque.getefecto() - defensa;
            danio = Math.max(danio, 0);
            int vidaActualDefensor = personajeDefensor.getVida();
            int nuevoValorVidaDefensor = vidaActualDefensor - danio;
            personajeDefensor.setVida(Math.max(nuevoValorVidaDefensor, 0));
            JOptionPane.showMessageDialog(null, atacante.getNombre() + " usó " + habilidadAtaque.getNombre() + " y causó " + danio + " de daño a " + personajeDefensor.getNombre() + ".");
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Error al realizar el ataque: " + e.getMessage());
        }
    }

    private void realizarAtaqueMaquina(Usuario jugador, List<Personaje> jugadorPersonajes, Usuario maquina, List<Personaje> maquinaPersonajes) {
        try {
            Random random = new Random();
            int indexAtacante = random.nextInt(maquinaPersonajes.size());
            Personaje personajeAtacante = maquinaPersonajes.get(indexAtacante);
            int indexDefensor = random.nextInt(jugadorPersonajes.size());
            Personaje personajeDefensor = jugadorPersonajes.get(indexDefensor);
            List<Habilidad> habilidadesMaquina = personajeAtacante.getHabilidades();
            int indexHabilidad = random.nextInt(habilidadesMaquina.size());
            Habilidad habilidadAtaqueMaquina = habilidadesMaquina.get(indexHabilidad);
            int defensaJugador = personajeDefensor.getEstadisticas().getDef();
            int danioMaquina = habilidadAtaqueMaquina.getefecto() - defensaJugador;
            danioMaquina = Math.max(danioMaquina, 0);
            int vidaActualJugador = personajeDefensor.getVida();
            int nuevoValorVidaJugador = vidaActualJugador - danioMaquina;
            personajeDefensor.setVida(Math.max(nuevoValorVidaJugador, 0));
            String mensajeAtaqueMaquina = "La máquina usó " + habilidadAtaqueMaquina.getNombre() + " y causó " + danioMaquina + " de daño a " + personajeDefensor.getNombre() + ".";
            JOptionPane.showMessageDialog(null, mensajeAtaqueMaquina);

        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null,"Error en el ataque de la máquina: " + e.getMessage());
            e.printStackTrace();
        }
    }
}