package com.example.trabalhoFinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.trabalhoFinal.modelo.Filme;
import com.example.trabalhoFinal.viewModel.RegistroListAdapter;
import com.example.trabalhoFinal.viewModel.RegistroViewModel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.trabalhoFinal.databinding.ActivityMainBinding;

import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements ConsultaApiAsyncTask.ListenerConsultaFilme {

    private ActivityMainBinding binding;
    private RegistroViewModel mRegistroViewModel;
    public RegistroListAdapter adapter;
    private SharedPreferences sharedPreferences;

    public static final int NEW_REG_ACTIVITY_REQUEST_CODE = 1;
    public static final int EDIT_REG_ACTIVITY_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String defaultValue = "titulo";
        String ordenacao = sharedPreferences.getString("ordenacao", defaultValue);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.radioGroup.check(ordenacao.equals("titulo") ? binding.radioTitulo.getId() : binding.radioNota.getId());

        mRegistroViewModel = new ViewModelProvider(this, new RegistroViewModel.RegistroViewModelFactory(this.getApplication(), ordenacao)).get(RegistroViewModel.class);

        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.recyclerview);
        adapter = new RegistroListAdapter(mRegistroViewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mRegistroViewModel.getAll(ordenacao).observe(this, regs -> {
            adapter.submitList(regs);
        });

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String novaOrdenacao = "";

                if (checkedId == binding.radioNota.getId()) {
                    editor.putString("ordenacao", "nota");
                    editor.commit();
                    novaOrdenacao = "nota";
                } else {
                    editor.putString("ordenacao", "titulo");
                    editor.commit();
                    novaOrdenacao = "titulo";
                }

                mRegistroViewModel.getAll(novaOrdenacao).observe(MainActivity.this, regs -> {
                    adapter.submitList(regs);
                });
            }
        });

        binding.btnApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConsultaApiAsyncTask(MainActivity.this).execute();
            }
        });

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FormActivity.class);
                startActivityForResult(intent, NEW_REG_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_REG_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Filme reg = new Filme(0, data.getStringExtra("titulo"), data.getBooleanExtra("assistido", false),
                    data.getIntExtra("nota", 0));
            mRegistroViewModel.insertAll(reg);
        } else if (requestCode == EDIT_REG_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Filme reg = new Filme(data.getIntExtra("id", 0), data.getStringExtra("titulo"),
                    data.getBooleanExtra("assistido", false), data.getIntExtra("nota", 0));
            mRegistroViewModel.update(reg);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConcludeConsultaFilme(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage(result)
                .setTitle("Filme: Movie 43");

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}