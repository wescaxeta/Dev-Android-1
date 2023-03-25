package com.example.trabalhoFinal.modelo;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Filme implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    public String titulo;
    public boolean assistido;
    public int nota;

    public Filme() {
    }

    public Filme(int id, String titulo, boolean assistido, int nota) {
        this.id = id;
        this.titulo = titulo;
        this.assistido = assistido;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean getAssistido() {
        return assistido;
    }

    public void setAssistido(boolean assistido) {
        this.assistido = assistido;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
