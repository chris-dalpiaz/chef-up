function carregarEvento() {
    let resetBtn = document.getElementById("reset_senha");

    resetBtn.addEventListener('click', (event) => {
        const novaSenha = document.getElementById("new_senha").value;
        const confirmarSenha = document.getElementById("confirm_senha").value;
        const emailValue = localStorage.getItem('email');

        if (novaSenha == confirmarSenha) {
            fetch("http://localhost:8080/codigos", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({email: emailValue, novaSenha: novaSenha})
            })
            .then((response) => response.json())
            .then((response) => {
                console.log(response)
                if (response.senhaAlterada == true) {
                    alert("Senha alterada com sucesso!")
                    localStorage.clear('email');
                    window.location.href = "../../entrar/entrar.html"
                } else {
                    alert("Falha ao alterar a senha");
                }
            })
        }
    })
}

window.addEventListener('load',
    carregarEvento);