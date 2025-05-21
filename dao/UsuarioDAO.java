/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import connection.Conexao;
import model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    public Usuario fazerLogin(String email, String senha) {
    String sql = "SELECT id, nome, email, senha, data_cadastro FROM usuarios WHERE email = ?";
    
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, email);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Usuario usuario = new Usuario(
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha")
                );
                usuario.setId(rs.getInt("id")); // DEFININDO O ID CORRETAMENTE
                usuario.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
                
                if (usuario.validarSenha(senha)) {
                    return usuario;
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao fazer login no banco de dados: " + e.getMessage());
        e.printStackTrace();
    }
    
    return null;
}
    
    // Método para cadastrar usuário (com segurança)
    public boolean cadastrarUsuario(Usuario usuario) {
        System.out.println("Data de cadastro: " + usuario.getDataCadastro());
        String sql = "INSERT INTO usuarios (nome, email, senha, data_cadastro) " +
                     "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            System.out.println("Conectado ao banco: " + conn.getCatalog());

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setTimestamp(4, Timestamp.valueOf(usuario.getDataCadastro()));            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    usuario.setId(rs.getInt(1));
                }
            }
                Conexao.commit(); // <-- COMMIT AQUI
                return true;
    }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            e.printStackTrace();
            Conexao.rollback();
        }
        return false;
    }

    // Método para autenticação
    public Usuario autenticar(String email, String senha) {
    String sql = "SELECT id, nome, email, senha, data_cadastro FROM usuarios WHERE email = ?";
    
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, email);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id")); // DEFININDO O ID
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());

                if (usuario.validarSenha(senha)) {
                    return usuario;
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao autenticar usuário: " + e.getMessage());
    }
    return null;
}

    // Métodos adicionais essenciais
    public boolean atualizarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET nome = ?, email = ?, ativo = ? WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setInt(3, usuario.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar usuário: " + e.getMessage());
            Conexao.rollback();
        }
        return false;
    }

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT id, nome, email, data_cadastro, ativo FROM usuarios";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setEmail(rs.getString("email"));
                usuario.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());                
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }
        return usuarios;
    }
}

