package database;

/**
 * Clase que se corresponde con un programa que hay en el servidor de base de datos
 */
public class Programa {
    int numRegistro;
    String nombre;
    String tipo;
    String cinta;

    public Programa(int numRegistro, String nombre, String tipo, String cinta) {
        this.numRegistro = numRegistro;
        this.nombre = nombre;
        this.tipo = tipo;
        this.cinta = cinta;
    }

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

    public String getCinta() {
        return cinta;
    }

    public void setCinta(String cinta) {
        this.cinta = cinta;
    }
}
