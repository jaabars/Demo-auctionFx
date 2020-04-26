package kg.megacom.okhttptest;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kg.megacom.okhttptest.http.OkHttpHelper;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();

    }

    public void start(Stage primaryStage) throws Exception {
        Parent root=FXMLLoader.load(getClass().getResource("/layouts/lotViewForm.fxml"));
        primaryStage.setTitle("User App");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }
}
