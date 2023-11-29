package Interfaz;

import javax.swing.*;


import BD.Conexion;

import java.sql.*;
import Logica.*;
import java.util.ArrayList;
import java.util.List;

public class Proyect_versus {

    private static List<Partida> partidasActivas = new ArrayList<>();

    public static void main(String[] args) {

        Conexion conexion = new Conexion();
        Connection connection = conexion.conectar();
        Validador sistema = new Validador(connection);
        PantallaDeInicio inicio = new PantallaDeInicio();
		inicio.run();
		
  
}
}