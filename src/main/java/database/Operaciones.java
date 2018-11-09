package database;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Operaciones {

    /**
     * @return La cuenta de registros
     */
    @GetMapping(value = "/numregistros")
    public Cuenta numRegistros() {
        return new Cuenta(473);
    }

    /**
     *
     * @param nombre
     * @return
     */
    @GetMapping(value = "/programa/{nombre}")
    public Programa datos(@PathVariable String nombre) {
        return new Programa(1, "Probando1", "Probando2","Probando3");
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/cinta/{id}")
    public Cinta programas(@PathVariable String id) {
        return new Cinta("Prueba", null);
    }
}
