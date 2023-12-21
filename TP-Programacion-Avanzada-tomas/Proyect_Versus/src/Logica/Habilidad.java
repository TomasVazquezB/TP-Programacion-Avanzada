package Logica;

import javax.swing.*;

public class Habilidad {
    private String nombre;
    private String descripcion;
    private String personaje_nombre;
    private int danio;  // Nuevo campo

    public String getPersonaje_nombre() {
        return personaje_nombre;
    }

    public void setPersonaje_nombre(String personaje_nombre) {
        this.personaje_nombre = personaje_nombre;
    }

    public Habilidad(String nombre, String descripcion, String personaje_nombre, int danio) {  
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.personaje_nombre = personaje_nombre;
        this.danio = danio;  
    }

    @Override
    public String toString() {
        return "Habilidad [nombre=" + nombre + ", descripcion=" + descripcion + ", personaje_nombre=" + personaje_nombre + "]";
    }

    public Habilidad(String nombre) {
        this.nombre = nombre;
        this.descripcion = "Descripción por defecto";
        this.danio = 0;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDanio() { 
        return danio;
    }

    public void setDanio(int danio) { 
        this.danio = danio;
    }

    public void usarHabilidad(Personaje objetivo) {
        JOptionPane.showMessageDialog(null, "Usando " + nombre + " contra " + objetivo.getNombre(), "Personaje en uso", JOptionPane.DEFAULT_OPTION);

        Estadistica estadisticasObjetivo = objetivo.getEstadisticas();
        if (estadisticasObjetivo != null) {
            estadisticasObjetivo.reducirVida(this.danio); 
            JOptionPane.showMessageDialog(null, "Se ha aplicado " + this.danio  + " de daño a " + objetivo.getNombre() + ".", "Ataque exitoso", JOptionPane.DEFAULT_OPTION);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo aplicar la habilidad a " + objetivo.getNombre() + ". Estadísticas no disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    }

