package co.edu.udistrital.modelo;

import java.io.*;
import java.util.Scanner;

public class ServicioJugador {
    private static final String ARCHIVO_USUARIOS = "jugadores.txt";

    public boolean registrarJugador(Jugador jugador) {
        if (existeUsuario(jugador.getUsuario())) {
            return false;
        }
        guardarUsuarioEnArchivo(jugador.getUsuario(), jugador.getContrasena());
        return true;
    }

    public boolean iniciarSesion(String usuario, String contrasena) {
        return validarUsuario(usuario, contrasena);
    }

    private void guardarUsuarioEnArchivo(String usuario, String contrasena) {
        try (FileWriter fw = new FileWriter(ARCHIVO_USUARIOS, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(usuario + "," + contrasena + ",0,0");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean existeUsuario(String usuario) {
        try (Scanner scanner = new Scanner(new File(ARCHIVO_USUARIOS))) {
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                if (datos.length > 0 && datos[0].equals(usuario)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            // Si el archivo no existe, no hay usuarios aún
            return false;
        }
        return false;
    }

    private boolean validarUsuario(String usuario, String contrasena) {
        try (Scanner scanner = new Scanner(new File(ARCHIVO_USUARIOS))) {
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                if (datos.length > 1 && datos[0].equals(usuario) && datos[1].equals(contrasena)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        }
        return false;
    }
}
