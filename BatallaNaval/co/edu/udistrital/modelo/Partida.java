package co.edu.udistrital.modelo;

import java.io.*;
import java.util.*;

public class Partida {
    private Tablero tableroJugador;
    private Tablero tableroMaquina;
    private boolean juegoTerminado = false;
    private List<Barco> barcosJugador; // Barcos del jugador
    private List<Barco> barcosMaquina; // Barcos de la máquina

    public void iniciarPartida(Jugador jugador1, Maquina jugador2) {
        tableroJugador = new Tablero(10, 10);
        tableroMaquina = new Tablero(10, 10);
        // Aquí deberías ubicar los barcos de ambos jugadores (puedes hacerlo aleatoriamente o por selección)
        // Ejemplo: colocar barcos del jugador y de la máquina
        // Este método puede ser expandido según tu lógica de ubicación de barcos
    }

    public void turnos(Jugador jugador1, Maquina jugador2) {
        boolean turnoJugador = true;
        while (!juegoTerminado) {
            if (turnoJugador) {
                // Espera movimiento del jugador (debe ser gestionado por el controlador)
                // Por ejemplo: controlador llama a movimientos(jugador1, x, y)
            } else {
                int[] mov = jugador2.decidirMovimiento(10, 10);
                boolean acierto = tableroJugador.recibirAtaque(mov[0], mov[1]);
                if (acierto) {
                    jugador2.agregarObjetivos(mov[0], mov[1], 10, 10);
                }
                if (verificarDerrota(tableroJugador)) {
                    derrota(jugador1);
                    break;
                }
            }
            turnoJugador = !turnoJugador;
        }
    }

    public boolean movimientos(Jugador jugador, int x, int y) {
        // El jugador ataca la máquina
        boolean acierto = tableroMaquina.recibirAtaque(x, y);
        // Si acierta, podrías notificar al controlador para actualizar la vista
        if (verificarDerrota(tableroMaquina)) {
            victoria(jugador);
            juegoTerminado = true;
        }
        return acierto;
    }

    public void victoria(Jugador jugador) {
        juegoTerminado = true;
        // Aquí puedes guardar el resultado o notificar al controlador
        guardarResultados(jugador, true);
    }

    public void derrota(Jugador jugador) {
        juegoTerminado = true;
        guardarResultados(jugador, false);
    }

    private boolean verificarDerrota(Tablero tablero) {
        String[][] matriz = tablero.getTablero();
        for (int i = 0; i < tablero.getFilas(); i++) {
            for (int j = 0; j < tablero.getColumnas(); j++) {
                if (matriz[i][j].equals("B")) {
                    return false; // Aún quedan barcos
                }
            }
        }
        return true; // Todos los barcos han sido hundidos
    }

    public Barco obtenerBarcoEnPosicionMaquina(int x, int y) {
        for (Barco barco : barcosMaquina) {
            for (int[] pos : barco.getPosiciones()) {
                if (pos[0] == x && pos[1] == y) {
                    return barco;
                }
            }
        }
        return null;
    }

    public Barco obtenerBarcoEnPosicionJugador(int x, int y) {
        for (Barco barco : barcosJugador) {
            for (int[] pos : barco.getPosiciones()) {
                if (pos[0] == x && pos[1] == y) {
                    return barco;
                }
            }
        }
        return null;
    }

    public void guardarResultados(Jugador jugador, boolean victoria) {
        File archivo = new File("jugadores.txt");
        List<String> lineas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos[0].equals(jugador.getUsuario())) {
                    int victorias = datos.length > 2 ? Integer.parseInt(datos[2]) : 0;
                    int derrotas = datos.length > 3 ? Integer.parseInt(datos[3]) : 0;
                    if (victoria) {
                        victorias++;
                    } else {
                        derrotas++;
                    }
                    // Actualiza la línea con los nuevos valores
                    linea = datos[0] + "," + datos[1] + "," + victorias + "," + derrotas;
                }
                lineas.add(linea);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sobrescribe el archivo con las líneas actualizadas
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, false))) {
            for (String l : lineas) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters para los tableros si los necesitas en el controlador
    public Tablero getTableroJugador() {
        return tableroJugador;
    }

    public Tablero getTableroMaquina() {
        return tableroMaquina;
    }
}
