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
('Farinha de trigo', 'Secos', 180, 'Armazene em local seco e arejado, de preferência em pote hermético.', 0), -- 1
('Manteiga sem sal - macia', 'Laticínios', 30, 'Mantenha refrigerada e retire com antecedência para amolecer antes do uso.', 0), -- 2
('Açúcar mascavo', 'Secos', 365, 'Guarde em pote bem fechado para evitar endurecimento.', 0), -- 3
('Gotas de chocolate', 'Doces', 180, 'Conserve em local fresco e seco, longe de fontes de calor.', 0), -- 4
('Açúcar', 'Secos', 730, 'Armazene em recipiente fechado, em local seco.', 0), -- 5
('Ovo', 'Frescos', 21, 'Mantenha refrigerado e consuma até a data de validade indicada na embalagem.', 0), -- 6
('Bicarbonato de sódio', 'Secos', 730, 'Guarde em pote fechado, longe da umidade.', 0), -- 7
('Sal', 'Secos', 1825, 'Armazene em local seco, em recipiente fechado.', 0); -- 8


-- ===== DADOS DA RECEITA =====
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, 
imagem_receita, version) VALUES
('Cookies com gotas de chocolate', 'Com textura macia e sabor marcante de açúcar mascavo, 
esses cookies são a escolha perfeita para quem ama biscoitos de chocolate irresistivelmente fofinhos',
 1200, 'Fácil', 30, 4, 'http://localhost:8080/img/receitas/cookies-de-chocolate.jpg', 0);

-- ===== INGREDIENTES DA RECEITA =====
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(1, 1, 'gramas', 300, 0), -- Farinha de trigo          - 1
(1, 2, 'gramas', 175, 0), -- Manteiga sem sal (macia)  - 2
(1, 3, 'gramas', 150, 0), -- Açúcar mascavo            - 3
(1, 4, 'gramas', 250, 0), -- Gotas de chocolate        - 4
(1, 5, 'gramas', 10,  0), -- Açúcar                    - 5
(1, 6, 'unidade', 2,  0), -- Ovo                       - 6
(1, 7, 'colher de chá', 1, 0), -- Bicarbonato de sódio - 7 
(1, 8, 'Colher de chá', 1, 0); -- Sal             - 8

-- ===== UTENSÍLIOS DA RECEITA =====
INSERT INTO utensilio_receita(receita_id, utensilio_id, version) VALUES
(1, 1, 0), -- Forno - 1
(1, 2, 0), -- Tigela grande - 2 
(1, 3, 0), -- Batedeira - 3
(1, 4, 0), -- Espátula de borracha - 4
(1, 5, 0), -- Assadeira - 5
(1, 6, 0), -- Colher de sorvete - 6
(1, 7, 0); -- Papel manteiga -7

-- ===== ETAPA 1 DA RECEITA =====
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(1, 1, 'Pré-aqueça o forno a 180 ° C / 350 ° F. Em uma tigela, misture a farinha, o bicarbonato e o sal.',
"http://localhost:8080/img/etapa-receita/cookies-de-chocolate-etapa1.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(1, 2, 0), -- Manteiga sem sal
(1, 3, 0), -- Bicarbonato
(1, 5, 0); -- Sal

-- ===== ETAPA 2 DA RECEITA =====
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(1, 2, 'Em outra tigela, bata a manteiga amolecida, o açúcar mascavo e o açúcar até obter uma mistura fofa e clara.',
"http://localhost:8080/img/etapa-receita/cookies-de-chocolate-etapa2.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(2, 2, 0), -- Manteiga
(2, 7, 0), -- Açúcar mascavo
(2, 8, 0); -- Açúcar

-- ===== ETAPA 3 DA RECEITA =====
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(1, 3, 'Bata os ovos, um de cada vez, até que estejam totalmente incorporados.',
"http://localhost:8080/img/etapa-receita/cookies-de-chocolate-etapa3.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(3, 6, 0); -- Ovo

-- ===== ETAPA 4 DA RECEITA =====
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(1, 4, 'Adicione a mistura de farinha à mistura de manteiga. Bata até misturar bem e, em seguida, adicione gotas de chocolate.',
"http://localhost:8080/img/etapa-receita/cookies-de-chocolate-etapa4.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(4, 4, 0); -- Gotas de chocolate

-- ===== ETAPA 5 DA RECEITA =====
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(1, 5, 'Coloque a massa em uma assadeira forrada, deixando espaço suficiente entre os biscoitos. 
Achate ligeiramente com a parte de trás da colher. Asse em forno pré-aquecido por aproximadamente 10 – 12 min. 
a 180°C/350°F até dourar. Os biscoitos devem permanecer macios por dentro. Deixe esfriar por aproximadamente 10 minutos antes de servir.',
"http://localhost:8080/img/etapa-receita/cookies-de-chocolate-etapa5.jpg" , 0);


-- ================== PÃO DE FRIGIDEIRA RECHEADO COM PURÊ DE BATATA E QUEIJOS -- CATEGORIA 13: Pães e Panificação ==================

-- ===== UTENSÍLIOS  (Tabela geral de utensílios - disponível pra todas as receitas) =====
INSERT INTO utensilio (nome, version) VALUES
-- 'Tijela Grande' 2
('Tigela média', 0), -- 8
('Colher', 0), -- 9
('Plástico filme', 0), -- 10
('Rolo de massa', 0), -- 11
('Panela', 0), -- 12
('Frigideira antiaderente', 0), -- 13
('Pincel de cozinha', 0); -- 14

-- ===== INGREDIENTES (Tabela geral de ingredientes - disponível pra todas as receitas) =====
INSERT INTO ingrediente (nome, categoria, estimativa_validade, dica_conservacao, version) VALUES
-- Farinha de trigo já cadastrada como ID 1
('Manteiga com sal - macia', 'Laticínios', 30, 'Mantenha refrigerada e retire com antecedência para amolecer antes do uso.', 0), -- 9
('Leite', 'Laticínios', 7, 'Mantenha refrigerado e consuma até a data de validade indicada na embalagem.', 0), -- 10
('Muçarela', 'Laticínios', 10, 'Conserve refrigerada e mantenha bem embalada para evitar ressecamento.', 0), -- 11
('Queijo parmesão', 'Laticínios', 30, 'Armazene refrigerado e bem embalado. Se for ralado, mantenha em pote fechado.', 0), -- 12
('Orégano', 'Temperos', 365, 'Guarde em local seco e arejado, longe da luz direta.', 0), -- 13
('Requeijão cremoso', 'Laticínios', 15, 'Mantenha refrigerado e consuma após aberto em até 7 dias.', 0), -- 14
('Batata média', 'Frescos', 2, 'Após o cozimento, mantenha refrigerada e consuma em até 2 dias.', 0), -- 15
('Azeite', 'Temperos', 180, 'Mantenha em local fresco e ao abrigo da luz para preservar o sabor e evitar oxidação.', 0); -- 16
-- Sal já cadastrado como ID 8

-- ===== DADOS DA RECEITA =====
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, 
imagem_receita, version) VALUES
('Pão de Frigideira Recheado com Purê de Batata e Queijos', 'Um pão de frigideira crocante por fora e recheado com um purê cremoso de batata, muçarela e parmesão. Ideal para um lanche rápido e reconfortante!',
 7600, 'Médio', 70, 13, 'http://localhost:8080/img/receitas/pao-batata-caseiro-crocante.png', 0);

-- ===== INGREDIENTES DA RECEITA =====
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(2, 1, 'xícara', 1, 0), -- Farinha de trigo          - 9
(2, 9, 'colher de sopa', 1, 0), -- Manteiga com sal (macia)  - 10
(2, 10, 'colher de sopa', 5, 0), -- Leite            - 11
(2, 11, 'gramas', 100, 0), -- Muçarela        - 12
(2, 12, 'colher de sopa', 1,  0), -- Queijo parmesão          - 13
(2, 13, 'colher de chá', 1,  0), -- Orégano                 - 14
(2, 14, 'colher de sopa', 2, 0), -- Requeijão cremoso - 15 
(2, 15, 'unidade', 2, 0), -- batata                   - 16
(2, 8, 'colher de chá', 1, 0), -- Sal           - 17
(2, 16, 'pincelada suave', 1, 0); -- Azeite           - 18

-- ===== UTENSÍLIOS DA RECEITA =====
INSERT INTO utensilio_receita (receita_id, utensilio_id, version) VALUES
(2, 8, 0),  -- Tigela média - 8
(2, 9, 0),  -- Colher - 9
(2, 10, 0),  -- Plástico filme - 10
(2, 11, 0), -- Rolo de massa - 11
(2, 12, 0), -- Panela - 12
(2, 13, 0), -- Frigideira antiaderente - 13
(2, 14, 0); -- Pincel de cozinha - 14

-- ===== ETAPA 1 DA RECEITA ===== 6
INSERT INTO etapa_receita (receita_id, ordem, conteudo, version) VALUES
(2, 1, 'Em uma tigela grande, misture a farinha de trigo, a manteiga derretida e o leite. 
Mexa com uma colher ou espátula até formar uma massa homogênea que desgrude das mãos. Não é necessário sovar.', 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(6, 9, 0), -- Farinha de trigo
(6, 10, 0), -- Manteiga com sal
(6, 11, 0); -- Leite

-- ===== ETAPA 2 DA RECEITA ===== 7
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, version) VALUES
(2, 2, 'Modele a massa em formato de bola, envolva em plástico filme e leve à geladeira por 1 hora para descansar.', 0);

-- ===== ETAPA 3 DA RECEITA ===== 8 
INSERT INTO etapa_receita (receita_id, ordem, conteudo, version) VALUES
(2, 3, 'Enquanto a massa descansa, cozinhe as batatas em uma panela com água e uma pitada de sal até ficarem bem macias.', 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(8, 16, 0), -- batata
(8, 17, 0); -- sal

-- ===== ETAPA 4 DA RECEITA ===== 9
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, version) VALUES
(2, 4, 'Transfira as batatas para um prato. Amasse bem com o parmesão e o orégano. Acrescente o leite ou requeijão e continue amassando até obter um purê cremoso e aveludado.', 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(9, 13, 0), -- queijo parmesão
(9, 14, 0), -- orégano
(9, 15, 0); -- requeijão

-- ===== ETAPA 5 DA RECEITA ===== 10
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, version) VALUES
(2, 5, 'Retire a massa da geladeira e abra com o rolo até que fique bem fina.', 0);

-- ===== ETAPA 6 DA RECEITA ===== 11
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, version) VALUES
(2, 6, 'No centro da massa aberta, coloque uma porção de muçarela ralada. Cubra com o purê de batata e finalize com o restante da muçarela.', 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(11, 12, 0); -- muçarela

-- ===== ETAPA 7 DA RECEITA ===== 12
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, version) VALUES
(2, 7, 'Junte as pontas da massa formando uma trouxinha. Pressione levemente com o rolo para achatar, sem romper o recheio.', 0);

-- ===== ETAPA 8 DA RECEITA ===== 13
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, version) VALUES
(2, 8, 'Aqueça a frigideira antiaderente, pincele com azeite e frite o pão dos dois lados até formar uma crosta dourada.', 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(13, 18, 0); -- azeite

-- ===== ETAPA 9 DA RECEITA ===== 14
INSERT INTO etapa_receita ( receita_id, ordem, conteudo, version) VALUES
(2, 9, 'Com o pão ainda quente, pincele manteiga derretida por cima e caso queira salpique salsinha picada.', 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(14, 10, 0); -- manteiga


-- ================== SMOOTHIE DE DESPERTAR -- CATEGORIA 5: Bebidas ==================

-- ===== UTENSÍLIOS  (Tabela geral de utensílios - disponível pra todas as receitas) =====
INSERT INTO utensilio (nome, version) VALUES
('Tábua de corte', 0), -- 15
('Faca', 0), -- 16
('Liquidificador', 0); -- 17

-- ===== INGREDIENTES (Tabela geral de ingredientes - disponível pra todas as receitas) =====
INSERT INTO ingrediente (nome, categoria, estimativa_validade, dica_conservacao, version) VALUES
('Espinafre', 'Verduras', 5, 'Conservar refrigerado e consumir rapidamente após a compra.', 0), -- 17
('Pera', 'Frutas', 7, 'Armazenar em local fresco ou refrigerado para maior durabilidade.', 0), -- 18
('Maçã', 'Frutas', 15, 'Manter em local fresco, longe da luz direta.', 0), -- 19
('Banana', 'Frutas', 5, 'Evitar refrigeração; consumir quando estiver madura.', 0), -- 20
('Kiwi', 'Frutas', 7, 'Refrigerar para prolongar a vida útil após amadurecimento.', 0), -- 21
('Suco de laranja', 'Bebidas', 3, 'Manter refrigerado e consumir em até 3 dias após aberto.', 0); -- 22

-- ===== DADOS DA RECEITA =====
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, 
imagem_receita, version) VALUES
('Smoothie de Despertar', 'Refrescante e nutritivo, o Wake-up Smoothie combina frutas frescas e espinafre para um começo de dia cheio de energia!',
 600, 'Fácil', 30, 13, 'http://localhost:8080/img/receitas/smoothie-do-despertar.png', 0);

-- ===== INGREDIENTES DA RECEITA =====
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(3, 17, 'gramas', 40, 0), -- Espinafre          - 19
(3, 18, 'gramas', 80, 0), -- Pera             - 20
(3, 19, 'gramas', 80, 0), -- Maçã            - 21
(3, 20, 'unidade', 1, 0), -- Banana        - 22
(3, 21, 'unidade', 2,  0), -- Kiwi          - 23
(3, 22, 'mililitros', 200,  0); -- Suco de laranja - 24

-- ===== UTENSÍLIOS DA RECEITA =====
INSERT INTO utensilio_receita (receita_id, utensilio_id, version) VALUES
(3, 15, 0),  -- Tábua de corte - 15
(3, 16, 0), -- Faca - 20
(3, 17, 0); -- Liquidificador - 21

-- ===== ETAPA 1 DA RECEITA ===== 15
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(3, 1, 'Lave suavemente o espinafre. Pique grosseiramente espinafre, pêra, maçã e banana. Adicione todos os ingredientes ao liquidificador.',
"http://localhost:8080/img/etapa-receita/smoothie-do-despertar-etapa1.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(15, 19, 0), -- Espinafre
(15, 20, 0), -- Pera
(15, 21, 0), -- Maçã
(15, 22, 0); -- Banana

-- ===== ETAPA 2 DA RECEITA ===== 16
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(3, 2, 'Retire os kiwis com uma colher e adicione ao liquidificador.',
"http://localhost:8080/img/etapa-receita/smoothie-do-despertar-etapa2.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(16, 23, 0); -- Kiwi

-- ===== ETAPA 3 DA RECEITA ===== 17
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(3, 3, 'Despeje o suco de laranja.',
"http://localhost:8080/img/etapa-receita/smoothie-do-despertar-etapa3.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(17, 24, 0); -- Suco de laranja

-- ===== ETAPA 4 DA RECEITA ===== 18 
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(3, 4, 'Bata até ficar homogêneo, aproximadamente 2 – 3 min. Pronto para desfrutar imediatamente.',
"http://localhost:8080/img/etapa-receita/smoothie-do-despertar-etapa4.jpg" , 0);



-- ================== LASANHA ITALIANA -- CATEGORIA 1: Massas ==================

-- ===== UTENSÍLIOS  (Tabela geral de utensílios - disponível pra todas as receitas) =====
INSERT INTO utensilio (nome, version) VALUES
-- ('Tábua de corte', 0), -- 15
-- ('Faca', 0), -- 16
('Frigideira grande', 0), -- 18
('Colher de pau', 0); -- 19

-- ===== INGREDIENTES (Tabela geral de ingredientes - disponível pra todas as receitas) =====
INSERT INTO ingrediente (nome, categoria, estimativa_validade, dica_conservacao, version) VALUES
('Cebola', 0, 30, 'Armazenar em local fresco, seco e ventilado, longe da luz direta.', 0), -- 23
('Cenoura', 0, 15, 'Guardar na geladeira em saco plástico perfurado para manter a umidade.', 0), -- 24
('Carne moída', 1, 3, 'Conservar refrigerada e consumir rapidamente ou congelar por até 3 meses.', 0), -- 25
-- ('Azeite'), -- 16
('Pimenta', 0, 180, 'Manter em pote fechado, ao abrigo da luz e umidade.', 0), -- 26
('Alho', 0, 60, 'Guardar em local seco e arejado, longe da luz direta.', 0), -- 27
('Extrato de tomate', 0, 7, 'Após aberto, conservar na geladeira e consumir em até uma semana.', 0), -- 28
-- Manteiga sem sal id 2
-- orégano id 13
-- farinha id 1
-- sal id 8
('Massa de lasanha', 0, 365, 'Armazenar em local seco e fresco, bem fechado.', 0), -- 29
('Queijo Parmesão', 1, 20, 'Conservar refrigerado, bem embalado para evitar ressecamento.', 0), -- 30
('Leite', 1, 7, 'Manter refrigerado e consumir até a data de validade. Após aberto, consumir em até 3 dias.', 1); -- 31

-- ===== DADOS DA RECEITA =====
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, 
imagem_receita, version) VALUES
('Lasanha Italiana', 'Uma lasanha clássica italiana com camadas generosas de molho bolonhesa, béchamel cremoso e crosta dourada de parmesão — perfeita para qualquer ocasião.',
 2400, 'Fácil', 30, 1, 'http://localhost:8080/img/receitas/lasanha-italiana.png', 0);
-- ===== INGREDIENTES DA RECEITA =====
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(4, 23, 'unidade', 1, 0), -- Cebola          - 25
(4, 24, 'unidade', 2, 0), -- Cenoura             - 26
(4, 25, 'gramas', 500, 0), -- Carne moída            - 27
(4, 16, 'pitada', 1, 0), -- Azeite        - 28
(4, 26, 'a gosto', 1,  0), -- Pimenta         - 29
(4, 27, 'dente', 1,  0), -- Alho         - 30
(4, 28, 'gramas', 800,  0), -- Extrato de tomate         - 31
(4, 2, 'gramas', 2,  0), -- Manteiga sem sal         - 32
(4, 13, 'colher de chá', 2,  0), -- Orégano         - 33
(4, 1, 'gramas', 40,  0), -- Farinha         - 34
(4, 29, 'gramas', 150,  0), -- Massa de lasanha         - 35
(4, 8, 'a gosto', 1,  0), -- Sal        - 36
(4, 30, 'gramas', 60,  0), -- Queijo parmesão         - 37
(4, 31, 'mililitros', 500,  0); -- Leite         - 38

-- ===== UTENSÍLIOS DA RECEITA =====
INSERT INTO utensilio_receita (receita_id, utensilio_id, version) VALUES
(4, 15, 0),  -- Tábua de corte - 22
(4, 16, 0), -- Faca - 23
(4, 18, 0), -- Frigideira grande - 24
(4, 19, 0); -- Colher de pau - 25

-- ===== ETAPA 1 DA RECEITA ===== 19
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(4, 1, 'Para a receita de lasanha, descasque e pique finamente o alho.  
Descasque e corte a cebola e as cenouras em cubos. Aqueça um pouco de azeite em uma panela grande 
e frite a cebola, a cenoura e o alho. Adicione a carne picada e doure uniformemente, usando uma 
colher de pau para quebrá-la à medida que avança. Tempere a gosto com sal e pimenta. Em seguida, 
adicione os tomates picados e tempere novamente com sal e pimenta. Tampe e cozinhe em fogo médio por aprox. 
15–20 min. Junte o orégano seco.',
"http://localhost:8080/img/etapa-receita/lasanha-italiana-etapa1.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(19, 30, 0), -- Alho
(19, 25, 0), -- Cebola
(19, 26, 0), -- Cenoura
(19, 27, 0), -- Carne moída
(19, 28, 0), -- Azeite
(19, 31, 0), -- Extrato de tomate
(19, 33, 0), -- Orégano
(19, 36, 0), -- Sal
(19, 29, 0); -- Pimenta

-- ===== ETAPA 2 DA RECEITA ===== 20
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(4, 2, 'Pré-aqueça o forno para a lasanha a 200 ° C / 390 ° F ou 220 ° C / 430 ° F em fogo superior 
/ inferior. Para o molho bechamel, derreta a manteiga em uma panela pequena. Adicione a farinha e 
doure mexendo sempre. Aos poucos, adicione o leite frio, mexendo sempre, depois reduza o fogo e 
cozinhe por aprox. 5–8 min. Tempere a gosto com noz-moscada, sal e pimenta.',
"http://localhost:8080/img/etapa-receita/lasanha-italiana-etapa2.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(20, 32, 0), -- Manteiga sem sal
(20, 34, 0), -- Farinha
(20, 38, 0), -- Leite
(20, 36, 0); -- Sal

-- ===== ETAPA 3 DA RECEITA ===== 21
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(4, 3, 'Cubra a base da caçarola com azeite e depois com uma camada de bechamel. 
Agora coloque as folhas de lasanha primeiro, depois o molho à bolonhesa e o bechamel em sucessão. 
Repita até que todos os ingredientes tenham se esgotado, finalizando com bechamel por cima.',
"http://localhost:8080/img/etapa-receita/lasanha-italiana-etapa3.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(21, 35, 0); -- Massa de lasanha

-- ===== ETAPA 4 DA RECEITA ===== 22
INSERT INTO etapa_receita (receita_id, ordem, conteudo, imagem_etapa, version) VALUES
(4, 4, 'Rale o parmesão sobre a última camada, transfira a lasanha para o forno pré-aquecido na prateleira 
do meio e leve ao forno por aprox. 30–40 min., ou até que o topo esteja dourado. Sirva na assadeira quente.',
"http://localhost:8080/img/etapa-receita/lasanha-italiana-etapa4.jpg" , 0);

INSERT INTO ingrediente_etapa_receita (etapa_receita_id, ingrediente_receita_id, version) VALUES
(22, 37, 0); -- Queijo parmesão
