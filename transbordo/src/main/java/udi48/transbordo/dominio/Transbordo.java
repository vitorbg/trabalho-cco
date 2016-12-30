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
public class Transbordo {

    private String nome;
    private double custos[];

    public Transbordo(String nome, double[] custos) {
        this.nome = nome;
        this.custos = custos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getCusto(int posicao) {
        return custos[posicao];
    }

    public double[] getCustos() {
        return custos;
    }

    public void setCustos(double[] custos) {
        this.custos = custos;
    }

}
