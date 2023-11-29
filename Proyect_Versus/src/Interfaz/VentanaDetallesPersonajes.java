
package Interfaz;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import Logica.Estadistica;
import Logica.Habilidad;
import BD.Conexion;

public class VentanaDetallesPersonajes extends JFrame {

    private Conexion conexion; // Agrega una variable de conexión como campo de clase

    public VentanaDetallesPersonajes(Conexion conexion) {
        this.conexion = conexion;
        setTitle("Detalles de Personajes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2));

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane);

        cargarDetallesPersonajes(); // Llamada al método para cargar detalles de personajes
    }

    private void cargarDetallesPersonajes() {
        List<String> nombresPersonajes = conexion.obtenerNombresPersonajesDisponibles();

        // Verificar si hay personajes disponibles
        if (nombresPersonajes.isEmpty()) {
            // Mostrar mensaje o manejar de acuerdo a tus necesidades
            JOptionPane.showMessageDialog(null, "No hay personajes disponibles", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
         // Crear un panel para organizar los detalles de los personajes
        JPanel panelDetalles = new JPanel(new GridLayout(nombresPersonajes.size(), 1));
        
        // Iterar sobre los nombres de los personajes
        for (String nombrePersonaje : nombresPersonajes) {
            try {
                // Asegurarse de que la conexión esté abierta
                if (conexion.obtenerConexion() == null || conexion.obtenerConexion().isClosed()) {
                    // Manejar la conexión cerrada de acuerdo a tus necesidades
                    JOptionPane.showMessageDialog(null, "La conexión está cerrada", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Obtener estadísticas y habilidades del personaje
                Estadistica estadistica = conexion.obtenerEstadisticasPorNombre(nombrePersonaje);
                List<Habilidad> habilidades = conexion.obtenerHabilidadesPorNombre(nombrePersonaje);

             // Crear etiquetas para mostrar la información del personaje
                JLabel etiquetaNombre = new JLabel("Nombre: " + nombrePersonaje);
                JLabel etiquetaEstadisticas = new JLabel("Estadísticas: " + estadistica);
                JLabel etiquetaHabilidades = new JLabel("Habilidades: " + habilidades);

                // Agregar las etiquetas al panel
                panelDetalles.add(etiquetaNombre);
                panelDetalles.add(etiquetaEstadisticas);
                panelDetalles.add(etiquetaHabilidades);
                // Ahora puedes usar la información para actualizar la interfaz gráfica
                // con los detalles del personaje y sus habilidades.
            } catch (SQLException e) {
                // Manejar la excepción de SQL de acuerdo a tus necesidades
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al cargar detalles de personajes", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
     // Crear un JScrollPane y agregar el panel de detalles al scroll
        JScrollPane scrollPane = new JScrollPane(panelDetalles);

        // Agregar el JScrollPane a la ventana
        add(scrollPane);
        
       
    }
        
    }
    
    
