package co.edu.udistrital.controlador;

import co.edu.udistrital.modelo.*;
import co.edu.udistrital.vista.VistaTablero;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
* Clase Control
* @author Steven
* @version 4.0
*/
public class Control {
    private Partida partida;
    private Jugador jugador;
    private Maquina maquina;
    private VistaTablero vista;
    private Cronometro cronometro; // Instancia del cronómetro

    // Para la colocación de barcos del jugador
    private int barcoActualIndex = 0;
    private List<Barco> barcosAColocarJugador; // Lista de barcos del jugador para colocar
    private List<Barco> barcosAColocarMaquina; // Lista de barcos de la máquina para colocar

    // Hilo para la lógica del juego (opcional, pero bueno para separar)
    private Thread juegoThread;

    /**
    * Instancia los objetos e llama los métodos necesarios para el funcionamiento del juego
    * @param jugador usuario que juega
    */
    public Control(Jugador jugador) {
        this.jugador = jugador;
        this.maquina = new Maquina();
        this.partida = new Partida();
        this.vista = new VistaTablero();
        this.cronometro = new Cronometro(vista.getEtiquetaTiempo()); // Pasa el JLabel del tiempo

        // Inicializar los barcos que el jugador y la máquina tienen para colocar
        this.barcosAColocarJugador = jugador.getBarcos(); // Los barcos ya están creados en Jugador
        this.barcosAColocarMaquina = crearBarcosFijosParaMaquina(); // Método auxiliar

        partida.iniciarPartida(jugador, maquina); // Esto inicializa los tableros, pero no ubica los barcos

        // Asignar las listas de barcos a la partida para que pueda usarlas
        partida.getBarcosJugador().addAll(barcosAColocarJugador);
        partida.getBarcosMaquina().addAll(barcosAColocarMaquina);

        // Ubicar los barcos de la máquina aleatoriamente al inicio
        ubicarBarcosMaquinaAleatoriamente();

        // Listeners para los tableros
        vista.addListenerTableroJugador(new ListenerTableroJugador());
        vista.addListenerTableroMaquina(new ListenerTableroMaquina());

        javax.swing.JOptionPane.showMessageDialog(null, "¡Es hora de ubicar tus barcos! Selecciona una casilla en tu tablero.");

        // Iniciar el cronómetro cuando el juego comienza (después de la ubicación de barcos)
        // Lo iniciaremos después de que el jugador ubique todos sus barcos.
    }

    // Método auxiliar para crear barcos para la máquina
    private List<Barco> crearBarcosFijosParaMaquina() {
        List<Barco> barcos = new ArrayList<>();
        barcos.add(new Barco("Portaaviones", 6, true));
        barcos.add(new Barco("Acorazado", 4, false));
        barcos.add(new Barco("Acorazado", 4, false));
        barcos.add(new Barco("Submarino", 3, true));
        barcos.add(new Barco("Submarino", 3, true));
        barcos.add(new Barco("Submarino", 3, true));
        barcos.add(new Barco("Fragata", 2, false));
        barcos.add(new Barco("Fragata", 2, false));
        barcos.add(new Barco("Fragata", 2, false));
        barcos.add(new Barco("Fragata", 2, false));
        return barcos;
    }

    /**
     * Ubica los barcos de la máquina aleatoriamente en su tablero.
     * Esto es un ejemplo básico y puede necesitar mejoras para evitar colisiones o ubicaciones inválidas.
     */
    private void ubicarBarcosMaquinaAleatoriamente() {
        Random random = new Random();
        for (Barco barco : barcosAColocarMaquina) {
            boolean colocado = false;
            while (!colocado) {
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                boolean horizontal = random.nextBoolean();
                List<int[]> posicionesBarco = new ArrayList<>();

                if (partida.getTableroMaquina().colocarBarco(x, y, barco.getTamanio(), horizontal)) {
                    // Si se colocó, guardar las posiciones en el barco
                    for (int i = 0; i < barco.getTamanio(); i++) {
                        if (horizontal) {
                            posicionesBarco.add(new int[]{x, y + i});
                        } else {
                            posicionesBarco.add(new int[]{x + i, y});
                        }
                    }
                    barco.setPosiciones(posicionesBarco);
                    colocado = true;
                }
            }
        }
    }

    /**
    * Listener para cuando el jugador ataca el tablero de la máquina
    * @param e evento que ocurre tras la acción
    */
    private class ListenerTableroMaquina implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (barcoActualIndex < barcosAColocarJugador.size()) {
                javax.swing.JOptionPane.showMessageDialog(null, "Primero debes ubicar todos tus barcos.");
                return;
            }
            if (partida.isJuegoTerminado()) {
                return; // No permitir movimientos si el juego ha terminado
            }

            String[] pos = e.getActionCommand().split(",");
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);

            // Verificar si la casilla ya fue atacada
            if (partida.getTableroMaquina().getTablero()[x][y].equals("X") || partida.getTableroMaquina().getTablero()[x][y].equals("O")) {
                javax.swing.JOptionPane.showMessageDialog(null, "Ya atacaste esta casilla. Elige otra.");
                return;
            }

            // Ejecutar el movimiento del jugador en un hilo separado para no bloquear la GUI
            // Esto es más relevante si el movimiento del jugador implicara cálculos complejos o esperas
            // Para un simple ataque, no es estrictamente necesario, pero demuestra el concepto.
            juegoThread = new Thread(() -> {
                boolean acierto = partida.movimientos(jugador, x, y);
                SwingUtilities.invokeLater(() -> vista.marcarAtaqueJugador(x, y, acierto)); // Actualizar GUI en EDT

                if (acierto) {
                    // Reducir vida del barco impactado
                    Barco barcoImpactado = partida.obtenerBarcoEnPosicionMaquina(x, y);
                    if (barcoImpactado != null) {
                        barcoImpactado.recibirAtaque();
                        if (barcoImpactado.estaHundido()) {
                            SwingUtilities.invokeLater(() ->
                                javax.swing.JOptionPane.showMessageDialog(null, "¡Barco enemigo '" + barcoImpactado.getTipo() + "' destruido!")
                            );
                        }
                    }
                }

                // Después del movimiento del jugador, verificar si la partida ha terminado
                if (verificarFinDePartida()) {
                    cronometro.detener(); // Detener el cronómetro al finalizar el juego
                    return; // Si el jugador ganó, no hay turno de la máquina
                }

                // Si no ha terminado, es el turno de la máquina
                turnoMaquina();
                verificarFinDePartida(); // Verificar de nuevo después del turno de la máquina
            });
            juegoThread.start();
        }
    }

    /**
    * Clase interna Listener para el tablero del jugador
    */
    private class ListenerTableroJugador implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (barcoActualIndex >= barcosAColocarJugador.size()) {
                javax.swing.JOptionPane.showMessageDialog(null, "¡Todos tus barcos ya han sido ubicados! Ahora puedes atacar el tablero de la máquina.");
                return;
            }

            String[] pos = e.getActionCommand().split(",");
            int x = Integer.parseInt(pos[0]);
            int y = Integer.parseInt(pos[1]);

            Barco barcoActual = barcosAColocarJugador.get(barcoActualIndex);

            String[] opciones = {"Horizontal", "Vertical"};
            int orientacion = javax.swing.JOptionPane.showOptionDialog(
                    null, "¿Orientación del barco '" + barcoActual.getTipo() + "' (Tamaño: " + barcoActual.getTamanio() + ")?", "Ubicar Barco",
                    javax.swing.JOptionPane.DEFAULT_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE,
                    null, opciones, opciones[0]);
            boolean horizontal = orientacion == 0;

            List<int[]> posicionesBarco = new ArrayList<>();
            boolean colocado = partida.getTableroJugador().colocarBarco(x, y, barcoActual.getTamanio(), horizontal);

            if (colocado) {
                // Marcar todas las celdas del barco en la vista
                for (int i = 0; i < barcoActual.getTamanio(); i++) {
                    if (horizontal) {
                        vista.marcarBarcoJugador(x, y + i);
                        posicionesBarco.add(new int[]{x, y + i});
                    } else {
                        vista.marcarBarcoJugador(x + i, y);
                        posicionesBarco.add(new int[]{x + i, y});
                    }
                }
                barcoActual.setPosiciones(posicionesBarco); // Guardar las posiciones en el objeto Barco
                barcoActualIndex++;

                if (barcoActualIndex == barcosAColocarJugador.size()) {
                    javax.swing.JOptionPane.showMessageDialog(null, "¡Todos tus barcos han sido ubicados! ¡Empieza la batalla!");
                    cronometro.iniciar(); // Iniciar el cronómetro aquí, cuando el juego realmente comienza
                } else {
                    javax.swing.JOptionPane.showMessageDialog(null, "Ubica el siguiente barco: " + barcosAColocarJugador.get(barcoActualIndex).getTipo() + " (Tamaño: " + barcosAColocarJugador.get(barcoActualIndex).getTamanio() + ")");
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "No se puede ubicar el barco aquí. Intenta otra posición u orientación.");
            }
        }
    }

    /**
    * Lógica para el turno de la máquina
    * Se ejecuta en el hilo del juego.
    */
    private void turnoMaquina() {
        if (partida.isJuegoTerminado()) {
            return; // No permitir movimientos si el juego ha terminado
        }

        // Simular un pequeño retraso para que el turno de la máquina no sea instantáneo
        try {
            Thread.sleep(500); // Espera 0.5 segundos
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Turno de la máquina interrumpido.");
            return;
        }

        int[] mov = maquina.decidirMovimiento(10, 10);
        boolean acierto = partida.getTableroJugador().recibirAtaque(mov[0], mov[1]);
        SwingUtilities.invokeLater(() -> vista.marcarAtaqueMaquina(mov[0], mov[1], acierto)); // Actualizar GUI en EDT

        if (acierto) {
            maquina.agregarObjetivos(mov[0], mov[1], 10, 10);
            // Verifica si un barco del jugador fue destruido
            Barco barcoImpactado = partida.obtenerBarcoEnPosicionJugador(mov[0], mov[1]);
            if (barcoImpactado != null) {
                barcoImpactado.recibirAtaque();
                if (barcoImpactado.estaHundido()) {
                    SwingUtilities.invokeLater(() ->
                        javax.swing.JOptionPane.showMessageDialog(null, "¡Uno de tus barcos '" + barcoImpactado.getTipo() + "' ha sido destruido!")
                    );
                }
            }
        }
    }

    /**
    * Verifica si la partida terminó
    * @return booleano que indica si la partida ha terminado
    */
    private boolean verificarFinDePartida() {
        if (partida.isJuegoTerminado()) {
            return true; // Ya se ha determinado el fin de la partida
        }

        boolean maquinaDerrotada = partida.verificarDerrota(partida.getTableroMaquina());
        boolean jugadorDerrotado = partida.verificarDerrota(partida.getTableroJugador());

        if (maquinaDerrotada) {
            partida.victoria(jugador);
            partida.setJuegoTerminado(true);
            SwingUtilities.invokeLater(() -> { // Asegurar que el JOptionPane se muestre en el EDT
                javax.swing.JOptionPane.showMessageDialog(null, "¡Felicidades! Has ganado la partida. Todos los barcos de la máquina han sido hundidos.");
            });
            cronometro.detener(); // Detener el cronómetro
            return true;
        } else if (jugadorDerrotado) {
            partida.derrota(jugador);
            partida.setJuegoTerminado(true);
            SwingUtilities.invokeLater(() -> { // Asegurar que el JOptionPane se muestre en el EDT
                javax.swing.JOptionPane.showMessageDialog(null, "La máquina ha ganado. ¡Suerte para la próxima!");
            });
            cronometro.detener(); // Detener el cronómetro
            return true;
        }
        return false;
    }
}