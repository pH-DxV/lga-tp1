package br.unitins.topicos1.lgc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioResourceTest {

    private static Long idUsuario;

    @Test
    @Order(1)
    public void testCreate() {
        UsuarioDTO dto = new UsuarioDTO("Usuario Teste", "11122233344", null, 75.5);

        UsuarioDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/usuarios")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("Usuario Teste"))
            .body("cpf", is("11122233344"))
            .extract().as(UsuarioDTOResponse.class);

        idUsuario = response.id();
    }

    @Test
    @Order(2)
    public void testUpdate() {
        UsuarioDTO dto = new UsuarioDTO("Usuario Teste Atualizado", "55566677788", null, 80.0);

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/usuarios/" + idUsuario)
        .then()
            .statusCode(200)
            .body("id", is(idUsuario.intValue()))
            .body("nome", is("Usuario Teste Atualizado"))
            .body("cpf", is("55566677788"));
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/usuarios")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindById() {
        given()
        .when()
            .get("/usuarios/" + idUsuario)
        .then()
            .statusCode(200)
            .body("id", is(idUsuario.intValue()));
    }

    @Test
    @Order(5)
    public void testFindByNome() {
        given()
        .when()
            .get("/usuarios/find/Usuario Teste")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(6)
    public void testDelete() {
        given()
        .when()
            .delete("/usuarios/" + idUsuario)
        .then()
            .statusCode(204);
    }
}