// *********************************************
// Configurações
// *********************************************
const API_BASE = 'http://localhost:8080';
const token = localStorage.getItem('token');
const userId = parseInt(localStorage.getItem('id'), 10);

// *********************************************
// Utilitário: lê JSON ou lança erro detalhado
// *********************************************
async function parseJsonOrThrow(resp, etapa) {
  const ct = resp.headers.get('content-type') || '';

  if (!resp.ok) {
    const txt = await resp.text().catch(() => '');
    throw new Error(`[${etapa}] HTTP ${resp.status} — ${txt || 'sem corpo'}`);
  }

  if (!ct.includes('application/json')) {
    const txt = await resp.text().catch(() => '');
    throw new Error(`[${etapa}] content-type inválido: ${ct} → ${txt.slice(0, 200)}`);
  }

  return resp.json();
}

// *********************************************
// Fluxo principal: avalia e persiste o prato
// *********************************************
async function processarPrato() {
  console.log('>>> Iniciando processarPrato');

  const botao = document.getElementById('botao_avaliar');
  if (botao) {
    botao.disabled = true;
    botao.classList.add('carregando');
  }

  const inputFile = document.getElementById('input_file');
  if (!inputFile?.files.length) {
    alert('Insira a imagem do prato');
    desbloquearBotao(botao);
    return;
  }
  if (!token || !userId) {
    alert('Usuário não autenticado');
    desbloquearBotao(botao);
    return;
  }

  const receitaId = parseInt(new URLSearchParams(window.location.search).get('id'), 10);
  console.log('receitaId=', receitaId);
  if (!receitaId) {
    alert('ID da receita não encontrado na URL');
    desbloquearBotao(botao);
    return;
  }

  try {
    const file = inputFile.files[0];
    const formData = new FormData();
    formData.append('file', file);

    console.log('Enviando POST /receitas/.../avaliar-prato');
    const avaliarResp = await fetch(`${API_BASE}/receitas/${receitaId}/avaliar-prato`, {
      method: 'POST',
      mode: 'cors',
      headers: { Authorization: `Bearer ${token}` },
      body: formData
    });

    const { comentario, nota, filename } = await parseJsonOrThrow(avaliarResp, 'avaliar-prato');
    console.log('JSON avaliação:', { comentario, nota, filename });

    const dataConclusao = new Date().toISOString().replace(/Z$/, '');
    const payload = {
      idReceita: receitaId,
      dataConclusao,
      fotoPrato: `/uploads/temp/${filename}`,
      pontuacaoPrato: nota,
      textoAvaliacao: comentario
    };

    console.log('Payload salvar-prato:', payload);

    console.log('Enviando POST /usuarios/.../receitas');
    const salvarResp = await fetch(`${API_BASE}/usuarios/${userId}/receitas`, {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(payload)
    });

    const salvarJson = await parseJsonOrThrow(salvarResp, 'salvar-prato');
    console.log('Resposta salvar-prato JSON:', salvarJson);

    exibirAvaliacao(comentario, nota);

    setTimeout(() => {
      alert('Receita concluída salva com sucesso!');
    }, 100);
  }
  catch (err) {
    console.error('processarPrato error:', err);
    alert('Erro no processamento:\n' + err.message);
  }
  finally {
    desbloquearBotao(botao);

    if (botao) {
      botao.removeEventListener('click', aoClicarBotao); // remove corretamente
      botao.textContent = 'Voltar à tela inicial';
      botao.classList.remove('carregando');
      botao.classList.add('botao-voltar');
      botao.onclick = () => {
        window.location.href = '../perfil-usuario/perfil/perfil.html';
      };
    }
  }
}

// *********************************************
// Função auxiliar para desbloquear botão
// *********************************************
function desbloquearBotao(botao) {
  if (botao) {
    botao.disabled = false;
    botao.classList.remove('carregando');
  }
}

// *********************************************
// Função para exibir avaliação e estrelas
// *********************************************
function exibirAvaliacao(comentario, nota) {
  const textoEl = document.getElementById('text');
  const estrelasEl = document.getElementById('star');

  if (!textoEl || !estrelasEl) {
    console.warn('Elementos #text ou #star não encontrados no DOM');
    return;
  }

  textoEl.textContent = comentario;
  estrelasEl.innerHTML = '';

  const totalEstrelas = 5;
  for (let i = 1; i <= totalEstrelas; i++) {
    const img = document.createElement('img');
    img.src = '../../back-end/img/icones/nota-estrela.svg';
    img.alt = i <= nota ? 'Estrela preenchida' : 'Estrela vazia';
    img.classList.add('estrela');
    if (i > nota) img.style.opacity = '0.3';
    estrelasEl.appendChild(img);
  }
}


function aoClicarBotao(e) {
  e.preventDefault();
  processarPrato();
}

// *********************************************
// Hook do botão
// *********************************************
function carregarEvento() {
  const botao = document.getElementById('botao_avaliar');
  if (botao) {
    botao.addEventListener('click', aoClicarBotao);
  }
}

window.addEventListener('load', () => {
  carregarEvento();
});

window.addEventListener('beforeunload', function (e) {
  console.warn('⚠️ Página está tentando recarregar!');
  console.trace();
  e.preventDefault();
  e.returnValue = '';
});
