package database.reader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import database.domain.Cuenta;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;
import java.util.concurrent.Semaphore;

@Service // Hacemos que spring cree un bean con una instancia del objeto
public class Lector {
    // Ruta del binario a ejecutar
    public static final String RUTA_PROGRAMA = "Database-MSDOS/Database/gwbasic.bat";

    // Variables globales
    private Robot robot = null;
    private Semaphore s;

    public Lector() throws IOException, AWTException {
        // Lanzamos el proceso
        Process p = new ProcessBuilder("dosbox", RUTA_PROGRAMA, "-fullscreen").start();
        s = new Semaphore(1, true); // Sólo 1 user concurrente
        robot = new Robot();
    }

    public Cuenta getNumRegistros() throws TesseractException, InterruptedException {
        // Acceso en exclusión mutua
        s.acquire();

        //Selecciono la opción de ver información de la aplicación
        robot.keyPress(KeyEvent.VK_4);
        robot.keyRelease(KeyEvent.VK_4);

        robot.delay(2000);

        //BufferedImage capture = robot.createScreenCapture(new Rectangle(0, 0, 1000, 600));
        BufferedImage capture = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

        // Liberamos la sección crítica
        s.release();

        // Leemos de la captura
        Tesseract1 ocr = new Tesseract1();
        ocr.setLanguage("sl");

        String result = ocr.doOCR(capture);

        char[] aux = result.toCharArray();
        int i = 0;
        while (i < aux.length) {
            if (Character.isDigit(aux[i])) {
                break;
            }
            i++;
        }
        int j = 0;
        String num = "";
        while (Character.isDigit(aux[i+j])) {
            num = num + aux[i + j];
            j++;
        }

        return new Cuenta(Integer.parseInt(num));
    }
}
