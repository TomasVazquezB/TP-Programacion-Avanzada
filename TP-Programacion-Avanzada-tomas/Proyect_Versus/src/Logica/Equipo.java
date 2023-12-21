package Logica;

import java.util.List;

public class Equipo {
    private int id;
    private String nombre;
    private List<Personaje> personajes;

    public Equipo(int id, String nombre, List<Personaje> personajes) {
        this.id = id;
        this.nombre = nombre;
        this.personajes = personajes;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<Personaje> getPersonajes() {
        return personajes;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Equipo ID: ").append(id).append("\n");
        sb.append("Nombre: ").append(nombre).append("\n");
        sb.append("Personajes: ").append("\n");
        for (Personaje personaje : personajes) {
            sb.append("\t").append(personaje.getNombre()).append("\n");
        }
        return sb.toString();
    }
}
