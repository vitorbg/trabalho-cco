package udi48.transbordo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private TextField tfQtdOrigem;
    private TextField tfQtdDestino;
    private TextField tfQtdTransbordo;
    private Button btnGerar;
    private Button btnExemplo1;
    private Button btnExemplo2;
    private Button btnExemplo3;
    private Pane paOrigem;
    private Pane paDestino;
    private Pane paTransbordo;
    private List<Origem> origem;
    private List<Destino> destino;
    private List<Transbordo> transbordo;
    private int qtdOrigem;
    private int qtdDestino;
    private int qtdTransbordo;

    private void initComponents() {
        spEntrada = new StackPane();
        scpPrincipal = new ScrollPane();
        vbPrincipal = new VBox();
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
        lbTitulo = new Label("Algoritmo Transporte com Transbordo");
        lbQtdOrigem = new Label("Quantidade Origem");
        lbQtdDestino = new Label("Quantidade Destino");
        lbQtdTransbordo = new Label("Quantidade Transbordo");
        tfQtdOrigem = new TextField("0");
        tfQtdDestino = new TextField("0");
        tfQtdTransbordo = new TextField("0");
        lbOrigem = new Label("Informações de ORIGEM");
        lbDestino = new Label("Informações de DESTINO");
        lbTransbordo = new Label("Informações de TRANSBORDO");
        btnGerar = new Button("Gerar");
        btnExemplo1 = new Button("Exemplo 1");
        btnExemplo2 = new Button("Exemplo 2");
        btnExemplo3 = new Button("Exemplo 3");
        paOrigem = new Pane(lbOrigem);
        paDestino = new Pane(lbDestino);
        paTransbordo = new Pane(lbTransbordo);
        origem = new ArrayList<>();
        destino = new ArrayList<>();
        transbordo = new ArrayList<>();

        stackPane.setMaxSize(ControleTamanhos.LARGURA_PRINCIPAL, ControleTamanhos.ALTURA_PRINCIPAL);
        stackPane.setMinSize(ControleTamanhos.LARGURA_PRINCIPAL, ControleTamanhos.ALTURA_PRINCIPAL);

        scpPrincipal.setMaxSize(ControleTamanhos.LARGURA, ControleTamanhos.ALTURA);
        scpPrincipal.setMinSize(ControleTamanhos.LARGURA, ControleTamanhos.ALTURA);

        gpCabecalho.setMaxWidth(ControleTamanhos.LARGURA);
        gpCabecalho.setMinWidth(ControleTamanhos.LARGURA);

        stackPane.getChildren().addAll(scpPrincipal);
        scpPrincipal.setContent(vbPrincipal);

        vbPrincipal.getChildren().addAll(lbTitulo, gpCabecalho, spEntrada);
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
        gpCabecalho.add(btnExemplo2, 1, 1);
        gpCabecalho.add(btnExemplo3, 2, 1);

        ajustaGridPaneEntrada(gpOrigem);
        ajustaGridPaneEntrada(gpDestino);
        ajustaPaneTitulo(paOrigem);
        ajustaPaneTitulo(paDestino);
        ajustaPaneTitulo(paTransbordo);
    }

    private void montaEntrada() {
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
            Label lbOrigem = new Label("Origem " + k + " Nome:");
            Label lbOferta = new Label("Oferta:");
            TextField tfOrigem = new TextField();
            TextField tfOferta = new TextField();
            //COLUNA LINHA
            gpOrigem.add(lbOrigem, j, i);
            gpOrigem.add(tfOrigem, j + 1, i);
            gpOrigem.add(lbOferta, j + 2, i);
            gpOrigem.add(tfOferta, j + 3, i);
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
            ajustaGridPaneEntrada(gpOrigemTransbordo);
            ajustaGridPaneEntrada(gpDestinoTransbordo);
            ajustaGridPaneTransbordo(gpTransbordoTransbordo);
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
                //COLUNA LINHA
                gpTransbordoTransbordo.add(lbTransbordo, j, i);
                gpTransbordoTransbordo.add(tfTransbordo, j + 1, i);
                j = j + 2;
            }
            Label lb = new Label("Transbordo " + t);
            TextField tf = new TextField();
            HBox hb = new HBox(lb, tf);
            VBox vb = new VBox();
            vb.getChildren().addAll(hb, gpOrigemTransbordo, gpDestinoTransbordo, gpTransbordoTransbordo);
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
            Label lbOrigem = (Label) getNodePorIndiceLinhaColuna(i, j, gpOrigem);
            Label lbOferta = (Label) getNodePorIndiceLinhaColuna(i, j + 2, gpOrigem);
            TextField tfOrigem = (TextField) getNodePorIndiceLinhaColuna(i, j + 1, gpOrigem);
            TextField tfOferta = (TextField) getNodePorIndiceLinhaColuna(i, j + 3, gpOrigem);

            System.out.println(lbOrigem.getText());
            System.out.println(lbOferta.getText());
            System.out.println(tfOrigem.getText());
            System.out.println(tfOferta.getText());
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
            Label lbDestinoAux = (Label) getNodePorIndiceLinhaColuna(i, j, gpDestino);
            Label lbDemandaAux = (Label) getNodePorIndiceLinhaColuna(i, j + 2, gpDestino);
            TextField tfDestinoAux = (TextField) getNodePorIndiceLinhaColuna(i, j + 1, gpDestino);
            TextField tfDemandaAux = (TextField) getNodePorIndiceLinhaColuna(i, j + 3, gpDestino);

            System.out.println(lbDestinoAux.getText());
            System.out.println(lbDemandaAux.getText());
            System.out.println(tfDestinoAux.getText());
            System.out.println(tfDemandaAux.getText());

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
            HBox hb = (HBox) vb.getChildren().get(0);
            TextField tfNomeTransbordo = (TextField) hb.getChildren().get(1);
            GridPane gpOrigemTransbordo = (GridPane) vb.getChildren().get(1);
            GridPane gpDestinoTransbordo = (GridPane) vb.getChildren().get(2);
            GridPane gpTransbordoTransbordo = (GridPane) vb.getChildren().get(3);
            System.out.println("Nome transbordo: " + tfNomeTransbordo.getText());

            String nomeTransbordo = tfNomeTransbordo.getText();

            double custos[] = new double[totalCustos];

//            
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
                Label lbOrigem = (Label) getNodePorIndiceLinhaColuna(i, j, gpOrigemTransbordo);
                TextField tfOrigem = (TextField) getNodePorIndiceLinhaColuna(i, j + 1, gpOrigemTransbordo);
                j = j + 2;

                System.out.println(lbOrigem.getText());
                System.out.println(tfOrigem.getText());
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
                Label lbDestino = (Label) getNodePorIndiceLinhaColuna(i, j, gpDestinoTransbordo);
                TextField tfDestino = (TextField) getNodePorIndiceLinhaColuna(i, j + 1, gpDestinoTransbordo);
                j = j + 2;

                System.out.println(lbDestino.getText());
                System.out.println(tfDestino.getText());
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
                Label lbTransbordo = (Label) getNodePorIndiceLinhaColuna(i, j, gpTransbordoTransbordo);
                TextField tfTransbordo = (TextField) getNodePorIndiceLinhaColuna(i, j + 1, gpTransbordoTransbordo);
                j = j + 2;

                System.out.println(lbTransbordo.getText());
                System.out.println(tfTransbordo.getText());
                double c = Double.valueOf(tfTransbordo.getText());
                custos[custo] = c;
                custo++;
            }

            Transbordo tr = new Transbordo(nomeTransbordo, custos);
            transbordo.add(tr);
        }
// -----------------------------------------------------------------------------        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initComponents();

        btnGerar.addEventHandler(ActionEvent.ACTION, (ActionEvent actionEvent) -> {
            qtdOrigem = Integer.valueOf(tfQtdOrigem.getText());
            qtdDestino = Integer.valueOf(tfQtdDestino.getText());
            qtdTransbordo = Integer.valueOf(tfQtdTransbordo.getText());
            montaEntrada();
        });
        btnExemplo1.addEventHandler(ActionEvent.ACTION, (ActionEvent actionEvent) -> {
            buscaInformacoesEntrada();
            mostraEntrada();
        });
        btnExemplo2.addEventHandler(ActionEvent.ACTION, (ActionEvent actionEvent) -> {
            Transporte transporte = new Transporte(origem, destino, transbordo);
            transporte.montraQuadroCustos();
        });
    }

    private void mostraEntrada() {
        System.out.println("------- ORIGEM ");
        for (int i = 0; i < origem.size(); i++) {
            System.out.println("--> Origem " + i);
            System.out.println(" nome: " + origem.get(i).getNome());
            System.out.println(" oferta: " + origem.get(i).getOferta());
        }
        System.out.println("------- DESTINO ");
        for (int i = 0; i < destino.size(); i++) {
            System.out.println("--> Destino " + i);
            System.out.println(" nome: " + destino.get(i).getNome());
            System.out.println(" demanda: " + destino.get(i).getDemanda());
        }

        System.out.println("------- TRANSBORDO ");
        for (int i = 0; i < transbordo.size(); i++) {
            System.out.println("--> Transbordo " + i);
            System.out.println(" nome: " + transbordo.get(i).getNome());
            double[] custos = transbordo.get(i).getCustos();
            for (int j = 0; j < custos.length; j++) {
                System.out.print("  " + custos[j]);
            }
            System.out.println(" ");

        }

    }

    private ColumnConstraints criaColumnConstraintsPercentual(double percentual) {
        ColumnConstraints c = new ColumnConstraints(percentual);
        c.setPercentWidth(percentual);
        return c;
    }

    private Node getNodePorIndiceLinhaColuna(final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if (gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }

    private void ajustaPaneTitulo(final Pane pane) {
        pane.setMaxWidth(ControleTamanhos.LARGURA);
        pane.setMinWidth(ControleTamanhos.LARGURA);
        pane.setStyle("-fx-background-color: lightgray;");
    }

    private void ajustaGridPaneCabecalho(final GridPane gridPane) {
        gridPane.getColumnConstraints().addAll(
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(8),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(8),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(8),
                criaColumnConstraintsPercentual(31)
        );
        gridPane.setVgap(ControleTamanhos.ESPACAMENTO);
        gridPane.setHgap(ControleTamanhos.ESPACAMENTO);
    }

    private void ajustaGridPaneEntrada(final GridPane gridPane) {
        gridPane.getColumnConstraints().addAll(
                criaColumnConstraintsPercentual(12.5),
                criaColumnConstraintsPercentual(12.5),
                criaColumnConstraintsPercentual(12.5),
                criaColumnConstraintsPercentual(12.5),
                criaColumnConstraintsPercentual(12.5),
                criaColumnConstraintsPercentual(12.5),
                criaColumnConstraintsPercentual(12.5),
                criaColumnConstraintsPercentual(12.5)
        );
        gridPane.setVgap(ControleTamanhos.ESPACAMENTO);
        gridPane.setHgap(ControleTamanhos.ESPACAMENTO);
    }

    private void ajustaGridPaneTransbordo(final GridPane gridPane) {
        gridPane.getColumnConstraints().addAll(
                criaColumnConstraintsPercentual(25),
                criaColumnConstraintsPercentual(25),
                criaColumnConstraintsPercentual(25),
                criaColumnConstraintsPercentual(25)
        );
        gridPane.setVgap(ControleTamanhos.ESPACAMENTO);
        gridPane.setHgap(ControleTamanhos.ESPACAMENTO);
    }
}
