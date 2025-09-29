package co.edu.udistrital.modelo;

import java.util.ArrayList;
import java.util.List;

/**
* Clase Jugador
* @author Steven
* @version 1.0
*/
public class Jugador {
    private String usuario;
    private String contrasena;
    private List<Barco> barcos;

    /**
    * Inicializa los atributos
    * @param usuario primer dato del jugador 
    * @param contrasena segundo dato del jugador
    */
    public Jugador(String usuario, String contrasena) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.barcos = crearBarcosFijos();
    }
    /**
    * Añade los distintos barcos del tablero
    */
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

    /**
    * Modifica los barcos
    * @param barco elemento del tablero
    */
    public void agregarBarco(Barco barco) {
        barcos.add(barco);
    }

    public void eliminarBarco(Barco barco) {
        barcos.remove(barco);
    }

    /**
    * Obtiene los atributos respectivamente
    * @return atributo correspondiente
    */
    public String getUsuario() {
        return usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public List<Barco> getBarcos() {
        return barcos;
    }

    /**
    * Establece los atributos respectivamente
    */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setBarcos(List<Barco> barcos) {
        this.barcos = barcos;
    }
}