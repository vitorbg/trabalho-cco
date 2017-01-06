package udi48.transbordo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import udi48.transbordo.dominio.Destino;
import udi48.transbordo.dominio.Origem;
import udi48.transbordo.dominio.Transbordo;
import udi48.transbordo.transporte.Transporte;

public class FXMLController implements Initializable {

    @FXML
    private StackPane stackPane;
    private StackPane spEntrada;
    private ScrollPane scpPrincipal;
    private VBox vbPrincipal;
    private VBox vbConteudo;
    private VBox vbEntrada;
    private VBox vbTransbordo;
    private GridPane gpCabecalho;
    private GridPane gpOrigem;
    private GridPane gpDestino;
    private Label lbPulaLinha1;
    private Label lbPulaLinha2;
    private Label lbPulaLinha3;
    private Label lbPulaLinha4;
    private Label lbPulaLinha5;
    private Label lbPulaLinha6;
    private Label lbTitulo;
    private Label lbQtdOrigem;
    private Label lbQtdDestino;
    private Label lbQtdTransbordo;
    private Label lbOrigem;
    private Label lbDestino;
    private Label lbTransbordo;
    private Label lbInstrucao1;
    private Label lbInstrucao2;
    private TextField tfQtdOrigem;
    private TextField tfQtdDestino;
    private TextField tfQtdTransbordo;
    private Button btnGerar;
    private Button btnCalcular;
    private Button btnExemplo1;
    private Button btnExemplo2;
    private Pane paOrigem;
    private Pane paDestino;
    private Pane paTransbordo;
    private Pane paInstrucao;
    private Pane paInstrucao2;
    private List<Origem> origem;
    private List<Destino> destino;
    private List<Transbordo> transbordo;
    private int qtdOrigem;
    private int qtdDestino;
    private int qtdTransbordo;
    private TextArea taResultado;
    private Transporte transporte;
    private boolean entradaValidada;

    private void initComponents() {
        spEntrada = new StackPane();
        scpPrincipal = new ScrollPane();
        vbPrincipal = new VBox();
        vbConteudo = new VBox();
        vbEntrada = new VBox();
        vbTransbordo = new VBox();
        gpCabecalho = new GridPane();
        gpOrigem = new GridPane();
        gpDestino = new GridPane();
        lbPulaLinha1 = new Label("\n");
        lbPulaLinha2 = new Label("\n");
        lbPulaLinha3 = new Label("\n");
        lbPulaLinha4 = new Label("\n");
        lbPulaLinha5 = new Label("\n");
        lbPulaLinha6 = new Label("\n");
        lbTitulo = new Label("Problema do Transporte com Transbordo");
        lbQtdOrigem = new Label("Quantidade Origem");
        lbQtdDestino = new Label("Quantidade Destino");
        lbQtdTransbordo = new Label("Quantidade Transbordo");
        tfQtdOrigem = new TextField("0");
        tfQtdDestino = new TextField("0");
        tfQtdTransbordo = new TextField("0");
        lbOrigem = new Label("Informações de ORIGEM");
        lbDestino = new Label("Informações de DESTINO");
        lbTransbordo = new Label("Informações de TRANSBORDO");
        lbInstrucao1 = new Label("Instrução: Quando não houver ligação de um elemento a outro digite o valor 999999 (BIG M). ");
        lbInstrucao2 = new Label("Instrução: Informe todas as quantidades para montar os dados de entrada. Uma quantidade não pode ser igual a 0.");
        btnGerar = new Button("Gerar Entrada");
        btnCalcular = new Button("Calcular Solução");
        btnExemplo1 = new Button("Exemplo 1");
        btnExemplo2 = new Button("Exemplo 2");
        paOrigem = new Pane(lbOrigem);
        paDestino = new Pane(lbDestino);
        paTransbordo = new Pane(lbTransbordo);
        paInstrucao = new Pane(lbInstrucao1);
        paInstrucao2 = new Pane(lbInstrucao2);
        origem = new ArrayList<>();
        destino = new ArrayList<>();
        transbordo = new ArrayList<>();
        taResultado = new TextArea();
        entradaValidada = false;

        stackPane.setMaxSize(ControleTamanhos.LARGURA_PRINCIPAL, ControleTamanhos.ALTURA_PRINCIPAL);
        stackPane.setMinSize(ControleTamanhos.LARGURA_PRINCIPAL, ControleTamanhos.ALTURA_PRINCIPAL);
        stackPane.setPadding(new Insets(ControleTamanhos.ESPACAMENTO));
        scpPrincipal.setMaxSize(ControleTamanhos.LARGURA, ControleTamanhos.ALTURA);
        scpPrincipal.setMinSize(ControleTamanhos.LARGURA, ControleTamanhos.ALTURA);
        taResultado.setMaxSize(ControleTamanhos.LARGURA, ControleTamanhos.ALTURA);
        taResultado.setMinSize(ControleTamanhos.LARGURA, ControleTamanhos.ALTURA);
        gpCabecalho.setMaxWidth(ControleTamanhos.LARGURA);
        gpCabecalho.setMinWidth(ControleTamanhos.LARGURA);
        vbPrincipal.setSpacing(ControleTamanhos.ESPACAMENTO);

        lbOrigem.setStyle("-fx-font-weight: bold");
        lbDestino.setStyle("-fx-font-weight: bold");
        lbTransbordo.setStyle("-fx-font-weight: bold");
        lbTitulo.setFont(Font.font(26));
        lbTitulo.setStyle("-fx-font-weight: bold");

        stackPane.getChildren().addAll(vbPrincipal);
        vbPrincipal.getChildren().addAll(lbTitulo, paInstrucao, paInstrucao2, gpCabecalho, scpPrincipal);
        scpPrincipal.setContent(vbConteudo);
        vbConteudo.getChildren().addAll(spEntrada);
        //COLUNA LINHA
        gpCabecalho.add(lbQtdOrigem, 0, 0);
        gpCabecalho.add(tfQtdOrigem, 1, 0);
        gpCabecalho.add(lbQtdDestino, 2, 0);
        gpCabecalho.add(tfQtdDestino, 3, 0);
        gpCabecalho.add(lbQtdTransbordo, 4, 0);
        gpCabecalho.add(tfQtdTransbordo, 5, 0);
        gpCabecalho.add(btnGerar, 6, 0);
        //
        gpCabecalho.add(btnExemplo1, 0, 1);
        gpCabecalho.add(btnExemplo2, 2, 1);
        gpCabecalho.add(btnCalcular, 6, 1);

        FXUtil.ajustaGridPaneEntrada(gpOrigem);
        FXUtil.ajustaGridPaneEntrada(gpDestino);
        FXUtil.ajustaPaneTitulo(paOrigem);
        FXUtil.ajustaPaneTitulo(paDestino);
        FXUtil.ajustaPaneTitulo(paTransbordo);
        FXUtil.ajustaGridPaneCabecalho(gpCabecalho);
        FXUtil.formataTextFieldNumerico(tfQtdOrigem);
        FXUtil.formataTextFieldNumerico(tfQtdDestino);
        FXUtil.formataTextFieldNumerico(tfQtdTransbordo);
        FXUtil.ajustaPaneInstrucao(paInstrucao);
        FXUtil.ajustaPaneInstrucao(paInstrucao2);
    }

    private void limpa() {
        spEntrada.getChildren().clear();
        gpOrigem.getChildren().clear();
        gpDestino.getChildren().clear();
        vbEntrada.getChildren().clear();
        vbTransbordo.getChildren().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComponents();

        btnGerar.addEventHandler(ActionEvent.ACTION, (ActionEvent actionEvent) -> {
            qtdOrigem = Integer.valueOf(tfQtdOrigem.getText());
            qtdDestino = Integer.valueOf(tfQtdDestino.getText());
            qtdTransbordo = Integer.valueOf(tfQtdTransbordo.getText());
            entradaValidada = validaCamposDigitados();
            if (entradaValidada == true) {
                montaEntrada();
            }
        });
        btnExemplo1.addEventHandler(ActionEvent.ACTION, (ActionEvent actionEvent) -> {
            montaExemplo1();
            transporte = new Transporte(origem, destino, transbordo);
            transporte.calculaSolucaoBasicaFinal();
            origem = transporte.getOrigem();
            destino = transporte.getDestino();
            mostraResultado();
        });
        btnExemplo2.addEventHandler(ActionEvent.ACTION, (ActionEvent actionEvent) -> {
            montaExemplo2();
            transporte = new Transporte(origem, destino, transbordo);
            transporte.calculaSolucaoBasicaFinal();
            origem = transporte.getOrigem();
            destino = transporte.getDestino();
            mostraResultado();
        });

        btnCalcular.addEventHandler(ActionEvent.ACTION, (ActionEvent actionEvent) -> {
            if (entradaValidada == true) {
                buscaInformacoesEntrada();
                mostraEntrada();
                transporte = new Transporte(origem, destino, transbordo);
                transporte.calculaSolucaoBasicaFinal();
                origem = transporte.getOrigem();
                destino = transporte.getDestino();
                mostraResultado();
            }
        });
    }

    private boolean validaCamposDigitados() {

        if (tfQtdOrigem.getText().isEmpty() || tfQtdDestino.getText().isEmpty() || tfQtdTransbordo.getText().isEmpty()) {
            return false;
        }

        return !(Integer.valueOf(tfQtdOrigem.getText()) == 0
                || Integer.valueOf(tfQtdDestino.getText()) == 0
                || Integer.valueOf(tfQtdTransbordo.getText()) == 0);
    }

    private void mostraResultado() {
        taResultado.clear();
        limpa();
        mostraEntrada();
        mostraSolucao();
        taResultado.appendText("\n");
        taResultado.appendText("Resultado Final Z= " + transporte.getResultadoZ());

        spEntrada.getChildren().addAll(taResultado);

    }

    private void mostraSolucao() {
        double[][] matrizSolucao = transporte.getMatrizSolucao();
        String s = "Configuração final------------------------------ ";

        System.out.println(s);
        taResultado.appendText(s);
        taResultado.appendText("\n");

        for (int i = 0; i < origem.size(); i++) {
            for (int j = 0; j < transbordo.size(); j++) {
                double valor;
                if (matrizSolucao[i][j] == -1) {
                    valor = 0;
                } else {
                    valor = matrizSolucao[i][j];
                }
                String s1 = "De Origem " + origem.get(i).getNome()
                        + " para Transbordo " + transbordo.get(j).getNome() + " : " + valor;
                System.out.println(s1);
                taResultado.appendText(s1);
                taResultado.appendText("\n");
            }
        }

        int d = 0;
        int k = 0;
        for (int i = origem.size(); i < transbordo.size() + origem.size(); i++) {
            d = 0;
            for (int j = transbordo.size(); j < (transbordo.size() + destino.size()); j++) {
                double valor;
                if (matrizSolucao[i][j] == -1) {
                    valor = 0;
                } else {
                    valor = matrizSolucao[i][j];
                }
                String s1 = "De Transbordo " + transbordo.get(k).getNome()
                        + " para Destino " + destino.get(d).getNome() + " : " + valor;
                System.out.println(s1);
                taResultado.appendText(s1);
                taResultado.appendText("\n");
                d++;
            }
            k++;

        }

    }

    private void mostraEntrada() {
        taResultado.appendText("VALORES DE ENTRADA ---------------------------------");
        taResultado.appendText("\n");
        String s = "------- ORIGEM ";
        System.out.println(s);
        taResultado.appendText(s);
        taResultado.appendText("\n");
        for (int i = 0; i < origem.size(); i++) {
            String s1 = "--> Origem " + i;
            String s2 = " nome: " + origem.get(i).getNome() + " oferta: " + origem.get(i).getOferta();
            System.out.println(s1);
            System.out.println(s2);
            taResultado.appendText(s1);
            taResultado.appendText("\n");
            taResultado.appendText(s2);
            taResultado.appendText("\n");
        }
        String s5 = "------- DESTINO ";
        System.out.println(s5);
        taResultado.appendText(s5);
        taResultado.appendText("\n");
        for (int i = 0; i < destino.size(); i++) {
            String s3 = "--> Destino " + i;
            String s4 = " nome: " + destino.get(i).getNome() + " demanda: " + destino.get(i).getDemanda();
            System.out.println(s3);
            System.out.println(s4);
            taResultado.appendText(s3);
            taResultado.appendText("\n");
            taResultado.appendText(s4);
            taResultado.appendText("\n");
        }
        String s6 = "------- TRANSBORDO ";
        System.out.println(s6);
        taResultado.appendText(s6);
        taResultado.appendText("\n");
        for (int i = 0; i < transbordo.size(); i++) {
            String s7 = "--> Transbordo " + i;
            String s8 = " nome: " + transbordo.get(i).getNome();
            System.out.println(s7);
            System.out.println(s8);
            taResultado.appendText(s7);
            taResultado.appendText("\n");
            taResultado.appendText(s8);
            taResultado.appendText("\n");
            double[] custos = transbordo.get(i).getCustos();
            int j = 0;
            while (j < custos.length) {
                for (int k = 0; k < origem.size(); k++) {
                    String s9 = "Para Origem " + origem.get(k).getNome() + " : " + custos[j];
                    System.out.println(s9);
                    taResultado.appendText(s9);
                    taResultado.appendText("\n");
                    j++;
                }
                for (int k = 0; k < destino.size(); k++) {
                    String s10 = "Para Destino " + destino.get(k).getNome() + " : " + custos[j];
                    System.out.println(s10);
                    taResultado.appendText(s10);
                    taResultado.appendText("\n");
                    j++;
                }
                for (int k = 0; k < transbordo.size(); k++) {
                    String s11 = "Para Transbordo " + transbordo.get(k).getNome() + " : " + custos[j];
                    System.out.println(s11);
                    taResultado.appendText(s11);
                    taResultado.appendText("\n");
                    j++;
                }
            }
            System.out.println(" ");
            taResultado.appendText("\n");
        }
    }

    private void montaEntrada() {
        limpa();
        int i;
        int j;
        int k;
        int pulaLinha;
//ORIGEM -----------------------------------------------------------------------        
        i = 0;
        j = 0;
        pulaLinha = 0;
        for (k = 0; k < qtdOrigem; k++) {
            if (pulaLinha == 2) {
                pulaLinha = 0;
                j = 0;
                i++;
            }
            pulaLinha++;
            Label lbOrigemAux = new Label("Origem " + k + " Nome:");
            Label lbOfertaAux = new Label("Oferta:");
            TextField tfOrigemAux = new TextField();
            TextField tfOfertaAux = new TextField();
            tfOfertaAux.setTextFormatter(FXUtil.textFormatterDouble());
            //COLUNA LINHA
            gpOrigem.add(lbOrigemAux, j, i);
            gpOrigem.add(tfOrigemAux, j + 1, i);
            gpOrigem.add(lbOfertaAux, j + 2, i);
            gpOrigem.add(tfOfertaAux, j + 3, i);
            j = j + 4;
        }
//DESTINO _---------------------------------------------------------------------        
        i = 0;
        j = 0;
        pulaLinha = 0;
        for (k = 0; k < qtdDestino; k++) {
            if (pulaLinha == 2) {
                pulaLinha = 0;
                j = 0;
                i++;
            }
            pulaLinha++;
            Label lbDestinoAux = new Label("Destino " + k + " Nome:");
            Label lbDemandaAux = new Label("Demanda:");
            TextField tfDestinoAux = new TextField();
            TextField tfDemandaAux = new TextField();
            tfDemandaAux.setTextFormatter(FXUtil.textFormatterDouble());
            //COLUNA LINHA
            gpDestino.add(lbDestinoAux, j, i);
            gpDestino.add(tfDestinoAux, j + 1, i);
            gpDestino.add(lbDemandaAux, j + 2, i);
            gpDestino.add(tfDemandaAux, j + 3, i);
            j = j + 4;
        }
//TRANSBORDO -------------------------------------------------------------------        
        int t = 0;
        for (t = 0; t < qtdTransbordo; t++) {
            GridPane gpOrigemTransbordo = new GridPane();
            GridPane gpDestinoTransbordo = new GridPane();
            GridPane gpTransbordoTransbordo = new GridPane();
            FXUtil.ajustaGridPaneEntrada(gpOrigemTransbordo);
            FXUtil.ajustaGridPaneEntrada(gpDestinoTransbordo);
            FXUtil.ajustaGridPaneTransbordo(gpTransbordoTransbordo);
            i = 0;
            j = 0;
            pulaLinha = 0;
            for (k = 0; k < qtdOrigem; k++) {
                if (pulaLinha == 2) {
                    pulaLinha = 0;
                    j = 0;
                    i++;
                }
                pulaLinha++;
                Label lbOrigem = new Label("Custo p/ Origem " + k + ":");
                TextField tfOrigem = new TextField();
                tfOrigem.setTextFormatter(FXUtil.textFormatterDouble());
                //COLUNA LINHA
                gpOrigemTransbordo.add(lbOrigem, j, i);
                gpOrigemTransbordo.add(tfOrigem, j + 1, i);
                j = j + 2;
            }
            //-------
            i = 0;
            j = 0;
            pulaLinha = 0;
            for (k = 0; k < qtdDestino; k++) {
                if (pulaLinha == 2) {
                    pulaLinha = 0;
                    j = 0;
                    i++;
                }
                pulaLinha++;
                Label lbDestino = new Label("Custo p/ Destino " + k + ":");
                TextField tfDestino = new TextField();
                tfDestino.setTextFormatter(FXUtil.textFormatterDouble());
                //COLUNA LINHA
                gpDestinoTransbordo.add(lbDestino, j, i);
                gpDestinoTransbordo.add(tfDestino, j + 1, i);

                j = j + 2;

            }
            //-----
            i = 0;
            j = 0;
            pulaLinha = 0;
            for (k = 0; k < qtdTransbordo; k++) {
                if (pulaLinha == 2) {
                    pulaLinha = 0;
                    j = 0;
                    i++;
                }
                pulaLinha++;
                Label lbTransbordo = new Label("Custo p/ Transbordo " + k + ":");
                TextField tfTransbordo = new TextField();
                tfTransbordo.setTextFormatter(FXUtil.textFormatterDouble());
                //COLUNA LINHA
                gpTransbordoTransbordo.add(lbTransbordo, j, i);
                gpTransbordoTransbordo.add(tfTransbordo, j + 1, i);
                j = j + 2;
                if (k == t) {
                    tfTransbordo.setText("0");
                    tfTransbordo.setDisable(true);
                }
            }
            Label lb = new Label("Transbordo " + t);
            TextField tf = new TextField();
            HBox hb = new HBox(lb, tf);
            Pane p = new Pane(hb);
            FXUtil.ajustaPaneTransbordo(p);
            VBox vb = new VBox();
            vb.getChildren().addAll(p, gpOrigemTransbordo, gpDestinoTransbordo, gpTransbordoTransbordo);
            vbTransbordo.getChildren().add(vb);
        }
// -----------------------------------------------------------------------------        
        vbEntrada.getChildren().addAll(lbPulaLinha1, paOrigem, lbPulaLinha2, gpOrigem,
                lbPulaLinha3, paDestino, lbPulaLinha4, gpDestino,
                lbPulaLinha5, paTransbordo, lbPulaLinha6, vbTransbordo
        );
        spEntrada.getChildren().addAll(vbEntrada);

    }

    private void buscaInformacoesEntrada() {
        int i;
        int j;
        int k;
        int pulaLinha;
//ORIGEM -----------------------------------------------------------------------        
        i = 0;
        j = 0;
        pulaLinha = 0;
        for (k = 0; k < qtdOrigem; k++) {
            if (pulaLinha == 2) {
                pulaLinha = 0;
                j = 0;
                i++;
            }
            pulaLinha++;
            Label lbOrigem = (Label) FXUtil.getNodePorIndiceLinhaColuna(i, j, gpOrigem);
            Label lbOferta = (Label) FXUtil.getNodePorIndiceLinhaColuna(i, j + 2, gpOrigem);
            TextField tfOrigem = (TextField) FXUtil.getNodePorIndiceLinhaColuna(i, j + 1, gpOrigem);
            TextField tfOferta = (TextField) FXUtil.getNodePorIndiceLinhaColuna(i, j + 3, gpOrigem);

            String nomeOrigem = tfOrigem.getText();
            double ofertaOrigem = Double.parseDouble(tfOferta.getText());
            Origem o = new Origem(nomeOrigem, ofertaOrigem);
            origem.add(o);

            j = j + 4;
        }
//DESTINO ----------------------------------------------------------------------                
        i = 0;
        j = 0;
        pulaLinha = 0;
        for (k = 0; k < qtdDestino; k++) {
            if (pulaLinha == 2) {
                pulaLinha = 0;
                j = 0;
                i++;
            }
            pulaLinha++;
            Label lbDestinoAux = (Label) FXUtil.getNodePorIndiceLinhaColuna(i, j, gpDestino);
            Label lbDemandaAux = (Label) FXUtil.getNodePorIndiceLinhaColuna(i, j + 2, gpDestino);
            TextField tfDestinoAux = (TextField) FXUtil.getNodePorIndiceLinhaColuna(i, j + 1, gpDestino);
            TextField tfDemandaAux = (TextField) FXUtil.getNodePorIndiceLinhaColuna(i, j + 3, gpDestino);

            String nomeDestino = tfDestinoAux.getText();
            double demandaDestino = Double.parseDouble(tfDemandaAux.getText());
            Destino d = new Destino(nomeDestino, demandaDestino);
            destino.add(d);
            j = j + 4;
        }
//TRANSBORDO -------------------------------------------------------------------        
        int t = 0;
        int custo = 0;
        int totalCustos = qtdOrigem + qtdDestino + qtdTransbordo;
        for (t = 0; t < qtdTransbordo; t++) {
            custo = 0;

            VBox vb = (VBox) vbTransbordo.getChildren().get(t);
            Pane p = (Pane) vb.getChildren().get(0);
            HBox hb = (HBox) p.getChildren().get(0);
            TextField tfNomeTransbordo = (TextField) hb.getChildren().get(1);
            GridPane gpOrigemTransbordo = (GridPane) vb.getChildren().get(1);
            GridPane gpDestinoTransbordo = (GridPane) vb.getChildren().get(2);
            GridPane gpTransbordoTransbordo = (GridPane) vb.getChildren().get(3);
            System.out.println("Nome transbordo: " + tfNomeTransbordo.getText());

            String nomeTransbordo = tfNomeTransbordo.getText();

            double custos[] = new double[totalCustos];

            i = 0;
            j = 0;
            pulaLinha = 0;
            for (k = 0; k < qtdOrigem; k++) {
                if (pulaLinha == 2) {
                    pulaLinha = 0;
                    j = 0;
                    i++;
                }
                pulaLinha++;
                Label lbOrigem = (Label) FXUtil.getNodePorIndiceLinhaColuna(i, j, gpOrigemTransbordo);
                TextField tfOrigem = (TextField) FXUtil.getNodePorIndiceLinhaColuna(i, j + 1, gpOrigemTransbordo);
                j = j + 2;

                double c = Double.valueOf(tfOrigem.getText());
                custos[custo] = c;
                custo++;
            }
            //-------
            i = 0;
            j = 0;
            pulaLinha = 0;
            for (k = 0; k < qtdDestino; k++) {
                if (pulaLinha == 2) {
                    pulaLinha = 0;
                    j = 0;
                    i++;
                }
                pulaLinha++;
                Label lbDestino = (Label) FXUtil.getNodePorIndiceLinhaColuna(i, j, gpDestinoTransbordo);
                TextField tfDestino = (TextField) FXUtil.getNodePorIndiceLinhaColuna(i, j + 1, gpDestinoTransbordo);
                j = j + 2;

                double c = Double.valueOf(tfDestino.getText());
                custos[custo] = c;
                custo++;
            }
            //-----
            i = 0;
            j = 0;
            pulaLinha = 0;
            for (k = 0; k < qtdTransbordo; k++) {
                if (pulaLinha == 2) {
                    pulaLinha = 0;
                    j = 0;
                    i++;
                }
                pulaLinha++;
                Label lbTransbordo = (Label) FXUtil.getNodePorIndiceLinhaColuna(i, j, gpTransbordoTransbordo);
                TextField tfTransbordo = (TextField) FXUtil.getNodePorIndiceLinhaColuna(i, j + 1, gpTransbordoTransbordo);
                j = j + 2;

                double c = Double.valueOf(tfTransbordo.getText());
                custos[custo] = c;
                custo++;
            }

            Transbordo tr = new Transbordo(nomeTransbordo, custos);
            transbordo.add(tr);
        }
// -----------------------------------------------------------------------------        
    }

    private void montaExemplo1() {
        qtdOrigem = 3;
        qtdDestino = 2;
        qtdTransbordo = 2;

        Origem o1 = new Origem("Brasil", 300);
        Origem o2 = new Origem("Japao", 220);
        Origem o3 = new Origem("E.U.A.", 400);
//        Origem o1 = new Origem("Brasil", 400);

        Destino d1 = new Destino("Norte", 400);
        Destino d2 = new Destino("Sul", 520);
//        Destino d1 = new Destino("Norte", 500);

        double[] v = new double[7];
        v[0] = 40;
        v[1] = 55;
        v[2] = 999999;
        v[3] = 0;
        v[4] = 25;
        v[5] = 30;
        v[6] = 40;

        Transbordo t1 = new Transbordo("Cuba", v);

        double[] v2 = new double[7];
        v2[0] = 45;
        v2[1] = 30;
        v2[2] = 35;
        v2[3] = 25;
        v2[4] = 0;
        v2[5] = 40;
        v2[6] = 35;

        Transbordo t2 = new Transbordo("Belgica", v2);

        origem = new ArrayList<>();
        destino = new ArrayList<>();
        transbordo = new ArrayList<>();
        origem.add(o1);
        origem.add(o2);
        origem.add(o3);
        destino.add(d1);
        destino.add(d2);
        transbordo.add(t1);
        transbordo.add(t2);
    }

    private void montaExemplo2() {
        qtdOrigem = 3;
        qtdDestino = 2;
        qtdTransbordo = 2;

        Origem o1 = new Origem("Brasil", 300);
        Origem o2 = new Origem("Japao", 220);
        Origem o3 = new Origem("E.U.A.", 400);

        Destino d1 = new Destino("Norte", 500);
        Destino d2 = new Destino("Sul", 520);

        double[] v = new double[7];
        v[0] = 40;
        v[1] = 55;
        v[2] = 999999;
        v[3] = 0;
        v[4] = 25;
        v[5] = 30;
        v[6] = 40;

        Transbordo t1 = new Transbordo("Cuba", v);

        double[] v2 = new double[7];
        v2[0] = 45;
        v2[1] = 30;
        v2[2] = 35;
        v2[3] = 25;
        v2[4] = 0;
        v2[5] = 40;
        v2[6] = 35;

        Transbordo t2 = new Transbordo("Belgica", v2);

        origem = new ArrayList<>();
        destino = new ArrayList<>();
        transbordo = new ArrayList<>();
        origem.add(o1);
        origem.add(o2);
        origem.add(o3);
        destino.add(d1);
        destino.add(d2);
        transbordo.add(t1);
        transbordo.add(t2);
    }

}
