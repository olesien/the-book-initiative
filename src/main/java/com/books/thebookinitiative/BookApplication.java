package com.books.thebookinitiative;

import com.books.thebookinitiative.controllers.AddReviewController;
import com.books.thebookinitiative.controllers.AuthorController;
import com.books.thebookinitiative.controllers.BookController;
import com.books.thebookinitiative.controllers.BooksController;
import com.books.thebookinitiative.openlibrary.Author;
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

        Scene scene = new Scene(parent, 650, 800);
        stage.setScene(scene);
        controller.init(this);

        stage.setTitle("The Book Initiative");
        stage.show();
    }

    public void openAuthor(String key) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("author-view.fxml"));
        Stage stage = new Stage();
        Parent parent; //Load the fxml
        try {
            parent = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Image icon = new Image("file:images/bookicon.png");
        stage.getIcons().add(icon);

        AuthorController controller = fxmlLoader.getController(); //Get controller ref before scene is made

        Scene scene = new Scene(parent, 600, 400);
        stage.setScene(scene);
        controller.init(this, key);
        stage.setTitle("The Book Initiative");
        stage.show();
    }

    public void openAddReview(BookController bookController, String bookId) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("add-review.fxml"));
        Stage stage = new Stage();
        Parent parent;//Load the fxml
        try {
            parent = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Image icon = new Image("file:images/bookicon.png");
        stage.getIcons().add(icon);

        AddReviewController controller = fxmlLoader.getController(); //Get controller ref before scene is made

        Scene scene = new Scene(parent, 600, 400);
        stage.setScene(scene);
        controller.init(stage, bookId, bookController);
        stage.setTitle("The Book Initiative");
        stage.show();
    }

    public void openBook(String key, Author author) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("book-view.fxml"));
        Stage stage = new Stage();
        Parent parent; //Load the fxml
        try {
            parent = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Image icon = new Image("file:images/bookicon.png");
        stage.getIcons().add(icon);

        BookController controller = fxmlLoader.getController(); //Get controller ref before scene is made

        Scene scene = new Scene(parent, 600, 800);
        stage.setScene(scene);
        controller.init(this, key, author);
        stage.setTitle("The Book Initiative");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}