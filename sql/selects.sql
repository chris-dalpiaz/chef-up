SELECT
  ir.receita_id,
  i.id AS ingrediente_id,
  i.nome AS nome_ingrediente,
  ir.unidade_medida,
  ir.quantidade,
  ier.etapa_receita_id
FROM ingrediente_receita ir
LEFT JOIN ingrediente i ON ir.ingrediente_id = i.id
LEFT JOIN ingrediente_etapa_receita ier ON ir.id = ier.ingrediente_receita_id;

select a.etapa_receita_id, b.id, b.quantidade, c.nome, b.ingrediente_id from ingrediente_etapa_receita a
inner join ingrediente_receita b on a.ingrediente_receita_id = b.id
inner join ingrediente c on b.ingrediente_id = c.id
where etapa_receita_id = 20;
select * from ingrediente;