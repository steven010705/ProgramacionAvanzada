package co.edu.udistrital.modelo;

/**
* Clase Tablero
* @author Steven
* @version 1.0
*/
public class Tablero {
    private final int filas;
    private final int columnas;
    private final String[][] tablero;

    /**
    * Inicializa las casillas
    * @param filas lineas horizontales del tablero
    * @param columnas lineas verticales del tablero
    */
    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = new String[filas][columnas];
        inicializarTablero();
    }

    /**
    * Recorre todas las casillas y las define como agua
    */
    private void inicializarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = "~"; // Agua
            }
        }
    }

    /**
    * Gestiona la ubicación de los barcos
    * @param x coordenada horizontal de la casilla
    * @param y coordenada vertical de la casilla
    * @param tamanio numero de casillas por fila y columna
    * @param horizontal bandera de las filas
    * @return devuelve que la casilla no tiene un barco (false)
    */
    public boolean colocarBarco(int x, int y, int tamanio, boolean horizontal) {
        if (horizontal) {
            if (y + tamanio > columnas) return false;
            for (int j = y; j < y + tamanio; j++) {
                if (!tablero[x][j].equals("~")) return false;
            }
            for (int j = y; j < y + tamanio; j++) {
                tablero[x][j] = "B";
            }
        } else {
            if (x + tamanio > filas) return false;
            for (int i = x; i < x + tamanio; i++) {
                if (!tablero[i][y].equals("~")) return false;
            }
            for (int i = x; i < x + tamanio; i++) {
                tablero[i][y] = "B";
            }
        }
        return true;
    }

    /**
    * Gestiona los estados de la casilla
    * @param x coordenada horizontal de la casilla
    * @param y coordenada vertical de la casilla
    * @return devuelve si la casilla fue impactada (true) o no (false)
    */
    public boolean recibirAtaque(int x, int y) {
        if (tablero[x][y].equals("B")) {
            tablero[x][y] = "X"; // Impacto
            return true;
        } else if (tablero[x][y].equals("~")) {
            tablero[x][y] = "O"; // Agua
            return false;
        }
        return false; // Ya atacado
    }

    /**
    * Obtiene los atributos respectivamente
    */
    public String[][] getTablero() {
        return tablero;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {
        return columnas;
    }
}
