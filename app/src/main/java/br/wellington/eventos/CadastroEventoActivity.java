package br.wellington.eventos;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import br.wellington.eventos.modelo.Evento;

public class CadastroEventoActivity extends AppCompatActivity {

    private final int RESULT_CODE_NOVO_EVENTO = 10;
    private final int RESULT_CODE_EVENTO_EDITADO = 11;

    private boolean edicao = false;
    private int id = 0;


    //DATAPICKER
    private DatePickerDialog.OnDateSetListener mostrarDataListener;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");

        carregarProduto();
        dataPicker();
    }

    private void dataPicker(){


        final EditText mostrarData= (EditText) findViewById(R.id.editText_data);

        mostrarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal =  Calendar.getInstance();
                int ano = cal.get(Calendar.YEAR);
                int mes = cal.get(Calendar.MONTH);
                int dia = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(CadastroEventoActivity.this,
                        android.R.style.Theme_Holo_Dialog_NoActionBar_MinWidth, mostrarDataListener, ano, mes,dia);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
                dialog.show();
            }
        });
        mostrarDataListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month +1;
                String data= dayOfMonth + "/" + month + "/" +year;
                mostrarData.setText(data);
            }
        };

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