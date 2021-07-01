package com.example.terraforte;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Lista_Contatos extends AppCompatActivity {
    private ListView listView;
    private UsuarioDAO dao;
    private List<Usuario> usuarios;
    private List<Usuario> usuariosFiltrados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lista_contatos);

        listView = findViewById(R.id.idListaContatos);
        dao = new UsuarioDAO(this);
        usuarios = dao.obterTodos();
        usuariosFiltrados.addAll(usuarios);
        //ArrayAdapter<Usuario> adaptador = new ArrayAdapter<Usuario>(this, android.R.layout.simple_list_item_1, usuariosFiltrados);
        ContatoAdapter adaptador = new ContatoAdapter(this, usuariosFiltrados);
        listView.setAdapter(adaptador);
    }

    @Override
    protected void onResume() {
        super.onResume();
        usuarios = dao.obterTodos();
        usuariosFiltrados.clear();
        usuariosFiltrados.addAll(usuarios);
        listView.invalidateViews();
    }
}
