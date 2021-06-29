package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

    UsuarioDAO db = new UsuarioDAO(this);

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

        //Criar OK
        /*Teste CRUD*/
        db.addUsuario(new Usuario("teste@gmail.com","123","teste","teste Testando","t","222333","rua teste","agricultor"));
        Toast.makeText(Cadastrar.this, "Salvo com sucesso", Toast.LENGTH_LONG).show();

        //Apagar OK
        /*Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        db.apagarUsuario(usuario);*/

        Usuario usuario = db.selecionarUsuario(1);
        Log.d("Usuario selecionado", "Id: " + usuario.getIdUsuario() + "email: " + usuario.getEmail() + "senha: " + usuario.getSenha() + "nome usuario: " + usuario.getNomeUsuario() + "nome completo: " + usuario.getNomeCompleto() + "Apelido: " + usuario.getApelido() + "Telefone: " + usuario.getTelefone() + "Endereço: " + usuario.getEndereco() + "Função: " + usuario.getFuncao());
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