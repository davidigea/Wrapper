package database.web;


import database.domain.Programa;
import database.service.Lector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProgramaController {
    // Instanciamos el bean del lector
    @Autowired
    private Lector l;

    /**
     * @return La cuenta de registros
     */
    @GetMapping(value = "/programa/{nombre}")
    public ResponseEntity<?> get(@PathVariable String nombre) throws Exception {
        Programa p = l.getProgram(nombre);
        if( p != null) {
            return new ResponseEntity<>(p, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}