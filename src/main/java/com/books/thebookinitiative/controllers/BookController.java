package com.books.thebookinitiative.controllers;


import com.books.thebookinitiative.Firebase;
import com.books.thebookinitiative.OpenLibrary;
import com.books.thebookinitiative.Review;
import com.books.thebookinitiative.openlibrary.Author;
import com.books.thebookinitiative.openlibrary.Book;
import com.books.thebookinitiative.openlibrary.BooksList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static java.lang.String.format;

public class BookController {
    String bookId;
    URL addReviewUrl;
    OpenLibrary openLibraryAPI = new OpenLibrary();

    Firebase firebase = new Firebase();

    @FXML
    Text title;

    @FXML
    Text author;

    @FXML
    Text description;

    @FXML
    ImageView cover;

    @FXML
    ListView subjects;

    @FXML
    VBox reviews;

    @FXML
    protected void onMakeReview() {
        System.out.println("Changing screen to be reviews");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(addReviewUrl);
        Stage stage = new Stage();
        Parent parent = null;//Load the fxml
        try {
            parent = fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        Image icon = new Image("file:images/bookicon.png");
        stage.getIcons().add(icon);

        AddReviewController controller = fxmlLoader.getController(); //Get controller ref before scene is made

        Scene scene = new Scene(parent, 600, 800);
        stage.setScene(scene);
        controller.init(bookId);
        stage.setTitle("The Book Initiative");
        stage.show();
    }

    public void init(String key, Author authorObject, URL addReviewUrl)
    {
        String[] splitKey = key.split("/");
        String bookId =splitKey[splitKey.length - 1];
        System.out.println(bookId);
        this.bookId = bookId;
        this.addReviewUrl = addReviewUrl;
        try {
            Book book = openLibraryAPI.getBook(key);
            System.out.println(book);
            title.setText(book.title);
            author.setText(authorObject.name);

            description.setText(book.description);

            Image bookCover = new Image(format("https://covers.openlibrary.org/b/id/%d-L.jpg", book.covers.get(0)), 180, 250, false, false);
            cover.setImage(bookCover);

            subjects.setItems(FXCollections.observableArrayList(book.subjects));

            List<Review> reviewList = firebase.getReviewsByBookId(bookId);
            System.out.println(reviewList);
            if (reviewList != null) {
                reviewList.forEach(r -> {
                    Text name = new Text(r.name);
                    name.setFont(new Font(14));

                    Text title = new Text(r.title);
                    title.setFont(new Font(14));

                    Text content = new Text(r.content);
                    Text reviewText = new Text( r.count + " / 5");

                    BorderPane reviewTop = new BorderPane();
                    reviewTop.setLeft(name);
                    reviewTop.setRight(reviewText);

                    VBox reviewContent = new VBox(title, content);
                    reviewContent.setPadding(new Insets(10, 10, 10, 10));
                    reviewContent.setSpacing(5);
                    reviewContent.setBackground(new Background(new BackgroundFill(Color.rgb(230, 247, 255), CornerRadii.EMPTY, Insets.EMPTY)));

                    VBox review = new VBox(reviewTop, reviewContent);
                    review.setMaxWidth(Double.MAX_VALUE);
                    review.setMaxHeight(Double.MAX_VALUE);
                    review.setSpacing(10);
                    reviews.getChildren().add(review);
                });
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}