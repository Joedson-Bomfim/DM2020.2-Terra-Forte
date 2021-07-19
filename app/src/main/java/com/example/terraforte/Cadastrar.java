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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cadastrar extends AppCompatActivity {
    private EditText email, senha, senha_confirmar, nome_usuario, nome_completo, apelido, telefone, endereco;
    String usuarioID;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);

        IniciarComponentes();
    }

    private void IniciarComponentes() {
        email = findViewById(R.id.idEmailCadastro);
        senha = findViewById(R.id.idSenhaCadastro);
        senha_confirmar = findViewById(R.id.idSenhaConfirmar);
        nome_usuario = findViewById(R.id.idNomeUsuarioCadastro);
        nome_completo = findViewById(R.id.idNomeCompletoCadastro);
        apelido = findViewById(R.id.idApelidoCadastro);
        telefone = findViewById(R.id.idTelefoneCadastro);
        endereco = findViewById(R.id.idEnderecoCadastro);
    }

    public void acessarLogin(View v) {
        if(email.getText().toString().isEmpty() || senha.getText().toString().isEmpty() || senha_confirmar.getText().toString().isEmpty() || nome_completo.getText().toString().isEmpty() || nome_usuario.getText().toString().isEmpty()){
            Toast.makeText(Cadastrar.this, "Apenas o apelido, telefone e endereço podem ser deixados em branco!", Toast.LENGTH_LONG).show();
        } else if(senha.getText().toString().equals(senha_confirmar.getText().toString())) {
            CadastrarUsuario(v);
            if(senha.length() >= 6) {
                AcessarLogin();
            }
        } else {
            Toast.makeText(Cadastrar.this, "Verifique senha e confirmar senha novamente", Toast.LENGTH_SHORT).show();
        }
    }

    private void CadastrarUsuario(View v) {
        String edit_email = email.getText().toString();
        String edit_senha = senha.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edit_email,edit_senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    SalvarDadosUsuario();

                    Toast.makeText(Cadastrar.this, "Você foi cadastrado com sucesso", Toast.LENGTH_SHORT).show();
                }else {
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha com no mínimo 6 caractéres";
                    }catch (FirebaseAuthUserCollisionException e) {
                        erro = "Este e-mail já foi cadastrada";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "E-mail inválido";
                    }catch (Exception e){
                        erro = "Erro ao cadastrar usuário";
                    }
                    Toast.makeText(Cadastrar.this, erro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SalvarDadosUsuario() {
        String edit_nome_usuario = nome_usuario.getText().toString();
        String edit_nome_completo = nome_completo.getText().toString();
        String edit_apelido = apelido.getText().toString();
        String edit_telefone = telefone.getText().toString();
        String edit_endereco = endereco.getText().toString();



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String,Object> usuarios = new HashMap<>();
        usuarios.put("nome_usuario", edit_nome_usuario);
        usuarios.put("nome_completo",edit_nome_completo);
        usuarios.put("apelido", edit_apelido);
        usuarios.put("telefone", edit_telefone);
        usuarios.put("endereco", edit_endereco);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao salvar os dados");
            }
        });
    }

    private void AcessarLogin() {
        Intent intent = new Intent(Cadastrar.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //public void salvar(View view) {

    //}
}