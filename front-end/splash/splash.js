// Função responsável por redirecionar o usuário para a página de cadastro
function redirecionar() {
  // Altera o endereço da página atual para o caminho especificado
  // Isso faz com que o navegador carregue a página "cadastrar.html"
  window.location.href = "/cadastrar/cadastrar.html";
}

// Função que configura os eventos da página
function configurarEventos() {
  // Obtém o botão com ID "cadastrar" do DOM (html)
  const cadastrar = document.getElementById("cadastrar");

  // Adiciona um ouvinte de evento de clique ao botão
  // Quando o botão for clicado, a função "redirecionar" será executada
  cadastrar.addEventListener("click", redirecionar);
}

// Adiciona um ouvinte de evento à janela
// Quando a página terminar de carregar, executa a função "configurarEventos"
// Isso garante que os elementos do DOM estejam disponíveis antes de serem acessados
window.addEventListener("load", configurarEventos);
