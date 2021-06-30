package com.example.terraforte;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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
}
