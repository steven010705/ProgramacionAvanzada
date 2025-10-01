package co.edu.udistrital.vista;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
* Clase VistaTablero
* @author Steven
* @version 2.0
*/
public class VistaBarcos extends JPanel {

    // Mapa para almacenar los barcos y su cantidad
    private final Map<String, Integer> barcosCantidad = new LinkedHashMap<>();
    private final Map<String, Integer> barcosTamanio = new LinkedHashMap<>();
    private final Map<String, Color> barcosColor = new LinkedHashMap<>();

    /**
    * Inicialización de GUI
    */
    public VistaBarcos() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Barcos disponibles"));

        // Definición de los tipos de barcos, cantidad y colores
        barcosCantidad.put("Portaaviones", 1);
        barcosTamanio.put("Portaaviones", 6);
        barcosColor.put("Portaaviones", new Color(0, 51, 153));

        barcosCantidad.put("Acorazado", 2);
        barcosTamanio.put("Acorazado", 4);
        barcosColor.put("Acorazado", Color.RED);

        barcosCantidad.put("Submarino", 3);
        barcosTamanio.put("Submarino", 3);
        barcosColor.put("Submarino", Color.GREEN.darker());

        barcosCantidad.put("Fragata", 4);
        barcosTamanio.put("Fragata", 2);
        barcosColor.put("Fragata", Color.GRAY);

        // Mostrar los barcos disponibles
        for (String nombre : barcosCantidad.keySet()) {
            int cantidad = barcosCantidad.get(nombre);
            int tamanio = barcosTamanio.get(nombre);
            Color color = barcosColor.get(nombre);
            for (int i = 0; i < cantidad; i++) {
                add(crearBarcoPanel(nombre, tamanio, color));
            }
        }
    }

    /** 
    * Creación de panel para cada barco
    * @param nombre cadena de texto
    * @param longitud cantidad de casillas del barco
    * @param color color del barco
    */
    private JPanel crearBarcoPanel(String nombre, int longitud, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(nombre + ": "));
        for (int i = 0; i < longitud; i++) {
            JLabel celda = new JLabel("  ");
            celda.setOpaque(true);
            celda.setBackground(color);
            celda.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            panel.add(celda);
        }
        return panel;
    }
}
