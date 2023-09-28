package Interfaz;

import javax.swing.*;

import Logica.*;

public class Proyect_versus {

	public static void main(String[] args) {
	
	Usuario usuario1 = new Usuario (null,null);
	SistemaJuego sistema = new SistemaJuego ();
	
	while (true) {
	
		String[] opciones =  {"Registrar Usuario","Iniciar Sesion","Salir"};
		String [] opciondeingreso = {"Si","No"};
		
		int seleccion =JOptionPane.showOptionDialog(null,"Bienvenido al menu del juego" ,"Menu de inicio",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE ,null, opciones,opciones[0]);
		
		
		switch (seleccion) {
		
		case 0:
            // Registro de usuario
            String nombreUsuario, contrasenaUsuario;
            
            JOptionPane.showMessageDialog(null, "Registrese con sus datos", "Registro", JOptionPane.QUESTION_MESSAGE);
            
            nombreUsuario = JOptionPane.showInputDialog(null, "Ingrese el nombre de usuario que desea", "Registro nombre", JOptionPane.QUESTION_MESSAGE);
            contrasenaUsuario = JOptionPane.showInputDialog(null, "Ingrese la contraseña que va a utilizar", "Registro contraseña", JOptionPane.QUESTION_MESSAGE);
            
            // Crear un nuevo usuario y registrar
            Usuario nuevoUsuario = new Usuario(nombreUsuario);
            nuevoUsuario.setContrasena(contrasenaUsuario);
            
            sistema.registrarUsuario(nuevoUsuario); // Registrar el nuevo usuario en el sistema
            
            JOptionPane.showMessageDialog(null, "Registro exitoso", "Registro finalizado", JOptionPane.INFORMATION_MESSAGE);
            break;

        case 1:
            // Iniciar sesión
            String nombreIngreso, contrasenaIngreso;

            JOptionPane.showMessageDialog(null, "Ingrese sus datos para iniciar sesión", "Ingreso de datos", JOptionPane.QUESTION_MESSAGE);
            
            nombreIngreso = JOptionPane.showInputDialog(null, "Ingrese su nombre de usuario", "Iniciar Sesión", JOptionPane.QUESTION_MESSAGE);
            contrasenaIngreso = JOptionPane.showInputDialog(null, "Ingrese su contraseña", "Iniciar Sesión", JOptionPane.QUESTION_MESSAGE);
            
            Usuario usuarioLogueado = sistema.iniciarSesion(nombreIngreso, contrasenaIngreso);
            
            if (usuarioLogueado != null) {
                JOptionPane.showMessageDialog(null, "Ingreso exitoso", "Ingreso", JOptionPane.INFORMATION_MESSAGE);
                
                // Aquí puedes agregar lógica adicional para las opciones del usuario logueado
                String[] opcionesLogueado = {"Armar equipo", "Jugar una partida", "Salir"};
                int seleccionLogueado = JOptionPane.showOptionDialog(
                    null,
                    "¿Qué desea realizar?",
                    "Opciones",
                    JOptionPane.DEFAULT_OPTION,
                    0,
                    null,
                    opcionesLogueado,
                    opcionesLogueado[0]
                );
                
                // Implementa la lógica para las opciones del usuario logueado aquí
            } else {
                JOptionPane.showMessageDialog(null, "Inicio de sesión fallido", "Error", JOptionPane.ERROR_MESSAGE);
            }
            break;
						
		case 2:
			
			JOptionPane.showMessageDialog(null, "Lista de equipos que tiene:", "Equipos",JOptionPane.QUESTION_MESSAGE);
			
			JOptionPane.showMessageDialog(null, "Los personajes que tiene para elegir son:", "Personajes", JOptionPane.QUESTION_MESSAGE);
			
		 
			
		   JOptionPane.showConfirmDialog(null, "Esta seguro que quiere guardar ese equipo? Si o No", "Guardar equipo", JOptionPane.YES_NO_OPTION);
			
			break;
			
			
		case 3:
			
			JOptionPane.showMessageDialog(null, "Con que equipo desea jugar?", "Listado de equipos", JOptionPane.QUESTION_MESSAGE);
			
		    JOptionPane.showMessageDialog(null, "La partida va a empezar, esta listo", "Partida iniciando", JOptionPane.QUESTION_MESSAGE);
			
			break;
				
		case 4: 
			
	    JOptionPane.showMessageDialog(null, "Gracias por jugar. ¡Hasta Luego!");
			
	    System.exit(0);
			
		default:
			break;
		}
	
	}
}
}