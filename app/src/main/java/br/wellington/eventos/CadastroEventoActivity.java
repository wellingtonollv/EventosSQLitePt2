package br.wellington.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.wellington.eventos.modelo.Evento;

public class CadastroEventoActivity extends AppCompatActivity {

    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int RESULT_CODE_EVENTO_EDITADO = 11;

    private boolean edicao = false;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");

        carregarProduto();
    }

    private void carregarProduto(){
        Intent intent = getIntent();
        if(intent!=null && intent.getExtras() != null && intent.getExtras().get("eventoEdicao") !=null){
            Evento evento = (Evento) intent.getExtras().get("eventoEdicao");
            EditText editTextNome = findViewById(R.id.editText_nome);
            EditText editTextData = findViewById(R.id.editText_data);
            EditText editTextLocal = findViewById(R.id.editText_local);

            editTextNome.setText(evento.getNome());
            editTextData.setText(evento.getData());
            editTextLocal.setText(evento.getLocal());
            edicao=true;
            id= evento.getId();
        }
    }

    public void onClickVoltar(View v){
        finish();
    }
    public void onClickSalvar(View v){
        EditText editTextNome = findViewById(R.id.editText_nome);
        EditText editTextData = findViewById(R.id.editText_data);
        EditText editTextLocal = findViewById(R.id.editText_local);

        String nome = editTextNome.getText().toString();
        String data = String.valueOf(editTextData.getText());
        String local = editTextLocal.getText().toString();

        Evento evento = new Evento(id,nome,data,local);

        Intent intent = new Intent();
        if(edicao){
            intent.putExtra("eventoEditado", evento);
            setResult(RESULT_CODE_EVENTO_EDITADO, intent);
        }else{
            intent.putExtra("novoEvento",evento);
            setResult(RESULT_CODE_NOVO_EVENTO, intent);
        }

        if(nome.matches("")){
            Toast.makeText(CadastroEventoActivity.this,"Nome é obrigatório", Toast.LENGTH_LONG).show();
        }else if(data.matches("")){
            Toast.makeText(CadastroEventoActivity.this,"Data é obrigatório", Toast.LENGTH_LONG).show();
        }else if(local.matches("")){
        Toast.makeText(CadastroEventoActivity.this,"Local é obrigatório", Toast.LENGTH_LONG).show();
        }else{
            finish();
        }

    }
}