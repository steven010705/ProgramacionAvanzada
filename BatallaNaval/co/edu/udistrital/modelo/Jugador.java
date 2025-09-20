package co.edu.udistrital.modelo;

import java.util.ArrayList;
import java.util.List;

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

    public List<Barco> getBarcos() {
        return barcos;
    }

    public void setBarcos(List<Barco> barcos) {
        this.barcos = barcos;
    }

    // Puedes agregar aquí métodos para lógica de juego, por ejemplo:
    public void agregarBarco(Barco barco) {
        barcos.add(barco);
    }

    public void eliminarBarco(Barco barco) {
        barcos.remove(barco);
    }
}