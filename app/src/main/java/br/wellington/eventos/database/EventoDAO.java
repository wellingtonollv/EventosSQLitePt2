package br.wellington.eventos.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import br.wellington.eventos.CadastroEventoActivity;
import br.wellington.eventos.MainActivity;
import br.wellington.eventos.database.entity.EventoEntity;
import br.wellington.eventos.modelo.Evento;

public class EventoDAO {

    private final String SQL_LISTAR_TODOS = "SELECT * FROM " + EventoEntity.TABLE_NAME;

    private DBGateway dbGateway;

    public EventoDAO(Context context){
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Evento evento){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, evento.getData());
        contentValues.put(EventoEntity.COLUMN_NAME_LOCAL, evento.getLocal());

            if(evento.getId() > 0) {
                return dbGateway.getDataBase().update(EventoEntity.TABLE_NAME,
                        contentValues,
                        EventoEntity._ID + "=?",
                        new String[]{String.valueOf(evento.getId())}) > 0;
            }

            return dbGateway.getDataBase().insert(EventoEntity.TABLE_NAME,
                    null, contentValues) > 0;



    }

    public boolean delete(long id) {
        return dbGateway.getDataBase().delete(EventoEntity.TABLE_NAME,
                EventoEntity._ID + "=" + id,
                null) > 0;
    }

    public List<Evento> listar(){
        List<Evento> eventos = new ArrayList<>();
        Cursor cursor = dbGateway.getDataBase().rawQuery(SQL_LISTAR_TODOS,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex(EventoEntity._ID));
            String nome = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_NOME));
            String data = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_DATA));
            String local = cursor.getString(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_LOCAL));
            eventos.add(new Evento(id, nome, data, local));
        }
        cursor.close();
        return eventos;
    }


}
