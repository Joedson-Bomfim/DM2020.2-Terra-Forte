package com.example.terraforte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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

    private UsuarioDAO dao;

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

        int idradioButtonEscolhido = radioGroup.getCheckedRadioButtonId();

        radioButtonEscolhido = findViewById(idradioButtonEscolhido);

        dao = new UsuarioDAO(this);
    }

    public void acessarHome(View v) {
        if(email.getText().toString().isEmpty() || senha.getText().toString().isEmpty()){
            Toast.makeText(Cadastrar.this, "Preencha o email!", Toast.LENGTH_SHORT).show();
        } else {
            CadastrarUsuario(v);
        }
    }

    private void CadastrarUsuario(View v) {
        String edit_email = email.getText().toString();
        String edit_senha = senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edit_email,edit_senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Snackbar snackbar = Snackbar.make(v, "Cadastrado com sucesso",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.GREEN);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }else {
                    Snackbar snackbar = Snackbar.make(v, "Problema no cadastro",Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(Color.RED);
                    snackbar.setTextColor(Color.WHITE);
                    snackbar.show();
                }
            }
        });
    }

    //public void salvar(View view) {

    //}
}