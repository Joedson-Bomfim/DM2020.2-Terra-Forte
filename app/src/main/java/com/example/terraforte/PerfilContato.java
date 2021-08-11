package com.example.terraforte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class PerfilContato extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView edit_nomeUsuario, edit_apelido, edit_email, edit_telefone, edit_endereco, edit_nomeCompleto, funcao;
    Button conversar;
    String id_contato, recebeEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_contato);

        IniciarComponentes();

        conversar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilContato.this, ChatActivity.class);
                intent.putExtra("idContato", id_contato);
                startActivity(intent);
            }
        });
    }

    private void IniciarComponentes() {
        edit_nomeUsuario = findViewById(R.id.idNomeUsuarioPerfilContato);
        edit_apelido = findViewById(R.id.idApelidoPerfilContato);
        edit_email = findViewById(R.id.idEmailPerfilContato);
        conversar = findViewById(R.id.btn_conversarPerfilContato);
        edit_telefone = findViewById(R.id.idTelefonePerfilContato);
        edit_endereco = findViewById(R.id.idEnderecoPerfilContato);
        edit_nomeCompleto = findViewById(R.id.idNomeCompletoPerfilContato);
        funcao = findViewById(R.id.idFuncaoPerfilContato);
    }

    @Override
    protected void onStart() {
        super.onStart();

        id_contato = getIntent().getStringExtra("IdContato");

        db.collection("Usuarios")
                .whereEqualTo("id_usuario", id_contato)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DocumentReference documentReference = db.collection("Usuarios").document(document.getId());
                                documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                                        if(documentSnapshot != null) {
                                            edit_nomeUsuario.setText(documentSnapshot.getString("nome_usuario"));
                                            edit_apelido.setText(documentSnapshot.getString("apelido"));
                                            edit_email.setText(documentSnapshot.getString("email"));
                                            edit_telefone.setText(documentSnapshot.getString("telefone"));
                                            edit_endereco.setText(documentSnapshot.getString("endereco"));
                                            edit_nomeCompleto.setText(documentSnapshot.getString("nome_completo"));
                                            funcao.setText(documentSnapshot.getString("funcao"));
                                        }
                                    }
                                });

                                id_contato = document.getId();
                            }
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}