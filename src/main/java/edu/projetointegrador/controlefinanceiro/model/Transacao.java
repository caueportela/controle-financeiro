package edu.projetointegrador.controlefinanceiro.model;

public class Transacao {
    private long usuarioId;
    private double valor;
    private String categoria;
    private String descricao;

    public long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(long usuarioId) { this.usuarioId = usuarioId; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
