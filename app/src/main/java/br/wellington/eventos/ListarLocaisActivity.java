package br.wellington.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import br.wellington.eventos.database.LocaisDAO;
import br.wellington.eventos.modelo.Locais;

public class ListarLocaisActivity extends AppCompatActivity {

    private ListView listViewLocais;
    private ArrayAdapter<Locais> adapterLocais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_locais);
        listViewLocais = findViewById(R.id.listView_locais);
        setTitle("Cadastro de Locais");
        definirOnClickListenerListView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocaisDAO locaisDao= new LocaisDAO(getBaseContext());
        adapterLocais = new ArrayAdapter<Locais>(ListarLocaisActivity.this,
                android.R.layout.simple_list_item_1,
                locaisDao.listar());
        listViewLocais.setAdapter(adapterLocais);

    }

    private void definirOnClickListenerListView(){
        listViewLocais.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Locais locaisClicado = adapterLocais.getItem(position);
                Intent intent = new Intent(ListarLocaisActivity.this, CadastroLocaisActivity.class);
                intent.putExtra( "locaisEdicao", locaisClicado);
                startActivity(intent);
            }
        });
    }
    public void onClickNovoLocais(View v){
        Intent intent = new Intent(ListarLocaisActivity.this, CadastroLocaisActivity.class);
        startActivity(intent);
    }

    public void onClickEventos(View v){
        Intent intent = new Intent(ListarLocaisActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}