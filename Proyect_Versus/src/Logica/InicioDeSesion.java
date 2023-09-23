package Logica;

public interface InicioDeSesion {

default Usuario iniciarSesion(String nombre,String contrasena) {
		
		if (nombre.equals("")) {
			Usuario nuevo = new Usuario(nombre,contrasena);
			return nuevo;
		}else {
			
			return null;
		}
	}
	
	void menu();
	
	void cerrarSesion();
		
}