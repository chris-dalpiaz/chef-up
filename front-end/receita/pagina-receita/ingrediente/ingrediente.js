document.addEventListener("DOMContentLoaded", async () => {
    console.log("ingrediente.js: Iniciando...");
  
    const token = localStorage.getItem("token");
    const urlParams = new URLSearchParams(window.location.search);
    const receitaId = urlParams.get("id");
  
    if (!token || !receitaId) {
      console.error("ingrediente.js: Token ou receitaId ausente");
      alert("Receita não encontrada ou usuário não autenticado.");
      return;
    }
  
    try {
      console.log(`ingrediente.js: Buscando receita com ID ${receitaId}`);
      const response = await fetch(`http://localhost:8080/receitas/${receitaId}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
  
      if (!response.ok) {
        console.error(`ingrediente.js: Erro na requisição - Status ${response.status}`);
        throw new Error("Erro ao buscar receita");
      }
  
      const receita = await response.json();
      console.log("ingrediente.js: Receita recebida:", receita);
  
      // Atualiza imagem
      const imgElement = document.querySelector(".recipe-image img");
      if (imgElement) {
        imgElement.src = receita.imagemReceita || "https://via.placeholder.com/300";
        imgElement.alt = receita.nome || "Receita";
      } else {
        console.error("ingrediente.js: Elemento .recipe-image img não encontrado");
      }
  
      // Atualiza título
      const titleElement = document.querySelector(".recipe-title");
      if (titleElement) {
        titleElement.textContent = receita.nome || "Sem título";
      } else {
        console.error("ingrediente.js: Elemento .recipe-title não encontrado");
      }
  
      // Atualiza tempo e porções
      const infoElement = document.querySelector(".recipe-info");
      if (infoElement) {
        const minutos = receita.tempoPreparoSegundos
          ? Math.ceil(receita.tempoPreparoSegundos / 60)
          : "Desconhecido";
        infoElement.textContent = `${minutos} minutos • 2 porções`;
      } else {
        console.error("ingrediente.js: Elemento .recipe-info não encontrado");
      }
  
      // Atualiza XP
      const xpText = document.querySelector(".xp-text");
      if (xpText) {
        xpText.textContent = `${receita.xpGanho || 0} XP`;
      } else {
        console.error("ingrediente.js: Elemento .xp-text não encontrado");
      }
  
      // Tabs e conteúdo
      const tabs = document.querySelectorAll(".tab");
      const utensiliosContent = document.querySelector("#utensilios-content");
      const ingredientesContent = document.querySelector("#ingredientes-content");
  
      if (!utensiliosContent || !ingredientesContent) {
        console.error("ingrediente.js: Elementos #utensilios-content ou #ingredientes-content não encontrados");
        return;
      }
  
      // Função switchTab para compatibilidade com o HTML
      window.switchTab = function(tabName) {
        console.log(`ingrediente.js: switchTab chamado para ${tabName}`);
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
  
      // Adiciona eventos dinâmicos para redundância
      tabs.forEach(tab => {
        tab.addEventListener("click", () => {
          const tabName = tab.getAttribute("onclick").match(/switchTab\('(.+)'\)/)[1];
          switchTab(tabName);
        });
      });
  
      // Ativa a aba inicial (Ingredientes, conforme HTML)
      const activeTab = document.querySelector(".tab.active");
      if (activeTab) {
        console.log("ingrediente.js: Ativando aba inicial");
        switchTab("ingredientes");
      } else {
        console.error("ingrediente.js: Nenhuma aba com classe .active encontrada");
      }
  
      // Botão "Começar"
      const botaoComecar = document.querySelector(".comecar-btn");
      if (botaoComecar) {
        botaoComecar.addEventListener("click", () => {
          console.log("ingrediente.js: Botão Começar clicado");
          window.location.href = `../etapa-receita/etapa-receita.html?id=${receitaId}`;
        });
      } else {
        console.error("ingrediente.js: Elemento .comecar-btn não encontrado");
      }
  
    } catch (error) {
      console.error("ingrediente.js: Erro ao carregar receita:", error);
      document.querySelector("#container").innerHTML = "<p>Erro ao carregar receita.</p>";
    }
  });
  
  function voltar() {
    console.log("ingrediente.js: Botão Voltar clicado");
    window.history.back();
  }
  
  function toggleFavorito() {
    console.log("ingrediente.js: Botão Favorito clicado");
    const path = document.getElementById("coracao-path");
    if (path) {
      const isFavorito = path.getAttribute("fill") === "#f05a28";
      path.setAttribute("fill", isFavorito ? "none" : "#f05a28");
    } else {
      console.error("ingrediente.js: Elemento #coracao-path não encontrado");
    }
  }