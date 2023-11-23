package BD;

import javax.swing.*;


import java.sql.*;

public class Conexion {

Connection con ;
	
	public Connection conectar() {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD Juego Por Turnos","root","");
			//JOptionPane.showMessageDialog(null, "Se conecto");
		} catch (Exception e) {
	
			JOptionPane.showMessageDialog(null, "Error al conectarse");
		}
		return con;
	}

public boolean validarConexion() {
    Connection con = null;

    try {
        con = conectar();
        if (con != null) {
            JOptionPane.showMessageDialog(null, "Conexión exitosa");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error al conectarse");
            return false;
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error al conectarse");
        return false;
    } finally {
        try {
            if (con != null) con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión");
        }
    }
} } 