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
    private String cod;
    private Point2D coordenada;
    private String bloco;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Point2D getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Point2D coordenada) {
        this.coordenada = coordenada;
    }
    
    public String getBloco() {
        return bloco;
    }

    public void setBloco(String bloco) {
        this.bloco = bloco;
    }
}
