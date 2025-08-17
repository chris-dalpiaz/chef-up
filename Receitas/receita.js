// Seleciona todos os elementos do DOM que possuem a classe 'footer-item' e guarda na variável footerItems
const footerItems = document.querySelectorAll('.footer-item');

// Para cada elemento dentro de footerItems, executa a função passada
footerItems.forEach(item => {
  // Adiciona um listener para o evento de clique no item atual
  item.addEventListener('click', () => {
    // Ao clicar, percorre todos os footerItems e remove a classe 'selected' de cada um
    footerItems.forEach(i => i.classList.remove('selected'));
    // Adiciona a classe 'selected' somente no item que foi clicado, para destacar ele
    item.classList.add('selected');
  });
});

window.addEventListener("load", carregarReceitas);

async function carregarReceitas() {
  const token = localStorage.getItem("token");
  const container = document.querySelector(".receitas_container");

  try {
    const res = await fetch("http://localhost:8080/receitas", {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!res.ok) throw new Error("Erro ao buscar receitas");

    const receitas = await res.json();
    container.innerHTML = "";

    receitas.forEach(r => {
      const card = document.createElement("div");
      card.className = "receita_card";

      // 👇 clique redireciona para página de detalhes
      card.addEventListener("click", () => {
        window.location.href = `detalhe_receita.html?id=${r.id}`;
      });

      card.innerHTML = `
        <img src="${r.imagemReceita}" alt="${r.nome}" class="receita_imagem">
        <h2>${r.nome}</h2>
      `;

      container.appendChild(card);
    });

  } catch (error) {
    console.error("Erro ao carregar receitas:", error);
    container.innerHTML = "<p>Não foi possível carregar as receitas.</p>";
  }
}
