package logica;

import java.util.ArrayList;
import java.util.List;

public class Usuario implements InicioDeSesion{
	
    private String nombre;
    private String contrasena;
    private int nivelCuenta;
    private int nivelClasificatorias;
    private List<Partida> historial;
    private Personaje personaje;

    public Usuario(String nombre,String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.nivelCuenta = 1;
        this.nivelClasificatorias = 1;
        this.historial = new ArrayList<>();
    }

	public Usuario(String nombre, String contrasena, int nivelCuenta, int nivelClasificatorias, List<Partida> historial) {
		super();
		this.nombre = nombre;
		this.contrasena = contrasena;
		this.nivelCuenta = nivelCuenta;
		this.nivelClasificatorias = nivelClasificatorias;
		this.historial = historial;
	}

	@Override
	public String toString() {
		return "Usuario [nombre=" + nombre + ", contrasena=" + contrasena + ", nivelCuenta=" + nivelCuenta
				+ ", nivelClasificatorias=" + nivelClasificatorias + ", historial=" + historial + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
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
	public void actualizarNivelCuenta(int nuevoNivel) {
        this.nivelCuenta = nuevoNivel;
    }

    public void actualizarNivelClasificatorias(int nuevoNivel) {
        this.nivelClasificatorias = nuevoNivel;
    }

    public void registrarPartida(Partida partida) {
        historial.add(partida);
    }

	@Override
	public void menu() {
		
	}

	@Override
	public void cerrarSesion() {
		
	}

	public Personaje getPersonaje() {
		return personaje;
	}

	public void setPersonaje(Personaje personaje) {
		this.personaje = personaje;
	}

}