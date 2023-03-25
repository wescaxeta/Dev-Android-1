package com.example.trabalhoFinal.viewModel;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.trabalhoFinal.modelo.Filme;

public class RegistroListAdapter extends ListAdapter<Filme, RegistroViewHolder> {

    private RegistroViewModel mRegistroViewModel;

    public RegistroListAdapter(RegistroViewModel mRegistroViewModel) {
        super(DIFF_CALLBACK);

        this.mRegistroViewModel = mRegistroViewModel;
    }

    @Override
    public RegistroViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RegistroViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RegistroViewHolder holder, int position) {
        Filme current = getItem(position);

        holder.deleteButton.setOnClickListener(view -> mRegistroViewModel.delete(current));

        holder.bind(current);
    }

    public static final DiffUtil.ItemCallback<Filme> DIFF_CALLBACK =
        new DiffUtil.ItemCallback<Filme>() {
            @Override
            public boolean areItemsTheSame(
                    @NonNull Filme oldReg, @NonNull Filme newReg) {
                return oldReg.getId() == newReg.getId();
            }
            @Override
            public boolean areContentsTheSame(
                    @NonNull Filme oldReg, @NonNull Filme newReg) {
                return (oldReg.getTitulo() != null && oldReg.getTitulo().equals(newReg.getTitulo()))
                        && (oldReg.getNota() == newReg.getNota())
                        && (oldReg.getAssistido() == newReg.getAssistido());
            }
        };
}