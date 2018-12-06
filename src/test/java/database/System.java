package database;

import com.jayway.jsonpath.JsonPath;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.Test;
import com.jayway.jsonpath.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DirtiesContext
public class System {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void thatRegistrosIsOK() {
        ResponseEntity<String> entity = restTemplate.getForEntity( "/numregistros", String.class);
        ReadContext rc = JsonPath.parse(entity.getBody());
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(rc.read("$.numRegistros"), is(763));
    }

    @Test
    public void thatProgramaFailsIfIdDoesNotExist() {
        ResponseEntity<String> entity = restTemplate.getForEntity( "/programa/programaFalso", String.class);
        assertThat(entity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void thatProgramaIsOk1() {
        ResponseEntity<String> entity = restTemplate.getForEntity( "/programa/1942", String.class);
        ReadContext rc = JsonPath.parse(entity.getBody());
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(rc.read("$.numRegistro"), is(369));
        assertThat(rc.read("$.nombre"), is("1942"));
        assertThat(rc.read("$.tipo"), is("ARCADE"));
    }

    @Test
    public void thatProgramaIsOk2() {
        ResponseEntity<String> entity = restTemplate.getForEntity( "/programa/3D tunnel", String.class);
        ReadContext rc = JsonPath.parse(entity.getBody());
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(rc.read("$.numRegistro"), is(130));
        assertThat(rc.read("$.nombre"), is("3D TUNNEL"));
        assertThat(rc.read("$.tipo"), is("ARCADE"));
    }

    @Test
    public void thatCintaFailsIfIdDoesNotExist() {
        ResponseEntity<String> entity = restTemplate.getForEntity( "/cinta/cintaFalsa", String.class);
        assertThat(entity.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void thatCintaIsOk1() {
        ResponseEntity<String> entity = restTemplate.getForEntity( "/cinta/C", String.class);
        ReadContext rc = JsonPath.parse(entity.getBody());
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(rc.read("$.nombre"), is("C"));
    }

    @Test
    public void thatCintaIsOk2() {
        ResponseEntity<String> entity = restTemplate.getForEntity( "/cinta/3", String.class);
        ReadContext rc = JsonPath.parse(entity.getBody());
        assertThat(entity.getStatusCode(), is(HttpStatus.OK));
        assertThat(rc.read("$.nombre"), is("3"));
    }
}
