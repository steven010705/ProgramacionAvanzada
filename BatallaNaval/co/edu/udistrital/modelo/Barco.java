package co.edu.udistrital.modelo;

import java.util.List;

public class Barco {
    private String tipo;
    private int tamanio;
    private boolean tipoAtaque; // true para ataque, false para defensa
    private int vida;
    private List<int[]> posiciones; // Cada int[] es {x, y}

    public Barco(String tipo, int tamanio, boolean tipoAtaque) {
        this.tipo = tipo;
        this.tamanio = tamanio;
        this.tipoAtaque = tipoAtaque;
        this.vida = tamanio;
    }

    public void recibirAtaque() {
        if (vida > 0) {
            vida--;
        }
    }

    public boolean estaHundido() {
        return vida == 0;
    }

    public int getVida() {
        return vida;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }

    public boolean isTipoAtaque() {
        return tipoAtaque;
    }

    public void setTipoAtaque(boolean tipoAtaque) {
        this.tipoAtaque = tipoAtaque;
    }

    public List<int[]> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<int[]> posiciones) {
        this.posiciones = posiciones;
    }
}