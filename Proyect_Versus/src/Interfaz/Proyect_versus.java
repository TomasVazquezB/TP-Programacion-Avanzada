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
			
			//Corregir esto y inicio de sesion y que ande todo bien sin bugs
			
			JOptionPane.showMessageDialog(null, "Registrese con sus datos","Registro",JOptionPane.QUESTION_MESSAGE);
			
			String nombredeusuario,contrasenadeusuario;
			
			nombredeusuario = JOptionPane.showInputDialog(null,"Ingrese el nombre de usuario que desea","Registro nombre",JOptionPane.QUESTION_MESSAGE);
			usuario1.setNombre(nombredeusuario);
			contrasenadeusuario = JOptionPane.showInputDialog(null,"Ingrese la contraseña que va a utilizar","Registro contraseña",JOptionPane.QUESTION_MESSAGE);
			usuario1.setContrasena(contrasenadeusuario);
		
			if (usuario1.iniciarSesion("","")==null) {
				JOptionPane.showMessageDialog(null, "Registro fallido", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Registro exitoso", "Registro finalizado", JOptionPane.INFORMATION_MESSAGE);
			}
			
			break;
			
		case 1:
			
			//Si no ingreso que no pueda ver las opciones, y si ingreso que pueda ver las opciones
			
			JOptionPane.showMessageDialog(null, "Ingrese sus datos para iniciar sesion", "Ingreso de datos",JOptionPane.QUESTION_MESSAGE);
			usuario1.iniciarSesion("","");
			
			 if (usuario1.iniciarSesion("","")!=null) {
					
				 JOptionPane.showMessageDialog(null, "Ingreso exitoso","Ingreso",JOptionPane.QUESTION_MESSAGE);
				 Usuario registrado = usuario1.iniciarSesion("","");
				 int opcion2 = JOptionPane.showOptionDialog(null,"Que desea realizar?\n" + "1 Armar equipo\n" +  "2 Jugar una partida\n" ,"Opciones",JOptionPane.DEFAULT_OPTION, 0, null,opciones,opciones[0]);
			
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