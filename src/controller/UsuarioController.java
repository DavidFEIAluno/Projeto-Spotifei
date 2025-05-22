/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Usuario;
import dao.UsuarioDAO;
import java.util.regex.Pattern;

/**
 *
 * @author ivald
 * Reponsavel para as requisicoes do usuario em tudo que envolve
 * o funcionamento do proprio usuario, armazenar informações, login, cadastro etc.
 */

public class UsuarioController {
    private final UsuarioDAO usuarioDAO;
    
    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

   public boolean cadastrarUsuario(String nome, String email, String senha) {
    // Validações
    if (nome == null || nome.trim().isEmpty()) {
        throw new IllegalArgumentException("Nome não pode ser vazio");
    }

    if (email == null || !validarEmail(email)) {
        throw new IllegalArgumentException("Email inválido");
    }

    if (senha == null || senha.length() < 6) {
        throw new IllegalArgumentException("Senha deve ter pelo menos 6 caracteres");
    }

    Usuario usuario = new Usuario(nome.trim(), email.trim(), senha);

    boolean sucesso = usuarioDAO.cadastrarUsuario(usuario);
    System.out.println("Cadastro realizado? " + sucesso);
    return sucesso;
}


    public Usuario login(String email, String senha) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email não pode ser vazio");
        }
        
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser vazia");
        }

        return usuarioDAO.fazerLogin(email.trim(), senha);
    }

    private boolean validarEmail(String email) {
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(regex).matcher(email).matches();
    }
}

