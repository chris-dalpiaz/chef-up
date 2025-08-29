function carregarEvento() {
    const compareBtn = document.getElementById("continuar");

    compareBtn.addEventListener('click', (event) => {
        event.preventDefault();

        const n1 = document.getElementById("number1").value;
        const n2 = document.getElementById("number2").value;
        const n3 = document.getElementById("number3").value;
        const n4 = document.getElementById("number4").value;

        let codeValue = ""+n1+""+n2+""+n3+""+n4+"";

        const emailValue = localStorage.getItem('email');

        fetch("http://localhost:8080/codigos/validacao", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({email: emailValue, digitCode: codeValue})
        })
        .then((response) => response.json())
        .then((response) => {
            console.log(response)
            if (response.codigoValido == true) {
                window.location.href = "../redefinir-senha/redefinir-senha.html";
            } else {
                alert("Código de Verificação Incorreto")
            }
        })
    })
}

window.addEventListener('load',
    carregarEvento);