package Interfaz;

import javax.swing.*;

import Logica.*;

public class Proyect_versus {

	public static void main(String[] args) {
	
	int opcion = 0;	
	Usuario usuario1 = new Usuario (null,null);
	
	do {
		
		String [] opcionesdeinicio =  {"Usuario"};
	    int selecciondeinicio;
	    selecciondeinicio = JOptionPane.showOptionDialog(null,"Bienvenido al menu del juego" ,"Menu de inicio",JOptionPane.DEFAULT_OPTION,JOptionPane.QUESTION_MESSAGE ,null, opcionesdeinicio,opcionesdeinicio[1]);
		
		switch (selecciondeinicio) {
		
		case 0: 
			
			String nombredeusuario,contrasenadeusuario;
			
			nombredeusuario = JOptionPane.showInputDialog("Ingrese el nombre de usuario que desea");
			usuario1.setNombre(nombredeusuario);
		    contrasenadeusuario = JOptionPane.showInputDialog("Ingrese la contraseña que va a utilizar");
		    usuario1.setContrasena(contrasenadeusuario);
			
		}
	    
	    
	} while (opcion == 6);
	
	
	
	
	}

}
