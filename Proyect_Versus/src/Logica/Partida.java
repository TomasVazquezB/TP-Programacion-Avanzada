package Logica;

public class Partida {
	
    private Usuario jugador1;
    private Usuario jugador2;
    private Usuario ganador;

    public Partida(Usuario jugador1, Usuario jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
    }

  
	public Partida(Usuario jugador1, Usuario jugador2, Usuario ganador) {
		super();
		this.jugador1 = jugador1;
		this.jugador2 = jugador2;
		this.ganador = ganador;
	}

	@Override
	public String toString() {
		return "Partida [jugador1=" + jugador1 + ", jugador2=" + jugador2 + ", ganador=" + ganador + "]";
	}

	public Usuario getJugador1() {
		return jugador1;
	}

	public void setJugador1(Usuario jugador1) {
		this.jugador1 = jugador1;
	}

	public Usuario getJugador2() {
		return jugador2;
	}

	public void setJugador2(Usuario jugador2) {
		this.jugador2 = jugador2;
	}

	public Usuario getGanador() {
		return ganador;
	}

	public void setGanador(Usuario ganador) {
		this.ganador = ganador;
	}
	
	public void finalizarPartida(Usuario ganador) {
        this.ganador = ganador;
    }

    public void mostrarResumen() {
        System.out.println("Resumen de la partida:");
        System.out.println("Jugador 1: " + jugador1.getNombre());
        System.out.println("Jugador 2: " + jugador2.getNombre());
        if (ganador != null) {
            System.out.println("Ganador: " + ganador.getNombre());
        } else {
            System.out.println("La partida está en curso.");
        }
    }
}