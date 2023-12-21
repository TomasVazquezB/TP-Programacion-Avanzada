package Interfaz;

import Logica.*;
import BD.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPrincipal extends JFrame {

    private static final long serialVersionUID = -7819908745778904368L;
	private JPanel contentPane;
    private Conexion con; 
    private Usuario usuario; 
    
    public void run(Usuario usuario) {
        try {
            MenuPrincipal frame = new MenuPrincipal(usuario);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public MenuPrincipal(Usuario usuario) {
        this.usuario = usuario; 
    	
        setTitle("Project Versus");
        setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\icono.jpg"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 492, 325);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel textomenu = new JLabel("¿Qué desea realizar?");
        textomenu.setFont(new Font("Tahoma", Font.PLAIN, 24));
        textomenu.setBounds(141, -20, 226, 123);
        contentPane.add(textomenu);

        JButton botondearmarequipo = new JButton("Armar y Seleccionar Equipo");
        botondearmarequipo.setBounds(10, 217, 191, 58);
        botondearmarequipo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        botondearmarequipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                usuario.seleccionarOArmarEquipo(usuario); 
            }
        });
        contentPane.add(botondearmarequipo);

        JButton botonbatalla = new JButton("Batalla");
        botonbatalla.setBounds(230, 217, 101, 58);
        botonbatalla.setFont(new Font("Tahoma", Font.PLAIN, 12));
        contentPane.add(botonbatalla);

        botonbatalla.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion(); 
                Partida nuevaPartida = new Partida(usuario, conexion);

                new Thread(new Runnable() {
                    public void run() {
                        nuevaPartida.iniciarPartida();
                    }
                }).start();
            }
        });

        con = new Conexion();

        JButton botonDetalles = new JButton("Detalles de Personajes");
        botonDetalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mostrarDetallesPersonajes(con);
            }
        });
        
        botonDetalles.setBounds(10, 150, 121, 58);
        botonDetalles.setFont(new Font("Tahoma", Font.PLAIN,12));
        contentPane.add(botonDetalles);

        JButton botondesalir = new JButton("Salir");
        botondesalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!", "Sesion Cerrada", JOptionPane.QUESTION_MESSAGE);
                System.exit(0);
            }
        });

        botondesalir.setBounds(355, 217, 101, 58);
        botondesalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
        contentPane.add(botondesalir);

        JLabel menu = new JLabel("");
        menu.setBounds(-347, -226, 1200, 875);
        menu.setIcon(new ImageIcon("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\menu de inicio.jpg"));
        contentPane.add(menu);
    }

    private void mostrarDetallesPersonajes(BD.Conexion conexion) {
        VentanaDetallesPersonajes detallesPersonajes = new VentanaDetallesPersonajes(conexion);
        detallesPersonajes.setVisible(true);
    }
}
