package co.edu.udistrital.modelo;

import java.util.*;

/**
* Clase Maquina
* @author Steven
* @version 2.0
*/
public class Maquina {
    private List<int[]> movimientosRealizados;
    private Queue<int[]> objetivos; // Casillas a atacar alrededor de un acierto
    private Random random;

    /**
    * Inicializa los atributos
    */
    public Maquina() {
        movimientosRealizados = new ArrayList<>();
        objetivos = new LinkedList<>();
        random = new Random();
    }

    /**
     * Decide el próximo movimiento de la máquina
     * Si hay objetivos alrededor de un acierto, los ataca primero
     * @param filas número de filas del tablero
     * @param columnas número de columnas del tablero
     * @return un arreglo [x, y] con la posición a atacar
     */
    public int[] decidirMovimiento(int filas, int columnas) {
        int[] movimiento;
        // Si hay objetivos pendientes, atacar esos primero
        while (!objetivos.isEmpty()) {
            movimiento = objetivos.poll();
            if (!yaAtacado(movimiento[0], movimiento[1]) && dentroTablero(movimiento[0], movimiento[1], filas, columnas)) {
                movimientosRealizados.add(movimiento);
                return movimiento;
            }
        }
        // Si no hay objetivos, atacar aleatoriamente
        int x, y;
        do {
            x = random.nextInt(filas);
            y = random.nextInt(columnas);
        } while (yaAtacado(x, y));
        movimiento = new int[]{x, y};
        movimientosRealizados.add(movimiento);
        return movimiento;
    }

    /**
     * Llama este método cuando la máquina acierta un barco,
     * para agregar las casillas adyacentes como próximos objetivos
     * @param filas número de filas del tablero
     * @param columnas número de columnas del tablero
     */
    public void agregarObjetivos(int x, int y, int filas, int columnas) {
        int[][] direcciones = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] dir : direcciones) {
            int nx = x + dir[0];
            int ny = y + dir[1];
            if (dentroTablero(nx, ny, filas, columnas) && !yaAtacado(nx, ny)) {
                objetivos.add(new int[]{nx, ny});
            }
        }
    }

    /**
    * Valida si hay un objetivo acertados
    * @param x coordenada horizontal de la casilla
    * @param y coordenada vertical de la casilla
    */
    private boolean yaAtacado(int x, int y) {
        for (int[] mov : movimientosRealizados) {
            if (mov[0] == x && mov[1] == y) {
                return true;
            }
        }
        return false;
    }

    /**
    * Valida si el tiro está dentro del tablero
    * @param filas número de filas del tablero
    * @param columnas número de columnas del tablero
    * @param x coordenada horizontal de la casilla
    * @param y coordenada vertical de la casilla
    */
    private boolean dentroTablero(int x, int y, int filas, int columnas) {
        return x >= 0 && x < filas && y >= 0 && y < columnas;
    }

    /**
    * Reestablece las decisiones de la máquina
    */
    public void reiniciarMovimientos() {
        movimientosRealizados.clear();
        objetivos.clear();
    }
}
