package com.example.terraforte;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import static com.example.terraforte.R.layout.login;

public class MainActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        IniciarComponentes();
    }

    private void IniciarComponentes() {
        email = findViewById(R.id.idEmailLogin);
        senha = findViewById(R.id.idSenhaLogin);
        progressBar = findViewById(R.id.pb_progressBar_login);
    }

    public void acessarHome(View view) {
        if ((email.getText().toString().isEmpty()) || (senha.getText().toString().isEmpty())) {
            Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
        }else {
            AutenticarUsuario();
        }
    }

    private void AutenticarUsuario() {
        String edit_email = email.getText().toString();
        String edit_senha = senha.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(edit_email,edit_senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressBar.setVisibility(View.VISIBLE);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            TelaPrincipal();
                        }
                    }, 2000);
                }else {
                    String erro;
                    try {
                        throw task.getException();
                    }catch (Exception e){
                        erro = "Usuário ou senha inválidos";
                    }
                    Toast.makeText(MainActivity.this, erro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser usuarioAtual = FirebaseAuth.getInstance().getCurrentUser();

        if(usuarioAtual != null) {
            TelaPrincipal();
        }
    }

    private void TelaPrincipal() {
        Intent intent = new Intent(MainActivity.this, Home.class);
        startActivity(intent);
        finish();
    }


    public void criarConta(View view) {
        Intent intent = new Intent(this, Cadastrar.class);
        startActivity(intent);
    }
}
