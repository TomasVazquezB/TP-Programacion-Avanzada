package Interfaz;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import BD.Conexion;
import Logica.*;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class EquipoyBatalla extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usuario;

	/**
	 * Launch the application.
	 */
			public void run(Usuario usuario) {
				try {
					EquipoyBatalla frame = new EquipoyBatalla(usuario);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

	/**
	 * Create the frame.
	 */
	public EquipoyBatalla(Usuario usuario) {

		this.usuario = usuario;
		setTitle("Project Versus");
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\icono.jpg"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 480, 357);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton botontorneo = new JButton("Torneo");
		botontorneo.setBounds(317, 230, 121, 58);
		contentPane.add(botontorneo);
		
		JButton botonbatalla = new JButton("Batalla");
		botonbatalla.setBounds(28, 230, 121, 58);
		botonbatalla.addActionListener(e -> iniciarBatalla());
		contentPane.add(botonbatalla);
		
		botonbatalla.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	
	            }
	        });
	        
	       
		JLabel menu = new JLabel("");
		menu.setBounds(-362, -228, 1393, 875);
		menu.setIcon(new ImageIcon("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\menu de inicio.jpg"));
		menu.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(menu);
	}
	
	private void iniciarBatalla() {
		Conexion conexion = new Conexion(); 
		Partida nuevaPartida = new Partida(usuario, conexion);
		nuevaPartida.jugar();
    }
	
	/**
	 * Launch the application.
	 */
}
