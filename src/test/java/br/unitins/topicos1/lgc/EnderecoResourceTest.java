package br.unitins.topicos1.lgc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EnderecoResourceTest {

    private static Long idEndereco;
    private static Long idUsuario;

    @BeforeEach
    public void setup() {
        // Cria um usuário para vincular o endereço
        if (idUsuario == null) {
            UsuarioDTO dto = new UsuarioDTO("Usuario Teste Endereco", "11188899900", null, 80.0);
            idUsuario = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/usuarios")
                .then().statusCode(201).extract().as(UsuarioDTOResponse.class).id();
        }
    }

    @Test
    @Order(1)
    public void testCreate() {
        // Assumindo que seu DTO pede: cep, rua, complemento, idUsuario
        // Se pedir idMunicipio também, você precisará criar um Municipio no setup()
        EnderecoDTO dto = new EnderecoDTO("77000000", "Rua das Flores", "Quadra 10", idUsuario);

        EnderecoDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/endereco") // Verifique se sua rota é /endereco ou /enderecos
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("cep", is("77000000"))
            .extract().as(EnderecoDTOResponse.class);

        idEndereco = response.id();
    }

    @Test
    @Order(2)
    public void testUpdate() {
        EnderecoDTO dto = new EnderecoDTO("77000123", "Rua Nova", "Apto 202", idUsuario);

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/endereco/" + idEndereco)
        .then()
            .statusCode(200)
            .body("id", is(idEndereco.intValue()))
            .body("rua", is("Rua Nova"));
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/endereco")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(4)
    public void testFindByCep() {
        given()
        .when()
            .get("/endereco/find/cep/77000123")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(5)
    public void testDelete() {
        given()
        .when()
            .delete("/endereco/" + idEndereco)
        .then()
            .statusCode(204);
    }
}
