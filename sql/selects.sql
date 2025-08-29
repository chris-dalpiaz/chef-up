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
