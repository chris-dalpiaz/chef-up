// Inicializa um array vazio para armazenar as etapas da receita
let etapas = [];

// Variável que indica qual etapa está sendo exibida atualmente
let etapaAtual = 0;

// Função para carregar as etapas da receita
async function  carregarEtapasReceita() {
  // Recupera o token de autenticação armazenado localmente
  const token = localStorage.getItem("token");

  // Obtém os parâmetros da URL atual (por exemplo, ?id=123)
  const urlParams = new URLSearchParams(window.location.search);

  // Extrai o valor do parâmetro "id", que representa o ID da receita
  const receitaId = urlParams.get("id");

  // Verifica se o token ou o ID da receita estão ausentes
  if (!token || !receitaId) {
    // Exibe um alerta informando que a receita ou autenticação falhou
    alert("Receita não encontrada ou usuário não autenticado.");
    return; // Encerra a execução da função
  }

  try {
    // Faz uma requisição à API para buscar as etapas da receita
    const res = await fetch(`http://localhost:8080/receitas/${receitaId}/etapas`, {
      // Envia o token no cabeçalho Authorization para autenticação
      headers: { Authorization: `Bearer ${token}` }
    });

    // Se a resposta não for bem-sucedida, lança um erro
    if (!res.ok) throw new Error("Erro ao buscar etapas");

    // Converte a resposta da API para JSON e armazena no array de etapas
    etapas = await res.json();

    // Renderiza a barra de progresso com base na quantidade de etapas
    renderizarBarraProgresso();

    // Exibe a primeira etapa (índice 0)
    mostrarEtapa(0);
  } catch (error) {
    // Exibe o erro no console em caso de falha na requisição
    console.error("Erro ao carregar etapas:", error);
  }
}

// Função que exibe uma etapa específica da receita
function mostrarEtapa(index) {
  // Obtém a etapa correspondente ao índice fornecido
  const etapa = etapas[index];

  // Se a etapa não existir (índice inválido), encerra a função
  if (!etapa) return;

  // Atualiza o número da etapa na interface, com dois dígitos (ex: 01, 02)
  document.getElementById("etapa-numero").textContent = etapa.ordem.toString().padStart(2, "0");

  // Atualiza o conteúdo da etapa (instruções, descrição, etc.)
  document.getElementById("etapa-conteudo").textContent = etapa.conteudo;

  // Limpa a lista de ingredientes (para quando for adicionado ingredientes nas etapas)
  document.getElementById("etapa-ingredientes").innerHTML = "";

  // Atualiza os "dots" da barra de progresso para refletir a etapa atual
  const dots = document.querySelectorAll(".dot");
  dots.forEach((dot, i) => {
    // Ativa os pontos até o índice atual
    dot.classList.toggle("active", i <= index);
  });

  // Atualiza a variável que indica a etapa atual
  etapaAtual = index;
}

// Função que avança para a próxima etapa da receita
function proximaEtapa() {
  // Verifica se ainda há etapas seguintes
  if (etapaAtual < etapas.length - 1) {
    // Exibe a próxima etapa
    mostrarEtapa(etapaAtual + 1);
  } else {
    // Se todas as etapas foram concluídas, exibe uma mensagem
    alert("Você concluiu todas as etapas!");
  }
}

// Função que renderiza a barra de progresso com base na quantidade de etapas
function renderizarBarraProgresso() {
  // Obtém o elemento da barra de progresso
  const progressBar = document.getElementById("progress-bar");

  // Limpa qualquer conteúdo anterior da barra
  progressBar.innerHTML = "";

  // Para cada etapa, cria um ponto visual (dot) na barra de progresso
  etapas.forEach(() => {
    const dot = document.createElement("div"); // Cria um novo elemento div
    dot.className = "dot"; // Define a classe CSS como "dot"
    progressBar.appendChild(dot); // Adiciona o ponto à barra de progresso
  });
}

// Função que retorna à página anterior no histórico do navegador
function voltar() {
  window.history.back(); // Navega para a página anterior
}

//Função que configura e inicializa os eventos js
function configurarEventos(){
  carregarEtapasReceita();
}

// Adiciona um ouvinte de evento à janela que será executado quando a página for totalmente carregada
// Isso garante que os elementos do DOM (html) estejam disponíveis antes de tentar acessá-los
window.addEventListener("load", configurarEventos);