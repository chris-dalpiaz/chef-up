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
  