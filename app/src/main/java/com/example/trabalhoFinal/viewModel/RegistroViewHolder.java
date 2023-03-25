package com.example.trabalhoFinal.viewModel;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trabalhoFinal.FormActivity;
import com.example.trabalhoFinal.R;
import com.example.trabalhoFinal.modelo.Filme;

import static com.example.trabalhoFinal.MainActivity.EDIT_REG_ACTIVITY_REQUEST_CODE;

public class RegistroViewHolder extends RecyclerView.ViewHolder {

    public TextView titulo;
    public TextView assistido;
    public TextView nota;
    public ImageButton editButton;
    public ImageButton deleteButton;

    public RegistroViewHolder(View itemView) {
        super(itemView);

        titulo = itemView.findViewById(R.id.text_titulo);
        assistido = itemView.findViewById(R.id.text_assistido);
        nota = itemView.findViewById(R.id.text_nota);
        editButton = itemView.findViewById(R.id.button_edit);
        deleteButton = itemView.findViewById(R.id.button_delete);
    }

    public void bind(Filme filme) {
        titulo.setText(filme.getTitulo());
        nota.setText(filme.getAssistido() ? String.valueOf(filme.getNota()) : "Sem nota");
        assistido.setText(filme.getAssistido() ? "Sim" : "Não");

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itemView.getContext(), FormActivity.class);
                intent.putExtra("registro", filme);
                ((Activity)itemView.getContext()).startActivityForResult(intent, EDIT_REG_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    static RegistroViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new RegistroViewHolder(view);
    }
}
