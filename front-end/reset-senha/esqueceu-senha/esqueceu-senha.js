document.addEventListener('DOMContentLoaded', () => {
    const emailInput = document.getElementById('email');
    const submitButton = document.getElementById('enviar');

    // Função para validar o email
    function validateEmail(email) {
        const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
        return regex.test(email);
    }

    // Evento que monitora a entrada no campo de email
    emailInput.addEventListener('input', () => {
        const emailValue = emailInput.value
        // Verifica se o email é válido
        if (validateEmail(emailValue)) {
            emailInput.classList.remove('invalido');
            emailInput.classList.add('valido');
            submitButton.disabled = false;  // Habilita o botão
        } else {
            emailInput.classList.remove('valido');
            emailInput.classList.add('invalido');
            submitButton.disabled = true;  // Desabilita o botão
        }
    });

    submitButton.addEventListener('click', (event) => {
        event.preventDefault(); // Impede o comportamento padrão (não recarregar a página)

        // Redireciona para a página desejada se o email for válido
        const emailValue = emailInput.value;
        if (validateEmail(emailValue)) {
            fetch("http://localhost:8080/1/codigo", {
        method: "POST",
        headers: {
            "Content-Type": "application/json", // Define que o conteúdo enviado é do tipo JSON
        },
    })
        .then((data) => data.json()) // converte a resposta da API para JSON
        .then((response) => {
            console.log(response); // exibe a resposta no console
            window.location.href = "../validacao-codigo/validacao-codigo.html";
        });
        } else {
            alert("Por favor, insira um email válido.");
        }
    });
});