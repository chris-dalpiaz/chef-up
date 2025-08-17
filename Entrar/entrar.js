// Função que realiza o login do usuário
function realizarLogin() {
    // Obtém os elementos de input do email e da senha
    const inputEmail = document.getElementById("input_email");
    const inputSenha = document.getElementById("input_senha");

    // Pega os valores digitados pelo usuário
    const email = inputEmail.value;
    const senha = inputSenha.value;

    console.log(email, senha); // Exibe os valores no console para debug

    // Verifica se os campos estão preenchidos
    if (!email.trim() || !senha.trim()) {
        alert("Preencha todos os campos!");
        return; // Encerra a função se algum campo estiver vazio
    }

    fetch("http://localhost:8080/auth/login", {
        method: "POST",
        body: JSON.stringify({ email, senha }),
        headers: {
            "Content-Type": "application/json",
        },
    })
        .then((data) => data.json())
        .then((response) => {
            const usuario = response.usuario;

            // Dados básicos
            localStorage.setItem("token", response.token);
            localStorage.setItem("email", usuario.email);
            localStorage.setItem("nome", usuario.nome);
            localStorage.setItem("pronome", JSON.stringify(usuario.pronome));

            // Titulos
            localStorage.setItem("titulos", JSON.stringify(usuario.titulos));

            // Adjetivos
            localStorage.setItem("adjetivos", JSON.stringify(usuario.adjetivos));

            // Receitas concluídas
            localStorage.setItem("receitasConcluidas", JSON.stringify(usuario.receitasConcluidas));

            // Progresso
            localStorage.setItem("progresso", JSON.stringify(usuario.progresso));

            // Ingredientes
            localStorage.setItem("ingredientes", JSON.stringify(usuario.ingredientes));

            // Avatares
            localStorage.setItem("avatares", JSON.stringify(usuario.avatares));

            // Chamada de função e feedback
            carregarUsuario();
            console.log(response);
            alert("Login realizado com sucesso!");
            redirecionarUsuario();
        })
        .catch((error) => {
            console.log(error);
            alert("Usuário ou senha incorretos");
        });
}

function redirecionarUsuario() {
    console.log("Redirecionando...");
    window.location.href = "../perfil-usuario/perfil/perfil.html";
}

// Função que configura os eventos da página
function configurarEventos() {
    const botaoEntrar = document.getElementById("botao_entrar"); // Obtém o botão de login
    botaoEntrar.addEventListener("click", realizarLogin); // Adiciona o evento de clique para chamar a função de login
}

// Quando a página carregar, configura os eventos
window.addEventListener("load", configurarEventos);