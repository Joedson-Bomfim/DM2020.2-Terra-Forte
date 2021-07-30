package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Lista_Contatos extends AppCompatActivity {
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_contatos);

        IniciarComponentes();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Lista_Contatos.this, Lista_AddContato.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void IniciarComponentes() {
        button = findViewById(R.id.btn_AddContato);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
