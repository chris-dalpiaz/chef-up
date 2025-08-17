// Carrega o nome do usuário armazenado no localStorage e exibe na interface
function carregarNome() {
    const nome = localStorage.getItem("nome");
    if (nome) {
        document.querySelector(".nome_usuario").innerHTML = `<b>${nome}</b>`; //insere o nome do localstorage no html
    }
}

// Função que carrega o título ativo do usuário e exibe na interface
function carregarTitulo() {
    // Recupera a lista de títulos do localStorage (em formato JSON)
    const titulos = JSON.parse(localStorage.getItem("titulos"));

    // Verifica se a variável 'titulos' é um array válido
    if (Array.isArray(titulos)) {

        // Procura dentro do array o título que está marcado como ativo (estaAtivo === true)
        const ativo = titulos.find(t => t.estaAtivo === true);

        // Se encontrou um título ativo e ele possui um nome válido
        if (ativo?.titulo?.nome) {

            // Seleciona o elemento HTML com a classe 'titulo_usuario'
            // Insere o nome do título dentro dele, usando a tag <i> para deixar o texto em itálico
            document.querySelector(".titulo_usuario").innerHTML = `<i>${ativo.titulo.nome}</i>`;
        }
    }
}

function carregarAdjetivos() {
  const adjetivos = JSON.parse(localStorage.getItem("adjetivos"));
  const adjetivosContainer = document.querySelector(".adjetivos_usuario");
  adjetivosContainer.innerHTML = "";

  if (Array.isArray(adjetivos)) {
    const nomesUnicos = new Set();

    adjetivos.forEach(function (item) {
      const nomeAdj = item?.adjetivo?.nome?.trim();
      if (nomeAdj && !nomesUnicos.has(nomeAdj)) {
        nomesUnicos.add(nomeAdj);
        const span = document.createElement("span");
        span.className = "adjetivo";
        span.textContent = nomeAdj;
        adjetivosContainer.appendChild(span);
      }
    });
  }
}


// Função que carrega o progresso do usuário (nível e XP) e atualiza os elementos visuais da interface
function carregarProgresso() {
    // Recupera os dados de progresso armazenados no localStorage e converte de JSON para objeto JavaScript
    const progresso = JSON.parse(localStorage.getItem("progresso"));

    // Verifica se o objeto 'progresso' existe (ou seja, se foi encontrado no localStorage)
    if (progresso) {
        // Atualiza o texto do elemento que exibe o nível do usuário
        document.querySelector(".nivel span").textContent = progresso.nivel;

        // Atualiza a largura da barra de XP com base no valor atual de experiência
        document.querySelector(".barra_xp").style.width = progresso.xp + "%";

        // Atualiza o texto que mostra a quantidade de XP atual em relação ao total necessário (100)
        document.querySelector(".pontos_xp").textContent = `${progresso.xp} / 100`;
    }
}

// Função que carrega as receitas concluídas pelo usuário e exibe as imagens dos pratos na interface
function carregarReceitas() {
    // Recupera o array de receitas concluídas do localStorage e converte de JSON para objeto JavaScript
    const receitas = JSON.parse(localStorage.getItem("receitasConcluidas"));

    // Seleciona o elemento HTML que representa a grade onde os pratos serão exibidos
    const grid = document.querySelector(".grid_pratos");

    // Limpa o conteúdo anterior da grade para evitar duplicações
    grid.innerHTML = "";

    // Verifica se o conteúdo recuperado é um array válido
    if (Array.isArray(receitas)) {

        // Percorre cada item do array de receitas concluídas
        receitas.forEach(function (item) {
            // Acessa os dados da receita dentro do item
            const receita = item?.receita;

            // Obtém o nome da receita, ou usa "Receita" como valor padrão se estiver ausente
            const nome = receita?.nome || "Receita";

            // Obtém o caminho da imagem do prato, se estiver disponível
            const foto = item?.fotoPrato;

            // Se houver uma imagem definida para o prato
            if (foto) {
                // Garante que o caminho da imagem comece com "/", para evitar erros de carregamento
                const caminho = foto.startsWith("/") ? foto : `/${foto}`;

                // Adiciona dinamicamente um bloco HTML à grade com a imagem do prato e o nome como texto alternativo
                grid.innerHTML += `
                    <div class="receita_item">
                        <img src="${caminho}" alt="${nome}">
                    </div>
                `;
            }
        });
    }
}

// Função que carrega o avatar ativo do usuário e exibe na imagem de perfil da interface
function carregarAvatar() {
    // Recupera a lista de avatares armazenados no localStorage e converte de JSON para objeto JavaScript
    const avatares = JSON.parse(localStorage.getItem("avatares"));

    // Seleciona o elemento <img> que representa a foto de perfil do usuário
    const imgUsuario = document.getElementById("foto_usuario");

    // Verifica se o conteúdo recuperado é um array válido
    if (Array.isArray(avatares)) {

        // Procura dentro do array o avatar que está marcado como ativo (estaAtivo === true)
        const ativo = avatares.find(a => a.estaAtivo === true);

        // Se encontrou um avatar ativo e ele possui uma URL de imagem válida
        if (ativo?.avatar?.imagemUrl) {

            // Garante que o caminho da imagem comece com "/", para evitar erros de carregamento
            const caminho = ativo.avatar.imagemUrl.startsWith("/")
                ? ativo.avatar.imagemUrl
                : `/${ativo.avatar.imagemUrl}`;

            // Define o caminho da imagem como fonte (src) da tag <img>
            imgUsuario.src = caminho;

            // Define o texto alternativo da imagem (usado por leitores de tela e em caso de erro)
            imgUsuario.alt = ativo.avatar.nome || "Avatar do usuário";

            // Se a imagem falhar ao carregar (ex: arquivo não encontrado), exibe uma imagem padrão (placeholder)
            imgUsuario.onerror = () => {
                imgUsuario.src = "img/placeholder_usuario1.jfif";
            };
        }
    }
}


// Chama todas as funções de carregamento de dados do usuário
function listarDados() {
    carregarAvatar();
    carregarNome();
    carregarTitulo();
    carregarAdjetivos();
    carregarProgresso();
    carregarReceitas();
}

// Configura os eventos iniciais da página (ex: ao carregar)
function configurarEventos() {
    carregarUsuario(); // Função externa que provavelmente busca dados do usuário
    listarDados();     // Atualiza a interface com os dados carregados
}

// Executa a função de configuração quando a página terminar de carregar
window.addEventListener("load", configurarEventos);
