package com.example.terraforte;

import java.io.Serializable;

public class Usuario {
    private String nome_usuario, apelido, email, usuarioId;

    public Usuario() {}

    public Usuario(String nome_usuario, String apelido, String email, String usuarioId) {
        this.nome_usuario = nome_usuario;
        this.apelido = apelido;
        this.email = email;
        this.usuarioId = usuarioId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String uid) {
        usuarioId = uid;
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