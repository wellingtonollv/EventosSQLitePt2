package br.wellington.eventos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.wellington.eventos.modelo.Evento;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private int id = 0;

    private final int REQUEST_CODE_NOVO_EVENTO = 1;
    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int REQUEST_CODE_EDITAR_EVENTO = 2;
    private final int RESULT_CODE_EVENTO_EDITADO = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");

        listViewEventos = findViewById(R.id.listView_eventos);
        ArrayList<Evento> eventos = new ArrayList<Evento>();

        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1, eventos);

        listViewEventos.setAdapter(adapterEventos);
        definirOnClickListenerListView();
        definirOnLongClickListener();

    }

//    private ArrayList<Evento> criarListaEventos(){
//        ArrayList<Evento> eventos = new ArrayList<Evento>();
//        eventos.add(new Evento("Java para iniciantes","03/02/2021","SENAI"));
//        eventos.add(new Evento("IOT- Internet das Coisas","04/02/2021","SENAI"));
//        eventos.add(new Evento("Computação gráfica para iniciantes","04/02/2021","SENAI"));
//        return eventos;
//    }

    private void definirOnLongClickListener(){
        listViewEventos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Evento eventoClicado = adapterEventos.getItem(position);

                new AlertDialog.Builder(MainActivity.this)
                        .setIcon((android.R.drawable.ic_delete))
                        .setTitle("Excluir o evento?")
                        .setMessage("Deseja excluir o evento?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                adapterEventos.remove(eventoClicado);
                                adapterEventos.notifyDataSetChanged();
                                Toast.makeText(MainActivity.this, "Evento Deletado",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Não",null).show();
                return true;
            }
        });
    }

    private void definirOnClickListenerListView(){
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoClicado    = adapterEventos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
                intent.putExtra("eventoEdicao", eventoClicado);
                startActivityForResult(intent, REQUEST_CODE_EDITAR_EVENTO);
            }
        });
    }

    public void onClickNovoEvento(View v){
        Intent intent= new Intent(MainActivity.this, CadastroEventoActivity.class);
        startActivityForResult(intent,REQUEST_CODE_NOVO_EVENTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_NOVO_EVENTO && resultCode==RESULT_CODE_NOVO_EVENTO){
            Evento evento= (Evento) data.getExtras().getSerializable("novoEvento");
            evento.setId(++id);
            this.adapterEventos.add(evento);
        }else if(requestCode==REQUEST_CODE_EDITAR_EVENTO && resultCode==RESULT_CODE_EVENTO_EDITADO){
            Evento eventoEditado =(Evento) data.getExtras().getSerializable("eventoEditado");
            for(int i = 0; i < adapterEventos.getCount(); i++){
                Evento evento = adapterEventos.getItem(i);
                if(evento.getId() == eventoEditado.getId()){
                    adapterEventos.remove(evento);
                    adapterEventos.insert(eventoEditado,i);
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);



    }
}