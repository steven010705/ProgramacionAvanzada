package EjercicioTres;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;

class RecursoLock {
    String nombre;
    ReentrantLock lock = new ReentrantLock();
    public RecursoLock(String nombre) {
    this.nombre = nombre;
 }
}

public class SolucionDosReentranLock {
    public static void main(String[] args) {

        RecursoLock r1 = new RecursoLock("Recurso A");
        RecursoLock r2 = new RecursoLock("Recurso B");
        
        Runnable tarea = () -> {
            
            RecursoLock primero = r1;
            RecursoLock segundo = r2;

            for (int i = 0; i < 3; i++) {
            try {
                // Intenta adquirir ambos locks con timeout
                if (primero.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "bloqueó " + primero.nombre);
                        Thread.sleep(50);
                        if (segundo.lock.tryLock(100, TimeUnit.MILLISECONDS)) {
                            try {
                                System.out.println(Thread.currentThread().getName() + "bloqueó " + segundo.nombre);
                                System.out.println(Thread.currentThread().getName() + "accede a ambos recursos");
                                break;
                            } finally {
                                segundo.lock.unlock();
                            }
                        }
                    } finally {
                        primero.lock.unlock();
                      }
                }
                Thread.sleep(100); // Espera antes de reintentar
                } catch (InterruptedException e) {
            e.printStackTrace();
            }
            }
        };
        Thread t1 = new Thread(tarea, "Hilo 1");
        Thread t2 = new Thread(tarea, "Hilo 2");
        t1.start();
        t2.start();
    }
}