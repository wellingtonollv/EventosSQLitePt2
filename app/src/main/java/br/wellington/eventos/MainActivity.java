package br.wellington.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.EventLog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import br.wellington.eventos.modelo.Evento;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;

    private final int REQUEST_CODE_NOVO_EVENTO=1;

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
    }

    private ArrayList<Evento> criarListaEventos(){
        ArrayList<Evento> eventos = new ArrayList<Evento>();
        eventos.add(new Evento("Java para iniciantes","03/02/2021","SENAI"));
        eventos.add(new Evento("IOT- Internet das Coisas","04/02/2021","SENAI"));
        eventos.add(new Evento("Computação gráfica para iniciantes","04/02/2021","SENAI"));
        return eventos;
    }

    public void onClickNovoEvento(View v){
        Intent intent= new Intent(MainActivity.this, CadastroEventoActivity.class);
        startActivityForResult(intent,REQUEST_CODE_NOVO_EVENTO);
    }

}