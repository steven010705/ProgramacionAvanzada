package co.edu.udistrital.modelo;

import java.io.*;
import java.util.Scanner;

/**
* Clase ServicioJugador
* @author Steven
* @version 2.0
*/
public class ServicioJugador {
    private static final String ARCHIVO_USUARIOS = "jugadores.txt";

    /**
    * Gestiona la creación de usuarios
    * @param jugador usuario que jugará
    * @return devuelve si el jugador que se quiere crear existe (true) o no (false)
    */
    public boolean registrarJugador(Jugador jugador) {
        if (existeUsuario(jugador.getUsuario())) {
            return false;
        }
        guardarUsuarioEnArchivo(jugador.getUsuario(), jugador.getContrasena());
        return true;
    }

    /**
    * Gestiona el ingreso de los usuarios creados
    * @param usuario nombre del jugador
    * @param contrasena clave del jugador
    * @return devuelve la verificación de la existencia del jugador
    */
    public boolean iniciarSesion(String usuario, String contrasena) {
        return validarUsuario(usuario, contrasena);
    }

    /**
    * Gestiona la persistencia de los usuarios del juego
    * @param usuario nombre del jugador
    * @param contrasena clave del jugador
    */
    private void guardarUsuarioEnArchivo(String usuario, String contrasena) {
        try (FileWriter fw = new FileWriter(ARCHIVO_USUARIOS, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(usuario + "," + contrasena + ",0,0");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Verifica la existencia del usuario en el archivo
    * @param usuario nombre del jugador
    * @return devuelve si el usuario existe (true) o no (false)
    */
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

    /**
    * Valida la existencia del usuario y contraseña ingresados
    * @param usuario nombre del jugador
    * @param contrasena clave del jugador
    * @return devuelve si los datos ingresados existen (true) o no (false)
    */
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
