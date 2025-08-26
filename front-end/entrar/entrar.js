// Função que realiza o login do usuário
function realizarLogin() {
    // Obtém os elementos de input do email e da senha
    const inputEmail = document.getElementById("input_email");
    const inputSenha = document.getElementById("input_senha");

    // Pega os valores digitados pelo usuário
    const email = inputEmail.value;
    const senha = inputSenha.value;

    // Exibe os valores no console para fins de depuração
    console.log(email, senha);

    // Verifica se os campos estão preenchidos (sem espaços em branco - trim())
    if (!email.trim() || !senha.trim()) {
        alert("Preencha todos os campos!");
        return; // Encerra a função se algum campo estiver vazio
    }

    // Envia os dados de login para a API
    fetch("http://localhost:8080/auth/login", {
        method: "POST", // Método HTTP usado para enviar dados
        body: JSON.stringify({ email, senha }), // Converte os dados para JSON
        headers: {
            "Content-Type": "application/json", // Informa que o corpo da requisição é JSON
        },
    })
        // Converte a resposta da API para JSON
        .then((data) => data.json())

        // Manipula a resposta recebida
        .then((response) => {
            const usuario = response.usuario; // Extrai os dados do usuário da resposta

            // Armazena o token de autenticação no localStorage
            localStorage.setItem("token", response.token);

            // Armazena dados básicos do usuário
            localStorage.setItem("email", usuario.email);
            localStorage.setItem("nome", usuario.nome);
            localStorage.setItem("pronome", JSON.stringify(usuario.pronome));

            // Armazena o ID do usuário
            localStorage.setItem("id", JSON.stringify(usuario.id));

            // Armazena dados adicionais do perfil
            localStorage.setItem("titulos", JSON.stringify(usuario.titulos));
            localStorage.setItem("adjetivos", JSON.stringify(usuario.adjetivos));
            localStorage.setItem("receitasConcluidas", JSON.stringify(usuario.receitasConcluidas));
            localStorage.setItem("progresso", JSON.stringify(usuario.progresso));
            localStorage.setItem("ingredientes", JSON.stringify(usuario.ingredientes));
            localStorage.setItem("avatares", JSON.stringify(usuario.avatares));

            // Chama função para carregar dados do usuário
            carregarUsuario();

            // Exibe a resposta no console
            console.log(response);

            // Informa ao usuário que o login foi bem-sucedido
            alert("Login realizado com sucesso!");

            // Redireciona o usuário para a próxima página
            redirecionarUsuario();
        })

        // Trata erros que possam ocorrer durante o processo de login
        .catch((error) => {
            console.log(error); // Exibe o erro no console
            alert("Usuário ou senha incorretos"); // Informa ao usuário
        });
}

// Função que redireciona o usuário após login bem-sucedido
function redirecionarUsuario() {
    console.log("Redirecionando...");
    // Redireciona para a página de boas-vindas
    window.location.href = "../boas-vindas/aprenda-receitas/aprenda-receitas.html";
}

// Função que configura os eventos da página
function configurarEventos() {
    // Obtém o botão de login pelo ID
    const botaoEntrar = document.getElementById("botao_entrar");

    // Adiciona um ouvinte de evento de clique ao botão
    // Quando clicado, chama a função de login
    botaoEntrar.addEventListener("click", realizarLogin);
}

// Quando a página terminar de carregar, configura os eventos
window.addEventListener("load", configurarEventos);
