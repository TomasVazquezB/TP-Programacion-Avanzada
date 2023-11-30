package Interfaz;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import BD.*;
import Logica.*;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

public class PantallaDeInicio extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField registrocontraseña;
	private JTextField registronombre;
	private JTextField IngresoContraseña;
	private JTextField IngresoNombre;

	/**
	 * Launch the application.
	 */
	
			public void run() {
				try {
					PantallaDeInicio frame = new PantallaDeInicio();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	/**
	 * Create the frame.
	 */
	public PantallaDeInicio() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\icono.jpg"));
		setTitle("Project Versus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 831, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel titulo = new JLabel("Bienvenido a Project Versus");
		titulo.setFont(new Font("Tahoma", Font.PLAIN, 28));
		titulo.setBounds(220, 11, 505, 119);
		contentPane.add(titulo);
		
		registrocontraseña = new JTextField();
		registrocontraseña.setBounds(34, 312, 179, 51);
		registrocontraseña.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(registrocontraseña);
		registrocontraseña.setColumns(10);
		 Conexion conexion = new Conexion();
	        Connection connection = conexion.conectar();
		JButton Ingresar = new JButton("Ingresar");
		Ingresar.setBounds(638, 394, 121, 58);
		Ingresar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Ingresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				 Conexion conexion = new Conexion();
			        Connection connection = conexion.conectar();
			        Validador sistema = new Validador(connection);
			        Usuario usuario = sistema.ValidarIngreso(IngresoNombre.getText(), IngresoContraseña.getText());
			        if (usuario!= null) {
               MenuPrincipal pantalla = new MenuPrincipal(usuario);
               pantalla.run(usuario);
               dispose();
           } else {
               JOptionPane.showMessageDialog(null, "Inicio de sesión fallido. Credenciales incorrectas.","Error", JOptionPane.ERROR_MESSAGE);
           }
				
			}
		});
		contentPane.add(Ingresar);
		
		JLabel Contraseña = new JLabel("Contraseña:");
		Contraseña.setBounds(34, 281, 134, 20);
		Contraseña.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(Contraseña);
		
		registronombre = new JTextField();
		registronombre.setBounds(34, 197, 179, 51);
		registronombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		registronombre.setColumns(10);
		contentPane.add(registronombre);
		
		JLabel Nombre = new JLabel("Nombre:");
		Nombre.setBounds(34, 166, 134, 20);
		Nombre.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(Nombre);
		
		JButton Salir = new JButton("Salir");
		Salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
		 JOptionPane.showMessageDialog(null, "Gracias por jugar. ¡Hasta Luego!", "Hasta Luego",JOptionPane.QUESTION_MESSAGE);
				System.exit(0);
			}
		});
		Salir.setBounds(329, 268, 169, 92);
		Salir.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(Salir);
		
		JButton Registrarse = new JButton("Registro");
		Registrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        Validador sistema = new Validador(connection);
		
		         if (registronombre == null || registronombre.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "El nombre de usuario no puede estar vacío. Intente nuevamente.","Error", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        if (registrocontraseña == null || registrocontraseña.getText().isEmpty()) {
		            JOptionPane.showMessageDialog(null, "La contraseña no puede estar vacía. Intente nuevamente.", "Error",JOptionPane.ERROR_MESSAGE);
		            return;
		      
				}else {
					
					Usuario nuevo = new Usuario(registronombre.getText(),registrocontraseña.getText());
					   if (sistema.registrarUsuario(nuevo)) {
				            JOptionPane.showMessageDialog(null, "Registro exitoso", "Registro finalizado", JOptionPane.INFORMATION_MESSAGE);
				            registronombre.setText("");
				            registrocontraseña.setText("");
				        } else {
				            JOptionPane.showMessageDialog(null, "El nombre de usuario ya está en uso. Intente con otro nombre.", "Error",JOptionPane.ERROR_MESSAGE);
				        }
				    }
				}	
			
		});
		Registrarse.setBounds(56, 394, 121, 58);
		Registrarse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(Registrarse);
		
		IngresoContraseña = new JTextField();
		IngresoContraseña.setBounds(607, 312, 179, 51);
		IngresoContraseña.setFont(new Font("Tahoma", Font.PLAIN, 12));
		IngresoContraseña.setColumns(10);
		contentPane.add(IngresoContraseña);
		
		JLabel Contraseña_1 = new JLabel("Contraseña:");
		Contraseña_1.setBounds(607, 281, 134, 20);
		Contraseña_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(Contraseña_1);
		
		IngresoNombre = new JTextField();
		IngresoNombre.setBounds(607, 197, 179, 51);
		IngresoNombre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		IngresoNombre.setColumns(10);
		contentPane.add(IngresoNombre);
		
		JLabel Nombre_1 = new JLabel("Nombre:");
		Nombre_1.setBounds(607, 166, 134, 20);
		Nombre_1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		contentPane.add(Nombre_1);
		
		JLabel menudeiniciodesesion = new JLabel("");
		menudeiniciodesesion.setIcon(new ImageIcon("C:\\Users\\Alumno\\Documents\\GitHub\\TP-Programacion-Avanzada\\Proyect_Versus\\src\\img\\menu de inicio.jpg"));
		menudeiniciodesesion.setBounds(-185, 0, 1000, 463);
		contentPane.add(menudeiniciodesesion);
	}
}