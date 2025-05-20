/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Usuario;
import dao.UsuarioDAO;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        usuarioDAO = new UsuarioDAO();
    }

    public boolean cadastrarUsuario(String nome, String email, String senha) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            return false;
        }

        Usuario usuario = new Usuario(nome, email, senha);
        return usuarioDAO.inserir(usuario);
    }

    public Usuario login(String email, String senha) {
        if (email.isEmpty() || senha.isEmpty()) {
            return null;
        }

        return usuarioDAO.buscarPorEmailSenha(email, senha);
    }
}

