document.addEventListener("DOMContentLoaded", () => {
  carregarIngredientes();
  configurarModalAdicionar();
});

function carregarIngredientes() {
  const idUsuario = JSON.parse(localStorage.getItem("id"));
  const token = localStorage.getItem("token");

  if (!idUsuario || !token) {
    console.error("ID ou token não encontrado no localStorage.");
    return;
  }

  fetch(`http://localhost:8080/usuarios/${idUsuario}/estoque-virtual`, {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    }
  })
    .then(response => {
      if (!response.ok) throw new Error("Erro ao buscar ingredientes");
      return response.json();
    })
    .then(dados => {
      const container = document.getElementById("lista-ingredientes");
      container.innerHTML = "";

      dados.forEach(item => {
        const ingrediente = item.ingrediente;
        const idAssociacao = item.id; // ID da associação
        const card = criarCardIngrediente(ingrediente, idAssociacao);
        container.appendChild(card);
      });
    })
    .catch(error => {
      console.error("Erro:", error);
    });
}

function criarCardIngrediente(ingrediente, idAssociacao) {
  const card = document.createElement("div");
  card.className = "card";
  card.dataset.ingredienteId = ingrediente.id; // usado para verificação de duplicidade

  card.innerHTML = `
    <div class="svg-container">
      <!-- SVG permanece igual -->
    </div>
    <div class="info">
      <h3>${ingrediente.nome}</h3>
      <p>Categoria: ${ingrediente.categoria}</p>
      <p>Validade estimada: ${ingrediente.estimativaValidade} dias</p>
    </div>
    <div class="acoes">
      <button class="btn-deletar" data-id="${idAssociacao}" title="Remover ingrediente">🗑️</button>
    </div>
  `;

  const botaoDeletar = card.querySelector(".btn-deletar");
  botaoDeletar.addEventListener("click", () => {
    if (confirm(`Deseja remover "${ingrediente.nome}" da despensa?`)) {
      deletarIngrediente(idAssociacao);
    }
  });

  return card;
}

function deletarIngrediente(idAssociacao) {
  const idUsuario = JSON.parse(localStorage.getItem("id"));
  const token = localStorage.getItem("token");

  if (!idUsuario || !token) {
    alert("Sessão expirada. Faça login novamente.");
    return;
  }

  fetch(`http://localhost:8080/usuarios/${idUsuario}/estoque-virtual/${idAssociacao}`, {
    method: "DELETE",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    }
  })
    .then(response => {
      if (!response.ok) throw new Error("Erro ao excluir ingrediente");
      alert("Ingrediente removido com sucesso!");
      carregarIngredientes();
    })
    .catch(error => {
      console.error("Erro ao excluir ingrediente:", error);
      alert("Erro ao excluir ingrediente.");
    });
}

function configurarModalAdicionar() {
  const botaoAbrir = document.getElementById("simbolo");
  const modal = document.getElementById("modalIngrediente");
  const botaoSalvar = document.getElementById("botaoSalvar");

  botaoAbrir.addEventListener("click", () => {
    modal.classList.remove("hidden");
    carregarListaIngredientes();
  });

  botaoSalvar.addEventListener("click", salvarIngrediente);
}

function carregarListaIngredientes() {
  const token = localStorage.getItem("token");

  if (!token) {
    console.error("Token não encontrado no localStorage.");
    return;
  }

  fetch("http://localhost:8080/ingredientes", {
    method: "GET",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    }
  })
    .then(response => {
      if (!response.ok) throw new Error("Erro ao buscar ingredientes disponíveis");
      return response.json();
    })
    .then(ingredientes => {
      const select = document.getElementById("select-ingrediente");
      if (!select) {
        console.error("Elemento <select> não encontrado no DOM.");
        return;
      }

      select.innerHTML = "";

      if (ingredientes.length === 0) {
        const option = document.createElement("option");
        option.textContent = "Nenhum ingrediente disponível";
        option.disabled = true;
        option.selected = true;
        select.appendChild(option);
        return;
      }

      ingredientes.forEach(ingrediente => {
        const option = document.createElement("option");
        option.value = ingrediente.id;
        option.textContent = `${ingrediente.nome} (${ingrediente.categoria})`;
        select.appendChild(option);
      });
    })
    .catch(error => {
      console.error("Erro ao carregar lista de ingredientes:", error);
    });
}

function salvarIngrediente() {
  const idUsuario = JSON.parse(localStorage.getItem("id"));
  const token = localStorage.getItem("token");
  const ingredienteId = parseInt(document.getElementById("select-ingrediente").value);

  // Verifica se o ingrediente já está na despensa
  const cards = document.querySelectorAll(".card");
  for (const card of cards) {
    const idExistente = parseInt(card.dataset.ingredienteId);
    if (idExistente === ingredienteId) {
      alert("Este ingrediente já está na despensa.");
      return;
    }
  }

  const dados = { idIngrediente: ingredienteId };

  fetch(`http://localhost:8080/usuarios/${idUsuario}/estoque-virtual`, {
    method: "POST",
    headers: {
      "Authorization": `Bearer ${token}`,
      "Content-Type": "application/json"
    },
    body: JSON.stringify(dados)
  })
    .then(response => {
      if (!response.ok) throw new Error("Erro ao salvar ingrediente");
      return response.json();
    })
    .then(() => {
      alert("Ingrediente adicionado com sucesso!");
      document.getElementById("modalIngrediente").classList.add("hidden");
      carregarIngredientes();
    })
    .catch(error => {
      console.error("Erro ao salvar ingrediente:", error);
      alert("Erro ao salvar ingrediente.");
    });
}
