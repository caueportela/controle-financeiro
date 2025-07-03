package edu.projetointegrador.controlefinanceiro.model;

public class Transacao {
    private long usuarioId;
    private double valor;
    private String categoria;   // nome, ex: "alimentação"
    private long idCategoria;   // id do banco
    private String descricao;

    // getters e setters para todos os campos
    public long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(long usuarioId) { this.usuarioId = usuarioId; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public long getIdCategoria() { return idCategoria; }
    public void setIdCategoria(long idCategoria) { this.idCategoria = idCategoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}

