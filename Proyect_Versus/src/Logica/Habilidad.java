package Logica;

import javax.swing.*;

public class Habilidad {
	
    private String nombre;
    private String descripcion;
    private int daño;

    public Habilidad(String nombre, String descripcion, int daño) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.daño = daño;
	}

	@Override
	public String toString() {
		return "Habilidad [nombre=" + nombre + ", descripcion=" + descripcion + ", daño=" + daño + "]";
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDaño() {
		return daño;
	}

	public void setDaño(int daño) {
		this.daño = daño;
	}
	
	public String getNombre() {
	        return nombre;
	    }
	 
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void usarHabilidad(Personaje objetivo) {
     JOptionPane.showMessageDialog(null,"Usando " + nombre + " contra " + objetivo.getNombre() + ". Daño: " + daño,"Personaje en uso",JOptionPane.DEFAULT_OPTION);
    }
}