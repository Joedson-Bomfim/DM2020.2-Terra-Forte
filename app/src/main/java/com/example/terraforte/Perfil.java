package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Perfil extends AppCompatActivity {
    private ListView lstUsuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        lstUsuarios = findViewById(R.id.lstUsuariosId);

        carregarUsuarios();

        lstUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Intents
                Toast.makeText(Perfil.this, "posição: " + position, Toast.LENGTH_LONG).show();

            }
        });
    }

    private void carregarUsuarios() {
        UsuarioDAO usuarioDAO = new UsuarioDAO(this);

        List<Usuario> usuarios = usuarioDAO.buscarTodos();

        ArrayAdapter<Usuario> adpUsuarios = new ArrayAdapter<Usuario>(getApplicationContext(), android.R.layout.simple_list_item_1, usuarios);

        lstUsuarios.setAdapter(adpUsuarios);

        usuarioDAO.close();
   }

    @Override
    protected void onResume() {
        super.onResume();

        carregarUsuarios();
    }
}
