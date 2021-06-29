package com.example.terraforte;

public class Usuario {
    private int idUsuario;
    private String email;
    private String senha;
    private String nomeUsuario;
    private String nomeCompleto;
    private String apelido;
    private String telefone;
    private String endereco;
    private String funcao;

    public Usuario () {

    }

    public Usuario (String _email, String _senha, String _nomeUsuario, String _nomeCompleto, String _apelido, String _telefone, String _endereco, String _funcao){
        this.email = _email;
        this.senha = _senha;
        this.nomeUsuario = _nomeUsuario;
        this.nomeCompleto = _nomeCompleto;
        this.apelido = _apelido;
        this.telefone = _telefone;
        this.endereco = _endereco;
        this.funcao = _funcao;
    }

    public Usuario (int _idUsuario, String _email, String _senha, String _nomeUsuario, String _nomeCompleto, String _apelido, String _telefone, String _endereco, String _funcao){
        this.idUsuario = _idUsuario;
        this.email = _email;
        this.senha = _senha;
        this.nomeUsuario = _nomeUsuario;
        this.nomeCompleto = _nomeCompleto;
        this.apelido = _apelido;
        this.telefone = _telefone;
        this.endereco = _endereco;
        this.funcao = _funcao;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getFuncao() {
        return endereco;
    }

    public void setFuncao(String telefone) {
        this.funcao = funcao;
    }
}