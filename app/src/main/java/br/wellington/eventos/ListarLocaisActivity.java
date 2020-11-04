package br.wellington.eventos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import br.wellington.eventos.database.EventoDAO;
import br.wellington.eventos.database.LocaisDAO;
import br.wellington.eventos.modelo.Evento;
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
        definirOnLongClickListener();
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
    private void definirOnLongClickListener(){
        listViewLocais.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Locais localClicado = adapterLocais.getItem(position);

                new AlertDialog.Builder(ListarLocaisActivity.this)
                        .setIcon((android.R.drawable.ic_delete))
                        .setTitle("Excluir esse local?")
                        .setMessage("Deseja excluir o local?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which){
                                //DELETE
                                LocaisDAO locaisDao = new LocaisDAO(getBaseContext());
                                locaisDao.delete(localClicado.getId());
                                //
                                adapterLocais.remove(localClicado);
                                adapterLocais.notifyDataSetChanged();
                                Toast.makeText(ListarLocaisActivity.this, "Local Deletado",Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("Não",null).show();
                return true;
            }
        });
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