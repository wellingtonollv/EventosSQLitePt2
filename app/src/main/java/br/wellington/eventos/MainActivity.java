package br.wellington.eventos;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import br.wellington.eventos.database.EventoDAO;
import br.wellington.eventos.modelo.Evento;

public class MainActivity extends AppCompatActivity {

    private ListView listViewEventos;
    private ArrayAdapter<Evento> adapterEventos;
    private int id = 0;
    private EditText editTextPesquisa;
    private List<String> listaPesquisa = new ArrayList<String>() {{
        add("");
        add("");
        add("ASC");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Eventos");
        listViewEventos = findViewById(R.id.listView_eventos);
        ArrayList<Evento> eventos = new ArrayList<Evento>();

        definirOnClickListenerListView();
        definirOnLongClickListener();
        Pesquisa();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1, eventoDao.listar(listaPesquisa));

        listViewEventos.setAdapter(adapterEventos);
    }

    private void Pesquisa(){
        final EditText editTextPesquisar = findViewById(R.id.editTextPesquisa);

        editTextPesquisar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String pesquisa = s.toString();
                listaPesquisa.set(0, pesquisa);
                EventoDAO eventoDao = new EventoDAO(getBaseContext());
                adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        eventoDao.listar(listaPesquisa));
                listViewEventos.setAdapter(adapterEventos);

            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

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
                                //DELETE
                                EventoDAO eventoDao = new EventoDAO(getBaseContext());
                                eventoDao.delete(eventoClicado.getId());
                                //
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

    //ASC E DESC

    public void onClickDescendente(View v) {
        listaPesquisa.set(2, "DESC");
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDao.listar(listaPesquisa));
        listViewEventos.setAdapter(adapterEventos);
    }

    public void onClickAscendente(View v) {
        listaPesquisa.set(2, "ASC");
        EventoDAO eventoDao = new EventoDAO(getBaseContext());
        adapterEventos = new ArrayAdapter<Evento>(MainActivity.this,
                android.R.layout.simple_list_item_1,
                eventoDao.listar(listaPesquisa));
        listViewEventos.setAdapter(adapterEventos);
    }

    private void definirOnClickListenerListView(){
        listViewEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoClicado    = adapterEventos.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroEventoActivity.class);
                intent.putExtra("eventoEdicao", eventoClicado);
                startActivity(intent);
            }
        });
    }

    public void onClickNovoEvento(View v){
        Intent intent= new Intent(MainActivity.this, CadastroEventoActivity.class);
        startActivity(intent);
    }

    public void onClickLocais(View v){
        Intent intent = new Intent(MainActivity.this, ListarLocaisActivity.class);
        startActivity(intent);
        finish();
    }
}