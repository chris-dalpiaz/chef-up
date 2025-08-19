window.addEventListener("load", carregarReceitas);

// Destaca o item do footer clicado
document.querySelectorAll('.footer-item').forEach(item => {
  item.addEventListener('click', () => {
    document.querySelectorAll('.footer-item').forEach(i => i.classList.remove('selected'));
    item.classList.add('selected');
  });
});

async function carregarReceitas() {
  const token = localStorage.getItem("token");
  const container = document.querySelector(".receitas_container");

  if (!token) {
    container.innerHTML = "<p>Usuário não autenticado.</p>";
    return;
  }

  try {
    const response = await fetch("http://localhost:8080/receitas", {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!response.ok) {
      throw new Error("Erro ao buscar receitas");
    }

    const receitas = await response.json();
    container.innerHTML = "";

    receitas.forEach(receita => {
      const card = document.createElement("div");
      card.className = "receita_card";

      card.addEventListener("click", () => {
        window.location.href = `detalhe_receita.html?id=${receita.id}`;
      });

      card.innerHTML = `
        <img src="${receita.imagemReceita}" alt="${receita.nome}" class="receita_imagem">
        <h2>${receita.nome}</h2>
      `;

      container.appendChild(card);
    });

  } catch (error) {
    console.error("Erro ao carregar receitas:", error);
    container.innerHTML = "<p>Não foi possível carregar as receitas.</p>";
  }
}
