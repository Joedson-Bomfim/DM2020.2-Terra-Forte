package com.example.terraforte;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
    TextView edit_nomeUsuario, edit_apelido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_contato);

        IniciarComponentes();
    }

    private void IniciarComponentes() {
        edit_nomeUsuario = findViewById(R.id.idNomeUsuarioPerfilContato);
        edit_apelido = findViewById(R.id.idApelidoPerfilContato);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String teste = getIntent().getStringExtra("tecnicoId");

        db.collection("Usuarios")
                .whereEqualTo("apelido", teste)
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
                                        }
                                    }
                                });
                            }
                        } else {
                            //Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}