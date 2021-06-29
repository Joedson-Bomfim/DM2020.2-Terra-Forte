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
        String query_coluna = "create table " + tabela_usuario + "("
                + coluna_id + " interger primary key, "
                + coluna_email + " text, " + coluna_senha + " text, "
                + coluna_nome_completo + " text, " + coluna_nome_usuario + " text, "
                + coluna_apelido + " text, " + coluna_telefone + " text, "
                + coluna_endereco + " text, " + coluna_funcao + " text)";

        db.execSQL(query_coluna);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*CRUD abaixo*/
     void addUsuario(Usuario usuario) {
         SQLiteDatabase db = this.getWritableDatabase();

         ContentValues values = new ContentValues();

         values.put(coluna_email, usuario.getEmail());
         values.put(coluna_senha, usuario.getSenha());
         values.put(coluna_nome_completo, usuario.getNomeCompleto());
         values.put(coluna_nome_usuario, usuario.getNomeUsuario());
         values.put(coluna_apelido, usuario.getApelido());
         values.put(coluna_telefone, usuario.getTelefone());
         values.put(coluna_endereco, usuario.getEndereco());
         values.put(coluna_funcao, usuario.getFuncao());

        db.insert(tabela_usuario, null, values);
        db.close();
     }
}
