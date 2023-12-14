package com.books.thebookinitiative;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BookApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BookApplication.class.getResource("books-view.fxml"));
        Parent parent = fxmlLoader.load();//Load the fxml

        //BooksController controller = fxmlLoader.getController(); //Get controller ref before scene is made

        Scene scene = new Scene(parent, 600, 600);
        stage.setScene(scene);
        //controller.init();
        stage.setTitle("Hello!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}