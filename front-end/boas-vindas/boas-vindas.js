// Função responsável por redirecionar o usuário para a página de perfil
function redirecionarPerfil() {
    // Altera o endereço da página atual para o caminho especificado
    // Isso faz com que o navegador carregue a página "perfil.html"
    window.location.href = "../../perfil-usuario/perfil/perfil.html";
}

// Função que redireciona o usuário para a página de controle da despensa
function irParaControleDespensa() {
    // Redireciona para "controle-despensa.html", que está em um diretório acima
    window.location.href = "../controle-despensa/controle-despensa.html";
}

// Função que redireciona o usuário para a página de experiência com amigos
function irParaExperiencieComAmigos() {
    // Redireciona para "experiencie-com-amigos.html", também em um diretório acima
    window.location.href = "../experiencie-com-amigos/experiencie-com-amigos.html";
}

// Função que associa eventos de clique aos botões da interface
function configurarEventos() {
    // Obtém o elemento com ID "pular" do DOM (Document Object Model)
    const botaoPular = document.getElementById("pular");

    // Obtém o elemento com ID "pular1"
    const botaoPular1 = document.getElementById("pular1");

    // Obtém o elemento com ID "proximo1"
    const botaoProximo1 = document.getElementById("proximo1");

    // Obtém o elemento com ID "proximo2"
    const botaoProximo2 = document.getElementById("proximo2");

    // Verifica se o botão "pular" existe na página
    if (botaoPular) {
        // Adiciona um ouvinte de evento de clique que chama a função de redirecionamento para o perfil
        botaoPular.addEventListener("click", redirecionarPerfil);
    }

    // Verifica se o botão "pular1" existe
    if (botaoPular1) {
        // Adiciona o evento de clique para redirecionar ao perfil
        botaoPular1.addEventListener("click", redirecionarPerfil);
    }

    // Verifica se o botão "proximo1" existe
    if (botaoProximo1) {
        // Adiciona o evento de clique para redirecionar à página de controle da despensa
        botaoProximo1.addEventListener("click", irParaControleDespensa);
    }

    // Verifica se o botão "proximo2" existe
    if (botaoProximo2) {
        // Adiciona o evento de clique para redirecionar à página de experiência com amigos
        botaoProximo2.addEventListener("click", irParaExperiencieComAmigos);
    }
}

// Adiciona um ouvinte de evento à janela que será executado quando a página for totalmente carregada
// Isso garante que os elementos do DOM (html) estejam disponíveis antes de tentar acessá-los
window.addEventListener("load", configurarEventos);
