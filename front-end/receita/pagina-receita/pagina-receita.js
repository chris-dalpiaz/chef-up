async function carregarReceita(){
  // Recupera o token de autenticação armazenado localmente
  const token = localStorage.getItem("token");

  // Obtém os parâmetros da URL atual (por exemplo, ?id=123)
  const urlParams = new URLSearchParams(window.location.search);

  // Extrai o valor do parâmetro 'id', que representa o ID da receita
  const receitaId = urlParams.get("id");

  // Verifica se o token ou o ID da receita estão ausentes
  if (!token || !receitaId) {
    alert("Receita não encontrada ou usuário não autenticado.");
    return; // Interrompe a execução se não houver autenticação ou ID válido
  }

  try {
    // Faz uma requisição para obter os dados da receita com base no ID
    const response = await fetch(`http://localhost:8080/receitas/${receitaId}`, {
      headers: { Authorization: `Bearer ${token}` } // Envia o token no cabeçalho para autenticação
    });

    // Se a resposta não for bem-sucedida, lança um erro
    if (!response.ok) throw new Error("Erro ao buscar receita");

    // Converte a resposta em JSON para acessar os dados da receita
    const receita = await response.json();

    // Atualiza a imagem da receita na interface
    document.querySelector(".imagem-receita img").src = receita.imagemReceita;
    document.querySelector(".imagem-receita img").alt = receita.nome;

    // Atualiza o título da receita na interface
    document.querySelector(".receita-titulo").textContent = receita.nome;

    // Converte o tempo de preparo de segundos para minutos (arredondado para cima)
    const minutos = Math.ceil(receita.tempoPreparoSegundos / 60);

    // Atualiza o tempo de preparo na interface
    document.querySelector(".receita-info").textContent = `${minutos} minutos`;

    // Atualiza o texto do botão de XP com a quantidade de XP que a receita concede
    const xpText = document.querySelector(".xp-button text");
    if (xpText) xpText.textContent = `${receita.xpGanho}XP`;

    // Seleciona todas as abas (tabs) da interface
    const tabs = document.querySelectorAll(".tab");

    // Seleciona o container onde o conteúdo da aba será exibido
    const conteudoContainer = document.querySelector(".receita-container .conteudo");

    // Limpa o conteúdo anterior do container
    conteudoContainer.innerHTML = "";

    // Adiciona um ouvinte de clique para cada aba
    tabs.forEach(tab => {
      tab.addEventListener("click", () => {
        // Remove a classe 'ativo' de todas as abas
        tabs.forEach(t => t.classList.remove("ativo"));

        // Adiciona a classe 'ativo' à aba clicada
        tab.classList.add("ativo");

        // Limpa o conteúdo atual do container
        conteudoContainer.innerHTML = "";

        // Verifica se a aba clicada é "Utensílios"
        if (tab.textContent === "Utensílios") {
          // Se não houver utensílios, exibe uma mensagem informativa
          if (receita.utensilios.length === 0) {
            conteudoContainer.innerHTML = "<p class='conteudo'>Nenhum utensílio necessário.</p>";
          } else {
            // Caso contrário, lista os utensílios necessários
            receita.utensilios.forEach(u => {
              conteudoContainer.innerHTML += `<p class="conteudo">${u.utensilio.nome}</p>`;
            });
          }
        } 
        // Verifica se a aba clicada é "Ingredientes"
        else if (tab.textContent === "Ingredientes") {
          // Lista os ingredientes com suas quantidades e unidades
          receita.ingredientes.forEach(i => {
            conteudoContainer.innerHTML += `
              <p class="conteudo">${i.quantidade}${i.unidadeMedida} de ${i.ingrediente.nome}</p>
            `;
          });
        }
      });
    });

    // Ativa a primeira aba automaticamente ao carregar a página
    tabs[0].click();

  } catch (error) {
    // Em caso de erro na requisição ou processamento, exibe mensagem no console e na interface
    console.error("Erro ao carregar receita:", error);
    document.querySelector(".receita-container").innerHTML = "<p>Erro ao carregar receita.</p>";
  }

  // Seleciona o botão "Começar"
  const botaoComecar = document.querySelector(".btn-comecar");

  // Se o botão existir, adiciona funcionalidade de redirecionamento
  if (botaoComecar) {
    botaoComecar.addEventListener("click", () => {
      // Redireciona para a página de etapas da receita, passando o ID na URL
      window.location.href = `../etapa-receita/etapa-receita.html?id=${receitaId}`;
    });
  }
}

// Função que retorna à página anterior no histórico do navegador
function voltar() {
  window.history.back();
}

// Função que exibe uma mensagem informando que o XP detalhado estará disponível futuramente
function abrirXP() {
  alert("XP detalhado em breve!");
}

// Função que alterna o estado do ícone de favorito (coração)
function toggleFavorito() {
  // Seleciona o elemento SVG do coração
  const path = document.getElementById("coracao-path");

  // Verifica se o coração está preenchido (favoritado)
  const isFavorito = path.getAttribute("fill") === "#f05a28";

  // Alterna entre preenchido e não preenchido
  path.setAttribute("fill", isFavorito ? "none" : "#f05a28");
}

function configurarEventos(){
  carregarReceita();
}

document.addEventListener("load", configurarEventos);