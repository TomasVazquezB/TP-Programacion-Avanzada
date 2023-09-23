package Interfaz;

import javax.swing.*;

import Logica.*;

public class Proyect_versus {

	public static void main(String[] args) {
	
	int opcion = 0;	
	Usuario usuario1 = new Usuario (null,null);
	
	do {
		
		String [] opcionesdeinicio = {"Usuario"};
	    int selecciondeinicio;
	    selecciondeinicio = JOptionPane.showOptionDialog(null,"Bienvenido al menu del juego" ,"Menu de inicio",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE ,null, opcionesdeinicio,opcionesdeinicio[1]);
		
		switch (selecciondeinicio) {
		
		case 0: 
			
			JOptionPane.showMessageDialog(null, "Registrese con sus datos","Registro",JOptionPane.QUESTION_MESSAGE);
			
			String nombredeusuario,contrasenadeusuario;
	
			nombredeusuario = JOptionPane.showInputDialog(null,"Ingrese el nombre de usuario que desea","Registro nombre",JOptionPane.QUESTION_MESSAGE);
			usuario1.setNombre(nombredeusuario);
		    contrasenadeusuario = JOptionPane.showInputDialog(null,"Ingrese la contraseña que va a utilizar","Registro contraseña",JOptionPane.QUESTION_MESSAGE);
		    usuario1.setContrasena(contrasenadeusuario);
		
		    if (usuario1.iniciarSesion("","")!=null) {
				
		    	JOptionPane.showMessageDialog(null, "Regristro exitoso", "Registro finalizado",JOptionPane.QUESTION_MESSAGE);
		    	
			}else {
				
				JOptionPane.showMessageDialog(null, "Regristro fallido", "Error",JOptionPane.QUESTION_MESSAGE);
				
			}
		
		break;
		
	case 1:
		
		JOptionPane.showMessageDialog(null, "Ingrese sus datos para iniciar sesion", "Ingreso de datos",JOptionPane.QUESTION_MESSAGE);
		usuario1.iniciarSesion(null, null);
		
		 if (usuario1.iniciarSesion("","")!=null) {
				
			 JOptionPane.showMessageDialog(null, "Ingreso exitoso","Ingreso",JOptionPane.QUESTION_MESSAGE);
			 Usuario registrado = usuario1.iniciarSesion("","");
			 int opcion2 = JOptionPane.showOptionDialog(null,"Que desea realizar?\n" + "1 Jugar una partida\n" +  "2 Armar un equipo\n" ,"Opciones",JOptionPane.DEFAULT_OPTION, 0, null,opcionesdeinicio,opcionesdeinicio[0]);
		
			}
			
			break;
			
	case 2: 
		
		
		
		
		break;
	
	case 3: 
		
		JOptionPane.showMessageDialog(null, "Lista de equipos que tiene:", "Equipos",JOptionPane.QUESTION_MESSAGE);
		
		JOptionPane.showMessageDialog(null, "Los personajes que tiene para elegir son:", "Personajes", JOptionPane.QUESTION_MESSAGE);
		
		
		break; 
		
		
		}

	} while (opcion == 6);
		
	
	}
}
