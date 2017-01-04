/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udi48.transbordo.dominio;

/**
 *
 * @author vitor
 */
public class Origem {

    private String nome;
    private double oferta;

    public Origem(String nome, double oferta) {
        this.nome = nome;
        this.oferta = oferta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getOferta() {
        return oferta;
    }

    public void setOferta(double oferta) {
        this.oferta = oferta;
    }

}
