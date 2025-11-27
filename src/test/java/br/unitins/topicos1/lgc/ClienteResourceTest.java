package br.unitins.topicos1.lgc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.time.LocalDate;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTO;
import br.unitins.topicos1.lgc.Cliente.dto.ClienteDTOResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClienteResourceTest {

    private static Long idCliente;

    // Dados de teste base
    private final LocalDate dataNasc = LocalDate.of(1990, 5, 15);
    
    // NOTA: O DTO foi adaptado, não inclui o campo 'peso'
    
    @Test
    @Order(1)
    public void testCreate() {
        // Criação de um Cliente (Perfil USER é forçado no Service)
        ClienteDTO dto = new ClienteDTO(
            "Cliente Teste", 
            "cliente.teste", // login
            "senha123",      // senha
            "11122233344",   // cpf
            dataNasc
        );

        ClienteDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/clientes")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("nome", is("Cliente Teste"))
            .body("cpf", is("11122233344"))
            .extract().as(ClienteDTOResponse.class);

        idCliente = response.id();
    }

    @Test
    @Order(2)
    public void testUpdate() {
        // Atualiza os dados
        ClienteDTO dto = new ClienteDTO(
            "Cliente Atualizado", 
            "cliente.teste.novo", // Novo login
            "senhaNova",          // Nova senha
            "11122233344",        // CPF inalterado
            dataNasc
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/clientes/" + idCliente)
        .then()
            .statusCode(200)
            .body("id", is(idCliente.intValue()))
            .body("login", is("cliente.teste.novo"))
            .body("nome", is("Cliente Atualizado"));
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/clientes")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindById() {
        given()
        .when()
            .get("/clientes/" + idCliente)
        .then()
            .statusCode(200)
            .body("id", is(idCliente.intValue()));
    }

    @Test
    @Order(5)
    public void testFindByNome() {
        given()
        .when()
            .get("/clientes/search/Cliente Atualizado")
        .then()
            .statusCode(200);
    }
    
    @Test
    @Order(6)
    public void testDelete() {
        given()
        .when()
            .delete("/clientes/" + idCliente)
        .then()
            .statusCode(204);
    }

    @Test
    @Order(7) // Testa se o delete funcionou (espera 404)
    public void testFindByIdAfterDelete() {
        given()
        .when()
            .get("/clientes/" + idCliente)
        .then()
            .statusCode(404);
    }
}