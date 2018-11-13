package database.web;


import database.domain.Cinta;
import database.reader.Lector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class CintaController {
    // Instanciamos el bean del lector
    @Autowired
    private Lector l;

    /**
     * @return La cuenta de registros
     */
    @GetMapping(value = "/cinta/{id}")
    public Cinta get(@PathVariable String id) throws Exception {
        LinkedList<String> l = new LinkedList<String>();
        l.add("Zelda");
        l.add("Pokemon");
        l.add("Resident Evil");
        return new Cinta("Prueba", l);
    }
}