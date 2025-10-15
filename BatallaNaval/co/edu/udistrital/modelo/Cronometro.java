package co.edu.udistrital.modelo;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 * Clase Cronometro
 * Implementa Runnable para ejecutarse en un hilo separado.
 * Actualiza un JLabel con el tiempo transcurrido.
 * @author Steven
 * @version 1.0
 */
public class Cronometro implements Runnable {

    private JLabel etiquetaTiempo;
    private volatile boolean corriendo; // volatile para asegurar visibilidad entre hilos
    private long tiempoInicio;

    public Cronometro(JLabel etiquetaTiempo) {
        this.etiquetaTiempo = etiquetaTiempo;
        this.corriendo = false;
    }

    public void iniciar() {
        this.corriendo = true;
        this.tiempoInicio = System.currentTimeMillis();
        new Thread(this).start(); // Inicia el hilo del cronómetro
    }

    public void detener() {
        this.corriendo = false;
    }

    public boolean isRunning() {
    return corriendo;
}

    @Override
    public void run() {
        while (corriendo) {
            long tiempoActual = System.currentTimeMillis();
            long tiempoTranscurrido = (tiempoActual - tiempoInicio) / 1000; // En segundos

            // Actualizar la GUI de forma segura en el Event Dispatch Thread (EDT)
            SwingUtilities.invokeLater(() -> {
                etiquetaTiempo.setText("Tiempo: " + tiempoTranscurrido + "s");
            });

            try {
                Thread.sleep(1000); // Espera 1 segundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restaura el estado de interrupción
                System.out.println("Cronómetro interrumpido.");
                break;
            }
        }
    }
}
