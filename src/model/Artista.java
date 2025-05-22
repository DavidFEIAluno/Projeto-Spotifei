/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ivald
 * Aqui ficam dados e toda a transportacao das regras Get e set e dos construtores. 
 * fundamental para o funcionamento onde requisite o get e set de tal model
 */

public class Artista {
    private int id;
    private String nome;
    private String genero;
    
    // Construtores
    public Artista() {}
    
    public Artista(int id, String nome, String genero) {
        this.id = id;
        this.nome = nome;
        this.genero = genero;
    }
    
    // Getters e Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    @Override
    public String toString() {
        return nome + (genero != null && !genero.isEmpty() ? " (" + genero + ")" : "");
    }
}
