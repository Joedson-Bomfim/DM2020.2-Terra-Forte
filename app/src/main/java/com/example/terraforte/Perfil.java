package com.example.terraforte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;

public class Perfil extends AppCompatActivity {
    private TextView nome_completo, nome_usuario, apelido, email, telefone, endereco;
    private Button bt_deslogar, bt_editar, bt_excluir_conta;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        IniciarComponentes();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deslogar();
            }
        });

        bt_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditarPerfil();
            }
        });

        bt_excluir_conta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExcluirConta();
                Deslogar();
            }
        });
    }

    private void IniciarComponentes() {
        nome_completo = findViewById(R.id.idNomeCompletoPerfil);
        nome_usuario = findViewById(R.id.idNomeUsuarioPerfil);
        apelido = findViewById(R.id.idApelidoPerfil);
        email = findViewById(R.id.idEmailPerfil);
        telefone = findViewById(R.id.idTelefonePerfil);
        endereco = findViewById(R.id.idEnderecoPerfil);
        bt_deslogar = findViewById(R.id.btn_deslogar_perfil);
        bt_editar = findViewById(R.id.btn_editar_perfil);
        bt_excluir_conta = findViewById(R.id.btn_deletar_Perfil);
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

                    email.setText(edit_email);
                }
            }
        });
    }

    private void ExcluirConta() {
        db.collection("Usuarios").document(usuarioID)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("db", "Sucesso ao excluir os dados");
                        Toast.makeText(Perfil.this, "Conta excluída!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("db_error", "Erro ao excluir os dados");
                        Toast.makeText(Perfil.this, "Erro ao excluir a conta", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void EditarPerfil() {
        Intent intent = new Intent(this, EditarUsuario.class);
        startActivity(intent);
        finish();
    }

    private void Deslogar() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(Perfil.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
