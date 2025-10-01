package co.edu.udistrital.modelo;

import java.util.List;

/**
* Clase Barco
* @author Steven
* @version 1.0
*/
public class Barco {
    private String tipo;
    private int tamanio;
    private boolean tipoAtaque; // true para ataque, false para defensa
    private int vida;
    private List<int[]> posiciones; // Cada int[] es {x, y}
    
    /**
    * Inicializa los atributos
    */
    public Barco(String tipo, int tamanio, boolean tipoAtaque) {
        this.tipo = tipo;
        this.tamanio = tamanio;
        this.tipoAtaque = tipoAtaque;
        this.vida = tamanio;
    }

    /**
    * Resta puntos de vida al barco
    */
    public void recibirAtaque() {
        if (vida > 0) {
            vida--;
        }
    }

    /**
    * Verifica si un barco está hundido
    * @return 0
    */
    public boolean estaHundido() {
        return vida == 0;
    }

    /**
    * Obtiene los atributos respectivamente
    * @return atributo correspondiente
    */
    public int getVida() {
        return vida;
    }

    public String getTipo() {
        return tipo;
    }

    public int getTamanio() {
        return tamanio;
    }

    public List<int[]> getPosiciones() {
        return posiciones;
    }

    public boolean isTipoAtaque() {
        return tipoAtaque;
    }

    /**
    * Establece los atributos respectivamente
    */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public void setTipoAtaque(boolean tipoAtaque) {
        this.tipoAtaque = tipoAtaque;
    }
    
    public void setPosiciones(List<int[]> posiciones) {
        this.posiciones = posiciones;
    }
}
