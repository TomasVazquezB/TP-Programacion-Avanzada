package Logica;

import javax.swing.*;

public class Habilidad {
	
    private String nombre;
    private String descripcion;
    private int efecto;

    public Habilidad(String nombre, String descripcion, int efecto) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.efecto = efecto;
	}

	@Override
	public String toString() {
		return "Habilidad [nombre=" + nombre + ", descripcion=" + descripcion + ", efecto=" + efecto + "]";
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
     JOptionPane.showMessageDialog(null,"Usando " + nombre + " contra " + objetivo.getNombre() + ". efecto: " + efecto,"Personaje en uso",JOptionPane.DEFAULT_OPTION);
    }
}