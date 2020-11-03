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

import br.wellington.eventos.database.EventoDAO;
import br.wellington.eventos.modelo.Evento;

public class CadastroEventoActivity extends AppCompatActivity {

    private int id = 0;


    //DATAPICKER
    private DatePickerDialog.OnDateSetListener mostrarDataListener;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");

        carregarEvento();
        dataPicker();
    }

    private void dataPicker(){


        final EditText mostrarData= (EditText) findViewById(R.id.editText_data);

        mostrarData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal =  Calendar.getInstance();
                int dia = cal.get(Calendar.DAY_OF_MONTH);
                int mes = cal.get(Calendar.MONTH);
                int ano = cal.get(Calendar.YEAR);

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

    private void carregarEvento(){
        Intent intent = getIntent();
        if(intent!=null && intent.getExtras() != null && intent.getExtras().get("eventoEdicao") !=null){
            Evento evento = (Evento) intent.getExtras().get("eventoEdicao");
            EditText editTextNome = findViewById(R.id.editText_nome);
            EditText editTextData = findViewById(R.id.editText_data);
            EditText editTextLocal = findViewById(R.id.editText_local);

            editTextNome.setText(evento.getNome());
            editTextData.setText(evento.getData());
            editTextLocal.setText(evento.getLocal());
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
        EventoDAO eventoDao = new EventoDAO(getBaseContext());



        if(evento.getNome().matches("")){
            Toast.makeText(CadastroEventoActivity.this,
                    "Nome é obrigatório", Toast.LENGTH_LONG).show();
        }else if(evento.getData().matches("")){
            Toast.makeText(CadastroEventoActivity.this,
                    "Data é obrigatório", Toast.LENGTH_LONG).show();
        }else if(evento.getLocal().matches("")){
            Toast.makeText(CadastroEventoActivity.this,
                    "Local é obrigatório", Toast.LENGTH_LONG).show();
        }else{
            boolean salvou = eventoDao.salvar(evento);

            if(salvou){
                finish();
            }
            else{
                Toast.makeText(CadastroEventoActivity.this, "Erro ao salvar!", Toast.LENGTH_LONG).show();
            }
        }


    }
}