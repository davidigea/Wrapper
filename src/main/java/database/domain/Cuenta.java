package database.domain;

/**
 * Clase que se corresponde con la cuenta de registros que hay en el servidor
 * de base de datos
 */
public class Cuenta {
    int numRegistros;

    public Cuenta(int numRegistros) {
        this.numRegistros = numRegistros;
    }

    public int getNumRegistros() {
        return numRegistros;
    }

    public void setNumRegistros(int numRegistros) {
        this.numRegistros = numRegistros;
    }
}
