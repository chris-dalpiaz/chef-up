// *********************************************
// Configurações
// *********************************************
const API_BASE = 'http://localhost:8080'
const token = localStorage.getItem('token')
const userId = parseInt(localStorage.getItem('id'), 10)

// *********************************************
// Utilitário: lê JSON ou lança erro detalhado
// *********************************************
async function parseJsonOrThrow(resp, etapa) {
    const ct = resp.headers.get('content-type') || ''

    // respostas não-ok
    if (!resp.ok) {
        const txt = await resp.text().catch(() => '')
        throw new Error(`[${etapa}] HTTP ${resp.status} — ${txt || 'sem corpo'}`)
    }

    // content-type não JSON
    if (!ct.includes('application/json')) {
        const txt = await resp.text().catch(() => '')
        throw new Error(
            `[${etapa}] content-type inválido: ${ct} → ${txt.slice(0, 200)}`
        )
    }

    return resp.json()
}

// *********************************************
// Fluxo principal: avalia e persiste o prato
// *********************************************
async function processarPrato() {
    // 1) validações iniciais
    console.log('>>> Iniciando processarPrato');
    const inputFile = document.getElementById('input_file');
    if (!inputFile?.files.length) {
      alert('Insira a imagem do prato');
      return;
    }
    if (!token || !userId) {
      alert('Usuário não autenticado');
      return;
    }
  
    // 2) extrai ID da receita
    const receitaId = parseInt(
      new URLSearchParams(window.location.search).get('id'),
      10
    );
    console.log('receitaId=', receitaId);
    if (!receitaId) {
      alert('ID da receita não encontrado na URL');
      return;
    }
  
    try {
      // 3) upload da imagem para avaliação
      const file = inputFile.files[0];
      const formData = new FormData();
      formData.append('file', file);
  
      console.log('Enviando /receitas/.../avaliar-prato');
      const avaliarResp = await fetch(
        `${API_BASE}/receitas/${receitaId}/avaliar-prato`, {
          method:  'POST',
          mode:    'cors',
          headers: { Authorization: `Bearer ${token}` },
          body:    formData
        }
      );
  
      console.log('[avaliar-prato] status=', avaliarResp.status);
      const avaliarRaw = await avaliarResp.text().catch(() => '');
      console.log('[avaliar-prato] raw body=', avaliarRaw);
  
      if (!avaliarResp.ok) {
        throw new Error(`[avaliar-prato] HTTP ${avaliarResp.status} — ${avaliarRaw || 'sem corpo'}`);
      }
  
      const { comentario, nota, filename } = JSON.parse(avaliarRaw);
      console.log('JSON avaliação:', { comentario, nota, filename });
  
      // 4) monta payload de salvar
      const dataConclusao = new Date()
        .toISOString()
        .replace(/Z$/, '');
  
      const payload = {
        idReceita:      receitaId,
        dataConclusao,             
        fotoPrato:      `/uploads/finais/${filename}`,
        pontuacaoPrato: nota,
        textoAvaliacao: comentario
      };
  
      console.log('Payload salvar-prato:', payload);
  
      // 5) persiste conclusão da receita
      console.log('Enviando POST /usuarios/.../receitas');
      const salvarResp = await fetch(
        `${API_BASE}/usuarios/${userId}/receitas`, {
          method:  'POST',
          mode:    'cors',
          headers: {
            'Content-Type': 'application/json',
            Authorization:  `Bearer ${token}`
          },
          body: JSON.stringify(payload)
        }
      );
  
      console.log('[salvar-prato] status=', salvarResp.status);
      const salvarRaw = await salvarResp.text().catch(() => '');
      console.log('[salvar-prato] raw body=', salvarRaw);
  
      if (!salvarResp.ok) {
        throw new Error(`[salvar-prato] HTTP ${salvarResp.status} — ${salvarRaw || 'sem corpo'}`);
      }
  
      const salvarJson = salvarRaw ? JSON.parse(salvarRaw) : {};
      console.log('Resposta salvar-prato JSON:', salvarJson);
  
      alert('Receita concluída salva com sucesso!');
    }
    catch (err) {
      console.error('processarPrato error:', err);
      alert('Erro no processamento:\n' + err.message);
    }
  }
  

// *********************************************
// Hook do botão
// *********************************************
function carregarEvento() {
    document
        .getElementById('botao_avaliar')
        ?.addEventListener('click', processarPrato)
}

window.addEventListener('load', carregarEvento)
