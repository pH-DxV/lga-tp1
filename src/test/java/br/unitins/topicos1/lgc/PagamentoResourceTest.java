package br.unitins.topicos1.lgc;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import br.unitins.topicos1.lgc.Cafe.dto.CafeDTO;
import br.unitins.topicos1.lgc.Cafe.dto.CafeDTOResponse;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTO;
import br.unitins.topicos1.lgc.CategoriaDoCafe.dto.CategoriaDoCafeDTOResponse;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTO;
import br.unitins.topicos1.lgc.Endereco.dto.EnderecoDTOResponse;
import br.unitins.topicos1.lgc.ItemPedido.dto.ItemPedidoDTO;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTO;
import br.unitins.topicos1.lgc.Marca.dto.MarcaDTOResponse;
import br.unitins.topicos1.lgc.NotaSensorial.model.NotaSensorial;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoBoletoDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoCartaoDTO;
import br.unitins.topicos1.lgc.Pagamento.dto.PagamentoPixDTO;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTO;
import br.unitins.topicos1.lgc.Pedido.dto.PedidoDTOResponse;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTO;
import br.unitins.topicos1.lgc.Usuario.dto.UsuarioDTOResponse;

import java.util.List;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PagamentoResourceTest {

    private static Long idPedidoCartao;
    private static Long idPedidoPix;
    private static Long idPedidoBoleto;
    
    // Variáveis auxiliares para criar dependências
    private static Long idUsuario;
    private static Long idEndereco;
    private static Long idCafe;
    private static Long idMarca;
    private static Long idCategoria;

    @BeforeEach
    public void setup() {
        // 1. Cria Usuário (Cliente)
        if (idUsuario == null) {
            UsuarioDTO dto = new UsuarioDTO(
                "Cliente Pagamento", 
                "cli_pagto", 
                "123456", 
                "77788899900", 
                2, 
                null
            );
            idUsuario = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/usuarios")
                .then().statusCode(201).extract().as(UsuarioDTOResponse.class).id();
        }

        // 2. Cria Endereço
        if (idEndereco == null) {
            Long idMunicipioTeste = 1L; 
            EnderecoDTO dto = new EnderecoDTO("77000123", "Rua Pagto", "100", "Apto", "Centro", idMunicipioTeste, idUsuario);
            idEndereco = given()
                .contentType(ContentType.JSON)
                .body(dto)
                .when().post("/endereco")
                .then().statusCode(201).extract().as(EnderecoDTOResponse.class).id();
        }

        // 3. Cria Café (Produto)
        if (idCafe == null) {
            long time = System.currentTimeMillis();
            
            if (idMarca == null) {
                MarcaDTO marcaDto = new MarcaDTO("Marca Pagto " + time, null);
                idMarca = given().contentType(ContentType.JSON).body(marcaDto).when().post("/marcas").then().statusCode(201).extract().as(MarcaDTOResponse.class).id();
            }
            
            if (idCategoria == null) {
                CategoriaDoCafeDTO catDto = new CategoriaDoCafeDTO("Cat Pagto " + time, null);
                idCategoria = given().contentType(ContentType.JSON).body(catDto).when().post("/categorias").then().statusCode(201).extract().as(CategoriaDoCafeDTOResponse.class).id();
            }

            CafeDTO cafeDto = new CafeDTO(
                "Café Pagamento " + time, 
                "Café para teste de pagamento",
                idMarca, 
                idCategoria, 
                3L, 
                1L, 
                Set.of(NotaSensorial.MEL), 
                85, 
                100.00, // Preço alto para facilitar conta
                500.0, 
                1000 // Estoque alto
            );
            
            idCafe = given()
                .contentType(ContentType.JSON)
                .body(cafeDto)
                .when().post("/cafes")
                .then().statusCode(201).extract().as(CafeDTOResponse.class).id();
        }

        // 4. Cria Pedidos Pendentes (Um para cada tipo de teste)
        // Precisamos criar pedidos novos se eles ainda não existirem ou já tiverem sido pagos/usados
        if (idPedidoCartao == null) {
            idPedidoCartao = criarPedidoPendente();
        }
        if (idPedidoPix == null) {
            idPedidoPix = criarPedidoPendente();
        }
        if (idPedidoBoleto == null) {
            idPedidoBoleto = criarPedidoPendente();
        }
    }

    private Long criarPedidoPendente() {
        ItemPedidoDTO item = new ItemPedidoDTO(1, idCafe);
        PedidoDTO dto = new PedidoDTO(idUsuario, idEndereco, List.of(item));
        
        return given()
            .contentType(ContentType.JSON)
            .body(dto)
            .when().post("/pedidos")
            .then().statusCode(201)
            .extract().as(PedidoDTOResponse.class).id();
    }

    @Test
    @Order(1)
    public void testPagarCartao() {
        PagamentoCartaoDTO dto = new PagamentoCartaoDTO(
            idPedidoCartao,
            "Joao Silva",
            "1234567890123456", 
            "VISA",
            "12/30",
            "123"
        );

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/pagamentos/cartao")
        .then()
            .statusCode(201)
            .body("confirmado", is(true))
            .body("numeroCartaoMask", notNullValue());
            
        // Verifica se o status do pedido mudou
        given()
            .when().get("/pedidos/" + idPedidoCartao)
            .then().statusCode(200)
            .body("status", is("PAGO"));
    }

    @Test
    @Order(2)
    public void testPagarPix() {
        PagamentoPixDTO dto = new PagamentoPixDTO(idPedidoPix);

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/pagamentos/pix")
        .then()
            .statusCode(201)
            .body("confirmado", is(false)) // Pix começa como não confirmado (pendente)
            .body("chavePix", notNullValue()); // Verifica se gerou a chave
            
        // Verifica se o status do pedido se manteve (ainda aguardando)
        given()
            .when().get("/pedidos/" + idPedidoPix)
            .then().statusCode(200)
            .body("status", is("AGUARDANDO_PAGAMENTO"));
    }

    @Test
    @Order(3)
    public void testPagarBoleto() {
        PagamentoBoletoDTO dto = new PagamentoBoletoDTO(idPedidoBoleto);

        given()
            .contentType(ContentType.JSON)
            .body(dto)
        .when()
            .post("/pagamentos/boleto")
        .then()
            .statusCode(201)
            .body("confirmado", is(false)) // Boleto começa como não confirmado
            .body("codigoBarras", notNullValue()); // Verifica se gerou o código
            
        // Verifica se o status do pedido se manteve
        given()
            .when().get("/pedidos/" + idPedidoBoleto)
            .then().statusCode(200)
            .body("status", is("AGUARDANDO_PAGAMENTO"));
    }
}