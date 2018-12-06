package database.domain;

import java.util.LinkedList;

/**
 * Clase que se corresponde con un programa que hay en el servidor de base de datos
 */
public class Programa {
    int numRegistro;
    String nombre;
    String tipo;
    LinkedList<String> cinta;

    public Programa(int numRegistro, String nombre, String tipo, LinkedList<String> cinta) {
        this.numRegistro = numRegistro;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cinta = cinta;
    }

    public Programa() {}

    public int getNumRegistro() {
        return numRegistro;
    }

    public void setNumRegistro(int numRegistro) {
        this.numRegistro = numRegistro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LinkedList<String> getCinta() {
        return cinta;
    }

    public void setCinta(LinkedList<String> cinta) {
        this.cinta = cinta;
    }
}
