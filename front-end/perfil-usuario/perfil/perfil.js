// Carrega o nome do usuário armazenado no localStorage e exibe na interface
function carregarNome() {
    const nome = localStorage.getItem("nome");
    if (nome) {
        document.querySelector(".nome_usuario").innerHTML = `<b>${nome}</b>`; //insere o nome do localstorage no html
    }
}

async function carregarTitulo() {
    try {
        const token = localStorage.getItem("token");
        const idUsuario = localStorage.getItem("id");

        if (!token || !idUsuario) {
            console.warn("Token ou ID do usuário não encontrado.");
            return;
        }

        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/titulos`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            console.error("Erro ao buscar título do usuário:", response.status);
            return;
        }

        const tituloData = await response.json();
        console.log(tituloData);

        const ativo = Array.isArray(tituloData)
            ? tituloData.find(t => t.estaAtivo)
            : (tituloData.estaAtivo ? tituloData : null);

        if (ativo?.titulo?.nome) {
            const tituloUsuario = document.querySelector(".titulo_usuario");
            const nome = ativo.titulo.nome;
            const condicao = ativo.titulo.condicaoDesbloqueio;

            tituloUsuario.innerHTML = `<i title="${condicao}">${nome}</i>`;
        }


    } catch (error) {
        console.error("Erro ao carregar título ativo:", error);
    }
}


async function carregarAdjetivos() {
    try {
        const token = localStorage.getItem("token");
        const idUsuario = localStorage.getItem("id"); // ou defina manualmente

        if (!token || !idUsuario) {
            console.warn("Token ou ID do usuário não encontrado.");
            return;
        }

        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/adjetivos`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            console.error("Erro ao buscar adjetivos:", response.status);
            return;
        }

        const adjetivos = await response.json();
        const adjetivosContainer = document.querySelector(".adjetivos_usuario");
        adjetivosContainer.innerHTML = "";

        const nomesUnicos = new Set();

        adjetivos.forEach(item => {
            const nomeAdj = item?.adjetivo?.nome?.trim();
            if (nomeAdj && !nomesUnicos.has(nomeAdj)) {
                nomesUnicos.add(nomeAdj);
                const span = document.createElement("span");
                span.className = "adjetivo";
                span.textContent = nomeAdj;
                adjetivosContainer.appendChild(span);
            }
        });

    } catch (error) {
        console.error("Erro ao carregar adjetivos:", error);
    }
}



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
                "Authorization": `Bearer ${token}`,
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
        document.querySelector(".nivel span").textContent = progresso.nivel;
        document.querySelector(".barra_xp").style.width = progresso.xp + "%";
        document.querySelector(".pontos_xp").textContent = `${progresso.xp} / 100`;

    } catch (error) {
        console.error("Erro ao carregar progresso:", error);
    }
}


async function carregarReceitas() {
    try {
        const token = localStorage.getItem("token");
        const idUsuario = localStorage.getItem("id");

        if (!token || !idUsuario) {
            console.warn("Token ou ID do usuário não encontrado.");
            return;
        }

        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/receitas`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            console.error("Erro ao buscar receitas concluídas:", response.status);
            return;
        }

        const receitas = await response.json();
        const grid = document.querySelector(".grid_pratos");
        grid.innerHTML = "";

        // Se o retorno for um único objeto, transforme em array
        const listaReceitas = Array.isArray(receitas) ? receitas : [receitas];

        listaReceitas.forEach(item => {
            const receita = item?.receita;
            const nome = receita?.nome || "Receita";
            const foto = item?.fotoPrato;

            if (foto) {
                // Se a imagem não for uma URL completa, prefixe com "/"
                const caminho = `http://localhost:8080${foto}`;

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


async function carregarAvatar() {
    try {
        const token = localStorage.getItem("token");
        const idUsuario = localStorage.getItem("id");

        if (!token || !idUsuario) {
            console.warn("Token ou ID do usuário não encontrado.");
            return;
        }

        const response = await fetch(`http://localhost:8080/usuarios/${idUsuario}/avatares`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            console.error("Erro ao buscar avatar do usuário:", response.status);
            return;
        }

        const avatarData = await response.json();
        console.log("Avatares recebidos:", avatarData);

        // Corrigido: busca o avatar ativo dentro do array
        const ativo = Array.isArray(avatarData)
            ? avatarData.find(a => a.estaAtivo)
            : (avatarData.estaAtivo ? avatarData : null);

        if (ativo?.avatar?.imagemUrl) {
            const imgUsuario = document.getElementById("foto_usuario");
            const imagemUrl = ativo.avatar.imagemUrl;
            const nomeAvatar = ativo.avatar.nome || "Avatar do usuário";

            imgUsuario.src = imagemUrl;
            imgUsuario.alt = nomeAvatar;
            imgUsuario.title = nomeAvatar;

            imgUsuario.onerror = () => {
                imgUsuario.src = "http://localhost:8080/img/avatares/default.png";
            };
        }

    } catch (error) {
        console.error("Erro ao carregar avatar ativo:", error);
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
