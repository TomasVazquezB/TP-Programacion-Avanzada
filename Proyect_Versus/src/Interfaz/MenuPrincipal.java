package Interfaz;
import Logica.Partida;

//Resto de tu código

import java.awt.EventQueue;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Logica.Usuario;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import BD.Conexion;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	/**
	 * Launch the application.
	 */

			public void run(Usuario usuarioquepaso) {
				try {
					MenuPrincipal frame = new MenuPrincipal(usuarioquepaso);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		

	/**
	 * Create the frame.
	 */
	public MenuPrincipal(Usuario usuario) {
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
		
		JButton botondearmarequipo = new JButton("Armar Equipo");
        botondearmarequipo.setBounds(10, 217, 121, 58);
        botondearmarequipo.setFont(new Font("Tahoma", Font.PLAIN, 12));
        botondearmarequipo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	usuario.armarEquipo(usuario);
            }
        });
        contentPane.add(botondearmarequipo);
		
        JButton botondecrearpartida = new JButton("Crear Partida");
        botondecrearpartida.setBounds(190, 217, 121, 58);
        botondecrearpartida.setFont(new Font("Tahoma", Font.PLAIN, 12));
        contentPane.add(botondecrearpartida);

        botondecrearpartida.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Conexion conexion = new Conexion(); 
                Partida nuevaPartida = new Partida(usuario, conexion);
                nuevaPartida.jugar();
            }
        });

		
		JButton botondesalir = new JButton("Salir");
		botondesalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
		JOptionPane.showMessageDialog(null, "Sesión cerrada. ¡Hasta luego!", "Sesion Cerrada",JOptionPane.QUESTION_MESSAGE);
				System.exit(0);
			}
		});
		
		botondesalir.setBounds(345, 217, 121, 58);
		botondesalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(botondesalir);
		
		
		JLabel menu = new JLabel("");
		menu.setBounds(-347, -226, 1200, 875);
		menu.setIcon(new ImageIcon("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\menu de inicio.jpg"));
		contentPane.add(menu);
	}
	
	
	

}
