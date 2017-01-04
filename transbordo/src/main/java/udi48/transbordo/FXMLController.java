package udi48.transbordo;

import java.net.URL;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
    private GridPane gpTransbordo;
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
        gpTransbordo = new GridPane();
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

        stackPane.setMaxSize(ControleTamanhos.LARGURA_PRINCIPAL, ControleTamanhos.ALTURA_PRINCIPAL);
        stackPane.setMinSize(ControleTamanhos.LARGURA_PRINCIPAL, ControleTamanhos.ALTURA_PRINCIPAL);
        scpPrincipal.setMaxSize(ControleTamanhos.LARGURA, ControleTamanhos.ALTURA);
        scpPrincipal.setMinSize(ControleTamanhos.LARGURA, ControleTamanhos.ALTURA);
        gpCabecalho.setMaxWidth(ControleTamanhos.LARGURA);
        gpCabecalho.setMinWidth(ControleTamanhos.LARGURA);

        gpCabecalho.setVgap(ControleTamanhos.ESPACAMENTO);
        gpCabecalho.setHgap(ControleTamanhos.ESPACAMENTO);

        gpOrigem.setVgap(ControleTamanhos.ESPACAMENTO);
        gpOrigem.setHgap(ControleTamanhos.ESPACAMENTO);

        gpDestino.setVgap(ControleTamanhos.ESPACAMENTO);
        gpDestino.setHgap(ControleTamanhos.ESPACAMENTO);

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

        gpOrigem.getColumnConstraints().addAll(
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10)
        );

        gpDestino.getColumnConstraints().addAll(
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10)
        );

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
            Label lbDestino = new Label("Destino " + k + " Nome:");
            Label lbDemanda = new Label("Demanda:");
            TextField tfDestino = new TextField();
            TextField tfDemanda = new TextField();
            //COLUNA LINHA
            gpDestino.add(lbDestino, j, i);
            gpDestino.add(tfDestino, j + 1, i);
            gpDestino.add(lbDemanda, j + 2, i);
            gpDestino.add(tfDemanda, j + 3, i);
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
            Label lbDestino = new Label("Destino " + k + " Nome:");
            Label lbDemanda = new Label("Demanda:");
            TextField tfDestino = new TextField();
            TextField tfDemanda = new TextField();
            //COLUNA LINHA
            gpDestino.add(lbDestino, j, i);
            gpDestino.add(tfDestino, j + 1, i);
            gpDestino.add(lbDemanda, j + 2, i);
            gpDestino.add(tfDemanda, j + 3, i);
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
            ajustaGridPaneEntrada(gpTransbordoTransbordo);
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
            VBox vb = new VBox();
            vb.getChildren().addAll(lb, tf, gpOrigemTransbordo, gpDestinoTransbordo, gpTransbordoTransbordo);
            vbTransbordo.getChildren().add(vb);
        }
// -----------------------------------------------------------------------------        
        vbEntrada.getChildren().addAll(lbPulaLinha1, lbOrigem, lbPulaLinha2, gpOrigem,
                lbPulaLinha3, lbDestino, lbPulaLinha4, gpDestino,
                lbPulaLinha5, lbTransbordo, lbPulaLinha6, vbTransbordo
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
            Label lbOrigem = (Label) getNodeByRowColumnIndex(i, j, gpOrigem);
            Label lbOferta = (Label) getNodeByRowColumnIndex(i, j + 2, gpOrigem);
            TextField tfOrigem = (TextField) getNodeByRowColumnIndex(i, j + 1, gpOrigem);
            TextField tfOferta = (TextField) getNodeByRowColumnIndex(i, j + 3, gpOrigem);

            System.out.println(lbOrigem.getText());
            System.out.println(lbOferta.getText());
            System.out.println(tfOrigem.getText());
            System.out.println(tfOferta.getText());

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
            Label lbDestino = (Label) getNodeByRowColumnIndex(i, j, gpDestino);
            Label lbDemanda = (Label) getNodeByRowColumnIndex(i, j + 2, gpDestino);
            TextField tfDestino = (TextField) getNodeByRowColumnIndex(i, j + 1, gpDestino);
            TextField tfDemanda = (TextField) getNodeByRowColumnIndex(i, j + 3, gpDestino);

            System.out.println(lbDestino.getText());
            System.out.println(lbDemanda.getText());
            System.out.println(tfDestino.getText());
            System.out.println(tfDemanda.getText());

            j = j + 4;
        }
//TRANSBORDO -------------------------------------------------------------------        
        int t = 0;
        for (t = 0; t < qtdTransbordo; t++) {

            VBox vb = (VBox) vbTransbordo.getChildren().get(t);

            TextField tf = (TextField) vb.getChildren().get(1);
            GridPane gpOrigemTransbordo = (GridPane) vb.getChildren().get(2);
            GridPane gpDestinoTransbordo = (GridPane) vb.getChildren().get(3);
            GridPane gpTransbordoTransbordo = (GridPane) vb.getChildren().get(4);

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
                Label lbOrigem = (Label) getNodeByRowColumnIndex(i, j, gpOrigemTransbordo);
                TextField tfOrigem = (TextField) getNodeByRowColumnIndex(i, j + 1, gpOrigemTransbordo);
                j = j + 2;

                System.out.println(lbOrigem.getText());
                System.out.println(tfOrigem.getText());
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
                Label lbDestino = (Label) getNodeByRowColumnIndex(i, j, gpDestinoTransbordo);
                TextField tfDestino = (TextField) getNodeByRowColumnIndex(i, j + 1, gpDestinoTransbordo);
                j = j + 2;

                System.out.println(lbDestino.getText());
                System.out.println(tfDestino.getText());
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
                Label lbTransbordo = (Label) getNodeByRowColumnIndex(i, j, gpTransbordoTransbordo);
                TextField tfTransbordo = (TextField) getNodeByRowColumnIndex(i, j + 1, gpTransbordoTransbordo);
                j = j + 2;

                System.out.println(lbTransbordo.getText());
                System.out.println(tfTransbordo.getText());
            }

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
        });
    }

    private ColumnConstraints criaColumnConstraintsPercentual(double percentual) {
        ColumnConstraints c = new ColumnConstraints(percentual);
        c.setPercentWidth(percentual);
        return c;
    }

    public Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
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

    private void ajustaGridPaneEntrada(final GridPane gridPane) {
        gridPane.getColumnConstraints().addAll(
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10),
                criaColumnConstraintsPercentual(15),
                criaColumnConstraintsPercentual(10)
        );
    }
}
