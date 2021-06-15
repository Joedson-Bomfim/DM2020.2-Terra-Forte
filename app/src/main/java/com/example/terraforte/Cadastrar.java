package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Cadastrar extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private EditText senha_confirmar;
    private EditText usuario;
    private EditText nome_completo;
    private EditText apelido;
    private EditText telefone;
    private EditText endereço;
    private RadioButton funcao;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);

        email = findViewById(R.id.idEmailCadastro);
        senha = findViewById(R.id.idSenhaCadastro);
        senha_confirmar = findViewById(R.id.idSenhaConfirmar);
        usuario = findViewById(R.id.idUsuario);
        nome_completo = findViewById(R.id.idNomeCompleto);
        apelido = findViewById(R.id.idApelido);
        telefone = findViewById(R.id.idTelefone);
        endereço = findViewById(R.id.idEndereco);
        funcao = findViewById(R.id.radioGroup);
    }

    public void acessarHome(View view) {
        if(email.getText().toString().isEmpty()){
            Toast.makeText(Cadastrar.this, "Preencha o email!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }
}
