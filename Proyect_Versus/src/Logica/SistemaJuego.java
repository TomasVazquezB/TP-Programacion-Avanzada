package Logica;

import java.util.ArrayList;
import java.util.List;

class SistemaJuego {
	
    private List<Usuario> usuariosRegistrados;
    private List<Partida> partidasActivas;

    public SistemaJuego() {
        this.usuariosRegistrados = new ArrayList<>();
        this.partidasActivas = new ArrayList<>();
    }

    public SistemaJuego(List<Usuario> usuariosRegistrados, List<Partida> partidasActivas) {
		super();
		this.usuariosRegistrados = usuariosRegistrados;
		this.partidasActivas = partidasActivas;
	}

	@Override
	public String toString() {
		return "SistemaJuego [usuariosRegistrados=" + usuariosRegistrados + ", partidasActivas=" + partidasActivas
				+ "]";
	}

	public List<Usuario> getUsuariosRegistrados() {
		return usuariosRegistrados;
	}

	public void setUsuariosRegistrados(List<Usuario> usuariosRegistrados) {
		this.usuariosRegistrados = usuariosRegistrados;
	}

	public List<Partida> getPartidasActivas() {
		return partidasActivas;
	}

	public void setPartidasActivas(List<Partida> partidasActivas) {
		this.partidasActivas = partidasActivas;
	}

	public void iniciarSesion(String nombre, String contraseña) {
        // Implementa la lógica de inicio de sesión aquí.
    }

    public void crearPartida(Usuario jugador1, Usuario jugador2) {
        Partida partida = new Partida(jugador1, jugador2);
        partidasActivas.add(partida);
    }

    public Usuario buscarUsuarioPorNombre(String nombre) {
        for (Usuario usuario : usuariosRegistrados) {
            if (usuario.getNombre().equals(nombre)) {
                return usuario;
            }
        }
        return null; // Usuario no encontrado
    }

    public void registrarUsuario(Usuario usuario) {
        usuariosRegistrados.add(usuario);
    }
}
