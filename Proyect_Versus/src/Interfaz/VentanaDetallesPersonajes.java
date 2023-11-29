package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import Logica.Estadistica;
import Logica.Habilidad;
import BD.Conexion;

public class VentanaDetallesPersonajes extends JFrame {

	private JPanel contentPane;
    private Conexion conexion; 

    public VentanaDetallesPersonajes(Conexion conexion) {
        this.conexion = conexion;
        setTitle("Project Versus");
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\icono.jpg"));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        cargarDetallesPersonajes(); 
    }

    private void cargarDetallesPersonajes() {
        List<String> nombresPersonajes = conexion.obtenerNombresPersonajesDisponibles();

        if (nombresPersonajes.isEmpty()) {
          
            JOptionPane.showMessageDialog(null, "No hay personajes disponibles", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
         
        JPanel panelDetalles = new JPanel(new GridLayout(nombresPersonajes.size(), 1));
        
        for (String nombrePersonaje : nombresPersonajes) {
            try {
             
                if (conexion.obtenerConexion() == null || conexion.obtenerConexion().isClosed()) {
                   
                    JOptionPane.showMessageDialog(null, "La conexión está cerrada", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Estadistica estadistica = conexion.obtenerEstadisticasPorNombre(nombrePersonaje);
                List<Habilidad> habilidades = conexion.obtenerHabilidadesPorNombre(nombrePersonaje);

                JLabel etiquetaNombre = new JLabel("Nombre: " + nombrePersonaje);
                JLabel etiquetaEstadisticas = new JLabel("Estadísticas: " + estadistica);
                JLabel etiquetaHabilidades = new JLabel("Habilidades: " + habilidades);

                panelDetalles.add(etiquetaNombre);
                panelDetalles.add(etiquetaEstadisticas);
                panelDetalles.add(etiquetaHabilidades);
                
            } catch (SQLException e) {
               
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar detalles de personajes", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    
        JScrollPane scrollPane = new JScrollPane(panelDetalles);

        add(scrollPane);
        
       } 
    } 