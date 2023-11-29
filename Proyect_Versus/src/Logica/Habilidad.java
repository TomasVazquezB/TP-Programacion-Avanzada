package Logica;

import java.util.List;

import javax.swing.*;

public class Habilidad {
	
    private String nombre;
    private String descripcion;
    private int efecto;
	private String personaje_nombre;

    public String getPersonaje_nombre() {
		return personaje_nombre;
	}

	public void setPersonaje_nombre(String personaje_nombre) {
		this.personaje_nombre = personaje_nombre;
	}

	public Habilidad(String nombre, String descripcion, int efecto, String personaje_nombre) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.efecto = efecto;
        this.personaje_nombre = personaje_nombre;
    }

    public int getEfecto() {
		return efecto;
	}

	public void setEfecto(int efecto) {
		this.efecto = efecto;
	}

	public Habilidad(String nombre) {
        this.nombre = nombre;
        // Puedes inicializar la descripción y el efecto según tus necesidades
        this.descripcion = "Descripción por defecto";
        this.efecto = 0;
    }

	@Override
	public String toString() {
		return "Habilidad [nombre=" + nombre + ", descripcion=" + descripcion + ", efecto=" + efecto
				+ ", personaje_nombre=" + personaje_nombre + "]";
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getefecto() {
		return efecto;
	}

	public void setefecto(int efecto) {
		this.efecto = efecto;
	}
	
	public String getNombre() {
	        return nombre;
	    }
	 
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void usarHabilidad(Personaje objetivo) {
	    JOptionPane.showMessageDialog(null, "Usando " + nombre + " contra " + objetivo.getNombre() + ". Efecto: " + efecto, "Personaje en uso", JOptionPane.DEFAULT_OPTION);

	    // Lógica adicional, por ejemplo, reducir la vida del personaje objetivo
	    objetivo.reducirVida(efecto);
	}
}