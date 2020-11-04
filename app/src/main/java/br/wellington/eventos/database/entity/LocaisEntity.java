package br.wellington.eventos.database.entity;

import android.provider.BaseColumns;

public class LocaisEntity implements BaseColumns {

    private LocaisEntity(){}

    public static final String TABLE_NAME = "locais";
    public static final String COLUMN_NAME_NOME_LOCAIS = "nomelocais";
    public static final String COLUMN_NAME_BAIRRO = "bairro";
    public static final String COLUMN_NAME_CIDADE = "cidade";
    public static final String COLUMN_NAME_CAPACIDADE = "capacidade";



}
