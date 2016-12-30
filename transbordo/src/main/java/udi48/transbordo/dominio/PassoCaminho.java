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
public class PassoCaminho {

    private int i;
    private int j;
    private double valor;

    public PassoCaminho(int i, int j, double valor) {
        this.i = i;
        this.j = j;
        this.valor = valor;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
