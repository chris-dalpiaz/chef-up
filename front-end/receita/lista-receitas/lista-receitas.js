// Função assíncrona que carrega as receitas do servidor
async function carregarReceitas() {
  // Recupera o token e o ID do usuário armazenados localmente
  const token = localStorage.getItem("token");
  const idUsuario = localStorage.getItem("id");
  const container = document.querySelector(".receitas_container");
  const filtrarCompletas = document.getElementById("filtro-completo").checked;

  // Verifica se o usuário está autenticado
  if (!token || !idUsuario) {
    container.innerHTML = "<p>Usuário não autenticado.</p>";
    return;
  }

  try {
    // Faz duas requisições simultâneas: uma para receitas e outra para o estoque virtual do usuário
    const [receitasRes, estoqueRes] = await Promise.all([
      fetch("http://localhost:8080/receitas", {
        headers: { Authorization: `Bearer ${token}` }
      }),
      fetch(`http://localhost:8080/usuarios/${idUsuario}/estoque-virtual`, {
        headers: { Authorization: `Bearer ${token}` }
      })
    ]);

    // Verifica se ambas as requisições foram bem-sucedidas
    if (!receitasRes.ok || !estoqueRes.ok) {
      throw new Error("Erro ao buscar dados");
    }

    // Converte as respostas para JSON
    const receitas = await receitasRes.json();
    const estoque = await estoqueRes.json();

    // Extrai os IDs dos ingredientes que o usuário possui
    const ingredientesUsuario = estoque.map(item => item.ingrediente.id);

    // Limpa o conteúdo atual do container de receitas
    container.innerHTML = "";

    // Itera sobre cada receita recebida
    for (const receita of receitas) {
      // Se o filtro estiver ativado, verifica se o usuário possui todos os ingredientes da receita
      if (filtrarCompletas) {
        const ingredientesRes = await fetch(`http://localhost:8080/receitas/${receita.id}/ingredientes`, {
          headers: { Authorization: `Bearer ${token}` }
        });

        // Se a requisição falhar, pula para a próxima receita
        if (!ingredientesRes.ok) continue;

        const ingredientesReceita = await ingredientesRes.json();
        const idsReceita = ingredientesReceita.map(i => i.ingrediente.id);

        // Verifica se todos os ingredientes da receita estão presentes no estoque do usuário
        const possuiTodos = idsReceita.every(id => ingredientesUsuario.includes(id));
        if (!possuiTodos) continue; // Se faltar algum ingrediente, pula a receita
      }

      // Cria um card para exibir a receita
      const card = document.createElement("div");
      card.className = "receita_card";

      // Adiciona funcionalidade de redirecionamento ao clicar no card
      card.addEventListener("click", () => {
        window.location.href = `../pagina-receita/pagina-receita.html?id=${receita.id}`;
      });

      // Define o conteúdo HTML do card com imagem e nome da receita
      card.innerHTML = `
        <img src="${receita.imagemReceita}" alt="${receita.nome}" class="receita_imagem">
        <h2>${receita.nome}</h2>
      `;

      // Adiciona o card ao container de receitas
      container.appendChild(card);
    }

    // Se nenhuma receita foi exibida, mostra uma mensagem informativa
    if (container.innerHTML === "") {
      container.innerHTML = "<p>Nenhuma receita disponível com os ingredientes atuais.</p>";
    }

  } catch (error) {
    // Em caso de erro, exibe uma mensagem no console e na interface
    console.error("Erro ao carregar receitas:", error);
    container.innerHTML = "<p>Não foi possível carregar as receitas.</p>";
  }
}

function configurarEventos() {
  carregarUsuario();
  carregarReceitas();
  carregarProgresso();

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
