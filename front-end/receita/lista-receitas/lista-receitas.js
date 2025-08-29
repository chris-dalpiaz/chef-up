async function carregarReceitas() {
  const token = localStorage.getItem("token");
  const idUsuario = localStorage.getItem("id");
  const container = document.querySelector(".receitas_container");
  const filtrarCompletas = document.getElementById("filtro-completo").checked;
  const termoBusca = document.getElementById("campo-busca").value.trim().toLowerCase();

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
    const ingredientesUsuario = estoque.map(item => Number(item.ingrediente.id));

    container.innerHTML = "";

    for (const receita of receitas) {
      // 🔍 Filtro por nome
      if (termoBusca && !receita.nome.toLowerCase().includes(termoBusca)) {
        continue;
      }

      // ✅ Filtro por ingredientes disponíveis
      if (filtrarCompletas) {
        const ingredientesRes = await fetch(`http://localhost:8080/receitas/${receita.id}/ingredientes`, {
          headers: { Authorization: `Bearer ${token}` }
        });

        if (!ingredientesRes.ok) continue;

        const ingredientesReceita = await ingredientesRes.json();
        const idsReceita = ingredientesReceita.map(i => Number(i.ingrediente.id));

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
      container.innerHTML = "<p>Nenhuma receita encontrada com os critérios atuais.</p>";
    }

  } catch (error) {
    console.error("Erro ao carregar receitas:", error);
    container.innerHTML = "<p>Não foi possível carregar as receitas.</p>";
  }
}

function configurarEventos() {
  carregarUsuario();
  carregarReceitas();
  carregarProgresso();
  
  document.getElementById("campo-busca").addEventListener("input", carregarReceitas);

  // Seleciona todos os itens do footer e adiciona um ouvinte de evento de clique
  document.querySelectorAll('.footer-item').forEach(item => {
    item.addEventListener('click', () => {
      // Remove a classe 'selected' de todos os itens do footer
      document.querySelectorAll('.footer-item').forEach(i => i.classList.remove('selected'));
      // Adiciona a classe 'selected' apenas ao item clicado
      item.classList.add('selected');
    });
  });

  // Adiciona funcionalidade ao botão de filtro para alternar a visibilidade das opções de filtro
  document.querySelector(".filter-btn").addEventListener("click", (e) => {
    e.stopPropagation(); // Impede que o clique no botão propague e feche o menu de filtro
    const filtro = document.querySelector(".filter-options");
    // Alterna a classe 'hidden' para mostrar ou esconder o menu de filtro
    filtro.classList.toggle("hidden");
  });

  // Fecha o menu de filtro se o usuário clicar fora dele
  document.addEventListener("click", (e) => {
    const filtro = document.querySelector(".filter-options");
    const btn = document.querySelector(".filter-btn");
    // Verifica se o clique não foi no botão nem no menu de filtro
    if (!btn.contains(e.target) && !filtro.contains(e.target)) {
      // Esconde o menu de filtro
      filtro.classList.add("hidden");
    }
  });

  // Recarrega as receitas quando o estado do filtro é alterado
  document.getElementById("filtro-completo").addEventListener("change", carregarReceitas);
}

window.addEventListener("load", configurarEventos);
