package database.web;


import database.domain.Cuenta;
import database.service.Lector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrosController {
    // Instanciamos el bean del lector
    @Autowired
    private Lector l;

    /**
     * @return La cuenta de registros
     */
    @GetMapping(value = "/numregistros")
    public ResponseEntity<?> get() throws Exception {
        return new ResponseEntity<>(l.getNumRegistros(), HttpStatus.OK);
    }
}