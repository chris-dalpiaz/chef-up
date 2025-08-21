const token = localStorage.getItem("token");

async function parseJsonOrThrow(resp, etapa) {
    const ct = resp.headers.get("content-type") || "";
    if (!resp.ok) {
        const texto = await resp.text().catch(() => "");
        throw new Error(`[${etapa}] HTTP ${resp.status} - ${texto || "sem corpo"}`);
    }
    if (!ct.includes("application/json")) {
        const texto = await resp.text().catch(() => "");
        throw new Error(`[${etapa}] Resposta não é JSON. content-type=${ct} corpo=${texto.slice(0, 200)}`);
    }
    return resp.json();
}

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
        // 🔸 1) Enviar direto para avaliação
        const avaliarResp = await fetch("http://localhost:8080/avaliacao/avaliar-prato", {
            method: "POST",
            headers: {
                Authorization: `Bearer ${token}`
            },
            body: formData
        });

        const avaliacaoData = await parseJsonOrThrow(avaliarResp, "avaliar-prato");
        const { avaliacaoTexto, pontuacao } = avaliacaoData;
        if (avaliacaoTexto == null || pontuacao == null) {
            throw new Error(`[avaliar-prato] Campos esperados ausentes. JSON=${JSON.stringify(avaliacaoData)}`);
        }

        console.log("Avaliação GPT:", avaliacaoTexto, "Pontuação:", pontuacao);
        alert("Avaliação do GPT: " + avaliacaoTexto + "\nPontuação: " + pontuacao);

        // 🔸 2) Salvar no banco
        const salvarResp = await fetch("http://localhost:8080/avaliacao/salvar-prato", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify({
                avaliacaoTexto,
                pontuacao,
                // podemos salvar só o nome do arquivo ou path final
                imageUrl: file.name,
                usuarioId: 1, // TODO: ID real do usuário
                receitaId: 1  // TODO: ID real da receita
            })
        });

        const salvarData = await parseJsonOrThrow(salvarResp, "salvar-prato");
        if (salvarData.message) {
            alert(salvarData.message);
        } else {
            throw new Error(`[salvar-prato] JSON inesperado: ${JSON.stringify(salvarData)}`);
        }

    } catch (err) {
        console.error("Erro no fluxo:", err);
        alert("Ocorreu um erro no processamento do prato.\n" + err.message);
    }
}

function carregarEvento() {
    const botaoAvaliar = document.getElementById("botao_avaliar");
    botaoAvaliar.addEventListener("click", processarPrato);
}
window.addEventListener("load", carregarEvento);