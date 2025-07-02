const form = document.getElementById("formTransacao");
const saldoEl = document.getElementById("saldo");
const tbody = document.querySelector("table tbody");

let transacoes = []; // vamos popular via backend

// Atualiza a tabela com as transações da variável transacoes
function atualizarTabela() {
    tbody.innerHTML = "";

    transacoes.forEach((transacao) => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
      <td>${transacao.descricao}</td>
      <td>R$ ${transacao.valor.toFixed(2)}</td>
      <td>${transacao.categoria}</td>
    `;
        tbody.appendChild(tr);
    });
}

// Atualiza o saldo com base nas transações da variável transacoes
function atualizarSaldo() {
    const total = transacoes.reduce((soma, item) => soma + item.valor, 0);
    saldoEl.textContent = `R$ ${total.toFixed(2)}`;
}

// Função que carrega as transações do backend
async function carregarTransacoes() {
    try {
        const res = await fetch("http://localhost:8080/controle-financeiro/transacoes");
        if (!res.ok) throw new Error("Falha ao carregar transações");
        transacoes = await res.json();
        atualizarTabela();
        atualizarSaldo();
    } catch (err) {
        alert(err.message);
    }
}

form.addEventListener("submit", async function (e) {
    e.preventDefault();

    const descricao = form.children[0].value.trim();
    const valor = parseFloat(form.children[1].value);
    const categoria = form.children[2].value;

    if (!descricao || isNaN(valor)) {
        alert("Preencha todos os campos corretamente.");
        return;
    }

    const novaTransacao = {
        descricao,
        valor,
        categoria,
    };

    try {
        const res = await fetch("http://localhost:8080/controle-financeiro/transacoes", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(novaTransacao),
        });

        if (!res.ok) throw new Error("Erro ao enviar para o servidor");
        const data = await res.json();
        console.log("Transação salva no servidor:", data);

        // Atualiza local com a nova transação
        transacoes.push(novaTransacao);
        atualizarTabela();
        atualizarSaldo();

        form.reset();
    } catch (err) {
        console.error("Erro:", err);
        alert("Erro ao salvar transação no servidor.");
    }
});

// Inicializa o app carregando as transações do backend
carregarTransacoes();
