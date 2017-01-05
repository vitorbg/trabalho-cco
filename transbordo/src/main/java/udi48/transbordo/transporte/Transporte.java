/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udi48.transbordo.transporte;

import java.util.ArrayList;
import java.util.List;
import udi48.transbordo.dominio.Destino;
import udi48.transbordo.dominio.Origem;
import udi48.transbordo.dominio.PassoCaminho;
import udi48.transbordo.dominio.Transbordo;

/**
 *
 * @author vitor
 */
public class Transporte {

    private List<Origem> origem;
    private List<Destino> destino;
    private List<Transbordo> transbordo;
    private int linhas;
    private int colunas;
    private int tamanhoVetorTransbordo;
    private int quantidadeTransbordo;
    private int quantidadeOrigem;
    private int quantidadeDestino;
    private boolean existeOrigemArtificial;
    private boolean existeDestinoArtificial;
    private double bigM = 999999;
    private double ofertaTotal = 0;
    private double demandaTotal = 0;
    private double matrizCusto[][];
    private double matrizSolucao[][];
    private double vetorU[];
    private double vetorV[];
    private boolean vetorUVisitado[];
    private boolean vetorVVisitado[];
    private boolean matrizVisitado[][];
    private double vetorOferta[];
    private double vetorDemanda[];
    private ArrayList<PassoCaminho> caminho;
    private PassoCaminho novaVariavelBasica;

    public Transporte(List<Origem> origem, List<Destino> destino, List<Transbordo> transbordo) {
        this.origem = origem;
        this.destino = destino;
        this.transbordo = transbordo;
    }

    private void balanceaSistema() {
//Verifica as demandas e capacidades totais para balancear o sistema -----------
        ofertaTotal = 0;
        demandaTotal = 0;
        existeOrigemArtificial = false;
        existeDestinoArtificial = false;

        int c;
        int k;
        int ajuste;

        for (c = 0; c < origem.size(); c++) {
            ofertaTotal = ofertaTotal + origem.get(c).getOferta();
        }
        for (c = 0; c < destino.size(); c++) {
            demandaTotal = demandaTotal + destino.get(c).getDemanda();
        }

        System.out.println("DEMANDA TOTAL INICIAL: " + ofertaTotal);
        System.out.println("CAPACIDADE TOTAL INICIAL: " + demandaTotal);

        ajuste = 0;
        if (ofertaTotal != demandaTotal) {
            if (ofertaTotal < demandaTotal) {
                Origem origemArtificial = new Origem("Origem Artifical", demandaTotal - ofertaTotal);
                this.origem.add(origemArtificial);

                for (c = 0; c < transbordo.size(); c++) {
                    double[] custos = transbordo.get(c).getCustos();
                    tamanhoVetorTransbordo = custos.length + 1;
                    double[] novoCustos = new double[tamanhoVetorTransbordo];

                    for (k = 0; k < tamanhoVetorTransbordo; k++) {

                        if (k < (origem.size() - 1)) {
                            novoCustos[k] = custos[k];
                        }
                        if (k == (origem.size() - 1)) {
                            novoCustos[k] = 0;
                            System.out.println("k == origem!! k=" + k);

                        }
                        if (k > (origem.size() - 1)) {
                            novoCustos[k] = custos[k - 1];
                        }
                    }
                    this.transbordo.get(c).setCustos(novoCustos);
                }
                existeOrigemArtificial = true;
            } else {
                Destino destinoArtificial = new Destino("Destino Artifical", ofertaTotal - demandaTotal);
                this.destino.add(destinoArtificial);
                for (c = 0; c < transbordo.size(); c++) {
                    double[] custos = transbordo.get(c).getCustos();
                    tamanhoVetorTransbordo = custos.length + 1;
                    double[] novoCustos = new double[tamanhoVetorTransbordo];

                    for (k = 0; k < tamanhoVetorTransbordo; k++) {
                        if (k == (tamanhoVetorTransbordo - 1)) {
                            novoCustos[k] = 0;
                        } else {
                            novoCustos[k] = custos[k];
                        }
                    }
                    this.transbordo.get(c).setCustos(novoCustos);
                }
                existeDestinoArtificial = true;
            }
        }

        mostraEntrada();

        ofertaTotal = 0;
        demandaTotal = 0;

        for (c = 0; c < origem.size(); c++) {
            ofertaTotal = ofertaTotal + origem.get(c).getOferta();
        }
        for (c = 0; c < destino.size(); c++) {
            demandaTotal = demandaTotal + destino.get(c).getDemanda();
        }
    }

    public void montraQuadroCustos() {

        balanceaSistema();
//Inicializa as variaveis de controle da matriz --------------------------------        

        linhas = origem.size() + transbordo.size();
        colunas = transbordo.size() + destino.size();
        quantidadeTransbordo = transbordo.size();
        quantidadeOrigem = origem.size();
        quantidadeDestino = destino.size();

        matrizCusto = new double[linhas][colunas];
        matrizSolucao = new double[linhas][colunas];
        matrizVisitado = new boolean[linhas][colunas];
        vetorU = new double[linhas];
        vetorV = new double[colunas];
        vetorOferta = new double[linhas];
        vetorDemanda = new double[colunas];
        vetorU = new double[linhas];
        vetorV = new double[colunas];
        vetorUVisitado = new boolean[linhas];
        vetorVVisitado = new boolean[colunas];

        System.out.println("LINHAS: " + linhas);
        System.out.println("COLUNAS: " + colunas);
        System.out.println("QUANTIDADE ORIGEM: " + quantidadeOrigem);
        System.out.println("QUANTIDADE TRANSBORDO: " + quantidadeTransbordo);
        System.out.println("QUANTIDADE DESTINO: " + quantidadeDestino);
        System.out.println("Oferta TOTAL: " + ofertaTotal);
        System.out.println("Demanda TOTAL: " + demandaTotal);

        int tranbordoAtual;
        int destinoAtual;
        int posicaoAtual;
        int origemAtual;
        int linha;
        int coluna;
//Monta custos das colunas de Transbordo ---------------------------------------
        tranbordoAtual = 0;
        for (coluna = 0; coluna < quantidadeTransbordo; coluna++) {

            double v[] = transbordo.get(tranbordoAtual).getCustos();

            for (linha = 0; linha < (linhas); linha++) {
                matrizCusto[linha][coluna] = v[linha];
            }

            vetorDemanda[coluna] = ofertaTotal;
            tranbordoAtual++;
        }

//Monta custos das colunas de Demanda ------------------------------------------
        tranbordoAtual = 0;
        destinoAtual = 0;
        int indicador = 0;

        for (coluna = quantidadeTransbordo; coluna < (quantidadeDestino + quantidadeTransbordo); coluna++) {
            double v[];
            if (existeDestinoArtificial && tranbordoAtual == (quantidadeTransbordo)) {
                v = new double[tamanhoVetorTransbordo];
                for (int i = 0; i < tamanhoVetorTransbordo; i++) {
                    v[i] = 0;
                }
            } else {
                v = transbordo.get(tranbordoAtual).getCustos();
                indicador = 0;
            }
            for (linha = 0; linha < (linhas); linha++) {
                if (linha < quantidadeOrigem) {
                    matrizCusto[linha][coluna] = bigM;
                } else {
                    int pos = (quantidadeOrigem + quantidadeTransbordo) + indicador;
                    int qtdElementos = quantidadeDestino + quantidadeOrigem + quantidadeTransbordo;

                    if (pos != qtdElementos) {
                        matrizCusto[linha][coluna] = v[pos];
                        indicador++;
                    }
                }
            }

            vetorDemanda[coluna] = destino.get(destinoAtual).getDemanda();
            destinoAtual++;
            tranbordoAtual++;
        }

//Monta custos da ultima coluna de capacidade
        origemAtual = 0;
        for (linha = 0; linha < (linhas); linha++) {
            if (linha < quantidadeOrigem) {
                vetorOferta[linha] = origem.get(origemAtual).getOferta();
            } else {
                vetorOferta[linha] = ofertaTotal;
            }
            origemAtual++;
        }
//        montaExemplo1();
        calculaSBFInicialCantroNoroeste();

        calculaOtimalidade();
        boolean otimalidade = false;
        while (otimalidade == false) {
            otimalidade = calculaOtimalidade();
        }
        mostraMatrizSolucao();
        mostraVetoresUJ();
        calculaSolucao();

    }

    private void calculaSBFInicialCantroNoroeste() {
        int i;
        int j;
        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
                matrizSolucao[i][j] = 0;
            }
        }
        mostraMatrizSolucao();
        mostraMatrizCusto();
        mostraVetorOferta();
        mostraVetorDemanda();
        int k;
        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
//                System.out.println("\n--------------------------------------------------");
//                mostraMatrizSolucao();
//                mostraVetorOferta();
//                mostraVetorDemanda();
//                System.out.println("\n--------------------------------------------------");
                if (matrizSolucao[i][j] != -1) {

                    if (vetorDemanda[j] < vetorOferta[i]) {

                        matrizSolucao[i][j] = vetorDemanda[j];

                        vetorOferta[i] = vetorOferta[i] - vetorDemanda[j];

                        vetorDemanda[j] = 0;

                        for (k = i + 1; k < linhas; k++) {
                            matrizSolucao[k][j] = -1;
                        }

                    } else {
                        matrizSolucao[i][j] = vetorOferta[i];

                        vetorDemanda[j] = vetorDemanda[j] - vetorOferta[i];

                        vetorOferta[i] = 0;

                        for (k = j + 1; k < colunas; k++) {
                            matrizSolucao[i][k] = -1;
                        }
                    }
                }
//                System.out.println("\n--------------------------------------------------");
//                mostraMatrizSolucao();
//                mostraVetorOferta();
//                mostraVetorDemanda();
//                System.out.println("\n--------------------------------------------------");
            }

        }
        System.out.println("\n--------------------------------------------------");
        mostraMatrizSolucao();
        mostraVetorOferta();
        mostraVetorDemanda();
        System.out.println("\n--------------------------------------------------");

    }

    private void calculaSolucao() {
        int i;
        int j;

        System.out.println("  ");
//        System.out.println("Matriz de Solução");

        double resultado = 0;
        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {

                if (matrizSolucao[i][j] != -1) {
                    resultado = resultado + (matrizSolucao[i][j] * matrizCusto[i][j]);
                }
            }
            System.out.println("\n Res===== " + resultado);
        }
    }

    public boolean calculaOtimalidade() {

        recarregaEstruturasAuxiliaresOtimalidade();
        calculaVetoresUV();
        boolean encontraMenorElemento = encontraMenorElemento();
        if (encontraMenorElemento == false) {
            return true;
        }

        System.out.println("TEM VARIAVEL NAO BASICA NEGATIVA: " + encontraMenorElemento);

        encontraCiclo();

        PassoCaminho indiceVariavelNegativa = caminho.get(1);

        for (int i = 1; i < caminho.size(); i++) {
            if (i % 2 != 0) {
                PassoCaminho aux = caminho.get(i);
                if (aux.getValor() < indiceVariavelNegativa.getValor()) {
                    indiceVariavelNegativa = aux;
                }
            }
        }

        System.out.println("VARIAVEL A SAIR: " + indiceVariavelNegativa.getI() + "    " + indiceVariavelNegativa.getJ() + "        " + indiceVariavelNegativa.getValor());

        matrizSolucao[novaVariavelBasica.getI()][novaVariavelBasica.getJ()] = indiceVariavelNegativa.getValor();

        boolean subtrai = true;
        int qtdElementosCaminho = caminho.size();

        for (int i = 1; i < qtdElementosCaminho; i++) {

            PassoCaminho aux = caminho.get((caminho.size() - 1));
            if (subtrai) {
                System.out.println("SUB          " + aux.getI() + "       " + aux.getJ() + "     " + matrizSolucao[aux.getI()][aux.getJ()]);
                matrizSolucao[aux.getI()][aux.getJ()] = matrizSolucao[aux.getI()][aux.getJ()] - indiceVariavelNegativa.getValor();

                subtrai = false;
            } else {
                System.out.println(" SOM         " + aux.getI() + "       " + aux.getJ() + "     " + matrizSolucao[aux.getI()][aux.getJ()]);
                matrizSolucao[aux.getI()][aux.getJ()] = matrizSolucao[aux.getI()][aux.getJ()] + indiceVariavelNegativa.getValor();
                subtrai = true;
            }
            caminho.remove((caminho.size() - 1));
        }

        mostraMatrizSolucao();
        return false;
    }

    private boolean encontraMenorElemento() {
        int i;
        int j;
        int iMenorElemento = 0;
        int jMenorElemento = 0;

        double resultado = 0;

        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
                if (matrizSolucao[i][j] == -1) {
                    double resultadoAuxiliar = matrizCusto[i][j] - (vetorU[i] + vetorV[j]);
                    System.out.println(" " + i + " " + j + " = " + resultadoAuxiliar);

                    if (resultadoAuxiliar < resultado) {
                        resultado = resultadoAuxiliar;
                        iMenorElemento = i;
                        jMenorElemento = j;

                    }
                }
            }
        }
        PassoCaminho passoInicial = new PassoCaminho(iMenorElemento, jMenorElemento, resultado);
        novaVariavelBasica = passoInicial;

        if (resultado >= 0) {
            return false;
        } else {
            return true;
        }

    }

    private void encontraCiclo() {
        int i;
        int j;
//        int iMenorElemento = 0;
//        int jMenorElemento = 0;
//
//        double resultado = 0;
//
//        for (i = 0; i < linhas; i++) {
//            for (j = 0; j < colunas; j++) {
//                if (matrizSolucao[i][j] == -1) {
//                    double resultadoAuxiliar = matrizCusto[i][j] - (vetorU[i] + vetorV[j]);
//                    System.out.println(" " + i + " " + j + " = " + resultadoAuxiliar);
//
//                    if (resultadoAuxiliar < resultado) {
//                        resultado = resultadoAuxiliar;
//                        iMenorElemento = i;
//                        jMenorElemento = j;
//
//                    }
//                }
//            }
//        }
//        System.out.println("RESS: i= " + iMenorElemento + "  j= " + jMenorElemento + "    VALOR: " + resultado);

        caminho = new ArrayList<>();

        PassoCaminho passoInicial = novaVariavelBasica;

        caminho.add(passoInicial);

        matrizSolucao[novaVariavelBasica.getI()][novaVariavelBasica.getJ()] = novaVariavelBasica.getValor();

        boolean aindaTemCaminho = true;
        int linha;
        int coluna;
        int c = 0;
        int proximaLinha = passoInicial.getI();//INICIAL
        matrizVisitado[passoInicial.getI()][passoInicial.getJ()] = true;

        PassoCaminho procuraLinha = null;
        PassoCaminho procuraColuna = null;

        while (aindaTemCaminho) {
            procuraLinha = verificaElementoLinha(proximaLinha);

            if (procuraLinha != null) {
                matrizVisitado[procuraLinha.getI()][procuraLinha.getJ()] = true;
                caminho.add(procuraLinha);

                if (procuraLinha.getI() == passoInicial.getI() && procuraLinha.getJ() == passoInicial.getJ()) {
                    aindaTemCaminho = false;
                } else {
                    procuraColuna = verificaElementoColuna(procuraLinha.getJ());
                    if (procuraColuna != null) {
                        matrizVisitado[procuraColuna.getI()][procuraColuna.getJ()] = true;
                        caminho.add(procuraColuna);

                        if (procuraColuna.getI() == passoInicial.getI() && procuraColuna.getJ() == passoInicial.getJ()) {
                            aindaTemCaminho = false;
                        } else {
//                            caminho.add(procuraLinha);
//                            caminho.add(procuraColuna);
                        }
                        proximaLinha = procuraColuna.getI();
                    } else {
                        aindaTemCaminho = false;
                    }
                    mostraMatrizVisitado();
                }
            } else {
                aindaTemCaminho = false;
            }
            mostraMatrizVisitado();

            mostraCaminho(caminho);

        }
    }

    private void mostraCaminho(ArrayList<PassoCaminho> caminho) {
        System.out.println("CAMINHO");
        for (int i = 0; i < caminho.size(); i++) {
            System.out.println("Valor: " + caminho.get(i).getValor() + " i= " + caminho.get(i).getI() + " j= " + caminho.get(i).getJ());
        }
    }

    private PassoCaminho verificaElementoLinha(int linha) {
        PassoCaminho p = null;
        ArrayList<PassoCaminho> caminhosLinha = new ArrayList<>();
        System.out.println("VERIFICA ELEMENTO LINHA LINHA LINHA = " + linha);

        for (int j = 0; j < colunas; j++) {
            if (matrizSolucao[linha][j] != -1 && matrizVisitado[linha][j] == false) {
                p = new PassoCaminho(linha, j, matrizSolucao[linha][j]);
                System.out.println("VERIFICA ELEMENTO LINHA = " + p.getI() + "   " + p.getJ());

                caminhosLinha.add(p);
            }
        }
        if (caminhosLinha.size() > 1) {
            p = caminhosLinha.get(0);
            for (int j = 0; j < caminhosLinha.size(); j++) {
                PassoCaminho c = caminhosLinha.get(j);
                if (c.getValor() <= p.getValor()) {
                    p = c;
                }
            }
        } else {
            if (caminhosLinha.isEmpty()) {
                p = null;
            } else {
                p = caminhosLinha.get(0);
            }
        }

//        System.out.println("VERIFICA ELEMENTO LINHA = " + p.getI() + "   " + p.getJ()
//        );
        return p;
    }

    private PassoCaminho verificaElementoColuna(int coluna) {
        PassoCaminho p = null;
        ArrayList<PassoCaminho> caminhosColuna = new ArrayList<>();

        for (int i = 0; i < linhas; i++) {
            if (matrizSolucao[i][coluna] != -1 && matrizVisitado[i][coluna] == false) {
                p = new PassoCaminho(i, coluna, matrizSolucao[i][coluna]);
                caminhosColuna.add(p);
            }
        }

        if (caminhosColuna.size() > 1) {
            p = caminhosColuna.get(0);
            for (int j = 0; j < caminhosColuna.size(); j++) {
                PassoCaminho c = caminhosColuna.get(j);

                if (c.getValor() <= p.getValor()) {
                    p = c;
                }
            }
        } else {
            if (caminhosColuna.isEmpty()) {
                p = null;
            } else {
                p = caminhosColuna.get(0);
            }
        }

        return p;
    }

    private void calculaVetoresUV() {
        int i;
        int j;

        int posicaoU = 0;
        int posicaoJ = 0;
        int posicaoVetor = 0;

        vetorU[0] = 0;
        vetorUVisitado[0] = true;
        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
                if (matrizSolucao[i][j] == -1) {
                } else {
                    System.out.println(" MATRIZ SOLUCAO " + i + " " + j);
                    if (vetorVVisitado[j] == false) {
                        vetorV[j] = matrizCusto[i][j] - vetorU[i];
                        vetorVVisitado[j] = true;
                        System.out.println(" VETOR V ");
                        System.out.print("vetorV[" + j + "] = " + vetorV[j]);
                        System.out.print("   -> matrizCusto[" + i + "][" + j + "] = " + matrizCusto[i][j]);
                        System.out.print("   vetorU[" + i + "] = " + vetorU[i]);
                        System.out.println("  ");

                    }
                    if (vetorUVisitado[i] == false) {
                        vetorUVisitado[i] = true;
//                        vetorU[i] = matrizCusto[i][j] - vetorV[i];
                        vetorU[i] = matrizCusto[i][j] - vetorV[j];
                        System.out.println(" VETOR U  ");
                        System.out.print("vetorU[" + i + "] = " + vetorU[i]);
                        System.out.print("   -> matrizCusto[" + i + "][" + j + "] = " + matrizCusto[i][j]);
                        System.out.print("   vetorV[" + j + "] = " + vetorV[j]);
                        System.out.println("  ");
                    }

                    System.out.println("          ");

                }
            }
        }
    }

    public void mostraMatrizVisitado() {
        int i;
        int j;

        System.out.println("  ");
        System.out.println("Matriz de Visitado");

        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
                System.out.print("           " + matrizVisitado[i][j]);
            }
            System.out.println("\n");
        }
    }

    public void mostraMatrizCusto() {
        int i;
        int j;

        System.out.println("  ");
        System.out.println("Matriz de Custos");

        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
                System.out.print("           " + matrizCusto[i][j]);
            }
            System.out.println("\n");
        }
    }

    public void mostraMatrizSolucao() {
        int i;
        int j;

        System.out.println("  ");
        System.out.println("Matriz de Solução");

        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
                System.out.print("           " + matrizSolucao[i][j]);
            }
            System.out.println("\n");
        }
    }

    public void mostraVetorOferta() {
        int i;

        System.out.println("  ");
        System.out.println("Vetor Oferta " + vetorOferta.length);

        for (i = 0; i < vetorOferta.length; i++) {
            System.out.print("  " + vetorOferta[i]);
        }
    }

    public void mostraVetorDemanda() {
        int i;

        System.out.println("  ");
        System.out.println("Vetor Demanda " + vetorDemanda.length);

        for (i = 0; i < vetorDemanda.length; i++) {
            System.out.print("  " + vetorDemanda[i]);
        }
    }

    public void mostraVetoresUJ() {
        int i;

        System.out.println("  ");
        System.out.println("Vetor U " + vetorU.length);

        for (i = 0; i < vetorU.length; i++) {
            System.out.print("  " + vetorU[i]);
        }
        System.out.println("  ");
        System.out.println("Vetor V " + vetorV.length);

        for (i = 0; i < vetorV.length; i++) {
            System.out.print("  " + vetorV[i]);
        }
    }

    public void mostraEntrada() {
        int i;
        int j;
        for (i = 0; i < transbordo.size(); i++) {
            double[] custos = transbordo.get(i).getCustos();
            System.out.println("Transbordo: " + i);
            System.out.println(" ");
            for (j = 0; j < transbordo.get(i).getCustos().length; j++) {
                System.out.print("  " + custos[j]);
            }
            System.out.println(" ");
        }
    }

    private void recarregaEstruturasAuxiliaresOtimalidade() {
        for (int i = 0; i < linhas; i++) {
            vetorU[i] = 0;
            vetorUVisitado[i] = false;
        }
        for (int i = 0; i < colunas; i++) {
            vetorV[i] = 0;
            vetorVVisitado[i] = false;
        }
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matrizVisitado[i][j] = false;
            }
        }
    }

    public void montaExemplo1() {
        linhas = 3;
        colunas = 4;

        matrizCusto = new double[linhas][colunas];
        matrizSolucao = new double[linhas][colunas];
        matrizVisitado = new boolean[linhas][colunas];
        vetorU = new double[linhas];
        vetorV = new double[colunas];
        vetorOferta = new double[linhas];
        vetorDemanda = new double[colunas];
        vetorU = new double[linhas];
        vetorV = new double[colunas];
        vetorUVisitado = new boolean[linhas];
        vetorVVisitado = new boolean[colunas];

        vetorOferta[0] = 6;
        vetorOferta[1] = 8;
        vetorOferta[2] = 10;
        vetorDemanda[0] = 4;
        vetorDemanda[1] = 7;
        vetorDemanda[2] = 6;
        vetorDemanda[3] = 7;
        //
        matrizSolucao[0][0] = 4;
        matrizSolucao[0][1] = 2;
        matrizSolucao[0][2] = -1;
        matrizSolucao[0][3] = -1;
        //
        matrizSolucao[1][0] = -1;
        matrizSolucao[1][1] = 5;
        matrizSolucao[1][2] = 3;
        matrizSolucao[1][3] = -1;
        //
        matrizSolucao[2][0] = -1;
        matrizSolucao[2][1] = -1;
        matrizSolucao[2][2] = 3;
        matrizSolucao[2][3] = 7;
        //
        matrizCusto[0][0] = 1;
        matrizCusto[0][1] = 2;
        matrizCusto[0][2] = 3;
        matrizCusto[0][3] = 4;
        //    
        matrizCusto[1][0] = 4;
        matrizCusto[1][1] = 3;
        matrizCusto[1][2] = 2;
        matrizCusto[1][3] = 4;
        //
        matrizCusto[2][0] = 0;
        matrizCusto[2][1] = 2;
        matrizCusto[2][2] = 2;
        matrizCusto[2][3] = 1;
    }
}
