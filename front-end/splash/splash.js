function redirecionar() {
  window.location.href = "/cadastrar/cadastrar.html"
}

function carregarEventos() {
  const cadastrar = document.getElementById("cadastrar");
  cadastrar.addEventListener("click", redirecionar);
}

window.addEventListener("load", carregarEventos);
