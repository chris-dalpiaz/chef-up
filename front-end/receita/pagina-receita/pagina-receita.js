// Variáveis globais
const token = localStorage.getItem("token");
const urlParams = new URLSearchParams(window.location.search);
const receitaId = urlParams.get("id");

// Funções de botão
function voltar() {
  window.history.back();
}

function abrirXP() {
  alert("XP detalhado em breve!");
}

function toggleFavorito() {
  const path = document.getElementById("coracao-path");
  const isFavorito = path.getAttribute("fill") === "#f05a28";
  path.setAttribute("fill", isFavorito ? "none" : "#f05a28");
}

function redirecionarGPT() {
  window.location.href = `../../chatgpt/chatgpt.html?id=${receitaId}`;
}

function configurarEventos() {
  const btnFeito = document.getElementById("btn-feito");
  if (btnFeito) {
    btnFeito.addEventListener("click", redirecionarGPT);
  }
}

// Evento principal
document.addEventListener("DOMContentLoaded", async () => {
  if (!token || !receitaId) {
    alert("Receita não encontrada ou usuário não autenticado.");
    return;
  }

  try {
    const response = await fetch(`http://localhost:8080/receitas/${receitaId}`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!response.ok) throw new Error("Erro ao buscar receita");

    const receita = await response.json();

    // Atualiza imagem
    document.querySelector(".imagem-receita img").src = receita.imagemReceita;
    document.querySelector(".imagem-receita img").alt = receita.nome;

    // Atualiza título
    document.querySelector(".receita-titulo").textContent = receita.nome;

    // Atualiza tempo
    const minutos = Math.ceil(receita.tempoPreparoSegundos / 60);
    document.querySelector(".receita-info").textContent = `${minutos} minutos`;

    // Atualiza XP
    const xpText = document.querySelector(".xp-button text");
    if (xpText) xpText.textContent = `${receita.xpGanho}XP`;

    // Tabs
    const tabs = document.querySelectorAll(".tab");
    const conteudoContainer = document.querySelector(".receita-container .conteudo");
    conteudoContainer.innerHTML = "";

    tabs.forEach(tab => {
      tab.addEventListener("click", () => {
        tabs.forEach(t => t.classList.remove("ativo"));
        tab.classList.add("ativo");
        conteudoContainer.innerHTML = "";

        if (tab.textContent === "Utensílios") {
          if (receita.utensilios.length === 0) {
            conteudoContainer.innerHTML = "<p class='conteudo'>Nenhum utensílio necessário.</p>";
          } else {
            receita.utensilios.forEach(u => {
              conteudoContainer.innerHTML += `<p class="conteudo">${u.utensilio.nome}</p>`;
            });
          }
        } else if (tab.textContent === "Ingredientes") {
          receita.ingredientes.forEach(i => {
            conteudoContainer.innerHTML += `
              <p class="conteudo">${i.quantidade}${i.unidadeMedida} de ${i.ingrediente.nome}</p>
            `;
          });
        }
      });
    });

    // Ativa a aba inicial
    tabs[0].click();

    // Botão "Começar"
    const botaoComecar = document.querySelector(".btn-comecar");
    if (botaoComecar) {
      botaoComecar.addEventListener("click", () => {
        window.location.href = `../etapa-receita/etapa-receita.html?id=${receitaId}`;
      });
    }

  } catch (error) {
    console.error("Erro ao carregar receita:", error);
    document.querySelector(".receita-container").innerHTML = "<p>Erro ao carregar receita.</p>";
  }
});

// Evento de carregamento
window.addEventListener("load", configurarEventos);
