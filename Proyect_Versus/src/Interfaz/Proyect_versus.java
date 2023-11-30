package Interfaz;

import BD.*;
import java.sql.*;
import Logica.*;

public class Proyect_versus {

    public static void main(String[] args) {

        Conexion conexion = new Conexion();
        Connection connection = conexion.conectar();
        Validador sistema = new Validador(connection);
        PantallaDeInicio inicio = new PantallaDeInicio();
		inicio.run();
		  
}
}