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
