package com.books.thebookinitiative.controllers;


import com.books.thebookinitiative.BookApplication;
import com.books.thebookinitiative.Firebase;
import com.books.thebookinitiative.OpenLibrary;
import com.books.thebookinitiative.Review;
import com.books.thebookinitiative.openlibrary.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.String.format;

public class AuthorController {
    BookApplication bookApplication;
    String key;
    OpenLibrary openLibraryAPI = new OpenLibrary();

    Firebase firebase = new Firebase();

    @FXML
    ImageView cover;

    @FXML
    Text authorName;

    @FXML
    VBox bookList;

    @FXML
    Text bio;

    public void renderBooks(ArrayList<Book> books) {
        HashMap<String, List<Review>> firebaseAllReviews = firebase.getAllReviews(); //Get the reviews


        //Clear items
        bookList.getChildren().clear();
        books.forEach(book -> {
            if (book.key == null || book.covers == null || book.covers.isEmpty()) return;
            String[] splitKey = book.key.split("/");
            String bookId =splitKey[splitKey.length - 1]; //The actual book id

            List<Review> reviewList = firebaseAllReviews.get(bookId);
            Image bookCover = new Image("file:images/Preview.png", 90, 130, false, false);
            ImageView imageView = new ImageView(bookCover);
            VBox coverContainer = new VBox(imageView);
            coverContainer.setPadding(new Insets(0, 10, 0, 10));
            Text title = new Text(book.title);
            title.setFont(new Font(20));

            title.setOnMouseClicked(e -> {
                Author author = new Author();

                author.key =key;
                author.name = authorName.getText();
                System.out.println(book.key);
                bookApplication.openBook(book.key, author);
            });

            title.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    title.setFill(Color.BLUE);
                } else {
                    title.setFill(Color.BLACK);
                }
            });

            title.setFont(new Font(16));

            //Text desc = new Text(book.description);
            title.setFont(new Font(14));

            VBox bookInfo = new VBox(title);
            bookInfo.prefHeight(150);
            bookInfo.prefWidth(236);
            bookInfo.setSpacing(15);


            //Reviews
            Text reviewText = new Text("- / 5");
            reviewText.setTextAlignment(TextAlignment.CENTER);

            //If the reviewList actually has any content, we want to change the text
            if (reviewList != null) {
                double averageReview = (double) reviewList.stream().mapToInt(number -> number.count).sum() / (double) reviewList.size();

                reviewText.setText(format("%.1f / 5", averageReview));
            }

            HBox reviewIcons = new HBox();
            reviewIcons.setAlignment(Pos.CENTER);

            VBox reviews = new VBox(reviewText, reviewIcons);
            reviews.prefHeight(150);
            reviews.prefWidth(136);
            reviews.setSpacing(5);
            reviews.setAlignment(Pos.CENTER);

            BorderPane item = new BorderPane();
            item.setLeft(coverContainer);
            item.setCenter(bookInfo);
            item.setRight(reviews);


            bookList.getChildren().add(item);

            //Load in the images later
            if (book.covers != null && !book.covers.isEmpty()) {
                new Thread(() -> {
                    Image bookCover2 = new Image(format("https://covers.openlibrary.org/b/id/%d-M.jpg", book.covers.get(0)), 90, 130, false, false);
                    imageView.setImage(bookCover2);
                }).start();
            }


        });
    }


    public void init(BookApplication bookApplication, String key)
    {
        this.key = key;
        this.bookApplication = bookApplication;
        String[] splitKey = key.split("/");
        String authorId =splitKey[splitKey.length - 1];

        try {
            AuthorDetailed author = openLibraryAPI.getAuthor(authorId);
            AuthorBooks authorBooks = openLibraryAPI.booksByAuthor(authorId);
            System.out.println(authorBooks.entries);
            authorName.setText(author.name);
            bio.setText(author.bio);

            Image bookCover = new Image("file:images/Preview.png", 180, 250, false, false);
            cover.setImage(bookCover);

           //If we have an image
            if (author.photos != null && !author.photos.isEmpty()) {
                //Load in the images later
                new Thread(() -> {
                    Image bookCover2 = new Image(format("https://covers.openlibrary.org/a/id/%d-L.jpg", author.photos.get(0)), 180, 250, false, false);
                    cover.setImage(bookCover2);
                }).start();
            }
            System.out.println(author.name);

            renderBooks(authorBooks.entries);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}