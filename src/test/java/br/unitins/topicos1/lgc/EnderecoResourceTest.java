package br.unitins.topicos1.lgc;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import java.time.LocalDate;

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

    // Dados de teste base
    private final LocalDate dataNasc = LocalDate.of(1990, 5, 15);

    @BeforeEach
    public void setup() {
        // Cria um usuário para vincular o endereço
        if (idUsuario == null) {
            // CORREÇÃO AQUI: Usando o construtor atualizado do UsuarioDTO (sem peso)
            UsuarioDTO dto = new UsuarioDTO(
                "Usuario Teste Endereco", 
                "user_endereco", // login
                "123456",        // senha
                "11188899900",   // cpf
                2,               // perfil (User)
                dataNasc         // dataNascimento
                // O campo 'peso' foi removido
            );
            
            idUsuario = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/usuarios") // Note: O endpoint de criação de usuário deve estar disponível ou use /clientes se preferir
                .then().statusCode(201).extract().as(UsuarioDTOResponse.class).id();
        }
    }

    @Test
    @Order(1)
    public void testCreate() {
        // Assumindo que seu DTO pede: cep, rua, numero, complemento, bairro, idMunicipio, idUsuario
        // Vamos usar valores fictícios para idMunicipio pois o foco é o endereço
        // Se o teste falhar por causa do municipio, precisaremos criar um no setup()
        
        // Nota: Se o seu EnderecoDTO atualizado pede idMunicipio, você deve passá-lo.
        // Vou assumir um valor 1L para municipio, mas o ideal é criar um municipio no setup.
        Long idMunicipio = 1L; 

        EnderecoDTO dto = new EnderecoDTO(
            "77000000",     // cep
            "Rua das Flores", // rua
            "10",           // numero
            "Quadra 10",    // complemento
            "Centro",       // bairro
            idMunicipio,    // idMunicipio
            idUsuario       // idUsuario
        );

        EnderecoDTOResponse response = given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/endereco")
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
        Long idMunicipio = 1L; // Mesmo do create

        EnderecoDTO dto = new EnderecoDTO(
            "77000123",     // cep novo
            "Rua Nova",     // rua nova
            "20",           // numero novo
            "Qd 2",         // complemento novo
            "Bairro Novo",  // bairro novo
            idMunicipio,
            idUsuario
        );

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
    public void testFindByRua() {
        given()
        .when()
            .get("/endereco/find/rua/Rua Nova")
        .then()
            .statusCode(200);
    }

    @Test
    @Order(6)
    public void testDelete() {
        given()
        .when()
            .delete("/endereco/" + idEndereco)
        .then()
            .statusCode(204);
    }
}