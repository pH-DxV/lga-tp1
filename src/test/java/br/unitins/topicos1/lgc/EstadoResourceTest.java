package br.unitins.topicos1.lgc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Estado.dto.EstadoDTO;
import br.unitins.topicos1.lgc.Estado.dto.EstadoDTOResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EstadoResourceTest {

    private static Long idEstado;

    @Test
    @Order(1)
    public void testCreate() {
        // ID 3L corresponde a Regiao.NORTE (verifique seu Enum Regiao)
        EstadoDTO dto = new EstadoDTO("Tocantins", "TO", 3L);

        EstadoDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/estados")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("Tocantins"))
            .body("sigla", is("TO"))
            .extract().as(EstadoDTOResponse.class);

        idEstado = response.id();
    }

    @Test
    @Order(2)
    public void testUpdate() {
        // Mudando para Regiao.CENTRO_OESTE (ID 1L) só para testar
        EstadoDTO dto = new EstadoDTO("Goiás", "GO", 1L);

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/estados/" + idEstado)
        .then()
            .statusCode(200)
            .body("id", is(idEstado.intValue()))
            .body("nome", is("Goiás"));
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/estados")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindById() {
        given()
        .when()
            .get("/estados/" + idEstado)
        .then()
            .statusCode(200)
            .body("id", is(idEstado.intValue()));
    }

    @Test
    @Order(5)
    public void testFindByNome() {
        given()
        .when()
            .get("/estados/find/Goiás")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(6)
    public void testDelete() {
        given()
        .when()
            .delete("/estados/" + idEstado)
        .then()
            .statusCode(204);
    }
}