package com.books.thebookinitiative.controllers;


import com.books.thebookinitiative.BookApplication;
import com.books.thebookinitiative.Firebase;
import com.books.thebookinitiative.OpenLibrary;
import com.books.thebookinitiative.Review;
import com.books.thebookinitiative.openlibrary.Author;
import com.books.thebookinitiative.openlibrary.AuthorDetailed;
import com.books.thebookinitiative.openlibrary.Book;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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

public class AuthorController {
    BookApplication bookApplication;
    String key;
    String bookId;
    URL addReviewUrl;

    Author authorObject;
    OpenLibrary openLibraryAPI = new OpenLibrary();

    Firebase firebase = new Firebase();

    @FXML
    ImageView cover;

    @FXML
    Text authorName;

    @FXML
    VBox books;

    @FXML
    Text bio;


    public void init(BookApplication bookApplication, String key)
    {
        this.bookApplication = bookApplication;
        String[] splitKey = key.split("/");
        String authorId =splitKey[splitKey.length - 1];

        try {
            AuthorDetailed author = openLibraryAPI.getAuthor(authorId);
            authorName.setText(author.name);
            bio.setText(author.bio);


            System.out.println(author.name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}