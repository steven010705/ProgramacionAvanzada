package co.edu.udistrital.controlador;

import co.edu.udistrital.vista.InicioJuego;

/**
* Clase Main
* @author Steven
* @version 2.0
*/
public class Main {
    /** 
    * Inicia la ventana de inicio de sesión/registro
    */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new InicioJuego());
    }
}
