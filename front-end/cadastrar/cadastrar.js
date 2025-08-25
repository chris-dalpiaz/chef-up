// Função responsável por realizar o registro do usuário
function realizarRegistro() {
    // Obtém o elemento de input do nome pelo ID
    const inputNome = document.getElementById("input_nome");

    // Obtém o elemento de input do email pelo ID
    const inputEmail = document.getElementById("input_email");

    // Obtém o elemento de input da senha pelo ID
    const inputSenha = document.getElementById("input_senha");

    // Captura o valor digitado no campo de nome
    const nome = inputNome.value;

    // Captura o valor digitado no campo de email
    const email = inputEmail.value;

    // Captura o valor digitado no campo de senha
    const senha = inputSenha.value;

    // Exibe os valores capturados no console para fins de depuração
    console.log(nome, email, senha);

    // Verifica se algum dos campos está vazio (após remover espaços em branco: trim())
    if (!email.trim() || !senha.trim() || !nome.trim()) {
        // Exibe um alerta informando que todos os campos devem ser preenchidos
        alert("Preencha todos os campos!");
        // Interrompe a execução da função
        return;
    }

    // Envia os dados para o servidor usando a API Fetch
    fetch("http://localhost:8080/auth/register", {
        // Define o método HTTP como POST, usado para envio de dados
        method: "POST",

        // Converte os dados do usuário para uma string JSON, que é o formato esperado pelo servidor
        body: JSON.stringify({
            email, // campo de email
            senha, // campo de senha
            nome   // campo de nome
        }),

        // Define os cabeçalhos da requisição
        headers: {
            // Informa ao servidor que o corpo da requisição está no formato JSON
            "Content-Type": "application/json",
        },
    })
        // Converte a resposta da requisição para JSON
        .then((data) => data.json())

        // Manipula a resposta convertida
        .then((response) => {
            // Exibe a resposta no console (pode conter informações como status ou mensagem)
            console.log(response);

            // Exibe um alerta informando que o cadastro foi realizado com sucesso
            alert("Cadastro realizado com sucesso!");

            // Redireciona o usuário para a página de login
            window.location.href = "../entrar/entrar.html";
        });
}

// Função que configura os eventos da página
function configurarEventos() {
    // Obtém o botão de registro pelo ID
    const botaoRegistrar = document.getElementById("registrar");

    // Adiciona um ouvinte de evento de clique ao botão
    // Quando clicado, executa a função de registro
    botaoRegistrar.addEventListener("click", realizarRegistro);
}

// Adiciona um ouvinte de evento à janela
// Quando a página terminar de carregar, executa a função de configuração dos eventos
window.addEventListener("load", configurarEventos);
