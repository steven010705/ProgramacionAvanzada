package EjercicioTres;

// Clase que representa un recurso que puede ser usado por hilos
class Recurso {
    String nombre;
    public Recurso(String nombre) {
    this.nombre = nombre;
 }

 // Método sincronizado para usar este recurso y luego intentar usar otro
 public synchronized void usar(Recurso otro) {
    System.out.println(Thread.currentThread().getName() + " usa " + nombre);
    try { 
        Thread.sleep(100);
    } catch (Exception e) {}
    
    System.out.println(Thread.currentThread().getName() + " espera por " + otro.nombre);
    otro.terminar(); // intento de acceder al otro recurso
 }
 
 // Método sincronizado que indica que se terminó de usar este recurso
 public synchronized void terminar() {
    System.out.println(Thread.currentThread().getName() + " termina con " + nombre);
 }
}

public class Escenario {
    public static void main(String[] args) {
        Recurso r1 = new Recurso("Recurso A");
        Recurso r2 = new Recurso("Recurso B");

        // Hilo 1 quiere primero A, luego B
        Thread t1 = new Thread(() -> r1.usar(r2), "Hilo 1");
        
        // Hilo 2 quiere primero B, luego A
        Thread t2 = new Thread(() -> r2.usar(r1), "Hilo 2");
        t1.start();
        t2.start();
    }
}

