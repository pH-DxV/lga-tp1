package test.java.br.unitins.topicos1.lgc;

@QuarkusTest
// 2. @TestMethodOrder: Garante que os testes rodem na ordem que definimos (com @Order)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MarcaResourceTest {

    // 3. Vamos guardar o ID da marca que criarmos para usar nos outros testes
    private static Long idMarca;

    @Test
    @Order(1) // Este será o primeiro teste a rodar
    public void testCreate() {
        // Define o DTO que será enviado no corpo da requisição
        MarcaDTO dto = new MarcaDTO(
            "Marca Teste T1",
            "Descrição da Marca Teste T1"
        );

        // 4. given() ... when() ... then() (Sintaxe do REST Assured)
        MarcaDTOResponse response = given()
            .contentType(ContentType.JSON) // Define o tipo de conteúdo
            .body(dto)                     // O que vai no corpo
        .when()
            .post("/marcas")               // Ação: fazer um POST no endpoint /marcas
        .then()
            .statusCode(201)               // 5. Verificação (Assert): Esperamos o status 201 (Created)
            .body("id", notNullValue())    // Verifica se o 'id' veio na resposta
            .body("nome", is("Marca Teste T1")) // Verifica se o 'nome' está correto
            .extract().as(MarcaDTOResponse.class); // Extrai a resposta como um DTO

        // 6. Guarda o ID para os próximos testes
        idMarca = response.id();
    }

    @Test
    @Order(2) // Rodará depois do testCreate()
    public void testUpdate() {
        MarcaDTO dto = new MarcaDTO(
            "Marca Teste T1 (Atualizada)",
            "Descrição Atualizada"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .put("/marcas/" + idMarca) // Ação: PUT, usando o ID que salvamos
        .then()
            .statusCode(200) // 7. Esperamos 200 (OK)
            .body("id", is(idMarca.intValue())) // Verifica se o ID retornado é o mesmo
            .body("nome", is("Marca Teste T1 (Atualizada)")); // Verifica se o nome foi atualizado
    }

    @Test
    @Order(3)
    public void testFindAll() {
        given()
        .when()
            .get("/marcas") // Ação: GET
        .then()
            .statusCode(200); // 8. Esperamos 200 (OK)
            // Poderíamos adicionar mais verificações no corpo (body) se quiséssemos
    }

    @Test
    @Order(4)
    public void testFindById() {
        given()
        .when()
            .get("/marcas/" + idMarca) // Ação: GET com o ID
        .then()
            .statusCode(200)
            .body("id", is(idMarca.intValue()));
    }

    @Test
    @Order(5)
    public void testFindByNome() {
        given()
        .when()
            .get("/marcas/search/nome/Marca Teste") // Ação: GET na busca por nome
        .then()
            .statusCode(200);
            // Aqui poderíamos verificar se o corpo (body) da lista não está vazio
    }

    @Test
    @Order(6) // Este é o penúltimo teste
    public void testDelete() {
        given()
        .when()
            .delete("/marcas/" + idMarca) // Ação: DELETE
        .then()
            .statusCode(204); // 9. Esperamos 204 (No Content)
    }

    @Test
    @Order(7) // Este é o último
    public void testFindByIdAfterDelete() {
        given()
        .when()
            .get("/marcas/" + idMarca) // Tenta buscar a marca que acabamos de deletar
        .then()
            .statusCode(404); // 10. Esperamos 404 (Not Found)
    }
}