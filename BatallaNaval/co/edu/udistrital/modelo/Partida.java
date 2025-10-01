package co.edu.udistrital.modelo;

import java.io.*;
import java.util.*;

/**
* Clase Partida
* @author Steven
* @version 3.0
*/
public class Partida {
    private Tablero tableroJugador;
    private Tablero tableroMaquina;
    private boolean juegoTerminado = false;
    private List<Barco> barcosJugador; // Barcos del jugador
    private List<Barco> barcosMaquina; // Barcos de la máquina

    /**
    * Inicialización de tableros y ubicación de barcos
    * @param jugador1 persona
    * @param jugador2 maquina
    */
    public void iniciarPartida(Jugador jugador1, Maquina jugador2) {
        tableroJugador = new Tablero(10, 10);
        tableroMaquina = new Tablero(10, 10);
        // Inicializar los barcos del jugador y la máquina
        this.barcosJugador = jugador1.getBarcos();
        this.barcosMaquina = crearBarcosFijosParaMaquina();
    }

    /**
    * Crea barcos fijos para la máquina, similar al jugador
    */
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
    * Gestiona los turnos del juego
    * @param jugador1 persona
    * @param jugador2 maquina
    */
    public void turnos(Jugador jugador1, Maquina jugador2) {
        boolean turnoJugador = true;
        while (!juegoTerminado) {
            if (turnoJugador) {
                // Vacío debido que se gestiona en el controlador
            } else {
                int[] mov = jugador2.decidirMovimiento(10, 10);
                boolean acierto = tableroJugador.recibirAtaque(mov[0], mov[1]);
                if (acierto) {
                    jugador2.agregarObjetivos(mov[0], mov[1], 10, 10);
                }
            }
            turnoJugador = !turnoJugador;
        }
    }

    /**
    * Gestiona los disparos realizados por el usuario
    * @param jugador usuario que juega
    * @param x coordenada horizontal de la casilla
    * @param y coordenada vertical de la casilla
    * @return si el tiro fue acertado, devuelve un impacto
    */
    public boolean movimientos(Jugador jugador, int x, int y) {
        // El jugador ataca la máquina
        boolean acierto = tableroMaquina.recibirAtaque(x, y);
        // Si acierta, se notifica al controlador para actualizar la vista
        return acierto;
    }

    /**
    * Valida si el jugador ganó
    * @param jugador usuario que juega
    */
    public void victoria(Jugador jugador) {
        juegoTerminado = true;
        guardarResultados(jugador, true);
    }

    /**
    * Valida si el jugador perdió
    * @param jugador usuario que juega
    */
    public void derrota(Jugador jugador) {
        juegoTerminado = true;
        guardarResultados(jugador, false);
    }

    /**
    * Valida si quedan barcos en el tableros
    * @param tablero lugar del juego
    * @return valor que determina la existencia o no de barcos
    */
    public boolean verificarDerrota(Tablero tablero) { // Cambiado a public para que el controlador pueda usarlo
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

    /**
    * Gestiona la posicion de los barcos de la máquina
    * @param x coordenada horizontal de la casilla
    * @param y coordenada vertical de la casilla
    * @return devuelve el barco correspondiente o "agua" (null)
    */
    public Barco obtenerBarcoEnPosicionMaquina(int x, int y) {
        if (barcosMaquina == null) return null; // Protección
        for (Barco barco : barcosMaquina) {
            if (barco.getPosiciones() != null) { // Protección
                for (int[] pos : barco.getPosiciones()) {
                    if (pos[0] == x && pos[1] == y) {
                        return barco;
                    }
                }
            }
        }
        return null;
    }

    /**
    * Gestiona la posicion de los barcos del jugador
    * @param x coordenada horizontal de la casilla
    * @param y coordenada vertical de la casilla
    * @return devuelve el barco correspondiente o "agua" (null)
    */
    public Barco obtenerBarcoEnPosicionJugador(int x, int y) {
        if (barcosJugador == null) return null; // Protección
        for (Barco barco : barcosJugador) {
            if (barco.getPosiciones() != null) { // Protección
                for (int[] pos : barco.getPosiciones()) {
                    if (pos[0] == x && pos[1] == y) {
                        return barco;
                    }
                }
            }
        }
        return null;
    }

    /**
    * Gestiona la persistencia de los resultados de las partidas
    * @param jugador usuario que juega
    * @param victoria bandera de estado de la partida
    */
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
                    // Se actualiza la línea con los nuevos valores
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

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }

    public List<Barco> getBarcosJugador() {
        return barcosJugador;
    }

    public List<Barco> getBarcosMaquina() {
        return barcosMaquina;
    }
}