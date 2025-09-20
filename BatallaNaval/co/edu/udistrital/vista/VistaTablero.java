package co.edu.udistrital.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaTablero extends JFrame {
    private JButton[][] tableroJugador;
    private JButton[][] tableroMaquina;
    private JPanel panelJugador;
    private JPanel panelMaquina;

    public VistaTablero() {
        setTitle("Batalla Naval");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2, 20, 0));

        panelJugador = new JPanel(new GridLayout(10, 10));
        panelMaquina = new JPanel(new GridLayout(10, 10));
        tableroJugador = new JButton[10][10];
        tableroMaquina = new JButton[10][10];

        inicializarTablero(panelJugador, tableroJugador, true);
        inicializarTablero(panelMaquina, tableroMaquina, false);

        add(crearPanelConTitulo(panelJugador, "Tu Tablero"));
        add(crearPanelConTitulo(panelMaquina, "Tablero Máquina"));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarTablero(JPanel panel, JButton[][] tablero, boolean esJugador) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(40, 40));
                btn.setBackground(Color.CYAN);
                btn.setActionCommand(i + "," + j);
                tablero[i][j] = btn;
                panel.add(btn);
            }
        }
    }

    private JPanel crearPanelConTitulo(JPanel panel, String titulo) {
        JPanel contenedor = new JPanel(new BorderLayout());
        contenedor.add(new JLabel(titulo, SwingConstants.CENTER), BorderLayout.NORTH);
        contenedor.add(panel, BorderLayout.CENTER);
        return contenedor;
    }

    // Métodos para que el controlador pueda añadir listeners
    public void addListenerTableroJugador(ActionListener listener) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                tableroJugador[i][j].addActionListener(listener);
    }

    public void addListenerTableroMaquina(ActionListener listener) {
        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                tableroMaquina[i][j].addActionListener(listener);
    }

    // Métodos para actualizar la vista desde el controlador
    public void marcarBarcoJugador(int x, int y) {
        tableroJugador[x][y].setBackground(Color.GRAY);
    }

    public void marcarAtaqueJugador(int x, int y, boolean acierto) {
        tableroMaquina[x][y].setBackground(acierto ? Color.RED : Color.WHITE);
    }

    public void marcarAtaqueMaquina(int x, int y, boolean acierto) {
        tableroJugador[x][y].setBackground(acierto ? Color.RED : Color.WHITE);
    }
}