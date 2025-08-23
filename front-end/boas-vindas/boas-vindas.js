function redirecionarPerfil() {
    window.location.href = "../../perfil-usuario/perfil/perfil.html"
}

function irParaControleDespensa() {
    window.location.href = "../controle-despensa/controle-despensa.html"
}

function irParaExperiencieComAmigos() {
    window.location.href = "../experiencie-com-amigos/experiencie-com-amigos.html"
}


function carregarEventos() {
    const botaoPular = document.getElementById("pular");
    const botaoPular1 = document.getElementById("pular1");
    const botaoProximo1 = document.getElementById("proximo1");
    const botaoProximo2 = document.getElementById("proximo2");
    
    if (botaoPular) {
        botaoPular.addEventListener("click", redirecionarPerfil);
    }

    if (botaoPular1) {
        botaoPular1.addEventListener("click", redirecionarPerfil);
    }

    if (botaoProximo1) {
        botaoProximo1.addEventListener("click", irParaControleDespensa);
    }

    if (botaoProximo2) {
        botaoProximo2.addEventListener("click", irParaExperiencieComAmigos);
    }
}

window.addEventListener("load", carregarEventos);
