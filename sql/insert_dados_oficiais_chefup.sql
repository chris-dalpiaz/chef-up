-- 1. pronome
INSERT INTO pronome (nome, version) VALUES
('Ele/Dele', 0),
('Ela/Dela', 0),
('Elu/Delu', 0),
('Prefiro não responder', 0);

INSERT INTO adjetivo (nome, version) VALUES
('Ágil', 0),
('Gentil', 0),
('Leal', 0),
('Pontual', 0),
('Sutil', 0),
('Ideal', 0),
('Formal', 0),
('Realista', 0),
('Autêntico', 0),
('Prático', 0),
('Inteligente', 0),
('Gentil', 0),
('Forte', 0),
('Simples', 0),
('Elegante', 0),
('Responsável', 0),
('Sociável', 0),
('Flexível', 0),
('Sensível', 0),
('Estável', 0);

-- 8. titulo
INSERT INTO titulo (nome, condicao_desbloqueio, version) VALUES
('Chef Iniciante', 'Concluir 1 receita', 0),
('Chef Intermediário', 'Concluir 10 receitas', 0),
('Chef Avançado', 'Concluir 50 receitas', 0),
('Mestre da Cozinha', 'Concluir 100 receitas', 0);

-- 10. avatar
INSERT INTO avatar (nome, imagem_url, version) VALUES
('Chef Pão de Queijo', 'http://localhost:8080/img/avatares/paozinho.png', 0),
('Chef Brócolis', 'http://localhost:8080/img/avatares/brocolinho.png', 0),
('Chef Tomate', 'http://localhost:8080/img/avatares/tomatinho.png', 0);

-- 4. categoria
INSERT INTO categoria (nome, icone_url, version) VALUES
('Massas', 'icone1.png', 0),
('Carnes', 'icone2.png', 0),
('Vegetariano', 'icone3.png', 0),
('Sobremesas', 'icone4.png', 0),
('Bebidas', 'icone5.png', 0),
('Vegano', 'icone6.png', 0),
('Peixes e Frutos do Mar', 'icone7.png', 0),
('Saladas', 'icone8.png', 0),
('Entradas', 'icone9.png', 0),
('Acompanhamentos', 'icone10.png', 0),
('Café da Manhã', 'icone11.png', 0),
('Lanches', 'icone12.png', 0),
('Pães e Panificação', 'icone13.png', 0);

-- ==========================================================
-- ======================== RECEITAS ========================
-- ==========================================================
-- FÁCIL>>   30XP
-- MÉDIO>>   70XP
-- DIFÍCIL>> 100XP

-- ================== COOKIES DE CHOCOLATE -- CATEGORIA 4: Sobremesas ==================
-- ===== UTENSÍLIOS  (Tabela geral de utensílios - disponível pra todas as receitas) =====
INSERT INTO utensilio (nome, version) VALUES
('Forno', 0), -- 1
('Tigela grande', 0), -- 2
('Batedeira', 0), -- 3
('Espátula de borracha', 0), -- 4
('Assadeira', 0), -- 5
('Colher de sorvete', 0), -- 6
('Papel manteiga', 0); -- 7

-- ===== INGREDIENTES (Tabela geral de ingredientes - disponível pra todas as receitas) =====
INSERT INTO ingrediente (nome, categoria, estimativa_validade, dica_conservacao, version) VALUES
('Farinha de trigo', 'Secos', 180, 'Armazene em local seco e arejado, de preferência em pote hermético.', 1), -- 1
('Manteiga sem sal (macia)', 'Laticínios', 30, 'Mantenha refrigerada e retire com antecedência para amolecer antes do uso.', 1), -- 2
('Açúcar mascavo', 'Secos', 365, 'Guarde em pote bem fechado para evitar endurecimento.', 1), -- 3
('Gotas de chocolate', 'Doces', 180, 'Conserve em local fresco e seco, longe de fontes de calor.', 1), -- 4
('Açúcar', 'Secos', 730, 'Armazene em recipiente fechado, em local seco.', 1), -- 5
('Ovo', 'Frescos', 21, 'Mantenha refrigerado e consuma até a data de validade indicada na embalagem.', 1), -- 6
('Bicarbonato de sódio', 'Secos', 730, 'Guarde em pote fechado, longe da umidade.', 1), -- 7
('Sal', 'Secos', 1825, 'Armazene em local seco, em recipiente fechado.', 1); -- 8


-- ===== DADOS DA RECEITA =====
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, 
imagem_receita, version) VALUES
('Cookies com gotas de chocolate', 'Com textura macia e sabor marcante de açúcar mascavo, 
esses cookies são a escolha perfeita para quem ama biscoitos de chocolate irresistivelmente fofinhos',
 1200, 'Fácil', 30, 4, 'http://localhost:8080/img/receitas/cookies-de-chocolate.jpg', 0);

-- ===== INGREDIENTES DA RECEITA =====
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(1, 1, 'g', 300, 0), -- Farinha de trigo          - 1
(1, 2, 'g', 175, 0), -- Manteiga sem sal (macia)  - 2
(1, 3, 'g', 150, 0), -- Açúcar mascavo            - 3
(1, 4, 'g', 250, 0), -- Gotas de chocolate        - 4
(1, 5, 'g', 10,  0), -- Açúcar                    - 5
(1, 6, 'un', 2,  0), -- Ovo                       - 6
(1, 7, 'colher de chá', 1, 0), -- Bicarbonato de sódio - 7 
(1, 8, 'Colher de chá', 1, 0); -- Sal             - 8

-- ===== UTENSÍLIOS DA RECEITA =====
INSERT INTO utensilio_receita(receita_id, utensilio_id, version) VALUES
(1, 1, 0), -- Forno
(1, 2, 0), -- Tigela grande
(1, 3, 0), -- Batedeira
(1, 4, 0), -- Espátula de borracha
(1, 5, 0), -- Assadeira
(1, 6, 0), -- Colher de sorvete
(1, 7, 0); -- Papel manteiga

-- ===== ETAPA 1 DA RECEITA =====
INSERT INTO etapa_receita (ordem, conteudo, receita_id, imagem_etapa, version) VALUES
(1, 'Pré-aqueça o forno a 180 ° C / 350 ° F. Em uma tigela, misture a farinha, o bicarbonato e o sal.',
 1, "http://localhost:8080/img/etapa-receita/farinha-tigela.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(1, 1, 0), -- Farinha
(1, 7, 0), -- Bicarbonato
(1, 8, 0); -- Sal

-- ================== COOKIES DE CHOCOLATE ==================


