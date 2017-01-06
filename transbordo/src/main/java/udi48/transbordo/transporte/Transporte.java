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
    private final double bigM = 999999;
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
    private double resultadoZ;
    private ArrayList<PassoCaminho> caminho;
    private PassoCaminho novaVariavelBasica;

    public Transporte(List<Origem> origem, List<Destino> destino, List<Transbordo> transbordo) {
        this.origem = origem;
        this.destino = destino;
        this.transbordo = transbordo;
    }

    public void calculaSolucaoBasicaFinal() {
        int contadorPassos = 0;
        System.out.println("---------------------------------------------------");
        System.out.println("Verificação do Balanceamento do Sistema");
        balanceaSistema();
        System.out.println("---------------------------------------------------");
        System.out.println("Montagem do Quadro de Custos e dos vetores Oferta e Demanda");
        montaQuadroCustos();
        System.out.println("---------------------------------------------------");
//Sem transbordo
//        montaExemplo1();
        System.out.println("Cálculo da SBF Inicial pelo método do Canto Noroeste");
        calculaSBFInicialCantoNoroeste();
        System.out.println("---------------------------------------------------");
        System.out.println("Calcula a otimalidade da SBF");
        calculaOtimalidade();
        boolean otimalidade = false;
        while (otimalidade == false) {
            System.out.println("************************ ");
            System.out.println("Passo " + contadorPassos);
            otimalidade = calculaOtimalidade();
            contadorPassos++;
        }
        System.out.println("\n************************ ");
        System.out.println("---------------------------------------------------");
        System.out.println("Resultados:");
        mostraMatrizSolucao();
        calculaSolucao();
        System.out.println("---------------------------------------------------");
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

        System.out.println("Oferta Total Inicial: " + ofertaTotal);
        System.out.println("Demanda Total Inicial: " + demandaTotal);

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
        System.out.println("Oferta Total Pos Balanceamento: " + ofertaTotal);
        System.out.println("Demanda Total Pos Balanceamento: " + demandaTotal);
    }

    private void montaQuadroCustos() {
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
//                if (v[linha] == -1) {
//                    matrizCusto[linha][coluna] = bigM;
//                } else {
                matrizCusto[linha][coluna] = v[linha];
//                }
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
//                        if (v[pos] == -1) {
//                            matrizCusto[linha][coluna] = bigM;
//                        } else {
                        matrizCusto[linha][coluna] = v[pos];
//                        }
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
    }

    private void calculaSBFInicialCantoNoroeste() {
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
//                    if (matrizSolucao[i][j] == 0) {
//                        matrizSolucao[i][j] = -1;
//                    }
                }
            }

        }
        System.out.println("\n ************");
        System.out.println("\n SBF Inicial pelo metodo do Canto Noroeste");
        mostraMatrizSolucao();
        mostraVetorOferta();
        mostraVetorDemanda();
        System.out.println("\n ************");

    }

    public boolean calculaOtimalidade() {

        recarregaEstruturasAuxiliaresOtimalidade();
        calculaVetoresUV();
        mostraVetoresUJ();
        boolean encontraMenorElemento = encontraMenorElemento();
        if (encontraMenorElemento == false) {
            System.out.println("\nNão há mais nenhuma variavel não básica negativa.");
            return true;
        }
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
        System.out.println("Variavel a deixar a SBF: " + indiceVariavelNegativa.getI() + "    " + indiceVariavelNegativa.getJ() + "        " + indiceVariavelNegativa.getValor());

        matrizSolucao[novaVariavelBasica.getI()][novaVariavelBasica.getJ()] = indiceVariavelNegativa.getValor();
//        matrizSolucao[indiceVariavelNegativa.getI()][indiceVariavelNegativa.getJ()] = -1;

        boolean subtrai = true;
        int qtdElementosCaminho = caminho.size();

        for (int i = 1; i < qtdElementosCaminho; i++) {
            PassoCaminho aux = caminho.get((caminho.size() - 1));
            if (subtrai) {
//                System.out.println("SUB          " + aux.getI() + "       " + aux.getJ() + "     " + matrizSolucao[aux.getI()][aux.getJ()]);
                matrizSolucao[aux.getI()][aux.getJ()] = matrizSolucao[aux.getI()][aux.getJ()] - indiceVariavelNegativa.getValor();
                subtrai = false;
            } else {
//                System.out.println(" SOM         " + aux.getI() + "       " + aux.getJ() + "     " + matrizSolucao[aux.getI()][aux.getJ()]);
                matrizSolucao[aux.getI()][aux.getJ()] = matrizSolucao[aux.getI()][aux.getJ()] + indiceVariavelNegativa.getValor();
                subtrai = true;
            }
            caminho.remove((caminho.size() - 1));
        }
//        matrizSolucao[indiceVariavelNegativa.getI()][indiceVariavelNegativa.getJ()] = -1;

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
        caminho = new ArrayList<>();
        PassoCaminho passoInicial = novaVariavelBasica;
        caminho.add(passoInicial);
        matrizSolucao[novaVariavelBasica.getI()][novaVariavelBasica.getJ()] = novaVariavelBasica.getValor();
        boolean aindaTemCaminho = true;

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
                        }
                        proximaLinha = procuraColuna.getI();
                    } else {
                        aindaTemCaminho = false;
                    }
                }
            } else {
                aindaTemCaminho = false;
            }
        }
        mostraCaminho(caminho);
    }

    private PassoCaminho verificaElementoLinha(int linha) {
        PassoCaminho p = null;
        ArrayList<PassoCaminho> caminhosLinha = new ArrayList<>();

        for (int j = 0; j < colunas; j++) {
            if (matrizSolucao[linha][j] != -1 && matrizVisitado[linha][j] == false) {
                p = new PassoCaminho(linha, j, matrizSolucao[linha][j]);
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
        vetorU[0] = 0;
        vetorUVisitado[0] = true;
        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
                if (matrizSolucao[i][j] == -1) {
                } else {
                    if (vetorVVisitado[j] == false) {
                        vetorV[j] = matrizCusto[i][j] - vetorU[i];
                        vetorVVisitado[j] = true;
                    }
                    if (vetorUVisitado[i] == false) {
                        vetorUVisitado[i] = true;
                        vetorU[i] = matrizCusto[i][j] - vetorV[j];
                    }
                }
            }
        }
    }

    private void calculaSolucao() {
        int i;
        int j;
        double resultado = 0;
        for (i = 0; i < linhas; i++) {
            for (j = 0; j < colunas; j++) {
                if (matrizSolucao[i][j] != -1) {
                    resultado = resultado + (matrizSolucao[i][j] * matrizCusto[i][j]);
                }
            }
        }
        System.out.println("\n Z= " + resultado);
        this.resultadoZ = resultado;
    }

    private void mostraCaminho(ArrayList<PassoCaminho> caminho) {
        System.out.println("Caminho do Ciclo");
        for (int i = 0; i < caminho.size(); i++) {
            System.out.print("[" + caminho.get(i).getI() + "][" + caminho.get(i).getJ() + "] = " + caminho.get(i).getValor() + "  ->  ");
        }
        System.out.print("[" + caminho.get(0).getI() + "][" + caminho.get(0).getJ() + "] = " + caminho.get(0).getValor());
        System.out.println("\n");
    }

    private void mostraMatrizVisitado() {
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

    private void mostraMatrizCusto() {
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

    private void mostraMatrizSolucao() {
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

    private void mostraVetorOferta() {
        int i;

        System.out.println("  ");
        System.out.println("Vetor Oferta " + vetorOferta.length);

        for (i = 0; i < vetorOferta.length; i++) {
            System.out.print("  " + vetorOferta[i]);
        }
    }

    private void mostraVetorDemanda() {
        int i;

        System.out.println("  ");
        System.out.println("Vetor Demanda " + vetorDemanda.length);

        for (i = 0; i < vetorDemanda.length; i++) {
            System.out.print("  " + vetorDemanda[i]);
        }
    }

    private void mostraVetoresUJ() {
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

    private void mostraEntrada() {
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

    public double getBigM() {
        return bigM;
    }

    public double[][] getMatrizSolucao() {
        return matrizSolucao;
    }

    public double getResultadoZ() {
        return resultadoZ;
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public List<Origem> getOrigem() {
        return origem;
    }

    public List<Destino> getDestino() {
        return destino;
    }

    public List<Transbordo> getTransbordo() {
        return transbordo;
    }

}
