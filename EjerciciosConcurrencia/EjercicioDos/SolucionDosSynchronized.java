package EjercicioDos;

// Clase Contador que implementa Runnable
class Contador implements Runnable {
    
    private int contador = 0; // contador normal (no atómico)
    
    // Método sincronizado para que solo un hilo a la vez pueda ejecutarlo
    public synchronized void incrementar() {
        contador++; // incremento protegido
    }

    // Método que ejecuta la tarea del hilo
    public void run() {
        for (int i = 0; i < 1000; i++) {
        incrementar(); // llamamos al método sincronizado
        }
    }
    
    // Método sincronizado para obtener el valor final del contador
    public synchronized int getValorFinal() {
        return contador;
    }
}

// Clase principal
public class SolucionDosSynchronized {
 public static void main(String[] args) {
    
    Contador tarea = new Contador(); // Creamos la tarea
    Thread t1 = new Thread(tarea); // Creamos hilo 1
    Thread t2 = new Thread(tarea); // Creamos hilo 2
    t1.start(); // Iniciamos hilo 1
    t2.start(); // Iniciamos hilo 2
    
    try {
        t1.join(); // Esperamos a que termine hilo 1
        t2.join(); // Esperamos a que termine hilo 2
    } catch (InterruptedException e) {
        e.printStackTrace(); // Captura de error si hay interrupción
      }

    // Mostramos el resultado final del contador
    System.out.println("Valor final del contador: " + tarea.getValorFinal());
    }
}