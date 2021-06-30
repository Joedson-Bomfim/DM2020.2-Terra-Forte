package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Perfil extends AppCompatActivity {

    private ListView listView;
    private UsuarioDAO dao;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil);

        listView = findViewById(R.id.idListaUsuarios);
        dao = new UsuarioDAO(this);
        usuarios = dao.obterTodos();
        usuariosFiltrados.addAll(usuarios);
        ArrayAdapter<Usuario> adaptador = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, usuarios);
        listView.setAdapter(adaptador);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
