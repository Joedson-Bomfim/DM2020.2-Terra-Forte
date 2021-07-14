package com.example.terraforte;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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
    private Button bt_deslogar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        IniciarComponentes();

        bt_deslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Perfil.this, MainActivity.class);
                startActivity(intent);
                finish();
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
    }

    @Override
    protected void onStart() {
        super.onStart();

        String edit_email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

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

    @Override
    protected void onResume() {
        super.onResume();
    }
}
