
function realizarRegistro() {
    const inputNome = document.getElementById("input_nome");
    const inputEmail = document.getElementById("input_email");
    const inputSenha = document.getElementById("input_senha");

    const nome = inputNome.value;
    const email = inputEmail.value;
    const senha = inputSenha.value;

    console.log(nome, email, senha)

    if (!email || !senha || !nome) {
        alert("Preencha todos os campos!");
        return;
    }

    fetch("http://localhost:8080/auth/register", {
        method: "POST",
        body: JSON.stringify({ // converte para o formato que o servidor espera os dados
            email,
            senha,
            nome
        }),
        headers: {
            "Content-Type": "application/json", // Define que o conteúdo enviado é do tipo JSON
        },
    })
        .then((data) => data.json()) // converte a resposta da API para JSON
        .then((response) => {
            console.log(response); // exibe a resposta no console
        });
    }

    function configurarEventos() {
        const botaoRegistrar = document.getElementById("registrar");
        botaoRegistrar.addEventListener("click", realizarRegistro);
    }

    window.addEventListener("load", configurarEventos);