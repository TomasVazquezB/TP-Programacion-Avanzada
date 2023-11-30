package Interfaz;

import java.awt.EventQueue;

import Logica.Partida;
import Logica.Usuario;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BD.Conexion;
<<<<<<< HEAD
=======
import Logica.Partida;
import Logica.Usuario;
>>>>>>> e8c5045767979f586ae777f8dc93cb1f11f8a3ef

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
<<<<<<< HEAD
	private Usuario usuario;
	private Conexion con;
	
=======

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
>>>>>>> e8c5045767979f586ae777f8dc93cb1f11f8a3ef

	/**
	 * Create the frame.
	 */
	public EquipoyBatalla(Usuario usuario) {
<<<<<<< HEAD
		this.usuario = usuario;
=======
>>>>>>> e8c5045767979f586ae777f8dc93cb1f11f8a3ef
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
	            Conexion conexion = new Conexion();
	            Partida nuevaPartida = new Partida(usuario, conexion);
	            nuevaPartida.jugar();
	            }
	        });
	        
	       
		JLabel menu = new JLabel("");
		menu.setBounds(-362, -228, 1393, 875);
		menu.setIcon(new ImageIcon("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\menu de inicio.jpg"));
		menu.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(menu);
	}
	
	
	
	private void iniciarBatalla() {
		Conexion conexion = new Conexion();  // Crear una instancia de Conexion
		Partida nuevaPartida = new Partida(usuario, conexion);
		nuevaPartida.jugar();
    }
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Ejemplo de uso
                Usuario ejemploUsuario = new Usuario("EjemploUsuario");
                EquipoyBatalla frame = new EquipoyBatalla(ejemploUsuario);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}
}
