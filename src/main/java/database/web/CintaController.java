package database.web;


import database.domain.Cinta;
import database.service.Lector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
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
    public ResponseEntity<?> get(@PathVariable String id) throws Exception {
        LinkedList<String> list = l.getProgramsById(id);
        if(list != null) {
            return new ResponseEntity<>(new Cinta(id, list), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}