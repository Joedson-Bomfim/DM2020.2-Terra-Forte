package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.terraforte.R.layout.login;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.idEmailLogin);
        senha = findViewById(R.id.idSenhaLogin);
    }

    public void acessarHome(View view) {
        if ((email.getText().toString().isEmpty()) || (senha.getText().toString().isEmpty())) {
            Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }

    public void criarConta(View view) {
        Intent intent = new Intent(this, Cadastrar.class);
        startActivity(intent);
    }
}
