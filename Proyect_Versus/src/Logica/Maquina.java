package Logica;

import java.util.List;
import java.util.Random;

public class Maquina extends Jugador {
    private Usuario usuario;

    public Maquina() {
		super();
	}

	public Maquina(Usuario usuario) {
        super();
        this.usuario = usuario;
    }

    @Override
    public void crearEquipoAleatorio() {
        List<Personaje> personajesDisponibles = usuario.obtenerPersonajesDisponibles();
        Random rand = new Random();

        for (int i = 0; i < 4; i++) {
            int indiceAleatorio = rand.nextInt(personajesDisponibles.size());
            Personaje personajeSeleccionado = personajesDisponibles.get(indiceAleatorio);
            getEquipo().add(personajeSeleccionado);
            personajesDisponibles.remove(indiceAleatorio);
        }
    }
}
