package com.example.trabalhoFinal.dao;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.trabalhoFinal.modelo.Filme;

import java.util.List;

public class RegistroRepository {
    private RegistroDao mRegistroDao;
    private LiveData<List<Filme>> mAllRegistros;

    public RegistroRepository(Application application) {
        RegistroDatabase db = RegistroDatabase.getDatabase(application);
        mRegistroDao = db.registroDao();
        mAllRegistros = mRegistroDao.getAlphabetizedTitulos();
    }

    public LiveData<List<Filme>> getAll(String ordenacao) {
        if (ordenacao.equals("titulo")) {
            return mRegistroDao.getAlphabetizedTitulos();
        } else {
            return mRegistroDao.getNotaOrderDesc();
        }
    }

    public void insertAll(Filme... filme) {
        RegistroDatabase.databaseWriteExecutor.execute(() -> {
            mRegistroDao.insertAll(filme);
        });
    }

    public void delete(Filme filme) {
        RegistroDatabase.databaseWriteExecutor.execute(() -> {
            mRegistroDao.delete(filme);
        });
    }

    public void update(Filme filme) {
        RegistroDatabase.databaseWriteExecutor.execute(() -> {
            mRegistroDao.update(filme);
        });
    }
}
