package udi48.transbordo;

import java.util.function.UnaryOperator;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class FXUtil {

    public static TextFormatter<Object> textFormatterDouble() {
        UnaryOperator<TextFormatter.Change> filter = new UnaryOperator<TextFormatter.Change>() {
            @Override
            public TextFormatter.Change apply(TextFormatter.Change t) {
                if (t.isReplaced()) {
                    if (t.getText().matches("[^0-9]")) {
                        t.setText(t.getControlText().substring(t.getRangeStart(), t.getRangeEnd()));
                    }
                }
                if (t.isAdded()) {
                    if (t.getControlText().contains(".")) {
                        if (t.getText().matches("[^0-9]")) {
                            t.setText("");
                        }
                    } else if (t.getText().matches("[^0-9.]")) {
                        t.setText("");
                    }
                }

                return t;
            }
        };

        TextFormatter<Object> textFormatter = new TextFormatter<>(filter);

        return textFormatter;
    }

    public static void formataTextFieldNumerico(final TextField textField) {
        textField.lengthProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            if (newValue.intValue() > oldValue.intValue()) {
                char ch = textField.getText().charAt(oldValue.intValue());
                if (!(ch >= '0' && ch <= '9')) {
                    textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                }
            }
        });
    }

    private static ColumnConstraints criaColumnConstraintsPercentual(double percentual) {
        ColumnConstraints c = new ColumnConstraints(percentual);
        c.setPercentWidth(percentual);
        return c;
    }

    public static Node getNodePorIndiceLinhaColuna(final int row, final int column, GridPane gridPane) {
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

    public static void ajustaPaneInstrucao(final Pane pane) {
        pane.setMaxWidth(ControleTamanhos.LARGURA);
        pane.setMinWidth(ControleTamanhos.LARGURA);
        pane.setStyle("-fx-background-color: cornflowerblue;");
    }

    public static void ajustaPaneTitulo(final Pane pane) {
        pane.setMaxWidth(ControleTamanhos.LARGURA);
        pane.setMinWidth(ControleTamanhos.LARGURA);
        pane.setStyle("-fx-background-color: darkgray;");
    }

    public static void ajustaPaneTransbordo(final Pane pane) {
        pane.setMaxWidth(ControleTamanhos.LARGURA);
        pane.setMinWidth(ControleTamanhos.LARGURA);
        pane.setStyle("-fx-background-color: lightgray;");
    }

    public static void ajustaGridPaneCabecalho(final GridPane gridPane) {
        gridPane.getColumnConstraints().addAll(
                criaColumnConstraintsPercentual(17),
                criaColumnConstraintsPercentual(8),
                criaColumnConstraintsPercentual(17),
                criaColumnConstraintsPercentual(8),
                criaColumnConstraintsPercentual(17),
                criaColumnConstraintsPercentual(8),
                criaColumnConstraintsPercentual(25)
        );
        gridPane.setVgap(ControleTamanhos.ESPACAMENTO);
        gridPane.setHgap(ControleTamanhos.ESPACAMENTO);
    }

    public static void ajustaGridPaneEntrada(final GridPane gridPane) {
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

    public static void ajustaGridPaneTransbordo(final GridPane gridPane) {
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
