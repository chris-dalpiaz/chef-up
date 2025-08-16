// Função que carrega os usuários, mas só se o usuário estiver autenticado
function carregarUsuario() {
    const token = localStorage.getItem("token"); // Recupera o token do armazenamento local

    if (!token) { // Se não houver token, o usuário não está autenticado
        console.log("Usuário não autenticado");
        window.location.href = "../entrar/entrar.html"; 
        return; // Encerra a função
    } else {
        console.log("Usuário carregado")
        return; // Encerra a função
    }

}