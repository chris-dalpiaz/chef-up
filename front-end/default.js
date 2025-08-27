
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