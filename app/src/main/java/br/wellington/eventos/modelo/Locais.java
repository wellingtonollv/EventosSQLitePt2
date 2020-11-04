package br.wellington.eventos.modelo;

import java.io.Serializable;

public class Locais implements Serializable {

    private int id;
    private String nome_locais;
    private String bairro;
    private String cidade;
    private int capacidade;

    public Locais(int id, String nome, String bairro, String cidade, int capacidade) {
        this.id = id;
        this.nome_locais = nome;
        this.bairro = bairro;
        this.cidade = cidade;
        this.capacidade = capacidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome_locais() {
        return nome_locais;
    }

    public void setNome_locais(String nome_locais) {
        this.nome_locais = nome_locais;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    @Override
    public String toString() {
        return nome_locais + " - " + "Bairro:" + this.bairro + " - " + "Cidade: " + this.cidade + " - " + "Capacidade:" + this.capacidade + " pessoas";
    }
}
