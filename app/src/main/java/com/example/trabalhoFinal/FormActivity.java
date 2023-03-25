package com.example.trabalhoFinal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trabalhoFinal.modelo.Filme;

public class FormActivity extends AppCompatActivity {

    private EditText editTitulo;
    private CheckBox checkAssistido;
    private NumberPicker editNota;
    private Button buttonSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        editTitulo = findViewById(R.id.edit_titulo);
        editNota = findViewById(R.id.edit_nota);
        checkAssistido = findViewById(R.id.check_assistido);
        buttonSave = findViewById(R.id.button_save);

        editNota.setMinValue(0);
        editNota.setMaxValue(10);

        Filme reg = (Filme) getIntent().getSerializableExtra("registro");
        if (reg != null) {
            editTitulo.setText(reg.getTitulo());
            checkAssistido.setChecked(reg.getAssistido());
            editNota.setValue(reg.getNota());
        }

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if (TextUtils.isEmpty(editTitulo.getText().toString())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String titulo = editTitulo.getText().toString();
                    int nota = editNota.getValue();
                    boolean assistido = checkAssistido.isChecked();

                    if (reg != null) {
                        replyIntent.putExtra("id", reg.getId());
                    }

                    replyIntent.putExtra("titulo", titulo);
                    replyIntent.putExtra("nota", nota);
                    replyIntent.putExtra("assistido", assistido);

                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}
