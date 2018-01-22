/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aglfx.model.bean;

import javafx.geometry.Point2D;

/**
 *
 * @author Usuário
 */
public class Pallet {
    
    private int id;
    private String nome;
    private Point2D coordenada;

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

    public Point2D getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Point2D coordenada) {
        this.coordenada = coordenada;
    }
    
    
    
    
}
