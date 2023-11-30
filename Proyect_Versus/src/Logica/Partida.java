package Logica;

import BD.Conexion;
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
        this.con = con;  // Asegúrate de que con se inicializa correctamente
    }

    public void jugar() {
        try {
            // Otras partes del código

            // Batalla
            while (jugadorTieneVida() && maquinaTieneVida()) {
                realizarRonda(jugador, jugadorPersonaje, maquina, maquinaPersonaje);
            }

            // Resultado de la partida
            determinarResultadoPartida();
        } catch (Exception e) {
            mostrarMensaje("Error durante la batalla: " + e.getMessage());
        }
    }

    private void realizarRonda(Usuario jugador, List<Personaje> jugadorPersonajes, Usuario maquina, List<Personaje> maquinaPersonajes) {
        // Ordenar los personajes de ambos equipos de manera aleatoria
        Collections.shuffle(jugadorPersonajes);
        Collections.shuffle(maquinaPersonajes);

        // Turno del jugador
        for (Personaje personaje : jugadorPersonajes) {
            realizarTurno(jugador, jugadorPersonajes, maquina, maquinaPersonajes, personaje);
        }

        // Turno de la máquina
        for (Personaje personaje : maquinaPersonajes) {
            realizarTurnoMaquina(jugador, jugadorPersonajes, maquina, maquinaPersonajes, personaje);
        }
    }

    private void realizarTurno(Usuario jugador, List<Personaje> jugadorPersonajes, Usuario maquina, List<Personaje> maquinaPersonajes, Personaje atacante) {
        // Seleccionar un objetivo de la máquina de manera aleatoria
        Personaje defensor = seleccionarDefensorAleatorio(maquinaPersonajes);

        // Realizar ataque del jugador
        realizarAtaque(atacante, jugador, defensor);

        // Verificar si el defensor quedó sin vida
        if (defensor.getVida() <= 0) {
            maquinaPersonajes.remove(defensor);
            mostrarMensaje("¡" + defensor.getNombre() + " ha sido derrotado!");
        }
    }

    private void realizarTurnoMaquina(Usuario jugador, List<Personaje> jugadorPersonajes, Usuario maquina, List<Personaje> maquinaPersonajes, Personaje atacante) {
        // Seleccionar un objetivo del jugador de manera aleatoria
        Personaje defensor = seleccionarDefensorAleatorio(jugadorPersonajes);

        // Realizar ataque de la máquina
        realizarAtaque(atacante, maquina, defensor);

        // Verificar si el defensor quedó sin vida
        if (defensor.getVida() <= 0) {
            jugadorPersonajes.remove(defensor);
            mostrarMensaje("¡" + defensor.getNombre() + " ha sido derrotado por la máquina!");
        }
    }

    private Personaje seleccionarDefensorAleatorio(List<Personaje> personajes) {
        Collections.shuffle(personajes);
        return personajes.get(0);
    }

    private void realizarAtaque(Personaje atacante, Usuario defensorUsuario, Personaje defensor) {
        // Lógica para calcular el daño
        int ataque = atacante.getEstadisticas().getAtk();
        int defensa = defensor.getEstadisticas().getEr();
        int danio = Math.max(ataque - defensa, 0);

        // Aplicar el daño al personaje defensor
        int vidaActualDefensor = defensor.getVida();
        int nuevoValorVidaDefensor = vidaActualDefensor - danio;
        defensor.setVida(Math.max(nuevoValorVidaDefensor, 0));

        // Mensaje para informar sobre el ataque
        mostrarMensaje(atacante.getNombre() + " atacó a " + defensor.getNombre() +
                " y causó " + danio + " de daño.");
    }

    private void determinarResultadoPartida() {
        // Lógica para determinar el resultado de la partida
        if (!jugadorTieneVida()) {
            registrarPartida(jugador, "Derrota");
            mostrarMensaje("¡La máquina ha ganado la batalla!");
        } else if (!maquinaTieneVida()) {
            registrarPartida(jugador, "Victoria");
            mostrarMensaje("¡Has ganado la batalla!");
        } else {
            registrarPartida(jugador, "Empate");
            mostrarMensaje("La batalla terminó en empate.");
        }
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

    
    
    
    private void registrarPartida(Usuario usuario, String resultado) {
        try {
            boolean exito = con.actualizarResultadoPartida(usuario, resultado);

<<<<<<< HEAD
            if (exito) {
                mostrarMensaje("Resultado de la partida guardado en la base de datos.");
            } else {
                mostrarMensaje("Error al guardar el resultado de la partida en la base de datos.");
            }
        } catch (Exception e) {
            mostrarMensaje("Error al registrar la partida en la base de datos: " + e.getMessage());
=======
        if (exito) {	  	
         JOptionPane.showMessageDialog(null,"Resultado de la partida guardado en la base de datos.","Resultado Guardado",JOptionPane.DEFAULT_OPTION);
        } else {
        	JOptionPane.showMessageDialog(null,"Error al guardar el resultado de la partida en la base de datos.");
>>>>>>> e8c5045767979f586ae777f8dc93cb1f11f8a3ef
        }
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
            mostrarMensaje("Error al cargar personajes aleatorios: " + e.getMessage());
            return new ArrayList<>(); // Retorna una lista vacía en caso de error
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
            mostrarMensaje(atacante.getNombre() + " usó " + habilidadAtaque.getNombre() +
                    " y causó " + danio + " de daño a " + personajeDefensor.getNombre() + ".");
        } catch (Exception e) {
            mostrarMensaje("Error al realizar el ataque: " + e.getMessage());
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

            String mensajeAtaqueMaquina = "La máquina usó " + habilidadAtaqueMaquina.getNombre() +
                    " y causó " + danioMaquina + " de daño a " + personajeDefensor.getNombre() + ".";
            mostrarMensaje(mensajeAtaqueMaquina);

        } catch (Exception e) {
            mostrarMensaje("Error en el ataque de la máquina: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje) {
        // Puedes ajustar este método para mostrar mensajes de la manera que prefieras
        System.out.println(mensaje);
        JOptionPane.showMessageDialog(null, mensaje);
    }
}