-- 1. pronome
INSERT INTO pronome (nome, version) VALUES
('Ele/Dele', 0),
('Ela/Dela', 0),
('Elu/Delu', 0),
('Nenhum', 0),
('Personalizado', 0);

-- 3. ingrediente
INSERT INTO ingrediente (nome, categoria, estimativa_validade, dica_conservacao, version) VALUES
('Arroz', 'Grãos', 365, 'Guardar em local seco', 0),
('Feijão', 'Grãos', 365, 'Guardar em local seco', 0),
('Tomate', 'Hortaliça', 7, 'Manter refrigerado', 0),
('Carne Bovina', 'Proteína', 5, 'Manter congelada', 0),
('Queijo', 'Laticínios', 15, 'Manter refrigerado', 0),
('Alface', 'Hortaliça', 5, 'Manter refrigerado em saco perfurado', 0),
('Cenoura', 'Hortaliça', 15, 'Guardar em geladeira sem lavar', 0),
('Batata', 'Hortaliça', 30, 'Guardar em local escuro e seco', 0),
('Maçã', 'Fruta', 20, 'Manter refrigerada', 0),
('Banana', 'Fruta', 7, 'Manter em temperatura ambiente', 0),
('Frango', 'Proteína', 3, 'Manter congelado', 0),
('Leite', 'Laticínios', 7, 'Manter refrigerado', 0),
('Iogurte', 'Laticínios', 10, 'Manter refrigerado', 0),
('Farinha de trigo', 'Grãos', 180, 'Guardar em pote fechado', 0),
('Açúcar', 'Grãos', 365, 'Guardar em local seco', 0),
('Sal', 'Temperos', 730, 'Guardar em pote fechado', 0),
('Óleo de soja', 'Outros', 180, 'Manter em local fresco', 0),
('Azeite de oliva', 'Outros', 365, 'Manter em local escuro', 0),
('Pão', 'Grãos', 5, 'Manter em saco fechado', 0),
('Presunto', 'Proteína', 7, 'Manter refrigerado', 0),
('Peixe', 'Proteína', 2, 'Manter congelado', 0),
('Cebola', 'Hortaliça', 30, 'Guardar em local seco', 0),
('Alho', 'Temperos', 60, 'Guardar em local seco', 0),
('Morango', 'Fruta', 3, 'Manter refrigerado', 0),
('Uva', 'Fruta', 7, 'Manter refrigerada', 0),
('Melancia', 'Fruta', 5, 'Manter refrigerada após cortar', 0),
('Abacaxi', 'Fruta', 7, 'Manter em temperatura ambiente', 0),
('Laranja', 'Fruta', 15, 'Manter refrigerada', 0),
('Limão', 'Fruta', 15, 'Manter refrigerado', 0),
('Couve', 'Hortaliça', 5, 'Manter refrigerada', 0),
('Espinafre', 'Hortaliça', 5, 'Manter refrigerado', 0),
('Repolho', 'Hortaliça', 10, 'Manter refrigerado', 0),
('Abobrinha', 'Hortaliça', 7, 'Manter refrigerada', 0),
('Berinjela', 'Hortaliça', 7, 'Manter refrigerada', 0),
('Pepino', 'Hortaliça', 5, 'Manter refrigerado', 0),
('Pimentão', 'Hortaliça', 7, 'Manter refrigerado', 0),
('Milho', 'Grãos', 180, 'Guardar em pote fechado', 0),
('Aveia', 'Grãos', 180, 'Guardar em pote fechado', 0),
('Granola', 'Grãos', 120, 'Manter em pote fechado', 0),
('Castanha-do-pará', 'Oleaginosas', 180, 'Manter em pote fechado', 0),
('Nozes', 'Oleaginosas', 180, 'Manter em pote fechado', 0),
('Amêndoas', 'Oleaginosas', 180, 'Manter em pote fechado', 0),
('Mel', 'Outros', 365, 'Manter em pote fechado', 0),
('Chocolate', 'Outros', 180, 'Manter em local fresco', 0),
('Café', 'Outros', 365, 'Manter em pote fechado', 0),
('Chá verde', 'Outros', 365, 'Manter em pote fechado', 0),
('Erva-mate', 'Outros', 365, 'Manter em pote fechado', 0),
('Molho de tomate', 'Outros', 10, 'Manter refrigerado após abrir', 0),
('Maionese', 'Outros', 30, 'Manter refrigerada após abrir', 0),
('Mostarda', 'Outros', 60, 'Manter refrigerada após abrir', 0),
('Ketchup', 'Outros', 60, 'Manter refrigerado após abrir', 0),
('Vinagre', 'Outros', 365, 'Manter em local fresco', 0),
('Molho shoyu', 'Outros', 180, 'Manter refrigerado após abrir', 0),
('Tofu', 'Proteína', 7, 'Manter refrigerado', 0),
('Lentilha', 'Grãos', 365, 'Guardar em local seco', 0),
('Grão-de-bico', 'Grãos', 365, 'Guardar em local seco', 0),
('Ricota', 'Laticínios', 7, 'Manter refrigerada', 0),
('Creme de leite', 'Laticínios', 10, 'Manter refrigerado', 0),
('Leite condensado', 'Laticínios', 30, 'Manter refrigerado após abrir', 0),
('Salsicha', 'Proteína', 7, 'Manter refrigerada', 0),
('Linguiça', 'Proteína', 7, 'Manter refrigerada', 0),
('Bacon', 'Proteína', 7, 'Manter refrigerado', 0),
('Ovos', 'Proteína', 15, 'Manter refrigerado', 0),
('Manteiga', 'Laticínios', 30, 'Manter refrigerada', 0),
('Margarina', 'Laticínios', 30, 'Manter refrigerada', 0),
('Polenta', 'Grãos', 180, 'Guardar em pote fechado', 0),
('Tapioca', 'Grãos', 180, 'Guardar em pote fechado', 0),
('Coco ralado', 'Outros', 90, 'Manter refrigerado após abrir', 0),
('Fermento biológico', 'Outros', 60, 'Manter refrigerado', 0),
('Fermento químico', 'Outros', 180, 'Manter em pote fechado', 0),
('Gelatina', 'Outros', 180, 'Manter em local fresco', 0),
('Pó para pudim', 'Outros', 180, 'Manter em local fresco', 0),
('Essência de baunilha', 'Outros', 365, 'Manter em local fresco', 0),
('Canela', 'Temperos', 365, 'Manter em pote fechado', 0),
('Páprica', 'Temperos', 365, 'Manter em pote fechado', 0),
('Orégano', 'Temperos', 365, 'Manter em pote fechado', 0),
('Manjericão', 'Temperos', 365, 'Manter em pote fechado', 0),
('Salsinha', 'Temperos', 5, 'Manter refrigerada', 0),
('Cebolinha', 'Temperos', 5, 'Manter refrigerada', 0),
('Coentro', 'Temperos', 5, 'Manter refrigerado', 0),
('Pimenta-do-reino', 'Temperos', 365, 'Manter em pote fechado', 0),
('Pimenta vermelha', 'Temperos', 30, 'Manter refrigerada', 0),
('Molho barbecue', 'Outros', 60, 'Manter refrigerado após abrir', 0),
('Molho pesto', 'Outros', 15, 'Manter refrigerado', 0),
('Molho branco', 'Outros', 7, 'Manter refrigerado', 0),
('Molho agridoce', 'Outros', 30, 'Manter refrigerado após abrir', 0),
('Molho de alho', 'Outros', 30, 'Manter refrigerado após abrir', 0);

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

-- 5. receita (depende de categoria)
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Macarrão à Bolonhesa', 'Macarrão com molho de carne', 1800, 'Médio', 50, 1, "http://localhost:8080/img/receitas/macarrao-a-bolonhesa.png", 0),
('Bife Acebolado', 'Carne grelhada com cebola', 1200, 'Fácil', 40, 2, "http://localhost:8080/img/receitas/bife-acebolado.png", 0),
('Salada Colorida', 'Mix de vegetais frescos', 600, 'Fácil', 30, 3, "http://localhost:8080/img/receitas/salada-colorida.png", 0),
('Bolo de Chocolate', 'Bolo fofinho de chocolate', 3600, 'Médio', 60, 4, "http://localhost:8080/img/receitas/bolo-de-chocolate.png", 0),
('Suco Natural', 'Suco de frutas frescas', 300, 'Fácil', 20, 5, "http://localhost:8080/img/receitas/suco-natural.png", 0);

-- 1 Massas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Penne ao Pesto', 'Massa com molho de manjericão e parmesão.', 1500, 'Fácil', 30, 1, 'penne_pesto.jpg', 0),
('Espaguete à Carbonara', 'Massa com ovos, queijo e bacon.', 1200, 'Média', 40, 1, 'carbonara.jpg', 0),
('Lasanha de Carne', 'Camadas de massa com carne moída e molho.', 2700, 'Difícil', 70, 1, 'lasanha_carne.jpg', 0),
('Fettuccine Alfredo', 'Massa com molho cremoso de queijo.', 1800, 'Média', 50, 1, 'fettuccine_alfredo.jpg', 0),
('Macarrão ao Alho e Óleo', 'Receita simples com alho dourado.', 900, 'Fácil', 20, 1, 'alho_oleo.jpg', 0),
('Ravioli de Ricota', 'Massa recheada com ricota e espinafre.', 2400, 'Média', 55, 1, 'ravioli_ricota.jpg', 0),
('Talharim com Frutos do Mar', 'Massa com camarões e lula.', 2100, 'Difícil', 65, 1, 'talharim_frutos.jpg', 0),
('Nhoque de Batata', 'Massa leve feita com batata.', 1800, 'Média', 45, 1, 'nhoque.jpg', 0),
('Canelone de Presunto e Queijo', 'Massa recheada e gratinada.', 2400, 'Média', 50, 1, 'canelone.jpg', 0),
('Macarrão de Forno', 'Massa gratinada com molho e queijo.', 2100, 'Fácil', 35, 1, 'macarrao_forno.jpg', 0);

-- 2 Carnes
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Filé Mignon ao Molho Madeira', 'Carne nobre com molho encorpado.', 2400, 'Difícil', 80, 2, 'file_madeira.jpg', 0),
('Costela Assada', 'Costela bovina assada lentamente.', 7200, 'Difícil', 100, 2, 'costela.jpg', 0),
('Frango Grelhado com Ervas', 'Peito de frango com ervas aromáticas.', 1200, 'Fácil', 30, 2, 'frango_ervas.jpg', 0),
('Almôndegas ao Molho', 'Bolinhas de carne com molho de tomate.', 1800, 'Fácil', 35, 2, 'almondengas.jpg', 0),
('Carne de Panela', 'Carne cozida com legumes e caldo.', 3600, 'Média', 60, 2, 'carne_panela.jpg', 0),
('Bife à Parmegiana', 'Carne empanada com molho e queijo.', 2400, 'Média', 55, 2, 'parmegiana.jpg', 0),
('Frango à Passarinho', 'Frango frito com alho e cheiro-verde.', 1500, 'Fácil', 25, 2, 'frango_passarinho.jpg', 0),
('Picadinho de Carne', 'Carne picada com arroz e feijão.', 1800, 'Fácil', 30, 2, 'picadinho.jpg', 0),
('Carne Moída com Batata', 'Refogado simples e saboroso.', 1500, 'Fácil', 25, 2, 'carne_batata.jpg', 0),
('Linguiça na Cerveja', 'Linguiça cozida com cebola e cerveja.', 2100, 'Média', 40, 2, 'linguica_cerveja.jpg', 0);

-- 3 Vegetariano
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Berinjela Recheada', 'Berinjela assada com legumes e queijo.', 1800, 'Média', 45, 3, 'berinjela_recheada.jpg', 0),
('Quibe de Abóbora', 'Quibe assado feito com abóbora e trigo.', 1500, 'Fácil', 30, 3, 'quibe_abobora.jpg', 0),
('Hambúrguer de Grão-de-Bico', 'Hambúrguer vegetariano rico em proteína.', 1800, 'Média', 40, 3, 'hamburguer_graodebico.jpg', 0),
('Escondidinho de Lentilha', 'Purê de batata com recheio de lentilhas.', 2100, 'Média', 50, 3, 'escondidinho_lentilha.jpg', 0),
('Torta de Legumes', 'Torta salgada recheada com vegetais.', 2400, 'Média', 55, 3, 'torta_legumes.jpg', 0),
('Arroz de Couve-Flor', 'Substituto leve para o arroz tradicional.', 900, 'Fácil', 20, 3, 'arroz_couveflor.jpg', 0),
('Omelete de Espinafre', 'Omelete nutritiva com vegetais.', 900, 'Fácil', 20, 3, 'omelete_espinafre.jpg', 0),
('Panqueca de Abobrinha', 'Panqueca leve e saborosa.', 1200, 'Fácil', 25, 3, 'panqueca_abobrinha.jpg', 0),
('Risoto de Legumes', 'Risoto cremoso com vegetais.', 1800, 'Média', 40, 3, 'risoto_legumes.jpg', 0),
('Cuscuz Paulista Vegetariano', 'Versão sem carne do clássico paulista.', 1500, 'Fácil', 30, 3, 'cuscuz_vegetariano.jpg', 0);

-- 4 Sobremesas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Mousse de Maracujá', 'Sobremesa leve e refrescante.', 900, 'Fácil', 25, 4, 'mousse_maracuja.jpg', 0),
('Brigadeiro', 'Doce brasileiro feito com leite condensado.', 600, 'Fácil', 15, 4, 'brigadeiro.jpg', 0),
('Pudim de Leite', 'Sobremesa cremosa com calda de caramelo.', 3600, 'Média', 50, 4, 'pudim.jpg', 0),
('Torta de Limão', 'Base crocante com recheio azedinho.', 2700, 'Difícil', 60, 4, 'torta_limao.jpg', 0),
('Bolo de Cenoura', 'Bolo fofinho com cobertura de chocolate.', 1800, 'Fácil', 30, 4, 'bolo_cenoura.jpg', 0),
('Gelatina Colorida', 'Sobremesa divertida com várias cores.', 1200, 'Fácil', 20, 4, 'gelatina.jpg', 0),
('Sorvete de Banana', 'Sorvete vegano e saudável.', 600, 'Fácil', 15, 4, 'sorvete_banana.jpg', 0),
('Torta de Maçã', 'Clássico americano com maçãs caramelizadas.', 2700, 'Média', 50, 4, 'torta_maca.jpg', 0),
('Brownie de Chocolate', 'Doce denso e cremoso.', 1800, 'Média', 40, 4, 'brownie.jpg', 0),
('Creme de Papaia com Licor', 'Sobremesa tropical com toque alcoólico.', 900, 'Fácil', 25, 4, 'creme_papaia.jpg', 0);

-- 5 Bebidas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Chá Gelado de Hibisco', 'Bebida aromática e antioxidante.', 600, 'Fácil', 15, 5, 'cha_hibisco.jpg', 0),
('Suco Verde', 'Suco detox com couve, limão e gengibre.', 300, 'Fácil', 10, 5, 'suco_verde.jpg', 0),
('Smoothie de Morango', 'Bebida cremosa com morangos e iogurte.', 600, 'Fácil', 15, 5, 'smoothie_morango.jpg', 0),
('Limonada Suíça', 'Suco de limão batido com casca e gelo.', 300, 'Fácil', 10, 5, 'limonada_suica.jpg', 0),
('Chá de Camomila', 'Infusão calmante e digestiva.', 480, 'Fácil', 10, 5, 'cha_camomila.jpg', 0),
('Milkshake de Chocolate', 'Bebida gelada com sorvete e leite.', 600, 'Fácil', 20, 5, 'milkshake_chocolate.jpg', 0),
('Café Gelado', 'Café forte servido com gelo e leite.', 300, 'Fácil', 10, 5, 'cafe_gelado.jpg', 0),
('Suco de Maracujá', 'Refrescante e calmante, feito com polpa natural.', 300, 'Fácil', 10, 5, 'suco_maracuja.jpg', 0),
('Água Saborizada', 'Água com frutas e ervas aromáticas.', 180, 'Fácil', 5, 5, 'agua_saborizada.jpg', 0),
('Chá Preto com Limão', 'Chá encorpado com toque cítrico.', 420, 'Fácil', 10, 5, 'cha_preto_limao.jpg', 0);

-- 8 Saladas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Salada Caesar', 'Alface, croutons e molho especial.', 900, 'Fácil', 20, 8, 'salada_caesar.jpg', 0),
('Salada de Quinoa', 'Quinoa com legumes e molho cítrico.', 1200, 'Fácil', 25, 8, 'salada_quinoa.jpg', 0),
('Salada Caprese', 'Tomate, mussarela e manjericão.', 600, 'Fácil', 15, 8, 'salada_caprese.jpg', 0),
('Salada Grega', 'Tomate, pepino, cebola roxa e queijo feta.', 900, 'Fácil', 20, 8, 'salada_grega.jpg', 0),
('Salada de Frutas', 'Frutas variadas com suco de laranja.', 600, 'Fácil', 15, 8, 'salada_frutas.jpg', 0),
('Salada de Grão-de-Bico', 'Grão-de-bico com legumes e azeite.', 900, 'Fácil', 20, 8, 'salada_graodebico.jpg', 0),
('Salada de Batata', 'Batata cozida com maionese e ervas.', 1200, 'Fácil', 25, 8, 'salada_batata.jpg', 0),
('Salada de Macarrão', 'Macarrão frio com vegetais e molho.', 1500, 'Fácil', 30, 8, 'salada_macarrao.jpg', 0),
('Salada de Lentilha', 'Lentilha cozida com cebola e tomate.', 900, 'Fácil', 20, 8, 'salada_lentilha.jpg', 0),
('Salada Tropical', 'Folhas verdes com manga e castanhas.', 900, 'Fácil', 20, 8, 'salada_tropical.jpg', 0);

-- 9 Entradas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Mini Quiches', 'Tortinhas salgadas com recheios variados.', 1800, 'Média', 40, 9, 'mini_quiches.jpg', 0),
('Bruschetta de Tomate', 'Pão tostado com tomate e manjericão.', 900, 'Fácil', 20, 9, 'bruschetta.jpg', 0),
('Tábua de Frios', 'Seleção de queijos, embutidos e frutas.', 600, 'Fácil', 15, 9, 'tabua_frios.jpg', 0),
('Canapés de Patê', 'Pães com patês variados.', 900, 'Fácil', 20, 9, 'canapes.jpg', 0),
('Rolinho de Abobrinha', 'Abobrinha grelhada recheada com queijo.', 1200, 'Média', 30, 9, 'rolinho_abobrinha.jpg', 0),
('Espetinho Caprese', 'Tomate, queijo e manjericão no palito.', 600, 'Fácil', 15, 9, 'espetinho_caprese.jpg', 0),
('Cestinhas de Massa', 'Massa folhada recheada com creme salgado.', 1500, 'Média', 35, 9, 'cestinhas.jpg', 0),
('Dadinho de Tapioca', 'Cubos crocantes de tapioca com queijo.', 1800, 'Média', 40, 9, 'dadinho_tapioca.jpg', 0),
('Croquete de Carne', 'Bolinho frito recheado com carne moída.', 1500, 'Média', 35, 9, 'croquete.jpg', 0),
('Palitos de Cenoura com Molho', 'Entrada leve com molho de iogurte.', 600, 'Fácil', 10, 9, 'palitos_cenoura.jpg', 0);


-- 6. ingrediente_receita (depende de ingrediente e receita)
INSERT INTO ingrediente_receita (ingrediente_id, receita_id, unidade_medida, quantidade, version) VALUES
(1, 1, 'g', 200, 0),
(4, 2, 'g', 150, 0),
(3, 3, 'un', 2, 0),
(5, 4, 'g', 100, 0),
(2, 1, 'g', 100, 0);

-- 7. adjetivo
INSERT INTO adjetivo (nome, version) VALUES
('Rápido', 0),
('Criativo', 0),
('Organizado', 0),
('Paciente', 0),
('Inovador', 0);

-- 8. titulo
INSERT INTO titulo (nome, condicao_desbloqueio, version) VALUES
('Chef Iniciante', 'Concluir 1 receita', 0),
('Chef Intermediário', 'Concluir 10 receitas', 0),
('Chef Avançado', 'Concluir 50 receitas', 0),
('Mestre da Cozinha', 'Concluir 100 receitas', 0),
('Colecionador de Receitas', 'Ter 20 receitas salvas', 0);

-- 9. etapa_receita (depende de receita)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar o macarrão', 1, 0),
(2, 'Preparar o molho', 1, 0),
(1, 'Grelhar o bife', 2, 0),
(1, 'Misturar vegetais', 3, 0);

-- 10. avatar
INSERT INTO avatar (nome, imagem_url, version) VALUES
('Chef Pão de Queijo', 'http://localhost:8080/img/avatares/paozinho.png', 0),
('Chef Brócolis', 'http://localhost:8080/img/avatares/brocolinho.png', 0),
('Chef Tomate', 'http://localhost:8080/img/avatares/tomatinho.png', 0);

-- 16. utensilio
INSERT INTO utensilio (nome, version) VALUES
('Panela', 0),
('Faca', 0),
('Colher de Pau', 0),
('Batedeira', 0),
('Liquidificador', 0);