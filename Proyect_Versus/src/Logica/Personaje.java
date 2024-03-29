package Logica;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Personaje {
	
    private String nombre;
    private String tipo;
    private List<Habilidad> habilidades;

    public Personaje(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.habilidades = new ArrayList<>();
    }

    public Personaje(String nombre, String tipo, List<Habilidad> habilidades) {
    	super();
    	this.nombre = nombre;
    	this.tipo = tipo;
    	this.habilidades = habilidades;
    }
        
    @Override
    public String toString() {
    	return "Personaje [nombre=" + nombre + ", tipo=" + tipo + ", habilidades=" + habilidades + "]";
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
       JOptionPane.showMessageDialog(null,"Las Habilidades de" + nombre + ":","Habilidades",JOptionPane.DEFAULT_OPTION);
        for (Habilidad habilidad : habilidades) {
           JOptionPane.showMessageDialog(null,"-" + habilidad.getNombre(),"Habilidad del personaje",JOptionPane.DEFAULT_OPTION);
        }
    }
}