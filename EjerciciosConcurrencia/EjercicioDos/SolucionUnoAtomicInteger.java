package EjercicioDos;

// Importamos la clase AtomicInteger que proporciona operaciones atómicas
import java.util.concurrent.atomic.AtomicInteger;

// La clase Contador implementa Runnable para poder ejecutarse en un hilo
class Contador implements Runnable {
    
    // Usamos AtomicInteger para tener operaciones seguras entre hilos
    private AtomicInteger contador = new AtomicInteger(0);
    
    // Este método se ejecuta cuando el hilo se inicia
    public void run() {
        for (int i = 0; i < 1000; i++) {
        contador.incrementAndGet(); // Incrementa el valor de forma atómica
        }
        // Muestra el valor actual del contador desde este hilo
        System.out.println("Contador parcial: " + contador.get());
    }

    // Método para obtener el valor final del contador
    public int getValorFinal() {
        return contador.get(); // Retorna el valor actual de forma segura
    }
}

// Clase principal donde se crean y ejecutan los hilos
public class SolucionUnoAtomicInteger {
    public static void main(String[] args) {
        
        Contador tarea = new Contador(); // Creamos la tarea compartida
        Thread t1 = new Thread(tarea); // Creamos hilo 1 con la tarea
        Thread t2 = new Thread(tarea); // Creamos hilo 2 con la misma tarea
        t1.start(); // Iniciamos hilo 1
        t2.start(); // Iniciamos hilo 2

        try {
            t1.join(); // Esperamos a que termine hilo 1
            t2.join(); // Esperamos a que termine hilo 2
        } catch (InterruptedException e) {
            e.printStackTrace(); // Captura errores si se interrumpe
          }

        // Mostramos el valor final del contador
        System.out.println("Valor final del contador: " + tarea.getValorFinal());
    }
}