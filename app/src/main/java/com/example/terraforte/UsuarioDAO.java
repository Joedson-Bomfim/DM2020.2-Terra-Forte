package com.example.terraforte;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UsuarioDAO extends SQLiteOpenHelper {

    public UsuarioDAO(Context context) {
        super(context, "Agenda", null, 1);
    }

    //public UsuarioDAO(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
    //    super(context, name, factory, version);
    //}



    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS usuario (" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "senha TEXT NOT NULL, " +
                "nomeusuario TEXT NOT NULL, " +
                "nomecompleto TEXT NOT NULL, " +
                "apelido TEXT, " +
                "telefone TEXT NOT NULL, " +
                "endereco TEXT NOT NULL, " +
                "funcao TEXT NOT NULL); ";
        db.execSQL(sql);
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

        conexao.insertOrThrow("contato", null, dados);
    }
}
