package com.sccs;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    
    private static Scene scene;
    
    @Override
    public void start (Stage stage) throws IOException {

       scene = new Scene(loadFXML("view/LoginView"));

        stage.setScene(scene);
        stage.setTitle("Swimming Club Control System");
        stage.show();
    }
    
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String path) throws IOException {
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(path + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
