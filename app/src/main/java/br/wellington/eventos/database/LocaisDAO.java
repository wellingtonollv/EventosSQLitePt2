package br.wellington.eventos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.wellington.eventos.database.entity.LocaisEntity;
import br.wellington.eventos.modelo.Locais;

public class LocaisDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + LocaisEntity.TABLE_NAME;

    private DBGateway dbGateway;

    public LocaisDAO (Context context){
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Locais local){
        ContentValues contentValues = new ContentValues();
        contentValues.put(LocaisEntity.COLUMN_NAME_NOME_LOCAIS, local.getNome_locais());
        contentValues.put(LocaisEntity.COLUMN_NAME_BAIRRO, local.getBairro());
        contentValues.put(LocaisEntity.COLUMN_NAME_CIDADE, local.getCidade());
        contentValues.put(LocaisEntity.COLUMN_NAME_CAPACIDADE, local.getCapacidade());


        if(local.getId() > 0) {
            return dbGateway.getDataBase().update(LocaisEntity.TABLE_NAME,
                    contentValues,
                    LocaisEntity._ID + "=?",
                    new String[]{String.valueOf(local.getId())}) > 0;
        }

        return dbGateway.getDataBase().insert(LocaisEntity.TABLE_NAME,
                null, contentValues) > 0;

    }

    public boolean delete(long id) {
        return dbGateway.getDataBase().delete(LocaisEntity.TABLE_NAME,
                LocaisEntity._ID + "=" + id , null) > 0;
    }

    public List<Locais> listar(){
        List<Locais> local = new ArrayList<>();
        Cursor cursor = dbGateway.getDataBase().rawQuery(SQL_LISTAR_TODOS,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(LocaisEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_NOME_LOCAIS));
            String bairro = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CAPACIDADE));

            local.add(new Locais(id, nome, bairro, cidade, capacidade));
        }
        cursor.close();
        return local;
    }



}
