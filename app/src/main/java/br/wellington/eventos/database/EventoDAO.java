package br.wellington.eventos.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import br.wellington.eventos.database.entity.EventoEntity;
import br.wellington.eventos.database.entity.LocaisEntity;
import br.wellington.eventos.modelo.Evento;
import br.wellington.eventos.modelo.Locais;

public class EventoDAO {

    private final String SQL_LISTAR_TODOS = "SELECT eventos._id, nome, data, local, idlocais, nomelocais, bairro, cidade, capacidade FROM " +
            EventoEntity.TABLE_NAME + " INNER JOIN " + LocaisEntity.TABLE_NAME + " ON " +
            EventoEntity.COLUMN_NAME_ID_LOCAL + " = " + LocaisEntity.TABLE_NAME + "." + LocaisEntity._ID;

    private DBGateway dbGateway;

    public EventoDAO(Context context){
        dbGateway = DBGateway.getInstance(context);
    }

    public boolean salvar(Evento evento){
        ContentValues contentValues = new ContentValues();
        contentValues.put(EventoEntity.COLUMN_NAME_NOME, evento.getNome());
        contentValues.put(EventoEntity.COLUMN_NAME_DATA, evento.getData());

        contentValues.put(EventoEntity.COLUMN_NAME_ID_LOCAL, evento.getLocais().getId());

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

            int idLocais = cursor.getInt(cursor.getColumnIndex(EventoEntity.COLUMN_NAME_ID_LOCAL));
            String nome_locais = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_NOME_LOCAIS));
            String bairro = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_BAIRRO));
            String cidade = cursor.getString(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CIDADE));
            int capacidade = cursor.getInt(cursor.getColumnIndex(LocaisEntity.COLUMN_NAME_CAPACIDADE));
            Locais locais = new Locais(idLocais, nome_locais, bairro, cidade, capacidade);
            eventos.add(new Evento(id, nome, data, locais));
        }
        cursor.close();
        return eventos;
    }


}
