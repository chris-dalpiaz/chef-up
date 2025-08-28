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
('Molho de alho', 'Outros', 30, 'Manter refrigerado após abrir', 0),
('Água', 'Outros', 7, 'Manter em recipiente fechado', 0),
('Massa', 'Massas', 30, 'Manter refrigerado', 0),
('Requeijão', 'Laticínios', 15, 'Manter refrigerado após abrir', 0),
('Cream cheese', 'Laticínios', 10, 'Manter refrigerado', 0),
('Molho de pimenta', 'Outros', 180, 'Manter em local fresco', 0),
('Molho de soja', 'Outros', 180, 'Manter refrigerado após abrir', 0),
('Molho de iogurte', 'Outros', 7, 'Manter refrigerado', 0),
('Molho rosé', 'Outros', 7, 'Manter refrigerado', 0),
('Molho curry', 'Outros', 30, 'Manter refrigerado após abrir', 0),
('Molho de ervas', 'Outros', 15, 'Manter refrigerado', 0),
('Molho de queijo', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de carne', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de peixe', 'Outros', 180, 'Manter refrigerado após abrir', 0),
('Molho teriyaki', 'Outros', 180, 'Manter refrigerado após abrir', 0),
('Molho ranch', 'Outros', 30, 'Manter refrigerado após abrir', 0),
('Molho tzatziki', 'Outros', 5, 'Manter refrigerado', 0),
('Molho chimichurri', 'Outros', 30, 'Manter refrigerado após abrir', 0),
('Molho aioli', 'Outros', 7, 'Manter refrigerado', 0),
('Molho tahine', 'Outros', 30, 'Manter refrigerado após abrir', 0),
('Molho de iogurte com hortelã', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de manga', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de maracujá', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de laranja', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de tomate seco', 'Outros', 15, 'Manter refrigerado', 0),
('Molho de cebola caramelizada', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de alho poró', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de abacate', 'Outros', 3, 'Manter refrigerado', 0),
('Molho de iogurte com limão', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de hortelã', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de beterraba', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de cenoura', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de espinafre', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de cogumelos', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de vinho', 'Outros', 30, 'Manter refrigerado após abrir', 0),
('Molho de leite', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de creme de leite', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de leite de coco', 'Outros', 7, 'Manter refrigerado após abrir', 0),
('Molho de curry com coco', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de abóbora', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de ervilha', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de milho', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de queijo gorgonzola', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de queijo cheddar', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de queijo parmesão', 'Outros', 7, 'Manter refrigerado', 0),
('Molho de queijo brie', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de queijo camembert', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de queijo minas', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de queijo prato', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de queijo mussarela', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de queijo coalho', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de queijo suíço', 'Outros', 5, 'Manter refrigerado', 0),
('Molho de queijo provolone', 'Outros', 5, 'Manter refrigerado', 0);

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

INSERT INTO utensilio (nome, version) VALUES
('Panela', 0),
('Faca', 0),
('Colher de Pau', 0),
('Batedeira', 0),
('Liquidificador', 0),
('Espátula', 0),
('Ralador', 0),
('Tábua de Corte', 0),
('Concha', 0),
('Escorredor de Macarrão', 0),
('Frigideira', 0),
('Peneira', 0),
('Abridor de Latas', 0),
('Tesoura de Cozinha', 0),
('Espremedor de Alho', 0),
('Espremedor de Frutas', 0),
('Copo Medidor', 0),
('Forma de Bolo', 0),
('Assadeira', 0),
('Tigela', 0),
('Jarra', 0),
('Coador', 0),
('Moedor de Pimenta', 0),
('Moedor de Sal', 0),
('Termômetro de Cozinha', 0),
('Timer de Cozinha', 0),
('Balança Digital', 0),
('Espeto', 0),
('Pegador de Massa', 0),
('Pegador de Salada', 0),
('Fouet (Batedor de Arame)', 0),
('Cortador de Pizza', 0),
('Cortador de Legumes', 0),
('Descascador', 0),
('Forma de Muffin', 0),
('Forma de Pão', 0),
('Pincel de Silicone', 0),
('Funil', 0),
('Saca-rolhas', 0),
('Espátula de Silicone', 0),
('Colher Medidora', 0),
('Espremedor de Batata', 0),
('Cesto de Fritura', 0),
('Panela de Pressão', 0),
('Panela Wok', 0),
('Panela de Ferro', 0),
('Panela de Barro', 0),
('Grill Elétrico', 0),
('Sanduicheira', 0),
('Mixer', 0);

-- Receitas:
-- 1 Massas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Macarrão à Bolonhesa', 'Macarrão com molho de carne', 1800, 'Médio', 50, 1, "http://localhost:8080/img/receitas/macarrao-a-bolonhesa.png", 0),
('Penne ao Pesto', 'Massa com molho de manjericão e parmesão.', 1500, 'Fácil', 30, 1, 'http://localhost:8080/img/receitas/penne-pesto.jpg', 0),
('Espaguete à Carbonara', 'Massa com ovos, queijo e bacon.', 1200, 'Média', 40, 1, 'http://localhost:8080/img/receitas/carbonara.jpg', 0),
('Lasanha de Carne', 'Camadas de massa com carne moída e molho.', 2700, 'Difícil', 70, 1, 'http://localhost:8080/img/receitas/lasanha-carne.jpg', 0),
('Fettuccine Alfredo', 'Massa com molho cremoso de queijo.', 1800, 'Média', 50, 1, 'http://localhost:8080/img/receitas/fettuccine-alfredo.jpg', 0),
('Macarrão ao Alho e Óleo', 'Receita simples com alho dourado.', 900, 'Fácil', 20, 1, 'http://localhost:8080/img/receitas/alho-oleo.jpg', 0),
('Ravioli de Ricota', 'Massa recheada com ricota e espinafre.', 2400, 'Média', 55, 1, 'http://localhost:8080/img/receitas/ravioli-ricota.jpg', 0),
('Talharim com Frutos do Mar', 'Massa com camarões e lula.', 2100, 'Difícil', 65, 1, 'http://localhost:8080/img/receitas/talharim-frutos.jpg', 0),
('Nhoque de Batata', 'Massa leve feita com batata.', 1800, 'Média', 45, 1, 'http://localhost:8080/img/receitas/nhoque-batata.jpg', 0),
('Canelone de Presunto e Queijo', 'Massa recheada e gratinada.', 2400, 'Média', 50, 1, 'http://localhost:8080/img/receitas/canelone.jpg', 0),
('Macarrão de Forno', 'Massa gratinada com molho e queijo.', 2100, 'Fácil', 35, 1, 'http://localhost:8080/img/receitas/macarrao_forno.jpg', 0);

-- 2 Carnes
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Bife Acebolado', 'Carne grelhada com cebola', 1200, 'Fácil', 40, 2, "http://localhost:8080/img/receitas/bife-acebolado.png", 0),
('Filé Mignon ao Molho Madeira', 'Carne nobre com molho encorpado.', 2400, 'Difícil', 80, 2, 'http://localhost:8080/img/receitas/file-madeira.jpg', 0),
('Costela Assada', 'Costela bovina assada lentamente.', 7200, 'Difícil', 100, 2, 'http://localhost:8080/img/receitas/costela.jpg', 0),
('Frango Grelhado com Ervas', 'Peito de frango com ervas aromáticas.', 1200, 'Fácil', 30, 2, 'http://localhost:8080/img/receitas/frango-ervas.jpg', 0),
('Almôndegas ao Molho', 'Bolinhas de carne com molho de tomate.', 1800, 'Fácil', 35, 2, 'http://localhost:8080/img/receitas/almondengas.jpg', 0),
('Carne de Panela', 'Carne cozida com legumes e caldo.', 3600, 'Média', 60, 2, 'http://localhost:8080/img/receitas/carne-panela.jpg', 0),
('Bife à Parmegiana', 'Carne empanada com molho e queijo.', 2400, 'Média', 55, 2, 'http://localhost:8080/img/receitas/parmegiana.jpg', 0),
('Frango à Passarinho', 'Frango frito com alho e cheiro-verde.', 1500, 'Fácil', 25, 2, 'http://localhost:8080/img/receitas/frango-passarinho.jpg', 0),
('Picadinho de Carne', 'Carne picada com arroz e feijão.', 1800, 'Fácil', 30, 2, 'http://localhost:8080/img/receitas/picadinho.jpg', 0),
('Carne Moída com Batata', 'Refogado simples e saboroso.', 1500, 'Fácil', 25, 2, 'http://localhost:8080/img/receitas/carne-batata.jpg', 0),
('Linguiça na Cerveja', 'Linguiça cozida com cebola e cerveja.', 2100, 'Média', 40, 2, 'http://localhost:8080/img/receitas/linguica-cerveja.jpg', 0);

-- 3 Vegetariano
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Berinjela Recheada', 'Berinjela assada com legumes e queijo.', 1800, 'Média', 45, 3, 'http://localhost:8080/img/receitas/berinjela-recheada.jpg', 0),
('Quibe de Abóbora', 'Quibe assado feito com abóbora e trigo.', 1500, 'Fácil', 30, 3, 'http://localhost:8080/img/receitas/quibe-abobora.jpg', 0),
('Hambúrguer de Grão-de-Bico', 'Hambúrguer vegetariano rico em proteína.', 1800, 'Média', 40, 3, 'http://localhost:8080/img/receitas/hamburguer-graodebico.jpg', 0),
('Escondidinho de Lentilha', 'Purê de batata com recheio de lentilhas.', 2100, 'Média', 50, 3, 'http://localhost:8080/img/receitas/escondidinho-lentilha.jpg', 0),
('Torta de Legumes', 'Torta salgada recheada com vegetais.', 2400, 'Média', 55, 3, 'http://localhost:8080/img/receitas/torta-legumes.jpg', 0),
('Arroz de Couve-Flor', 'Substituto leve para o arroz tradicional.', 900, 'Fácil', 20, 3, 'http://localhost:8080/img/receitas/arroz-couveflor.jpg', 0),
('Omelete de Espinafre', 'Omelete nutritiva com vegetais.', 900, 'Fácil', 20, 3, 'http://localhost:8080/img/receitas/omelete-espinafre.jpg', 0),
('Panqueca de Abobrinha', 'Panqueca leve e saborosa.', 1200, 'Fácil', 25, 3, 'http://localhost:8080/img/receitas/panqueca-abobrinha.jpg', 0),
('Risoto de Legumes', 'Risoto cremoso com vegetais.', 1800, 'Média', 40, 3, 'http://localhost:8080/img/receitas/risoto-legumes.jpg', 0),
('Cuscuz Paulista Vegetariano', 'Versão sem carne do clássico paulista.', 1500, 'Fácil', 30, 3, 'http://localhost:8080/img/receitas/cuscuz-vegetariano.jpg', 0);

-- 4 Sobremesas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Bolo de Chocolate', 'Bolo fofinho de chocolate', 3600, 'Médio', 60, 4, "http://localhost:8080/img/receitas/bolo-de-chocolate.png", 0),
('Mousse de Maracujá', 'Sobremesa leve e refrescante.', 900, 'Fácil', 25, 4, 'http://localhost:8080/img/receitas/mousse-maracuja.jpg', 0),
('Brigadeiro', 'Doce brasileiro feito com leite condensado.', 600, 'Fácil', 15, 4, 'http://localhost:8080/img/receitas/brigadeiro.jpg', 0);

-- 5 Bebidas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Suco Natural', 'Suco de frutas frescas', 300, 'Fácil', 20, 5, "http://localhost:8080/img/receitas/suco-natural.png", 0),
('Chá Gelado de Hibisco', 'Bebida aromática e antioxidante.', 600, 'Fácil', 15, 5, 'http://localhost:8080/img/receitas/cha-hibisco.jpg', 0),
('Suco Verde', 'Suco detox com couve, limão e gengibre.', 300, 'Fácil', 10, 5, 'http://localhost:8080/img/receitas/suco-verde.jpg', 0);

-- 8 Saladas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Salada Colorida', 'Mix de vegetais frescos', 600, 'Fácil', 30, 3, "http://localhost:8080/img/receitas/salada-colorida.png", 0),
('Salada Caesar', 'Alface, croutons e molho especial.', 900, 'Fácil', 20, 8, 'http://localhost:8080/img/receitas/salada-caesar.jpg', 0),
('Salada de Quinoa', 'Quinoa com legumes e molho cítrico.', 1200, 'Fácil', 25, 8, 'http://localhost:8080/img/receitas/salada-quinoa.jpg', 0);

-- 9 Entradas
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Mini Quiches', 'Tortinhas salgadas com recheios variados.', 1800, 'Média', 40, 9, 'http://localhost:8080/img/receitas/mini-quiches.jpg', 0),
('Bruschetta de Tomate', 'Pão tostado com tomate e manjericão.', 900, 'Fácil', 20, 9, 'http://localhost:8080/img/receitas/bruschetta.jpg', 0);

-- Macarrão à Bolonhesa (receita_id = 1)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(1, 89, 'g', 200, 0), -- Massa
(1, 4, 'g', 150, 0), -- Carne Bovina
(1, 49, 'g', 100, 0); -- Molho de tomate

-- Penne ao Pesto (receita_id = 2)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
 (2, 89, 'g', 200, 0), -- Massa
(2, 18, 'ml', 30, 0); -- Azeite de oliva

-- Espaguete à Carbonara (receita_id = 3)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(3, 89, 'g', 200, 0), -- Massa
(3, 64, 'g', 50, 0), -- Bacon
(3, 61, 'un', 2, 0); -- Ovos

-- Lasanha de Carne (receita_id = 4)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(4, 89, 'g', 250, 0), -- Massa
(4, 4, 'g', 200, 0), -- Carne Bovina
(4, 49, 'g', 100, 0); -- Molho de tomate

-- Fettuccine Alfredo (receita_id = 5)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
 (5, 89, 'g', 200, 0), -- Massa
(5, 5, 'g', 100, 0), -- Queijo
(5, 60, 'ml', 50, 0); -- Creme de leite

-- Macarrão ao Alho e Óleo (receita_id = 6)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
 (6, 89, 'g', 200, 0), -- Massa
(6, 23, 'g', 10, 0), -- Alho
(6, 17, 'ml', 30, 0); -- Óleo de soja

-- Ravioli de Ricota (receita_id = 7)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(7, 89, 'g', 200, 0),   -- Massa
(7, 58, 'g', 100, 0),   -- Ricota
(7, 30, 'g', 50, 0);    -- Espinafre

-- Talharim com Frutos do Mar (receita_id = 8)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(8, 89, 'g', 200, 0), -- Massa
(8, 21, 'g', 150, 0), -- Peixe
(8, 18, 'ml', 30, 0); -- Azeite de oliva

-- Nhoque de Batata (receita_id = 9)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(9, 8, 'g', 200, 0), -- Batata
(9, 14, 'g', 100, 0), -- Farinha de trigo
(9, 61, 'un', 1, 0); -- Ovo

-- Canelone de Presunto e Queijo (receita_id = 10)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(10, 89, 'g', 200, 0), -- Massa
(10, 20, 'g', 100, 0), -- Presunto
(10, 5, 'g', 100, 0); -- Queijo

-- Macarrão de Forno (receita_id = 11)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(11, 89, 'g', 200, 0), -- Massa
(11, 49, 'g', 100, 0), -- Molho de tomate
(11, 5, 'g', 80, 0); -- Queijo

-- Bife Acebolado (receita_id = 12)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(12, 4, 'g', 200, 0), -- Carne Bovina
(12, 22, 'g', 50, 0), -- Cebola
(12, 17, 'ml', 20, 0); -- Óleo de soja

-- Filé Mignon ao Molho Madeira (receita_id = 13)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(13, 4, 'g', 250, 0), -- Carne Bovina
(13, 49, 'g', 100, 0), -- Molho de tomate
(13, 18, 'ml', 30, 0); -- Azeite de oliva

-- Costela Assada (receita_id = 14)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(14, 4, 'g', 500, 0), -- Carne Bovina
(14, 22, 'g', 50, 0), -- Cebola
(14, 76, 'g', 5, 0); -- Manjericão

-- Frango Grelhado com Ervas (receita_id = 15)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(15, 11, 'g', 200, 0), -- Frango
(15, 76, 'g', 5, 0), -- Manjericão
(15, 74, 'g', 5, 0); -- Salsinha

-- Almôndegas ao Molho (receita_id = 16)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(16, 4, 'g', 200, 0), -- Carne Bovina
(16, 49, 'g', 100, 0), -- Molho de tomate
(16, 61, 'un', 1, 0); -- Ovo

-- Carne de Panela (receita_id = 17)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(17, 4, 'g', 300, 0), -- Carne Bovina
(17, 22, 'g', 50, 0), -- Cebola
(17, 7, 'g', 50, 0), -- Cenoura
(17, 8, 'g', 100, 0); -- Batata

-- Bife à Parmegiana (receita_id = 18)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(18, 4, 'g', 200, 0), -- Carne Bovina
(18, 49, 'g', 80, 0), -- Molho de tomate
(18, 5, 'g', 50, 0), -- Queijo
(18, 61, 'un', 1, 0); -- Ovo

-- Frango à Passarinho (receita_id = 19)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(19, 11, 'g', 250, 0), -- Frango
(19, 22, 'g', 30, 0), -- Cebola
(19, 74, 'g', 5, 0), -- Salsinha
(19, 23, 'g', 10, 0); -- Alho

-- Picadinho de Carne (receita_id = 20)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(20, 4, 'g', 200, 0), -- Carne Bovina
(20, 1, 'g', 100, 0), -- Arroz
(20, 2, 'g', 100, 0); -- Feijão

-- Carne Moída com Batata (receita_id = 21)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(21, 4, 'g', 200, 0),   -- Carne Bovina
(21, 8, 'g', 150, 0),   -- Batata
(21, 22, 'g', 30, 0);   -- Cebola

-- Linguiça na Cerveja (receita_id = 22)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(22, 66, 'g', 200, 0),  -- Linguiça
(22, 22, 'g', 50, 0),   -- Cebola
(22, 17, 'ml', 20, 0);  -- Óleo de soja

-- Berinjela Recheada (receita_id = 23)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(23, 34, 'g', 200, 0),  -- Berinjela
(23, 7,  'g', 50, 0),   -- Cenoura
(23, 22, 'g', 30, 0),   -- Cebola
(23, 5,  'g', 50, 0);   -- Queijo

-- Quibe de Abóbora (receita_id = 24)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(24, 8,  'g', 100, 0),  -- Batata (substituindo abóbora)
(24, 14, 'g', 100, 0),  -- Farinha de trigo
(24, 61, 'un', 1, 0);   -- Ovo

-- Hambúrguer de Grão-de-Bico (receita_id = 25)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(25, 57, 'g', 150, 0),  -- Grão-de-bico
(25, 22, 'g', 30, 0),   -- Cebola
(25, 74, 'g', 5, 0);    -- Salsinha

-- Escondidinho de Lentilha (receita_id = 26)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(26, 8,  'g', 150, 0), -- Batata
(26, 56, 'g', 100, 0),     -- Lentilha
(26, 22, 'g', 30, 0);      -- Cebola

-- Torta de Legumes (receita_id = 27)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(27, 7,  'g', 50, 0),  -- Cenoura
(27, 35, 'g', 50, 0),      -- Pepino
(27, 33, 'g', 50, 0),      -- Abobrinha
(27, 14, 'g', 100, 0),     -- Farinha de trigo
(27, 61, 'un', 2, 0);      -- Ovos

-- Arroz de Couve-Flor (receita_id = 28)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(28, 29, 'g', 150, 0), -- Couve (substituindo couve-flor)
(28, 22, 'g', 20, 0),      -- Cebola
(28, 17, 'ml', 10, 0);     -- Óleo de soja

-- Omelete de Espinafre (receita_id = 29)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(29, 30, 'g', 50, 0),  -- Espinafre
(29, 61, 'un', 2, 0),      -- Ovos
(29, 5,  'g', 30, 0);      -- Queijo

-- Panqueca de Abobrinha (receita_id = 30)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(30, 33, 'g', 100, 0), -- Abobrinha
(30, 14, 'g', 100, 0),     -- Farinha de trigo
(30, 61, 'un', 1, 0);      -- Ovo

-- Risoto de Legumes (receita_id = 31)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(31, 1,  'g', 150, 0), -- Arroz
(31, 7,  'g', 50, 0),      -- Cenoura
(31, 22, 'g', 30, 0),      -- Cebola
(31, 5,  'g', 50, 0);      -- Queijo

-- Cuscuz Paulista Vegetariano (receita_id = 32)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(32, 1,  'g', 100, 0), -- Arroz (substituindo farinha de milho)
(32, 22, 'g', 30, 0),      -- Cebola
(32, 33, 'g', 50, 0),      -- Abobrinha
(32, 74, 'g', 5, 0);       -- Salsinha

-- Bolo de Chocolate (receita_id = 33)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(33, 14, 'g', 200, 0), -- Farinha de trigo
(33, 61, 'un', 2, 0),  -- Ovos
(33, 62, 'g', 395, 0), -- Leite condensado
(33, 44, 'g', 100, 0); -- Chocolate

-- Mousse de Maracujá (receita_id = 34)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(34, 60, 'ml', 200, 0), -- Creme de leite
(34, 62, 'g', 395, 0),  -- Leite condensado
(34, 69, 'g', 12, 0);   -- Gelatina

-- Brigadeiro (receita_id = 35)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(35, 62, 'g', 395, 0), -- Leite condensado
(35, 44, 'g', 100, 0), -- Chocolate
(35, 17, 'g', 10, 0);  -- Óleo de soja (para untar)
-- Suco Natural (receita_id = 36)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(36, 9, 'g', 100, 0),   -- Maçã
(36, 10, 'g', 100, 0),  -- Banana
(36, 28, 'ml', 50, 0);  -- Limão

-- Chá Gelado de Hibisco (receita_id = 37)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(37, 72, 'g', 10, 0),   -- Chá verde (substituindo hibisco)
(37, 86, 'ml', 300, 0); -- Água (representado por café ou chá base)

-- Suco Verde (receita_id = 38)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(38, 29, 'g', 50, 0),   -- Couve
(38, 28, 'ml', 30, 0),  -- Limão
(38, 22, 'g', 10, 0);   -- Cebola (substituindo gengibre)

-- Salada Colorida (receita_id = 39)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(39, 6, 'g', 50, 0),    -- Alface
(39, 7, 'g', 30, 0),    -- Cenoura
(39, 33, 'g', 30, 0),   -- Abobrinha
(39, 74, 'g', 5, 0);    -- Salsinha

-- Salada Caesar (receita_id = 40)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(40, 6, 'g', 50, 0),    -- Alface
(40, 5, 'g', 30, 0),    -- Queijo
(40, 49, 'g', 20, 0);   -- Maionese (para molho)

-- Salada de Quinoa (receita_id = 41)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(41, 33, 'g', 30, 0),   -- Abobrinha
(41, 7, 'g', 30, 0),    -- Cenoura
(41, 28, 'ml', 20, 0),  -- Limão
(41, 91, 'g', 5, 0);    -- Salsinha

-- Mini Quiches (receita_id = 42)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(42, 14, 'g', 100, 0),  -- Farinha de trigo
(42, 61, 'un', 2, 0),   -- Ovos
(42, 5, 'g', 50, 0),    -- Queijo
(42, 22, 'g', 30, 0);   -- Cebola

-- Bruschetta de Tomate (receita_id = 43)
INSERT INTO ingrediente_receita (receita_id, ingrediente_id, unidade_medida, quantidade, version) VALUES
(43, 3, 'g', 100, 0),   -- Tomate
(43, 90, 'g', 5, 0),    -- Manjericão
(43, 18, 'ml', 20, 0);  -- Azeite de oliva

-- 9. etapas da receita (depende de receita)
-- 1. Macarrão à Bolonhesa
INSERT INTO etapa_receita (ordem, conteudo, receita_id, imagem_etapa, version) VALUES
(1, 'Cozinhar o macarrão até ficar al dente.', 1, "http://localhost:8080/img/etapa-receita/alho-oleo.jpg" , 0);

INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(2, 'Refogar a carne moída com alho e cebola.', 1, 0),
(3, 'Adicionar o molho de tomate e cozinhar por 10 minutos.', 1, 0),
(4, 'Misturar o molho ao macarrão e servir.', 1, 0);

-- 2. Penne ao Pesto
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar o penne em água fervente com sal.', 2, 0),
(2, 'Preparar o molho pesto com manjericão, azeite e queijo.', 2, 0),
(3, 'Misturar o molho ao penne e servir.', 2, 0);

-- 3. Espaguete à Carbonara
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar o espaguete até ficar al dente.', 3, 0),
(2, 'Fritar o bacon até dourar.', 3, 0),
(3, 'Misturar ovos batidos com queijo ralado.', 3, 0),
(4, 'Juntar tudo ao macarrão quente e servir.', 3, 0);

-- 4. Lasanha de Carne
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Preparar o molho de carne com tomate.', 4, 0),
(2, 'Cozinhar as folhas de lasanha.', 4, 0),
(3, 'Montar camadas de massa, carne e molho.', 4, 0),
(4, 'Levar ao forno por 30 minutos.', 4, 0);

-- 5. Fettuccine Alfredo
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar o fettuccine até ficar al dente.', 5, 0),
(2, 'Derreter manteiga e adicionar creme de leite.', 5, 0),
(3, 'Misturar queijo ralado até formar molho cremoso.', 5, 0),
(4, 'Juntar o molho à massa e servir.', 5, 0);

-- 6. Macarrão ao Alho e Óleo
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar o macarrão em água com sal.', 6, 0),
(2, 'Dourar o alho no óleo.', 6, 0),
(3, 'Misturar o alho ao macarrão e servir.', 6, 0);

-- 7. Ravioli de Ricota
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Preparar o recheio com ricota e espinafre.', 7, 0),
(2, 'Rechear os raviolis e fechar bem.', 7, 0),
(3, 'Cozinhar os raviolis em água fervente.', 7, 0),
(4, 'Servir com molho de sua preferência.', 7, 0);

-- 8. Talharim com Frutos do Mar
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar o talharim até ficar al dente.', 8, 0),
(2, 'Refogar os frutos do mar com alho e azeite.', 8, 0),
(3, 'Adicionar molho e cozinhar por 5 minutos.', 8, 0),
(4, 'Misturar ao talharim e servir.', 8, 0);

-- 9. Nhoque de Batata
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar e amassar as batatas.', 9, 0),
(2, 'Misturar com farinha e ovo até formar massa.', 9, 0),
(3, 'Modelar os nhoques e cozinhar em água fervente.', 9, 0),
(4, 'Servir com molho de sua escolha.', 9, 0);

-- 10. Canelone de Presunto e Queijo
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Rechear a massa com presunto e queijo.', 10, 0),
(2, 'Enrolar os canelones e colocar em refratário.', 10, 0),
(3, 'Cobrir com molho e queijo ralado.', 10, 0),
(4, 'Levar ao forno para gratinar.', 10, 0);

-- 11. Macarrão de Forno
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar o macarrão até ficar al dente.', 11, 0),
(2, 'Misturar com molho de tomate e queijo.', 11, 0),
(3, 'Colocar em refratário e cobrir com mais queijo.', 11, 0),
(4, 'Levar ao forno por 20 minutos.', 11, 0);

-- Bife Acebolado (receita_id = 12)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Temperar os bifes com sal e pimenta.', 12, 0),
(2, 'Aquecer a frigideira com óleo e grelhar os bifes.', 12, 0),
(3, 'Adicionar a cebola fatiada e refogar até dourar.', 12, 0),
(4, 'Servir os bifes com a cebola por cima.', 12, 0);

-- Filé Mignon ao Molho Madeira (receita_id = 13)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Temperar os filés com sal e pimenta.', 13, 0),
(2, 'Selar os filés em frigideira quente com azeite.', 13, 0),
(3, 'Preparar o molho com vinho, molho de tomate e temperos.', 13, 0),
(4, 'Cozinhar os filés no molho por alguns minutos.', 13, 0),
(5, 'Servir com acompanhamento de sua preferência.', 13, 0);

-- Costela Assada (receita_id = 14)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Temperar a costela com sal, alho e ervas.', 14, 0),
(2, 'Deixar marinando por pelo menos 2 horas.', 14, 0),
(3, 'Levar ao forno coberta com papel alumínio por 4 horas.', 14, 0),
(4, 'Retirar o papel e assar por mais 1 hora para dourar.', 14, 0);

-- Frango Grelhado com Ervas (receita_id = 15)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Temperar o frango com sal, alho e ervas.', 15, 0),
(2, 'Aquecer a frigideira com azeite.', 15, 0),
(3, 'Grelhar o frango até dourar dos dois lados.', 15, 0),
(4, 'Servir com salada ou arroz.', 15, 0);

-- Almôndegas ao Molho (receita_id = 16)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Misturar carne moída com ovo e temperos.', 16, 0),
(2, 'Modelar bolinhas e dourar em frigideira.', 16, 0),
(3, 'Adicionar molho de tomate e cozinhar por 10 minutos.', 16, 0),
(4, 'Servir com arroz ou massa.', 16, 0);

-- Carne de Panela (receita_id = 17)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cortar a carne em cubos e temperar.', 17, 0),
(2, 'Refogar a carne com cebola e alho.', 17, 0),
(3, 'Adicionar legumes e água ou caldo.', 17, 0),
(4, 'Cozinhar em fogo baixo por 1 hora ou até amaciar.', 17, 0);

-- Bife à Parmegiana (receita_id = 18)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Temperar os bifes e empanar com ovo e farinha.', 18, 0),
(2, 'Fritar os bifes até dourar.', 18, 0),
(3, 'Cobrir com molho de tomate e queijo.', 18, 0),
(4, 'Levar ao forno para gratinar.', 18, 0);

-- Frango à Passarinho (receita_id = 19)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cortar o frango em pedaços pequenos.', 19, 0),
(2, 'Temperar com alho, sal e cheiro-verde.', 19, 0),
(3, 'Fritar em óleo quente até dourar.', 19, 0),
(4, 'Escorrer e servir com limão.', 19, 0);

-- Picadinho de Carne (receita_id = 20)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cortar a carne em cubos pequenos.', 20, 0),
(2, 'Refogar com cebola e alho.', 20, 0),
(3, 'Servir com arroz e feijão.', 20, 0);

-- Carne Moída com Batata (receita_id = 21)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Refogar a carne moída com cebola.', 21, 0),
(2, 'Adicionar batatas em cubos e temperar.', 21, 0),
(3, 'Cozinhar até as batatas ficarem macias.', 21, 0),
(4, 'Servir com arroz ou pão.', 21, 0);

-- Linguiça na Cerveja (receita_id = 22)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cortar a linguiça em pedaços.', 22, 0),
(2, 'Refogar com cebola até dourar.', 22, 0),
(3, 'Adicionar cerveja e cozinhar até reduzir.', 22, 0),
(4, 'Servir quente com pão ou arroz.', 22, 0);

-- Berinjela Recheada (receita_id = 23)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cortar as berinjelas ao meio e retirar parte da polpa.', 23, 0),
(2, 'Refogar os legumes picados com cebola e temperos.', 23, 0),
(3, 'Rechear as berinjelas com o refogado e cobrir com queijo.', 23, 0),
(4, 'Levar ao forno até dourar.', 23, 0);

-- Quibe de Abóbora (receita_id = 24)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar a abóbora e amassar até virar purê.', 24, 0),
(2, 'Misturar com trigo, ovo e temperos.', 24, 0),
(3, 'Modelar em forma de quibe e colocar em assadeira.', 24, 0),
(4, 'Assar até dourar.', 24, 0);

-- Hambúrguer de Grão-de-Bico (receita_id = 25)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar o grão-de-bico até ficar macio.', 25, 0),
(2, 'Processar com cebola e temperos até formar uma massa.', 25, 0),
(3, 'Modelar os hambúrgueres e grelhar em frigideira.', 25, 0),
(4, 'Servir com pão ou salada.', 25, 0);

-- Escondidinho de Lentilha (receita_id = 26)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar as lentilhas com cebola e temperos.', 26, 0),
(2, 'Preparar o purê de batata.', 26, 0),
(3, 'Montar em camadas: purê, lentilha e purê.', 26, 0),
(4, 'Levar ao forno para gratinar.', 26, 0);

-- Torta de Legumes (receita_id = 27)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Refogar os legumes picados com temperos.', 27, 0),
(2, 'Preparar a massa com farinha, ovos e leite.', 27, 0),
(3, 'Misturar os legumes à massa.', 27, 0),
(4, 'Despejar em assadeira untada e assar até dourar.', 27, 0);

-- Arroz de Couve-Flor (receita_id = 28)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Processar a couve-flor até ficar com textura de arroz.', 28, 0),
(2, 'Refogar cebola e alho em óleo.', 28, 0),
(3, 'Adicionar a couve-flor e cozinhar por alguns minutos.', 28, 0),
(4, 'Servir como acompanhamento.', 28, 0);

-- Omelete de Espinafre (receita_id = 29)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Bater os ovos com sal e queijo.', 29, 0),
(2, 'Adicionar o espinafre picado à mistura.', 29, 0),
(3, 'Despejar em frigideira quente e cozinhar até firmar.', 29, 0),
(4, 'Virar e dourar do outro lado.', 29, 0);

-- Panqueca de Abobrinha (receita_id = 30)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Ralar a abobrinha e misturar com farinha e ovo.', 30, 0),
(2, 'Temperar a massa e deixar descansar por 10 minutos.', 30, 0),
(3, 'Fritar porções em frigideira até dourar dos dois lados.', 30, 0),
(4, 'Servir com molho ou salada.', 30, 0);

-- Risoto de Legumes (receita_id = 31)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Refogar cebola e legumes picados.', 31, 0),
(2, 'Adicionar o arroz e mexer bem.', 31, 0),
(3, 'Acrescentar água quente aos poucos, mexendo sempre.', 31, 0),
(4, 'Finalizar com queijo e servir cremoso.', 31, 0);

-- Cuscuz Paulista Vegetariano (receita_id = 32)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Refogar os legumes com cebola e temperos.', 32, 0),
(2, 'Adicionar o arroz e misturar bem.', 32, 0),
(3, 'Colocar em forma untada e pressionar levemente.', 32, 0),
(4, 'Levar à geladeira por 30 minutos antes de servir.', 32, 0);

-- Bolo de Chocolate (receita_id = 33)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Misturar os ingredientes secos em uma tigela.', 33, 0),
(2, 'Adicionar ovos, leite condensado e bater até formar massa homogênea.', 33, 0),
(3, 'Despejar a massa em forma untada.', 33, 0),
(4, 'Assar em forno médio por cerca de 40 minutos.', 33, 0);

-- Mousse de Maracujá (receita_id = 34)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Bater o creme de leite, leite condensado e suco de maracujá no liquidificador.', 34, 0),
(2, 'Adicionar gelatina dissolvida e bater novamente.', 34, 0),
(3, 'Levar à geladeira por pelo menos 2 horas antes de servir.', 34, 0);

-- Brigadeiro (receita_id = 35)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Misturar leite condensado e chocolate em uma panela.', 35, 0),
(2, 'Cozinhar em fogo baixo, mexendo sempre até desgrudar do fundo.', 35, 0),
(3, 'Deixar esfriar e enrolar em bolinhas, se desejar.', 35, 0);
-- Suco Natural (receita_id = 36)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Lavar bem as frutas.', 36, 0),
(2, 'Cortar em pedaços e retirar sementes se necessário.', 36, 0),
(3, 'Bater tudo no liquidificador com água gelada.', 36, 0),
(4, 'Coar e servir imediatamente.', 36, 0);

-- Chá Gelado de Hibisco (receita_id = 37)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Ferver a água em uma panela.', 37, 0),
(2, 'Adicionar o chá e deixar em infusão por 5 minutos.', 37, 0),
(3, 'Coar e deixar esfriar.', 37, 0),
(4, 'Servir com gelo e adoçante se desejar.', 37, 0);

-- Suco Verde (receita_id = 38)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Lavar bem os ingredientes.', 38, 0),
(2, 'Cortar a couve e o limão em pedaços.', 38, 0),
(3, 'Bater tudo no liquidificador com água gelada.', 38, 0),
(4, 'Coar e servir imediatamente.', 38, 0);

-- Salada Colorida (receita_id = 39)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Lavar e cortar todos os vegetais.', 39, 0),
(2, 'Misturar em uma tigela grande.', 39, 0),
(3, 'Temperar com azeite, sal e limão.', 39, 0),
(4, 'Servir fresca.', 39, 0);

-- Salada Caesar (receita_id = 40)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Lavar e rasgar as folhas de alface.', 40, 0),
(2, 'Preparar o molho com maionese, queijo e temperos.', 40, 0),
(3, 'Misturar o molho à salada e adicionar croutons.', 40, 0),
(4, 'Servir imediatamente.', 40, 0);

-- Salada de Quinoa (receita_id = 41)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cozinhar a quinoa em água fervente por 15 minutos.', 41, 0),
(2, 'Escorrer e deixar esfriar.', 41, 0),
(3, 'Misturar com legumes picados e temperos.', 41, 0),
(4, 'Servir fria ou em temperatura ambiente.', 41, 0);

-- Mini Quiches (receita_id = 42)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Preparar a massa com farinha, ovos e manteiga.', 42, 0),
(2, 'Forrar forminhas com a massa.', 42, 0),
(3, 'Misturar os ingredientes do recheio.', 42, 0),
(4, 'Despejar o recheio nas forminhas e assar por 25 minutos.', 42, 0);

-- Bruschetta de Tomate (receita_id = 43)
INSERT INTO etapa_receita (ordem, conteudo, receita_id, version) VALUES
(1, 'Cortar o pão em fatias e levar ao forno para tostar.', 43, 0),
(2, 'Picar o tomate e misturar com manjericão e azeite.', 43, 0),
(3, 'Cobrir as fatias de pão com a mistura.', 43, 0),
(4, 'Servir imediatamente.', 43, 0);

-- Utensilios da receita
-- Macarrão à Bolonhesa (receita_id = 1)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 1, 0),  -- Panela
(2, 1, 0),  -- Faca
(3, 1, 0),  -- Colher de Pau
(10, 1, 0), -- Escorredor de Macarrão
(8, 1, 0);  -- Tábua de Corte

-- Penne ao Pesto (receita_id = 2)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 2, 0),  -- Panela
(2, 2, 0),  -- Faca
(5, 2, 0),  -- Liquidificador
(10, 2, 0), -- Escorredor de Macarrão
(8, 2, 0);  -- Tábua de Corte

-- Espaguete à Carbonara (receita_id = 3)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 3, 0),  -- Panela
(11, 3, 0), -- Frigideira
(2, 3, 0),  -- Faca
(3, 3, 0),  -- Colher de Pau
(10, 3, 0); -- Escorredor de Macarrão

-- Lasanha de Carne (receita_id = 4)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 4, 0),  -- Panela
(2, 4, 0),  -- Faca
(3, 4, 0),  -- Colher de Pau
(19, 4, 0), -- Assadeira
(8, 4, 0);  -- Tábua de Corte

-- Fettuccine Alfredo (receita_id = 5)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 5, 0),  -- Panela
(3, 5, 0),  -- Colher de Pau
(2, 5, 0),  -- Faca
(10, 5, 0), -- Escorredor de Macarrão
(8, 5, 0);  -- Tábua de Corte

-- Macarrão ao Alho e Óleo (receita_id = 6)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 6, 0),  -- Panela
(11, 6, 0), -- Frigideira
(2, 6, 0),  -- Faca
(15, 6, 0), -- Espremedor de Alho
(10, 6, 0); -- Escorredor de Macarrão

-- Ravioli de Ricota (receita_id = 7)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 7, 0),  -- Panela
(2, 7, 0),  -- Faca
(8, 7, 0),  -- Tábua de Corte
(20, 7, 0), -- Tigela
(10, 7, 0); -- Escorredor de Macarrão

-- Talharim com Frutos do Mar (receita_id = 8)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 8, 0),  -- Panela
(11, 8, 0), -- Frigideira
(2, 8, 0),  -- Faca
(3, 8, 0),  -- Colher de Pau
(10, 8, 0); -- Escorredor de Macarrão

-- Nhoque de Batata (receita_id = 9)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 9, 0),  -- Panela
(42, 9, 0), -- Espremedor de Batata
(2, 9, 0),  -- Faca
(3, 9, 0),  -- Colher de Pau
(10, 9, 0); -- Escorredor de Macarrão

-- Canelone de Presunto e Queijo (receita_id = 10)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(2, 10, 0), -- Faca
(8, 10, 0), -- Tábua de Corte
(19, 10, 0),-- Assadeira
(3, 10, 0), -- Colher de Pau
(20, 10, 0);-- Tigela

-- Macarrão de Forno (receita_id = 11)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 11, 0), -- Panela
(19, 11, 0),-- Assadeira
(2, 11, 0), -- Faca
(3, 11, 0), -- Colher de Pau
(10, 11, 0);-- Escorredor de Macarrão

-- Bife Acebolado (receita_id = 12)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 12, 0), -- Frigideira
(2, 12, 0),  -- Faca
(3, 12, 0),  -- Colher de Pau
(8, 12, 0);  -- Tábua de Corte

-- Filé Mignon ao Molho Madeira (receita_id = 13)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 13, 0), -- Frigideira
(1, 13, 0),  -- Panela
(2, 13, 0),  -- Faca
(3, 13, 0),  -- Colher de Pau
(8, 13, 0);  -- Tábua de Corte

-- Costela Assada (receita_id = 14)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(19, 14, 0), -- Assadeira
(2, 14, 0),  -- Faca
(3, 14, 0),  -- Colher de Pau
(8, 14, 0);  -- Tábua de Corte

-- Frango Grelhado com Ervas (receita_id = 15)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 15, 0), -- Frigideira
(2, 15, 0),  -- Faca
(3, 15, 0),  -- Colher de Pau
(8, 15, 0);  -- Tábua de Corte

-- Almôndegas ao Molho (receita_id = 16)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 16, 0), -- Frigideira
(1, 16, 0),  -- Panela
(20, 16, 0), -- Tigela
(2, 16, 0),  -- Faca
(3, 16, 0);  -- Colher de Pau

-- Carne de Panela (receita_id = 17)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 17, 0),  -- Panela
(2, 17, 0),  -- Faca
(3, 17, 0),  -- Colher de Pau
(8, 17, 0);  -- Tábua de Corte

-- Bife à Parmegiana (receita_id = 18)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 18, 0), -- Frigideira
(19, 18, 0), -- Assadeira
(2, 18, 0),  -- Faca
(3, 18, 0),  -- Colher de Pau
(20, 18, 0); -- Tigela

-- Frango à Passarinho (receita_id = 19)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 19, 0), -- Frigideira
(2, 19, 0),  -- Faca
(3, 19, 0),  -- Colher de Pau
(8, 19, 0);  -- Tábua de Corte

-- Picadinho de Carne (receita_id = 20)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 20, 0),  -- Panela
(2, 20, 0),  -- Faca
(3, 20, 0),  -- Colher de Pau
(8, 20, 0);  -- Tábua de Corte

-- Carne Moída com Batata (receita_id = 21)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 21, 0),  -- Panela
(2, 21, 0),  -- Faca
(3, 21, 0),  -- Colher de Pau
(8, 21, 0);  -- Tábua de Corte

-- Linguiça na Cerveja (receita_id = 22)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 22, 0),  -- Panela
(11, 22, 0), -- Frigideira
(2, 22, 0),  -- Faca
(3, 22, 0),  -- Colher de Pau
(8, 22, 0); -- Tábua de Corte

-- Berinjela Recheada (receita_id = 23)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(19, 23, 0), -- Assadeira
(2, 23, 0),  -- Faca
(8, 23, 0),  -- Tábua de Corte
(3, 23, 0);  -- Colher de Pau

-- Quibe de Abóbora (receita_id = 24)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 24, 0),  -- Panela
(20, 24, 0), -- Tigela
(2, 24, 0),  -- Faca
(3, 24, 0);  -- Colher de Pau

-- Hambúrguer de Grão-de-Bico (receita_id = 25)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 25, 0), -- Frigideira
(5, 25, 0),  -- Liquidificador
(2, 25, 0),  -- Faca
(8, 25, 0);  -- Tábua de Corte

-- Escondidinho de Lentilha (receita_id = 26)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 26, 0),  -- Panela
(19, 26, 0), -- Assadeira
(42, 26, 0), -- Espremedor de Batata
(2, 26, 0);  -- Faca

-- Torta de Legumes (receita_id = 27)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(19, 27, 0), -- Assadeira
(20, 27, 0), -- Tigela
(2, 27, 0),  -- Faca
(3, 27, 0); -- Colher de Pau

-- Arroz de Couve-Flor (receita_id = 28)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 28, 0),  -- Panela
(5, 28, 0),  -- Liquidificador
(2, 28, 0),  -- Faca
(8, 28, 0);  -- Tábua de Corte

-- Omelete de Espinafre (receita_id = 29)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 29, 0), -- Frigideira
(2, 29, 0),  -- Faca
(20, 29, 0), -- Tigela
(3, 29, 0);  -- Colher de Pau

-- Panqueca de Abobrinha (receita_id = 30)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 30, 0), -- Frigideira
(20, 30, 0), -- Tigela
(2, 30, 0),  -- Faca
(3, 30, 0);  -- Colher de Pau

-- Risoto de Legumes (receita_id = 31)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 31, 0),  -- Panela
(2, 31, 0),  -- Faca
(3, 31, 0),  -- Colher de Pau
(8, 31, 0);  -- Tábua de Corte

-- Cuscuz Paulista Vegetariano (receita_id = 32)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 32, 0),  -- Panela
(19, 32, 0), -- Assadeira
(2, 32, 0),  -- Faca
(3, 32, 0);  -- Colher de Pau

-- Bolo de Chocolate (receita_id = 33)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(18, 33, 0), -- Forma de Bolo
(4, 33, 0),  -- Batedeira
(2, 33, 0),  -- Faca
(20, 33, 0); -- Tigela

-- Mousse de Maracujá (receita_id = 34)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(5, 34, 0),  -- Liquidificador
(20, 34, 0), -- Tigela
(21, 34, 0); -- Jarra

-- Brigadeiro (receita_id = 35)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 35, 0),  -- Panela
(3, 35, 0),  -- Colher de Pau
(20, 35, 0); -- Tigela
-- Suco Natural (receita_id = 36)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(5, 36, 0),   -- Liquidificador
(21, 36, 0),  -- Jarra
(2, 36, 0);   -- Faca

-- Chá Gelado de Hibisco (receita_id = 37)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 37, 0),   -- Panela
(21, 37, 0),  -- Jarra
(22, 37, 0);  -- Coador

-- Suco Verde (receita_id = 38)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(5, 38, 0),   -- Liquidificador
(2, 38, 0),   -- Faca
(21, 38, 0);  -- Jarra

-- Salada Colorida (receita_id = 39)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(2, 39, 0),   -- Faca
(8, 39, 0),   -- Tábua de Corte
(20, 39, 0),  -- Tigela
(29, 39, 0);  -- Pegador de Salada

-- Salada Caesar (receita_id = 40)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(2, 40, 0),   -- Faca
(8, 40, 0),   -- Tábua de Corte
(20, 40, 0),  -- Tigela
(29, 40, 0);  -- Pegador de Salada

-- Salada de Quinoa (receita_id = 41)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(1, 41, 0),   -- Panela
(2, 41, 0),   -- Faca
(8, 41, 0),   -- Tábua de Corte
(20, 41, 0);  -- Tigela

-- Mini Quiches (receita_id = 42)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(19, 42, 0),  -- Assadeira
(4, 42, 0),   -- Batedeira
(20, 42, 0),  -- Tigela
(2, 42, 0);   -- Faca

-- Bruschetta de Tomate (receita_id = 43)
INSERT INTO utensilio_receita(utensilio_id, receita_id, version) VALUES
(11, 43, 0),  -- Frigideira
(2, 43, 0),   -- Faca
(8, 43, 0),   -- Tábua de Corte
(3, 43, 0);   -- Colher de Pau
