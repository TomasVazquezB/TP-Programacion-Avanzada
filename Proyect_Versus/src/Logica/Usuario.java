package Logica;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	
    private String nombre;
    private int nivelCuenta;
    private int nivelClasificatorias;
    private List<Partida> historial;

    public Usuario(String nombre) {
        this.nombre = nombre;
        this.nivelCuenta = 1;
        this.nivelClasificatorias = 1;
        this.historial = new ArrayList<>();
    }

  
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNivelCuenta() {
		return nivelCuenta;
	}

	public void setNivelCuenta(int nivelCuenta) {
		this.nivelCuenta = nivelCuenta;
	}

	public int getNivelClasificatorias() {
		return nivelClasificatorias;
	}

	public void setNivelClasificatorias(int nivelClasificatorias) {
		this.nivelClasificatorias = nivelClasificatorias;
	}

	public List<Partida> getHistorial() {
		return historial;
	}

	public void setHistorial(List<Partida> historial) {
		this.historial = historial;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", nivelCuenta=" + nivelCuenta + ", nivelClasificatorias="
				+ nivelClasificatorias + ", historial=" + historial + "]";
	}

	public Usuario(String nombre, int nivelCuenta, int nivelClasificatorias, List<Partida> historial) {
		super();
		this.nombre = nombre;
		this.nivelCuenta = nivelCuenta;
		this.nivelClasificatorias = nivelClasificatorias;
		this.historial = historial;
	}
	public void actualizarNivelCuenta(int nuevoNivel) {
        this.nivelCuenta = nuevoNivel;
    }

    public void actualizarNivelClasificatorias(int nuevoNivel) {
        this.nivelClasificatorias = nuevoNivel;
    }

    public void registrarPartida(Partida partida) {
        historial.add(partida);
    }

}