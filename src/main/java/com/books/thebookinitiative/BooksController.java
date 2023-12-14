package com.books.thebookinitiative;

import com.books.thebookinitiative.openlibrary.BooksList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BooksController {
    OpenLibrary openLibraryAPI = new OpenLibrary();

    Firebase firebase = new Firebase();

    @FXML
    private TextField search;

    @FXML
    private ComboBox category;

    @FXML
    private VBox list;

    void fetchBooks() {
        try {
            BooksList bookslist = openLibraryAPI.getBooksBySubject("Fantasy");
            System.out.println(bookslist.works.get(0).title);

            //Clear items
            list.getChildren().clear();

            bookslist.works.forEach(book -> {

                Text title = new Text(book.title);
                title.setFont(new Font(20));

                VBox bookInfo = new VBox(title);
                bookInfo.prefHeight(150);
                bookInfo.prefWidth(236);
                bookInfo.setSpacing(15);

                HBox item = new HBox(bookInfo);
                item.prefHeight(100);
                item.prefWidth(200);
                item.setSpacing(10);

                list.getChildren().add(item);

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

        fetchBooks();

    }

}