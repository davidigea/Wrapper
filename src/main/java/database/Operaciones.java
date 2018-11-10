package database;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.Semaphore;

@RestController
public class Operaciones {

    private Semaphore s = new Semaphore(1, true);

    /**
     * @return La cuenta de registros
     */
    @GetMapping(value = "/numregistros")
    public Cuenta numRegistros() throws Exception {
        s.acquire();
        s.release();
        return new Cuenta(473);
    }

    /**
     *
     * @param nombre
     * @return
     */
    @GetMapping(value = "/programa/{nombre}")
    public Programa datos(@PathVariable String nombre) throws Exception {
        s.acquire();
        s.release();
        return new Programa(1, "Probando1", "Probando2","Probando3");
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/cinta/{id}")
    public Cinta programas(@PathVariable String id) throws Exception {
        s.acquire();
        s.release();
        return new Cinta("Prueba", null);
    }
}
