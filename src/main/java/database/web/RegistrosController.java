package database.web;


import database.domain.Cuenta;
import database.reader.Lector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class RegistrosController {
    // Instanciamos el bean del lector
    @Autowired
    private Lector l;

    /**
     * @return La cuenta de registros
     */
    @GetMapping(value = "/numregistros")
    public Cuenta get() throws Exception {
        return l.getNumRegistros();
    }
}