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
    private final LocalDate dataNasc = LocalDate.of(1990, 5, 15);

    @Test
    @Order(1)
    public void testCreate() {
        ClienteDTO dto = new ClienteDTO(
            "Cliente Teste", 
            "cliente.teste", 
            "senha123",      
            "11122233355",   
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
            .extract().as(ClienteDTOResponse.class);

        idCliente = response.id();
    }

    @Test
    @Order(2)
    public void testUpdate() {
        ClienteDTO dto = new ClienteDTO(
            "Cliente Atualizado", 
            "cliente.teste", 
            "senhaNova",          
            "11122233355",        
            dataNasc
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/clientes/" + idCliente)
        .then()
            .statusCode(200)
            .body("nome", is("Cliente Atualizado"));
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given().when().get("/clientes").then().statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindById() {
        given().when().get("/clientes/" + idCliente).then().statusCode(200);
    }
    
    @Test
    @Order(5)
    public void testDelete() {
        given().when().delete("/clientes/" + idCliente).then().statusCode(204);
    }
}