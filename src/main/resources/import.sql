-- ============================================================================
-- 1. DADOS GEOGRÁFICOS (Estado e Município)
-- ============================================================================
INSERT INTO estado (nome, sigla, regiao) VALUES ('Tocantins', 'TO', 3); -- ID 1
INSERT INTO estado (nome, sigla, regiao) VALUES ('Goiás', 'GO', 1);     -- ID 2
INSERT INTO estado (nome, sigla, regiao) VALUES ('Minas Gerais', 'MG', 4); -- ID 3
INSERT INTO estado (nome, sigla, regiao) VALUES ('São Paulo', 'SP', 4);    -- ID 4

INSERT INTO municipio (nome, id_estado) VALUES ('Palmas', 1);    -- ID 1
INSERT INTO municipio (nome, id_estado) VALUES ('Paraíso', 1);   -- ID 2
INSERT INTO municipio (nome, id_estado) VALUES ('Goiânia', 2);   -- ID 3
INSERT INTO municipio (nome, id_estado) VALUES ('Varginha', 3);  -- ID 4

-- ============================================================================
-- 2. USUÁRIOS E CLIENTES (Herança JOINED)
-- Senha Hash: KUtBD9kIl87mEJ9A9ykmmWdNdO5AARI95nCklB5rpjrGkb7LVoqBwrpHiYiaMh+yyBfnfYR+G1gJecdm8A85rw==
-- ============================================================================

-- 2.1 ADMIN (Apenas na tabela Usuario)
INSERT INTO usuario (...) VALUES (...);
INSERT INTO usuario_perfil (...) VALUES (1, 1);

-- 2.2 CLIENTE (Tabela Usuario + Tabela Cliente)
INSERT INTO usuario (...) VALUES (...);
INSERT INTO cliente (id) VALUES (2); -- <--- ISSO define que ele é cliente
INSERT INTO usuario_perfil (...) VALUES (2, 2);

-- ============================================================================
-- 3. CONTATOS E ENDEREÇOS (Vinculados ao Usuário)
-- ============================================================================
INSERT INTO telefone (ddd, numero, id_usuario) VALUES ('63', '999991234', 2);

-- Endereço agora tem vínculo com Municipio e Usuário, além de novos campos
INSERT INTO endereco (cep, rua, numero, complemento, bairro, id_municipio, id_usuario) 
VALUES ('77000000', 'Rua dos Cafés', '10', 'Qd 10 Lt 1', 'Centro', 1, 2);

-- ============================================================================
-- 4. CATÁLOGO DE PRODUTOS (Marca, Categoria, Café)
-- ============================================================================
INSERT INTO marca (nome, descricao) VALUES ('3 Corações', 'Tradicional.'); -- ID 1
INSERT INTO marca (nome, descricao) VALUES ('Orfeu', 'Especial.'); -- ID 2

INSERT INTO categoriadocafe (nome, descricao) VALUES ('Tradicional', 'Dia a dia.'); -- ID 1
INSERT INTO categoriadocafe (nome, descricao) VALUES ('Especial', 'Alta pontuação.'); -- ID 2

-- Café 1: Orfeu (Sem estoque aqui, pois separamos a entidade)
-- Atenção aos Enums salvos como STRING
INSERT INTO cafe (nome, descricao, pontuacao_sca, preco, peso, id_marca, id_categoria_cafe, nivelDeTorra, tratamento)
VALUES ('Orfeu Clássico', 'Equilibrado.', 84, 45.90, 250.0, 2, 2, 'MEDIA', 'NATURAL'); -- ID 1

-- Café 2: Mogiana
INSERT INTO cafe (nome, descricao, pontuacao_sca, preco, peso, id_marca, id_categoria_cafe, nivelDeTorra, tratamento)
VALUES ('Mogiana Paulista', 'Doce.', 82, 25.50, 250.0, 1, 1, 'MEDIA_ESCURA', 'LAVADO'); -- ID 2

-- Notas Sensoriais (Tabela auxiliar do @ElementCollection)
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (1, 'CARAMELO');
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (1, 'NOZ');
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (2, 'CHOCOLATE_AO_LEITE');
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (2, 'AMENDOAS');

-- ============================================================================
-- 5. ESTOQUE (Entidade Separada)
-- ============================================================================
INSERT INTO estoque (id_cafe, quantidade, dataUltimaMovimentacao) VALUES (1, 100, NOW());
INSERT INTO estoque (id_cafe, quantidade, dataUltimaMovimentacao) VALUES (2, 50, NOW());

-- ============================================================================
-- 6. PEDIDOS E ITENS
-- ============================================================================

-- Pedido 1: Realizado pelo João (ID 2), Status PAGO, com Frete calculado
INSERT INTO pedido (dataHora, totalPedido, valorFrete, status, id_usuario, id_endereco_entrega)
VALUES (NOW(), 91.80, 0.0, 'PAGO', 2, 1); -- Total = (45.90 * 2) + 0 frete

-- Itens do Pedido 1 (2 unidades do Café Orfeu)
INSERT INTO itempedido (precoUnitario, quantidade, id_cafe, id_pedido)
VALUES (45.90, 2, 1, 1);

-- ============================================================================
-- 7. PAGAMENTO (Polimorfismo JOINED)
-- ============================================================================

-- Pagamento do Pedido 1 (Cartão de Crédito)
-- 1. Insere na tabela pai PAGAMENTO
INSERT INTO pagamento (valor, confirmado, dataConfirmacao, id_pedido)
VALUES (91.80, true, NOW(), 1); -- ID do pagamento será 1

-- 2. Insere na tabela filha PAGAMENTOCARTAO (usando o mesmo ID 1)
INSERT INTO pagamentocartao (id, nomeTitular, numeroCartao, bandeira)
VALUES (1, 'JOAO CLIENTE', '**** **** **** 1234', 'VISA');