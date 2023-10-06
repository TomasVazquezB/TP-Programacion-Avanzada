package BD;

import javax.swing.*;
import java.sql.*;

public class Conexion {

Connection con ;
	
	public Connection conectar() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3463/BD Juego Por Turnos","root","");
			//JOptionPane.showMessageDialog(null, "Se conecto");
		} catch (Exception e) {
	
			JOptionPane.showMessageDialog(null, "Error al conectarse");
		}
		return con;
	}
}