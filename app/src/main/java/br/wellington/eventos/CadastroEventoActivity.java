package br.wellington.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.wellington.eventos.modelo.Evento;

public class CadastroEventoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");
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

        Evento evento = new Evento(nome,data,local);
        finish();
    }
}