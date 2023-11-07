package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

public class Project_Versus extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Project_Versus frame = new Project_Versus();
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
	public Project_Versus() {
		setEnabled(false);
		setFont(new Font("Dialog", Font.PLAIN, 12));
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alumno\\Desktop\\icono.jpg"));
		setTitle("Project Versus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 403);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Bienvenido al menu, que desea realizar?");
		lblNewLabel.setBounds(129, 32, 325, 50);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Iniciar Sesion");
		btnNewButton.setBounds(31, 186, 140, 67);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnNewButton);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(213, 186, 140, 67);
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnRegistrar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(394, 186, 140, 67);
		btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnSalir);
	}
}
