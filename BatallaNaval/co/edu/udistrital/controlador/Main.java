package co.edu.udistrital.controlador;

import co.edu.udistrital.vista.InicioJuego;

public class Main {
    public static void main(String[] args) {
        // Inicia la ventana de inicio de sesión/registro
        javax.swing.SwingUtilities.invokeLater(() -> new InicioJuego());
    }
}
