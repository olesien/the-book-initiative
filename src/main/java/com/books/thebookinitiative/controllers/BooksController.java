package com.books.thebookinitiative.controllers;

import com.books.thebookinitiative.Firebase;
import com.books.thebookinitiative.OpenLibrary;
import com.books.thebookinitiative.openlibrary.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class BooksController {
    URL bookUrl;

    URL addReviewUrl;
    OpenLibrary openLibraryAPI = new OpenLibrary();

    Firebase firebase = new Firebase();

    int currentPage = 0;

    int per = 10;

    String searchText = "";

    @FXML
    private TextField search;

    @FXML
    private ComboBox category;

    @FXML
    private VBox list;

    @FXML
    private BorderPane app;

    public void changePange(int newPage) {
        currentPage = newPage;
        if (search.getLength() > 0) {
            makeSearch();
        } else {
            fetchBooksBySubject(category.getValue().toString().toLowerCase());
        }
       
    }

    void showBook(String key, Author author, Integer cover_id) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(bookUrl);
        Stage stage = new Stage();
        Parent parent = null;//Load the fxml
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
        controller.init(key, author, addReviewUrl);
        stage.setTitle("The Book Initiative");
        stage.show();
    }

    public void renderBooks(ArrayList<Works> books, int total_books) {
        //Clear items
        list.getChildren().clear();
        books.forEach(book -> {
            Image bookCover = new Image("file:images/Preview.png", 90, 130, false, false);
            ImageView imageView = new ImageView(bookCover);
            VBox coverContainer = new VBox(imageView);
            coverContainer.setPadding(new Insets(0, 10, 0, 10));
            Text title = new Text(book.title);
            title.setFont(new Font(20));

            title.setOnMouseClicked(e -> {
               showBook(book.key, book.authors.get(0), book.cover_id);
            });

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
            Pagination pagination = new Pagination(total_books / per, currentPage);
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
    }
    
    //Handle search
    public void makeSearch() {
        if (searchText == "") return;
        System.out.println("Started search");
        //Set loading
        list.getChildren().clear();
        Text loadingText = new Text("Searching...");
        list.getChildren().add(loadingText);
        try {
            //Make the search to the open library api. Replace all spaces with - as that otherwise screws the call
            BookSearch booksList = openLibraryAPI.getBooksBySearch(searchText.replace(" ", "-"), currentPage, per);

            ArrayList<Works> books = booksList.docs.stream().map(doc -> {
                Works book = new Works();
                book.key = doc.key;
                book.subject = doc.subject;
                book.title = doc.title;
                book.first_publish_year = doc.first_publish_year;
                book.cover_id = doc.cover_i;

                AtomicInteger i = new AtomicInteger(); //So that the index can be incremented without errors

                //Change author type
                ArrayList<Author> authors = doc.author_name.stream().map((name) -> {

                    Author author = new Author();
                    author.name = name;
                    author.key = doc.author_key.get(i.get());
                    i.getAndIncrement();
                    return author;
                }).collect(Collectors
                        .toCollection(ArrayList::new)); ;
                book.authors = authors;
                return book;
            }).collect(Collectors
                    .toCollection(ArrayList::new)); ;

            renderBooks(books, booksList.numFound);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void fetchBooksBySubject(String subject) {
        //Set loading
        list.getChildren().clear();
        Text loadingText = new Text("Loading...");
        list.getChildren().add(loadingText);
        try {
            BooksList bookslist = openLibraryAPI.getBooksBySubject(subject, currentPage, per);

            renderBooks(bookslist.works, bookslist.work_count);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void init(URL bookUrl, URL addReviewUrl)
    {
        //Run on start
        System.out.println("Started");

        this.bookUrl = bookUrl;
        this.addReviewUrl = addReviewUrl;

        try {
            List<String> categories = firebase.getCategories(new URL("https://books-initiative-default-rtdb.europe-west1.firebasedatabase.app/categories.json"));
            System.out.println(categories);
            category.setItems(FXCollections.observableList(categories));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        category.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            System.out.println("CATEGORY: " + newValue.toString());

            fetchBooksBySubject(newValue.toString().toLowerCase());
        });

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            searchText = newValue;
        });

        search.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    //Out of foucs
                    makeSearch();
                }
            }
        });



    }

}