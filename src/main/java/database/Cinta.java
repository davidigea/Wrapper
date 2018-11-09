package database;

import java.util.List;

/**
 * Clase que se corresponde con una cinta que hay en el servidor de bases de datos
 */
public class Cinta {
    String nombre;
    List<String> programas;

    public Cinta(String nombre, List<String> programas) {
        this.nombre = nombre;
        this.programas = programas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getProgramas() {
        return programas;
    }

    public void setProgramas(List<String> programas) {
        this.programas = programas;
    }
}
