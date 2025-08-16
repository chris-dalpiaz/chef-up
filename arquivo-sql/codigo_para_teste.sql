-- 1. pronome
INSERT INTO pronome (nome) VALUES
('Ele/Dele'),
('Ela/Dela'),
('Elu/Delu'),
('Nenhum'),
('Personalizado');

-- 3. ingrediente
INSERT INTO ingrediente (nome, categoria, estimativa_validade, dica_conservacao) VALUES
('Arroz', 'Grãos', 365, 'Guardar em local seco'),
('Feijão', 'Grãos', 365, 'Guardar em local seco'),
('Tomate', 'Hortaliça', 7, 'Manter refrigerado'),
('Carne Bovina', 'Proteína', 5, 'Manter congelada'),
('Queijo', 'Laticínios', 15, 'Manter refrigerado');

-- 4. categoria
INSERT INTO categoria (nome, icone_url) VALUES
('Massas', 'icone1.png'),
('Carnes', 'icone2.png'),
('Vegetariano', 'icone3.png'),
('Sobremesas', 'icone4.png'),
('Bebidas', 'icone5.png');

-- 5. receita (depende de categoria)
INSERT INTO receita (nome, descricao, tempo_preparo_segundos, dificuldade, xp_ganho, categorias_id) VALUES
('Macarrão à Bolonhesa', 'Macarrão com molho de carne', 1800, 'Médio', 50, 1),
('Bife Acebolado', 'Carne grelhada com cebola', 1200, 'Fácil', 40, 2),
('Salada Colorida', 'Mix de vegetais frescos', 600, 'Fácil', 30, 3),
('Bolo de Chocolate', 'Bolo fofinho de chocolate', 3600, 'Médio', 60, 4),
('Suco Natural', 'Suco de frutas frescas', 300, 'Fácil', 20, 5);

-- 6. ingrediente_receita (depende de ingrediente e receita)
INSERT INTO ingrediente_receita (ingredientes_id, receitas_id, unidade_medida, quantidade) VALUES
(1, 1, 'g', 200),
(4, 2, 'g', 150),
(3, 3, 'un', 2),
(5, 4, 'g', 100),
(2, 1, 'g', 100);

-- 7. adjetivo
INSERT INTO adjetivo (nome) VALUES
('Rápido'),
('Criativo'),
('Organizado'),
('Paciente'),
('Inovador');

-- 8. titulo
INSERT INTO titulo (nome, condicao_desbloqueio) VALUES
('Chef Iniciante', 'Concluir 1 receita'),
('Chef Intermediário', 'Concluir 10 receitas'),
('Chef Avançado', 'Concluir 50 receitas'),
('Mestre da Cozinha', 'Concluir 100 receitas'),
('Colecionador de Receitas', 'Ter 20 receitas salvas');

-- 9. etapa_receita (depende de receita)
INSERT INTO etapa_receita (ordem, conteudo, receitas_id) VALUES
(1, 'Cozinhar o macarrão', 1),
(2, 'Preparar o molho', 1),
(1, 'Grelhar o bife', 2),
(1, 'Misturar vegetais', 3),
(1, 'Bater a massa do bolo', 4);

-- 10. avatar
INSERT INTO avatar (nome, imagem_url) VALUES
('Chef Pão de Queijo', 'avatar1.png'),
('Chef Sorvete', 'avatar2.png'),
('Chef Pizza', 'avatar3.png'),
('Chef Sushi', 'avatar4.png'),
('Chef Café', 'avatar5.png');


-- DEPOIS DE GERAR USUÁRIO

-- 11. titulo_usuario (depende de titulo e usuario)
INSERT INTO titulo_usuario (titulos_id, usuarios_id, desbloqueado_em) VALUES
(1, 1, NOW());

-- 12. adjetivo_usuario (depende de adjetivo e usuario)
INSERT INTO adjetivo_usuario (adjetivos_id, usuarios_id) VALUES
(1, 1);

-- 13. avatar_usuario (depende de avatar e usuario)
INSERT INTO avatar_usuario (usuarios_id, avatares_id, desbloqueado_em) VALUES
(1, 1, NOW());

-- 14. receita_usuario (depende de usuario e receita)
INSERT INTO receita_usuario (usuarios_id, receitas_id, data_conclusao, foto_prato, pontuacao_prato) VALUES
(1, 1, NOW(), 'foto1.png', 5);

-- 16. utensilio
INSERT INTO utensilio (nome) VALUES
('Panela'),
('Faca'),
('Colher de Pau'),
('Batedeira'),
('Liquidificador');

-- 17. utensilio_receita (depende de utensilio e receita)
INSERT INTO utensilio_receita (utensilios_id, receitas_id) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- 18. colecao (depende de usuario)
INSERT INTO colecao (nome, usuarios_id) VALUES
('Favoritas', 1);

-- 19. receita_colecao (depende de receita e colecao)
INSERT INTO receita_colecao (receitas_id, colecoes_id) VALUES
(1, 1);
