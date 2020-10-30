package br.wellington.eventos.database.contract;

import br.wellington.eventos.database.entity.EventoEntity;

public class EventoContract {

    private EventoContract(){}

    public static final String criarTabela(){
        return "CREATE TABLE " + EventoEntity.TABLE_NAME + " (" +
                EventoEntity._ID + " INTEGER PRIMARY KEY," +
                EventoEntity.COLUMN_NAME_NOME + " TEXT," +
                EventoEntity.COLUMN_NAME_DATA + " REAL," +
                EventoEntity.COLUMN_NAME_LOCAL + " REAL)";
    }

    public static final String removerTabela(){
        return "DROP TABLE IF EXISTS " + EventoEntity.TABLE_NAME;
    }
}
