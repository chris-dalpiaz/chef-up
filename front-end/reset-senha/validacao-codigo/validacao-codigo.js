function carregarEvento() {
    const compareBtn = document.getElementById("continuar");

    compareBtn.addEventListener('click', (event) => {
        event.preventDefault();

        const n1 = document.getElementById("number1");
        const n2 = document.getElementById("number2");
        const n3 = document.getElementById("number3");
        const n4 = document.getElementById("number4");

        const digitCode = ""+n1+""+n2+""+n3+""+n4+""

        const emailValue = localStorage.getItem('email');

        fetch("http:localhost:8080/codigos/validacao", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({email: emailValue, cmpCode: digitCode})
        })
        .then((response) => response.json())
        .then((response) => {
            console.log(response)
            if (response.getCodigoValido == true) {
                window.location.href = "../redefinir-senha/redefinir-senha.html";
            } else {
                alert("Código de Verificação Incorreto")
            }
        })
    })
}

window.addEventListener('load',
    carregarEvento);