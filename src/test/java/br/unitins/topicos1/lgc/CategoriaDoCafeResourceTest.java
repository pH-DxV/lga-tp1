package br.unitins.topicos1.lgc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoriaDoCafeResourceTest {

    private static Long idCategoria;

    @Test
    @Order(1)
    public void testCreate() {
        CategoriaDoCafeDTO dto = new CategoriaDoCafeDTO("Especial", "Cafés acima de 80 pontos.");

        CategoriaDoCafeDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/categorias")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("Especial"))
            .extract().as(CategoriaDoCafeDTOResponse.class);

        idCategoria = response.id();
    }

    @Test
    @Order(2)
    public void testUpdate() {
        CategoriaDoCafeDTO dto = new CategoriaDoCafeDTO("Tradicional", "Cafés para o dia a dia.");

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/categorias/" + idCategoria)
        .then()
            .statusCode(200)
            .body("id", is(idCategoria.intValue()))
            .body("nome", is("Tradicional"));
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/categorias")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindById() {
        given()
        .when()
            .get("/categorias/" + idCategoria)
        .then()
            .statusCode(200)
            .body("id", is(idCategoria.intValue()));
    }

    @Test
    @Order(5)
    public void testFindByNome() {
        given()
        .when()
            .get("/categorias/search/Tradicional")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(6)
    public void testDelete() {
        given()
        .when()
            .delete("/categorias/" + idCategoria)
        .then()
            .statusCode(204);
    }
}