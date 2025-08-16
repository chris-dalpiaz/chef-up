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

    // Envia os dados para a API de login
    fetch("http://localhost:8080/auth/login", {
        method: "POST", // Método HTTP POST
        body: JSON.stringify({ // Converte os dados para JSON
            email,
            senha
        }),
        headers: {
            "Content-Type": "application/json", // Informa que o corpo da requisição é JSON
        },
    })
    .then((data) => data.json()) // Converte a resposta da API para JSON
    .then((response) => {
        localStorage.setItem("token", response.token); // Salva o token no armazenamento local
        localStorage.setItem("email", response.usuario.email); // salva o e-mail no armazenamento local
        localStorage.setItem("pronome", response.usuario.pronome); // salva o pronome
        
        carregarUsuario(); // Chama a função para carregar os usuários
        console.log(response); // Exibe a resposta no console
        alert("Login realizado com sucesso!"); // Exibe mensagem de sucesso
    })
    .catch((error) => {
        console.log(error); // Exibe o erro no console
        alert("Usuário ou senha incorretos"); // Exibe mensagem de erro
    });
}

// Função que configura os eventos da página
function configurarEventos() {
    const botaoEntrar = document.getElementById("botao_entrar"); // Obtém o botão de login
    botaoEntrar.addEventListener("click", realizarLogin); // Adiciona o evento de clique para chamar a função de login
}

// Quando a página carregar, configura os eventos
window.addEventListener("load", configurarEventos);