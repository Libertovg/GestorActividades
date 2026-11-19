/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Main;

import Modelos.datosApp;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author DAM2
 */
public class App extends Application {

    private static Scene escena;

    private static Scene scene;
    private static final datosApp datos = new datosApp();

    public static datosApp getDatos() {
        return datos;
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        escena = new Scene(loadFXML("inicio"), 1100, 750);
        stage.setScene(escena);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/vistas/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void setRoot(String fxml) throws IOException {
        escena.setRoot(loadFXML(fxml));
    }

}
