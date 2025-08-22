const userId = localStorage.getItem("id");
const token = localStorage.getItem("token");

let tituloSelecionadoId = null;
let adjetivosSelecionados = [];

async function carregarPerfil() {
  const headers = { Authorization: `Bearer ${token}` };

  const [usuarioRes, pronomesRes] = await Promise.all([
    fetch(`http://localhost:8080/usuarios/${userId}`, { headers }),
    fetch("http://localhost:8080/pronomes", { headers })
  ]);

  if (!usuarioRes.ok || !pronomesRes.ok) {
    alert("Erro ao carregar dados do servidor");
    return;
  }

  const usuario = await usuarioRes.json();
  const pronomes = await pronomesRes.json();

  document.getElementById("input_cadastro").value = usuario.nome || "";
  document.getElementById("input_email").value = usuario.email || "";

  renderizarPronomes(pronomes, usuario.pronome);
  carregarTitulos();
  carregarAdjetivos();
}

async function carregarAdjetivos() {
  try {
    if (!token || !userId) {
      console.warn("Token ou ID do usuário não encontrado.");
      return;
    }

    const [resAdjetivos, resSelecionados] = await Promise.all([
      fetch("http://localhost:8080/adjetivos", {
        headers: { Authorization: `Bearer ${token}` }
      }),
      fetch(`http://localhost:8080/usuarios/${userId}/adjetivos`, {
        headers: { Authorization: `Bearer ${token}` }
      })
    ]);

    if (!resAdjetivos.ok || !resSelecionados.ok) {
      console.error("Erro ao buscar adjetivos:", resAdjetivos.status, resSelecionados.status);
      return;
    }

    const lista = await resAdjetivos.json();
    const selecionados = await resSelecionados.json();

    const idsSelecionados = [...new Set(selecionados.map(item => item.adjetivo.id))];
    adjetivosSelecionados = [...idsSelecionados];

    const container = document.querySelector(".container_adjetivos");
    container.innerHTML = "";

    lista.forEach(adj => {
      const btn = document.createElement("button");
      btn.className = "adjetivo";
      btn.textContent = adj.nome;
      btn.dataset.id = adj.id;

      if (idsSelecionados.includes(adj.id)) {
        btn.classList.add("ativo");
      }

      btn.addEventListener("click", () => {
        const id = parseInt(btn.dataset.id);
        const index = adjetivosSelecionados.indexOf(id);

        if (index >= 0) {
          adjetivosSelecionados.splice(index, 1);
          btn.classList.remove("ativo");
        } else if (adjetivosSelecionados.length < 3) {
          adjetivosSelecionados.push(id);
          btn.classList.add("ativo");
        }
      });

      container.appendChild(btn);
    });

  } catch (error) {
    console.error("Erro ao carregar adjetivos:", error);
  }
}

function renderizarPronomes(lista, ativo) {
  const container = document.querySelector(".container_pronomes");
  container.innerHTML = "";

  lista.forEach(p => {
    const btn = document.createElement("button");
    btn.className = "pronome";
    btn.textContent = p.nome;
    btn.dataset.id = p.id;

    if (p.id === ativo?.id) {
      btn.classList.add("ativo");
    }

    btn.addEventListener("click", async () => {
      container.querySelectorAll("button").forEach(b => b.classList.remove("ativo"));
      btn.classList.add("ativo");

      try {
        await fetch(`http://localhost:8080/usuarios/${userId}/pronome`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`
          },
          body: JSON.stringify({ id: p.id })
        });
      } catch (error) {
        console.error("Erro ao atualizar pronome:", error);
      }
    });

    container.appendChild(btn);
  });
}

async function carregarTitulos() {
  try {
    if (!token || !userId) {
      console.warn("Token ou ID do usuário não encontrado.");
      return;
    }

    const [resTodos, resUsuario] = await Promise.all([
      fetch("http://localhost:8080/titulos", {
        headers: { Authorization: `Bearer ${token}` }
      }),
      fetch(`http://localhost:8080/usuarios/${userId}/titulos`, {
        headers: { Authorization: `Bearer ${token}` }
      })
    ]);

    if (!resTodos.ok || !resUsuario.ok) {
      console.error("Erro ao buscar títulos:", resTodos.status, resUsuario.status);
      return;
    }

    const todosTitulos = await resTodos.json();
    const titulosUsuario = await resUsuario.json();

    const container = document.querySelector(".container_titulos");
    container.innerHTML = "";

    const desbloqueadosMap = new Map();
    titulosUsuario.forEach(t => {
      if (t.desbloqueadoEm) {
        desbloqueadosMap.set(t.titulo.id, t);
      }
    });

    todosTitulos.forEach(titulo => {
      const btn = document.createElement("button");
      btn.className = "adjetivo";
      btn.textContent = titulo.nome;
      btn.dataset.id = titulo.id;

      const desbloqueado = desbloqueadosMap.get(titulo.id);

      if (desbloqueado) {
        btn.dataset.usuarioTituloId = desbloqueado.id;

        if (desbloqueado.estaAtivo) {
          btn.classList.add("ativo");
          tituloSelecionadoId = desbloqueado.id;
        }

        btn.addEventListener("click", async () => {
          container.querySelectorAll("button").forEach(b => b.classList.remove("ativo"));
          btn.classList.add("ativo");
          tituloSelecionadoId = desbloqueado.id;

          try {
            await fetch(`http://localhost:8080/usuarios/${userId}/titulo`, {
              method: "PUT",
              headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
              },
              body: JSON.stringify({ id: desbloqueado.id })
            });
          } catch (error) {
            console.error("Erro ao atualizar título ativo:", error);
          }
        });

      } else {
        btn.classList.add("bloqueado");
        btn.title = `Bloqueado: ${titulo.condicaoDesbloqueio}`;
      }

      container.appendChild(btn);
    });

  } catch (error) {
    console.error("Erro ao carregar títulos:", error);
  }
}

async function salvarAlteracoes() {
  const emailAntigo = localStorage.getItem("email");

  const nome = document.getElementById("input_cadastro").value.trim();
  const email = document.getElementById("input_email").value.trim();
  const pronomeBtn = document.querySelector(".container_pronomes .ativo");

  const payload = {};
  const updatePromises = [];

  if (nome) payload.nome = nome;
  if (email) payload.email = email;
  if (pronomeBtn) payload.idPronome = parseInt(pronomeBtn.dataset.id);

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

  const updateResults = await Promise.all(updatePromises);
  const updateSuccess = updateResults.every(res => res.ok);

  let adjetivoSuccess = true;
  try {
    const atuaisRes = await fetch(`http://localhost:8080/usuarios/${userId}/adjetivos`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (atuaisRes.ok) {
      const atuais = await atuaisRes.json();

      const deletePromises = atuais.map(item =>
        fetch(`http://localhost:8080/usuarios/${userId}/adjetivos/${item.id}`, {
          method: "DELETE",
          headers: { Authorization: `Bearer ${token}` }
        })
      );
      await Promise.all(deletePromises);

      await new Promise(resolve => setTimeout(resolve, 100));

      const idsUnicos = [...new Set(adjetivosSelecionados)];
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
      adjetivoSuccess = postResults.every(res => res.ok);
    }
  } catch (error) {
    console.error("Erro ao atualizar adjetivos:", error);
    adjetivoSuccess = false;
  }

  if (updateSuccess && adjetivoSuccess) {
    if (payload.nome) localStorage.setItem("nome", payload.nome);
    if (payload.email) localStorage.setItem("email", payload.email);
    if (payload.idPronome) {
      const pronomeSelecionado = document.querySelector(`.container_pronomes button[data-id="${payload.idPronome}"]`);
      if (pronomeSelecionado) {
        localStorage.setItem("pronome", JSON.stringify({ id: payload.idPronome, nome: pronomeSelecionado.textContent }));
      }
    }

    if (tituloSelecionadoId) {
      const tituloSelecionado = document.querySelector(`.container_titulos button[data-id="${tituloSelecionadoId}"]`);
      if (tituloSelecionado) {
        localStorage.setItem("titulos", JSON.stringify([
          { id: tituloSelecionadoId, titulo: { nome: tituloSelecionado.textContent }, estaAtivo: true }
        ]));
      }
    }

    const novosAdjetivosRes = await fetch(`http://localhost:8080/usuarios/${userId}/adjetivos`, {
      headers: { Authorization: `Bearer ${token}` }
    });
    if (novosAdjetivosRes.ok) {
      const novosAdjetivos = await novosAdjetivosRes.json();
      localStorage.setItem("adjetivos", JSON.stringify(novosAdjetivos));
    }

    if (payload.email && payload.email !== emailAntigo) {
      alert("Email alterado. Por segurança, faça login novamente.");
      localStorage.clear();
      window.location.href = "../../entrar/entrar.html";
    } else {
      alert("Perfil atualizado com sucesso!");
      window.location.href = "../perfil/perfil.html";
    }
  } else {
    alert("Erro ao salvar alterações");
  }
}

function redirecionarAlterarAvatar() {
  window.location.href = "../editar-imagem/editar-imagem.html";
}

function carregarEventos() {
  carregarUsuario();
  carregarPerfil();
  document.querySelector(".botao_comum").addEventListener("click", salvarAlteracoes);

  const alterarAvatar = document.getElementById("btn_avatar");
  alterarAvatar.addEventListener("click", redirecionarAlterarAvatar);
}

window.addEventListener("load", carregarEventos);