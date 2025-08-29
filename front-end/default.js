
// Função que carrega os usuários, mas só se o usuário estiver autenticado
function carregarUsuario() {
    // Recupera o token de autenticação armazenado no navegador (localStorage)
    // O token geralmente é salvo após o login e usado para verificar se o usuário está autenticado
    const token = localStorage.getItem("token");

    // Verifica se o token não existe (ou seja, o usuário não está autenticado)
    if (!token) {
        // Exibe uma mensagem no console informando que o usuário não está autenticado
        console.log("Usuário não autenticado");


        // Redireciona o usuário para a página de splash (tela inicial do site)
        window.location.href = "../../splash/splash.html";

        // Encerra a execução da função para evitar que o restante do código seja executado
        return;

    } else {
        // Se o token existir, significa que o usuário está autenticado
        // Exibe uma mensagem no console confirmando que o usuário foi carregado
        console.log("Usuário carregado");

        // Encerra a função (não há mais ações a serem realizadas aqui)
        return;
    }
}


// Função que carrega o progresso do usuário (nível e XP)
async function carregarProgresso() {
    try {
        // Recupera o token JWT e o id do usuario salvo no localStorage
        const token = localStorage.getItem("token");
        const userId = parseInt(localStorage.getItem('id'), 10);

        // Verifica se o token existe
        if (!token) {
            console.warn("Token não encontrado. Usuário não autenticado.");
            return;
        }

        // Faz a requisição à API com o token Bearer
        const response = await fetch(`http://localhost:8080/usuarios/${userId}/progresso`, {
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

        const xpTotal = progresso.xp;

        const nivel = Math.floor(xpTotal / 100);

        const xpLevel = xpTotal % 100;


        // Atualiza os elementos visuais com os dados recebidos
        document.querySelector(".nivel span").textContent = nivel; // Exibe o nível
        document.querySelector(".barra_xp").style.width = xpLevel + "%"; // Atualiza a barra de XP
        document.querySelector(".pontos_xp").textContent = `${xpLevel} / 100`; // Exibe os pontos de XP
    } catch (error) {
        console.error("Erro ao carregar progresso:", error);
    }

    async function atualizarTitulos() {
        const token = localStorage.getItem("token");
        const userId = parseInt(localStorage.getItem('id'), 10);
        // Verifica se o token existe
        if (!token) {
            console.warn("Token não encontrado. Usuário não autenticado.");
            return;
        }
        const response = await fetch(`http://localhost:8080/usuarios/${userId}/receitas`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`, // Autenticação via token
                "Content-Type": "application/json"
            }
        });
        const data = await response.json(); // ← converte a resposta para JSON
        const quantidade = Array.isArray(data) ? data.length : 0; // ← conta os itens
        console.log("Quantidade de receitas concluídas:", quantidade);

        let tituloId;
        if (quantidade >= 1) {
            tituloId = 1; // Exemplo: "Chef Supremo"
        } else if (quantidade >= 10) {
            tituloId = 2; // Exemplo: "Chef Intermediário"
        } else if (quantidade >= 50) {
            tituloId = 3; // Exemplo: "Aprendiz de Chef"
        } else if (quantidade >= 50) {
            tituloId = 4;

        }
    }