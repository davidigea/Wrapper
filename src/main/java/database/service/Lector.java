package database.service;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import database.domain.Cuenta;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Predicate;
import database.domain.Programa;

@Service // Hacemos que spring cree un bean con una instancia del objeto
public class Lector {
    // Constantes
    private static final String RUTA_PROGRAMA = "database-msdos/gwbasic.bat";
    private static final int PROGS_PER_PAGE = 18;
    private static final int X = 640;
    private static final int Y = 350;
    private static final int ANCHURA = 640;
    private static final int ALTURA = 420;

    // Variables globales
    private Robot robot;
    private Tesseract1 ocr;
    private int orden; // 1:nombre, 2:tipo, 3:cinta, 4:antigüedad

    // Constructor de la clase
    public Lector() throws IOException, AWTException, InterruptedException {
        // Lanzamos el proceso
        new ProcessBuilder("dosbox", RUTA_PROGRAMA).start();
        orden = 1;
        robot = new Robot();
        ocr = new Tesseract1();
        ocr.setLanguage("spa+eng");
        ocr.setOcrEngineMode(2);
        ocr.setDatapath("./tessdata");
        ocr.setTessVariable("preserve_interword_spaces", "1");
        Thread.sleep(1000);
    }

    /*
     * Pre:  ---
     * Post: Devuelve el nº de registros que hay en la base de datos
     */
    public synchronized Cuenta getNumRegistros() throws TesseractException, InterruptedException {
        pulsar('4');
        Thread.sleep(2000);
        String result = capturarPantalla();
        pulsar('\n');
        Thread.sleep(500);
        int indice = skipPart(result, 0, (c) -> !Character.isDigit(c));
        return new Cuenta(Integer.parseInt(result.substring(indice, skipPart(result, indice, (c) -> Character.isDigit(c)))));
    }

    /*
     * Pre:  ---
     * Post: Si hay un programa de nombre 'nombre', devuelve sus datos. En caso
     *       contrario, devuelve null.
     */
    public synchronized Programa getProgram(String nombre) throws TesseractException, NumberFormatException, InterruptedException {
        ordenar(1); // ordenamos según la cinta
        Thread.sleep(500);
        entrarMenu6(nombre);
        Thread.sleep(500); // Hacemos captura
        String result = capturarPantalla();

        // Se comprueba si existe el programa
        int indice = skipPart(result, 0, (c) -> !Character.isAlphabetic(c));
        if (result.charAt(indice) == 'M') { // El id no existía
            return null;
        }

        // El programa existe
        Programa p = new Programa();
        leerPrograma(result, indice, p);
        pulsar('\n');
        return p;
    }


    public synchronized LinkedList<String> getProgramsById(String idCinta) throws TesseractException, NumberFormatException, InterruptedException {
        ordenar(3); // ordenamos según la cinta
        Thread.sleep(500);
        entrarMenu6(idCinta);
        Thread.sleep(500); // Hacemos captura
        String result = capturarPantalla();

        // Comprobamos si el id existía
        int indice = skipPart(result, 0, (c) -> !Character.isAlphabetic(c));
        if (result.charAt(indice) == 'M') { // El id no existía
            return null;
        }

        // El ID existe: leemos los programas de una página
        boolean leerPagina = true;
        LinkedList<Programa> progs = new LinkedList<>();
        idCinta = idCinta.toUpperCase();
        while (leerPagina) {
            boolean ultimoPerteneceCinta = false;
            for (int i = 0; i < PROGS_PER_PAGE; i++) {
                ultimoPerteneceCinta = false;
                Programa p = new Programa();
                indice = leerPrograma(result, indice, p);
                if(indice == -1) { // Error, salimos
                    break;
                }
                for (String j : p.getCinta()) {
                    if (j.equals(idCinta)) {
                        progs.add(p);
                        ultimoPerteneceCinta = true;
                    }
                }
            }
            if(ultimoPerteneceCinta) { // Hacer captura
                indice = 0;
                pulsar(' ');
                Thread.sleep(500);
                result = capturarPantalla();
            }
            else {
                leerPagina = false;
            }
        }
        pulsar('\n');

        // Ordenamos la lista según el registro
        progs.sort(Comparator.comparingInt(Programa::getNumRegistro));
        LinkedList<String> nombres = new LinkedList<>();
        for (Programa i : progs) {
            nombres.add((i.getNombre()));
        }
        return nombres;
    }

    /*
     * Pre:  ---
     * Post: Captura lo que hay la pantalla del emulador
     */
    private String capturarPantalla() throws TesseractException {
        return ocr.doOCR(robot.createScreenCapture(new Rectangle(X, Y, ANCHURA, ALTURA)));
    }

    /*
     * Pre:  1 <= criterio <=4
     * Post: Ordena la BD según el criterio indicado
     */
    private void ordenar(int criterio) throws InterruptedException {
        if(orden != criterio) {
            orden = criterio;
            pulsar('3');
            Thread.sleep(300);
            pulsar(String.valueOf(criterio).charAt(0));
            pulsar('\n');
            Thread.sleep(2000);
            pulsar('\n');
            Thread.sleep(500);
        }
    }

    /*
     * Pre:  ---
     * Post: Entra al menú 6, escribiendo el texto correspondiente
     */
    private void entrarMenu6(String texto) {
        pulsar('6'); // Entramos al menú
        escribir(texto); // escribimos el id de la cinta
        pulsar('\n');
    }

    /*
     * Pre:  cadena != null
     * Post: Escribe mediante el robot la cadena indicada en mayúsculas
     */
    private void escribir(String cadena) {
        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            if (Character.isLowerCase(c)) {
                c = Character.toUpperCase(c);
            }
            pulsar(c);
        }
    }

    /*
     * Pre:  ---
     * Post: Simula la pulsación del carácter.
     */
    private void pulsar(char c) {
        char x;
        if (Character.isLowerCase(c)) {
            x = Character.toUpperCase(c);
        } else {
            x = c;
            if (Character.isUpperCase(c)) {
                robot.keyPress(KeyEvent.VK_SHIFT);
            }
        }
        robot.keyPress((int) x);
        robot.keyRelease((int) x);
        if (Character.isUpperCase(c)) {
            robot.keyRelease(KeyEvent.VK_SHIFT);
        }
    }

    /*
     * Pre:  --
     * Post: Intenta leer un programa. Si no puede, devuelve -1.
     */
    private int leerPrograma(String result, Integer indice, Programa p) {
        try {
            // Descartamos hasta el título
            indice = skipPart(result, indice, (c) -> !Character.isDigit(c));
            indice = skipPart(result, indice, (c) -> Character.isDigit(c));
            indice = skipPart(result, indice, (c) -> !Character.isDigit(c) && !Character.isAlphabetic(c));

            // Leemos el título del programa
            int oldIndex = indice;
            indice = skipUntil2Whitespaces(result, indice);
            p.setNombre(result.substring(oldIndex, indice));
            indice = skipPart(result, indice, (c) -> !Character.isDigit(c) && !Character.isAlphabetic(c));

            // Leemos el tipo del programa
            oldIndex = indice;
            indice = skipUntil2Whitespaces(result, indice);
            p.setTipo(result.substring(oldIndex, indice));
            indice = skipPart(result, indice, (c) -> !Character.isDigit(c) && !Character.isAlphabetic(c));

            // Leemos las cintas
            oldIndex = indice;
            indice = skipPart(result, indice, (c) -> Character.isDigit(c) || Character.isAlphabetic(c) || c == '-');
            LinkedList<String> cintas = new LinkedList<>();
            Collections.addAll(cintas, result.substring(oldIndex, indice).split("-"));
            p.setCinta(cintas);
            indice = skipPart(result, indice, (c) -> !Character.isDigit(c));

            // Leemos el registro
            oldIndex = indice;
            indice = skipPart(result, indice, (c) -> Character.isDigit(c));
            p.setNumRegistro(Integer.parseInt(result.substring(oldIndex, indice)));

            return indice;
        }
        catch(Exception e) {
            return -1;
        }
    }

    /*
     * Pre:  ---
     * Post: Salta la parte del string mientras se cumplan ambos predicados.
     */
    private int skipPart(String result, int indice, Predicate<Character> p) {
        for (int i = indice; i < result.length(); i++) {
            if (!p.test(result.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    /*
     * Pre:  'result' contiene en alguna parte dos espacios seguidos
     * Post: Salta la parte del string 'result' desde indice hasta que encuentra
     *       dos espacios en blanco seguidos.
     */
    private int skipUntil2Whitespaces(String result, int indice) {
        while (true) {
            indice = skipPart(result, indice, (c) -> c != ' ');
            if (result.charAt(indice + 1) == ' ' || result.charAt(indice + 1) == '—' || result.charAt(indice + 1) == '-') {
                return indice;
            }
            indice++;
        }
    }

}
