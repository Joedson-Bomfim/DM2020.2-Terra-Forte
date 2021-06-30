package com.example.terraforte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    private Conexao conexao;
    private SQLiteDatabase banco;

    public UsuarioDAO(Context context) {
        conexao = new Conexao(context);
        banco = conexao.getWritableDatabase();
    }

    public long inserir(Usuario usuario) {
        ContentValues values = new ContentValues();
        values.put("email", usuario.getEmail());
        values.put("senha", usuario.getSenha());
        values.put("nome_usuario", usuario.getNomeUsuario());
        values.put("nome_completo", usuario.getNomeCompleto());
        values.put("apelido", usuario.getApelido());
        values.put("telefone", usuario.getTelefone());
        values.put("endereco", usuario.getEndereco());
        values.put("funcao", usuario.getFuncao());

        return banco.insert("usuario", null, values);
    }

    public List<Usuario> obterTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        Cursor cursor = banco.query("usuario", new String[] {"id", "email", "senha", "nome_usuario", "nome_completo", "apelido", "telefone", "endereco", "funcao"}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Usuario u = new Usuario();
            u.setIdUsuario(cursor.getInt(0));
            u.setEmail(cursor.getString(1));
            u.setSenha(cursor.getString(2));
            u.setNomeUsuario(cursor.getString(3));
            u.setNomeCompleto(cursor.getString(4));
            u.setApelido(cursor.getString(5));
            u.setTelefone(cursor.getString(6));
            u.setEndereco(cursor.getString(7));
            u.setFuncao(cursor.getString(8));
            usuarios.add(u);
        }

        return usuarios;
    }
}
