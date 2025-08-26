// Recupera o ID do usuário e o token de autenticação do localStorage
const userId = localStorage.getItem("id");
const token = localStorage.getItem("token");

// Variável que armazena temporariamente o ID do título selecionado
let tituloSelecionadoId = null;

// Array que armazena os IDs dos adjetivos selecionados pelo usuário
let adjetivosSelecionados = [];

// Função principal que carrega os dados do perfil do usuário
async function carregarPerfil() {
  // Define os headers da requisição com o token de autenticação
  const headers = { Authorization: `Bearer ${token}` };

  // Faz duas requisições em paralelo:
  // 1. Para obter os dados do usuário
  // 2. Para obter a lista de pronomes disponíveis
  const [usuarioRes, pronomesRes] = await Promise.all([
    fetch(`http://localhost:8080/usuarios/${userId}`, { headers }),
    fetch("http://localhost:8080/pronomes", { headers })
  ]);

  // Verifica se ambas as respostas foram bem-sucedidas
  if (!usuarioRes.ok || !pronomesRes.ok) {
    alert("Erro ao carregar dados do servidor");
    return;
  }

  // Converte as respostas para JSON
  const usuario = await usuarioRes.json();
  const pronomes = await pronomesRes.json();

  // Preenche os campos de nome e email com os dados do usuário
  document.getElementById("input_cadastro").value = usuario.nome || "";
  document.getElementById("input_email").value = usuario.email || "";

  // Renderiza os pronomes na interface, marcando o pronome atual do usuário
  renderizarPronomes(pronomes, usuario.pronome);

  // Chama funções para carregar títulos e adjetivos
  carregarTitulos();
  carregarAdjetivos();
}

// Função que carrega os adjetivos disponíveis e os selecionados pelo usuário
async function carregarAdjetivos() {
  try {
    // Verifica se o token e o ID do usuário estão disponíveis
    if (!token || !userId) {
      console.warn("Token ou ID do usuário não encontrado.");
      return;
    }

    // Faz duas requisições em paralelo:
    // 1. Para obter todos os adjetivos disponíveis
    // 2. Para obter os adjetivos já selecionados pelo usuário
    const [resAdjetivos, resSelecionados] = await Promise.all([
      fetch("http://localhost:8080/adjetivos", {
        headers: { Authorization: `Bearer ${token}` }
      }),
      fetch(`http://localhost:8080/usuarios/${userId}/adjetivos`, {
        headers: { Authorization: `Bearer ${token}` }
      })
    ]);

    // Verifica se ambas as respostas foram bem-sucedidas
    if (!resAdjetivos.ok || !resSelecionados.ok) {
      console.error("Erro ao buscar adjetivos:", resAdjetivos.status, resSelecionados.status);
      return;
    }

    // Converte as respostas para JSON
    const lista = await resAdjetivos.json();
    const selecionados = await resSelecionados.json();

    // Extrai os IDs dos adjetivos selecionados e remove duplicatas
    const idsSelecionados = [...new Set(selecionados.map(item => item.adjetivo.id))];

    // Atualiza o array global com os IDs selecionados
    adjetivosSelecionados = [...idsSelecionados];

    // Seleciona o container onde os botões de adjetivos serão exibidos
    const container = document.querySelector(".container_adjetivos");
    container.innerHTML = ""; // Limpa o conteúdo anterior

    // Para cada adjetivo disponível, cria um botão interativo
    lista.forEach(adj => {
      const btn = document.createElement("button"); // Cria o botão
      btn.className = "adjetivo";                   // Aplica a classe CSS
      btn.textContent = adj.nome;                   // Define o texto do botão
      btn.dataset.id = adj.id;                      // Armazena o ID como atributo

      // Se o adjetivo estiver entre os selecionados, marca como ativo
      if (idsSelecionados.includes(adj.id)) {
        btn.classList.add("ativo");
      }

      // Adiciona evento de clique para selecionar/deselecionar o adjetivo
      btn.addEventListener("click", () => {
        const id = parseInt(btn.dataset.id); // Obtém o ID do botão clicado
        const index = adjetivosSelecionados.indexOf(id); // Verifica se já está selecionado

        if (index >= 0) {
          // Se já estiver selecionado, remove do array e desativa visualmente
          adjetivosSelecionados.splice(index, 1);
          btn.classList.remove("ativo");
        } else if (adjetivosSelecionados.length < 3) {
          // Se não estiver e ainda houver espaço (máximo de 3), adiciona e ativa
          adjetivosSelecionados.push(id);
          btn.classList.add("ativo");
        }
      });

      // Adiciona o botão ao container
      container.appendChild(btn);
    });

  } catch (error) {
    // Trata erros inesperados durante o carregamento
    console.error("Erro ao carregar adjetivos:", error);
  }
}

// Função que renderiza os pronomes disponíveis na interface e permite ao usuário selecionar um
function renderizarPronomes(lista, ativo, token, userId) {
  // Seleciona o container onde os botões de pronomes serão exibidos
  const container = document.querySelector(".container_pronomes");

  // Limpa o conteúdo anterior do container
  container.innerHTML = "";

  // Itera sobre a lista de pronomes recebida
  lista.forEach(p => {
    // Cria um botão para cada pronome
    const btn = document.createElement("button");
    btn.className = "pronome";       // Aplica a classe CSS para estilo
    btn.textContent = p.nome;        // Define o texto do botão como o nome do pronome
    btn.dataset.id = p.id;           // Armazena o ID do pronome como atributo personalizado

    // Se o pronome atual for o ativo do usuário, marca o botão como ativo
    if (p.id === ativo?.id) {
      btn.classList.add("ativo");
    }

    // Adiciona evento de clique ao botão
    btn.addEventListener("click", async () => {
      // Remove a classe "ativo" de todos os botões
      container.querySelectorAll("button").forEach(b => b.classList.remove("ativo"));

      // Adiciona a classe "ativo" ao botão clicado
      btn.classList.add("ativo");

      try {
        // Envia requisição PUT para atualizar o pronome do usuário
        await fetch(`http://localhost:8080/usuarios/${userId}/pronome`, {
          method: "PUT", // Método HTTP para atualização
          headers: {
            "Content-Type": "application/json", // Define o tipo de conteúdo como JSON
            Authorization: `Bearer ${token}`    // Envia o token de autenticação
          },
          body: JSON.stringify({ id: p.id })    // Envia o ID do pronome selecionado
        });
      } catch (error) {
        // Exibe erro no console em caso de falha na requisição
        console.error("Erro ao atualizar pronome:", error);
      }
    });

    // Adiciona o botão ao container
    container.appendChild(btn);
  });
}

// Função responsável por carregar os títulos disponíveis e os títulos desbloqueados pelo usuário
async function carregarTitulos() {
  try {
    // Verifica se o token e o ID do usuário estão disponíveis
    if (!token || !userId) {
      console.warn("Token ou ID do usuário não encontrado.");
      return;
    }

    // Faz duas requisições em paralelo:
    // 1. Para obter todos os títulos disponíveis
    // 2. Para obter os títulos associados ao usuário
    const [resTodos, resUsuario] = await Promise.all([
      fetch("http://localhost:8080/titulos", {
        headers: { Authorization: `Bearer ${token}` }
      }),
      fetch(`http://localhost:8080/usuarios/${userId}/titulos`, {
        headers: { Authorization: `Bearer ${token}` }
      })
    ]);

    // Verifica se ambas as respostas foram bem-sucedidas
    if (!resTodos.ok || !resUsuario.ok) {
      console.error("Erro ao buscar títulos:", resTodos.status, resUsuario.status);
      return;
    }

    // Converte as respostas para JSON
    const todosTitulos = await resTodos.json();
    const titulosUsuario = await resUsuario.json();

    // Seleciona o container onde os botões de títulos serão exibidos
    const container = document.querySelector(".container_titulos");
    container.innerHTML = ""; // Limpa o conteúdo anterior

    // Cria um mapa para acesso rápido aos títulos desbloqueados pelo usuário
    const desbloqueadosMap = new Map();
    titulosUsuario.forEach(t => {
      if (t.desbloqueadoEm) {
        desbloqueadosMap.set(t.titulo.id, t); // Associa o ID do título ao objeto completo
      }
    });

    // Itera sobre todos os títulos disponíveis
    todosTitulos.forEach(titulo => {
      const btn = document.createElement("button"); // Cria um botão para o título
      btn.className = "adjetivo";                   // Aplica a classe CSS
      btn.textContent = titulo.nome;                // Define o texto do botão
      btn.dataset.id = titulo.id;                   // Armazena o ID do título como atributo

      const desbloqueado = desbloqueadosMap.get(titulo.id); // Verifica se o título está desbloqueado

      if (desbloqueado) {
        // Se o título estiver desbloqueado, armazena o ID da associação
        btn.dataset.usuarioTituloId = desbloqueado.id;

        // Se o título estiver ativo, marca como ativo e atualiza a variável global
        if (desbloqueado.estaAtivo) {
          btn.classList.add("ativo");
          tituloSelecionadoId = desbloqueado.id;
        }

        // Adiciona evento de clique para ativar o título
        btn.addEventListener("click", async () => {
          // Remove a classe "ativo" de todos os botões
          container.querySelectorAll("button").forEach(b => b.classList.remove("ativo"));

          // Adiciona a classe "ativo" ao botão clicado
          btn.classList.add("ativo");

          // Atualiza a variável global com o ID do título selecionado
          tituloSelecionadoId = desbloqueado.id;

          try {
            // Envia requisição PUT para atualizar o título ativo do usuário
            await fetch(`http://localhost:8080/usuarios/${userId}/titulo`, {
              method: "PUT",
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
              },
              body: JSON.stringify({ id: desbloqueado.id }) // Envia o ID da associação
            });
          } catch (error) {
            console.error("Erro ao atualizar título ativo:", error);
          }
        });

      } else {
        // Se o título estiver bloqueado, aplica estilo e título informativo
        btn.classList.add("bloqueado");
        btn.title = `Bloqueado: ${titulo.condicaoDesbloqueio}`;
      }

      // Adiciona o botão ao container
      container.appendChild(btn);
    });

  } catch (error) {
    // Trata erros inesperados durante o carregamento
    console.error("Erro ao carregar títulos:", error);
  }
}

// Função que salva as alterações feitas no perfil do usuário
async function salvarAlteracoes() {
  // Recupera o email antigo do localStorage para verificar se foi alterado
  const emailAntigo = localStorage.getItem("email");

  // Obtém os valores dos campos de nome e email
  const nome = document.getElementById("input_cadastro").value.trim();
  const email = document.getElementById("input_email").value.trim();

  // Obtém o botão de pronome que está marcado como ativo
  const pronomeBtn = document.querySelector(".container_pronomes .ativo");

  // Objeto que será enviado com os dados atualizados
  const payload = {};

  // Lista de promessas de atualização (requisições)
  const updatePromises = [];

  // Adiciona os dados ao payload se estiverem preenchidos
  if (nome) payload.nome = nome;
  if (email) payload.email = email;
  if (pronomeBtn) payload.idPronome = parseInt(pronomeBtn.dataset.id);

  // Se houver dados para atualizar, envia requisição PUT para atualizar o usuário
  if (Object.keys(payload).length > 0) {
    const usuarioReq = fetch(`http://localhost:8080/usuarios/${userId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify(payload)
    });

    updatePromises.push(usuarioReq);
  }

  // Se um título foi selecionado, envia requisição para ativá-lo
  if (tituloSelecionadoId) {
    const tituloReq = fetch(`http://localhost:8080/usuarios/${userId}/titulos/${tituloSelecionadoId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({ estaAtivo: true })
    });

    updatePromises.push(tituloReq);
  }

  // Aguarda todas as atualizações básicas (nome, email, pronome, título)
  const updateResults = await Promise.all(updatePromises);

  // Verifica se todas as requisições foram bem-sucedidas
  const updateSuccess = updateResults.every(res => res.ok);

  // Variável para controlar sucesso na atualização de adjetivos
  let adjetivoSuccess = true;

  try {
    // Busca os adjetivos atuais do usuário
    const atuaisRes = await fetch(`http://localhost:8080/usuarios/${userId}/adjetivos`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (atuaisRes.ok) {
      const atuais = await atuaisRes.json();

      // Cria requisições para deletar todos os adjetivos atuais
      const deletePromises = atuais.map(item =>
        fetch(`http://localhost:8080/usuarios/${userId}/adjetivos/${item.id}`, {
          method: "DELETE",
          headers: { Authorization: `Bearer ${token}` }
        })
      );

      await Promise.all(deletePromises); // Aguarda exclusões

      // Aguarda um pequeno tempo para garantir sincronização
      await new Promise(resolve => setTimeout(resolve, 100));

      // Remove duplicatas dos adjetivos selecionados
      const idsUnicos = [...new Set(adjetivosSelecionados)];

      // Cria requisições para adicionar os novos adjetivos
      const postPromises = idsUnicos.map(idAdjetivo =>
        fetch(`http://localhost:8080/usuarios/${userId}/adjetivos`, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
          },
          body: JSON.stringify({ idAdjetivo })
        })
      );

      const postResults = await Promise.all(postPromises);

      // Verifica se todas as requisições de adição foram bem-sucedidas
      adjetivoSuccess = postResults.every(res => res.ok);
    }
  } catch (error) {
    console.error("Erro ao atualizar adjetivos:", error);
    adjetivoSuccess = false;
  }

  // Se todas as atualizações foram bem-sucedidas
  if (updateSuccess && adjetivoSuccess) {
    // Atualiza localStorage com os novos dados
    if (payload.nome) localStorage.setItem("nome", payload.nome);
    if (payload.email) localStorage.setItem("email", payload.email);

    // Atualiza pronome no localStorage
    if (payload.idPronome) {
      const pronomeSelecionado = document.querySelector(`.container_pronomes button[data-id="${payload.idPronome}"]`);
      if (pronomeSelecionado) {
        localStorage.setItem("pronome", JSON.stringify({ id: payload.idPronome, nome: pronomeSelecionado.textContent }));
      }
    }

    // Atualiza título ativo no localStorage
    if (tituloSelecionadoId) {
      const tituloSelecionado = document.querySelector(`.container_titulos button[data-id="${tituloSelecionadoId}"]`);
      if (tituloSelecionado) {
        localStorage.setItem("titulos", JSON.stringify([
          { id: tituloSelecionadoId, titulo: { nome: tituloSelecionado.textContent }, estaAtivo: true }
        ]));
      }
    }

    // Atualiza adjetivos no localStorage
    const novosAdjetivosRes = await fetch(`http://localhost:8080/usuarios/${userId}/adjetivos`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (novosAdjetivosRes.ok) {
      const novosAdjetivos = await novosAdjetivosRes.json();
      localStorage.setItem("adjetivos", JSON.stringify(novosAdjetivos));
    }

    // Se o email foi alterado, força logout por segurança
    if (payload.email && payload.email !== emailAntigo) {
      alert("Email alterado. Por segurança, faça login novamente.");
      localStorage.clear(); // Limpa todos os dados locais
      window.location.href = "../../entrar/entrar.html"; // Redireciona para login
    } else {
      alert("Perfil atualizado com sucesso!");
      window.location.href = "../perfil/perfil.html"; // Redireciona para perfil
    }
  } else {
    alert("Erro ao salvar alterações");
  }
}

// Função que redireciona para a página de edição de avatar
function redirecionarAlterarAvatar() {
  window.location.href = "../editar-imagem/editar-imagem.html";
}

// Função que configura os eventos da página
function carregarEventos() {
  carregarUsuario(); // Carrega dados do usuário
  carregarPerfil();  // Carrega dados do perfil

  // Adiciona evento ao botão de salvar alterações
  document.querySelector(".botao_comum").addEventListener("click", salvarAlteracoes);

  // Adiciona evento ao botão de alterar avatar
  const alterarAvatar = document.getElementById("btn_avatar");
  alterarAvatar.addEventListener("click", redirecionarAlterarAvatar);
}

// Executa a configuração de eventos quando a página carregar
window.addEventListener("load", carregarEventos);
