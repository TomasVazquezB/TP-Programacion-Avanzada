package Interfaz;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		

	/**
	 * Create the frame.
	 */
	public MenuPrincipal() {
		setTitle("Project Versus");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\icono.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 644, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton botondearmarequipo = new JButton("Armar Equipo");
		botondearmarequipo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botondearmarequipo.setBounds(57, 314, 121, 58);
		contentPane.add(botondearmarequipo);
		
		JButton botondecrearpartida = new JButton("Crear Partida");
		botondecrearpartida.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botondecrearpartida.setBounds(257, 314, 121, 58);
		contentPane.add(botondecrearpartida);
		
		JButton botondesalir = new JButton("Salir");
		botondesalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
		botondesalir.setBounds(481, 314, 121, 58);
		contentPane.add(botondesalir);
		
		JLabel menu = new JLabel("");
		menu.setBounds(-284, -113, 1200, 875);
		menu.setIcon(new ImageIcon("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\menu de inicio.jpg"));
		contentPane.add(menu);
	}
}
