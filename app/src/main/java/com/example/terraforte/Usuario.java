package com.example.terraforte;

import java.io.Serializable;

public class Usuario {
    private String nome_usuario, apelido, email, id_usuario;

    public Usuario() {}

    public Usuario(String nome_usuario, String apelido, String email, String id_usuario) {
        this.nome_usuario = nome_usuario;
        this.apelido = apelido;
        this.email = email;
        this.id_usuario = id_usuario;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String uid) {
        id_usuario = uid;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}