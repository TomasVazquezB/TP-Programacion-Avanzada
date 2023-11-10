package Interfaz;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Logica.Conexion;
import Logica.Usuario;
import Logica.Validador;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class PantallaInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField nombre;
	private JTextField contrasena;
	private JTextField registronombre;
	private JTextField registrocontrasena;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaInicio frame = new PantallaInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PantallaInicio() {
		setEnabled(false);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\icono.jpg"));
		setTitle("Project Versus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 830, 547);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(90, 443, 121, 58);
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnNewButton = new JButton("Iniciar Sesion");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 Conexion conexion = new Conexion();
			        Connection connection = conexion.conectar();
			        Validador sistema = new Validador(connection);
			        nombre.getText();
			        contrasena.getText();
				 Usuario usuarioLogueado = sistema.ValidarIngreso(nombre.getText(), contrasena.getText());
				 if (usuarioLogueado != null) {
                     MenuPrincipal pantalla = new MenuPrincipal();
                     pantalla.run();
                 } else {
                     JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Credenciales incorrectas.","Error", JOptionPane.ERROR_MESSAGE);
                 }
				
			}
		});
		
		JLabel titulo = new JLabel("Bienevidos al menu, que desea realizar?");
		titulo.setFont(new Font("Tahoma", Font.PLAIN, 28));
		titulo.setBounds(161, 11, 505, 119);
		contentPane.add(titulo);
		
		JLabel ingresocontrasena = new JLabel("Contraseña");
		ingresocontrasena.setFont(new Font("Tahoma", Font.PLAIN, 24));
		ingresocontrasena.setBounds(611, 331, 191, 44);
		contentPane.add(ingresocontrasena);
		
		JLabel ingresonombre = new JLabel("Usuario");
		ingresonombre.setFont(new Font("Tahoma", Font.PLAIN, 24));
		ingresonombre.setBounds(611, 221, 191, 44);
		contentPane.add(ingresonombre);
		
		JLabel registrotextocontrasena = new JLabel("Contraseña");
		registrotextocontrasena.setFont(new Font("Tahoma", Font.PLAIN, 24));
		registrotextocontrasena.setBounds(51, 331, 191, 44);
		contentPane.add(registrotextocontrasena);
		
		JLabel textoregistronomre = new JLabel("Usuario");
		textoregistronomre.setFont(new Font("Tahoma", Font.PLAIN, 24));
		textoregistronomre.setBounds(51, 221, 191, 44);
		contentPane.add(textoregistronomre);
		
		registrocontrasena = new JTextField();
		registrocontrasena.setColumns(10);
		registrocontrasena.setBounds(51, 374, 179, 58);
		contentPane.add(registrocontrasena);
		
		registronombre = new JTextField();
		registronombre.setColumns(10);
		registronombre.setBounds(51, 262, 179, 58);
		contentPane.add(registronombre);
		
		contrasena = new JTextField();
		contrasena.setBounds(611, 374, 179, 58);
		contentPane.add(contrasena);
		contrasena.setColumns(10);
		
		nombre = new JTextField();
		nombre.setBounds(611, 262, 179, 58);
		contentPane.add(nombre);
		nombre.setColumns(10);
		btnNewButton.setBounds(648, 443, 121, 58);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnNewButton);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(314, 385, 179, 80);
		btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnSalir);
		contentPane.add(btnRegistrar);
		
		JLabel menudeiniciodesesion = new JLabel("");
		menudeiniciodesesion.setIcon(new ImageIcon("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\menu de inicio.jpg"));
		menudeiniciodesesion.setBounds(-195, 0, 1095, 546);
		menudeiniciodesesion.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(menudeiniciodesesion);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(51, 213, 160, 38);
		contentPane.add(textPane);
	}
}
