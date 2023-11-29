package Logica;

import BD.Conexion;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Logica.Estadistica;


public class Personaje {
    private String nombre;
    private String tipo;
    private List<Habilidad> habilidades;
    private int vida;
    private Estadistica estadisticas; // Agregamos el atributo de estadísticas

    public Personaje(String nombre, String tipo, String habilidades, int vida) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.habilidades = new ArrayList<>(); // Inicializar la lista de habilidades
        // Asumiendo que deseas agregar la habilidad proporcionada al personaje
        this.habilidades.add(new Habilidad(habilidades));
        this.vida = vida;
    }


    public Personaje(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Personaje [nombre=" + nombre + ", tipo=" + tipo + ", habilidades=" + habilidades + ", vida=" + vida + "]";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<Habilidad> getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(List<Habilidad> habilidades) {
        this.habilidades = habilidades;
    }

    public void agregarHabilidad(Habilidad habilidad) {
        habilidades.add(habilidad);
    }

    public void mostrarHabilidades() {
        JOptionPane.showMessageDialog(null, "Las Habilidades de " + nombre + ":", "Habilidades", JOptionPane.DEFAULT_OPTION);
        for (Habilidad habilidad : habilidades) {
            JOptionPane.showMessageDialog(null, "-" + habilidad.getNombre(), "Habilidad del personaje", JOptionPane.DEFAULT_OPTION);
        }
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void reducirVida(int cantidad) {
        vida -= cantidad;

        if (vida < 0) {
            vida = 0; // Asegura que la vida no sea negativa
        }
    }

    // Método para obtener las estadísticas del personaje desde la base de datos
    public Estadistica getEstadisticas() {
        Conexion conexion = new Conexion(); // Instancia de la clase Conexion
        this.estadisticas = conexion.obtenerEstadisticasPorNombre(nombre);
        return this.estadisticas;
    }

    // Método para obtener el ID del personaje
    public int getId() {
        // Lógica para obtener el ID del personaje
        return 0; // Reemplazar con la lógica de obtención del ID
    }
}
