package com.example.terraforte;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContatoAdapter extends BaseAdapter {
    private List<Usuario> usuarios;
    private Activity activity;

    public ContatoAdapter(Activity activity, List<Usuario> usuarios) {
        this.activity = activity;
        this.usuarios = usuarios;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int i) {
        return usuarios.get(i);
    }

    @Override
    public long getItemId(int i) {
        return usuarios.get(i).getIdUsuario();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = activity.getLayoutInflater().inflate(R.layout.item, viewGroup, false);
        TextView nome_completo = v.findViewById(R.id.idNomeCompletoLista);
        TextView apelido = v.findViewById(R.id.idApelidoLista);
        TextView funcao = v.findViewById(R.id.idFuncaoLista);
        Usuario u = usuarios.get(i);
        nome_completo.setText(u.getNomeCompleto());
        apelido.setText(u.getApelido());
        funcao.setText(u.getTelefone());

        return v;
    }
}
