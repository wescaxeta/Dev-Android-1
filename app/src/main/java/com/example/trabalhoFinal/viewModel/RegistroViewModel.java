package com.example.trabalhoFinal.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.trabalhoFinal.dao.RegistroRepository;
import com.example.trabalhoFinal.modelo.Filme;

import java.util.List;

public class RegistroViewModel extends AndroidViewModel {

    private RegistroRepository mRegistroRepository;

    private LiveData<List<Filme>> mAllRegistros;
    private String mOrdenacao;

    public RegistroViewModel (Application application, String ordenacao) {
        super(application);
        mRegistroRepository = new RegistroRepository(application);
        mOrdenacao = ordenacao;
        mAllRegistros = mRegistroRepository.getAll(mOrdenacao);
    }

    public LiveData<List<Filme>> getAll(String ordenacao) {
        return mRegistroRepository.getAll(ordenacao);
    }

    public void insertAll(Filme... reg) { mRegistroRepository.insertAll(reg); }

    public void delete(Filme reg) { mRegistroRepository.delete(reg); }

    public void update(Filme reg) { mRegistroRepository.update(reg); }

    public static class RegistroViewModelFactory implements ViewModelProvider.Factory {
        private Application mApplication;
        private String ordenacao;

        public RegistroViewModelFactory(Application application, String ordenacao) {
            mApplication = application;
            this.ordenacao = ordenacao;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new RegistroViewModel(mApplication, ordenacao);
        }
    }
}