let etapas = [];
let etapaAtual = 0;

document.addEventListener("DOMContentLoaded", async () => {
  const token = localStorage.getItem("token");
  const urlParams = new URLSearchParams(window.location.search);
  const receitaId = urlParams.get("id");

  if (!token || !receitaId) {
    alert("Receita não encontrada ou usuário não autenticado.");
    return;
  }

  try {
    const res = await fetch(`http://localhost:8080/receitas/${receitaId}/etapas`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (!res.ok) throw new Error("Erro ao buscar etapas");

    etapas = await res.json();
    renderizarBarraProgresso();
    mostrarEtapa(0);
  } catch (error) {
    console.error("Erro ao carregar etapas:", error);
  }
});

function mostrarEtapa(index) {
  const etapa = etapas[index];
  if (!etapa) return;

  document.getElementById("etapa-numero").textContent = etapa.ordem.toString().padStart(2, "0");
  document.getElementById("etapa-conteudo").textContent = etapa.conteudo;
  document.getElementById("etapa-ingredientes").innerHTML = ""; // se quiser listar ingredientes específicos

  const dots = document.querySelectorAll(".dot");
  dots.forEach((dot, i) => {
    dot.classList.toggle("active", i <= index);
  });

  etapaAtual = index;
}

function proximaEtapa() {
  if (etapaAtual < etapas.length - 1) {
    mostrarEtapa(etapaAtual + 1);
  } else {
    alert("Você concluiu todas as etapas!");
  }
}

function renderizarBarraProgresso() {
  const progressBar = document.getElementById("progress-bar");
  progressBar.innerHTML = "";
  etapas.forEach(() => {
    const dot = document.createElement("div");
    dot.className = "dot";
    progressBar.appendChild(dot);
  });
}

function voltar() {
  window.history.back();
}
