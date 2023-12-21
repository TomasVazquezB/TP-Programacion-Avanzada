package Logica;

import BD.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Personaje {
    private int id;  
    private String nombre;
    private String tipo;
    private List<Habilidad> habilidades;
    private Estadistica estadisticas; 
    private Conexion con; 
    private int nivelDeHabilidad;
    private int jugadorId;
    private int estadisticaId;

    public Personaje(String nombre, String tipo, String habilidadInicial) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.habilidades = new ArrayList<>(); 
        this.habilidades.add(new Habilidad(habilidadInicial));
        this.estadisticas = new Estadistica(); 
        this.con = new Conexion(); 
        con.conectar();
    }
    
    public Personaje(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.con = new Conexion(); 
        con.conectar();
        this.habilidades = cargarHabilidadesDesdeBD(nombre); 
        this.estadisticas = new Estadistica(); 
    }

    public Personaje(String nombre, int nivelDeHabilidad, String tipo, int jugadorId, int estadisticaId) {
        this.nombre = nombre;
        this.nivelDeHabilidad = nivelDeHabilidad;
        this.tipo = tipo;
        this.jugadorId = jugadorId;
        this.estadisticaId = estadisticaId;
        this.con = new Conexion(); 
        con.conectar(); 
        this.habilidades = cargarHabilidadesDesdeBD(nombre); 
        this.estadisticas = new Estadistica(); 
    }
    
    @Override
    public String toString() {
        return "Personaje [nombre=" + nombre + ", tipo=" + tipo + ", habilidades=" + habilidades +"]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    //Mostrar las habilidades de los personajes
    public void mostrarHabilidades() {
        JOptionPane.showMessageDialog(null, "Las Habilidades de" + nombre + ":", "Habilidades", JOptionPane.DEFAULT_OPTION);
        for (Habilidad habilidad : habilidades) {
            JOptionPane.showMessageDialog(null, "-" + habilidad.getNombre(), "Habilidad del personaje", JOptionPane.DEFAULT_OPTION);
        }
    }

   //Mostrar las estadisticas de los personajes
    public Estadistica getEstadisticas() {
        try {
            if (con != null) {
                this.estadisticas = con.obtenerEstadisticasPorNombre(nombre);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener la conexión", "Error", JOptionPane.ERROR_MESSAGE);
                this.estadisticas = new Estadistica(); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las estadísticas:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            this.estadisticas = new Estadistica();
        }
        return this.estadisticas;
    }

    public int obtenerVida() {
        return (estadisticas != null) ? estadisticas.getHp() : 0;
    }

    private List<Habilidad> cargarHabilidadesDesdeBD(String nombrePersonaje) {
        List<Habilidad> habilidades = new ArrayList<>();
        try {
            habilidades = con.obtenerHabilidadesPorNombre(nombrePersonaje);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener las habilidades:" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return habilidades;
    }
}


