package co.edu.udistrital.controlador;

import co.edu.udistrital.modelo.*;
import co.edu.udistrital.vista.VistaTablero;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Control {
    private Partida partida;
    private Jugador jugador;
    private Maquina maquina;
    private VistaTablero vista;

    public Control(Jugador jugador) {
        this.jugador = jugador;
        this.maquina = new Maquina();
        this.partida = new Partida();
        this.vista = new VistaTablero();

        partida.iniciarPartida(jugador, maquina);

        // Listeners para los tableros
        vista.addListenerTableroJugador(new ListenerTableroJugador());
        vista.addListenerTableroMaquina(new ListenerTableroMaquina());
    }

    // Listener para cuando el jugador ataca el tablero de la máquina
    private class ListenerTableroMaquina implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String[] pos = e.getActionCommand().split(",");
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);
            boolean acierto = partida.movimientos(jugador, x, y);
            vista.marcarAtaqueJugador(x, y, acierto);

            if (acierto) {
                // Verifica si un barco fue destruido
                Barco barco = partida.obtenerBarcoEnPosicionMaquina(x, y);
                if (barco != null && barco.estaHundido()) {
                    javax.swing.JOptionPane.showMessageDialog(null, "¡Barco enemigo destruido!");
                }
            }
            if (!partida.getTableroMaquina().getTablero()[x][y].equals("B")) {
                turnoMaquina();
            }
        }
    }

    // Listener para el tablero del jugador (puedes usarlo para ubicar barcos si lo deseas)
    private class ListenerTableroJugador implements ActionListener {
        private int barcoActual = 0;
        private final int[] tamaniosBarcos = {6, 4, 4, 3, 3, 3, 2, 2, 2, 2}; // Ejemplo

        @Override
        public void actionPerformed(ActionEvent e) {
            if (barcoActual >= tamaniosBarcos.length) {
                // Todos los barcos ubicados
                return;
            }
            String[] pos = e.getActionCommand().split(",");
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);
            // Preguntar orientación
            String[] opciones = {"Horizontal", "Vertical"};
            int orientacion = javax.swing.JOptionPane.showOptionDialog(
                    null, "¿Orientación del barco?", "Ubicar Barco",
                    javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opciones[0]);
            boolean horizontal = orientacion == 0;

            boolean colocado = partida.getTableroJugador().colocarBarco(x, y, tamaniosBarcos[barcoActual], horizontal);
            if (colocado) {
                vista.marcarBarcoJugador(x, y); // Puedes mejorar para marcar todo el barco
                barcoActual++;
                if (barcoActual == tamaniosBarcos.length) {
                    javax.swing.JOptionPane.showMessageDialog(null, "¡Todos los barcos han sido ubicados!");
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "No se puede ubicar el barco aquí.");
            }
        }
    }

    // Lógica para el turno de la máquina
    private void turnoMaquina() {
        int[] mov = maquina.decidirMovimiento(10, 10);
        boolean acierto = partida.getTableroJugador().recibirAtaque(mov[0], mov[1]);
        vista.marcarAtaqueMaquina(mov[0], mov[1], acierto);
        if (acierto) {
            maquina.agregarObjetivos(mov[0], mov[1], 10, 10);
            // Verifica si un barco del jugador fue destruido
            Barco barco = partida.obtenerBarcoEnPosicionJugador(mov[0], mov[1]);
            if (barco != null && barco.estaHundido()) {
                javax.swing.JOptionPane.showMessageDialog(null, "¡Uno de tus barcos ha sido destruido!");
            }
        }
        if (verificarFinDePartida()) {
            // Puedes mostrar mensaje de victoria/derrota aquí
        }
    }

    // Verifica si la partida terminó
    private boolean verificarFinDePartida() {
        if (partida.getTableroMaquina() != null && partida.getTableroJugador() != null) {
            if (partida.getTableroMaquina().getTablero() != null && partida.getTableroJugador().getTablero() != null) {
                if (partida.getTableroMaquina().getTablero().length > 0 && partida.getTableroJugador().getTablero().length > 0) {
                    if (partida.getTableroMaquina().getTablero().length == 10 && partida.getTableroJugador().getTablero().length == 10) {
                        // Verifica si hay barcos restantes
                        boolean jugadorGana = !hayBarcos(partida.getTableroMaquina());
                        boolean maquinaGana = !hayBarcos(partida.getTableroJugador());
                        if (jugadorGana) {
                            partida.victoria(jugador);
                            javax.swing.JOptionPane.showMessageDialog(null, "¡Felicidades! Has ganado la partida.");
                            return true;
                        } else if (maquinaGana) {
                            partida.derrota(jugador);
                            javax.swing.JOptionPane.showMessageDialog(null, "La máquina ha ganado. ¡Suerte para la próxima!");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Verifica si quedan barcos en el tablero
    private boolean hayBarcos(Tablero tablero) {
        String[][] matriz = tablero.getTablero();
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (matriz[i][j].equals("B")) {
                    return true;
                }
            }
        }
        return false;
    }
}
