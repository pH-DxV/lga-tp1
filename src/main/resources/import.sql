-- ============================================================================
-- 1. DADOS GEOGRÁFICOS (Estado e Município)
-- ============================================================================

-- === ESTADOS ===
-- NORTE (3)
INSERT INTO estado (nome, sigla, regiao) VALUES ('Acre', 'AC', 3);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Amapá', 'AP', 3);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Amazonas', 'AM', 3);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Pará', 'PA', 3);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Rondônia', 'RO', 3);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Roraima', 'RR', 3);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Tocantins', 'TO', 3);

-- NORDESTE (2)
INSERT INTO estado (nome, sigla, regiao) VALUES ('Alagoas', 'AL', 2);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Bahia', 'BA', 2);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Ceará', 'CE', 2);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Maranhão', 'MA', 2);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Paraíba', 'PB', 2);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Pernambuco', 'PE', 2);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Piauí', 'PI', 2);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Rio Grande do Norte', 'RN', 2);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Sergipe', 'SE', 2);

-- CENTRO-OESTE (1)
INSERT INTO estado (nome, sigla, regiao) VALUES ('Distrito Federal', 'DF', 1);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Goiás', 'GO', 1);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Mato Grosso', 'MT', 1);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Mato Grosso do Sul', 'MS', 1);

-- SUDESTE (4)
INSERT INTO estado (nome, sigla, regiao) VALUES ('Espírito Santo', 'ES', 4);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Minas Gerais', 'MG', 4);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Rio de Janeiro', 'RJ', 4);
INSERT INTO estado (nome, sigla, regiao) VALUES ('São Paulo', 'SP', 4);

-- SUL (5)
INSERT INTO estado (nome, sigla, regiao) VALUES ('Paraná', 'PR', 5);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Rio Grande do Sul', 'RS', 5);
INSERT INTO estado (nome, sigla, regiao) VALUES ('Santa Catarina', 'SC', 5);

-- === MUNICIPIOS ===
-- NORTE
INSERT INTO municipio (nome, id_estado) VALUES ('Rio Branco', 1);   -- Acre
INSERT INTO municipio (nome, id_estado) VALUES ('Macapá', 2);       -- Amapá
INSERT INTO municipio (nome, id_estado) VALUES ('Manaus', 3);       -- Amazonas
INSERT INTO municipio (nome, id_estado) VALUES ('Belém', 4);        -- Pará
INSERT INTO municipio (nome, id_estado) VALUES ('Porto Velho', 5);  -- Rondônia
INSERT INTO municipio (nome, id_estado) VALUES ('Boa Vista', 6);    -- Roraima
INSERT INTO municipio (nome, id_estado) VALUES ('Palmas', 7);       -- Tocantins

-- NORDESTE
INSERT INTO municipio (nome, id_estado) VALUES ('Maceió', 8);        -- Alagoas
INSERT INTO municipio (nome, id_estado) VALUES ('Salvador', 9);      -- Bahia
INSERT INTO municipio (nome, id_estado) VALUES ('Fortaleza', 10);    -- Ceará
INSERT INTO municipio (nome, id_estado) VALUES ('São Luís', 11);     -- Maranhão
INSERT INTO municipio (nome, id_estado) VALUES ('João Pessoa', 12);  -- Paraíba
INSERT INTO municipio (nome, id_estado) VALUES ('Recife', 13);       -- Pernambuco
INSERT INTO municipio (nome, id_estado) VALUES ('Teresina', 14);     -- Piauí
INSERT INTO municipio (nome, id_estado) VALUES ('Natal', 15);        -- Rio Grande do Norte
INSERT INTO municipio (nome, id_estado) VALUES ('Aracaju', 16);      -- Sergipe

-- CENTRO-OESTE
INSERT INTO municipio (nome, id_estado) VALUES ('Brasília', 17);     -- Distrito Federal
INSERT INTO municipio (nome, id_estado) VALUES ('Goiânia', 18);      -- Goiás
INSERT INTO municipio (nome, id_estado) VALUES ('Cuiabá', 19);       -- Mato Grosso
INSERT INTO municipio (nome, id_estado) VALUES ('Campo Grande', 20); -- Mato Grosso do Sul

-- SUDESTE
INSERT INTO municipio (nome, id_estado) VALUES ('Vitória', 21);       -- Espírito Santo
INSERT INTO municipio (nome, id_estado) VALUES ('Belo Horizonte', 22); -- Minas Gerais
INSERT INTO municipio (nome, id_estado) VALUES ('Rio de Janeiro', 23); -- Rio de Janeiro
INSERT INTO municipio (nome, id_estado) VALUES ('São Paulo', 24);     -- São Paulo

-- SUL
INSERT INTO municipio (nome, id_estado) VALUES ('Curitiba', 25);      -- Paraná
INSERT INTO municipio (nome, id_estado) VALUES ('Porto Alegre', 26);  -- Rio Grande do Sul
INSERT INTO municipio (nome, id_estado) VALUES ('Florianópolis', 27); -- Santa Catarina


-- ============================================================================
-- 2. USUÁRIOS E CLIENTES (Herança JOINED)
-- ATENÇÃO: Campo 'peso' REMOVIDO
-- Senha Hash para '123456'
-- ============================================================================

-- 2.1 ADMIN
INSERT INTO usuario (nome, cpf, login, senha, dataNascimento) VALUES ('Admin', '11111111111', 'admin', 'KUtBD9kIl87mEJ9A9ykmmWdNdO5AARI95nCklB5rpjrGkb7LVoqBwrpHiYiaMh+yyBfnfYR+G1gJecdm8A85rw==', '2000-01-01');
INSERT INTO usuario_perfil (id_usuario, id_perfil) VALUES (1, 1);

-- 2.2 CLIENTE
INSERT INTO usuario (nome, cpf, login, senha, dataNascimento) VALUES ('Cliente 01', '22222222222', 'cliente', 'KUtBD9kIl87mEJ9A9ykmmWdNdO5AARI95nCklB5rpjrGkb7LVoqBwrpHiYiaMh+yyBfnfYR+G1gJecdm8A85rw==', '1995-05-20');
-- Tabela filha para herança
INSERT INTO cliente (id) VALUES (2);
INSERT INTO usuario_perfil (id_usuario, id_perfil) VALUES (2, 2);

-- ============================================================================
-- 3. CONTATOS E ENDEREÇOS
-- ============================================================================
INSERT INTO telefone (ddd, numero, id_usuario) VALUES ('63', '999991234', 2);

INSERT INTO endereco (cep, rua, numero, complemento, bairro, id_municipio, id_usuario) VALUES ('77000000', 'Rua dos Cafes', '10', 'Qd 10 Lt 1', 'Centro', 7, 2);

-- ============================================================================
-- 4. CATÁLOGO DE PRODUTOS
-- ============================================================================
INSERT INTO marca (nome, descricao) VALUES ('Fazenda Ambiental Fortaleza', 'Orgulho nacional do café especial');
INSERT INTO marca (nome, descricao) VALUES ('St. Helena Coffee', 'Café raríssimo da ilha onde Napoleão foi exilado');
INSERT INTO marca (nome, descricao) VALUES ('Hacienda La Esmeralda', 'Considerado o café mais prestigiado do planeta');

INSERT INTO categoriadocafe (nome, descricao) VALUES ('Microlate', 'Produção limitada.');
INSERT INTO categoriadocafe (nome, descricao) VALUES ('Fine Robusta', 'Robusta de alta qualidade');
INSERT INTO categoriadocafe (nome, descricao) VALUES ('Single Origin', 'Café de uma única origem');

-- CAFÉS (Sem a coluna estoque)
INSERT INTO cafe (nome,
                    descricao,
                    pontuacao_sca, preco, peso, id_marca, id_categoria_cafe, nivelDeTorra, tratamento) VALUES ('Caparaó Aurora Nº 08 – Microlote',
                                                                                                                                'Microlote exclusivo de produção limitada, colheita manual e lote numerado. Perfil complexo e elegante.',
                                                                                                                                89, 140.0, 250.0, 3, 1, 'MEDIA_CLARA', 'NATURAL');

INSERT INTO cafe (nome,
                    descricao,
                    pontuacao_sca, preco, peso, id_marca, id_categoria_cafe, nivelDeTorra, tratamento) VALUES ('Sul de Minas – Fazenda Boa Vista',
                                                                                                                                'Café de origem única, representando o terroir clássico do Sul de Minas, com equilíbrio e doçura natural.',
                                                                                                                                85, 80.0, 250.0, 1, 3, 'MEDIA', 'NATURAL');

-- Notas Sensoriais
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (1, 'FRUTAS_TROPICAIS');
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (1, 'PESSEGO');
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (2, 'CHOCOLATE_AO_LEITE');
INSERT INTO cafe_notasensorial (id_cafe, nota_sensorial) VALUES (2, 'AMENDOAS');

-- ============================================================================
-- 5. ESTOQUE (Entidade Separada)
-- ============================================================================
INSERT INTO estoque (id_cafe, quantidade, dataUltimaMovimentacao) VALUES (1, 100, NOW());
INSERT INTO estoque (id_cafe, quantidade, dataUltimaMovimentacao) VALUES (2, 300, NOW());

-- ============================================================================
-- 6. PEDIDOS E ITENS
-- ============================================================================
INSERT INTO pedido (dataHora, totalPedido, valorFrete, status, id_usuario, id_endereco_entrega) VALUES (NOW(), 1400.0, 0.0, 'PAGO', 2, 1);

INSERT INTO itempedido (precoUnitario, quantidade, id_cafe, id_pedido) VALUES (140.0, 10, 1, 1);

-- ============================================================================
-- 7. PAGAMENTO (Herança JOINED)
-- ============================================================================
-- Tabela Pai
INSERT INTO pagamento (valor, confirmado, dataConfirmacao, id_pedido) VALUES (91.80, true, NOW(), 1);

-- Tabela Filha (Cartão) - ID deve ser o mesmo do pai (1)
INSERT INTO pagamentocartao (id, nomeTitular, numeroCartao, bandeira) VALUES (1, 'CLIENTE ZERO UM', '**** **** **** 1234', 'VISA');