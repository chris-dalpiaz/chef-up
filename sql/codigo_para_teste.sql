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
('Queijo', 'Laticínios', 15, 'Manter refrigerado', 0);

-- 4. categoria
INSERT INTO categoria (nome, icone_url, version) VALUES
('Massas', 'icone1.png', 0),
('Carnes', 'icone2.png', 0),
('Vegetariano', 'icone3.png', 0),
('Sobremesas', 'icone4.png', 0),
('Bebidas', 'icone5.png', 0);

-- 5. receita (depende de categoria)
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categoria_id, imagem_receita, version) VALUES
('Macarrão à Bolonhesa', 'Macarrão com molho de carne', 1800, 'Médio', 50, 1, "http://localhost:8080/img/receitas/macarrao-a-bolonhesa.png", 0),
('Bife Acebolado', 'Carne grelhada com cebola', 1200, 'Fácil', 40, 2, "http://localhost:8080/img/receitas/bife-acebolado.png", 0),
('Salada Colorida', 'Mix de vegetais frescos', 600, 'Fácil', 30, 3, "http://localhost:8080/img/receitas/salada-colorida.png", 0),
('Bolo de Chocolate', 'Bolo fofinho de chocolate', 3600, 'Médio', 60, 4, "http://localhost:8080/img/receitas/bolo-de-chocolate.png", 0),
('Suco Natural', 'Suco de frutas frescas', 300, 'Fácil', 20, 5, "http://localhost:8080/img/receitas/suco-natural.png", 0);

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