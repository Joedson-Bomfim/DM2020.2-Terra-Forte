package com.example.terraforte;

import java.io.Serializable;

public class Usuario {
    private String nome_usuario, apelido;

    public Usuario() {}

    public Usuario(String nome_usuario, String apelido) {
        this.nome_usuario = nome_usuario;
        this.apelido = apelido;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_completo) {
        this.nome_usuario = nome_completo;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }
}