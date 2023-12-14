package com.books.thebookinitiative;

import com.books.thebookinitiative.openlibrary.BooksList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

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

        try {
            BooksList bookslist = openLibraryAPI.getBooksBySubject("Fantasy");
            System.out.println(bookslist.works.get(0).title);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}