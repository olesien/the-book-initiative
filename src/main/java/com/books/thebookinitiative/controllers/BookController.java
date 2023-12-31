package com.books.thebookinitiative.controllers;

import com.books.thebookinitiative.BookApplication;
import com.books.thebookinitiative.Firebase;
import com.books.thebookinitiative.OpenLibrary;
import com.books.thebookinitiative.Review;
import com.books.thebookinitiative.openlibrary.Author;
import com.books.thebookinitiative.openlibrary.Book;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.io.IOException;
import java.util.List;
import static java.lang.String.format;

public class BookController {
    BookApplication bookApplication;
    String key;
    String bookId;

    Author authorObject;
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
    ListView<String> subjects;

    @FXML
    VBox reviews;

    @FXML
    Text reviewText;

    @FXML
    protected void onMakeReview() {
        System.out.println("Changing screen to be reviews");

        bookApplication.openAddReview(this, bookId);
    }

    //Get all the reviews and add them
    public void getReviews() {
        List<Review> reviewList = firebase.getReviewsByBookId(bookId);


        if (reviewList != null) {
            double averageReview = (double) reviewList.stream().mapToInt(number -> number.count).sum() / (double) reviewList.size();

            reviewText.setText(format("%.1f / 5", averageReview));

            reviews.getChildren().clear();
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
    }

    //Fetch the book
    public void fetchBook() {
        try {
            Book book = openLibraryAPI.getBook(key);
            title.setText(book.title);
            author.setText(authorObject.name);

            //Add click event on author name to change screen
            author.setOnMouseClicked(e -> {
                String[] splitKey = authorObject.key.split("/");
                String authorId = splitKey[splitKey.length - 1]; //The actual book id

                bookApplication.openAuthor(authorId);
            });

            //Add hover on author
            author.hoverProperty().addListener((obs, oldSelectedText, newSelectedText) -> {
                if (oldSelectedText != null) {
                    author.setStyle("");
                }
                if (newSelectedText != null) {
                    author.setStyle("-fx-background-color: lightblue;");
                }
            });
            author.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    author.setFill(Color.BLUE);
                } else {
                    author.setFill(Color.BLACK);
                }
            });

            description.setText(book.description);

            Image bookCover = new Image("file:images/Preview.png", 180, 250, false, false);
            cover.setImage(bookCover);

            //Load in the images later
            new Thread(() -> {
                Image bookCover2 = new Image(format("https://covers.openlibrary.org/b/id/%d-L.jpg", book.covers.get(0)), 180, 250, false, false);
                cover.setImage(bookCover2);
            }).start();

            if (book.subjects != null) {
                subjects.setItems(FXCollections.observableArrayList(book.subjects));
            }


            getReviews();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void init(BookApplication bookApplication, String key, Author authorObject)
    {

        String[] splitKey = key.split("/");
        String bookId =splitKey[splitKey.length - 1];
        System.out.println(bookId);
        this.bookApplication = bookApplication;
        this.key = key;
        this.bookId = bookId;
        this.authorObject = authorObject;
        fetchBook();
    }

}