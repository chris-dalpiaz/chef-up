// Alternar abas
const tabs = document.querySelectorAll(".tab");

tabs.forEach(tab => {
  tab.addEventListener("click", () => {
    tabs.forEach(t => t.classList.remove("ativo"));
    tab.classList.add("ativo");
  });
});

// Ação dos botões superiores
function voltar() {
    window.history.back();
  }
  
  function abrirXP() {
    alert("Abrindo painel de XP...");
  }
  

  let favoritado = false;

function toggleFavorito() {
  const coracao = document.getElementById("coracao-path");
  favoritado = !favoritado;

  if (favoritado) {
    coracao.setAttribute("fill", "#FF4E4E"); // cor vermelha preenchida
    coracao.setAttribute("stroke", "#FF4E4E");
  } else {
    coracao.setAttribute("fill", "none");
    coracao.setAttribute("stroke", "#000");
  }
}

