/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author ivald
 * Acesso ao banco de dados dos Artistas e devolve 
 * a informação para os controllers que requisitam pelo o artista, como o HistoricoController
 */

import connection.Conexao;
import model.Artista;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ArtistaDAO {
    
    private Connection conn;
    public ArtistaDAO(Connection conn) {
        this.conn = conn;
    }


   public int inserirArtista(Artista artista) {
    String sql = "INSERT INTO artistas (nome, genero) VALUES (?, ?)";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        stmt.setString(1, artista.getNome());
        stmt.setString(2, artista.getGenero());

        int affectedRows = stmt.executeUpdate();

        if (affectedRows > 0) {
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int idGerado = generatedKeys.getInt(1);
                    artista.setId(idGerado); // opcional
                    return idGerado;
                }
            }
        }
    } catch (SQLException e) {
        System.err.println("Erro ao inserir artista: " + e.getMessage());
    }
    return -1; // -1 indica erro
}

    public Artista buscarPorId(int id) {
        String sql = "SELECT * FROM artistas WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Artista(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("genero")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar artista por ID: " + e.getMessage());
        }
        return null;
    }

    public List<Artista> listarTodos() {
        List<Artista> artistas = new ArrayList<>();
        String sql = "SELECT * FROM artistas ORDER BY nome";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                artistas.add(new Artista(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("genero")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar artistas: " + e.getMessage());
        }
        return artistas;
    }

    public List<Artista> buscarPorNome(String nome) {
        List<Artista> artistas = new ArrayList<>();
        String sql = "SELECT * FROM artistas WHERE nome ILIKE ? ORDER BY nome";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + nome + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                artistas.add(new Artista(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("genero")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar artistas por nome: " + e.getMessage());
        }
        return artistas;
    }
    
    public Artista buscarPorNomeExato(String nome) {
    String sql = "SELECT * FROM artistas WHERE LOWER(nome) = LOWER(?) LIMIT 1";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, nome);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return new Artista(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("genero")
            );
        }
    } catch (SQLException e) {
        System.err.println("Erro ao buscar artista por nome exato: " + e.getMessage());
    }
    return null;
}

    public List<Artista> buscarPorGenero(String genero) {
        List<Artista> artistas = new ArrayList<>();
        String sql = "SELECT * FROM artistas WHERE genero ILIKE ? ORDER BY nome";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + genero + "%");
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                artistas.add(new Artista(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("genero")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar artistas por gênero: " + e.getMessage());
        }
        return artistas;
    }

    public boolean atualizar(Artista artista) {
        String sql = "UPDATE artistas SET nome = ?, genero = ? WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, artista.getNome());
            stmt.setString(2, artista.getGenero());
            stmt.setInt(3, artista.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar artista: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        if (temMusicasAssociadas(id)) {
            System.err.println("Não é possível excluir - artista tem músicas associadas");
            return false;
        }
        
        String sql = "DELETE FROM artistas WHERE id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir artista: " + e.getMessage());
            return false;
        }
    }

    private boolean temMusicasAssociadas(int artistaId) {
        String sql = "SELECT 1 FROM musicas WHERE artista_id = ? LIMIT 1";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, artistaId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar músicas do artista: " + e.getMessage());
            return true; // Em caso de erro, assume que tem para prevenir exclusão
        }
    }

    public List<Artista> listarPopulares(int limite) {
        List<Artista> artistas = new ArrayList<>();
        String sql = """
            SELECT a.id, a.nome, a.genero, COUNT(c.musica_id) AS total_curtidas
            FROM artistas a
            JOIN musicas m ON a.id = m.artista_id
            JOIN curtidas c ON m.id = c.musica_id
            WHERE c.acao = true
            GROUP BY a.id, a.nome, a.genero
            ORDER BY total_curtidas DESC
            LIMIT ?
            """;
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, limite);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                artistas.add(new Artista(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("genero")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar artistas populares: " + e.getMessage());
        }
        return artistas;
    }
}