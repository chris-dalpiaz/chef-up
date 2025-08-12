// ==================== DADOS EXEMPLO ====================
const receitas = [
  {
    id: 1,
    nome: "Brussels Sprouts, Mashed Potato & Sausage Bowl",
    imagem: "https://via.placeholder.com/300x200",
    xp: 10,
    categoria: "Popular"
  },
  {
    id: 2,
    nome: "Sushi Especial",
    imagem: "https://via.placeholder.com/300x200",
    xp: 8,
    categoria: "Oriental"
  },
  {
    id: 3,
    nome: "Suco Detox",
    imagem: "https://via.placeholder.com/300x200",
    xp: 5,
    categoria: "Bebidas"
  },
  {
    id: 4,
    nome: "Sanduíche Natural",
    imagem: "https://via.placeholder.com/300x200",
    xp: 6,
    categoria: "Lanches"
  }
];


// ==================== ELEMENTOS ====================
const listaReceitas = document.getElementById("lista-receitas");
const campoBusca = document.querySelector(".search-bar input");
const categorias = document.querySelectorAll(".category");
const footerItems = document.querySelectorAll(".footer-item");
const nivelEl = document.querySelector(".nivel");
const barraXpEl = document.querySelector(".barra_xp");
const pontosXpEl = document.querySelector(".pontos_xp");

// ==================== FUNÇÕES ====================

// Atualiza a barra de progresso
function atualizarProgresso() {
  nivelEl.innerHTML = `<span>${nivel}</span>`;
  barraXpEl.style.width = `${(xpAtual / xpMax) * 100}%`;
  pontosXpEl.textContent = `${xpAtual} / ${xpMax}`;
}

// Renderiza as receitas na tela
function renderReceitas(filtroCategoria = "Popular", termoBusca = "") {
  listaReceitas.innerHTML = "";

  const receitasFiltradas = receitas.filter(r => {
    const matchCategoria = filtroCategoria ? r.categoria === filtroCategoria : true;
    const matchBusca = termoBusca
      ? r.nome.toLowerCase().includes(termoBusca.toLowerCase())
      : true;
    return matchCategoria && matchBusca;
  });

  if (receitasFiltradas.length === 0) {
    listaReceitas.innerHTML = "<p>Nenhuma receita encontrada.</p>";
    return;
  }

  receitasFiltradas.forEach(r => {
    const card = document.createElement("div");
    card.classList.add("card-receita");

    card.innerHTML = `
      <img src="${r.imagem}" alt="${r.nome}">
      <div class="xp-tag">${r.xp}XP</div>
      <p>${r.nome}</p>
    `;

    listaReceitas.appendChild(card);
  });
}

// ==================== EVENTOS ====================

// Muda a categoria selecionada
categorias.forEach(cat => {
  cat.addEventListener("click", () => {
    categorias.forEach(c => c.classList.remove("selected"));
    cat.classList.add("selected");

    const categoriaNome = cat.getAttribute("data-nome");
    renderReceitas(categoriaNome, campoBusca.value);
  });
});

// Filtra enquanto digita
campoBusca.addEventListener("input", () => {
  const categoriaAtual = document.querySelector(".category.selected").getAttribute("data-nome");
  renderReceitas(categoriaAtual, campoBusca.value);
});

// Rodapé — muda o item selecionado
footerItems.forEach(item => {
  item.addEventListener("click", () => {
    footerItems.forEach(i => i.classList.remove("selected"));
    item.classList.add("selected");
    console.log("Mudou para:", item.innerText);
  });
});

// ==================== INICIALIZAÇÃO ====================
renderReceitas("Popular");
atualizarProgresso();
