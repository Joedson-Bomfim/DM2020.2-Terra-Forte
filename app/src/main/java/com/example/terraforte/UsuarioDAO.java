package com.example.terraforte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO extends SQLiteOpenHelper {

    private static final String banco_usuario = "bd_usuarios";
    private static final int versao_banco = 1;

    private static final String tabela_usuario = "tb_usuario";
    private static final String coluna_id = "id_usuario";
    private static final String coluna_email = "tb_email";
    private static final String coluna_senha = "tb_senha";
    private static final String coluna_nome_usuario = "tb_nome_usuario";
    private static final String coluna_nome_completo = "tb_nome_completo";
    private static final String coluna_apelido = "tb_apelido";
    private static final String coluna_telefone = "tb_telefone";
    private static final String coluna_endereco = "tb_endereco";
    private static final String coluna_funcao = "tb_funcao";


    public UsuarioDAO(Context context) {
        super(context, banco_usuario, null, versao_banco);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void cadastrar(Usuario usuario) {
        SQLiteDatabase conexao = getWritableDatabase();

        ContentValues dados = new ContentValues();

        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
        dados.put("nomeUsuario", usuario.getNomeUsuario());
        dados.put("nomeCompleto", usuario.getNomeCompleto());
        dados.put("apelido", usuario.getApelido());
        dados.put("telefone", usuario.getTelefone());
        dados.put("endereco", usuario.getEndereco());
        dados.put("funcao", usuario.getFuncao());

        conexao.insertOrThrow("usuario", null, dados);
    }
}
