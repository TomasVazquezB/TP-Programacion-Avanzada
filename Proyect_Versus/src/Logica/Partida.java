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
    private Conexion con; // Instancia de Conexion

    public Partida(Usuario jugador, Conexion con) {
        this.jugador = jugador;
        this.jugadorPersonaje = cargarPersonajesDesdeBD(jugador);
        this.maquina = new Usuario("Maquina"); // Crear un usuario para la máquina
        this.maquinaPersonaje = cargarPersonajesAleatorios();
        this.con = con; // Asignar la instancia de Conexion
    }


    public void jugar() {
    	JOptionPane.showMessageDialog(null,maquinaPersonaje);
    	JOptionPane.showMessageDialog(null,jugadorPersonaje);
        while (jugadorTieneVida() && maquinaTieneVida()) {
            realizarAtaque(jugador, jugadorPersonaje, maquina, maquinaPersonaje);
            if (!maquinaTieneVida()) {
                // La máquina ha perdido todas las vidas, jugador gana
                JOptionPane.showMessageDialog(null, "¡Has derrotado a la máquina!");
                break;
            }

            realizarAtaqueMaquina(jugador, jugadorPersonaje, maquina, maquinaPersonaje);
            if (!jugadorTieneVida()) {
                // El jugador ha perdido todas las vidas, máquina gana
                JOptionPane.showMessageDialog(null, "¡La máquina te ha derrotado!");
                break;
            }
        }

        // Al finalizar la partida, actualiza la base de datos solo si es necesario
        if (!jugadorTieneVida()) {
            // Actualiza la base de datos con el resultado de la partida (derrota del jugador)
            registrarPartida(jugador, "Derrota");
        } else if (!maquinaTieneVida()) {
            // Actualiza la base de datos con el resultado de la partida (victoria del jugador)
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
        Conexion conexion = new Conexion(); // Instancia de la clase Conexion
        List<String> nombresPersonajes = conexion.cargarPersonajesDesdeBD(usuario);

        // Lista para almacenar los objetos Personaje
        List<Personaje> personajes = new ArrayList<>();
        for (String nombrePersonaje : nombresPersonajes) {
            // Crea un objeto Personaje usando el nombre obtenido de la base de datos
            Personaje nuevoPersonaje = new Personaje(nombrePersonaje, nombrePersonaje);
            // Agrega el personaje a la lista
            personajes.add(nuevoPersonaje);
        }
        // Retornar la lista de Personaje cargados desde la base de datos
        return personajes;
    }

        private List<Personaje> cargarPersonajesAleatorios() {
            Conexion conexion = new Conexion(); // Instancia de la clase Conexion
            List<String> nombresPersonajesDisponibles = conexion.obtenerNombresPersonajesDisponibles();

            List<Personaje> personajesAleatorios = new ArrayList<>();

            int cantidadPersonajes = obtenerCantidadPersonajesAleatorios(personajesAleatorios);

            // Seleccionar aleatoriamente los nombres de personajes de la lista
            Random random = new Random();
            for (int i = 0; i < cantidadPersonajes; i++) {
                // Obtener un índice aleatorio dentro del rango de nombres disponibles
                int indiceAleatorio = random.nextInt(nombresPersonajesDisponibles.size());

                // Obtener el nombre del personaje aleatorio
                String nombrePersonaje = nombresPersonajesDisponibles.get(indiceAleatorio);

                // Crear un objeto Personaje usando el nombre obtenido aleatoriamente
                Personaje nuevoPersonaje = new Personaje(nombrePersonaje, nombrePersonaje);

                // Agregar el personaje a la lista de personajes aleatorios
                personajesAleatorios.add(nuevoPersonaje);

                // Eliminar el nombre seleccionado para evitar repetición
                nombresPersonajesDisponibles.remove(indiceAleatorio);
            }

            // Retornar la lista de Personaje generados aleatoriamente para la máquina
            return personajesAleatorios;
        }

        private int obtenerCantidadPersonajesAleatorios(List<Personaje> personajesDisponibles) {
            // Limitar la cantidad máxima de personajes que la máquina puede elegir a 4
            int maxCantidad = Math.min(4, personajesDisponibles.size());

            return (int) (Math.random() * maxCantidad) + 1;
        }

        private void realizarAtaque(Usuario atacante, List<Personaje> atacantePersonajes, Usuario defensor, List<Personaje> defensorPersonajes) {
            Random random = new Random();
            int indexAtacante = random.nextInt(atacantePersonajes.size());
            int indexDefensor = random.nextInt(defensorPersonajes.size());

            Personaje personajeAtacante = atacantePersonajes.get(indexAtacante);
            Personaje personajeDefensor = defensorPersonajes.get(indexDefensor);

            // Simulación de ataque usando habilidades y estadísticas
            int defensa = personajeDefensor.getEstadisticas().getDef();

            // Obtener una habilidad aleatoria del personaje atacante
            List<Habilidad> habilidades = personajeAtacante.getHabilidades();
            int indexHabilidad = random.nextInt(habilidades.size());
            Habilidad habilidadAtaque = habilidades.get(indexHabilidad);

            // Realizar cálculo de daño basado en la habilidad y estadísticas
            int danio = habilidadAtaque.getefecto() - defensa;
            danio = Math.max(danio, 0); // Para asegurar que el daño no sea negativo

            // Reducir la vida del personaje defensor según el daño calculado
            int vidaActualDefensor = personajeDefensor.getVida();
            int nuevoValorVidaDefensor = vidaActualDefensor - danio;

            personajeDefensor.setVida(Math.max(nuevoValorVidaDefensor, 0));
            System.out.println(atacante.getNombre() + " usó " + habilidadAtaque.getNombre() +
                    " y causó " + danio + " de daño a " + personajeDefensor.getNombre() + ".");
        }


        private void realizarAtaqueMaquina(Usuario jugador, List<Personaje> jugadorPersonajes, Usuario maquina, List<Personaje> maquinaPersonajes) {
            Random random = new Random();

            // Seleccionar el personaje de la máquina que atacará
            int indexAtacante = random.nextInt(maquinaPersonajes.size());
            Personaje personajeAtacante = maquinaPersonajes.get(indexAtacante);

            // Seleccionar el personaje del jugador que defenderá
            int indexDefensor = random.nextInt(jugadorPersonajes.size());
            Personaje personajeDefensor = jugadorPersonajes.get(indexDefensor);

            // Obtener una habilidad aleatoria del personaje atacante de la máquina
            List<Habilidad> habilidadesMaquina = personajeAtacante.getHabilidades();
            int indexHabilidad = random.nextInt(habilidadesMaquina.size());
            Habilidad habilidadAtaqueMaquina = habilidadesMaquina.get(indexHabilidad);

            // Obtener la defensa del personaje del jugador
            int defensaJugador = personajeDefensor.getEstadisticas().getDef();

            // Realizar cálculo de daño basado en la habilidad y estadísticas de la máquina
            int danioMaquina = habilidadAtaqueMaquina.getefecto() - defensaJugador;
            danioMaquina = Math.max(danioMaquina, 0); // Asegurarse de que el daño no sea negativo

            // Reducir la vida del personaje del jugador según el daño calculado por la máquina
            int vidaActualJugador = personajeDefensor.getVida();
            int nuevoValorVidaJugador = vidaActualJugador - danioMaquina;
            personajeDefensor.setVida(Math.max(nuevoValorVidaJugador, 0));

            // Mostrar información sobre el ataque de la máquina
            String mensajeAtaqueMaquina = "La máquina usó " + habilidadAtaqueMaquina.getNombre() +
                    " y causó " + danioMaquina + " de daño a " + personajeDefensor.getNombre() + ".";
            JOptionPane.showMessageDialog(null, mensajeAtaqueMaquina);
        }
}
