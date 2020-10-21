package br.wellington.eventos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    private final int REQUEST_CODE_NOVO_EVENTO = 1;
    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int REQUEST_CODE_EDITAR_EVENTO = 2;
    private final int RESULT_CODE_EVENTO_EDITADO = 11;

    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");

        listViewEventos = findViewById(R.id.listView_eventos);
        ArrayList<Evento> eventos = this.criarListaEventos();

        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1, eventos);

        listViewEventos.setAdapter(adapterEventos);
        definirOnClickListenerListView();

    }

    private ArrayList<Evento> criarListaEventos(){
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        eventos.add(new Evento("Java para iniciantes","03/02/2021","SENAI"));
        eventos.add(new Evento("IOT- Internet das Coisas","04/02/2021","SENAI"));
        eventos.add(new Evento("Computação gráfica para iniciantes","04/02/2021","SENAI"));
        return eventos;
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
            this.adapterEventos.add(evento);
        }else if(requestCode==REQUEST_CODE_EDITAR_EVENTO && resultCode==RESULT_CODE_EVENTO_EDITADO){
            Evento evento =(Evento) data.getExtras().getSerializable("eventoEditado");
            Toast.makeText(MainActivity.this,"Editado",Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);



    }
}