package br.wellington.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.wellington.eventos.database.LocaisDAO;
import br.wellington.eventos.modelo.Locais;

public class CadastroLocaisActivity extends AppCompatActivity {

    private int id =0;
    private EditText editTextNome;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextCapacidade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_locais);
        setTitle("Cadastro de Locais");
        editTextNome = findViewById(R.id.editText_nome_locais);
        editTextBairro = findViewById(R.id.editText_bairro_locais);
        editTextCidade = findViewById(R.id.editText_cidade_locais);
        editTextCapacidade = findViewById(R.id.editText_capacidade_locais);


        carregarLocais();
    }

    public void carregarLocais() {
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null &&
                intent.getExtras().get("locaisEdicao") != null){
            Locais locais = (Locais) intent.getExtras().get("locaisEdicao");
            editTextNome.setText(locais.getNome_locais());
            editTextBairro.setText(locais.getBairro());
            editTextCidade.setText(locais.getCidade());
            editTextCapacidade.setText(String.valueOf(locais.getCapacidade()));
            id= locais.getId();
        }
    }

    public void onClickVoltar(View v){
        finish();
    }

    public void onClickSalvar(View v){
        String nome = editTextNome.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade= editTextCidade.getText().toString();
        int capacidade = Integer.parseInt(editTextCapacidade.getText().toString());


        Locais locais = new Locais(id, nome, bairro, cidade, capacidade);
        LocaisDAO locaisDao = new LocaisDAO(getBaseContext());
        boolean salvou = locaisDao.salvar(locais);
        if(salvou){
            finish();
        }else{
            Toast.makeText(CadastroLocaisActivity.this, "Erro ao salvar!", Toast.LENGTH_LONG).show();
        }
    }

}