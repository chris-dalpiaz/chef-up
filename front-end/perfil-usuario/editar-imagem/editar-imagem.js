// Recupera o ID do usuário e o token de autenticação do localStorage
const userId = localStorage.getItem("id");
const token = localStorage.getItem("token");

// Inicializa variáveis para armazenar os avatares desbloqueados e o avatar selecionado
let avataresDesbloqueados = [];
let avatarSelecionadoId = null;

// Função que carrega todos os avatares e os desbloqueados pelo usuário
async function carregarAvatares() {
  // Define os headers da requisição com o token de autenticação
  const headers = { Authorization: `Bearer ${token}` };

  try {
    // Faz duas requisições simultâneas:
    // 1. Para obter todos os avatares disponíveis
    // 2. Para obter os avatares desbloqueados pelo usuário
    const [todosRes, desbloqueadosRes] = await Promise.all([
      fetch("http://localhost:8080/avatares", { headers }),
      fetch(`http://localhost:8080/usuarios/${userId}/avatares`, { headers })
    ]);

    // Verifica se ambas as respostas foram bem-sucedidas
    if (!todosRes.ok || !desbloqueadosRes.ok) {
      alert("Erro ao carregar avatares");
      return;
    }

    // Converte as respostas para JSON
    const todos = await todosRes.json();
    const desbloqueados = await desbloqueadosRes.json();

    // Extrai os IDs dos avatares desbloqueados
    avataresDesbloqueados = desbloqueados.map(a => a.avatar.id);

    // Verifica se há um avatar ativo e armazena seu ID
    const ativo = desbloqueados.find(a => a.estaAtivo);
    if (ativo) avatarSelecionadoId = ativo.avatar.id;

    // Seleciona o container onde os avatares serão exibidos
    const container = document.querySelector(".grid_avatar");
    container.innerHTML = ""; // Limpa o conteúdo anterior

    // Para cada avatar disponível, cria um elemento visual
    todos.forEach(avatar => {
      const img = document.createElement("img"); // Cria uma imagem
      img.src = avatar.imagemUrl;                // Define a URL da imagem
      img.alt = avatar.nome;                     // Define o texto alternativo
      img.dataset.id = avatar.id;                // Armazena o ID como atributo

      // Verifica se o avatar está desbloqueado
      if (avataresDesbloqueados.includes(avatar.id)) {
        img.classList.add("desbloqueado"); // Aplica estilo de desbloqueado

        // Se for o avatar ativo, aplica estilo de ativo
        if (avatar.id === avatarSelecionadoId) {
          img.classList.add("ativo");
        }

        // Adiciona evento de clique para selecionar o avatar
        img.addEventListener("click", () => {
          // Remove a classe "ativo" de todos os avatares
          document.querySelectorAll(".grid_avatar img").forEach(i => i.classList.remove("ativo"));

          // Adiciona a classe "ativo" ao avatar clicado
          img.classList.add("ativo");

          // Atualiza o ID do avatar selecionado
          avatarSelecionadoId = avatar.id;
        });
      } else {
        // Se o avatar estiver bloqueado, aplica estilo e título informativo
        img.classList.add("bloqueado");
        img.title = "Avatar bloqueado";
      }

      // Adiciona o avatar ao container
      container.appendChild(img);
    });
  } catch (error) {
    // Trata erros inesperados durante o carregamento
    console.error("Erro ao carregar avatares:", error);
    alert("Erro inesperado ao carregar avatares");
  }
}

// Função que salva o avatar selecionado como ativo
async function salvarAvatar() {
  // Verifica se algum avatar foi selecionado
  if (!avatarSelecionadoId) {
    alert("Selecione um avatar desbloqueado");
    return;
  }

  try {
    // Envia requisição PUT para atualizar o avatar ativo do usuário
    const res = await fetch(`http://localhost:8080/usuarios/${userId}/avatares/${avatarSelecionadoId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({ estaAtivo: true }) // Define o avatar como ativo
    });

    // Verifica se a resposta foi bem-sucedida
    if (!res.ok) {
      alert("Erro ao salvar avatar");
      return;
    }

    // Informa sucesso ao usuário e redireciona para a página de perfil
    alert("Avatar atualizado com sucesso!");
    window.location.href = "../perfil/perfil.html";
  } catch (error) {
    // Trata erros inesperados durante o salvamento
    console.error("Erro ao salvar avatar:", error);
    alert("Erro inesperado ao salvar avatar");
  }
}

async function configurarEventos() {
  // Carrega os avatares disponíveis e desbloqueados
  await carregarAvatares();

  // Adiciona evento ao botão para salvar o avatar selecionado
  document.querySelector("button").addEventListener("click", salvarAvatar);
}

// Quando a página terminar de carregar, configura os eventos
window.addEventListener("load", configurarEventos);