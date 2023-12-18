package com.books.thebookinitiative;

import com.books.thebookinitiative.controllers.BooksController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class BookApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BookApplication.class.getResource("books-view.fxml"));
        Parent parent = fxmlLoader.load();//Load the fxml
        Image icon = new Image("file:images/bookicon.png");
        stage.getIcons().add(icon);

        BooksController controller = fxmlLoader.getController(); //Get controller ref before scene is made

        Scene scene = new Scene(parent, 600, 800);
        stage.setScene(scene);
        controller.init(getClass().getResource("book-view.fxml"));

        stage.setTitle("The Book Initiative");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}