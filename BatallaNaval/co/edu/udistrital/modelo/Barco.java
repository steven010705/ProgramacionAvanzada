package co.edu.udistrital.modelo;

public class Barco {
    private String tipo;
    private int tamanio;
    private boolean tipoAtaque; // true para ataque, false para defensa

    public Barco(String tipo, int tamanio, boolean tipoAtaque) {
        this.tipo = tipo;
        this.tamanio = tamanio;
        this.tipoAtaque = tipoAtaque;
    }

    public void recibirAtaque() {
        // Lógica para recibir un ataque
    }

    public void estado() {
        // Lógica para mostrar el estado del barco
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
}