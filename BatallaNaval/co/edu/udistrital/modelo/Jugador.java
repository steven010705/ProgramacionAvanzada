package co.edu.udistrital.modelo;

import java.awt.GridLayout;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Jugador {
    private String usuario;
    private String contrasena;
    private List<Barco> barcos;

    public Jugador(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.barcos = crearBarcosFijos();
    }

    private List<Barco> crearBarcosFijos() {
        List<Barco> barcos = new ArrayList<>();
        barcos.add(new Barco("Portaaviones", 6, true));
        barcos.add(new Barco("Acorazado", 4, false));
        barcos.add(new Barco("Acorazado", 4, false));
        barcos.add(new Barco("Submarino", 3, true));
        barcos.add(new Barco("Submarino", 3, true));
        barcos.add(new Barco("Submarino", 3, true));
        barcos.add(new Barco("Fragata", 2, false));
        barcos.add(new Barco("Fragata", 2, false));
        barcos.add(new Barco("Fragata", 2, false));
        barcos.add(new Barco("Fragata", 2, false));
        return barcos;
    }

    // Archivo donde se almacenan los usuarios
    private static final String ARCHIVO_USUARIOS = "jugadores.txt";

    // Método para registrar un jugador con GUI y guardar en archivo
    public void registrarJugador() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel userLabel = new JLabel("Usuario:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Contraseña:");
        JPasswordField passField = new JPasswordField();

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(
            null,
            panel,
            "Crear Usuario",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String usuarioIngresado = userField.getText();
            String contrasenaIngresada = new String(passField.getPassword());

            if (usuarioIngresado.isEmpty() || contrasenaIngresada.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Usuario y contraseña no pueden estar vacíos.");
                return;
            }

            if (existeUsuario(usuarioIngresado)) {
                JOptionPane.showMessageDialog(null, "El usuario ya existe.");
                return;
            }

            setUsuario(usuarioIngresado);
            setContrasena(contrasenaIngresada);
            guardarUsuarioEnArchivo(getUsuario(), getContrasena());
            JOptionPane.showMessageDialog(null, "Usuario creado: " + getUsuario());
        }
    }

    // Método para iniciar sesión con GUI y validación en archivo
    public void iniciarSesion() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        JLabel userLabel = new JLabel("Usuario:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Contraseña:");
        JPasswordField passField = new JPasswordField();

        panel.add(userLabel);
        panel.add(userField);
        panel.add(passLabel);
        panel.add(passField);

        int result = JOptionPane.showConfirmDialog(
            null,
            panel,
            "Iniciar Sesión",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String usuarioIngresado = userField.getText();
            String contrasenaIngresada = new String(passField.getPassword());

            if (validarUsuario(usuarioIngresado, contrasenaIngresada)) {
                setUsuario(usuarioIngresado);
                setContrasena(contrasenaIngresada);
                JOptionPane.showMessageDialog(null, "¡Inicio de sesión exitoso!");
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
            }
        }
    }

    // Guarda usuario y contraseña en el archivo
    private void guardarUsuarioEnArchivo(String usuario, String contrasena) {
        try (FileWriter fw = new FileWriter(ARCHIVO_USUARIOS, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(usuario + "," + contrasena);
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar usuario.");
        }
    }

    // Verifica si el usuario ya existe en el archivo
    private boolean existeUsuario(String usuario) {
        try (Scanner scanner = new Scanner(new File(ARCHIVO_USUARIOS))) {
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                if (datos.length > 0 && datos[0].equals(usuario)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Archivo de usuarios no encontrado. Se creará uno nuevo al registrar un usuario.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al verificar usuario: " + e.getMessage());
        }
        return false;
    }

    // Valida usuario y contraseña en el archivo
    private boolean validarUsuario(String usuario, String contrasena) {
        try (Scanner scanner = new Scanner(new File(ARCHIVO_USUARIOS))) {
            while (scanner.hasNextLine()) {
                String[] datos = scanner.nextLine().split(",");
                if (datos.length == 2 && datos[0].equals(usuario) && datos[1].equals(contrasena)) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Archivo de usuarios no encontrado. Debe registrar un usuario primero.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al validar usuario: " + e.getMessage());
        }
        return false;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}