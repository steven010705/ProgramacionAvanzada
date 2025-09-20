package co.edu.udistrital.modelo;

import java.io.*;
import java.util.*;

public class MigrarJugadores {
    public static void main(String[] args) {
        File archivo = new File("jugadores.txt");
        List<String> lineas = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length < 4) {
                    // Si faltan victorias o derrotas, agrégalas como 0
                    StringBuilder nuevaLinea = new StringBuilder(linea);
                    for (int i = datos.length; i < 4; i++) {
                        nuevaLinea.append(",0");
                    }
                    lineas.add(nuevaLinea.toString());
                } else {
                    lineas.add(linea);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Sobrescribe el archivo con las líneas actualizadas
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, false))) {
            for (String l : lineas) {
                writer.write(l);
                writer.newLine();
            }
            System.out.println("Migración completada. Todas las líneas tienen victorias y derrotas.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}