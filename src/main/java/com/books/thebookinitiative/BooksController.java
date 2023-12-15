package com.books.thebookinitiative;

import com.books.thebookinitiative.openlibrary.BooksList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static java.lang.String.format;

public class BooksController {
    OpenLibrary openLibraryAPI = new OpenLibrary();

    Firebase firebase = new Firebase();

    int currentPage = 0;

    int per = 10;

    @FXML
    private TextField search;

    @FXML
    private ComboBox category;

    @FXML
    private VBox list;

    @FXML
    private BorderPane app;

    void changePange(int newPage) {
        currentPage = newPage;

        fetchBooks(category.getValue().toString().toLowerCase());
    }

    void fetchBooks(String subject) {

        try {
            BooksList bookslist = openLibraryAPI.getBooksBySubject(subject, currentPage, per);

            //Clear items
            list.getChildren().clear();


            bookslist.works.forEach(book -> {
                Image bookCover = new Image("file:images/Preview.png", 90, 130, false, false);
                ImageView imageView = new ImageView(bookCover);
                VBox coverContainer = new VBox(imageView);
                coverContainer.setPadding(new Insets(0, 10, 0, 10));
                Text title = new Text(book.title);
                title.setFont(new Font(20));

                Text author = new Text(book.authors.get(0).name);
                title.setFont(new Font(16));

                Text desc = new Text(book.first_publish_year.toString());
                title.setFont(new Font(14));

                VBox bookInfo = new VBox(title, author, desc);
                bookInfo.prefHeight(150);
                bookInfo.prefWidth(236);
                bookInfo.setSpacing(15);

                //Reviews
                Text reviewText = new Text("5 / 5");
                reviewText.setTextAlignment(TextAlignment.CENTER);

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

                //Set pagination
                Pagination pagination = new Pagination(bookslist.work_count / per, currentPage);
                app.setBottom(pagination);

                pagination.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                {
                    pagination.setDisable(true);
                    changePange(newIndex.intValue());
                    pagination.setDisable(false);
                });

                list.getChildren().add(item);

                //Load in the images later
                new Thread(() -> {
                    Image bookCover2 = new Image(format("https://covers.openlibrary.org/b/id/%d-M.jpg", book.cover_id), 90, 130, false, false);
                    imageView.setImage(bookCover2);
                }).start();

            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void init()
    {
        //Run on start
        System.out.println("Started");

        try {
            List<String> categories = firebase.getCategories(new URL("https://books-initiative-default-rtdb.europe-west1.firebasedatabase.app/categories.json"));
            System.out.println(categories);
            category.setItems(FXCollections.observableList(categories));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        category.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            System.out.println("CATEGORY: " + newValue.toString());

            fetchBooks(newValue.toString().toLowerCase());
        });



    }

}