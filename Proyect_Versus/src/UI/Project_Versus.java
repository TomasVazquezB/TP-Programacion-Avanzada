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
import javax.swing.ImageIcon;
import javax.swing.JTextField;

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
		setBounds(100, 100, 836, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(319, 344, 179, 80);
		btnRegistrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setBounds(610, 344, 179, 80);
		btnSalir.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnSalir);
		contentPane.add(btnRegistrar);
		
		JButton btnNewButton = new JButton("Iniciar Sesion");
		btnNewButton.setBounds(50, 344, 179, 80);
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("Bienvenido al menu, que desea realizar?");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Alumno\\Desktop\\menu de inicio.jpg"));
		lblNewLabel.setBounds(-174, -14, 1095, 546);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		contentPane.add(lblNewLabel);
	}
}
