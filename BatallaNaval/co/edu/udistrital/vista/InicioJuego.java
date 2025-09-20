package co.edu.udistrital.vista;

import co.edu.udistrital.modelo.Jugador;
import co.edu.udistrital.modelo.ServicioJugador;

import javax.swing.*;
import java.awt.*;

public class InicioJuego {

    private ServicioJugador servicioJugador;

    public InicioJuego() {
        servicioJugador = new ServicioJugador();

        JFrame frame = new JFrame("Batalla Naval UD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Batalla Naval UD", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton crearUsuarioBtn = new JButton("Crear Usuario");
        JButton iniciarSesionBtn = new JButton("Iniciar Sesión");

        // Acción para "Crear Usuario"
        crearUsuarioBtn.addActionListener(e -> {
            JTextField usuarioField = new JTextField();
            JPasswordField contrasenaField = new JPasswordField();
            Object[] fields = {
                "Usuario:", usuarioField,
                "Contraseña:", contrasenaField
            };
            int option = JOptionPane.showConfirmDialog(frame, fields, "Crear Usuario", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String usuario = usuarioField.getText();
                String contrasena = new String(contrasenaField.getPassword());
                Jugador nuevoJugador = new Jugador(usuario, contrasena);
                boolean registrado = servicioJugador.registrarJugador(nuevoJugador);
                if (registrado) {
                    JOptionPane.showMessageDialog(frame, "Usuario registrado exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(frame, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Acción para "Iniciar Sesión"
        iniciarSesionBtn.addActionListener(e -> {
            JTextField usuarioField = new JTextField();
            JPasswordField contrasenaField = new JPasswordField();
            Object[] fields = {
                "Usuario:", usuarioField,
                "Contraseña:", contrasenaField
            };
            int option = JOptionPane.showConfirmDialog(frame, fields, "Iniciar Sesión", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String usuario = usuarioField.getText();
                String contrasena = new String(contrasenaField.getPassword());
                boolean login = servicioJugador.iniciarSesion(usuario, contrasena);
                if (login) {
                    JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso.");
                    frame.dispose(); // Cierra la ventana de login
                    new co.edu.udistrital.controlador.Control(new Jugador(usuario, contrasena));
                } else {
                    JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonPanel.add(crearUsuarioBtn);
        buttonPanel.add(iniciarSesionBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }
}
