package com.example.terraforte;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Home extends AppCompatActivity {

    TextView nomeUsuario;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        IniciarComponentes();

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void IniciarComponentes() {
        nomeUsuario = findViewById(R.id.id_nomeUsuario_Home);
    }

    public void acessarPerfil(View view) {
        Intent intent = new Intent(this, Perfil.class);
        startActivity(intent);
    }

    public void acessarContatos(View view) {
        Intent intent = new Intent(this, Lista_AddContato.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null) {
                    nomeUsuario.setText(documentSnapshot.getString("nome_usuario"));
                }
            }
        });
    }
}
