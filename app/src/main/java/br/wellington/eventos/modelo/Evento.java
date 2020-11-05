package br.wellington.eventos.modelo;

import java.io.Serializable;

public class Evento implements Serializable {

    private int id;
    private String nome;
    private String data;
    private Locais locais;
    private String pesquisa;

    public Evento(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public Evento(int id, String nome, String data, Locais locais) {
        this.id = id;
        this.nome = nome;
        this.data = data;

        this.locais = locais;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public Locais getLocais() {
        return locais;
    }

    public void setLocais(Locais locais) {
        this.locais = locais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }






    @Override
    public String toString() {

        return this.nome + " - " + data + " | Local: " + locais;
    }
}
