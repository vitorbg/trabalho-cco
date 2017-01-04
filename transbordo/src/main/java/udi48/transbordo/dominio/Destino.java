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
public class Destino {

    private String nome;
    private double demanda;

    public Destino(String nome, double demanda) {
        this.nome = nome;
        this.demanda = demanda;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDemanda() {
        return demanda;
    }

    public void setDemanda(double demanda) {
        this.demanda = demanda;
    }

}
