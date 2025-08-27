// Função que busca os ingredientes do estoque virtual do usuário autenticado
async function carregarIngredientes() {
  const idUsuario = JSON.parse(localStorage.getItem("id")); // Recupera o ID do usuário do localStorage
  const token = localStorage.getItem("token");              // Recupera o token de autenticação

  // Verifica se o ID ou token estão ausentes
  if (!idUsuario || !token) {
    console.error("ID ou token não encontrado no localStorage.");
    return;
  }

  try {
    // Faz requisição GET para buscar os ingredientes do estoque virtual do usuário
    const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/estoque-virtual`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,         // Envia o token no cabeçalho para autenticação
        "Content-Type": "application/json"
      }
    });

    // Se a resposta não for bem-sucedida, lança erro
    if (!response.ok) throw new Error("Erro ao buscar ingredientes");

    const dados = await response.json(); // Converte a resposta para JSON
    const container = document.getElementById("lista-ingredientes"); // Seleciona o container onde os cards serão exibidos
    container.innerHTML = ""; // Limpa o conteúdo anterior

    // Para cada item recebido, cria um card e adiciona ao container
    dados.forEach(item => {
      const card = criarCardIngrediente(item.ingrediente, item.id);
      container.appendChild(card);
    });

  } catch (error) {
    console.error("Erro ao carregar ingredientes:", error);
  }
}

// Função que cria visualmente um card de ingrediente
function criarCardIngrediente(ingrediente, idAssociacao) {
  const card = document.createElement("div"); // Cria um elemento div
  card.className = "card";                    // Define a classe CSS
  card.dataset.ingredienteId = ingrediente.id; // Armazena o ID do ingrediente como atributo personalizado

  // Define o conteúdo HTML do card
  card.innerHTML = `
    <div class="svg-container">
      <!-- SVG permanece igual -->
    </div>
    <div class="info">
      <h3>${ingrediente.nome}</h3>
      <p>Categoria: ${ingrediente.categoria}</p>
      <p>Validade estimada: ${ingrediente.estimativaValidade} dias</p>
      <p>Dica conservação: ${ingrediente.dicaConservacao}</p>
    </div>
    <div class="acoes">
      <button class="btn-deletar" data-id="${idAssociacao}" title="Remover ingrediente">🗑️</button>
    </div>
  `;

  // Adiciona evento de clique ao botão de deletar
  card.querySelector(".btn-deletar").addEventListener("click", () => {
    if (confirm(`Deseja remover "${ingrediente.nome}" da despensa?`)) {
      deletarIngrediente(idAssociacao); // Chama a função para deletar o ingrediente
    }
  });

  return card; // Retorna o card criado
}

// Função que exclui um ingrediente do estoque virtual
async function deletarIngrediente(idAssociacao) {
  const idUsuario = JSON.parse(localStorage.getItem("id"));
  const token = localStorage.getItem("token");

  if (!idUsuario || !token) {
    alert("Sessão expirada. Faça login novamente.");
    return;
  }

  try {
    // Faz requisição DELETE para remover o ingrediente
    const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/estoque-virtual/${idAssociacao}`, {
      method: "DELETE",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });

    if (!response.ok) throw new Error("Erro ao excluir ingrediente");

    alert("Ingrediente removido com sucesso!");
    carregarIngredientes(); // Atualiza a lista após exclusão
  } catch (error) {
    console.error("Erro ao excluir ingrediente:", error);
    alert("Erro ao excluir ingrediente.");
  }
}

// Função que configura o modal para adicionar ingredientes
function configurarModalAdicionar() {
  const botaoAbrir = document.getElementById("simbolo");           // Botão que abre o modal
  const modal = document.getElementById("modalIngrediente");       // Elemento do modal
  const botaoSalvar = document.getElementById("botaoSalvar");      // Botão para salvar ingrediente

  // Ao clicar no botão de abrir, exibe o modal e carrega a lista de ingredientes disponíveis
  botaoAbrir.addEventListener("click", () => {
    modal.classList.remove("hidden");
    carregarListaIngredientes();
  });

  // Ao clicar no botão de salvar, executa a função para adicionar ingrediente
  botaoSalvar.addEventListener("click", salvarIngrediente);
}

// Função que carrega a lista de ingredientes disponíveis para adicionar
async function carregarListaIngredientes() {
  const token = localStorage.getItem("token");

  if (!token) {
    console.error("Token não encontrado no localStorage.");
    return;
  }

  try {
    // Requisição GET para buscar todos os ingredientes disponíveis
    const response = await fetch("http://localhost:8080/ingredientes", {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    });

    //se a resposta não estiver ok, lança erro
    if (!response.ok) throw new Error("Erro ao buscar ingredientes disponíveis");

    const ingredientes = await response.json(); // Converte resposta para JSON
    const select = document.getElementById("select-ingrediente"); // Seleciona o elemento <select>

    if (!select) {
      console.error("Elemento <select> não encontrado no DOM.");
      return;
    }

    select.innerHTML = ""; // Limpa opções anteriores

    // Se não houver ingredientes, exibe uma opção informativa
    if (ingredientes.length === 0) {
      const option = document.createElement("option");
      option.textContent = "Nenhum ingrediente disponível";
      option.disabled = true;
      option.selected = true;
      select.appendChild(option);
      return;
    }

    // Para cada ingrediente, cria uma opção no <select>
    ingredientes.forEach(ingrediente => {
      const option = document.createElement("option");
      option.value = ingrediente.id;
      option.textContent = `${ingrediente.nome} (${ingrediente.categoria})`;
      select.appendChild(option);
    });

  } catch (error) {
    console.error("Erro ao carregar lista de ingredientes:", error);
  }
}

// Função que salva um novo ingrediente no estoque virtual do usuário
async function salvarIngrediente() {
  const idUsuario = JSON.parse(localStorage.getItem("id"));
  const token = localStorage.getItem("token");
  const ingredienteId = parseInt(document.getElementById("select-ingrediente").value);

  // Verifica se o ingrediente já está na despensa
  const cards = document.querySelectorAll(".card");
  for (const card of cards) {
    if (parseInt(card.dataset.ingredienteId) === ingredienteId) {
      alert("Este ingrediente já está na despensa.");
      return;
    }
  }

  const dados = { idIngrediente: ingredienteId }; // Dados a serem enviados

  try {
    // Requisição POST para adicionar ingrediente ao estoque virtual
    const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/estoque-virtual`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      },
      body: JSON.stringify(dados)
    });

    if (!response.ok) throw new Error("Erro ao salvar ingrediente");

    alert("Ingrediente adicionado com sucesso!");
    document.getElementById("modalIngrediente").classList.add("hidden"); // Fecha o modal
    carregarIngredientes(); // Atualiza a lista de ingredientes

  } catch (error) {
    console.error("Erro ao salvar ingrediente:", error);
    alert("Erro ao salvar ingrediente.");
  }
}

// Evento para fechar o modal ao clicar no botão de fechar
document.addEventListener('DOMContentLoaded', () => {
  const closeModal = document.querySelector('.close-modal'); // Botão de fechar modal
  const modal = document.querySelector('#modalIngrediente'); // Elemento do modal

  // Ao clicar no botão de fechar, adiciona a classe "hidden" para ocultar o modal
  closeModal.addEventListener('click', () => {
    modal.classList.add('hidden');
  });
});

// Função para configurar os eventos js
function configurarEventos() {
  carregarIngredientes();         // Carrega os ingredientes da despensa virtual do usuário
  configurarModalAdicionar();    // Configura o modal para adicionar novos ingredientes
  carregarProgresso();
}

// Adiciona um ouvinte de evento à janela que será executado quando a página for totalmente carregada
// Isso garante que os elementos do DOM (html) estejam disponíveis antes de tentar acessá-los
window.addEventListener("load", configurarEventos)