package Logica;

import BD.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Personaje {
    private String nombre;
    private String tipo;
    private List<Habilidad> habilidades;
    private Estadistica estadisticas; 
    private Conexion con; 

    public Personaje(String nombre, String tipo, String habilidadInicial) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.habilidades = new ArrayList<>(); 
        this.habilidades.add(new Habilidad(habilidadInicial));
    }
    
    public Personaje(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
    
    @Override
    public String toString() {
        return "Personaje [nombre=" + nombre + ", tipo=" + tipo + ", habilidades=" + habilidades +"]";
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

    public Estadistica getEstadisticas() {
        try {
            if (con != null) {
                this.estadisticas = con.obtenerEstadisticasPorNombre(nombre);
                return this.estadisticas;
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo obtener la conexión.", "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener estadísticas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public int getId() {
        return 0;
    }

    public int obtenerVida() {
        return (estadisticas != null) ? estadisticas.getHp() : 0;
    }
}

