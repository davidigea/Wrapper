package database;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Lanza la aplicación web
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(Application.class);
        builder.headless(false).run(args);
    }
}
