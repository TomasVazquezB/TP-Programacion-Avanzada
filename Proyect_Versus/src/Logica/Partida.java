package logica;

import javax.swing.*;

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
        JOptionPane.showMessageDialog(null,"Resumen de la partida:","Resumen de la partida",JOptionPane.DEFAULT_OPTION);
        JOptionPane.showMessageDialog(null,"Jugador 1:" + jugador1.getNombre());
        JOptionPane.showMessageDialog(null,"Jugador 2:" + jugador2.getNombre());
        if (ganador != null) {
        JOptionPane.showMessageDialog(null,"El Ganador es:" + ganador.getNombre(),"Ganador",JOptionPane.DEFAULT_OPTION);
        } else {
        JOptionPane.showMessageDialog(null,"La partida está en curso","Partida en curso",JOptionPane.DEFAULT_OPTION);
        }
    }
}