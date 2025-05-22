/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author ivald
 * Aqui ficam dados e toda a transportacao das regras Get e set e dos construtores. 
 * fundamental para o funcionamento da transportação de id, nome, senha e data de cadastro o usuario.
 */


public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha; 
    private LocalDateTime dataCadastro;

    public Usuario() {}

    public Usuario(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataCadastro = LocalDateTime.now();
    }

 public boolean validarSenha(String senhaPlana) {
        return this.senha.equals(senhaPlana);
    }

    public void criptografarSenha() {
        this.senha = "HASH_" + this.senha; 
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(LocalDateTime dataCadastro) { 
        this.dataCadastro = dataCadastro; 
    }
}

