// Clase que representa la tarea de descargar un archivo
class DescargarArchivo extends Thread {
    public void run() {
        for (int i = 1; i <= 5; i++) { // Simula 5 pasos de descarga
            System.out.println("Descargando... " + i * 20 + "%");
            try {
                Thread.sleep(1000); // Espera 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("¡Descarga completada!");
    }
}

// Clase que imprime puntos como animación
class AnimacionCarga extends Thread {
    public void run() {
        while (true) {
            System.out.print(".");
            try {
                Thread.sleep(500); // medio segundo por punto
            } catch (InterruptedException e) {
                break; // Si recibe interrupción, sale del bucle
            }
        }
    }
}

public class EjercicioUno {
    public static void main(String[] args) {
        DescargarArchivo descarga = new DescargarArchivo(); // Crea hilo de descarga
        AnimacionCarga animacion = new AnimacionCarga(); // Crea hilo de animación
        animacion.start(); // Inicia animación en segundo plano
        descarga.start(); // Inicia descarga
        try {
            descarga.join(); // Espera a que termine la descarga
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        animacion.interrupt(); // Detiene la animación
        System.out.println("\nListo para usar el archivo.");
    }
}