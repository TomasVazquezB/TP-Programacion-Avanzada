package Logica;

import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import BD.*;

public class Partida {
    private Jugador usuario;
    private Jugador maquina;
    private Conexion con;

    public Partida(Usuario usuario, Conexion con) {
        this.usuario = usuario;
        this.con = con;
        this.maquina = new Maquina(usuario);
    }
    
    //Inicializacion de la partida
    public void iniciarPartida() {
        if (!((Usuario) usuario).puedeJugar()) {
            return;
        }
        maquina.crearEquipoAleatorio();
        mostrarEquipoMaquina();
        while (!partidaTerminada()) {
            realizarTurno(usuario, true);
            if (!partidaTerminada()) {
                realizarTurno(maquina, false);
            }
        }  
        guardarResultado();
    }
    
    //Realizacion de turno
    private void realizarTurno(Jugador jugador, boolean esUsuario) {
        Random rand = new Random();
        int danio;  
        Personaje personaje;
        Personaje objetivo;
        Habilidad habilidad = null;

        //Se define el 'oponente' aquí
        Jugador oponente = (jugador == usuario) ? maquina : usuario;

        if (esUsuario) {
            //Deja que el usuario elija qué personaje va a usar para atacar
            String[] personajes = jugador.getEquipo().stream().map(Personaje::getNombre).toArray(String[]::new);
            String personajeSeleccionado = (String) JOptionPane.showInputDialog(null, "Elige un personaje", "Personajes", JOptionPane.QUESTION_MESSAGE, null, personajes, personajes[0]);
            personaje = jugador.getEquipo().stream().filter(p -> p.getNombre().equals(personajeSeleccionado)).findFirst().orElse(null);

            //Deja que el usuario elija a qué personaje desea atacar
            String[] objetivos = oponente.getEquipo().stream().map(Personaje::getNombre).toArray(String[]::new);
            String objetivoSeleccionado = (String) JOptionPane.showInputDialog(null, "Elige un objetivo", "Objetivos", JOptionPane.QUESTION_MESSAGE, null, objetivos, objetivos[0]);
            objetivo = oponente.getEquipo().stream().filter(p -> p.getNombre().equals(objetivoSeleccionado)).findFirst().orElse(null);

            //Eleccion de Ataques
            Object[] opciones = {"Ataque básico", "Usar habilidad"};
            int seleccion = JOptionPane.showOptionDialog(null, "¿Qué acción te gustaría realizar?", "Turno del jugador", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);
            if (seleccion == 0) {
                danio = personaje.getEstadisticas().getAtk();
            } else {
                String[] habilidades = personaje.getHabilidades().stream().map(Habilidad::getNombre).toArray(String[]::new);
                String habilidadSeleccionada = (String) JOptionPane.showInputDialog(null, "Elige una habilidad", "Habilidades", JOptionPane.QUESTION_MESSAGE, null, habilidades, habilidades[0]);
                habilidad = personaje.getHabilidades().stream().filter(h -> h.getNombre().equals(habilidadSeleccionada)).findFirst().orElse(null);
                JOptionPane.showMessageDialog(null, habilidad.getDescripcion());
                danio = habilidad.getDanio();
                personaje.getEstadisticas().reducirVida(5); 
            }
        } else {
            personaje = jugador.getEquipo().get(rand.nextInt(jugador.getEquipo().size()));
            objetivo = oponente.getEquipo().get(rand.nextInt(oponente.getEquipo().size()));
            habilidad = personaje.getHabilidades().get(rand.nextInt(personaje.getHabilidades().size()));
            JOptionPane.showMessageDialog(null, personaje.getNombre() + " Usó " + habilidad.getNombre());
            danio = habilidad.getDanio();
        }

        if (esFuerteContra(personaje.getTipo(), objetivo.getTipo())) {
            danio *= 4.75; 
        } else if (esDebilContra(personaje.getTipo(), objetivo.getTipo())) {
            danio *= 2.55;
        }
        objetivo.getEstadisticas().reducirVida(danio);
        JOptionPane.showMessageDialog(null, objetivo.getNombre() + " Ahora tiene " + objetivo.getEstadisticas().getHp() + " De vida","Vida",JOptionPane.DEFAULT_OPTION);
    }
    
    //Finalizacion de la partida
    private boolean partidaTerminada() {
        return equipoDerrotado(usuario.getEquipo()) || equipoDerrotado(maquina.getEquipo());
    }
    private boolean equipoDerrotado(List<Personaje> equipo) {
        for (Personaje personaje : equipo) {
            if (personaje.getEstadisticas().getHp() > 0) {
                return false;
            }
        }
        return true;
    }
    
    //Guardar Resultado de la partida
    private void guardarResultado() {
        String resultado = equipoDerrotado(usuario.getEquipo()) ? "Derrota" : "Victoria";
        con.actualizarResultadoPartida((Usuario) usuario, resultado);
    }
    
    //Mostrar el equipo que tiene la maquina
    private void mostrarEquipoMaquina() {
        String equipo = "Equipo de la máquina:\n";
        for (Personaje personaje : maquina.getEquipo()) {
            equipo += personaje.getNombre() + "\n";
        }
        JOptionPane.showMessageDialog(null, equipo);
    }
    
    private boolean esFuerteContra(String tipoPersonaje, String tipoObjetivo) {
        return (tipoPersonaje.equals("Pyro") && tipoObjetivo.equals("Cryo")) ||
               (tipoPersonaje.equals("Cryo") && tipoObjetivo.equals("Electro")) ||
               (tipoPersonaje.equals("Electro") && tipoObjetivo.equals("Hydro")) ||
               (tipoPersonaje.equals("Hydro") && tipoObjetivo.equals("Pyro"));
    }
    private boolean esDebilContra(String tipoPersonaje, String tipoObjetivo) {
        return (tipoPersonaje.equals("Pyro") && tipoObjetivo.equals("Hydro")) ||
               (tipoPersonaje.equals("Cryo") && tipoObjetivo.equals("Pyro")) ||
               (tipoPersonaje.equals("Electro") && tipoObjetivo.equals("Cryo")) ||
               (tipoPersonaje.equals("Hydro") && tipoObjetivo.equals("Electro"));
    }
}
