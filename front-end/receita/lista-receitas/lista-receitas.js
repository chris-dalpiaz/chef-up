window.addEventListener("load", carregarReceitas);

// Destaca o item do footer clicado
document.querySelectorAll('.footer-item').forEach(item => {
  item.addEventListener('click', () => {
    document.querySelectorAll('.footer-item').forEach(i => i.classList.remove('selected'));
    item.classList.add('selected');
  });
});

// Alterna visibilidade do filtro ao clicar no ícone
document.querySelector(".filter-btn").addEventListener("click", (e) => {
  e.stopPropagation(); // Impede que o clique feche o menu imediatamente
  const filtro = document.querySelector(".filter-options");
  filtro.classList.toggle("hidden");
});

// Fecha o menu de filtro ao clicar fora
document.addEventListener("click", (e) => {
  const filtro = document.querySelector(".filter-options");
  const btn = document.querySelector(".filter-btn");
  if (!btn.contains(e.target) && !filtro.contains(e.target)) {
    filtro.classList.add("hidden");
  }
});

// Recarrega receitas ao mudar o filtro
document.getElementById("filtro-completo").addEventListener("change", carregarReceitas);

async function carregarReceitas() {
  const token = localStorage.getItem("token");
  const idUsuario = localStorage.getItem("id");
  const container = document.querySelector(".receitas_container");
  const filtrarCompletas = document.getElementById("filtro-completo").checked;

  if (!token || !idUsuario) {
    container.innerHTML = "<p>Usuário não autenticado.</p>";
    return;
  }

  try {
    const [receitasRes, estoqueRes] = await Promise.all([
      fetch("http://localhost:8080/receitas", {
        headers: { Authorization: `Bearer ${token}` }
      }),
      fetch(`http://localhost:8080/usuarios/${idUsuario}/estoque-virtual`, {
        headers: { Authorization: `Bearer ${token}` }
      })
    ]);

    if (!receitasRes.ok || !estoqueRes.ok) {
      throw new Error("Erro ao buscar dados");
    }

    const receitas = await receitasRes.json();
    const estoque = await estoqueRes.json();
    const ingredientesUsuario = estoque.map(item => item.ingrediente.id);

    container.innerHTML = "";

    for (const receita of receitas) {
      if (filtrarCompletas) {
        const ingredientesRes = await fetch(`http://localhost:8080/receitas/${receita.id}/ingredientes`, {
          headers: { Authorization: `Bearer ${token}` }
        });

        if (!ingredientesRes.ok) continue;

        const ingredientesReceita = await ingredientesRes.json();
        const idsReceita = ingredientesReceita.map(i => i.ingrediente.id);

        const possuiTodos = idsReceita.every(id => ingredientesUsuario.includes(id));
        if (!possuiTodos) continue;
      }

      const card = document.createElement("div");
      card.className = "receita_card";

      card.addEventListener("click", () => {
        window.location.href = `../pagina-receita/pagina-receita.html?id=${receita.id}`;
      });

      card.innerHTML = `
        <img src="${receita.imagemReceita}" alt="${receita.nome}" class="receita_imagem">
        <h2>${receita.nome}</h2>
      `;

      container.appendChild(card);
    }

    if (container.innerHTML === "") {
      container.innerHTML = "<p>Nenhuma receita disponível com os ingredientes atuais.</p>";
    }

  } catch (error) {
    console.error("Erro ao carregar receitas:", error);
    container.innerHTML = "<p>Não foi possível carregar as receitas.</p>";
  }
}
