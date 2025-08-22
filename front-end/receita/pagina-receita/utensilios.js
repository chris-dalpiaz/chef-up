document.addEventListener("DOMContentLoaded", async () => {
  const token = localStorage.getItem("token");
  const urlParams = new URLSearchParams(window.location.search);
  const receitaId = urlParams.get("id");

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
    document.querySelector(".recipe-image img").src = receita.imagemReceita || "https://via.placeholder.com/300";
    document.querySelector(".recipe-image img").alt = receita.nome || "Receita";

    // Atualiza título
    document.querySelector(".recipe-title").textContent = receita.nome || "Sem título";

    // Atualiza tempo e porções
    const minutos = receita.tempoPreparoSegundos
      ? Math.ceil(receita.tempoPreparoSegundos / 60)
      : "Desconhecido";
    document.querySelector(".recipe-info").textContent = `${minutos} minutos • 2 porções`;

    // Atualiza XP
    const xpText = document.querySelector(".xp-text");
    if (xpText) xpText.textContent = `${receita.xpGanho || 0} XP`;

    // Tabs e conteúdo
    const tabs = document.querySelectorAll(".tab");
    const utensiliosContent = document.querySelector("#utensilios-content");
    const ingredientesContent = document.querySelector("#ingredientes-content");

    // Função switchTab para compatibilidade com o HTML
    window.switchTab = function(tabName) {
      tabs.forEach(t => t.classList.remove("active"));
      utensiliosContent.classList.add("hidden");
      ingredientesContent.classList.add("hidden");

      if (tabName === "utensilios") {
        document.querySelector(".tab[onclick*='utensilios']").classList.add("active");
        utensiliosContent.classList.remove("hidden");
        utensiliosContent.innerHTML = "";
        const itemList = document.createElement("div");
        itemList.classList.add("item-list");
        if (!receita.utensilios || receita.utensilios.length === 0) {
          itemList.innerHTML = "<div class='item'>Nenhum utensílio necessário.</div>";
        } else {
          receita.utensilios.forEach(u => {
            const item = document.createElement("div");
            item.classList.add("item");
            item.textContent = u.utensilio?.nome || "Utensílio desconhecido";
            itemList.appendChild(item);
          });
        }
        utensiliosContent.appendChild(itemList);
      } else if (tabName === "ingredientes") {
        document.querySelector(".tab[onclick*='ingredientes']").classList.add("active");
        ingredientesContent.classList.remove("hidden");
        ingredientesContent.innerHTML = "";
        const ingredientsTable = document.createElement("div");
        ingredientsTable.classList.add("ingredients-table");
        if (!receita.ingredientes || receita.ingredientes.length === 0) {
          ingredientsTable.innerHTML = "<div class='ingredient-row'><span class='ingredient-name'>Nenhum ingrediente necessário.</span></div>";
        } else {
          receita.ingredientes.forEach(i => {
            const ingredientRow = document.createElement("div");
            ingredientRow.classList.add("ingredient-row");
            ingredientRow.innerHTML = `
              <span class="ingredient-name">${i.ingrediente?.nome || "Ingrediente desconhecido"}</span>
              <span class="ingredient-amount">${i.quantidade || ""}${i.unidadeMedida || ""}</span>
            `;
            ingredientsTable.appendChild(ingredientRow);
          });
        }
        ingredientesContent.appendChild(ingredientsTable);
      }
    };

    // Adiciona eventos dinâmicos
    tabs.forEach(tab => {
      tab.addEventListener("click", () => {
        const tabName = tab.getAttribute("onclick")?.match(/switchTab\('(.+)'\)/)?.[1];
        if (tabName) switchTab(tabName);
      });
    });

    // Ativa a aba inicial (Utensílios, conforme HTML)
    document.querySelector(".tab.active").click();

    // Botão "Começar"
    const botaoComecar = document.querySelector(".comecar-btn");
    if (botaoComecar) {
      botaoComecar.addEventListener("click", () => {
        window.location.href = `../etapa-receita/etapa-receita.html?id=${receitaId}`;
      });
    }

  } catch (error) {
    console.error("Erro ao carregar receita:", error);
    document.querySelector("#container").innerHTML = "<p>Erro ao carregar receita.</p>";
  }
});

function voltar() {
  window.history.back();
}

function toggleFavorito() {
  const path = document.getElementById("coracao-path");
  const isFavorito = path.getAttribute("fill") === "#f05a28";
  path.setAttribute("fill", isFavorito ? "none" : "#f05a28");
}