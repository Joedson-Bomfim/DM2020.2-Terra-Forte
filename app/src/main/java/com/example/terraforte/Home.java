package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    public void acessarPerfil(View view) {
        Intent intent = new Intent(this, Perfil.class);
        startActivity(intent);
    }

    public void acessarReceituarios(View view) {
        Intent intent = new Intent(this, Receituarios.class);
        startActivity(intent);
    }
}
