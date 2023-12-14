package com.books.thebookinitiative;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class BookApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BookApplication.class.getResource("books-view.fxml"));
        Parent parent = fxmlLoader.load();//Load the fxml

        Image icon = new Image("file:images/bookicon.png");
        System.out.println(icon.getUrl());
        System.out.println(icon.getWidth());
        stage.getIcons().add(icon);

        BooksController controller = fxmlLoader.getController(); //Get controller ref before scene is made

        Scene scene = new Scene(parent, 600, 600);
        stage.setScene(scene);
        controller.init();

        stage.setTitle("The Book Initiative");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}