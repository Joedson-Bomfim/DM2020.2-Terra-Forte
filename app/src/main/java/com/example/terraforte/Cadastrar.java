package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Cadastrar extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private EditText senha_confirmar;
    private EditText nome_usuario;
    private EditText nome_completo;
    private EditText apelido;
    private EditText telefone;
    private EditText endereco;

    private RadioGroup radioGroup;
    private RadioButton radioButtonEscolhido;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);

        email = findViewById(R.id.idEmailCadastro);
        senha = findViewById(R.id.idSenhaCadastro);
        senha_confirmar = findViewById(R.id.idSenhaConfirmar);
        nome_usuario = findViewById(R.id.idNomeUsuarioCadastro);
        nome_completo = findViewById(R.id.idNomeCompletoCadastro);
        apelido = findViewById(R.id.idApelidoCadastro);
        telefone = findViewById(R.id.idTelefoneCadastro);
        endereco = findViewById(R.id.idEnderecoCadastro);

        radioGroup = findViewById(R.id.radioGroup);
    }

    /*
    public void acessarHome(View view) {
        if(email.getText().toString().isEmpty()){
            Toast.makeText(Cadastrar.this, "Preencha o email!", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }*/

    public void salvar(View view) {
        Usuario u = new Usuario();
        u.setEmail(email.getText().toString());
        u.setSenha(senha.getText().toString());
        u.setNomeUsuario(nome_usuario.getText().toString());
        u.setNomeCompleto(nome_completo.getText().toString());
        u.setApelido(apelido.getText().toString());
        u.setTelefone(telefone.getText().toString());
        u.setEndereco(endereco.getText().toString());

    }
}