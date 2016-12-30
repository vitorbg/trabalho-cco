package udi48.transbordo;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import udi48.transbordo.dominio.Destino;
import udi48.transbordo.dominio.Origem;
import udi48.transbordo.dominio.Transbordo;
import udi48.transbordo.transporte.Transporte;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        launch(args);

        Origem o1 = new Origem("Brasil", 300);
        Origem o2 = new Origem("Japao", 220);
        Origem o3 = new Origem("Brasil", 400);
//        Origem o1 = new Origem("Brasil", 400);

        Destino d1 = new Destino("Norte", 400);
        Destino d2 = new Destino("Sul", 520);
//        Destino d1 = new Destino("Norte", 500);

        double[] v = new double[7];
        v[0] = 40;
        v[1] = 55;
        v[2] = 9999;
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

        List<Origem> origem = new ArrayList<>();
        List<Destino> destino = new ArrayList<>();
        List<Transbordo> transbordo = new ArrayList<>();
        origem.add(o1);
        origem.add(o2);
        origem.add(o3);
        destino.add(d1);
        destino.add(d2);
        transbordo.add(t1);
        transbordo.add(t2);

        Transporte t = new Transporte(origem, destino, transbordo);

        t.montraQuadroCustos();

    }

}
