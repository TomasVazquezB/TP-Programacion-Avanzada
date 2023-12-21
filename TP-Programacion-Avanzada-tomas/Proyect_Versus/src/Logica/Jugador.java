package Logica;

import java.util.ArrayList;
import java.util.List;

public abstract class Jugador {
	protected List<Personaje> equipo;

    public Jugador() {
        this.equipo = new ArrayList<>();
    }

    public List<Personaje> getEquipo() {
        return equipo;
    }
    
    public abstract void crearEquipoAleatorio();

}

