const userId = localStorage.getItem("id");
const token = localStorage.getItem("token");

let avataresDesbloqueados = [];
let avatarSelecionadoId = null;

window.addEventListener("load", async () => {
  await carregarAvatares();
  document.querySelector("button").addEventListener("click", salvarAvatar);
});

async function carregarAvatares() {
  const headers = { Authorization: `Bearer ${token}` };

  try {
    const [todosRes, desbloqueadosRes] = await Promise.all([
      fetch("http://localhost:8080/avatares", { headers }),
      fetch(`http://localhost:8080/usuarios/${userId}/avatares`, { headers })
    ]);

    if (!todosRes.ok || !desbloqueadosRes.ok) {
      alert("Erro ao carregar avatares");
      return;
    }

    const todos = await todosRes.json();
    const desbloqueados = await desbloqueadosRes.json();

    avataresDesbloqueados = desbloqueados.map(a => a.avatar.id);
    const ativo = desbloqueados.find(a => a.estaAtivo);
    if (ativo) avatarSelecionadoId = ativo.avatar.id;

    const container = document.querySelector(".grid_avatar");
    container.innerHTML = "";

    todos.forEach(avatar => {
      const img = document.createElement("img");
      img.src = avatar.imagemUrl;
      img.alt = avatar.nome;
      img.dataset.id = avatar.id;

      if (avataresDesbloqueados.includes(avatar.id)) {
        img.classList.add("desbloqueado");

        if (avatar.id === avatarSelecionadoId) {
          img.classList.add("ativo");
        }

        img.addEventListener("click", () => {
          document.querySelectorAll(".grid_avatar img").forEach(i => i.classList.remove("ativo"));
          img.classList.add("ativo");
          avatarSelecionadoId = avatar.id;
        });
      } else {
        img.classList.add("bloqueado");
        img.title = "Avatar bloqueado";
      }

      container.appendChild(img);
    });
  } catch (error) {
    console.error("Erro ao carregar avatares:", error);
    alert("Erro inesperado ao carregar avatares");
  }
}

async function salvarAvatar() {
  if (!avatarSelecionadoId) {
    alert("Selecione um avatar desbloqueado");
    return;
  }

  try {
    const res = await fetch(`http://localhost:8080/usuarios/${userId}/avatares/${avatarSelecionadoId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({ estaAtivo: true })
    });

    if (!res.ok) {
      alert("Erro ao salvar avatar");
      return;
    }

    alert("Avatar atualizado com sucesso!");
    window.location.href = "../perfil/perfil.html";
  } catch (error) {
    console.error("Erro ao salvar avatar:", error);
    alert("Erro inesperado ao salvar avatar");
  }
}
