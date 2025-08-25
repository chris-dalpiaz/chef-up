// Carrega o nome do usuário armazenado no localStorage e exibe na interface
function carregarNome() {
    const nome = localStorage.getItem("nome"); // Recupera o nome do usuário do localStorage
    if (nome) {
        // Insere o nome no elemento com a classe "nome_usuario", usando <b> para negrito
        document.querySelector(".nome_usuario").innerHTML = `<b>${nome}</b>`;
    }
}

// Função que carrega o título ativo do usuário e exibe na interface
async function carregarTitulo() {
    try {
        const token = localStorage.getItem("token");     // Recupera o token de autenticação
        const idUsuario = localStorage.getItem("id");    // Recupera o ID do usuário

        // Verifica se os dados necessários estão disponíveis
        if (!token || !idUsuario) {
            console.warn("Token ou ID do usuário não encontrado.");
            return;
        }

        // Faz requisição para buscar os títulos do usuário
        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/titulos`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        // Verifica se a resposta foi bem-sucedida
        if (!response.ok) {
            console.error("Erro ao buscar título do usuário:", response.status);
            return;
        }

        const tituloData = await response.json(); // Converte a resposta para JSON
        console.log(tituloData); // Exibe os dados no console para depuração

        // Verifica se há um título ativo
        const ativo = Array.isArray(tituloData)
            ? tituloData.find(t => t.estaAtivo) // Se for array, busca o ativo
            : (tituloData.estaAtivo ? tituloData : null); // Se for objeto único, verifica se está ativo

        // Se houver título ativo, exibe na interface
        if (ativo?.titulo?.nome) {
            const tituloUsuario = document.querySelector(".titulo_usuario");
            const nome = ativo.titulo.nome;
            const condicao = ativo.titulo.condicaoDesbloqueio;

            // Insere o nome do título com um tooltip mostrando a condição de desbloqueio
            tituloUsuario.innerHTML = `<i title="${condicao}">${nome}</i>`;
        }

    } catch (error) {
        console.error("Erro ao carregar título ativo:", error);
    }
}

// Função que carrega os adjetivos do usuário e exibe na interface
async function carregarAdjetivos() {
    try {
        const token = localStorage.getItem("token");     // Recupera o token de autenticação
        const idUsuario = localStorage.getItem("id");    // Recupera o ID do usuário

        // Verifica se os dados necessários estão disponíveis
        if (!token || !idUsuario) {
            console.warn("Token ou ID do usuário não encontrado.");
            return;
        }

        // Faz requisição para buscar os adjetivos do usuário
        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/adjetivos`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        // Verifica se a resposta foi bem-sucedida
        if (!response.ok) {
            console.error("Erro ao buscar adjetivos:", response.status);
            return;
        }

        const adjetivos = await response.json(); // Converte a resposta para JSON
        const adjetivosContainer = document.querySelector(".adjetivos_usuario");
        adjetivosContainer.innerHTML = ""; // Limpa o conteúdo anterior

        const nomesUnicos = new Set(); // Usado para evitar adjetivos duplicados

        // Itera sobre os adjetivos recebidos
        adjetivos.forEach(item => {
            const nomeAdj = item?.adjetivo?.nome?.trim(); // Obtém o nome do adjetivo

            // Se o nome for válido e ainda não foi adicionado
            if (nomeAdj && !nomesUnicos.has(nomeAdj)) {
                nomesUnicos.add(nomeAdj); // Marca como adicionado

                const span = document.createElement("span"); // Cria um elemento <span>
                span.className = "adjetivo";                 // Aplica a classe CSS
                span.textContent = nomeAdj;                  // Define o texto do adjetivo

                adjetivosContainer.appendChild(span);        // Adiciona o span ao container
            }
        });

    } catch (error) {
        console.error("Erro ao carregar adjetivos:", error);
    }
}

// Função que carrega o progresso do usuário (nível e XP)
async function carregarProgresso() {
    try {
        // Recupera o token JWT salvo no localStorage
        const token = localStorage.getItem("token");

        // Verifica se o token existe
        if (!token) {
            console.warn("Token não encontrado. Usuário não autenticado.");
            return;
        }

        // Faz a requisição à API com o token Bearer
        const response = await fetch("http://localhost:8080/usuarios/1/progresso", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`, // Autenticação via token
                "Content-Type": "application/json"
            }
        });

        // Verifica se a resposta foi bem-sucedida
        if (!response.ok) {
            console.error("Erro ao buscar progresso:", response.status);
            return;
        }

        // Converte a resposta para JSON
        const progresso = await response.json();

        // Atualiza os elementos visuais com os dados recebidos
        document.querySelector(".nivel span").textContent = progresso.nivel; // Exibe o nível
        document.querySelector(".barra_xp").style.width = progresso.xp + "%"; // Atualiza a barra de XP
        document.querySelector(".pontos_xp").textContent = `${progresso.xp} / 100`; // Exibe os pontos de XP
    } catch (error) {
        console.error("Erro ao carregar progresso:", error);
    }
}

// Função que carrega as receitas concluídas pelo usuário
async function carregarReceitas() {
    try {
        const token = localStorage.getItem("token"); // Recupera o token
        const idUsuario = localStorage.getItem("id"); // Recupera o ID do usuário

        // Verifica se os dados necessários estão disponíveis
        if (!token || !idUsuario) {
            console.warn("Token ou ID do usuário não encontrado.");
            return;
        }

        // Faz requisição para buscar receitas concluídas
        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/receitas`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        // Verifica se a resposta foi bem-sucedida
        if (!response.ok) {
            console.error("Erro ao buscar receitas concluídas:", response.status);
            return;
        }

        const receitas = await response.json(); // Converte a resposta para JSON
        const grid = document.querySelector(".grid_pratos"); // Seleciona o container das receitas
        grid.innerHTML = ""; // Limpa o conteúdo anterior

        // Se o retorno for um único objeto, transforma em array
        const listaReceitas = Array.isArray(receitas) ? receitas : [receitas];

        // Itera sobre as receitas e renderiza cada uma
        listaReceitas.forEach(item => {
            const receita = item?.receita; // Dados da receita
            const nome = receita?.nome || "Receita"; // Nome da receita
            const foto = item?.fotoPrato; // Foto do prato

            if (foto) {
<<<<<<< HEAD
                // Se a imagem não for uma URL completa, prefixa com "/"
                const caminho = foto.startsWith("http") ? foto : `/${foto}`;
=======
                // Se a imagem não for uma URL completa, prefixe com "/"
                const caminho = `http://localhost:8080${foto}`;
>>>>>>> 05e26656b3b5a128d112c07ae3b01e78087ba637

                // Adiciona o card da receita ao grid
                grid.innerHTML += `
                    <div class="receita_item">
                        <img src="${caminho}" alt="${nome}">
                    </div>
                `;
            }
        });

    } catch (error) {
        console.error("Erro ao carregar receitas:", error);
    }
}

// Função que carrega o avatar ativo do usuário e exibe na interface
async function carregarAvatar() {
    try {
        const token = localStorage.getItem("token");     // Recupera o token de autenticação
        const idUsuario = localStorage.getItem("id");    // Recupera o ID do usuário

        // Verifica se os dados necessários estão disponíveis
        if (!token || !idUsuario) {
            console.warn("Token ou ID do usuário não encontrado.");
            return;
        }

        // Faz requisição para buscar os avatares do usuário
        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/avatares`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,       // Envia o token no cabeçalho
                "Content-Type": "application/json"
            }
        });

        // Verifica se a resposta foi bem-sucedida
        if (!response.ok) {
            console.error("Erro ao buscar avatar do usuário:", response.status);
            return;
        }

        const avatarData = await response.json(); // Converte a resposta para JSON
        console.log("Avatares recebidos:", avatarData); // Exibe os dados no console

        // Verifica se há um avatar ativo
        const ativo = Array.isArray(avatarData)
            ? avatarData.find(a => a.estaAtivo) // Se for array, busca o ativo
            : (avatarData.estaAtivo ? avatarData : null); // Se for objeto único, verifica se está ativo

        // Se houver avatar ativo, atualiza a imagem na interface
        if (ativo?.avatar?.imagemUrl) {
            const imgUsuario = document.getElementById("foto_usuario"); // Seleciona o elemento da imagem
            const imagemUrl = ativo.avatar.imagemUrl;                   // URL da imagem
            const nomeAvatar = ativo.avatar.nome || "Avatar do usuário"; // Nome do avatar

            imgUsuario.src = imagemUrl;         // Define a imagem
            imgUsuario.alt = nomeAvatar;        // Texto alternativo
            imgUsuario.title = nomeAvatar;      // Tooltip com o nome

            // Se a imagem falhar ao carregar, define uma imagem padrão
            imgUsuario.onerror = () => {
                imgUsuario.src = "http://localhost:8080/img/avatares/default.png";
            };
        }

    } catch (error) {
        console.error("Erro ao carregar avatar ativo:", error);
    }
}

// Função que chama todas as funções de carregamento de dados do usuário
function listarDados() {
    carregarAvatar();     // Carrega e exibe o avatar
    carregarNome();       // Carrega e exibe o nome
    carregarTitulo();     // Carrega e exibe o título
    carregarAdjetivos();  // Carrega e exibe os adjetivos
    carregarProgresso();  // Carrega e exibe o progresso (nível e XP)
    carregarReceitas();   // Carrega e exibe as receitas concluídas
}

// Função que configura os eventos iniciais da página
function configurarEventos() {
    carregarUsuario(); // Função externa que provavelmente inicializa dados do usuário
    listarDados();     // Atualiza a interface com os dados carregados
}

// Executa a função de configuração quando a página terminar de carregar
window.addEventListener("load", configurarEventos);