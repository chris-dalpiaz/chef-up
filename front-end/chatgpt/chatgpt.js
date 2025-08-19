async function processarPrato() {
    const inputFile = document.getElementById("input_file");
    if (!inputFile || inputFile.files.length === 0) {
        alert("Insira a imagem do prato");
        return;
    }

    const file = inputFile.files[0];
    const formData = new FormData();
    formData.append("file", file);

    try {
        // 1️⃣ Upload temporário
        const uploadResp = await fetch("http://localhost:8080/avaliacao/upload-temp", {
            method: "POST",
            body: formData
        });

        const { tempUrl } = await uploadResp.json();
        console.log("Upload temporário concluído:", tempUrl);

        // 2️⃣ Avaliação pelo ChatGPT
        const avaliarResp = await fetch("http://localhost:8080/avaliacao/avaliar-prato", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ imageUrl: tempUrl })
        });

        const avaliacaoData = await avaliarResp.json();
        const { avaliacaoTexto, pontuacao } = avaliacaoData;
        console.log("Avaliação GPT:", avaliacaoTexto, "Pontuação:", pontuacao);

        alert("Avaliação do GPT: " + avaliacaoTexto + "\nPontuação: " + pontuacao);

        // 3️⃣ Salvar no banco somente após avaliação
        const salvarResp = await fetch("http://localhost:8080/avaliacao/salvar-prato", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                avaliacaoTexto: avaliacaoTexto,
                pontuacao: pontuacao,
                imageUrl: tempUrl,
                usuarioId: 1, // substituir pelo ID do usuário logado
                receitaId: 1  // substituir pelo ID da receita correspondente
            })
        });

        const salvarData = await salvarResp.json();
        if (salvarData.message) {
            alert(salvarData.message);
        } else {
            alert("Erro ao salvar prato.");
        }

    } catch (err) {
        console.error("Erro:", err);
        alert("Ocorreu um erro no processamento do prato.");
    }
}


function carregarEvento() {
    const botaoAvaliar = document.getElementById("botao_avaliar")
    botaoAvaliar.addEventListener("click", verificaçãoChatGPT)
}

window.addEventListener("load", carregarEvento);

