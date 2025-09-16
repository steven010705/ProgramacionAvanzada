package EjercicioTres;

class Recurso {
    String nombre;
    public Recurso(String nombre) {
    this.nombre = nombre;
 }

    // Método sincronizado para usar ambos recursos en orden
    public void usarAmbos(Recurso otro) {
        Recurso primero = this.nombre.compareTo(otro.nombre) < 0 ? this : otro;
        Recurso segundo = this == primero ? otro : this;
        synchronized (primero) {
            System.out.println(Thread.currentThread().getName() + " adquirió " +
            primero.nombre);

            try { 
                Thread.sleep(100); 
            } catch (Exception e) {}

            synchronized (segundo) {
                System.out.println(Thread.currentThread().getName() + " adquirió " +
                segundo.nombre);
                System.out.println(Thread.currentThread().getName() + " terminó operación con ambos recursos");
            }
        }
    }
}

public class SolucionUnoOrdenFijo {
    public static void main(String[] args) {
        Recurso r1 = new Recurso("Recurso A");
        Recurso r2 = new Recurso("Recurso B");
        Thread t1 = new Thread(() -> r1.usarAmbos(r2), "Hilo 1");
        Thread t2 = new Thread(() -> r2.usarAmbos(r1), "Hilo 2");
        t1.start();
        t2.start();
    }
}
