package com.example.terraforte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class EditarUsuario extends AppCompatActivity {
    private EditText nome_completo, nome_usuario, apelido, telefone, endereco;
    private Button cancelar, atualizar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_usuario);
        IniciarComponentes();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        atualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AtualizarPerfil();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VoltarPerfil();
            }
        });
    }

    private void IniciarComponentes() {
        nome_completo = findViewById(R.id.idNomeCompletoEditPerfil);
        nome_usuario = findViewById(R.id.idNomeUsuarioEditPerfil);
        apelido = findViewById(R.id.idApelidoEditPerfil);
        telefone = findViewById(R.id.idTelefoneEditPerfil);
        endereco = findViewById(R.id.idEnderecoEditPerfil);
        cancelar = findViewById(R.id.btn_cancelar_editPerfil);
        atualizar = findViewById(R.id.btn_atualizar_editPerfil);
    }

    private void AtualizarPerfil() {
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);

        String edit_nome_completo = nome_completo.getText().toString();
        String edit_usuario = nome_usuario.getText().toString();
        String edit_apelido = apelido.getText().toString();
        String edit_telefone = telefone.getText().toString();
        String edit_endereco = endereco.getText().toString();

        documentReference.update("nome_completo", edit_nome_completo,"nome_usuario", edit_usuario, "apelido", edit_apelido, "telefone", edit_telefone, "endereco", edit_endereco).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao atualizar os dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db_error", "Erro ao atualizar os dados");
            }
        });
        VoltarPerfil();
    }

    private void VoltarPerfil() {
        Intent intent = new Intent(this, Perfil.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String edit_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null) {
                    nome_completo.setText(documentSnapshot.getString("nome_completo"));
                    nome_usuario.setText(documentSnapshot.getString("nome_usuario"));
                    apelido.setText(documentSnapshot.getString("apelido"));
                    telefone.setText(documentSnapshot.getString("telefone"));
                    endereco.setText(documentSnapshot.getString("endereco"));
                }
            }
        });
    }
}