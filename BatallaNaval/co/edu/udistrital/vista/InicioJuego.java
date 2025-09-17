package co.edu.udistrital.vista;

import co.edu.udistrital.modelo.Jugador;
import javax.swing.*;
import java.awt.*;


public class InicioJuego {

    Jugador jugador;
    
    public InicioJuego() {

        JFrame frame = new JFrame("Batalla Naval UD");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);

        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // label de bienvenida
        JLabel welcomeLabel = new JLabel("Batalla Naval UD", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Panel para botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton crearUsuarioBtn = new JButton("Crear Usuario");
        JButton iniciarSesionBtn = new JButton("Iniciar Sesión");

        // Acción para "Crear Usuario"
        crearUsuarioBtn.addActionListener(e -> {
            jugador.registrarJugador();
        });

        // Acción para "Iniciar Sesión"
        iniciarSesionBtn.addActionListener(e -> {
            jugador.iniciarSesion();
        });

        buttonPanel.add(crearUsuarioBtn);
        buttonPanel.add(iniciarSesionBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.setContentPane(mainPanel);
        frame.setVisible(true);
    }

}
