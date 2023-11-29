package Logica;

import BD.Conexion;
import java.util.ArrayList;
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
    
    public void jugar() {
        while (jugadorTieneVida() && maquinaTieneVida()) { 
            realizarAtaque(jugador, jugadorPersonaje, maquina, maquinaPersonaje);
            if (!maquinaTieneVida()) {
                JOptionPane.showMessageDialog(null, "¡Has derrotado a la máquina!");
                break;
            }

            realizarAtaqueMaquina(jugador, jugadorPersonaje, maquina, maquinaPersonaje);
            if (!jugadorTieneVida()) {
                JOptionPane.showMessageDialog(null, "¡La máquina te ha derrotado!");
                break;
            }
        }

        if (!jugadorTieneVida()) {
          registrarPartida(jugador, "Derrota");
        } else if (!maquinaTieneVida()) {
            registrarPartida(jugador, "Victoria");
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
        boolean exito = con.actualizarResultadoPartida(usuario, resultado);

        if (exito) {
            System.out.println("Resultado de la partida guardado en la base de datos.");
        } else {
            System.out.println("Error al guardar el resultado de la partida en la base de datos.");
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
        }

        private int obtenerCantidadPersonajesAleatorios(List<Personaje> personajesDisponibles) {
     
            int maxCantidad = Math.min(4, personajesDisponibles.size());

            return (int) (Math.random() * maxCantidad) + 1;
        }

        private void realizarAtaque(Usuario atacante, List<Personaje> atacantePersonajes, Usuario defensor, List<Personaje> defensorPersonajes) {
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
            System.out.println(atacante.getNombre() + " usó " + habilidadAtaque.getNombre() +
                    " y causó " + danio + " de daño a " + personajeDefensor.getNombre() + ".");
        }

        private void realizarAtaqueMaquina(Usuario jugador, List<Personaje> jugadorPersonajes, Usuario maquina, List<Personaje> maquinaPersonajes) {
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
            JOptionPane.showMessageDialog(null, mensajeAtaqueMaquina);
        }
}
