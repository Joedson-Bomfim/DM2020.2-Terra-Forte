package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

    public void acessarHome(View view) {
        if(email.getText().toString().isEmpty()){
            Toast.makeText(Cadastrar.this, "Preencha o email!", Toast.LENGTH_SHORT).show();
        } else {
            Usuario usuario = new Usuario();
            usuario.setEmail(String.valueOf(email.getText()));
            usuario.setSenha(String.valueOf(senha.getText()));
            usuario.setNomeUsuario(String.valueOf(nome_usuario.getText()));
            usuario.setNomeCompleto(String.valueOf(nome_completo.getText()));
            usuario.setApelido(String.valueOf(apelido.getText()));
            usuario.setTelefone(String.valueOf(telefone.getText()));
            usuario.setEndereco(String.valueOf(endereco.getText()));
            usuario.setEndereco(String.valueOf(endereco.getText()));
            usuario.setFuncao(String.valueOf(radioGroup.getCheckedRadioButtonId()));

            UsuarioDAO usuarioDAO = new UsuarioDAO(getApplicationContext());
            usuarioDAO.cadastrar(usuario);
            usuarioDAO.close();

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }
}