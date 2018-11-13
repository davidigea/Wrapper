package database.web;


import database.domain.Programa;
import database.reader.Lector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
public class ProgramaController {
    // Instanciamos el bean del lector
    @Autowired
    private Lector l;

    /**
     * @return La cuenta de registros
     */
    @GetMapping(value = "/programa/{nombre}")
    public Programa get(@PathVariable String nombre) throws Exception {
        LinkedList<String> l = new LinkedList<String>();
        l.add("D");
        l.add("X");
        return new Programa(1, nombre, "Humano", l);
    }
}