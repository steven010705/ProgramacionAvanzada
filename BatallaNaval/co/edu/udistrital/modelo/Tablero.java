package co.edu.udistrital.modelo;

public class Tablero {
    private final int filas;
    private final int columnas;
    private final String[][] tablero;

    public Tablero(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.tablero = new String[filas][columnas];
        inicializarTablero();
    }

    private void inicializarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                tablero[i][j] = "~"; // Agua
            }
        }
    }

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