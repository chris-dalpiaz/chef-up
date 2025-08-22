// *********************************************
// Utilitário: parse JSON ou lança erro claro
// *********************************************
async function parseJsonOrThrow(resp, etapa) {
    const ct = resp.headers.get("content-type") || "";
  
    if (!resp.ok) {
      const texto = await resp.text().catch(() => "");
      throw new Error(`[${etapa}] HTTP ${resp.status} – ${texto || "sem corpo"}`);
    }
  
    if (!ct.includes("application/json")) {
      const texto = await resp.text().catch(() => "");
      throw new Error(
        `[${etapa}] Resposta não é JSON. content-type=${ct} corpo=${texto.slice(0,200)}`
      );
    }
  
    return resp.json();
  }
  
  // *********************************************
  // Função principal: avalia imagem e salva conclusão
  // *********************************************
  async function processarPrato() {
    // validações iniciais
    const inputFile = document.getElementById("input_file");
    if (!inputFile || inputFile.files.length === 0) {
      alert("Insira a imagem do prato");
      return;
    }
  
    const params     = new URLSearchParams(window.location.search);
    const receitaId  = parseInt(params.get("id"), 10);
    const userId     = parseInt(localStorage.getItem("id"), 10);
    const token      = localStorage.getItem("token");
    const file       = inputFile.files[0];
  
    if (!receitaId || !userId) {
      alert("ID da receita ou do usuário não encontrados.");
      return;
    }
  
    if (!token) {
      alert("Token de autenticação não encontrado. Faça login novamente.");
      return;
    }
  
    try {
      // 1) envia imagem para avaliação
      const formData = new FormData();
      formData.append("file", file);
  
      const avaliarResp = await fetch(
        `http://localhost:8080/receitas/${receitaId}/avaliar-prato`,
        {
          method: "POST",
          headers: { Authorization: `Bearer ${token}` },
          body: formData
        }
      );
  
      const { comentario, nota, filename } =
        await parseJsonOrThrow(avaliarResp, "avaliar-prato");
  
      if (comentario == null || nota == null || !filename) {
        throw new Error("Resposta de avaliação incompleta.");
      }
  
      alert(`Avaliação do GPT:\n${comentario}\nNota: ${nota}`);
  
      // 2) monta payload e salva como receita concluída
      const payload = {
        idReceita:      receitaId,
        dataConclusao:  new Date().toISOString(),
        fotoPrato:      `/uploads/finais/${filename}`,
        pontuacaoPrato: nota,
        textoAvaliacao: comentario
      };
  
      // JSON puro, sem crases envolventes
      const bodyJson = JSON.stringify(payload);
      console.log("Body JSON enviado:", bodyJson, "primeiro char:", bodyJson.charAt(0));
  
      const salvarResp = await fetch(
        `http://localhost:8080/usuarios/${userId}/receitas`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
          },
          body: bodyJson
        }
      );
  
      const salvarData = await parseJsonOrThrow(salvarResp, "salvar-prato");
      alert("Receita concluída salva com sucesso!");
  
    } catch (err) {
      console.error("Erro no fluxo:", err);
      alert("Ocorreu um erro:\n" + err.message);
    }
  }
  
  // *********************************************
  // Vincula o clique no botão avaliar
  // *********************************************
  function carregarEvento() {
    const botaoAvaliar = document.getElementById("botao_avaliar");
    if (botaoAvaliar) {
      botaoAvaliar.addEventListener("click", processarPrato);
    }
  }
  
  window.addEventListener("load", carregarEvento);
  