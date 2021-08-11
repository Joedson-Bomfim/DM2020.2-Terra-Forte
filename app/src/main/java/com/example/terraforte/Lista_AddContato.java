package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Lista_AddContato extends AppCompatActivity {
    private RecyclerView places_list;
    private FirebaseFirestore db;
    ListaAddContatoAdapter adapter;
    ArrayList<Usuario> datalist;

    String IdCurrentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_add_contato);

        db = FirebaseFirestore.getInstance();
        places_list = findViewById(R.id.rv_listaAddContato);
        places_list.setLayoutManager(new LinearLayoutManager(this));
        datalist = new ArrayList<>();
        adapter = new ListaAddContatoAdapter(datalist);
        places_list.setAdapter(adapter);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando contatos...");
        progressDialog.show();

        db.collection("Usuarios").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d:list){
                            if(!d.getId().equals(IdCurrentUser)) {
                                Usuario obj = d.toObject(Usuario.class);
                                datalist.add(obj);
                            }
                        }

                        adapter.notifyDataSetChanged();

                        progressDialog.dismiss();
                    }
                });


    }
}